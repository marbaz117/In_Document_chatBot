package com.example.InDocumentCharBot.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.example.InDocumentCharBot.model.ParsedBlock;

public class SmartPdfParser {

    private static double similarity(String a, String b) {
        Set<String> s1 = new HashSet<>(Arrays.asList(a.toLowerCase().split(" ")));
        Set<String> s2 = new HashSet<>(Arrays.asList(b.toLowerCase().split(" ")));

        Set<String> intersection = new HashSet<>(s1);
        intersection.retainAll(s2);

        Set<String> union = new HashSet<>(s1);
        union.addAll(s2);

        return (double) intersection.size() / union.size();
    }

    private static String clean(String line) {
        return line.replaceAll("\\s+", " ").trim();
    }

    private static boolean isNoise(String line) {
        return line.isEmpty() || line.matches("Page \\d+") || line.length() < 3;
    }

    private static boolean isHeading(String line) {
        return line.equals(line.toUpperCase()) && line.length() < 60;
    }

    private static boolean isParagraphEnd(String line) {
        return line.endsWith(".") || line.endsWith("?") || line.endsWith("!");
    }


    private static void buildRelations(List<ParsedBlock> blocks) {

        DisjointSet ds = new DisjointSet(blocks.size());

        for (int i = 0; i < blocks.size(); i++) {
            for (int j = i + 1; j < blocks.size(); j++) {

                double sim = similarity(blocks.get(i).getText(), blocks.get(j).getText());

                if (sim > 0.4) {
                    ds.union(i, j);
                    blocks.get(i).getRelatedIds().add(j);
                    blocks.get(j).getRelatedIds().add(i);
                }
            }
        }
    }

    public static List<ParsedBlock> parse(File file) throws Exception {

        List<ParsedBlock> blocks = new ArrayList<>();
        PDDocument doc = PDDocument.load(file);
        PDFTextStripper stripper = new PDFTextStripper();

        int id = 0;
        String currentSection = "General";

        for (int page = 1; page <= doc.getNumberOfPages(); page++) {

            stripper.setStartPage(page);
            stripper.setEndPage(page);

            String text = stripper.getText(doc);
            String[] lines = text.split("\n");

            StringBuilder paragraph = new StringBuilder();

            for (int i = 0; i < lines.length; i++) {

                String line = clean(lines[i]);

                if (isNoise(line)) continue;

                if (isHeading(line)) {
                    currentSection = line;
                    blocks.add(new ParsedBlock(id++, line, "heading", currentSection, page));
                    continue;
                }

                if (line.endsWith("-") && i + 1 < lines.length) {
                    line = line.substring(0, line.length() - 1) + clean(lines[i + 1]);
                    i++;
                }

                if (isParagraphEnd(line)) {
                    paragraph.append(line);
                    blocks.add(new ParsedBlock(id++, paragraph.toString(), "paragraph", currentSection, page));
                    paragraph.setLength(0);
                } else {
                    paragraph.append(line).append(" ");
                }
            }
        }

        doc.close();
        buildRelations(blocks);
        return blocks;
    }
}
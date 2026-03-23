package com.example.InDocumentCharBot.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParsedBlock {

    private int id;
    private String text;
    private String type;
    private String section;
    private int page;
    private List<String> keywords;
    private Set<Integer> relatedIds = new HashSet<>();

    public ParsedBlock(int id, String text, String type, String section, int page) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.section = section;
        this.page = page;
        this.keywords = extractKeywords(text);
    }

    private List<String> extractKeywords(String text) {
        String[] words = text.toLowerCase().split("\\W+");
        Set<String> stopWords = Set.of("the","is","and","of","to","a","in","for","on","with");

        List<String> result = new ArrayList<>();
        for (String w : words) {
            if (!stopWords.contains(w) && w.length() > 3) {
                result.add(w);
            }
        }
        return result;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getText() { return text; }
    public String getType() { return type; }
    public String getSection() { return section; }
    public int getPage() { return page; }
    public List<String> getKeywords() { return keywords; }
    public Set<Integer> getRelatedIds() { return relatedIds; }
}
package com.example.InDocumentCharBot.service;

import com.example.InDocumentCharBot.model.ParsedBlock;
import com.example.InDocumentCharBot.util.SmartPdfParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class PdfService {

    public List<ParsedBlock> parsePdf(MultipartFile file) throws Exception {

        File tempFile = File.createTempFile("upload", ".pdf");
        file.transferTo(tempFile);

        List<ParsedBlock> result = SmartPdfParser.parse(tempFile);

        tempFile.delete();

        return result;
    }
}
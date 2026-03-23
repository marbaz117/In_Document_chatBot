package com.example.InDocumentCharBot.controller;

import com.example.InDocumentCharBot.model.ParsedBlock;
import com.example.InDocumentCharBot.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService service;

    @PostMapping("/parse")
    public List<ParsedBlock> parsePdf(@RequestParam("file") MultipartFile file) throws Exception {
        return service.parsePdf(file);
    }
}
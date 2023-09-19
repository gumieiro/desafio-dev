package com.gumieiro.devchallenge.controllers;

import com.gumieiro.devchallenge.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TransactionController {

    @Autowired
    TransactionService service;

    @GetMapping("/transaction/import")
    public String importCNAB() {
        return "import";
    }

    @PostMapping("/transaction/import")
    public String importCNAB(@RequestParam("file") MultipartFile file) {
        service.importCNAB(file);
        return "redirect:/store";
    }
}

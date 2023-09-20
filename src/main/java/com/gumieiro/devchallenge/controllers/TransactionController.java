package com.gumieiro.devchallenge.controllers;

import com.gumieiro.devchallenge.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
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
        return "fragments/import";
    }

    @PostMapping("/transaction/import")
    public String importCNAB(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        if (Boolean.TRUE.equals(service.importCNAB(file))) return "redirect:/store";
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}

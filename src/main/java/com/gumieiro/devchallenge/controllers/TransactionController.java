package com.gumieiro.devchallenge.controllers;

import com.gumieiro.devchallenge.entities.models.Transaction;
import com.gumieiro.devchallenge.services.TransactionService;

import lombok.NonNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService service;

    @GetMapping("/import")
    public String importCNAB() {
        return "pages/import";
    }

    @PostMapping("/import")
    public String importCNAB(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
            throws Exception {
        return service.importCNAB(file, redirectAttributes);
    }

    @GetMapping("/search/{name}")
    public String getByStoreName(@PathVariable @NonNull String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {
        Page<Transaction> transactions = service.findAllByStore(name, page, pageSize);
        model.addAttribute("balance", Transaction.balance(transactions.getContent()));
        model.addAttribute("items", transactions);
        model.addAttribute("selected", name);
        
        return "pages/list";
    }

    @GetMapping("/list")
    public String getAll(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {
        Page<Transaction> transactions = service.findAll(page, pageSize);
        model.addAttribute("items", transactions);
        model.addAttribute("balance", Transaction.balance(transactions.getContent()));
        model.addAttribute("selected", null);
        return "pages/list";
    }
}

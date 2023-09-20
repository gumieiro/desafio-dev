package com.gumieiro.devchallenge.controllers;

import com.gumieiro.devchallenge.entities.models.Store;
import com.gumieiro.devchallenge.services.StoreService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class StoreController {

    @Autowired
    StoreService service;

    @GetMapping("/store/{name}")
    public String getByStoreName(@PathVariable @NonNull String name, Model model) {
        Store store = service.findByName(name);
        model.addAttribute("store", store);
        model.addAttribute("balance", store.balance());
        return "pages/store";
    }

    @GetMapping("/stores")
    public String getAll(Model model) {
        List<Store> stores = service.findAll();
        model.addAttribute("stores", stores);
        return "pages/store";
    }
}

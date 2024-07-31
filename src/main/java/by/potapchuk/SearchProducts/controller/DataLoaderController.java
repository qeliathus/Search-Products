package by.potapchuk.SearchProducts.controller;

import by.potapchuk.SearchProducts.service.DataLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/load")
public class DataLoaderController {
    @Autowired
    private DataLoaderService dataLoaderService;

    @PostMapping
    public ResponseEntity<String> loadData() {
        dataLoaderService.loadDataToElasticsearch();
        return ResponseEntity.ok("Data loaded successfully.");
    }
}
package by.potapchuk.SearchProducts.controller;

import by.potapchuk.SearchProducts.service.DataLoaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/load")
public class DataLoaderController {
    private final DataLoaderService dataLoaderService;

    public DataLoaderController(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    @PostMapping
    public ResponseEntity<String> loadData() {
        dataLoaderService.loadDataToElasticsearch();
        return ResponseEntity.ok("Data loaded successfully.");
    }
}
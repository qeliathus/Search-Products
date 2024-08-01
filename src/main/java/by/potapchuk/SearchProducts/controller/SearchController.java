package by.potapchuk.SearchProducts.controller;

import by.potapchuk.SearchProducts.core.dto.ProductDto;
import by.potapchuk.SearchProducts.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String query) {
        try {
            List<ProductDto> results = searchService.searchProducts(query);
            return ResponseEntity.ok(results);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
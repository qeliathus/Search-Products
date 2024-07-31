package by.potapchuk.SearchProducts.service;

import by.potapchuk.SearchProducts.core.entity.Product;
import by.potapchuk.SearchProducts.core.entity.Sku;
import by.potapchuk.SearchProducts.repository.ProductRepository;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataLoaderService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestHighLevelClient client;

    @Transactional(readOnly = true)
    public void loadDataToElasticsearch() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            List<Sku> skus = product.getSkus();

            Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", product.getId());
            productMap.put("name", product.getName());
            productMap.put("description", product.getDescription());
            productMap.put("price", product.getPrice());
            productMap.put("active", product.getActive());
            productMap.put("startDate", product.getStartDate());

            List<Map<String, Object>> skuList = skus.stream().map(sku -> {
                Map<String, Object> skuMap = new HashMap<>();
                skuMap.put("id", sku.getId());
                skuMap.put("skuCode", sku.getSkuCode());
                skuMap.put("description", sku.getDescription());
                skuMap.put("quantity", sku.getQuantity());
                skuMap.put("dateAdded", sku.getDateAdded());
                return skuMap;
            }).collect(Collectors.toList());

            productMap.put("skus", skuList);

            UpdateRequest updateRequest = new UpdateRequest("products", product.getId().toString())
                    .doc(productMap)
                    .upsert(productMap);
            try {
                UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
                System.out.println("Updated document id: " + updateResponse.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

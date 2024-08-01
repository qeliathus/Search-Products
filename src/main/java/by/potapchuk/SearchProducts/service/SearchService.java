package by.potapchuk.SearchProducts.service;

import by.potapchuk.SearchProducts.core.dto.ProductDto;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private DtoMapperService dtoMapperService;

    public List<ProductDto> searchProducts(String query) throws IOException {
        List<ProductDto> results = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("products");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(query, "name", "description"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            ProductDto productDto = new ProductDto();

            productDto.setId(((Number) sourceAsMap.get("id")).longValue());
            productDto.setName((String) sourceAsMap.get("name"));
            productDto.setDescription((String) sourceAsMap.get("description"));
            productDto.setPrice(BigDecimal.valueOf((Double) sourceAsMap.get("price")));
            productDto.setActive((Boolean) sourceAsMap.get("active"));
            productDto.setStartDate(LocalDate.parse((String) sourceAsMap.get("startDate")));

            results.add(productDto);
        }
        return results;
    }
}

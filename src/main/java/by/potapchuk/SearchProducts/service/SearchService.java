package by.potapchuk.SearchProducts.service;

import by.potapchuk.SearchProducts.core.dto.ProductDto;
import by.potapchuk.SearchProducts.core.dto.SkuDto;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private final RestHighLevelClient client;
    private final DtoMapperService dtoMapperService;

    public SearchService(RestHighLevelClient client, DtoMapperService dtoMapperService) {
        this.client = client;
        this.dtoMapperService = dtoMapperService;
    }

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

            List<Map<String, Object>> skuList = (List<Map<String, Object>>) sourceAsMap.get("skus");
            if (skuList != null) {
                List<SkuDto> skuDtos = new ArrayList<>();
                for (Map<String, Object> skuMap : skuList) {
                    SkuDto skuDto = new SkuDto();
                    skuDto.setId(((Number) skuMap.get("id")).longValue());
                    skuDto.setSkuCode((String) skuMap.get("skuCode"));
                    skuDto.setDescription((String) skuMap.get("description"));
                    skuDto.setQuantity((Integer) skuMap.get("quantity"));
                    skuDto.setDateAdded(LocalDate.parse((String) skuMap.get("dateAdded")));
                    skuDtos.add(skuDto);
                }
                productDto.setSkus(skuDtos);
            }

            results.add(productDto);
        }
        return results;
    }
}

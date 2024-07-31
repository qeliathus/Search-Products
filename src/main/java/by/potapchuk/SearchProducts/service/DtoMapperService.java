package by.potapchuk.SearchProducts.service;

import by.potapchuk.SearchProducts.core.dto.ProductDto;
import by.potapchuk.SearchProducts.core.dto.SkuDto;
import by.potapchuk.SearchProducts.core.entity.Product;
import by.potapchuk.SearchProducts.core.entity.Sku;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoMapperService {

    public ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setActive(product.getActive());
        productDto.setStartDate(product.getStartDate());
        List<SkuDto> skuDtos = product.getSkus().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        productDto.setSkus(skuDtos);
        return productDto;
    }

    public SkuDto convertToDto(Sku sku) {
        SkuDto skuDto = new SkuDto();
        skuDto.setId(sku.getId());
        skuDto.setSkuCode(sku.getSkuCode());
        skuDto.setDescription(sku.getDescription());
        skuDto.setQuantity(sku.getQuantity());
        skuDto.setDateAdded(sku.getDateAdded());
        return skuDto;
    }
}
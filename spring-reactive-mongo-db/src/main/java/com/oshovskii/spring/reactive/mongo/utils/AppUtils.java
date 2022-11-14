package com.oshovskii.spring.reactive.mongo.utils;

import com.oshovskii.spring.reactive.mongo.dto.ProductDto;
import com.oshovskii.spring.reactive.mongo.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    private AppUtils() {
    }

    public static ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}

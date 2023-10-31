package ru.clevertec.product.mapper.impl;

import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;

public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        Product product = new Product();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        return product;
    }

    @Override
    public InfoProductDto toInfoProductDto(Product product) {
        if (product == null) {
            return null;
        }
        return new InfoProductDto(product.getUuid(), product.getName(), product.getDescription(), product.getPrice());
    }

    @Override
    public Product merge(Product product, ProductDto productDto) {
        if (product == null || productDto == null) {
            return null;
        }
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        return product;
    }
}

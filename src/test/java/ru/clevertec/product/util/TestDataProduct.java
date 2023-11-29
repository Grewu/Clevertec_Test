package ru.clevertec.product.util;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with",toBuilder = true)
@Accessors(chain = true)
public class TestDataProduct {
    @Builder.Default
    private UUID uuid = UUID.fromString("b6e1d925-ebca-458e-b032-c3dd2b8f1671");

    @Builder.Default
    private String name = "Product";

    @Builder.Default
    private String description = "Description";

    @Builder.Default
    private BigDecimal price = BigDecimal.valueOf(1.01);

    @Builder.Default
    private LocalDateTime created = LocalDateTime.of(2023, Month.DECEMBER, 26, 17, 0, 0);

    public Product buildProduct(){
        return new Product(uuid,name,description,price,created);
    }

    public ProductDto buildProductDTO(){
        return new ProductDto(name,description,price);
    }

    public InfoProductDto buidInfoProductDto(){
        return  new InfoProductDto(uuid,name,description,price);
    }

}

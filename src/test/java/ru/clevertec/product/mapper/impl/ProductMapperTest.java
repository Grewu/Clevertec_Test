package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.TestDataProduct;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {
    private ProductMapperImpl mapper;

    @ParameterizedTest
    @CsvSource(value = {"Product,NewProduct,1.57"})
    void toProductShouldReturnProduct(String name, String description, BigDecimal bigDecimal) {
        ProductDto productDto = TestDataProduct.builder().
                withName(name)
                .withDescription(description)
                .withPrice(bigDecimal)
                .build().buildProductDTO();
        Product expected = TestDataProduct.builder()
                .withUuid(null)
                .withName(name)
                .withDescription(description)
                .withPrice(bigDecimal).build().buildProduct();
        Product actual = mapper.toProduct(productDto);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"Product", "Description,1.58"})
    void toInfoProductDtoShouldReturnInfoProductDTO(String name, String description, BigDecimal bigDecimal) {
        Product expected = TestDataProduct.builder()
                .withName(name)
                .withCreated(null)
                .withDescription(description)
                .withPrice(bigDecimal).build().buildProduct();
        InfoProductDto actual = mapper.toInfoProductDto(expected);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"Product1,Desc1,2.0,Product1,Desc1,2.0",
            "Product2,Desc2,3.5,Product2,NewDesc2,4.0",
            "Product3,Desc3,5.0,Product3,NewDesc3,6.0"})
    void mergeShouldReturnMergedProduct(Product product, ProductDto productDto,
                                        String mergedName, String mergedDesc, BigDecimal mergedPrice) {
        Product expected = TestDataProduct.builder()
                .withName(mergedName)
                .withCreated(null)
                .withDescription(mergedDesc)
                .withPrice(mergedPrice)
                .build()
                .buildProduct();
        Product actual = mapper.merge(product, productDto);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"Product1,Desc1,2.0", "Product2,Desc2,3.5", "Product3,Desc3,5.0"})
    void toInfoProductDtoShouldReturnInfoProductDTOWithDifferentValues(String name, String description, BigDecimal bigDecimal) {
        Product expected = TestDataProduct.builder()
                .withName(name)
                .withCreated(null)
                .withDescription(description)
                .withPrice(bigDecimal)
                .build()
                .buildProduct();
        InfoProductDto actual = mapper.toInfoProductDto(expected);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"Product1,Desc1,2.0", "Product2,Desc2,3.5", "Product3,Desc3,5.0"})
    void toProductShouldReturnProductWithDifferentValues(String name, String description, BigDecimal bigDecimal) {
        ProductDto productDto = TestDataProduct.builder()
                .withName(name)
                .withDescription(description)
                .withPrice(bigDecimal)
                .build()
                .buildProductDTO();
        Product expected = TestDataProduct.builder()
                .withUuid(null)
                .withName(name)
                .withDescription(description)
                .withPrice(bigDecimal)
                .build()
                .buildProduct();
        Product actual = mapper.toProduct(productDto);
        assertEquals(expected, actual);
    }
}
package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.util.TestDataProduct;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


class ProductMapperTest {

    private final ProductMapper mapper = new ProductMapperImpl();

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
                .withCreated(null)
                .withPrice(bigDecimal).build().buildProduct();
        Product actual = mapper.toProduct(productDto);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"Product,Description,1.58", "AnotherProduct,AnotherDescription,2.0"})
    void toInfoProductDtoShouldReturnInfoProductDTO(String name, String description, BigDecimal bigDecimal) {
        InfoProductDto expected = TestDataProduct.builder()
                .withName(name)
                .withDescription(description)
                .withPrice(bigDecimal)
                .withCreated(null)
                .build()
                .buidInfoProductDto();
        Product product = TestDataProduct.builder().
                withName(name)
                .withDescription(description)
                .withPrice(bigDecimal)
                .withCreated(null)
                .build().buildProduct();
        InfoProductDto actual = mapper.toInfoProductDto(product);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"ProductA, ProductA, NewNameA, NewDescriptionA"})
    void mergeShouldReturnMergedProduct(String productName, String productDtoName, String description) {
        Product product = TestDataProduct.builder()
                .withName(productName)
                .withDescription(description)
                .build().buildProduct();
        ProductDto productDto = TestDataProduct.builder()
                .withName(productDtoName)
                .withDescription(description)
                .build().buildProductDTO();
        Product expected = TestDataProduct.builder()
                .withName(productName)
                .withDescription(description)
                .build().buildProduct();
        Product actual = mapper.merge(product, productDto);
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
                .withCreated(null)
                .withName(name)
                .withDescription(description)
                .withPrice(bigDecimal)
                .build()
                .buildProduct();
        Product actual = mapper.toProduct(productDto);
        assertEquals(expected, actual);
    }
}
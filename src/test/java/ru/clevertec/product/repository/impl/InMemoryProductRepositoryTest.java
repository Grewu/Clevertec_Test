package ru.clevertec.product.repository.impl;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.TestDataProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {
    @Mock
    private InMemoryProductRepository productRepository;

    @Test
    void findByIdShouldReturnExpectedProductWithUUID() {
        Product expected = TestDataProduct.builder().build().buildProduct();
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid());
    }

    @Test
    void findByIdShouldReturnExpectedProductEqualsWithoutUUID() {
        TestDataProduct expected = TestDataProduct.builder()
                .withUuid(null)
                .build();
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
    }

    @Test
    void findByIdShouldReturnOptionalEmpty() {
        UUID uuid = UUID.fromString("5724d294-44c0-4e12-8737-83f8ab775405");
        Optional<Product> expected = Optional.empty();
        Optional<Product> actual = productRepository.findById(uuid);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdShouldReturnExpectedProductWithName() {
        Product expected = TestDataProduct.builder().build().buildProduct();
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid());
    }

    @Test
    void findAll() {
        Product expected =  TestDataProduct.builder().build().buildProduct();
        List<Product> actual = productRepository.findAll();
        Mockito.when(actual.get(any())).thenReturn(expected);
    }

    @Test
    void save() {
        Product expected = TestDataProduct.builder().build().buildProduct();
        Product actual = productRepository.save(expected);
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
    }
    @Test
    void saveShouldReturnExpectedProductWithoutUUID() {
        Product expected = TestDataProduct.builder()
                .withUuid(null)
                .build()
                .buildProduct();
        Product actual = productRepository.save(expected);
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
    }
    @Test
    void saveShouldReturnExpectedProductWithPriceIsNull() {
        Product expected = TestDataProduct.builder()
                .withPrice(null)
                .build()
                .buildProduct();
        Product actual = productRepository.save(expected);
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
    }
    @Test
    void saveShouldThrowExceptionIllegalArgumentException() {
       when(productRepository.save(null))
               .thenThrow(IllegalArgumentException.class);
    }
    @Test
    void deleteProductWithUUID() {
        Product expected = TestDataProduct.builder().build().buildProduct();
       productRepository.delete(expected.getUuid());
        verify(productRepository).delete(expected.getUuid());
    }

    @Test
    void deleteProductEqualsWithoutUUID() {
        TestDataProduct expected = TestDataProduct.builder()
                .withUuid(null)
                .build();
         verify(productRepository).delete(expected.getUuid());
    }

    @Test
    void deleteShouldWithName() {
        TestDataProduct expected = TestDataProduct.builder()
                .withName("test")
                .build();
        verify(productRepository).delete(expected.getUuid());
    }

}
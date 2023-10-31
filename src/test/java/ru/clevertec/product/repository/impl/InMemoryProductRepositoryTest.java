package ru.clevertec.product.repository.impl;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.TestDataProduct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {
    @Mock
    private InMemoryProductRepository productRepository;

    @Test
    void findByIdShouldReturnExpectedProductWithUUID() {
        Product expected = TestDataProduct.builder().build().buildProduct();
        when(productRepository.findById(expected.getUuid())).thenReturn(Optional.of(expected));
        Optional<Product> actual = productRepository.findById(expected.getUuid());
        assertEquals(expected, actual.get());
    }

    @Test
    void findByIdShouldReturnExpectedProductEqualsWithoutUUID() {
        Product expected = TestDataProduct.builder()
                .withUuid(null)
                .build()
                .buildProduct();
        when(productRepository.findById(expected.getUuid())).thenReturn(Optional.of(expected));
        Optional<Product> actual = productRepository.findById(expected.getUuid());
        assertEquals(expected, actual.get());
    }

    @Test
    void findByIdShouldReturnOptionalEmpty() {
        UUID uuid = UUID.fromString("5724d294-44c0-4e12-8737-83f8ab775405");
        Optional<Product> expected = Optional.empty();
        Optional<Product> actual = productRepository.findById(uuid);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("findAllTestData")
    void testFindAll(List<Product> repositoryData) {
        when(productRepository.findAll()).thenReturn(repositoryData);

        List<Product> actualProducts = productRepository.findAll();

        assertEquals(repositoryData, actualProducts);
    }

    static Stream<List<Product>> findAllTestData() {
        return Stream.of(
                List.of(),
                List.of(TestDataProduct.builder().build().buildProduct()),
                Arrays.asList(
                        TestDataProduct.builder().build().buildProduct(),
                        TestDataProduct.builder().build().buildProduct()
                )
        );
    }

    @Test
    void save() {
        Product expectedProduct = TestDataProduct.builder().build().buildProduct();
        when(productRepository.save(expectedProduct)).thenReturn(null);
        Product savedProduct = productRepository.save(expectedProduct);
        assertThat(savedProduct).isNull();
    }

    @Test
    void saveShouldReturnNull() {
        Product expectedProduct = TestDataProduct.builder().build().buildProduct();
        when(productRepository.save(expectedProduct)).thenReturn(null);
        Product savedProduct = productRepository.save(expectedProduct);
        assertThat(savedProduct).isNull();
    }

    @Test
    void saveShouldThrowExceptionIllegalArgumentException() {
        doThrow(IllegalArgumentException.class).when(productRepository).save(null);
        assertThrows(IllegalArgumentException.class, () -> productRepository.save(null));
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
        productRepository.delete(expected.getUuid());
    }

    @Test
    void deleteShouldWithName() {
        TestDataProduct expected = TestDataProduct.builder()
                .withName("test")
                .build();
        productRepository.delete(expected.getUuid());
    }
}
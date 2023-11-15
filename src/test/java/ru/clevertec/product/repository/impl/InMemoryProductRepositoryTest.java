package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.TestDataProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {
    @InjectMocks
    private InMemoryProductRepository productRepository;

    @Test
    void findById_nonExistingProduct_returnsOptionalEmpty() {
        UUID nonExistingUuid = UUID.fromString("b6e1d925-ebca-458e-b032-c3dd2b8f1671");
        Optional<Product> result = productRepository.findById(nonExistingUuid);
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_emptyRepository_returnsEmptyList() {
        List<Product> result = productRepository.findAll();
        assertEquals(0, result.size());
    }

    @Test
    void save_validProduct_returnsSavedProduct() {
        Product product = TestDataProduct.builder().build().buildProduct();
        Product savedProduct = productRepository.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    void delete_existingProduct_deletesProduct() {
        UUID uuid = UUID.fromString("b6e1d925-ebca-458e-b032-c3dd2b8f1671");
        Product product = TestDataProduct.builder().build().buildProduct();
        productRepository.save(product);
        productRepository.delete(uuid);
        Optional<Product> result = productRepository.findById(uuid);
        assertFalse(result.isPresent());
    }

    @ParameterizedTest
    @MethodSource("provideFindByIdScenarios")
    void findById_multipleScenarios(UUID inputUuid, Product expectedProduct) {
        if (expectedProduct != null) {
            productRepository.save(expectedProduct);
        }
        Optional<Product> result = productRepository.findById(inputUuid);
        if (expectedProduct != null) {
            assertThat(result).isPresent().contains(expectedProduct);
        } else {
            assertThat(result).isEmpty();
        }
    }

    private static Stream<Arguments> provideFindByIdScenarios() {
        UUID existingUuid = UUID.fromString("b6e1d925-ebca-458e-b032-c3dd2b8f1671");
        UUID nonExistingUuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        Product existingProduct = TestDataProduct.builder().build().buildProduct();

        return Stream.of(
                Arguments.of(existingUuid, existingProduct),
                Arguments.of(nonExistingUuid, null)
        );
    }
}
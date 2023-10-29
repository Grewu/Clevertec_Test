package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.TestDataProduct;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    void get() {
        UUID uuid = TestDataProduct.builder().build().getUuid();
        productService.get(uuid);
        verify(productRepository).findById(uuid);
    }

    @Test
    void getAllShouldInvokeRepositoryWithNull() {
        UUID uuid = TestDataProduct.builder().build().getUuid();
        Product productFromRepository = TestDataProduct.builder().build().buildProduct();
        ProductDto expected = TestDataProduct.builder().build().buildProductDTO();

        doReturn(expected)
                .when(productRepository).findById(uuid);
        when(productMapper.toInfoProductDto(productFromRepository));

        productService.getAll();
        verify(productRepository).findById(uuid);
        assertThat(productArgumentCaptor.getValue()).hasAllNullFieldsOrProperties();
    }

    @Test
    void createShouldInvokeRepositoryWithProductUUID() {
        Product productToSave = TestDataProduct.builder().
                withUuid(null)
                .build().buildProduct();
        Product expected = TestDataProduct.builder()
                .build().buildProduct();
        ProductDto productDto = TestDataProduct.builder().
                withUuid(null)
                .build().buildProductDTO();

        doReturn(expected)
                .when(productRepository).save(productToSave);
        when(productMapper.toProduct(productDto))
                .thenReturn(productToSave);

        productService.create(productDto);

        verify(productRepository).save(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue()).hasFieldOrPropertyWithValue(Product.Fields.uuid, null);
    }

    @Test
    void update() {
        UUID uuid = TestDataProduct.builder().build().getUuid();
        ProductDto productDto = TestDataProduct.builder().build().buildProductDTO();
        productService.update(uuid, productDto);
    }

    @Test
    void delete() {
        UUID uuid = TestDataProduct.builder().build().getUuid();
        productService.delete(uuid);
        verify(productRepository)
                .delete(uuid);
    }
}
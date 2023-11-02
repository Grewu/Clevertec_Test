package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.TestDataProduct;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
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
    void getShouldWithNameNull() {
        Product expected = TestDataProduct.builder()
                .withName(null)
                .build()
                .buildProduct();
        assertThrows(ProductNotFoundException.class, () -> {
            productService.get(expected.getUuid());
        });
    }

    @Test
    void getShouldWithoutUUID() {
        Product expected = TestDataProduct.builder().withUuid(null).build().buildProduct();
        assertThrows(ProductNotFoundException.class, () -> {
            productService.get(expected.getUuid());
        });
    }

    @Test
    void getShouldThrowExceptionIllegalArgumentException() {
        assertThrows(ProductNotFoundException.class, () -> productService.get(null));
    }

    @Test
    void getAll() {
        List<InfoProductDto> actual = Collections.singletonList(TestDataProduct.builder().build().buidInfoProductDto());
        InfoProductDto expected = TestDataProduct.builder().build().buidInfoProductDto();
        assertThat(actual).contains(expected);
    }


    @Test
    void createShouldInvokeRepositoryWithoutProductUUID() {
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
    void createShouldInvokeRepositoryWithoutProductNameIsNull() {
        Product productToSave = TestDataProduct.builder().
                withName(null)
                .build().buildProduct();
        Product expected = TestDataProduct.builder()
                .build().buildProduct();
        ProductDto productDto = TestDataProduct.builder().
                withName(null)
                .build().buildProductDTO();

        doReturn(expected)
                .when(productRepository).save(productToSave);
        when(productMapper.toProduct(productDto))
                .thenReturn(productToSave);

        productService.create(productDto);

        verify(productRepository).save(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue()).hasFieldOrPropertyWithValue(Product.Fields.name, null);
    }

    @Test
    void updateShouldWithUUID() {
        ProductDto expected = TestDataProduct.builder().build().buildProductDTO();
        productService.update(UUID.fromString("aa77f040-308f-4ee0-acf1-d86c310bb52f"), expected);
    }

    @Test
    void updateShouldWithUUIDNull() {
        ProductDto expected = TestDataProduct.builder().build().buildProductDTO();
        productService.update(null, expected);
    }

    @Test
    void deleteProductEqualsWithoutUUID() {
        Product product = TestDataProduct.builder().withUuid(null).build().buildProduct();
        doNothing().when(productRepository).delete(product.getUuid());
        productService.delete(product.getUuid());
        verify(productRepository).delete(product.getUuid());
    }

    @Test
    void deleteShouldWithPriceOne() {
        Product product = TestDataProduct.builder()
                .withPrice(BigDecimal.ONE)
                .build()
                .buildProduct();
        productService.delete(product.getUuid());
        verify(productRepository).delete(product.getUuid());
    }

    @Test
    void getInfoProductDto() {
        InfoProductDto expected = TestDataProduct.builder().build().buidInfoProductDto();
        doReturn(Optional.of(TestDataProduct.builder().build().buildProduct())).
                when(productRepository).findById(TestDataProduct.builder().build().getUuid());
        InfoProductDto actual = productService.get(TestDataProduct.builder().build().getUuid());
        Assertions.assertEquals(expected, actual);
    }
}
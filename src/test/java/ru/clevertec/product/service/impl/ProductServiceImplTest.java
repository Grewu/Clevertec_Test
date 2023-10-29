package ru.clevertec.product.service.impl;

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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    void getShouldWithName() {
        Product expected = TestDataProduct.builder()
                .withName("expected")
                .build()
                .buildProduct();
        InfoProductDto actual = productService.get(expected.getUuid());
        assertThat(actual)
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.uuid,expected.getUuid())
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.name,expected.getName())
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.description,expected.getDescription())
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.price,expected.getPrice());
    }
    @Test
    void getShouldWithoutUUID() {
        Product expected = TestDataProduct.builder()
                .withUuid(null)
                .build()
                .buildProduct();
        InfoProductDto actual = productService.get(expected.getUuid());
        assertThat(actual)
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.uuid,expected.getUuid())
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.name,expected.getName())
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.description,expected.getDescription())
                .hasFieldOrPropertyWithValue(InfoProductDto.Fields.price,expected.getPrice());
    }
    @Test
    void getShouldThrowExceptionIllegalArgumentException() {
        when(productService.get(null))
                .thenThrow(ProductNotFoundException.class);
    }
    @Test
    void getAll() {
        InfoProductDto expected =  TestDataProduct.builder().build().buidInfoProductDto();
        List<InfoProductDto> actual = productService.getAll();
        Mockito.when(actual.get(any())).thenReturn(expected);
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
        assertThat(productArgumentCaptor.getValue()).hasFieldOrPropertyWithValue(Product.Fields.uuid, null);
    }

    @Test
    void updateShouldWithUUID() {
        ProductDto expected = TestDataProduct.builder().build().buildProductDTO();
        productService.update(UUID.fromString("aa77f040-308f-4ee0-acf1-d86c310bb52f")
                              ,expected);
    }

    @Test
    void updateShouldWithUUIDNull() {
        ProductDto expected = TestDataProduct.builder().build().buildProductDTO();
        productService.update(null,expected);
    }

    @Test
    void deleteProductEqualsWithoutUUID() {
        TestDataProduct expected = TestDataProduct.builder()
                .withUuid(null)
                .build();
        verify(productService).delete(expected.getUuid());
    }
    @Test
    void deleteShouldWithPrice() {
        TestDataProduct expected = TestDataProduct.builder()
                .withPrice(BigDecimal.ONE)
                .build();
        verify(productRepository).delete(expected.getUuid());
    }
}
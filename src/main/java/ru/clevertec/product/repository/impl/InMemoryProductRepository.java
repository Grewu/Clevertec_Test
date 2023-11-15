package ru.clevertec.product.repository.impl;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<UUID, Product> productMap = new HashMap<>();

    @Override
    public Optional<Product> findById(UUID uuid) {
        return productMap.entrySet().stream()
                .filter(uuidProductEntry -> uuidProductEntry.getValue().getUuid().equals(uuid))
                .findFirst()
                .map(Map.Entry::getValue);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    @Override
    public Product save(Product product) {
        if (product.getUuid() == null) {
            product.setUuid(UUID.randomUUID());
        }
        productMap.put(product.getUuid(), product);

        return product;
    }

    @Override
    public void delete(UUID uuid) {
        productMap.remove(uuid);
    }
}

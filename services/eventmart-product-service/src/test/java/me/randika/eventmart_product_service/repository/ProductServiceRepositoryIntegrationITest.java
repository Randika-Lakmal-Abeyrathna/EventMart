package me.randika.eventmart_product_service.repository;

import me.randika.eventmart_product_service.domain.entity.Product;
import me.randika.eventmart_product_service.domain.entity.ProductCategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ExtendWith(SpringExtension.class)
class ProductServiceRepositoryIntegrationITest {

    @Container
    static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("eventmart_test")
                    .withUsername("test")
                    .withPassword("test");

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setup() {
        productImageRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();

        ProductCategory category = new ProductCategory();
        category.setName("Electronics");

        productCategoryRepository.save(category);

        Product product = Product.builder()
                .productCode("sku-001")
                .name("Laptop")
                .description("Gaming Laptop")
                .brand("ASUS")
                .price(new BigDecimal("2499.99"))
                .categoryId(category.getId())
                .build();

        productRepository.save(product);
    }

    @Test
    void testCategoryRepositoryFindByName() {
        Optional<ProductCategory> category =
                productCategoryRepository.findByName("Electronics");

        assertThat(category).isPresent();
    }

    @Test
    void testProductRepositoryFindByCategoryId() {
        Optional<ProductCategory> category =
                productCategoryRepository.findByName("Electronics");
        assertThat(category).isPresent();
        var products = productRepository.findByCategoryId(category.get().getId());

        assertThat(products).isNotEmpty();
    }
}

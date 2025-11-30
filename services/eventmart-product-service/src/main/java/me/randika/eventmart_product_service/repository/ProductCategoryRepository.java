package me.randika.eventmart_product_service.repository;

import me.randika.eventmart_product_service.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository  extends JpaRepository<ProductCategory, UUID> {

    Optional<ProductCategory> findByName(String name);

    List<ProductCategory> findProductCategoriesById(UUID id);
}

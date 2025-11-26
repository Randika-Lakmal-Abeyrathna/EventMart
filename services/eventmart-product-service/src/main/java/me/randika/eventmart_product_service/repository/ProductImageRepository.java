package me.randika.eventmart_product_service.repository;

import me.randika.eventmart_product_service.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductImageRepository  extends JpaRepository<ProductImage, UUID> {

    List<ProductImage> findByProductId(UUID productId);
}

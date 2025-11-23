package me.randika.eventmart_product_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @UuidGenerator
    @GeneratedValue
    private String id;
    @Column(nullable = false,unique = true)
    private String name;
    private String description;
    private String brand;
    @Column(name = "category_id")
    private String categoryId;
    @Column(nullable=false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private ProductStatus status =  ProductStatus.ACTIVE;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}

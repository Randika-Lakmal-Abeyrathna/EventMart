package me.randika.eventmart_product_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_image")
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @UuidGenerator
    @GeneratedValue
    private String id;
    @Column(name = "product_id",nullable = false)
    private String productId;
    @Column(name = "image_url",nullable = false)
    private String imageUrl;
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary  = false;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}

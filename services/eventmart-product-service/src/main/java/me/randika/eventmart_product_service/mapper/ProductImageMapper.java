package me.randika.eventmart_product_service.mapper;

import me.randika.eventmart_product_service.domain.dto.request.ProductImageRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductImageResponse;
import me.randika.eventmart_product_service.domain.entity.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductImage toEntity(ProductImageRequest productImageRequest);

    ProductImageResponse toResponse(ProductImage productImage);
}

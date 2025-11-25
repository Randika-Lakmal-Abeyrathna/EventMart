package me.randika.eventmart_product_service.mapper;

import me.randika.eventmart_product_service.domain.dto.request.ProductRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductResponse;
import me.randika.eventmart_product_service.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class})
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", expression = "java(me.randika.eventmart_product_service.domain.entity.ProductStatus.valueOf(request.status()))")
    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product product);
}

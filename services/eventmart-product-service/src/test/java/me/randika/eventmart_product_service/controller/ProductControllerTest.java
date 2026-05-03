package me.randika.eventmart_product_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.randika.eventmart_product_service.domain.dto.request.ProductRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductResponse;
import me.randika.eventmart_product_service.exception.DuplicateProductCodeException;
import me.randika.eventmart_product_service.config.SecurityConfig;
import me.randika.eventmart_product_service.exception.GlobalExceptionHandler;
import me.randika.eventmart_product_service.exception.ProductCategoryNotFoundException;
import me.randika.eventmart_product_service.exception.ProductNotFoundException;
import me.randika.eventmart_product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(ProductController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
@ActiveProfiles("test")
@WithMockUser
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testCreateProduct() throws Exception {
        ProductRequest request = new ProductRequest("P001", "Product A", "Test dis",
                UUID.randomUUID(),new BigDecimal("10.0"),"test brand","ACTIVE");


        ProductResponse response = new ProductResponse(UUID.randomUUID(), "P001", "Product A",
                "Test dis","test brand",new BigDecimal("10.0"),"ACTIVE",UUID.randomUUID(),
                LocalDateTime.now(),LocalDateTime.now(),null
        );

        Mockito.when(productService.createProduct(any(ProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productCode").value("P001"))
                .andExpect(jsonPath("$.name").value("Product A"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        UUID id = UUID.randomUUID();
        ProductRequest request = new ProductRequest("P001", "Updated A", "Test dis",
                UUID.randomUUID(),new BigDecimal("20.0"),"test brand","ACTIVE");


        ProductResponse response = new ProductResponse(id, "P001", "Updated A", "Test dis",
                "test brand",new BigDecimal("20.0"),"ACTIVE",UUID.randomUUID(),
                LocalDateTime.now(),LocalDateTime.now(),null
        );

        Mockito.when(productService.updateProduct(eq(id), any(ProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/products/id/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated A"))
                .andExpect(jsonPath("$.price").value(20.0));
    }


    @Test
    void testGetProductById() throws Exception {
        UUID id = UUID.randomUUID();

        ProductResponse response = new ProductResponse(id, "P001", "Product A",
                "Test dis","test brand",new BigDecimal("20.0"),"ACTIVE",UUID.randomUUID(),
                LocalDateTime.now(),LocalDateTime.now(),null
        );

        Mockito.when(productService.getProductById(id))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/products/id/" + id))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.productCode").value("P001"))
                .andExpect((ResultMatcher) jsonPath("$.name").value("Product A"));
    }


    @Test
    void testGetAllProducts() throws Exception {
        ProductResponse product =new ProductResponse(UUID.randomUUID(), "P001", "Product A",
                "Test dis","test brand",new BigDecimal("10.0"),"ACTIVE",UUID.randomUUID(),
                LocalDateTime.now(),LocalDateTime.now(),null
        );
        Page<ProductResponse> page = new PageImpl<>(List.of(product), PageRequest.of(0, 10), 1);

        Mockito.when(productService.productList(any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/products?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.content[0].productCode").value("P001"));
    }


    @Test
    void testDeleteProduct() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(productService).deleteProductById(id);

        mockMvc.perform(delete("/api/v1/products/id/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(productService.getProductById(id))
                .thenThrow(new ProductNotFoundException("Product not found: " + id));

        mockMvc.perform(get("/api/v1/products/id/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PRODUCT_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Product not found: " + id));
    }

    @Test
    void testCreateProduct_DuplicateCode() throws Exception {
        ProductRequest request = new ProductRequest(
                "P001", "Product A", "Test dis", UUID.randomUUID(),
                new BigDecimal("10.0"), "test brand", "ACTIVE"
        );

        Mockito.when(productService.createProduct(any()))
                .thenThrow(new DuplicateProductCodeException("Product Code already exists: P001"));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATE_PRODUCT_CODE"))
                .andExpect(jsonPath("$.message").value("Product Code already exists: P001"));
    }

    @Test
    void testCreateProduct_CategoryNotFound() throws Exception {
        ProductRequest request = new ProductRequest(
                "P002", "Product B", "Test B", UUID.randomUUID(),
                new BigDecimal("15.0"), "brandB", "ACTIVE"
        );

        Mockito.when(productService.createProduct(any()))
                .thenThrow(new ProductCategoryNotFoundException("Category not found"));

        mockMvc.perform(post("/api/v1/products")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PRODUCT_CATEGORY_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Category not found"));
    }

    @Test
    void testUnhandledException_InternalServerError() throws Exception {
        Mockito.when(productService.productList(any()))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.error").value("Internal Server Error"));
    }


}
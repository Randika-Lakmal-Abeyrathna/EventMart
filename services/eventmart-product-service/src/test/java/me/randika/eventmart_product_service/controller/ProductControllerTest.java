package me.randika.eventmart_product_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.randika.eventmart_product_service.domain.dto.request.ProductRequest;
import me.randika.eventmart_product_service.domain.dto.response.ProductResponse;
import me.randika.eventmart_product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
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
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

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

        mockMvc.perform(put("/api/v1/products/" + id)
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

        mockMvc.perform(get("/api/v1/products/" + id))
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

        mockMvc.perform(delete("/api/v1/products/" + id))
                .andExpect(status().isNoContent());
    }


}
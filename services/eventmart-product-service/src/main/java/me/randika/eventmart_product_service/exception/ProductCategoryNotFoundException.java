package me.randika.eventmart_product_service.exception;

public class ProductCategoryNotFoundException extends BaseException {
    public ProductCategoryNotFoundException(String message) {
        super(message,"PRODUCT_CATEGORY_NOT_FOUND");
    }
}

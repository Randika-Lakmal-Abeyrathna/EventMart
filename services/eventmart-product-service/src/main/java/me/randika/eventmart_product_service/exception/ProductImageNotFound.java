package me.randika.eventmart_product_service.exception;

public class ProductImageNotFound extends BaseException {
    public ProductImageNotFound(String message) {
        super(message,"PRODUCT_IMAGE_NOT_FOUND");
    }
}

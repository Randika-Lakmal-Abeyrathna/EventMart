package me.randika.eventmart_product_service.exception;

public class DuplicateProductCategoryException extends BaseException {
    public DuplicateProductCategoryException(String message) {
        super(message,"DUPLICATE_PRODUCT_CATEGORY");
    }
}

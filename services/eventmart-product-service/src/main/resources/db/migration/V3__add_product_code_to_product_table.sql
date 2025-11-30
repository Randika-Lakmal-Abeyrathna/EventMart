ALTER TABLE product
    ADD product_code VARCHAR(255);

ALTER TABLE product
    ALTER COLUMN product_code SET NOT NULL;

ALTER TABLE product
    ADD CONSTRAINT uc_product_product_code UNIQUE (product_code);
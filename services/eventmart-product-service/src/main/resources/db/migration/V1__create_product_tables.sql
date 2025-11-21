-- Enable pgcrypto extension for UUID generation
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ==========================================
-- PRODUCT CATEGORY TABLE
-- ==========================================
CREATE TABLE product_category (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  name VARCHAR(255) NOT NULL,
                                  description TEXT,
                                  parent_category_id UUID,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                  CONSTRAINT fk_parent_category
                                      FOREIGN KEY (parent_category_id)
                                          REFERENCES product_category(id)
);

-- ==========================================
-- PRODUCT TABLE
-- ==========================================
CREATE TABLE product (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         category_id UUID NOT NULL,
                         price NUMERIC(12, 2) NOT NULL,
                         status VARCHAR(50) DEFAULT 'ACTIVE',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT fk_product_category
                             FOREIGN KEY (category_id)
                                 REFERENCES product_category(id)
);

-- ==========================================
-- PRODUCT IMAGES TABLE
-- ==========================================
CREATE TABLE product_image (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               product_id UUID NOT NULL,
                               image_url TEXT NOT NULL,
                               is_primary BOOLEAN DEFAULT FALSE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                               CONSTRAINT fk_image_product
                                   FOREIGN KEY (product_id)
                                       REFERENCES product(id)
);

-- Optional indexes for faster queries
CREATE INDEX idx_product_category_id ON product(category_id);
CREATE INDEX idx_product_image_product_id ON product_image(product_id);

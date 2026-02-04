CREATE TABLE suppliers (
                           id BIGSERIAL PRIMARY KEY,
                           code VARCHAR(20) NOT NULL UNIQUE,
                           name VARCHAR(20) NOT NULL,
                           surname VARCHAR(30) NOT NULL,
                           phone VARCHAR(12) NOT NULL,
                           email VARCHAR(30),
                           created_at TIMESTAMP,
                           updated_at TIMESTAMP
);

CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(20) NOT NULL,
                          type VARCHAR(30) NOT NULL,
                          price NUMERIC(10,2) NOT NULL,
                          supplier_id BIGINT NOT NULL,

                          CONSTRAINT fk_product_supplier
                              FOREIGN KEY (supplier_id)
                                  REFERENCES suppliers(id)
);

CREATE TABLE delivery (
                          id BIGSERIAL PRIMARY KEY,
                          delivery_date_time TIMESTAMP NOT NULL,
                          supplier_id BIGINT NOT NULL,
                          status VARCHAR(20),
                          total_amount NUMERIC(12,2),
                          comment VARCHAR(255),

                          CONSTRAINT fk_delivery_supplier
                              FOREIGN KEY (supplier_id)
                                  REFERENCES suppliers(id)
);

CREATE TABLE delivery_items (
                                id BIGSERIAL PRIMARY KEY,
                                product_id BIGINT NOT NULL,
                                delivery_id BIGINT NOT NULL,
                                weight NUMERIC(10,2) NOT NULL,
                                price NUMERIC(10,2) NOT NULL,

                                CONSTRAINT fk_item_product
                                    FOREIGN KEY (product_id)
                                        REFERENCES products(id),

                                CONSTRAINT fk_item_delivery
                                    FOREIGN KEY (delivery_id)
                                        REFERENCES delivery(id)
);

CREATE TABLE orders
(
    id                       UUID PRIMARY KEY,
    status                   VARCHAR(20)    NOT NULL,
    total_amount             NUMERIC(19, 2) NOT NULL,
    currency                 VARCHAR(3)     NOT NULL,
    payment_method_reference VARCHAR(255)   NOT NULL,
    created_at               TIMESTAMPTZ    NOT NULL,
    updated_at               TIMESTAMPTZ    NOT NULL
);

CREATE TABLE order_lines
(
    order_id   UUID         NOT NULL,
    line_index INTEGER      NOT NULL,
    sku        VARCHAR(100) NOT NULL,
    quantity   INTEGER      NOT NULL,

    CONSTRAINT pk_order_lines
        PRIMARY KEY (order_id, line_index),

    CONSTRAINT fk_order_lines_order
        FOREIGN KEY (order_id)
        REFERENCES orders (id)
        ON DELETE CASCADE,

    CONSTRAINT chk_order_lines_quantity
        CHECK (quantity > 0)
);

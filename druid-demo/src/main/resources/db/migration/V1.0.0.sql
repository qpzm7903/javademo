CREATE TABLE mall_address
(
    id       VARCHAR(32),
    street   VARCHAR(100),
    city     VARCHAR(100),
    state    VARCHAR(100),
    zip_code VARCHAR(100),
    country  VARCHAR(100),
    PRIMARY KEY (id)
);
CREATE TABLE mall_category
(
    id          VARCHAR(32),
    NAME        VARCHAR(100),
    description VARCHAR(100),
    PRIMARY KEY (id)
);
CREATE TABLE mall_member
(
    id      VARCHAR(32),
    user_id VARCHAR(32),
    points  INT,
    PRIMARY KEY (id)
);
CREATE TABLE mall_order
(
    id              VARCHAR(32),
    order_number    VARCHAR(32),
    order_date      VARCHAR(10),
    order_status    INT,
    shippingAddress INT,
    totalPrice      TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE mall_order_item
(
    id              VARCHAR(32),
    product_id      VARCHAR(32),
    quantity        INT,
    unit_price      DECIMAL,
    total_price     DECIMAL,
    shopping_car_id VARCHAR(32),
    PRIMARY KEY (id)
);
CREATE TABLE mall_payment
(
    id             VARCHAR(32),
    payment_date   TIMESTAMP,
    amount         INT,
    payment_status VARCHAR(10),
    PRIMARY KEY (id)
);
CREATE TABLE mall_product
(
    id                VARCHAR(32),
    NAME              VARCHAR(32),
    description       VARCHAR(2000),
    price             DECIMAL,
    quantity_in_stock INT,
    PRIMARY KEY (id)
);
CREATE TABLE mall_shopping_car
(
    id CHAR(32),
    PRIMARY KEY (id)
);
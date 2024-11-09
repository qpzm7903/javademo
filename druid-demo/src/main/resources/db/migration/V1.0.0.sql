CREATE TABLE mall_address (
                              id CHAR ( 32 ),
                              street VARCHAR ( 100 ),
                              city VARCHAR ( 100 ),
                              state VARCHAR ( 100 ),
                              zip_code VARCHAR ( 100 ),
                              country VARCHAR ( 100 ),
                              PRIMARY KEY ( id )
);
CREATE TABLE mall_category ( id CHAR ( 32 ), NAME VARCHAR ( 100 ), description VARCHAR ( 100 ), PRIMARY KEY ( id ) );
CREATE TABLE mall_member ( id CHAR ( 32 ), user_id CHAR ( 32 ), points INT, PRIMARY KEY ( id ) );
CREATE TABLE mall_order (
                            id CHAR ( 32 ),
                            order_number CHAR ( 32 ),
                            order_date CHAR ( 10 ),
                            order_status INT,
                            shippingAddress INT,
                            totalPrice TIMESTAMP,
                            PRIMARY KEY ( id ));
CREATE TABLE mall_order_item (
                                 id CHAR ( 32 ),
                                 product_id CHAR ( 32 ),
                                 quantity INT,
                                 unit_price DECIMAL,
                                 total_price DECIMAL,
                                 shopping_car_id CHAR ( 32 ),
                                 PRIMARY KEY ( id )
);
CREATE TABLE mall_payment (
                              id CHAR ( 32 ),
                              payment_date TIMESTAMP,
                              amount INT,
                              payment_status CHAR ( 10 ),
                              PRIMARY KEY ( id ));
CREATE TABLE mall_product ( id CHAR ( 32 ), NAME CHAR ( 32 ), description VARCHAR ( 2000 ), price DECIMAL, quantity_in_stock INT, PRIMARY KEY ( id ) );
CREATE TABLE mall_shopping_car ( id CHAR ( 32 ), PRIMARY KEY ( id ) );
insert into category (id, created_date, last_modified_date, description, name) 
values (1, current_timestamp, current_timestamp, 'Phones & Smartphones category', 'Phone');

insert into product (id, created_date, last_modified_date, description, name, price, sales_counter, status, category_id)  
values (1, current_timestamp, current_timestamp, 'The latest powerful iPhone from Apple', 'iPhone 11 Pro', 999.00, 0,
        'AVAILABLE', 1),
       (2, current_timestamp, current_timestamp, 'The most powerful iPhone from Apple', 'iPhone XS', 759.00, 0,
        'AVAILABLE', 1);

insert into review (id, created_date, last_modified_date, description, rating, title) 
values (1, current_timestamp, current_timestamp, 'I like the product but I found that it''s not perfect', 4,
        'Good but not perfect'),
       (2, current_timestamp, current_timestamp, 'Wonderful product', 5, 'Excellent'),
       (3, current_timestamp, current_timestamp, 'I like the product but not the price', 3, 'Good but very expensive');

insert into product_review (product_id, review_id)
values (1, 1),
       (1, 2),
       (2, 3);

insert into customer (id, created_date, last_modified_date, email, enabled, first_name, last_name, telephone, 
    home_address_1, home_address_2, home_city, home_country, home_postcode, 
    office_address_1, office_address_2, office_city, office_country, office_postcode)
values (1, current_timestamp, current_timestamp, 'jason.bourne@mail.hello', TRUE, 'Jason', 'Bourne', '010203040506', 'Rue Vaugirard', NULL, 'Paris', 'FR', '75015', 'Rue Maupertuis', NULL, 'Le Mans', 'FR', '72100'),
       (2, current_timestamp, current_timestamp, 'homer.simpson@mail.hello', TRUE, 'Homer', 'Simpson', '060504030201', 'Rue Maupertuis', NULL, 'Le Mans', 'FR', '72100', 'Rue Maupertuis', NULL, 'Le Mans', 'FR', '72100'),
       (3, current_timestamp, current_timestamp, '1', TRUE, '1', '1', '1', 'Rue 1', NULL, 'Le 1', 'FR', '1', 'Rue 1', NULL, 'Le 1', '1', '1');

insert into cart (id, created_date, last_modified_date, status, customer_id)
values (1, current_timestamp, current_timestamp, 'NEW', 1),
       (2, current_timestamp, current_timestamp, 'NEW', 2);

insert into orders (id, created_date, last_modified_date, address_1, address_2, city, country, postcode, shipped, status, total_price, cart_id)
values (1, current_timestamp, current_timestamp, 'Rue Vaugirard', NULL, 'Paris', 'FR', '75015', current_timestamp, 'HOLD',
        999.00, 1),
       (2, current_timestamp, current_timestamp, 'Rue Maupertuis', NULL, 'Le Mans', 'FR', '72100', NULL, 'CREATION',
        759.00, 2);

insert into order_item (id, created_date, last_modified_date, quantity, order_id, product_id)
values (1, current_timestamp, current_timestamp, 1, 1, 1),
       (2, current_timestamp, current_timestamp, 1, 2, 2);
       
insert into payment (id, created_date, last_modified_date, paypal_payment_id, status, order_id)
values (1, current_timestamp, current_timestamp, 1234, 'ACCEPTED', 1);

alter sequence category_seq increment by 1;
select category_seq.nextval from dual;
alter sequence category_seq increment by 1;

alter sequence product_seq increment by 2;
select product_seq.nextval from dual;
alter sequence product_seq increment by 1;

alter sequence review_seq increment by 3;
select review_seq.nextval from dual;
alter sequence review_seq increment by 1;

alter sequence customer_seq increment by 3;
select customer_seq.nextval from dual;
alter sequence customer_seq increment by 1;

alter sequence cart_seq increment by 2;
select cart_seq.nextval from dual;
alter sequence cart_seq increment by 1;

alter sequence order_seq increment by 2;
select order_seq.nextval from dual;
alter sequence order_seq increment by 1;

alter sequence order_item_seq increment by 2;
select order_item_seq.nextval from dual;
alter sequence order_item_seq increment by 1;

alter sequence payment_seq increment by 1;
select payment_seq.nextval from dual;
alter sequence payment_seq increment by 1;

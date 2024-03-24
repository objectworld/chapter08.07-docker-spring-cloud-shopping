drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence;

drop table if exists cart cascade;
create table cart
(
    id                 bigint      not null,
    created_date       timestamp    not null,
    last_modified_date timestamp,
    status             varchar(255) not null,
    customer_id        int
);
alter table cart
    add constraint cart_pk primary key (id);
drop sequence if exists cart_seq;
create sequence cart_seq start with 1 increment by 1;

drop table if exists category cascade;
create table category
(
    id                 bigint      not null,
    created_date       timestamp    not null,
    last_modified_date timestamp,
    description        varchar(255) not null,
    name               varchar(255) not null
);
alter table category
    add constraint category_pk primary key (id);
drop sequence if exists category_seq;
create sequence category_seq start with 1 increment by 1;

drop table if exists customer cascade;
create table customer
(
    id                 bigint   not null,
    created_date       timestamp not null,
    last_modified_date timestamp,
    email              varchar(255),
    enabled            boolean   not null,
    first_name         varchar(255),
    last_name          varchar(255),
    telephone          varchar(255),
    home_address_1     varchar(255),
    home_address_2     varchar(255),
    home_city          varchar(255),
    home_country       varchar(2),
    home_postcode      varchar(10),
    office_address_1   varchar(255),
    office_address_2   varchar(255),
    office_city        varchar(255),
    office_country     varchar(2),
    office_postcode    varchar(10)
);
alter table customer
    add constraint customer_pk primary key (id);
drop sequence if exists customer_seq;
create sequence customer_seq start with 1 increment by 1;

drop table if exists order_item cascade;
create table order_item
(
    id                 bigint   not null,
    created_date       timestamp not null,
    last_modified_date timestamp,
    quantity           bigint   not null,
    order_id           int,
    product_id         int
);
alter table order_item
    add constraint order_item_pk primary key (id);
drop sequence if exists order_item_seq;
create sequence order_item_seq start with 1 increment by 1;

drop table if exists orders cascade;
create table orders
(
    id                 bigint        not null,
    created_date       timestamp      not null,
    last_modified_date timestamp,
    address_1          varchar(255),
    address_2          varchar(255),
    city               varchar(255),
    country            varchar(2),
    postcode           varchar(10),
    shipped            timestamp,
    status             varchar(255)   not null,
    total_price        decimal(10, 2) not null default 0,
    cart_id            int
);
alter table orders
    add constraint order_pk primary key (id);
drop sequence if exists order_seq;
create sequence order_seq start with 1 increment by 1;

drop table if exists payment cascade;
create table payment
(
    id                 bigint      not null,
    created_date       timestamp    not null,
    last_modified_date timestamp,
    paypal_payment_id  varchar(255),
    status             varchar(255) not null,
    order_id           int
);
alter table payment
    add constraint payment_pk primary key (id);
drop sequence if exists payment_seq;
create sequence payment_seq start with 1 increment by 1;

drop table if exists product cascade;
create table product
(
    id                 bigint        not null,
    created_date       timestamp      not null,
    last_modified_date timestamp,
    description        varchar(255)   not null,
    name               varchar(255)   not null,
    price              decimal(10, 2) not null,
    sales_counter      integer,
    status             varchar(255)   not null,
    category_id        int
);
alter table product
    add constraint product_pk primary key (id);
drop sequence if exists product_seq;
create sequence product_seq start with 1 increment by 1;

drop table if exists product_review cascade;
create table product_review
(
    product_id bigint not null,
    review_id bigint not null
);
alter table product_review
    add constraint product_review_pk primary key (product_id, review_id);

drop table if exists review cascade;
create table review
(
    id                 bigint      not null,
    created_date       timestamp    not null,
    last_modified_date timestamp,
    description        varchar(255) not null,
    rating             bigint      not null,
    title              varchar(255) not null
);
alter table review
    add constraint review_pk primary key (id);
drop sequence if exists review_seq;
create sequence review_seq start with 1 increment by 1;

alter table product_review
    add constraint product_review_uk unique (review_id);
alter table payment
    add constraint payment_uk unique (order_id);
alter table product_review
    add constraint product_review_fk1 foreign key (review_id) references review (id);
alter table cart
    add constraint cart_fk foreign key (customer_id) references customer (id);
alter table order_item
    add constraint order_item_fk1 foreign key (product_id) references product (id);
alter table payment
    add constraint payment_fk foreign key (order_id) references orders (id);
alter table orders
    add constraint order_fk1 foreign key (cart_id) references cart (id);
alter table order_item
    add constraint order_item_fk2 foreign key (order_id) references orders (id);
alter table product
    add constraint product_fk foreign key (category_id) references category (id);
alter table product_review
    add constraint product_review_fk2 foreign key (product_id) references product (id);
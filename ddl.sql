create table address
(
    id          bigint auto_increment,
    state_code  varchar(2)   null,
    city        varchar(255) null,
    first_line  varchar(255) null,
    second_line varchar(255) null,
    constraint id_UNIQUE
        unique (id)
);

alter table address
    add primary key (id);

create table category
(
    id   bigint auto_increment,
    name varchar(45) null,
    constraint id_UNIQUE
        unique (id)
);

alter table category
    add primary key (id);

create table customer
(
    id                bigint auto_increment,
    name              varchar(255)     null,
    registration_date date             null,
    active            bit default b'0' null,
    constraint id_UNIQUE
        unique (id)
);

alter table customer
    add primary key (id);

create table customer_category
(
    id           bigint auto_increment,
    category_tax float default 0 null,
    customer_id  bigint          null,
    category_id  bigint          null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_customer_category_category
        foreign key (category_id) references category (id),
    constraint fk_customer_category_customer
        foreign key (customer_id) references customer (id)
);

create index fk_customer_category_category_idx
    on customer_category (category_id);

create index fk_customer_category_customer_idx
    on customer_category (customer_id);

alter table customer_category
    add primary key (id);

create table item
(
    id          bigint auto_increment,
    upc         varchar(20) null,
    label       varchar(45) null,
    units       int         null,
    category_id bigint      null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_category_id
        foreign key (category_id) references category (id)
);

create index fk_category_id_idx
    on item (category_id);

alter table item
    add primary key (id);

create table location
(
    id                 bigint auto_increment,
    identifier         varchar(255)    null,
    type               varchar(45)     null,
    total_capacity     int             null,
    available_capacity int             null,
    address_id         bigint          null,
    rental_tax_rate    float default 0 null,
    customer_id        bigint          not null,
    constraint id_UNIQUE
        unique (id),
    constraint customer_id_fk
        foreign key (customer_id) references customer (id)
            on update cascade on delete cascade,
    constraint fk_location_address
        foreign key (address_id) references address (id)
);

create index fk_locations_addresses_idx
    on location (address_id);

alter table location
    add primary key (id);

create table location_item
(
    id          bigint auto_increment,
    location_id bigint not null,
    item_id     bigint not null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_location_item_item
        foreign key (item_id) references item (id),
    constraint fk_location_item_location
        foreign key (location_id) references location (id)
);

create index fk_location_item_location_idx
    on location_item (location_id);

alter table location_item
    add primary key (id);

create table role
(
    id   bigint auto_increment,
    role varchar(30) not null,
    constraint id_UNIQUE
        unique (id),
    constraint role_UNIQUE
        unique (role)
);

alter table role
    add primary key (id);

create table state_tax
(
    id         bigint auto_increment,
    state_code varchar(2) null,
    tax        float      null,
    constraint id_UNIQUE
        unique (id),
    constraint state_code_UNIQUE
        unique (state_code)
);

alter table state_tax
    add primary key (id);

create table supplier
(
    id         bigint auto_increment,
    name       varchar(255) null,
    identifier varchar(255) null,
    constraint identifier_UNIQUE
        unique (id)
);

alter table supplier
    add primary key (id);

create table supplier_warehouse
(
    id           bigint auto_increment,
    supplier_id  bigint not null,
    warehouse_id bigint not null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_supplier_warehouse_supplier
        foreign key (supplier_id) references supplier (id),
    constraint fk_supplier_warehouse_warehouse
        foreign key (warehouse_id) references location (id)
);

create index fk_supplier_warehouse_supplier_idx
    on supplier_warehouse (supplier_id);

create index fk_supplier_warehouse_warehouse_idx
    on supplier_warehouse (warehouse_id);

alter table supplier_warehouse
    add primary key (id);

create table user
(
    id          bigint auto_increment,
    name        varchar(255)     null,
    surname     varchar(255)     null,
    birthday    date             null,
    email       varchar(255)     not null,
    login       varchar(255)     null,
    password    varchar(255)     not null,
    active      bit default b'0' null,
    role_id     bigint           null,
    address_id  bigint           null,
    location_id bigint           null,
    customer_id bigint           null,
    constraint email_UNIQUE
        unique (email),
    constraint id_UNIQUE
        unique (id),
    constraint fk_customer_id
        foreign key (customer_id) references customer (id)
            on update cascade on delete cascade,
    constraint fk_user_address
        foreign key (address_id) references address (id),
    constraint fk_user_location
        foreign key (location_id) references location (id),
    constraint fk_user_role
        foreign key (role_id) references role (id)
);

create index fk_user_address_idx
    on user (address_id);

create index fk_user_location_idx
    on user (location_id);

alter table user
    add primary key (id);

create table application
(
    id                   bigint auto_increment,
    application_number   varchar(45) null,
    reg_date_time        datetime    null,
    last_upd_date_time   datetime    null,
    status               varchar(45) null,
    items_total          bigint      null,
    units_total          bigint      null,
    source_location      bigint      null,
    destination_location bigint      null,
    created_by           bigint      null,
    last_upd_by          bigint      null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_application_created_by
        foreign key (created_by) references user (id),
    constraint fk_application_dest_location
        foreign key (destination_location) references location (id),
    constraint fk_application_src_location
        foreign key (source_location) references location (id),
    constraint fk_application_upd_by
        foreign key (last_upd_by) references user (id)
);

create index fk_application_created_by_idx
    on application (created_by);

create index fk_application_dest_location_idx
    on application (destination_location);

create index fk_application_src_location_idx
    on application (source_location);

create index fk_application_upd_by_idx
    on application (last_upd_by);

alter table application
    add primary key (id);

create table application_item
(
    id             bigint auto_increment,
    amount         int    null,
    cost           float  null,
    application_id bigint null,
    item_id        bigint null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_application_item_application
        foreign key (application_id) references application (id),
    constraint fk_application_item_item
        foreign key (item_id) references item (id)
);

create index fk_application_item_application_idx
    on application_item (application_id);

create index fk_items_idx
    on application_item (item_id);

alter table application_item
    add primary key (id);

create table bill
(
    id           bigint auto_increment,
    number       varchar(45) null,
    total_amount bigint      null,
    total_units  bigint      null,
    date_time    datetime    null,
    location_id  bigint      null,
    shop_manager bigint      null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_bill_location
        foreign key (location_id) references location (id),
    constraint fk_bill_user
        foreign key (shop_manager) references user (id)
);

create index fk_bill_location_idx
    on bill (location_id);

create index fk_bills_users_idx
    on bill (shop_manager);

alter table bill
    add primary key (id);

create table bill_item
(
    id      bigint auto_increment,
    amount  int    null,
    price   float  null,
    bill_id bigint null,
    item_id bigint null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_bill_item_bill
        foreign key (bill_id) references bill (id),
    constraint fk_bill_item_item
        foreign key (item_id) references item (id)
);

create index fk_bills_idx
    on bill_item (bill_id);

create index fk_items_idx
    on bill_item (item_id);

alter table bill_item
    add primary key (id);

create table write_off_act
(
    id           bigint auto_increment,
    identifier   varchar(255) null,
    date_time    datetime     null,
    total_amount bigint       null,
    total_sum    bigint       null,
    location_id  bigint       null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_write_off_act_location
        foreign key (location_id) references location (id)
);

create index fk_write_off_act_location_idx
    on write_off_act (location_id);

alter table write_off_act
    add primary key (id);

create table write_off_item
(
    id           bigint auto_increment,
    amount       int         null,
    reason       varchar(45) null,
    write_off_id bigint      not null,
    item_id      bigint      not null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_write_off_item_item
        foreign key (item_id) references item (id),
    constraint fk_write_off_item_write_off
        foreign key (write_off_id) references write_off_act (id)
);

create index fk_item_idx
    on write_off_item (item_id);

create index fk_write_off_item_write_off_idx
    on write_off_item (write_off_id);

alter table write_off_item
    add primary key (id);



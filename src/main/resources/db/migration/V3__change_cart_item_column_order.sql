alter table cart_items
    modify quantity int default 1 not null after cart_id;

alter table cart_items
    modify id bigint auto_increment first;
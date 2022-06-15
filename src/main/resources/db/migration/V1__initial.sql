create table `account`
(
    id              uuid        not null,
    document_number varchar(11) not null,
    `created_at`    timestamp   not null,
    status          varchar(1)  not null,
    primary key (id)
);

create table `operation_type`
(
    id          integer     not null,
    description varchar(55) not null,
    primary key (id)
);

create table transaction
(
    id           uuid      not null,
    account_id   uuid      not null,
    operation_id integer   not null,
    amount       decimal   not null,
    `event_date` timestamp not null,
    primary key (id),
    foreign key (account_id) references `account` (id),
    foreign key (operation_id) references `operation_type` (id)
);

insert into operation_type
values (1, 'COMPRA A VISTA');
insert into operation_type
values (2, 'COMPRA PARCELADA');
insert into operation_type
values (3, 'SAQUE');
insert into operation_type
values (4, 'PAGAMENTO');
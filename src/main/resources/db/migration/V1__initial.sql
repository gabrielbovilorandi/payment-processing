create table `account`
(
    id              uuid        not null,
    document_number varchar(11) not null,
    `created_at`    timestamp   not null,
    status          varchar(1)  not null,
    primary key (id)
);

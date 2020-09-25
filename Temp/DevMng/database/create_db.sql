create database business_develop_management;
use business_develop_management;
create table lbm_table_mapping (
    Lbm varchar(16) not null,
    TableName varchar(64) not null,
    unique key (lbm, table_name)
);


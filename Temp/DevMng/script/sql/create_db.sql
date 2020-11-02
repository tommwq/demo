create database business_develop_management;
use business_develop_management;
create table lbm_table_mapping (
    Lbm varchar(16) not null,
    TableName varchar(64) not null,
    unique key (lbm, table_name)
);

create table biz_log (
    `Time` time,
    IsRequest tinyint,
    ResultMessage varchar(1024),
    ResultCode varchar(1024),
    SoftName varchar(1024),
    Session varchar(1024),
    SysVer varchar(1024),
    UserCode varchar(1024),
    MAC varchar(1024),
    Path varchar(1024),
    HWID varchar(1024),
    Mobile varchar(1024),
    IP varchar(1024),
    NetAddr varchar(1024),
    DeviceVers varchar(1024),
    TraceID varchar(1024),
    ParentID varchar(1024),
    Service varchar(1024),
    Method varchar(1024),
    Raw text
);


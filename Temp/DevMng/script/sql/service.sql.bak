create table tc_service (
  id int primary key auto_increment,
  service int,
  mfunc int,
  sfunc int
);

create table url_service (
  id int primary key auto_increment,
  url varchar(128),
  class_name varchar(128),
  method_name varchar(128)
);

create table lbm_service (
  id int primary key auto_increment,
  lbm varchar(128),
  description varchar(128)
);


create table tc_lbm (
  tc_service_id int,
  lbm_id int
);


create table url_tc (
  tc_service_id int,
  url_id int
);

create table lbm_kbssacct (
  lbm_id int,
  table_name varchar(128)
);


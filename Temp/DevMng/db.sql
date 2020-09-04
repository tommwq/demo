-- 记录*biz*日志。
create table biz_log (
  `time` timestamp,
  method varchar(128),
  trace_id varchar(128),
  parent_id varchar(1024),
  soft_name varchar(1024),
  mobile varchar(1024),
  sys_ver varchar(1024),
  user_code varchar(1024),
  path varchar(1024),
  req varchar(4096),
  resp varchar(4096),
  unique key(method, trace_id)
);


create table debug_log (
  service int,
  mf int,
  cust_code varchar(1024),
  param varchar(1024),
  ns_biz varchar(1024),
  lbm varchar(1024),
  `time` timestamp,
  input varchar(1024),
  output varchar(1024),
  duration int,
  error varchar(4096)
);



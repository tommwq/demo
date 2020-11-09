-- gbk

CREATE TABLE interface_type (
    id integer primary key,
    description text,
    table_name text -- 接口定义表
);

CREATE TABLE interface_main(
    id integer primary key autoincrement,
    type integer
);

CREATE TABLE interface_lbm (
    id integer primary key,
    label text, -- "LBM编号，如L1102034"
    description text, -- "功能"
    function_name text -- "函数名"
);

CREATE TABLE interface_busiproc (
    id integer primary key,
    url text,
    description text,
    class_name text,
    method_name text
);

CREATE TABLE interface_tc (
    id integer primary key,
    service integer,
    mfunc integer,
    sfunc integer,
    description text
);

CREATE TABLE interface_call_relation (
    caller integer,
    callee integer
);

CREATE TABLE interface_comment (
    interface integer,
    comment text,
    time integer,
    user text
);

CREATE TABLE interface_parameter (
    id integer primary key autoincrement, -- "自动生成的编号"
    interface integer, -- "lbm_function编号"
    name text, -- "参数名"
    code text, -- "参数代码，如USER_CODE"
    data_type text, -- "参数类型，如CHAR(2)"
    is_required integer, -- "是否可选参数"
    is_input integer -- "是否入参"
);

CREATE TABLE data_dict (
    parameter integer, -- "interfaceparameter编号"
    value text, -- "参数值"
    description text -- "含义"
);

insert into interface_type values (1, "账户系统接口", "interface_lbm");
insert into interface_type values (2, "中台业务办理接口", "interface_busiproc");
insert into interface_type values (3, "TC接口", "interface_tc");

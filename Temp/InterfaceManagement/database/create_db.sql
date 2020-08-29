-- gbk

CREATE TABLE interface_type (
    id integer primary key,
    description text,
    table_name text -- �ӿڶ����
);

CREATE TABLE interface_main(
    id integer primary key autoincrement,
    type integer
);

CREATE TABLE interface_lbm (
    id integer primary key,
    label text, -- "LBM��ţ���L1102034"
    description text, -- "����"
    function_name text -- "������"
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
    id integer primary key autoincrement, -- "�Զ����ɵı��"
    interface integer, -- "lbm_function���"
    name text, -- "������"
    code text, -- "�������룬��USER_CODE"
    data_type text, -- "�������ͣ���CHAR(2)"
    is_required integer, -- "�Ƿ��ѡ����"
    is_input integer -- "�Ƿ����"
);

CREATE TABLE data_dict (
    parameter integer, -- "interfaceparameter���"
    value text, -- "����ֵ"
    description text -- "����"
);

insert into interface_type values (1, "�˻�ϵͳ�ӿ�", "interface_lbm");
insert into interface_type values (2, "��̨ҵ�����ӿ�", "interface_busiproc");
insert into interface_type values (3, "TC�ӿ�", "interface_tc");

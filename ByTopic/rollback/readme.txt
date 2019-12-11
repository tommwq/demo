以银行转账为例，考虑当新版本服务发生故障，并将错误数据写入数据库后，如何进行回滚。


第一版。转账时会向目标账户加入两笔款项。
第二版。修复故障。

数据库设计：
account: id, balance
transfer_history: from_id, to_id, amount



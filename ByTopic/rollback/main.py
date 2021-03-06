import csvdb
from account import *

def print_info(title, customer1, customer2):
    print("==============================")
    print(title)
    print("==============================")
    customer1.print()
    customer2.print()
    print()

def print_operation_log():
    [print(x) for x in account_system._operationhistory.select_many()]

db_path = './db'
deal_service = DealService2()

account_system = AccountSystem(db_path, deal_service)
deal_service.set_account_system(account_system)
alice = account_system.customer(1)
bob = account_system.customer(2)

# alice以每股1元的价格向bob购买100股abc股票。
# 转账中发生错误。
print_info("BEFORE", alice, bob)
alice.buy(bob, "abc", 1.00, 100)
print_info("AFTER", alice, bob)

print_operation_log()

# 开始恢复
# 锁定账户
# alice.lock()
# bob.lock()
# print_info("LOCKED", alice, bob)

# 执行逆操作撤销影响。

import csvdb
from account import *
from config import *


def print_info(title, customer1, customer2):
    print()
    print("==============================")
    print(title)
    print("==============================")
    customer1.print()
    customer2.print()




account_system = AccountSystem(db_path, deal_service)
deal_service.set_account_system(account_system)
alice = account_system.customer(1)
bob = account_system.customer(2)

# alice以每股1元的价格向bob购买100股abc股票。
print_info("BEFORE", alice, bob)
alice.buy(bob, "abc", 1.00, 100)
print_info("AFTER", alice, bob)

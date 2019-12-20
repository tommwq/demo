import csvdb
from account import *
from config import *

account_system = AccountSystem(db_path, deal_service)
deal_service.set_account_system(account_system)

def print_title(title):
    print()
    print("==============================")
    print(title)
    print("==============================")

print_title("BEFORE")
alice = account_system.customer(1)
bob = account_system.customer(2)
alice.print()
bob.print()

# alice以每股1元的价格向bob购买100股abc股票。
alice.buy(bob, "abc", 1.00, 100)

print_title("AFTER")
alice.print()
bob.print()

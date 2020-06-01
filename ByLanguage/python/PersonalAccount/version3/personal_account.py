
class Item(object):
    def __init__(self, is_debit, label, amount):
        self.is_debit = is_debit
        self.label = label
        self.amount = amount

class Account(object):
    def __init__(self):
        self.items = []
        self.expense = 0.0
        self.income = 0.0
        self.balance = 0.0

    def add_item(self, is_debit, label, amount):
        self.items.append(Item(is_debit, label, amount))

    def calculate(self):
        self.expense = sum([item.amount for item in self.items if item.is_debit])
        self.income = sum([item.amount for item in self.items if not item.is_debit])
        self.balance = self.income - self.expense


account = Account()
quit = False
while not quit:
    is_debit = input('input is_debit(enter "end" to end): ')
    if is_debit == 'end':
        break
    
    label = input('input label: ')
    amount = float(input('input amount: '))
    account.add_item(bool(is_debit), label, amount)

account.calculate()

print('''----------------
account
----------------''')

for item in account.items:
    amount = item.amount
    if item.is_debit:
        amount *= -1
    print('item: {} amount: {}'.format(item.label, amount))

    
print('''----------------
expense: {}
income:  {}
balance: {}'''.format(account.expense, account.income, account.balance))

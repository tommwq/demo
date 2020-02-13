
class Item:
    def __init__(self, commodity, expense):
        self.commodity = commodity
        self.expense = expense

quit = False

total_expense = 0.0
items = []

while not quit:
    commodity = input('input commodity(enter "end" to end): ')
    if commodity == 'end':
        break
    expense = float(input('input expense: '))
    items.append(Item(commodity, expense))
    total_expense += expense

print('-'*16)
print('bill')
print('-'*16)

for item in items:
    print('commodity: ', item.commodity, ' expense: ', item.expense)
    
print('total expense: ', total_expense)

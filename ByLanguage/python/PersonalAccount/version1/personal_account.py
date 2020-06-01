
print('expense')

quit = False
total_expense = 0.0

while not quit:
    commodity = input('input commodity(enter "end" to end): ')
    if commodity == 'end':
        break
    expense = float(input('input expense: '))
    total_expense += expense

print('total expense: ', total_expense)

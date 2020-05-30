
class Solve(object):
    def __init__(self, year, month, day):
        self._year = year;
        self._month = month;
        self._day = day;
        self._days_in_month = [
            31, 29, 31, 30,
            31, 30, 31, 31,
            30, 31, 30, 31
        ]

    def solve(self):
        # TODO ignore invalid date and leap year
        print(self.day_of_year())

    def day_of_year(self):
        days = 0
        for m in range(0, self._month - 1):
            days += self._days_in_month[m]

        days += self._day
        return days

    def is_valid_date(self):
        # TODO
        return True

    def is_leap_year(self):
        y = self._year
        if y % 400 == 0:
            return True
        elif y % 100 != 0 and y % 4 == 0:
            return True
        else:
            return False


if __name__ == '__main__':
    year = int(input())
    month = int(input())
    day = int(input())
    Solve(year, month, day).solve()

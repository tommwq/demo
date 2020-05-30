import random

class Dice(object):
    def __init__(self):
        self._face_value = 1

    def face_value(self):
        return self._face_value

    def roll(self):
        self._face_value = random.randint(1, 6)


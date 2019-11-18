from dicegame import DiceGame

class Player(object):
    def __init__(self):
        self._game = DiceGame()

    def start_game(self):
        self._game.start_game()
        

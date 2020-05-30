from dice import Dice

class DiceGame(object):
    win_number = 7
    
    def __init__(self):
        self._dice1 = Dice()
        self._dice2 = Dice()
        self._is_player_win = False

    def check_game_result(self):
        if self._dice1.face_value() + self._dice2.face_value() == self.win_number:
            self._is_player_win = True
        else:
            self._is_player_win = False

    def start_game(self):
        self._dice1.roll()
        self.show_face_value(self._dice1.face_value())
        self._dice2.roll()
        self.show_face_value(self._dice2.face_value())
        self.check_game_result()
        self.show_game_result()

    def show_face_value(self, face_value):
        print(face_value)

    def show_game_result(self):
        if self._is_player_win:
            print("win")
        else:
            print("loss")

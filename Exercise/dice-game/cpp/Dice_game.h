#pragma once

#include <iostream>
#include "Dice.h"

class Dice_game {
public:
        void start_game() {
                dice1.roll();
                show_face_value(dice1.get_face_value());
                
                dice2.roll();
                show_face_value(dice2.get_face_value());

                show_game_result();
        }

        void show_face_value(Face_value face_value) {
                std::cout << "Face value: " << static_cast<int>(face_value) << std::endl;
        }

        void show_game_result() {
                if (is_player_win()) {
                        std::cout << "win" << std::endl;
                } else {
                        std::cout << "loss" << std::endl;
                }
        }

        bool is_player_win() {
                return static_cast<int>(dice1.get_face_value()) + static_cast<int>(dice2.get_face_value()) == WIN_SUM;
        }
private:
        const static int WIN_SUM = 7;
        Dice dice1;
        Dice dice2;
};

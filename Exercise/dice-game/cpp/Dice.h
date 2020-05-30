#pragma once

#include <random>
#include "Face_value.h"

class Dice {
public:
        void roll() {
                std::random_device random_device;
                std::mt19937 generator(random_device());
                std::uniform_int_distribution<int> distribution(static_cast<int>(Face_value::one), static_cast<int>(Face_value::six));
                face_value = static_cast<Face_value>(distribution(generator));
        }

        Face_value get_face_value() {
                return face_value;
        }
private:
        Face_value face_value;
};

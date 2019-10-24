#pragma once

#include "face_value.h"

struct dice_t;
typedef struct dice_t dice_t;

void dice_initialize(dice_t *dice);
void dice_finalize(dice_t *dice);
void dice_roll(dice_t *dice);
face_value_t dice_face_value(dice_t *dice);

struct dice_t {
        face_value_t face_value;
};

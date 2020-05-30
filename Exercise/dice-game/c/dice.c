#include "common.h"
#include "dice.h"

void dice_initialize(dice_t *dice) {
        ASSERT_NOT_NULL(dice);

        dice->face_value = one;
}

void dice_finalize(dice_t *dice) {
        ASSERT_NOT_NULL(dice);
        // NULL
}

void dice_roll(dice_t *dice) {
        ASSERT_NOT_NULL(dice);

        dice->face_value = (face_value_t) (rand() % 6) + 1;
}

face_value_t dice_face_value(dice_t *dice) {
        ASSERT_NOT_NULL(dice);

        return dice->face_value;
}

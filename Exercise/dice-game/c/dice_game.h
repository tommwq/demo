#pragma once

#include "dice.h"

struct dice_game_t;
typedef struct dice_game_t dice_game_t;

void dice_game_initialize(dice_game_t *dice_game);
void dice_game_finalize(dice_game_t *dice_game);
void dice_game_start(dice_game_t *dice_game);

struct dice_game_t {
        dice_t *dice1;
        dice_t *dice2;
};

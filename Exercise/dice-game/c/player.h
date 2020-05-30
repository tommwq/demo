#pragma once

#include "dice_game.h"

struct player_t;
typedef struct player_t player_t;

void player_initialize(player_t *player);
void player_finalize(player_t *player);
void player_start_game(player_t *player);

struct player_t {
        dice_game_t *dice_game;
};

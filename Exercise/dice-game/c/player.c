#include "common.h"
#include "dice_game.h"
#include "player.h"

void player_initialize(player_t *player) {
        ASSERT_NOT_NULL(player);
        ASSERT_NULL(player->dice_game);

        player->dice_game = ALLOC(dice_game_t);
        ASSERT_NOT_NULL(player->dice_game);
        dice_game_initialize(player->dice_game);
}

void player_finalize(player_t *player) {
        ASSERT_NOT_NULL(player);
        ASSERT_NOT_NULL(player->dice_game);
        
        dice_game_finalize(player->dice_game);
        RELEASE(player->dice_game);
}

void player_start_game(player_t *player) {
        ASSERT_NOT_NULL(player);
        ASSERT_NOT_NULL(player->dice_game);

        dice_game_start(player->dice_game);
}

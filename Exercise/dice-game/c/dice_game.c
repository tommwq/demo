#include <stdio.h>
#include "common.h"
#include "dice.h"
#include "dice_game.h"

#define WIN_SUM 7

void show_face_value(face_value_t face_value);
void dice_game_show_game_result(dice_game_t *dice_game);
int dice_game_is_player_win(dice_game_t *dice_game);

void dice_game_initialize(dice_game_t *dice_game) {
        ASSERT_NOT_NULL(dice_game);
        ASSERT_NULL(dice_game->dice1);
        ASSERT_NULL(dice_game->dice2);
        DEBUG();
        dice_game->dice1 = ALLOC(dice_t);
        ASSERT_NOT_NULL(dice_game->dice1);
        dice_initialize(dice_game->dice1);

        dice_game->dice2 = ALLOC(dice_t);
        ASSERT_NOT_NULL(dice_game->dice2);
        dice_initialize(dice_game->dice2);
}

void dice_game_finalize(dice_game_t *dice_game) {
        ASSERT_NOT_NULL(dice_game);
        ASSERT_NOT_NULL(dice_game->dice1);
        ASSERT_NOT_NULL(dice_game->dice2);
        
        dice_finalize(dice_game->dice1);
        RELEASE(dice_game->dice1);
        ASSERT_NULL(dice_game->dice1);
        
        dice_finalize(dice_game->dice2);
        RELEASE(dice_game->dice2);
        ASSERT_NULL(dice_game->dice2);
}

void dice_game_start(dice_game_t *dice_game) {
        ASSERT_NOT_NULL(dice_game);
        ASSERT_NOT_NULL(dice_game->dice1);
        ASSERT_NOT_NULL(dice_game->dice2);

        dice_roll(dice_game->dice1);
        show_face_value(dice_face_value(dice_game->dice1));

        dice_roll(dice_game->dice2);
        show_face_value(dice_face_value(dice_game->dice2));

        dice_game_show_game_result(dice_game);
}

void show_face_value(face_value_t face_value) {
        printf("Face value: %d\n", (int) face_value);
}

void dice_game_show_game_result(dice_game_t *dice_game) {
        if (dice_game_is_player_win(dice_game)) {
                printf("win\n");
        } else {
                printf("loss\n");
        }
}

int dice_game_is_player_win(dice_game_t *dice_game) {
        ASSERT_NOT_NULL(dice_game);
        ASSERT_NOT_NULL(dice_game->dice1);
        ASSERT_NOT_NULL(dice_game->dice2);

        return dice_face_value(dice_game->dice1) + dice_face_value(dice_game->dice2) == WIN_SUM;
}

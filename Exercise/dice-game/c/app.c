#include <time.h>
#include "common.h"
#include "player.h"

int main(int argc, char **argv) {
        srand(time(NULL));
        
        player_t *player = ALLOC(player_t);
        ASSERT_NOT_NULL(player);
        player_initialize(player);

        player_start_game(player);

        player_finalize(player);
        RELEASE(player);
        ASSERT_NULL(player);
}

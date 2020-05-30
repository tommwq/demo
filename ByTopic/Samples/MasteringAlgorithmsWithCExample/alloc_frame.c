#include "list.h"

int alloc_frame(List *frames) {
    int frame_number, *data;

    if (List_size(frames) == 0) {
        return error;
    }

    if (List_remove_next(frames, NULL, (void **)&data) != ok) {
        return error;
    }

    frame_number = *data;
    free(data);

    return frame_number;
}

int free_frame(List *frames, int frame_number) {
    int *data;
    if ((data = (int*) malloc(sizeof(int))) == NULL) {
        return error;
    }

    *data = frame_number;
    return List_insert_next(frames, NULL, data);
}

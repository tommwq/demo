/*
 * ls.c
 * ls of my version.
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/dir.h>

int main(int argc, char *argv[]){
    DIR *dir;
    struct dirent *dirent;
    const char *dir_name;
    dir_name = argc < 2 ? "." : argv[1];
    if ((dir = opendir(dir_name)) == NULL){
        fprintf(stderr, "cannot open %s\n", dir_name);
        perror("");
        exit(EXIT_FAILURE);
    }
    while ((dirent = readdir(dir)) != NULL){
        fprintf(stdout, "%s\n", dirent->d_name);
    }
    closedir(dir);
    return EXIT_SUCCESS;
}

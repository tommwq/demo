/**
 * 创建一个1.44M软盘镜像，把文件内容写入镜像。
 * 参数： 文件名 创建的镜像名
 * 1.44MB软盘的容量是1.44 * 1000 * 1024字节。
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv){
  FILE *file;
  char *filename;
  char *outputFilename;
  int floppySize = 1024 * 1440;
  char *buffer;
  int length;

  if (argc < 2){
    printf("usage: %s <file> <output-file>\n", argv[0]);
    exit(EXIT_SUCCESS);
  }

  filename = argv[1];
  outputFilename = argv[2];

  if ((file = fopen(filename, "r")) == NULL){
    perror("cannot open input file");
    exit(EXIT_FAILURE);
  }

  if ((buffer = (char *)malloc(floppySize)) == NULL){
    perror("cannot allocate buffer");
    fclose(file);
    exit(EXIT_FAILURE);
  }
  memset(buffer, 0x0, floppySize);

  if ((length = fread(buffer, 1, floppySize, file)) == 0){
    perror("read file error");
    fclose(file);
    free(buffer);
    exit(EXIT_FAILURE);
  }
  fclose(file);

  if ((file = fopen(outputFilename, "w")) == NULL){
    perror("cannot open output file");
    free(buffer);
    exit(EXIT_FAILURE);
  }

  if (floppySize != fwrite(buffer, 1, floppySize, file)){
    perror("write file error");
    fclose(file);
    free(buffer);
    exit(EXIT_FAILURE);
  }

  fclose(file);
  free(buffer);

  return EXIT_SUCCESS;	
}
	

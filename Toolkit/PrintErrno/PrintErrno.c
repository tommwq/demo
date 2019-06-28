/*
 * File: PrintErrno.c
 * Description: View message associated with errno, 
 *              or list all errno and their messages, 
 *              an alternative of program perror.
 * Author: Wang Qian
 * Create Date: 2016-08-15
 * Last Modified Date: 2016-08-16
 */

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void PrintErrorMessage(int errnum);
void ShowHelp();
void ShowAllErrorMessages();
// 1: success, 0: failure.
int ConvertToInteger(const char *s, int *value);

int main(int argc, char **argv) {
  if (argc == 1) {
      ShowHelp();
      return 0;
  }

  if (argc == 2 && strncmp(argv[1], "-help", 5) == 0) {
      ShowHelp();
      return 0;
  }

  if (argc == 2 && strncmp(argv[1], "-list", 5) == 0) {
      ShowAllErrorMessages();
      return 0;
  }

  fprintf(stdout, "ERROR\tMESSAGE\n");
  fprintf(stdout, "-----------------------\n");

  int i;
  int value;
  for (i = 1; i < argc; ++i) {
      if (!ConvertToInteger(argv[i], &value)) {
          continue;
      }
      PrintErrorMessage (value);
  }

  fprintf(stdout, "-----------------------\n");
  return 0;
}

void PrintErrorMessage(int errnum) {
  const char *message = strerror(errnum);
  fprintf(stdout, "%d\t%s\n", errnum, message);
}

void ShowHelp() {
  const char *message = 
      "usage:\n"
      "\t./Errno [option]\n"
      "\t./Errno errnum1, errnum2, ...\n"
      "option:\n"
      "\t-help: show this message.\n"
      "\t-list: list all errors.\n";
  fprintf(stdout, "%s\n", message);
}

void ShowAllErrorMessages() {
  fprintf(stdout, "ERROR\tMESSAGE\n");
  fprintf(stdout, "-----------------------\n");

  int i;
  const char *message;
  for (i = 0; i < sys_nerr; i++) {
      message = sys_errlist[i];
      if (message != NULL) {
          fprintf(stdout, "%d\t%s\n", i, message);
      }
  }
  
  fprintf(stdout, "-----------------------\n");
}

int ConvertToInteger(const char *s, int *value) {
  int val = 0;
  int ret = 0;
  char buffer[256];
  val = atoi(s);
  int len = snprintf(buffer, 256, "%d", val);
  if (strlen(s) == len) {
      ret = 1;
  }
  if (value != NULL) {
      *value = val;
  }
  return ret;
}

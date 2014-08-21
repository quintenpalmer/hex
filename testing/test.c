#include <stdio.h>
#include <stdlib.h>

#include "loc.h"

FILE* getFP(char* filename);
int analyzeLine(char* line);
int readUntil(char* buffer, char* input, int index, char delim);

int main() {
    FILE* entireFile = getFP("data/createTest.txt");
    size_t len = 0;
    int working = 1;
    int numTested = 0;
    int numCorrect = 0;
    while (1) {
        char* buffer = NULL;
        int retLen = getline(&buffer, &len, entireFile);
        if (retLen == -1) {
            break;
        }
        int worked = analyzeLine(buffer);
        if (! (worked == -1)) {
            if (worked) {
                numCorrect += 1;
            }
            working = working && worked;
            numTested += 1;
        }
    }
    printf("Results:\n");
    printf("debug: 1\n");
    if (working) {
        printf("Success!\n");
    } else {
        printf("Failed!\n");
    }
    printf("%d/%d succeeded\n", numCorrect, numTested);
    return 0;
}

int analyzeLine(char* line) {
    int index = 0;
    char ret[100];
    if (line[0] == '#') {
        return -1;
    } else {
        char buffer[100];
        index = readUntil(buffer, line, index, ',');
        int x1 = atoi(buffer);
        index = readUntil(buffer, line, index, ',');
        int y1 = atoi(buffer);
        index = readUntil(buffer, line, index, ',');
        int z1 = atoi(buffer);
        index = readUntil(buffer, line, index, ',');
        int x2 = atoi(buffer);
        index = readUntil(buffer, line, index, ',');
        int y2 = atoi(buffer);
        index = readUntil(buffer, line, index, ',');
        int z2 = atoi(buffer);
        index = readUntil(buffer, line, index, '\n');
        int shouldWork = strcmp(buffer, "true") == 0;

        Loc loc1 = newLoc(x1, y1, z1);
        Loc loc2 = newLoc(x2, y2, z2);
        int equal = equalLoc(loc1, loc2);
        int answeredCorrectly = (equal && shouldWork) ||
                                ((!equal) && (!shouldWork));
        if (! answeredCorrectly) {
            printf("Should work: %s\n", buffer);
            printLoc(ret, loc1);
            printf("%s\n", ret);
            printLoc(ret, loc2);
            printf("%s\n", ret);
        }
        return answeredCorrectly;
    }
}

int readUntil(char* buffer, char* input, int index, char delim) {
    int bindex = 0;
    while (1) {
        if (input[index] == delim || input[index] == '\0') {
            buffer[bindex] = '\0';
            index += 1;
            //printf("%s\n", buffer);
            return index;
        }
        buffer[bindex] = input[index];
        bindex += 1;
        index += 1;
    }
}

FILE* getFP(char* filename) {
    FILE *fp;
    fp = fopen(filename , "rb" );

    if (!fp) {
        perror(filename),exit(1);
    }
    return fp;
}

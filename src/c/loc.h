#ifndef __hex_src_c__
#define __hex_src_c__

typedef struct {
    int x;
    int y;
    int z;
} Loc;

void printLoc(char* buf, Loc loc);

Loc newLoc(int x, int y, int z);

int equalLoc(Loc loc1, Loc loc2);

int distance(Loc l1, Loc l2);

Loc normal(Loc l1, Loc l2);

int getDelta(int rx, int ry, int rz, int ax, int ay, int az);

#endif //__hex_src_c__

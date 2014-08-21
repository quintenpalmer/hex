
#include "loc.h"

#include <stdio.h>

void printLoc(char* buf, Loc loc) {
    sprintf(buf, "Loc(%d, %d, %d)", loc.x, loc.y, loc.z);
}

Loc newLoc(int x, int y, int z) {
    Loc loc;
    loc.x = x;
    loc.y = y;
    loc.z = z;
    Loc oloc;
    oloc.x = 0;
    oloc.y = 0;
    oloc.z = 0;
    Loc retLoc = normal(loc, oloc);
    return retLoc;
}

int equalLoc(Loc loc1, Loc loc2) {
    return loc1.x == loc2.x && loc1.y == loc2.y && loc1.z == loc2.z;
}

int distance(Loc l1, Loc l2) {
    Loc l3 = normal(l1, l2);
    return abs(l3.x) + abs(l3.y) + abs(l3.z);
}

Loc normal(Loc l1, Loc l2) {
    int rx = l1.x - l2.x;
    int ry = l1.y - l2.y;
    int rz = l1.z - l2.z;

    int ax = abs(rx);
    int ay = abs(ry);
    int az = abs(rz);
    int delta = getDelta(rx, ry, rz, ax, ay, az);
    Loc retLoc;
    retLoc.x = rx - delta;
    retLoc.y = ry + delta;
    retLoc.z = rz - delta;
    return retLoc;
}

int getDelta(int rx, int ry, int rz, int ax, int ay, int az) {
    // I && II
    // +x +y +z
    if (rx >= 0 && ry >= 0 && rz >= 0) {
        return nearZero(rx, rz);
    // II && III
    // -x +y +z
    } else if (rx <= 0 && ry >= 0 && rz >= 0) {
        int amount = nearZero(rx, ry);
        if (ax > ay) {
            return -amount;
        } else if (ax == ay) {
            return -abs(amount);
        } else {
            return amount;
        }
    // III && IV
    // -x -y +z
    } else if (rx <= 0 && ry <= 0 && rz >= 0) {
        int amount = nearZero(ry, rz);
        if (ay < az) {
            return -amount;
        } else if (ay == az) {
            return abs(amount);
        } else {
            return amount;
        }
    // IV && V
    // -x -y -z
    } else if (rx <= 0 && ry <= 0 && rz <= 0) {
        return nearZero(rx,rz);
    // V && VI
    // +x -y -z
    } else if (rx >= 0 && ry <= 0 && rz <= 0) {
        int amount = nearZero(rx, ry);
        if (ax > ay) {
            return -amount;
        } else if (ax == ay) {
            return abs(amount);
        } else {
            return amount;
        }
    // VI && I
    // +x +y -z
    } else if (rx >= 0 && ry >= 0 && rz <= 0) {
        int amount = nearZero(ry, rz);
        if (ay < az) {
            return -amount;
        } else if (ay == az) {
            return -abs(amount);
        } else {
            return amount;
        }
    // +x -y +z
    } else if (rx >= 0 && ry <= 0 && rz >= 0) {
        // +x axis
        if (ax > az && az == ay) {
            return abs(nearZero(ry,rz));
        // I
        } else if (ax > az && az > ay) {
            return az;
        // +y axis
        } else if (az == ax && ax > ay) {
            return abs(nearZero(rx,rz));
        // II
        } else if (az > ax && ax > ay) {
            return ax;
        // +z axis
        } else if (az > ay && ay == ax) {
            return abs(nearZero(rx,ry));
        // III
        } else if (az > ay && ay > ax) {
            return ay;
        // -x axis
        } else if (ay == az && az > ax) {
            return abs(nearZero(ry,rz));
        // IV
        } else if (ay > az && az > ax) {
            return az;
        // -y axis
        } else if (ay > az && az == ax) {
            return abs(nearZero(rx,rz));
        // V
        } else if (ay > ax && ax > az) {
            return ax;
        // -z axis
        } else if (ax == ay && ay > az) {
            return abs(nearZero(rx,ry));
        // VI
        } else if (ax > ay && ay > az) {
            return ay;
        // Origin
        } else if (ax == ay && ay == az) {
            return ax;
        } else {
            return 0;
        }
    // -x +y -z
    } else if (rx <= 0 && ry >= 0 && rz <= 0) {
        // +x axis
        if (ax < az && az == ay) {
            return -abs(nearZero(ry,rz));
        // I
        } else if (ax < az && az < ay) {
            return -az;
        // +y axis
        } else if (az == ax && ax < ay) {
            return -abs(nearZero(rx,rz));
        // II
        } else if (az < ax && ax < ay) {
            return -ax;
        // +z axis
        } else if (az < ay && ay == ax) {
            return -abs(nearZero(rx,ry));
        // III
        } else if (az < ay && ay < ax) {
            return -ay;
        // -x axis
        } else if (ay == az && az < ax) {
            return -abs(nearZero(ry,rz));
        // IV
        } else if (ay < az && az < ax) {
            return -az;
        // -y axis
        } else if (ay < az && az == ax) {
            return -abs(nearZero(rx,rz));
        // V
        } else if (ay < ax && ax < az) {
            return -ax;
        // -z axis
        } else if (ax == ay && ay < az) {
            return -abs(nearZero(rx,ry));
        // VI
        } else if (ax < ay && ay < az) {
            return -ay;
        // Origin
        } else if (ax == ay && ay == az) {
            return -ax;
        } else {
            return 0;
        }
    } else {
        return 0;
    }
}

int nearZero(int a, int b) {
    if (abs(a) < abs(b)) {
        return a;
    } else {
        return b;
    }
}

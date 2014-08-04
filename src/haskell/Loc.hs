module Loc (
    Loc(..),
    newLoc,
    distance,
    normal
) where

data Loc = Loc Int Int Int deriving (Show, Eq)

newLoc :: Int -> Int -> Int -> Loc
newLoc x y z = normal (Loc x y z) (Loc 0 0 0)

distance :: Loc -> Loc -> Int
distance l1 l2 =
    let (Loc x y z) = normal l1 l2 in
    (abs x) + (abs y) + (abs z)

normal :: Loc -> Loc -> Loc
normal (Loc x y z) (Loc ox oy oz) =
    let (rx, ry, rz) = (x - ox, y - oy, z - oz)
        (ax, ay, az) = (abs rx, abs ry, abs rz)
        delta = getDelta rx ry rz ax ay az in
    (Loc (x - delta) (y + delta) (z - delta))


getDelta :: Int -> Int -> Int -> Int -> Int -> Int -> Int
getDelta rx ry rz ax ay az =
    -- I && II
    -- +x +y +z
    if rx >= 0 && ry >= 0 && rz >= 0 then
        nearZero rx rz
    -- II && III
    -- -x +y +z
    else if rx <= 0 && ry >= 0 && rz >= 0 then
        let amount = nearZero rx ry in
        if ax > ay then
            -amount
        else if ax == ay then
            -abs amount
        else
            amount
    -- III && IV
    -- -x -y +z
    else if rx <= 0 && ry <= 0 && rz >= 0 then
        let amount = nearZero ry rz in
        if ay < az then
            -amount
        else if ay == az then
            abs amount
        else
            amount
    -- IV && V
    -- -x -y -z
    else if rx <= 0 && ry <= 0 && rz <= 0 then
        nearZero rx rz
    -- V && VI
    -- +x -y -z
    else if rx >= 0 && ry <= 0 && rz <= 0 then
        let amount = nearZero rx ry in
        if ax > ay then
            -amount
        else if ax == ay then
            abs amount
        else
            amount
    -- VI && I
    -- +x +y -z
    else if rx >= 0 && ry >= 0 && rz <= 0 then
        let amount = nearZero ry rz in
        if ay < az then
            -amount
        else if ay == az then
            -abs amount
        else
            amount
    -- +x -y +z
    else if rx >= 0 && ry <= 0 && rz >= 0 then
        -- +x axis
        if ax > az && az == ay then
            abs $ nearZero ry rz
        -- I
        else if ax > az && az > ay then
            az
        -- +y axis
        else if az == ax && ax > ay then
            abs $ nearZero rx rz
        -- II
        else if az > ax && ax > ay then
            ax
        -- +z axis
        else if az > ay && ay == ax then
            abs $ nearZero rx ry
        -- III
        else if az > ay && ay > ax then
            ay
        -- -x axis
        else if ay == az && az > ax then
            abs $ nearZero ry rz
        -- IV
        else if ay > az && az > ax then
            az
        -- -y axis
        else if ay > az && az == ax then
            abs $ nearZero rx rz
        -- V
        else if ay > ax && ax > az then
            ax
        -- -z axis
        else if ax == ay && ay > az then
            abs $ nearZero rx ry
        -- VI
        else if ax > ay && ay > az then
            ay
        -- Origin
        else if ax == ay && ay == az then
            ax
        else
            0
    -- -x +y -z
    else if rx <= 0 && ry >= 0 && rz <= 0 then
        -- +x axis
        if ax < az && az == ay then
            - (abs $ nearZero ry rz)
        -- I
        else if ax < az && az < ay then
            -az
        -- +y axis
        else if az == ax && ax < ay then
            - (abs $ nearZero rx rz)
        -- II
        else if az < ax && ax < ay then
            -ax
        -- +z axis
        else if az < ay && ay == ax then
            - (abs $ nearZero rx ry)
        -- III
        else if az < ay && ay < ax then
            -ay
        -- -x axis
        else if ay == az && az < ax then
            - (abs $ nearZero ry rz)
        -- IV
        else if ay < az && az < ax then
            -az
        -- -y axis
        else if ay < az && az == ax then
            - (abs $ nearZero rx rz)
        -- V
        else if ay < ax && ax < az then
            -ax
        -- -z axis
        else if ax == ay && ay < az then
            - (abs $ nearZero rx ry)
        -- VI
        else if ax < ay && ay < az then
            -ay
        -- Origin
        else if ax == ay && ay == az then
            -ax
        else 0
    else 0

nearZero :: (Num a, Ord a) => a -> a -> a
nearZero a b =
    if (abs a) < (abs b) then
        a
    else
        b

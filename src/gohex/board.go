package gohex

import (
	"strconv"
	"sort"
)

type Board struct {
	locs map[[3]int]*Loc
	radius int
	size int
}

func NewBoard(size int) *Board {
	board := new(Board)
	board.locs = make(map[[3]int]*Loc)
	board.size = 0
	board.radius = size
	x,y,z := 0, 0, 0
	board.AddLoc(x,y,z)
	for i := 1; i <= size; i++ {
		x += i
		board.AddLoc(x,y,z)
		for j := 1; j < i+1; j++ {
			z += 1
			board.AddLoc(x,y,z)
		}
		for j := 1; j < i+1; j++ {
			x -= 1
			board.AddLoc(x,y,z)
		}
		for j := 1; j < i+1; j++ {
			y -= 1
			board.AddLoc(x,y,z)
		}
		for j := 1; j < i+1; j++ {
			z -= 1
			board.AddLoc(x,y,z)
		}
		for j := 1; j < i+1; j++ {
			x += 1
			board.AddLoc(x,y,z)
		}
		for j := 1; j < i+1; j++ {
			y += 1
			board.AddLoc(x,y,z)
		}
		y += 1
		x -= i
	}
	return board
}

func (board *Board) AddLoc(x,y,z int) {
	loc := NewLoc(x,y,z)
	board.locs[[3]int{x,y,z}] = loc
	board.size += 1
}

func (board *Board) Draw() string {
	lines := make(map[int]string)
	for tuple, _ := range board.locs {
		line := tuple[1] + tuple[2]
		if _, ok := lines[line]; !ok {
			lines[line] = ""
		} else {
			lines[line] += ","
		}
		lines[line] += "("
		for i,coord := range tuple {
			if i > 0 {
				lines[line] += ","
			}
			if coord > -1 {
				lines[line] += " "
			}
			lines[line] += strconv.Itoa(coord)
		}
		lines[line] += ") "
	}
	retString := ""
	keys := make([]int,0)
	for i,_ := range lines {
		keys = append(keys,i)
	}
	sort.Ints(keys)
	for _,i := range keys {
		retString += lines[i] + "\n"
	}
	return retString
}

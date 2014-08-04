package main

import (
	"fmt"
	"gohex"
	"flag"
)

var size = flag.Int("s",1,"the size of the board to make")

func main() {
	flag.Parse()
	fmt.Println(*size)
	board := gohex.NewBoard(*size)
	//fmt.Println(board)
	fmt.Println(board.Draw())
	/*
	origin := gohex.NewLoc(0,0,0)
	loc := gohex.NewLoc(1,-1,-1)
	fmt.Println(loc)
	fmt.Println(origin.Distance(loc))
	*/
}

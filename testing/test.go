package main

import (
	"fmt"
	"strings"
	"strconv"
	"io/ioutil"
	"gohex"
)

var debug int

func main() {
	b, err := ioutil.ReadFile("config.txt")
	if err != nil {
		panic(err)
	}
	debug_and_instr := strings.Split(string(b),",")
	debug, _ = strconv.Atoi(strings.Trim(debug_and_instr[0]," \t\n"))
	instr := strings.Trim(debug_and_instr[1]," \t\n")
	if instr == "create" {
		createTest()
	} else {
		fmt.Println("Not a valid test")
	}
}

func createTest() {
	fmt.Println("createTest")
	worked := true
	header := ""
	meta_header := ""
	num_wrong := 0
	num_tested := 0
	b, err := ioutil.ReadFile("data/createTest.txt")
	// "data/createTest.log"
	if err != nil {
		panic(err)
	}
	for _, line := range strings.Split(string(b),"\n") {
		line = strings.Trim(line," \t")
		if len(line) > 0 && string(line[0]) == "#" {
			if len(line) > 1 {
				if string(line[1]) == "!" {
					header = line[3:]
				} else if string(line[1]) == "@" {
					meta_header = line[3:]
				}
			}
		} else if len(line) > 0 {
			split := strings.Split(line,",")
			x, _ := strconv.Atoi(split[0])
			y, _ := strconv.Atoi(split[1])
			z, _ := strconv.Atoi(split[2])
			xd, _ := strconv.Atoi(split[3])
			yd, _ := strconv.Atoi(split[4])
			zd, _ := strconv.Atoi(split[5])
			should_be_same := split[len(split)-1] == "true"
			loc1 := gohex.NewLoc(x,y,z)
			loc2 := gohex.NewRawLoc(xd,yd,zd)
			same := (loc1.Eq(loc2)) == should_be_same
			worked = same && worked
			if !same {
				num_wrong += 1
			}
			num_tested += 1
			var result string
			if same {
				result = "true"
			} else {
				result = "false"
			}
			full_result := fmt.Sprintf("%s ------------\n%s\n%s\n%r\n%r\n",same,meta_header,header,loc1,loc2)
			if debug == 2 || (debug == 1 && !same) {
				ioutil.WriteFile("data/createTest.log",[]byte((full_result)),0644)
			} else {
				ioutil.WriteFile("data/createTest.log",[]byte((result)),0644)
			}
		}
	}
	var success string
	if worked {
		success = "Success!"
	} else {
		success = "Failure!"
	}
	fmt.Printf(`Results:
debug: %d
%s
%d/%d succedded

`,debug,success,num_tested - num_wrong,num_tested)
}

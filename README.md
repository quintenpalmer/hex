hex
===
Hex coords for 2D discrete movement

Config
------
The config file is config.txt
It is a comma-seperated list, where
The first item is the debug level to run at.
0 : Only show how many tests passed and failed
1 : Only show the full output for tests that fail
2 : Show the output for every test that runs

The second item is the type of test to run.
The only type of test is creation.

Run
---
To run the python test, from the root of
the project run:
	./hex python

To run the Java test, from the root of the
project run:
	./hex javacompile
	./hex java

To run the Go test, from the root of the project
run:
	./hex go

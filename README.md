# Project 0: Onboarding 
By Lucas Brito (`lbrito2`). Est. 8 hours to complete.

# Design 
`Main.java` contains the REPL implementation, including the read and print 
architecture and delegating the computational work to `MathBot.java` and 
`CSVParser.java`. 

`MathBot.java` contains implementations of addition, subtraction, 
k nearest-neighbors, and Euclidean distance. `distance` takes
arbitrary-dimension vectors for generality. `naive_neighbors` currently takes 
in an `ArrayList` of strings as the datatype for the data from which the nearest
neighbors will be determined for this is the datatype produced by `CSVParser`;
additionally, the method has been abstracted to take as input a user-specified
sorting `Comparator` which will be used to define "nearness."

`CSVParser.java` is constructed without arguments, and uses a `read()` method 
to, using the built-in `File` object, read a specified path. The class does not 
take the path as an argument to the constructor in order to allow the user to 
use the same instance to read multiple `.csv` files. Upon calling `read()`, the 
instance will use `Collections.clear()` to dump the parsed data. The class 
uses `ArrayList` for flexibility (especially in size of array when actually 
parsing the `.csv` file). The object does not parse column data types, leaving 
datatype decisions up to the user. 

# Errors/Bug 
The implementation naively sorts the parsed `.csv` `ArrayList` without shuffling 
it in order to randomly sample equally-distant rows. This was previously 
implemented, but re-implementing the feature leads to failure of the 
`naiveNeighbors()` functionality test (line 166). Curiously, this naive 
implementation appears to pass `naiveNeighborsRandomizesTies()`; further 
investigation is in order.

There was also a bizarre bug with JUnit's `assertNull` function and the 
columns returned by `CSVParser.getCols()`, and so this test has not been 
performed. 

# Tests 
System testing ensures that the REPL appropriately handles: 
- Errors (`error.test`)
- Addition functionality (`adding.test`)
- Subtraction functionality (`subtraction.test`)
- Multiple commands (`multiplecommands.test`)
- No-neighbor result (`0-neighbors.test`)
- One-star result (`1-star.test`)
- Bad filenames (`no_stars_file.test`)
- Coordinates coinciding precisely with existing star (`coords_on_star.test`)
- Extra spaces in commands (`double_space.test`)
- Exclusion of named star in stars command with `ProperName` (`exclude_star/test`)
- Bad commands (`incorrect_command.test`)
- Spaces in arguments (`name_with_spaces.test`)
- Numerical arguments in quotes (`number_with_quotes.test`)
- Dump data upon re-parsing new CSV (`reload_data.test`)

Unit testing of `MathBot` ensures addition, subtraction, and nearest-neighbors 
implementations handle large and small numbers well, ensurs that nearest
neighbors produces a random sample of equally-distant objects, distance between 
vectors with unequal dimensions, k values larger than the dataset, and 
non-Euclidean metrics given to the nearest-neighbors implementation. 

Unit testing of `CSVParser` ensures that its index-finder functionality 
appropriately handles nonexistent column names, that `read()` throws the 
appropriate exception given a bad filename, that a `CSVParser` that has not 
parsed a `.csv` is still capable of returning (empty) values for rows (see above 
for column case), that instances dump parsed `.csv`s upon calling `read()`, and
that getters work properly. 

# Usage 
To build use:
`mvn package`

To run use:
`./run`

To start the server use:
`./run --gui [--port=<port>]`

To test addition and subtraction and basic REPL functionality: 
`./cs32-test src/test/system/*.test`

To test stars-related commands: 
`./cs32-test src/test/system/stars/*.test`

To run all unit tests: 
`mvn test`
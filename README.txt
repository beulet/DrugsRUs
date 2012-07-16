The problem with drugs:
You are a drug trafficker. Every day you meet with a different nice older lady (the mule) and find out how much weight she can carry in her handbag. You then meet with your supplier who has packed various drugs into a myriad of collectible porcelain dolls. Once packed with drugs, each of the precious dolls has a unique combination of weight and street value. Sometimes your supplier has more dolls than the nice lady can carry, though space in her handbag is never an issue. Your job is to choose which dolls the kind soul will carry, maximizing street value, while not going over her weight restriction.


My Solution: The last program you will need before going to jail (seeing as you really need to maximize your cash to make bail).
The following program written in Clojure can be used to calculate the maximum profit and load for Granny.  Not only will it solve the problem with the presented doll values and weights, but it can solve your problem everyday with whatever odd arrangement of dolls that you are handed.  Just input those values into the data.txt file and you can quickly pack Granny's bag.


Running the program:

Because of issues installing and running leiningen the best method of installation is to simply fork the repository.

There are three main methods of interest:
	(solve-drug-smuggler 400) -This function will use the problem data given to find the maximum value and the set of dolls that deliver that value.  The maximum weight of the bag is the input argument which can be changed to any weight.

	(unit-test) -This function runs the unit tests for the program.  A success message is printed out if all assertions are successful.  The max value and solution vector of the short test are also printed out.

	(test-other-data "C://path/to/some/data.txt") -This function will read a text file and produce the solution for that data.  The data file must be in the format name weight value with only one space between data points and no punctuation.  The maximum value and solution vector is printed out to the repl console.

  


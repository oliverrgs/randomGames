import java.util.*;
import java.io.*;



class Guess {

	static int LINE_MAX = 100;
	static int GOT_IT = 1;
	static int TOO_BIG = 2;
	static int TOO_SMALL = 3;
	static int MAX_NUM = 100;
	static Scanner scan = new Scanner (System.in);

	
	public static void main (String[] args) throws java.io.IOException {
  int response = 0;
  int numGuesses = 1;
  int min = 1;
  int max = MAX_NUM;
  int guess = nextGuess(min, max);
  System.out.println("Think of a number between " + min + " and " + max);
  System.out.println("I'll try and guess it in 8 or fewer guesses.");

  while (min < max ) {
    // make a guess and get the user response
    response = makeAGuess(numGuesses, guess);

    // decide what to do based upon the user response
    if (response == GOT_IT) {
      min = max = guess;
    }
    else if (response == TOO_BIG) {
      max = guess -1;
      guess = nextGuess(min, max);
      numGuesses++;
    }
    else if (response == TOO_SMALL) {
      min = guess+1;
      guess = nextGuess(min,max);
      numGuesses++;
    }
    else {
      System.out.println("That was not a valid response.");
    }
  }
  if (response != 1) {
    System.out.println("The number you were thinking of was " + guess);
    numGuesses++;
  }
  System.out.println("I did it in " + numGuesses + " guesses.");
}

/*
  Prompt the user with the current guess and ask for their response.
  The response should be an integer value that is one of GOT_IT, TOO_BIG, or
  TOO_SMALL (constants).
 */
static int makeAGuess(int numGuesses, int guess) {
  int response;

	System.out.println("Guess " + numGuesses + ": Is it " + guess + "?");
	System.out.println("Enter " + GOT_IT + " if I guessed correctly");
	System.out.println("Enter " + TOO_BIG + " if my guess was too big. Otherwise enter " + TOO_SMALL);
	response = scan.nextInt();
	return response;
	}

  
/*
  Determine the next guess given the current min and max values for the possible
  range of the  unknown value. The optimal strategy is to guess right in the 
  middle of the current range, thus cutting the range in half on each guess.
 */
	static int nextGuess (int min, int max) {
		int diff;
		diff = max - min;
		return (min + (diff+1)/2);
	}
}


/*This program was created using lone programming by 
 Oliver Goldberg-Seder (ogoldber). 
 
 Because I never contacted my partner (I was content with working out
  this problem on my own) and they never contacted me, I defaulted to doing
  this assignment on my own.
  
  I spent 4 HOURS working alone.
 
 The program is a craps simulator. It is run in two
 modes. 
	The first mode is a user-interface mode used simply
	with "java playcraps." The user sets the seed, enter their
	initial money, and may bet and roll the dice until he has
	no money left.
	
	The second mode plays the game automatically, and is 
	called with "java playcraps #" where number is a int
	greater than 0 (any other value will give an invalid result).
	This mode plays the craps game for # times and the prints out
	the (win/total games played) percentage.
 */
import java.util.*;
import java.io.*;
 
class PlayCraps{
	public static void main (String[] args) throws java.io.IOException {
		/*
		These two conditions divide the program into halves
		
		The non-arged half is the game, played by the user
		with the gui enabled
		
		The arged half is the "probability of winning" program,
		which determines the win ratio for arg[0] games
		*/
		if (args.length == 0){
			//this is the splash screen, just for looks
			
			int[] rowone={1,2,3,4,5,6};
			ShowDice(rowone);
			int[] rowtwo={1,2,0,0,2,1};
			ShowDice(rowtwo);
			int[] rowThree={6,5,4,3,2,1};
			ShowDice(rowThree);
			
			
			
			UserGame();
			}
		else {
		// run the craps game args[0] times and print win %
			int size = 0;
			try { size=Integer.parseInt(args[0]); } 
				catch(NumberFormatException e) { } 
			Random rand = new Random();
			int successes = 0;
			//false =nogui
			for (int i = 0; i < size; i++)
				if (CrapsGame(rand, false))
					successes++;
			System.out.println("You won " + 
			100*(double)successes / (double)size + "% of the time");
		}	
	}
	/*
	The user-end of gameplay. Takes in initial conditions
	and bets. The game is played in another method and determines
	if the bet is won or lost.
	
	Some conditions are required for betting:
	no negative or 0 bets. Also, when the money is
	0 the game ends.
	*/
	static void UserGame () throws java.io.IOException {
		Scanner scan = new Scanner (System.in);
		System.out.print("Enter Seed:");
		int seed = scan.nextInt();
		Random rand = new Random(seed);
		System.out.print("Enter Money:");
		int money = scan.nextInt();
		int bet = 0;
		//play until bankrupt
		while(money > 0){
			//bet
			System.out.println("-----You have " + money + " dollars-----");
			System.out.print("Your bet:");
			boolean madebet = false;
			while (madebet != true){
				bet = scan.nextInt();
				madebet = true;
				if (money < bet || bet <= 0){
					madebet = false;
					System.out.println("-Not enough money or invalid bet");
				}
			}
			//play game (true = gui)	
			if(CrapsGame(rand, true)){
				System.out.println("You win " + bet + " dollars");
				money = money + bet;
			}
			else {
				System.out.println("You lose " + bet + " dollars");
				money = money - bet;
			}
		}
		//you lose
		System.out.println("Money is " +money+ ", game over");
	}
	
	/*This method is the actual game, called with a random for the purposes of 
	bugtesting and a boolean that tells it whether or not it should show the
	graphical user elements. It returns a win/loss boolean after the game is played.
	The dice-showing elements are outsourced to the "showdice" method.
	It plays the game of craps using two Randoms
	as dice and a switch statement to determine the results of the
	first roll. A while loop is used for the "point," waiting until the point
	or a 7 is rolled*/
	static boolean CrapsGame (Random rand, boolean gui) throws java.io.IOException {
		int[] die = new int[2];//make 2
		die[0] = rand.nextInt(6) + 1;
		die[1] = rand.nextInt(6) + 1;
		int point = die[0] +die[1];
		if(gui){//10=enter key ascii value
			System.out.println("Press Enter Key to Roll");
			while(System.in.read() != 10);
			ShowDice(die);
		}
		switch(die[0] +die[1]){
			case(11):
			case(7):return(true);
			case(3):
			case(2):
			case(12):return(false);
			default:
				if(gui){
					System.out.println("The point is " + point);
					System.out.println("Press Enter Key to Roll");
				}
		}
		while (true){
			die[0] = rand.nextInt(6) + 1;
			die[1] = rand.nextInt(6) + 1;	
			if(gui){
				while(System.in.read() != 10);
				ShowDice(die);}
			if ((die[0] + die[1])==7)
				return(false);
			if ((die[0] + die[1]) == point)
				return(true);
		}
	}
	/*
	This method, "ShowDice" takes in a integer array of integers 1 though 6
	and prints dice ascii images from the integers.
	The craps assignment requires only two dice, 
	but this method allows the displaying
	of many, many,  more dice and 
	thus is a more robust and useful solution.
	
	This method prints dice line-by-line, printing
	each part of the die seperately.
	
	There are 5 possible variations of how the dots on a 
	die may be on a line. For die 1 and line 2, it is "| * |".
	This is shared with 3 and five, so it is apparent how this
	method is space efficient (especially when considering that "|* *|
	is shared by 6, 5 and 4 seven times).
	*/
	static void ShowDice (int die[]) {
		int s = 0;
		int q = 0;
		while (q < die.length){
			//the number added to s here (and 15 lines down) is the line length
			q = s+7;
			if (q > die.length)
				q = die.length;
			for (int line = 0; line <5; line++){
				for(int i =s; i<q; i++){
					if (line == 0)
						System.out.print(" ___  ");
					else if (line == 4)
						System.out.print(" ^^^  ");
					else if ((die[i] == 1 && (line == 1 || line == 3)) || 
						((die[i] == 2 || die[i] == 4) && line == 2))
						System.out.print("|   | ");
					else if ((die[i] == 2 || die[i] == 3) && line ==1)
						System.out.print("|  *| ");
					else if ((die[i] % 2 == 1) && line == 2)
						System.out.print("| * | ");
					else if (die[i] <= 3 && die[i] > 0)
						System.out.print("|*  | ");
					else if (die[i] > 3)
						System.out.print("|* *| ");
					else System.out.print("Craps ");
				}
				System.out.println("");
			}
			s = s+7;
		}
	}
}

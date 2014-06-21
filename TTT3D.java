/* This program was created using lone programming
by me, Oliver Goldberg-Seder (ogoldber)

I did not work with my partner because they never contacted me.
I was happy to do this assignment on my own.

I spent 4 hours working alone.

This program is a 3-dimensional 4x4x4 game of tic-tac-toe
where 4 of a player's marks on a single row, diagonal (both 2d and 3d)
 or column will win them the game. 
 
 A special call may be used with this program,
 a text file where the first number is the number of lines, and 
 each subsequent line is 4 integers- the board number, the row, the column,
 and then the player's move- 5 for
 the computer and 1 for the player. By default all spaces are 0, blank.
 
 The difficult aspect of this program was making an AI to respond to other
 moves with a degree of intelligence. To do this, the program looks 
 at each and every space and adds up all the lines going through that space 
 using some simple looping 'if' statements. The sum of a line is passed to a 
 method that weights the sum. A line with a player and computer mark is worth
 0, because there is no chance of winning if that move is made. A line with 3 
 computer marks on the other hand gets top priority and so forth.
 
  The AI also checks to see if it is a cat's game or if a player has won,
  because it is very fast. It has some weaknesses and is not difficult to beat
  if you fork it, a problem which is rather difficult to solve and I have yet to find
  a solution. 
 */

import java.util.*;
import java.io.*;
class TTT3D{
	public static void main (String[] args) throws java.io.IOException {
		//initialize stuff
		int board[][][] = new int[4][4][4];
		int x = 0;
		int y = 0;
		int z = 0;
		int PlayerMove = 0;
		Scanner scan = new Scanner (System.in);
		
		//make the board
		if(args.length == 1) {
			Scanner in = new Scanner(new FileInputStream(args[0]));
			int size = in.nextInt();//assume the first line is the number of lines
			for(int i = 0; i <= size; i++)
				board[in.nextInt()][in.nextInt()][in.nextInt()] = in.nextInt();
		}
		
		//check the starting board
		boolean gameover = false;
		System.out.println(AiResponse(board));
		int AiMove = AiResponse(board);
		if(AiMove == -1 || AiMove == -2){
			ShowBoard(board);
			System.out.println("Invalid Starting Board");
			gameover = true;
		}
		/*The "main" part of the game, with a gui
		The player makes their move (under restrictions),
		the game checks to see if the player won and continues
		along to the Ai's move
		
		The player's move is parsed digit-per digit. The computer's
		move is encoded as a single int defined by x*16 + y*4 + z
		*/
		while (!gameover){
			ShowBoard(board);
			/*the next two groups are for the player
			and computer's moves respectively
			
			By changing what mark the player makes and
			the number of moves/turn, you can
			make a game with any number of players
			computer or human
			*/
			boolean PlayerHasMoved = false;
			while (!PlayerHasMoved){
				System.out.print("Your move:");
				PlayerMove = scan.nextInt();
				x = PlayerMove /100;
				y = (PlayerMove %100)/10;
				z = (PlayerMove %100)%10;
				if (3 < z || z < 0 || 3 < y || y < 0 || 3 < x || x < 0)
					System.out.println("Out of Range");
				else if (board[x][y][z]!=0)
					System.out.println("Occupied");
				else {
					board[x][y][z] = 1;
					PlayerHasMoved = true;
					//AiResponse is -1 for game over, -2 for tie game
					if(AiResponse(board) == -1){
						ShowBoard(board);
						System.out.println("You Win");
						gameover = true;
					}
					else if(AiResponse(board) == -2){
						ShowBoard(board);
						System.out.println("Cat's Game");
						gameover = true;
					}
				}
			}
			
			AiMove = AiResponse(board);
			x= AiMove/16;
			y=(AiMove % 16) / 4;
			z=(AiMove% 16) % 4;
			if (AiMove == -2){
				ShowBoard(board);
				System.out.println("Cat's Game");
				gameover = true;
			}
			if (gameover == false){
				board[x][y][z] = 5;	
				if (AiResponse(board) == -1){
						ShowBoard(board);
						System.out.println("I win");
						gameover = true;
					}
			}
		}
		
		System.out.println("Game Over");
	}
	/* show the board */
	public static void ShowBoard(int board[][][]){
		for(int i = 3; i>=0;i--){
			for(int j = 3; j>=0;j--){
				for(int t = 0; t < j; t++)
					System.out.print(" ");
				System.out.print(i+""+j + "  ");
				for(int k = 0; k<4; k++){
					if (board[i][j][k] == 5)
						System.out.print("O ");
					else if (board[i][j][k] == 1)
						System.out.print("X ");
					else if (board[i][j][k] == 0)
						System.out.print("_ ");
					else System.out.print("? ");
				}
				System.out.println("");
			}System.out.println("");
		}System.out.println("    0 1 2 3");
	}
	/*give a sum of values on a line a "weight" score
	the higher the score, the better the move. The best move
	is on a line with 3 CPU marks, and has 100% priority. The second
	best is on a line with 3 Player marks, and so forth.
	
	Currently it is possible to fork the computer, however I think this
	may be a problem of the game's inherent advantage to the player
	who first moves
	*/
	public static int WeightScore(int score){
		Random rand = new Random();
		switch(score){
			case(0):return(4);
			case(1):
			case(5):return(10);
			case(2):
			case(10):return(21);
			case(3):return(1000);
			case(15):return(6001);
			default:return(0);
		}
	}
	
	/*The algorhythm that governs the computer's response
	It simply goes to each potential spot and adds up the scores* of lines
	passing through that spot. The spot with the highest score wins.
	
	*scores are determined by the "WeightScore" Method
	
	The method also includes two special returns.
	
	-1 a player has won
	-2 is cat's game
	
	These may be applied to either player's moves
	*/
	
	public static int AiResponse(int board[][][]){
		//64 possible lines
		int value = 0;
		int basevalue = 0;
		int scorevalue = 0;
		int linevalue = 0;
		int l = 0;
		//-2 = cat's game
		int AiMove = -2;
		//for each point...
		for(int i = 0; i<4;i++){
			for(int j = 0; j<4;j++){
				for(int k = 0; k<4; k++){
					linevalue = 4 * board[i][j][k];
					scorevalue = 0;
					//...look at horizontals and verticals...
					for (l = 0; l<4; l++)
						value = value + board[l][j][k];
					scorevalue = scorevalue + WeightScore(value);
					if(value == linevalue && linevalue != 0)
						return(-1);
					value = 0;
					for (l = 0; l<4; l++)
						value = value + board[i][l][k];
					scorevalue = scorevalue + WeightScore(value);
					if(value == linevalue && linevalue != 0)
						return(-1);
					value = 0;
					for (l = 0; l<4; l++)
						value = value + board[i][j][l];
					if(value == linevalue && linevalue != 0)
						return(-1);
					scorevalue = scorevalue + WeightScore(value);
					value = 0;
					//...look at 2d diagonals...
					if (j==k){
						for (l = 0; l<4; l++) 
							value = value + board[i][l][l];
						scorevalue = scorevalue + WeightScore(value);
						if(value == linevalue && linevalue != 0)
							return(-1);
						value = 0;
					}
					if (k==i){
						for (l = 0; l<4; l++) 
							value = value + board[l][j][l];
						scorevalue = scorevalue + WeightScore(value);
						if(value == linevalue && linevalue != 0)
							return(-1);
						value = 0;
					}
					if (j==i){
						for (l = 0; l<4; l++) 
							value = value + board[l][l][k];
						scorevalue = scorevalue + WeightScore(value);
						if(value == linevalue && linevalue != 0)
							return(-1);
						value = 0;
					}
					if (j + k == 3){
						for (l = 0; l<4; l++) 
							value = value + board[i][3-l][l];
						scorevalue = scorevalue + WeightScore(value);
						if(value == linevalue && linevalue != 0)
							return(-1);
						value = 0;
					}
					if (k + i==3){
						for (l = 0; l<4; l++) 
							value = value + board[3-l][j][l];
						scorevalue = scorevalue + WeightScore(value);
						if(value == linevalue && linevalue != 0)
							return(-1);
						value = 0;
					}
					if (j + i==3){
						for (l = 0; l<4; l++) 
							value = value + board[l][3-l][k];
						scorevalue = scorevalue + WeightScore(value);
						if(value == linevalue && linevalue != 0)
							return(-1);
						value = 0;
					}
					//...look at 3d diagonals...
					if (j==i && i == k){
						for (l = 0; l<4; l++) 
							value = value + board[l][l][l];
						scorevalue = scorevalue + WeightScore(value);
						if(value == linevalue && linevalue != 0)
							return(-1);
						value = 0;
					}
					if (i==j && j + k==3){
						for (l = 0; l<4; l++) 
							value = value + board[l][l][3-l];
						scorevalue = scorevalue + WeightScore(value);
						if(value == linevalue && linevalue != 0)
							return(-1);
						value = 0;
					}
					if (j==k && j + i==3){
						for (l = 0; l<4; l++) 
							value = value + board[3-l][l][l];
						scorevalue = scorevalue + WeightScore(value);
						if(value == linevalue && linevalue != 0)
							return(-1);
						value = 0;
					}
					if (k==i && i + j==3){
						for (l = 0; l<4; l++) 
							value = value + board[l][3-l][l];
						scorevalue = scorevalue + WeightScore(value);
						if(value == linevalue && linevalue != 0)
							return(-1);
						value = 0;
					}
					//... on a point that is avaliable
					if(board[i][j][k] == 0){
						/*.. take the sum, and if the value of the point is higher than 
						the current best point, make it the new current best point. */
						if (basevalue < scorevalue){
							basevalue = scorevalue;
							//encode the move as an int
							AiMove = 16*i + 4 * j + k;
						}
					}
				}
			}
		}
	return(AiMove);
	}
}

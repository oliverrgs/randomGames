import java.util.*;
import java.io.*;

class PositiveEncouragement {
	public static void main (String[] args)  
	{
      int howencouragedami =0;
      if (args.length == 0){
			howencouragedami = 1;
			}
		else {
		// run the craps game args[0] times and print win %	
			try { howencouragedami=Integer.parseInt(args[0]);} 
				catch(NumberFormatException e) { } 
		}
      String leadin = ""; 
      String encouragement = ""; 
      String exclaimer = ""; 
	  Random randemer = new Random();
      //number of cases = randemer input
      System.out.println();
      for(int i=0;i<howencouragedami;i++){
        switch(randemer.nextInt(16)){
            case(0):leadin ="You can ";break;
            case(1):leadin ="Let's ";break;
            case(2):leadin ="Good job and ";break;
            case(3):leadin ="You can do accomplish a lot if you ";break;
            case(4):leadin ="Don't ever hesitate to ";break;
            case(5):leadin ="Nothing can stop you if you ";break;
            case(6):leadin ="Excellent work, continue to ";break;
            case(7):leadin ="You'll meet those deadlines, so just ";break;
            case(8):leadin ="Keep patient and ";break;
            case(9):leadin ="Keep your eyes on the prize and ";break;
            case(10):leadin ="Defeat the little things that make you unable to ";break;
            case(11):leadin ="Stay on task and ";break;
            case(12):leadin = "Teamwork will allow us to ";break;
            case(13):leadin = "I came by on monday when everyone was here and working, ";break;
            case(14):leadin = "Also, ";break;
            case(15):leadin = "I can see that you can ";break; 
        }
        switch(randemer.nextInt(13)){
            case(0):encouragement = "work hard and prosper";break;
            case(1):encouragement = "keep up the good work";break;
            case(2):encouragement = "act as a team";break;
            case(3):encouragement = "do great tasks together";break;
            case(4):encouragement = "exceed our expectations";break;
            case(5):encouragement = "leap hurdles";break;
            case(6):encouragement = "beat the quotas";break;
            case(7):encouragement = "finish the big project";break;
            case(8):encouragement = "overcome great hurdles";break;
            case(9):encouragement = "attain victory";break;
            case(10):encouragement = "solve problems";break;
            case(11):encouragement = "be the best";break;
            case(12):encouragement = "show the opposition your best";break;
        }
        switch(randemer.nextInt(11)){
            case(0):exclaimer = "! ";break;
            case(1):exclaimer = " by keeping yourself on track! ";break;
            case(2):exclaimer = " with haste and professionalism! ";break;
            case(3):exclaimer = " by using the tools at your disposal! ";break;
            case(4):exclaimer = " and do what needs to be done! ";break;
            case(5):exclaimer = ", but remember the company vision! ";break;
            case(6):exclaimer = ", but do not let up! ";break;
            case(7):exclaimer = ". Period. ";break;
            case(8):exclaimer = "- we can hold the number one position! ";break;
            case(9):exclaimer = " by keeping the focus on *THE* project! ";break;
            case(10):exclaimer = " with all of your talents! ";break;
        }
        System.out.print(leadin+encouragement+exclaimer);
      }
      System.out.println();
	}
}


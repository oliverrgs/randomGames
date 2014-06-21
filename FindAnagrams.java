/*
  This program was completed using lone programming by me
 Oliver Goldberg-Seder (ogoldber).
Because I never contacted my partner (I was content with working out
  this problem on my own) and they never contacted me, I defaulted to doing
  this assignment on my own.
  
  I spent 4 HOURS working alone.

  This program is meant to find anagrams of a string entered from a
  given dictionary. As far as meeting this purpose in its current form I believe
  it is highly effective and fast and meets the specifications to the letter.
  
  The program works by loading the dictionary into a
  array and then asking the  user for a string. The string is 
  given a special code*  by generating a prime for
  each character.  The code matches other words with the
  same character set, and should this happen the anagram will be printed.
  
  There are two prompts, one asks for the string, the other asks to try
  again. The argument is the dictionary to use.
 
 *This code may only be matched
  by other words with a set of characters that
  multiply out to the same number.  
  Because the characters are assigned primes, there is only
  one set of factors for each code.  Statistically,
  only words with the same characters may have the same code.
  Because the numbers "roll over" to negative there is a extremely, absurdly rare
  chance that mismatches may occur. By also comparing the lengths of the 
  strings and hashing, the program works faster and 
  further decreases the mismatch probability.
  Also, a  check ensures that the entered string is 
  not printed.*
 
*/

import java.util.*;
import java.io.*;

class FindAnagrams {
    public static void main(String[] args) 
	throws FileNotFoundException, java.io.IOException {
		//initialize stuff
		int i = 0;
		Scanner scan = new Scanner (System.in);
		Scanner in = new Scanner(new FileInputStream(args[0]));
		boolean trytocontinue=true;
		//get the size of the file and make an array
		int size = in.nextInt();//assume the first line is the number of lines
		StringBuffer compare[] = new StringBuffer[size];
		for (i=1; size > i; i++){//load all words into array
			compare[i] = new StringBuffer(in.next());
		}
		
		//repeat program until user wants to stop
		while (trytocontinue == true){
			//get user input
			System.out.print("type a string and I will try to find anagrams:");
			StringBuffer anagram = new StringBuffer(scan.nextLine());
			int codeofword =WordCode(anagram);
			//compare to every word
			for (i=1; size > i; i++){
				/*condition 1 prevents comparing words of different length
				the second uses a hash to separate even more non-anagrams
				third is slow and precise, 
				comparing to be sure both words have same characters
				fourth is to meet spec of not printing the input string
				
				only third condition is needed for program to function, first
				two are just for speed and fourth is for spec.
				*/
				if (
				anagram.length() == compare[i].length() && 
				SecondCheck(anagram, compare[i]) &&
				codeofword == WordCode(compare[i]) && 
				!anagram.toString().equals(compare[i].toString())
				)
					System.out.println(compare[i]);
			}
			//try again?
			System.out.print("That's all I could find, try again? y/n:");
			char choose = '?';
			while (choose != 'y' && choose != 'n'){
				anagram = new StringBuffer(scan.nextLine());
				choose = anagram.charAt(0);
				if(choose == 'n')
					trytocontinue = false;
			}
			System.out.println("");
		}
	}

	/*The second check is a hashing algorithm to 
	avoid having to run the comparison word through the
	more effective yet slower WordCode method. This increases
	the speed of the program 20 fold but is imprecise and cannot be
	relied upon
	
	it takes two StringBuffers of equal length and sees if they have the same ascii sums
	*/
	static boolean SecondCheck (StringBuffer word1, StringBuffer word2) {
		int base1 = 1;
		int base2 = 1;
		for (int i=word1.length()-1; i>=0; i--){
			base1 = base1 * word1.charAt(i);
			base2 = base2 * word2.charAt(i);
		}
		return(base1 == base2);
	}
	
	/* This method takes a StringBuffer
	and examines a character,
	the character is given a prime value 
	(generated by method "prime()")
	specific to that character. That prime
	value is multiplied by a base (initially 1)
	and when there are no characters left a value 
	specific to the letter-set of the StringBuffer is returned
	as an int.
	*/	
	
	static int WordCode (StringBuffer word) {
		int wordlength = word.length();
		StringBuffer attach = new StringBuffer("");
		int wordcode = 1;
		for (int i=0;i<wordlength;i++){
			//multiply base by 'i'th character in word prime
			wordcode = wordcode * Prime((char)(word.charAt(i)));
		}
		return wordcode;
	}

	/*This method is a modified version of a prime number generator
	I wrote on my own time. It has been modified to generate an nth prime for
	number n (input into the method as a 'char'),
	always returning the same prime for that number*/
	static int Prime (char numberpicked) {
		int r=0;
		int number = 3;
		int chosenprime = 1;
		boolean prime=true;
		while (r<numberpicked){int divisor = 2;
			number = number + 2;
			prime = true;
			while (divisor < number){
				if(number % divisor == 0) {
					prime = false;
					divisor = number;
				}
				divisor = divisor + 1;
			}
			if (prime == true) {
				r++;
				chosenprime=number;
			}
		}
		return(chosenprime);
	}

}
	
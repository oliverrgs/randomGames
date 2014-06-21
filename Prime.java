import java.util.*;
import java.io.*;

class Prime {
	public static void main (String[] args)  
	{
	java.util.Timer m_timer=new java.util.Timer();
	long startTime = System.currentTimeMillis();
	long number = 1;
	long divisor = 1;
	boolean prime = true;
	double Root = 1;
	while (number < 100000000)
		{
		
		divisor = 3;
		number = number + 2;
		prime = true;
		Root = Math.sqrt(number);
		while (divisor <= Root){
			if(number % divisor == 0) {
				prime = false;
				divisor = number;
				}
			divisor = divisor + 2;
			}
		if (prime == true)  {
			System.out.println(number);
			}			
		}
		long stopTime = System.currentTimeMillis();
        System.out.println(stopTime - startTime);
	}
}


/*try*/
	/*BufferedWriter out = new BufferedWriter(new FileWriter("Primes.txt", true));
			out.newLine();
			out.write(String.valueOf(number));
			out.close(); */

			//catch (IOException e){	e.printStackTrace();}

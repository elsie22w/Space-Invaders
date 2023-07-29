
/*
 Util.java 
 Elsie Wang
 Util class for utility methods, picking a random integer and running a delay. 
 */

public class Util {
    public static int randint(int low, int high){ // method chooses a random integer from low to high parameters
        return(int)(Math.random()*(high-low-1) + low); // (high-low-1) is range, low is lower bound
    }

    public static void delay (long len){ // method that creates a delay in running code "freezes"/makes program sleep for specified time
		try	{
		    Thread.sleep (len);
		}
		catch (InterruptedException ex)	{
		    System.out.println("ex");
		}
    }
}

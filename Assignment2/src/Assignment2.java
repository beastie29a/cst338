/*
   Robert Contreras
   CST 338 - Fall 2015 Session A
   Assignment 1, Part 1 and 2
   Team Members: Hyo Lee, Ryan Doherty
*/

import java.util.*;
import java.lang.Math;

public class Assignment2
{

   // Constants to set the betting range
   public static final int MIN_BET = 1;
   public static final int MAX_BET = 100;

   public static void main(String[] args)
   {
      int multiplier, winnings;
      int pullCount = 0;
      TripleString finalPull = new TripleString();

      // Get input from the user
      int myBet = getBet();

      // Loop to control the program
      // A bet of 0 or reaching MAX_PULLS ends the loop
      while (0 != myBet && pullCount++ < TripleString.MAX_PULLS)
      {
         // Main workflow according to Specifications
         TripleString myPull = pull();
         multiplier = getPayMultiplier(myPull);
         winnings = multiplier * myBet;
         myPull.saveWinnings(winnings);
         display(myPull, winnings);
         
         myBet = getBet();
      }
      // Loop ended, print out the winnings to the console
      System.out.println(finalPull.displayWinnings());
   }
   
   // prompts the user for input, returns int
   public static int getBet()
   {
      Scanner keyboard = new Scanner(System.in);
      
      while (true)
      {
         System.out.println("How much would you like to bet " + MIN_BET 
            + " - " + MAX_BET + " ) or 0 to quit? ");
         
         int myBet = keyboard.nextInt();
      
         if (myBet == 0)
            return 0;
         
         if (myBet < MIN_BET)
            System.out.println("Bet is less than the minimum bet of "
               + MIN_BET + "\nTry again");
         else if (myBet > MAX_BET)
            System.out.println("Bet is greater than the maximum bet of "
               + MAX_BET + "\nTry again");
         else
            return myBet;
      }
   }
   
   // simulate a random pull of the slot machine
   public static TripleString pull()
   {
      TripleString pullString = new TripleString();
      
      if (!pullString.setString1(randString()))
         System.out.println("Unable to set string1");
      
      if (!pullString.setString2(randString()))
         System.out.println("Unable to set string2");
      
      if (!pullString.setString3(randString()))
         System.out.println("Unable to set string3");
      
      return pullString;
   }
   
   // Return a random string
   private static String randString()
   {
      // Math.random gives a double between 0.0 and 1.0
      // Multiply that number by 1000 and type cast to int
      // 1000 was chosen as the multiplier since 12.5% becomes 125
      int randInt = (int)(Math.random() * 1000);
      
      // 50% chance of BAR
      if (randInt < 500)
         return "BAR";
      // 25% chance of cherries
      if (randInt < 750)
         return "cherries";
      // 12.5% change of (space)
      if (randInt < 875)
         return "(space)";
      // Remaining 12.5% for 7
      return "7";
   }
   
   // Figure out the multiplier depending on pull results
   public static int getPayMultiplier(TripleString thePull)
   {
      // Check if "cherries" is in the first slot, guaranteed win
      if (thePull.getString1().equals("cherries"))
      {
         // Check for second "cherries", increased winnings
         if (thePull.getString2().equals("cherries"))
         {
            // Check for third
            if (thePull.getString3().equals("cherries"))
               return 30;
            return 15;
         }
         return 5;
      }
      
      // Check for three BARs or 7s 
      if (thePull.getString1().equals("BAR")
         && thePull.getString2().equals("BAR")
         && thePull.getString3().equals("BAR"))
         return 50;
      
      if (thePull.getString1().equals("7")
         && thePull.getString2().equals("7")
         && thePull.getString3().equals("7"))
         return 100;

      // No luck this round
      return 0;
   }
   
   // Show results of pull
   public static void display(TripleString thePull, int winnings )
   {
      System.out.println("whirrrrrr .... and your pull is ...");
      System.out.println(thePull.toString());
      if (winnings > 0)
         System.out.println("congratulations, you win: " + winnings);
      else
         System.out.println("sorry, you lose.");
   }
}

class TripleString
{
   // Static constants for boundaries
   public static final int MAX_LEN = 20;
   public static final int MAX_PULLS = 40;
   
   private String string1;
   private String string2;
   private String string3;
   private static int[] pullWinnings = new int[MAX_PULLS];
   private static int numPulls = 0;
   
   // Default contructor, sets strings to ""
   public TripleString()
   {
      string1 = new String("");
      string2 = new String("");
      string3 = new String("");
   }
   
   // Helper to check valid string
   private boolean validString( String str )
   {
      if ( str != null && str
            .length() < MAX_LEN )
         return true;
      return false;
   }
   
   // Accessors for strings
   public String getString1()
   {
      return string1;
   }
   
   public String getString2()
   {
      return string2;
   }
   
   public String getString3()
   {
      return string3;
   }
   
   // Mutators for strings. Check for valid string and then assign if true
   public boolean setString1( String str )
   {
      if (validString(str))
      {
         string1 = str;
         return true;
      }
      return false;
   }
   
   public boolean setString2( String str )
   {
      if (validString(str))
      {
         string2 = str;
         return true;
      }
      return false;
   }
   
   public boolean setString3( String str )
   {
      if (validString(str))
      {
         string3 = str;
         return true;
      }
      return false;
   }
   
   // Concatenate the strings for output
   public String toString()
   {
      return string1 + " " + string2 + " " + string3;
   }
   
   // Enter the winnings into an array then increment numPulls
   public void saveWinnings( int winnings )
   {
      pullWinnings[numPulls++] = winnings;
   }
   
   // Construct a string to display winnings information to the user
   public String displayWinnings()
   {
      String displayString;
      int totalWinnings = 0;
      displayString = "Thanks for playing at the Casino!\n"
         + "Your total winnings were:\n";
      
      // Loop through numPulls and output the winnings as well as calculate
      // the total winnings
      for (int i = 0; i < numPulls ; i++ )
      {
         displayString += "$" + pullWinnings[i] + " ";
         totalWinnings += pullWinnings[i];
      }

      displayString += "\n" + "Your total winnings were: "
         + "$" + totalWinnings;
      
      return displayString;
   }
   
}

/***************************************************************

How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries 7 cherries
congratulations, you win: 250
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
(space) BAR BAR
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR 7 cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries BAR BAR
congratulations, you win: 250
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries BAR cherries
congratulations, you win: 250
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR BAR BAR
congratulations, you win: 2500
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
(space) 7 cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR cherries BAR
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR cherries cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
7 cherries BAR
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries BAR 7
congratulations, you win: 250
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR cherries cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries (space) cherries
congratulations, you win: 250
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR 7 BAR
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries BAR BAR
congratulations, you win: 250
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries BAR BAR
congratulations, you win: 250
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries cherries cherries
congratulations, you win: 1500
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR BAR BAR
congratulations, you win: 2500
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
7 cherries cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR BAR BAR
congratulations, you win: 2500
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries cherries BAR
congratulations, you win: 750
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR BAR cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR 7 cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR 7 (space)
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR (space) cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR cherries 7
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries BAR BAR
congratulations, you win: 250
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR cherries cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR 7 cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries cherries BAR
congratulations, you win: 750
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR cherries BAR
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
7 7 cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR cherries BAR
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR BAR cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries BAR BAR
congratulations, you win: 250
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries cherries (space)
congratulations, you win: 750
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
cherries BAR (space)
congratulations, you win: 250
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
(space) BAR BAR
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
(space) (space) BAR
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
whirrrrrr .... and your pull is ...
BAR 7 cherries
sorry, you lose.
How much would you like to bet 1 - 100 ) or 0 to quit? 
50
Thanks for playing at the Casino!
Your total winnings were:
$250 $0 $0 $250 $250 $2500 $0 $0 $0 $0 $250 $0 $250 $0 $250 $250 $1500 $2500 $0 $2500 $750 $0 $0 $0 $0 $0 $250 $0 $0 $750 $0 $0 $0 $0 $250 $750 $250 $0 $0 $0 
Your total winnings were: $13750

****************************************************************/

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

   public static final int MIN_BET = 1;
   public static final int MAX_BET = 100;
  // Test Main
   public static void main(String[] args)
   {
      int multiplier, winnings;
      int myBet = getBet();
      TripleString finalPull = new TripleString();

      while (0 != myBet)
      {
         TripleString myPull = pull();
         multiplier = getPayMultiplier(myPull);
         winnings = multiplier * myBet;
         myPull.saveWinnings(winnings);
         display(myPull, winnings);
         
         myBet = getBet();
      }
      System.out.println(finalPull.displayWinnings());
   }
   
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
            System.out.println("Bet is greater than the minimum bet of "
               + MAX_BET + "\nTry again");
         else
            return myBet;
      }
   }
   
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
   
   private static String randString()
   {
      int randInt = (int)(Math.random() * 1000);
      
      if (randInt < 500)
         return "BAR";
      if (randInt < 750)
         return "cherries";
      if (randInt < 875)
         return "(space)";
      return "7";
   }
   
   public static int getPayMultiplier(TripleString thePull)
   {
      if (thePull.getString1().equals("cherries"))
      {
         if (thePull.getString2().equals("cherries"))
         {
            if (thePull.getString3().equals("cherries"))
               return 30;
            return 15;
         }
         return 5;
      }
      
      if (thePull.getString1().equals("BAR")
         && thePull.getString2().equals("BAR")
         && thePull.getString3().equals("BAR"))
         return 50;
      
      if (thePull.getString1().equals("7")
         && thePull.getString2().equals("7")
         && thePull.getString3().equals("7"))
         return 100;

      return 0;
   }
   
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
   public static final int MAX_LEN = 20;
   public static final int MAX_PULLS = 40;
   
   private String string1;
   private String string2;
   private String string3;
   private static int[] pullWinnings = new int[MAX_PULLS];;
   private static int numPulls = 0;
   
   public TripleString()
   {
      string1 = new String("");
      string2 = new String("");
      string3 = new String("");
   }
   
   private boolean validString( String str )
   {
      if ( str != null && str
            .length() < MAX_LEN )
         return true;
      return false;
   }
   
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
   
   public String toString()
   {
      return string1 + " " + string2 + " " + string3;
   }
   
   public void saveWinnings( int winnings )
   {
      pullWinnings[numPulls++] = winnings;
   }
   
   public String displayWinnings()
   {
      String displayString;
      int totalWinnings = 0;
      displayString = "Thanks for playing at the Casino!\n"
         + "Your total winnings were:\n";
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
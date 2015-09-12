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
   public static void main(String[] args)
   {
      
   }
   
   public static int getBet()
   {
      return 1;
   }
   
   public static TripleString pull()
   {
      TripleString pullString = new TripleString();
      pullString.setString1(randString());
      pullString.setString2(randString());
      pullString.setString3(randString());
      return pullString;
   }
   
   private static String randString()
   {
      int randInt = (int)Math.random() * 1000;
      
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
      return 2;
   }
   
   
   
   public static void display(TripleString thePull, int winnings )
   {
      // Nothing to return
   }
   
}

class TripleString
{
   public static final int MAX_LEN = 20;
   public static final int MAX_PULLS = 40;
   
   private String string1;
   private String string2;
   private String string3;
   private static int[] pullWinnings;
   private static int numPulls;
   
   public TripleString()
   {
      string1 = new String("");
      string2 = new String("");
      string3 = new String("");
      pullWinnings = new int[MAX_PULLS];
   }
   
   private boolean validString( String str )
   {
      if ( str != null && str.length() < MAX_LEN )
         return true;
      return false;
   }
   
   public boolean getString1( String str )
   {
      return true;
   }
   
   public boolean getString2( String str )
   {
      return true;
   }
   
   public boolean getString3( String str )
   {
      return true;
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
   
   public void saveWinnings(int winnings)
   {
      pullWinnings[numPulls] = winnings;
   }
   
   public void displayWinnings()
   {
      for (int i = 0; i < MAX_PULLS ; i++ )
         System.out.print(pullWinnings[i]);
      System.out.println();
   }
   
   
   

}
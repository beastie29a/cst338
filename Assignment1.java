/*
   Robert Contreras
   CST 338 - Fall 2015 Session A
   Assignment 1, Part 1 and 2
*/

// Import the Scanner Class for keyboard input
import java.util.Scanner;
// Import DecimalFormat to format numbers
import java.text.DecimalFormat;

public class Assignment1
{
   // Declare the constants
   public static final int MIN_HOURS = 12;
   public static final int MAX_HOURS = 20;

   public static void main(String[] args)
   {
      // Setup variables and Scanner for Part 1
      String fullName;
      int length;
      Scanner keyboard = new Scanner(System.in);

      // Setup DecimalFormat class for Part 2
      DecimalFormat formattingObject = new DecimalFormat("#0.0");
      
      System.out.println("Begin Part 1\n");

      // Prompt the user for input then Get input from the user
      System.out.println("Enter your First name followed by your Last name");
      System.out.println("Capitalize the first initial of each: ");
      String firstName = keyboard.nextLine(); 
      String lastName = keyboard.nextLine(); 

      // Concatenate the first and last name
      fullName = firstName + " " + lastName;
      length = fullName.length();

      // Print out information to the screen
      System.out.println("Your Full Name is: " + fullName);
      System.out.println("The length is: " + length + " Characters");
      System.out.println("Your Full Name in upper case: "
         + fullName.toUpperCase());
      System.out.println("Your Full Name in lower case: "
         + fullName.toLowerCase());
      
      System.out.println("\nBegin Part 2\n");

      // Print out information to the screen
      System.out.println("The minimum amount of time you should spend in");
      System.out.println("this class is between " + MIN_HOURS + " and " + 
         MAX_HOURS + " hours.");

      // Prompt the user for input
      System.out.println("Enter the amount of time you spent "
         + "to three decimal places: ");
      Double studyHours = keyboard.nextDouble();
      
      System.out.println(formattingObject.format(studyHours));
      
      // Close the keyboard resource
      if(keyboard != null)
      {
         keyboard.close();
      }
   }
}

/* *******************************************************
********* First Run **********
Begin Part 1

Enter your First name followed by your Last name
Capitalize the first initial of each: 
Robert
Contreras
Your Full Name is: Robert Contreras
The length is: 16 Characters
Your Full Name in upper case: ROBERT CONTRERAS
Your Full Name in lower case: robert contreras

Begin Part 2

The minimum amount of time you should spend in
this class is between 12 and 20 hours.
Enter the amount of time you spent to three decimal places: 
23.231
23.2

********* Second Run **********
Begin Part 1

Enter your First name followed by your Last name
Capitalize the first initial of each: 
George
Washington
Your Full Name is: George Washington
The length is: 17 Characters
Your Full Name in upper case: GEORGE WASHINGTON
Your Full Name in lower case: george washington

Begin Part 2

The minimum amount of time you should spend in
this class is between 12 and 20 hours.
Enter the amount of time you spent to three decimal places: 
17.981
18.0


******************************************************** */
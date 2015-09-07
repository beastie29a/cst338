/*
   Robert Contreras
   CST 338 - Fall 2015 Session A
   Assignment 1, Part 1 and 2
*/
/*
// Import the Scanner Class for keyboard input
import java.util.Scanner;

public class Assignment1
{
   public static void main(String[] args)
   {
      String fullName;
      int length;
      
      // Create a Scanner class
      Scanner keyboard = new Scanner(System.in);

      // Prompt the user for input then Get input from the user
      System.out.println("Enter your First name followed by your Last name");
      System.out.println("Capitalize the first initial of each: ");
      String firstName = keyboard.nextLine(); 
      String lastName = keyboard.nextLine(); 

      // Concatenate the first and last name
      fullName = firstName + " " + lastName;
      length = firstName.length() + lastName.length();

      // Print out information to the screen
      System.out.println("Your Full Name is: " + fullName);
      System.out.println("The length is: " + length + " Characters");
      System.out.println("Your Full Name in upper case: "
         + fullName.toUpperCase());
      System.out.println("Your Full Name in lower case: "
         + fullName.toLowerCase());
      
      // Close the keyboard resource
      if(keyboard != null)
      {
         keyboard.close();
      }
   }
}
*/

import java.util.Scanner;
import java.text.DecimalFormat;

public class Assignment1
{
   // Declasre the constants
   public static final int MIN_HOURS = 12;
   public static final int MAX_HOURS = 20;

   public static void main(String[] args)
   {
      // Setup Scanner and DecimalFormat classes
      Scanner keyboard = new Scanner(System.in);
      DecimalFormat formattingObject = new DecimalFormat("#0.0");

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

/*
   *************** Part 1 Output ******************** 

Enter your First and Last name
Capitalize the first initial of each: 
Robert
Contreras
Your Full Name is: Robert Contreras
The length is: 15 Characters
Your Full Name in upper case: ROBERT CONTRERAS
Your Full Name in lower case: robert contreras

   *************** Part 2 Output ********************

The minimum amount of time you should spend in
this class is between 12 and 20 hours.
Enter the amount of time you spent to three decimal places: 22.321
22.3

*/
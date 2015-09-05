/*
import java.util.Scanner;

public class Assignment1
{
   public static void main(String[] args)
   {
      String fullName;
      int length;
      
      Scanner keyboard = new Scanner(System.in);

      System.out.print("Enter your First Name: ");
      String firstName = keyboard.nextLine(); 
      System.out.print("Enter your Last Name: ");
      String lastName = keyboard.nextLine(); 

      fullName = firstName + " " + lastName;
      length = firstName.length() + lastName.length();

      System.out.println("Your Full Name is: " + fullName);
      System.out.println("The length is: " + length + " Characters");

      System.out.println("Your Full Name in upper case: "
                         + fullName.toUpperCase());

      System.out.println("Your Full Name in lower case: "
                         + fullName.toLowerCase());
      
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
   public static final int MIN_HOURS = 12;
   public static final int MAX_HOURS = 20;

   public static void main(String[] args)
   {
      Scanner keyboard = new Scanner(System.in);
      DecimalFormat formattingObject = new DecimalFormat("#0.0");

      System.out.println("The minimum amount of time you should spend in");
      System.out.println("this class is between " + MIN_HOURS + " and " + 
            MAX_HOURS + " hours.");
      System.out.print("Enter the amount of time you spent " +
            "to three decimal places: ");
      Double studyHours = keyboard.nextDouble();
      
      System.out.println(formattingObject.format(studyHours));
      
      if(keyboard != null)
      {
         keyboard.close();
      }
   }
}
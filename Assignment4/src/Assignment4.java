/**
   CST 338 - Fall 2015 Session A
   Assignment 4, Optical Barcode Readers and Writers
   @author Robert Contreras
   @author Ryan Doherty
   @author Hyo Lee
*/
import java.util.*;

public class Assignment4
{

   public static void main(String[] args)
   {

      Scanner keyboard = new Scanner(System.in);
      keyboard.close();
      testBarcodeImage();

      System.out.println();
   }


   public static void testBarcodeImage(){

      String[] testCode = new String[1950];
      for (int x=0;x<1950;x++){
         if (x%2==0){
            testCode[x]=" ";
         } else {
            testCode[x]="*";
         }
      }

      for (int x=0;x<1950;x++){
         System.out.print(testCode[x]);
      }

      System.out.println();
      System.out.println();

      BarcodeImage stringBarcodeImage =  new BarcodeImage(testCode);
      stringBarcodeImage.displayToConsole();

      System.out.println();
      System.out.println();
      BarcodeImage cloneBarcodeImage = stringBarcodeImage.clone();
      cloneBarcodeImage.displayToConsole();
   }

}
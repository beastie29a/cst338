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
      sampleMain();
      System.out.println();
      System.out.println();
      //testBarcodeImage();

      System.out.println();
   }

   public static void sampleMain()
   {
      String[] sImageIn =
      {
      "                                               ",
      "                                               ",
      "                                               ",
      "     * * * * * * * * * * * * * * * * * * * * * ",
      "     *                                       * ",
      "     ****** **** ****** ******* ** *** *****   ",
      "     *     *    ****************************** ",
      "     * **    * *        **  *    * * *   *     ",
      "     *   *    *  *****    *   * *   *  **  *** ",
      "     *  **     * *** **   **  *    **  ***  *  ",
      "     ***  * **   **  *   ****    *  *  ** * ** ",
      "     *****  ***  *  * *   ** ** **  *   * *    ",
      "     ***************************************** ",
      "                                               ",
      "                                               ",
      "                                               "

      };



      String[] sImageIn_2 =
      {
      "                                          ",
      "                                          ",
      "* * * * * * * * * * * * * * * * * * *     ",
      "*                                    *    ",
      "**** *** **   ***** ****   *********      ",
      "* ************ ************ **********    ",
      "** *      *    *  * * *         * *       ",
      "***   *  *           * **    *      **    ",
      "* ** * *  *   * * * **  *   ***   ***     ",
      "* *           **    *****  *   **   **    ",
      "****  *  * *  * **  ** *   ** *  * *      ",
      "**************************************    ",
      "                                          ",
      "                                          ",
      "                                          ",
      "                                          "

      };

      BarcodeImage bc = new BarcodeImage(sImageIn);
      DataMatrix dm = new DataMatrix(bc);

      // First secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();

      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();

      // create your own message
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayRawImage();
      dm.displayImageToConsole();

      DataMatrix dmTest = new DataMatrix("Testing the DataMatrix( text ) " +
         "constructor.");
      dmTest.displayTextToConsole();
      dmTest.generateImageFromText();
      dmTest.displayRawImage();
      dmTest.displayImageToConsole();

   }

   public static void testBarcodeImage(){

	   
      String[] sImageIn =
          {
          "                                               ",
          "                                               ",
          "                                               ",
          "     * * * * * * * * * * * * * * * * * * * * * ",
          "     *                                       * ",
          "     ****** **** ****** ******* ** *** *****   ",
          "     *     *    ****************************** ",
          "     * **    * *        **  *    * * *   *     ",
          "     *   *    *  *****    *   * *   *  **  *** ",
          "     *  **     * *** **   **  *    **  ***  *  ",
          "     ***  * **   **  *   ****    *  *  ** * ** ",
          "     *****  ***  *  * *   ** ** **  *   * *    ",
          "     ***************************************** ",
          "                                               ",
          "                                               ",
          "                                               "

          };
      
      System.out.println();
      System.out.println();

      BarcodeImage stringBarcodeImage =  new BarcodeImage(sImageIn);
      stringBarcodeImage.displayToConsole();

      System.out.println();
     // System.out.println();
     // BarcodeImage cloneBarcodeImage = stringBarcodeImage.clone();
      //cloneBarcodeImage.displayToConsole();
   }

}

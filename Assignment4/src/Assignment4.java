/**
 * CST 338 - Fall 2015 Session A
 * Assignment 4, Optical Barcode Readers and Writers
 *
 * @author Robert Contreras
 * @author Ryan Doherty
 * @author Hyo Lee
 */

<<<<<<< HEAD

=======
>>>>>>> master
public class Assignment4
{
   public static void main(String[] args)
   {
      // Main tests of DataMatrix
      sampleMain();
      System.out.println();
      System.out.println();
      // Test for the BarcodeImage Class
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
      System.out.println();

      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      System.out.println();

      // create your own message
      dm.readText("What a great resume builder this is!");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
      //dm.displayRawImage();
      System.out.println();

      DataMatrix dmTest = new DataMatrix("Testing the DataMatrix( text ) " +
            "constructor.");
      dmTest.displayTextToConsole();
      dmTest.generateImageFromText();
      dmTest.displayImageToConsole();
      //dmTest.displayRawImage();
   }

   public static void testBarcodeImage()
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

      System.out.println("Test BarcodeImage Class");

      BarcodeImage stringBarcodeImage = new BarcodeImage(sImageIn);
      stringBarcodeImage.displayToConsole();

      System.out.println();
   }
}

interface BarcodeIO
{
   public boolean scan(BarcodeImage bc);

   public boolean readText(String text);

   public boolean generateImageFromText();

   public boolean translateImageToText();

   public void displayTextToConsole();

   public void displayImageToConsole();
}

class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   public static final int MAX_LENGTH = MAX_HEIGHT * MAX_WIDTH;

   private boolean[][] image_data;

   public BarcodeImage()
   {
      //initialize 2d array - all elements default to false
      image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
   }

   public BarcodeImage(String[] str_data)
   {
      //initialize 2d array - all elements default to false
      image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];

      //find the top row of the code
      int topRow = 0;
      for (int x = 0; x < str_data.length - 1; x++)
      {
         if ((str_data[x]).trim().equals(""))
         {
            topRow++;
         }
         else
         {
            break;
         }
      }

      //find the bottom row of the code
      int bottomUp = 0;
      for (int x = str_data.length - 1; x > 0; x--)
      {
         if ((str_data[x]).trim().equals(""))
         {
            bottomUp++;
         }
         else
         {
            break;
         }
      }

      int bottomRow = (str_data.length - 1) - bottomUp;

      //write the code into the image_data array
      //from bottom left corner and up
      for (int x = bottomRow; x >= topRow; x--)
      {
         String temp = str_data[x].trim();
         for (int k = 0; k < temp.length(); k++)
         {
            if (temp.charAt(k) == '*')
            {
               setPixel(((MAX_HEIGHT - 1) - (bottomRow - x)), k, true);
            }
         }
      }
   }

   // Accessor for row and column of image
   public boolean getPixel(int row, int col)
   {

      if (isValid(row, col))
      {
         return image_data[row][col];
      }
      return false;

   }

   // Mutator for row and column of image
   public boolean setPixel(int row, int col, boolean value)
   {
      if (isValid(row, col))
      {
         image_data[row][col] = value;
         return true;
      }
      return false;
   }

   // Validation of array size
   private boolean isValid(int row, int col)
   {
      if ((row >= 0 && row < MAX_HEIGHT) && (col >= 0 && col < MAX_WIDTH))
      {
         return true;
      }
      return false;
   }

   // Validation of String size
   private boolean checkSize(String[] data)
   {
      if (data.length <= MAX_LENGTH && data.length >= 0)
      {
         return true;
      }
      return false;
   }

   // Debugging check
   public void displayToConsole()
   {
      for (int x = 0; x < image_data.length; x++)
      {
         for (int i = 0; i < image_data[x].length; i++)
         {
            if (this.getPixel(x, i))
            {
               System.out.print("*");
            }
            else
            {
               System.out.print(" ");
            }
         }
         System.out.println();
      }
   }

   // Override BarcodeImage
   public BarcodeImage clone() throws CloneNotSupportedException
   {
      BarcodeImage clonedBarcodeImage = new BarcodeImage();
      for (int x = 0; x < image_data.length; x++)
      {
         for (int i = 0; i < image_data[x].length; i++)
         {
            clonedBarcodeImage.setPixel(x, i, getPixel(x, i));
         }
      }

      return clonedBarcodeImage;
   }
}

class DataMatrix implements BarcodeIO
{
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   // Amount of digits in the binary representation of ASCII
   public static final int ASCII_BINARY_DIGITS = 8;
   private BarcodeImage image;
   private String text;
   private int actualWidth, actualHeight;

   // Default constructor to setup initial empty image
   public DataMatrix()
   {
      this.image = new BarcodeImage();
      actualWidth = actualHeight = 0;
      readText("undefined");
   }

   // Set up for scan and move image to the lower left corner
   public DataMatrix(BarcodeImage image)
   {
      if (scan(image)) ;
   }

   // Set up to read text string from mutator
   public DataMatrix(String text)
   {
      if (readText(text)) ;
   }

   // Mutator to feed constructor for string
   public boolean readText(String text)
   {
      if (text.length() < image.MAX_WIDTH)
      {
         this.text = text;
         this.actualWidth = text.length() - 2;
         return true;
      }
      return false;
   }

   // Mutator to feed image constructor, clone image, set actual dimensions,
   // and feed scan in constructor to move image to lower left corner
   public boolean scan(BarcodeImage image)
   {
      try
      {
         this.image = image.clone();
      } catch (CloneNotSupportedException exception)
      {
         return false;
      }

      cleanImage();
      this.actualWidth = this.computeSignalWidth();
      this.actualHeight = this.computeSignalHeight();
      return true;
   }

   // Accessors
   public int getActualWidth()
   {
      return this.actualWidth;
   }

   public int getActualHeight()
   {
      return this.actualHeight;
   }

   // Account for spines for actual dimensions
   private int computeSignalWidth()
   {

      int columnWidth = 0;

      while (image.getPixel((image.MAX_HEIGHT - 1), columnWidth++))
      {
      }
      // Subtract the last while loop condition
      return columnWidth - 1;
   }

   private int computeSignalHeight()
   {
      int signalHeight = 0;

      while (image.getPixel((image.MAX_HEIGHT - ++signalHeight), 0))
      {
      }

      // Subtract the last while loop condition
      return signalHeight - 1;
   }

   // Create image, Closed Limitation lines and alternating borders
   public boolean generateImageFromText()
   {
      int textLength = text.length();

      this.image = new BarcodeImage();
      // Add 2 to textLength for the left/right border
      this.actualWidth = textLength + 2;
      // Add 2 to ASCII binary digit fields for the top/bottom border
      this.actualHeight = ASCII_BINARY_DIGITS + 2;

      //Output Bottom spine (Bottom Closed Limitation Line) &
      //Output Top alternating black-white pattern border
      for (int i = 0; i < actualWidth; i++)
      {
         image.setPixel(actualHeight - 1, i, true);
         if (i % 2 == 0)
            image.setPixel(actualHeight - 10, i, true);
         // Begin to populate chars ro ASCII, actualWidth - 2 borders
         if (i > 0 && i <= textLength)
         {
            if (!WriteCharToCol(i, (int) text.charAt(i - 1)))
               return false;
         }
      }

      //Output Left spine (Left Closed Limitation Line) &
      //Output Right alternating black-white pattern border
      for (int i = 0; i < actualHeight; i++)
      {
         // Subtract 1 to account for array offsets
         image.setPixel((actualHeight - 1) - i, 0, true);
         if (i % 2 == 0)
            image.setPixel((actualHeight - 1) - i, (actualWidth - 1), true);
      }

      cleanImage();
      return true;
   }

   public boolean translateImageToText()
   {
      this.text = "";

      // Make sure the values are within range, include spine
      if (actualHeight > ASCII_BINARY_DIGITS + 2)
         return false;

      // Iterate through each column, concatenating the string with chars
      for (int i = 1; i < actualWidth - 1; i++)
         this.text += readCharFromCol(i);

      return true;
   }

   public void displayTextToConsole()
   {
      System.out.println("|*" + text + "*|");
   }

   // Assumes being called from a cleanImage() object
   public void displayImageToConsole()
   {
      int col;

      // Print top outline, 2 longer than the Barcode
      for (int i = 0; i < actualWidth + 2; i++)
         System.out.print("-");

      System.out.println();

      for (int i = 0; i < image.MAX_HEIGHT; i++)
      {
         // position self at left side of array
         col = 0;
         // Search for the left spine
         if (image.getPixel(i, 0))
         {
            // Begin left outline
            System.out.print("|");
            // Output to the console, row-by-row
            for (; col < actualWidth; col++)
            {
               if (image.getPixel(i, col))
                  System.out.print(BLACK_CHAR);
               else
                  System.out.print(WHITE_CHAR);
            }
            // Right outline
            System.out.println("|");

         }
      }
   }

   // Move image to lower left with helper methods
   private void cleanImage()
   {
      moveImageToLowerLeft();
   }

   private void moveImageToLowerLeft()
   {
      int firstRow = 0;
      int firstCol = 0;
      int lastRow = 0;
      int lastCol = 0;

      outerloop:
      for (int row = 0; row < image.MAX_HEIGHT; row++)
      {
         for (int col = 0; col < image.MAX_WIDTH; col++)
         {
            if (image.getPixel(row, col))
            {
               firstRow = row;
               firstCol = col;
               break outerloop;
            }

         }
      }

      if (image.getPixel(image.MAX_HEIGHT - 1, firstCol))
      {
         lastRow = image.MAX_HEIGHT - 1;
      }
      else
      {
         outerloop2:
         for (int i = firstRow; i < image.MAX_HEIGHT; i++)
         {
            if (!image.getPixel(i, firstCol))
            {
               lastRow = i;
               break outerloop2;
            }
         }
      }

      outerloop3:
      for (int i = firstCol; i < image.MAX_WIDTH; i++)
      {
         if (!image.getPixel(lastRow, i))
         {
            lastCol = i;
            break outerloop3;
         }
      }

      if (lastRow != image.MAX_HEIGHT - 1)
      {
         shiftImageDown(lastRow);
      }

      if (firstCol != 0)
      {
         shiftImageLeft(firstCol);
      }
   }

   private void shiftImageDown(int offset)
   {
      int currentRow = image.MAX_HEIGHT;
      for (int row = offset; row >= 0; row--)
      {
         for (int col = 0; col < image.MAX_WIDTH; col++)
         {
            image.setPixel(currentRow, col, image.getPixel(row, col));
            image.setPixel(row, col, false);
         }
         currentRow--;
      }
   }

   private void shiftImageLeft(int offset)
   {
      int currentCol = offset;
      for (int row = 0; row < image.MAX_HEIGHT; row++)
      {
         for (int col = currentCol; col > image.MAX_WIDTH; col++)
         {
            image.setPixel(row, currentCol - col, image.getPixel(row, col));
            image.setPixel(row, col, false);
         }
      }
   }

   // Helper method to translate image to text
   private char readCharFromCol(int col)
   {
      // Initially position row at the ones row
      int row = image.MAX_HEIGHT - 2;
      // Int to total char decimal values
      int charDec = 0;

      // Check is array values are true and calculate the value
      for (int exponent = 0; exponent < actualHeight - 2; exponent++)
         if (image.getPixel(row--, col))
            charDec += Math.pow(2, exponent);

      return (char) charDec;
   }

   // Writes a character to a column in ASCII format
   private boolean WriteCharToCol(int col, int code)
   {
      // Make sure the column is withing our actual image width
      if (col > actualWidth || col < 0)
         return false;

      // Make the sure code is in the extended ASCII table
      if (code > 255 || code < 0)
         return false;

      // Covert the (int) char into a binary string
      String binaryString = Integer.toBinaryString(code);
      int stringLength = binaryString.length();

      // Fix any strings that are too short
      while (stringLength < ASCII_BINARY_DIGITS)
      {
         binaryString = "0" + binaryString;
         stringLength++;
      }

      // Populate the matrix from bottom -> up
      for (int row = stringLength; row > 0; row--)
      {
         if (binaryString.charAt(row - 1) == '1')
            image.setPixel(row, col, true);
      }
      return true;
   }

   public void displayRawImage()
   {
      image.displayToConsole();
   }

   private void clearImage()
   {
      this.image = new BarcodeImage();
   }
}

/********************** Test Run ***********************

|*CSUMB CSIT online program is top notch.*|
-------------------------------------------
|* * * * * * * * * * * * * * * * * * * * *|
|*                                       *|
|****** **** ****** ******* ** *** *****  |
|*     *    ******************************|
|* **    * *        **  *    * * *   *    |
|*   *    *  *****    *   * *   *  **  ***|
|*  **     * *** **   **  *    **  ***  * |
|***  * **   **  *   ****    *  *  ** * **|
|*****  ***  *  * *   ** ** **  *   * *   |
|*****************************************|

|*You did it!  Great work.  Celebrate.*|
----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|**** *** **   ***** ****   *********  |
|* ************ ************ **********|
|** *      *    *  * * *         * *   |
|***   *  *           * **    *      **|
|* ** * *  *   * * * **  *   ***   *** |
|* *           **    *****  *   **   **|
|****  *  * *  * **  ** *   ** *  * *  |
|**************************************|

|*What a great resume builder this is!*|
----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|***** * ***** ****** ******* **** **  |
|* ************************************|
|**  *    *  * * **    *    * *  *  *  |
|* *               *    **     **  *  *|
|**  *   * * *  * ***  * ***  *        |
|**      **    * *    *     *    *  * *|
|** *  * * **   *****  **  *    ** *** |
|**************************************|

|*Testing the DataMatrix( text ) constructor.*|
-----------------------------------------------
|* * * * * * * * * * * * * * * * * * * * * * *|
|*                                           *|
|******** *** **********  ****   ***********  |
|* *********** *** ***************************|
|** **    *     *   ** *  * **      **** * *  |
|*    **   *      *   ***   *  *  **      * **|
|*** * ** * * * * * *     ** *    ** * * ** * |
|*  *  **            *           **** * * ****|
|* ** * *   *  * ***  *    *   * ** *  ** *   |
|*********************************************|

*********************************************************/

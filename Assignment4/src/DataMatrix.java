
public class DataMatrix implements BarcodeIO
{

   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   // Amount of digits in the binary representation of ASCII
   public static final int ASCII_BINARY_DIGITS = 7;
   private BarcodeImage image;
   private String text;
   private int actualWidth, actualHeight;

   public DataMatrix()
   {
      this.image = new BarcodeImage();
      actualWidth = actualHeight = 0;
      readText("undefined");
   }

   public DataMatrix(BarcodeImage image)
   {
      if (scan(image));
   }

   public DataMatrix(String text)
   {
      if (readText(text));
   }

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

   public boolean scan(BarcodeImage image)
   {
      try
      {
         this.image = image.clone();
      }
      catch (CloneNotSupportedException exception)
      {
        // Does nothing
      }

      this.actualWidth = this.computeSignalWidth();
      this.actualHeight = this.computeSignalHeight();
//      cleanImage();
      return true;
   }

   public int getActualWidth()
   {
      return actualWidth;
   }

   public int getActualHeight()
   {
      return actualHeight;
   }

   private int computeSignalWidth()
   {

      int columnWidth = 0;

      while (image.getPixel((image.MAX_HEIGHT - 1), columnWidth++)){}
      // Subtract left and right edge and last while loop condition
      return columnWidth - 3;
   }

   private int computeSignalHeight()
   {
      int signalHeight = 0;

      while (image.getPixel((image.MAX_HEIGHT - ++signalHeight),0)){}

      // Subtract top and bottom edge and last while loop condition
      return signalHeight - 3;
   }

   public boolean generateImageFromText()
   {
      this.image = new BarcodeImage();
      this.actualWidth = text.length();
      this.actualHeight = ASCII_BINARY_DIGITS;
/*
      // First create the border - at the top left of array [0][0]
      for ( int i = 0 ; i < actualWidth ; i++)
      {
         for (int j = ASCII_BINARY_DIGITS; j >= 0; j++)
         {
            if (0 == j)
               image.setPixel(i, j, true);
            else if ( actualWidth == j && ())
         }
      }

*/

      // Iterate through each column assigning the character
      for ( int i = 0 ; i < actualWidth ; i++)
      {
         if (!WriteCharToCol(i, (int) text.charAt(i)))
            return false;
      }
      return true;
   }

   public boolean translateImageToText()
   {
      this.text = "";
      // Make sure image is within ASCII range

      if (actualHeight > 8)
         return false;
      // Iterate through each column, concatenating the string with chars
      for ( int i = 1 ; i < actualWidth ; i++)
         this.text += readCharFromCol(i);

      return true;
   }

   public void displayTextToConsole()
   {
      System.out.println(text);
   }

   public void displayImageToConsole()
   {
      int col;
      // Get real signal width and add 2 for the left and right
      int endCol = getActualWidth() + 2;

      for ( int i = 0 ; i < endCol + 2; i++)
         System.out.print("-");

      System.out.println();

      for ( int i = 0 ; i < image.MAX_HEIGHT ; i++)
      {
         col = 0;
         if (image.getPixel(i, 0))
         {
            System.out.print("|");
            for (; col < endCol; col++)
            {
               if (image.getPixel(i, col))
                  System.out.print(BLACK_CHAR);
               else
                  System.out.print(WHITE_CHAR);
            }
            System.out.println("|");

         }
      }
   }

   private void cleanImage()
   {
      // Something here
   }

   private void moveImageToLowerLeft()
   {
      // Something here
   }

   private void shiftImageDown(int offset)
   {
      // Something here
   }
   private void shiftImageLeft(int offset)
   {
      // Something here
   }

   private char readCharFromCol(int col)
   {
      int row = image.MAX_HEIGHT - 2;
      int charDec = 0;

      // Iterate through the column and add
      for ( int i = 0; i < getActualHeight() ; i++)
         if (image.getPixel(row--,col))
            charDec += Math.pow(2,i);

      return (char) charDec;
   }

   // Writes a character to a column in ASCII format
   private boolean WriteCharToCol(int col, int code)
   {
      // Make sure the column is withing our actual image width
      if (col > getActualWidth() || col < 0)
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
      for ( int row = stringLength ; row > 0 ; row-- )
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

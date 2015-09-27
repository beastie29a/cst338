
public class DataMatrix implements BarcodeIO
{

   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
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
      if (scan(image))
          System.out.print("Worked!");
   }

   public DataMatrix(String text)
   {
      if (readText(text))
         System.out.print("Worked!");
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
      // Iterate through each colum assigning the character
      for ( int i = 0 ; i < text.length() ; i++)
      {
         // Type cast char to in to get ASCII decimal represenation
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

      // Populate the matrix from bottom -> up
      for ( int row = binaryString.length() ; row >= 0 ; row-- )
      {
         if (binaryString.charAt(row) == '1')
            image.setPixel(row, col, true);
      }
      return true;
   }

   public void displayRawImage()
   {
      // Something here
   }

   private void clearImage()
   {
      // Something here
   }

}

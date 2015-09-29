import javax.management.BadAttributeValueExpException;
import java.security.InvalidParameterException;

public class DataMatrix implements BarcodeIO
{

   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   // Amount of digits in the binary representation of ASCII
   public static final int ASCII_BINARY_DIGITS = 8;
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
        return false;
      }

      //      cleanImage();
      this.actualWidth = this.computeSignalWidth();
      this.actualHeight = this.computeSignalHeight();
      return true;
   }

   public int getActualWidth()
   {
      return this.actualWidth;
   }

   public int getActualHeight()
   {
      return this.actualHeight;
   }

   private int computeSignalWidth()
   {

      int columnWidth = 0;

      while (image.getPixel((image.MAX_HEIGHT - 1), columnWidth++)){}
      // Subtract the last while loop condition
      return columnWidth - 1;
   }

   private int computeSignalHeight()
   {
      int signalHeight = 0;

      while (image.getPixel((image.MAX_HEIGHT - ++signalHeight),0)){}

      // Subtract the last while loop condition
      return signalHeight - 1;
   }

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
      for (int i = 0; i <= actualWidth; i++)
      {
         image.setPixel(actualHeight - 1 , i, true);
         if (i % 2 == 0)
            image.setPixel(actualHeight - 10, i, true);
         // Begin to populate chars ro ASCII, actualWidth - 2 borders
         if ( i > 0  && i <= textLength)
         {
            if (!WriteCharToCol(i, (int) text.charAt(i - 1)))
               return false;
         }
      }

      //Output Left spine (Left Closed Limitation Line) &
      //Output Right alternating black-white pattern border
      for (int i = 0; i < actualHeight ; i++)
      {
         // Subtract 1 to account for array offsets
         image.setPixel((actualHeight - 1) - i, 0, true);
         if (i % 2 == 0)
            image.setPixel((actualHeight - 1) - i, (actualWidth - 1), true);
      }

      //this.actualHeight = this.computeSignalHeight();
      return true;
   }

   public boolean translateImageToText()
   {
      this.text = "";

      // Make sure the values are within range, include spine
      if (actualHeight > ASCII_BINARY_DIGITS + 2)
         return false;

      // Iterate through each column, concatenating the string with chars
      for ( int i = 1 ; i < actualWidth - 1 ; i++)
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
      for ( int i = 0 ; i < actualWidth + 2; i++)
         System.out.print("-");

      System.out.println();

      for ( int i = 0 ; i < image.MAX_HEIGHT ; i++)
      {
         // position self at left side of array
         col = 0;
         // Search for the left spine
         if (image.getPixel(i, 0))
         {
            // Begin left outline
            System.out.print("|");
            // Output to the console, row-by-row
            for (; col < actualWidth ; col++)
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

   private void cleanImage()
   {
      // Something here
       //find the top row of the code
	   /*
       int topRow=0;
       for (int x=0;x<str_data.length-1;x++){
       	if ((str_data[x]).trim().equals("")){
       		topRow++;
       	}else{
       		break;
       	}
       }

       //find the bottom row of the code
       int bottomUp=0;
       for (int x=str_data.length-1;x>0;x--){
       	if ((str_data[x]).trim().equals("")){
       		bottomUp++;
       	}else{
       		break;
       	}
       }
       
       int bottomRow=(str_data.length-1)-bottomUp;
       
       //write the code into the image_data array
       //from bottom left corner and up
       for (int x=bottomRow;x>=topRow;x--){
       	String temp=str_data[x].trim();
       	for (int k=0;k<temp.length();k++){
       		if (temp.charAt(k) ==  '*'){
       			setPixel(((MAX_HEIGHT-1)-(bottomRow-x)),k,true);
       		}
       	}
       }
	   */
	   
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
      // Initially position row at the ones row
      int row = image.MAX_HEIGHT - 2;
      // Int to total char decimal values
      int charDec = 0;

      // Check is array values are true and calculate the value
      for ( int exponent = 0 ; exponent < actualHeight - 2 ; exponent++)
         if (image.getPixel(row--,col))
            charDec += Math.pow(2,exponent);

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

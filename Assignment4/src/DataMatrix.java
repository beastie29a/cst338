
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
         return true;
      }
      return false;
   }

   public boolean scan(BarcodeImage image)
   {
      //this.image = super.clone(image);
      this.actualWidth = 0;
      this.actualHeight = 0;
      cleanImage();
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
      return 1;
   }

   private int computeSignalHeight()
   {
       return 1;
   }

   public boolean generateImageFromText()
   {
      return true;
   }

   public boolean translateImageToText()
   {
      return true;
   }

   public void displayTextToConsole()
   {
      // Something here
   }

   public void displayImageToConsole()
   {
      // Something here
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
      return 'c';
   }

   private boolean WriteCharToCol(int col, int code)
   {
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
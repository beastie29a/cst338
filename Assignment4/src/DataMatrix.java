

public class datamatrix implements barcodeio
{

   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   private BarcodeImage image;
   private String text;
   private int actualWidth, actualHeight;

   public DataMatrix()
   {
      actualwidth = actualheight = 0;
      readtext("undefined");
   }

   public DataMatrix(BarcodeImage image)
   {
      if (scan(image))
          System.out.print("Worked!");
   }

   public DataMatrix(String text)
   {
      if (readtext(text))
         System.out.print("Worked!");
   }

   public boolean readtext(String text)
   {
      this.text = text;
   }

   public boolean scan(BarcodeImage image)
   {
      this.image = super.clone(image);
      this.actualHeight = 0;
      this.actualHeight = 0;
   }

}
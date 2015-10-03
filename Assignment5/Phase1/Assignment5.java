import javax.swing.*;
import java.awt.*;

/**
 * Created by Me on 10/2/2015.
 */


public class Assignment5
{
   // Static for 57 icons and their corresponding labels
   // normally we would not have a separate label for each card, but
   // if we want to display all at once using labels, we need to.

   static final int NUM_CARD_IMAGES = 57; // 52+4 jokers + 1 back-of-card image
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];

   static void loadCardIcons()
   {
      // Build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.
      // in a SHORT loop. For each file name, read it in and use it to
      // instantiate each of the 57 Icons in the icon[] array.

      short index = 0;
      for (short j = 0; j < 4; j++)
      {
         for (short k = 0; k < 14; k++)
         {
            icon[index++] = new ImageIcon("images/" + turnIntIntoCardValue(k)
               + turnIntIntoCardSuit(j) + ".gif");
         }
      }
      icon[index++] = new ImageIcon("images/BK.gif");
   }

   // Turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      String standardCardValue;
      String[] imageFileCardValue = {"A", "2", "3", "4", "5", "6", "7", "8",
         "9", "T", "J", "Q", "K", "X"};

      if (k < 0 || k > 13)
      {
         return "[ INVALID ]";
      }
      standardCardValue = imageFileCardValue[k];
      return standardCardValue;
   }

   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      String standardCardSuit;
      String[] imageFileCardSuit = {"C", "D", "H", "S"};

      if (j < 0 || j > 3)
      {
         return "[ INVALID ]";
      }
      standardCardSuit = imageFileCardSuit[j];
      return standardCardSuit;
   }

   // A simple main to display all the JLabels
   public static void main(String[] args)
   {
      int k;

      // Prepare the image icon array
      loadCardIcons();

      // Establish main frame in which program will run
      JFrame frmMyWindow = new JFrame("Card Room");
      frmMyWindow.setSize(1150, 650);
      frmMyWindow.setLocationRelativeTo(null);
      frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Set up layout which will control placement of buttons, etc
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      frmMyWindow.setLayout(layout);

      // Prepare the image label array
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];

      for (k = 0; k < NUM_CARD_IMAGES; k++)
         labels[k] = new JLabel(icon[k]);

      // Place your 3 controls into frame
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         frmMyWindow.add(labels[k]);

      // Show everything to the user
      frmMyWindow.setVisible(true);
   }
}


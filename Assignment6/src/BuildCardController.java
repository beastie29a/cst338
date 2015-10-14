import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class BuildCardController {
	
	private BuildCardView theView;
	private BuildCardModel theModel;
	
	public BuildCardController(BuildCardView theView,BuildCardModel theModel){
		this.theView=theView;
		this.theModel=theModel;
		
		this.theView.addPlayCardListener(new CardPlayListener());
		
	}

   class CardPlayListener implements ActionListener
   {
      //TODO: Remove commented code after testing
      public void actionPerformed(ActionEvent e)
      {
         theView.setCurrentButton((JButton) e.getSource());
         loop:
         for (int x = 0; x < theView.NUM_CARDS_PER_HAND; x++)
         {

            //System.out.println(theView.humanHand.inspectCard(x) + " | " + x);
            if (theView.getCurrentButton() == theView.humanCardButtons[x])
            {
               if (theView.checkPlayedCard(theView.humanHand.inspectCard(x)))
               {

                   //System.out.println("**");
                   //System.out.println(theView.humanHand.inspectCard(x) + " | " + x);
                   //System.out.println("**");
                  //theView.myCardTable.pnlHumanHand.remove(theView.getCurrentButton());
                  theView.playCards(theView.humanHand.inspectCard(x), theView.computerHand);
                  break loop;
               }
               //break loop;
            }
         }
         //System.out.println("---");
         theView.refreshPlayerPanel();
      }
   }
}

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

      public void actionPerformed(ActionEvent e)
      {
         if (e.getActionCommand().equals("I Cannot Play"))
         {
            System.out.println(e.getActionCommand());
            if (theView.cannotPlay == 1)
            {
               theView.cannotPlay = 0;
               theView.clearPlayArea();
               theView.setupPlayArea();
               theView.refreshPlayArea();
            }
            else
               theView.cannotPlay++;

            theView.playerSkipCount++;

            theView.computerPlayCard( theView.computerHand );
         }

         else
         {

            //System.out.println(theView.humanHand.inspectCard(x) + " | " + x);
            theView.setCurrentButton((JButton) e.getSource());

            loop:
            for (int x = 0; x < theView.NUM_CARDS_PER_HAND; x++)
            {
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
               //break loop;
            }
         }
         //System.out.println("---");
         theView.refreshPlayerPanel();
         theView.refreshScreen();
      }
   }
}

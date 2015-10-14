import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class HighCardController {
	
	private HighCardView theView;
	private HighCardModel theModel;
	
	public HighCardController(HighCardView theView,HighCardModel theModel){
		this.theView=theView;
		this.theModel=theModel;
		
		this.theView.addPlayCardListener(new CardPlayListener());
		
	}
	
	class CardPlayListener implements ActionListener{
		
	            public void actionPerformed(ActionEvent e)
	            {
	               theView.setCurrentButton((JButton) e.getSource());
	               theView.myCardTable.pnlHumanHand.remove(theView.getCurrentButton());
	               loop:
	               for (int x = 0; x < theView.NUM_CARDS_PER_HAND; x++)
	               {
	                  if (theView.getCurrentButton() == theView.humanCardButtons[x])
	                  {
	                     theView.playCards(theView.humanHand.inspectCard(x), theView.computerHand);
	                     break loop;
	                  }
	               }
	               theView.refreshPlayerPanel();
	            }
	         
		
		
		
	}

}

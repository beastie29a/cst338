import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildCardController
{
   private BuildCardModel buildCardModel;
   private BuildCardView buildCardView;

   public BuildCardController(BuildCardView theView, BuildCardModel theModel)
   {
      this.buildCardView = theView;
      this.buildCardModel = theModel;

      this.buildCardView.addPlayCardListener(new CardPlayListener());

   }

   class CardPlayListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         buildCardView.setCurrentButton((JButton) e.getSource());
         buildCardView.myCardTable.pnlHumanHand.remove(
               buildCardView.getCurrentButton());
         loop:
         for (int x = 0; x < buildCardView.NUM_CARDS_PER_HAND; x++)
         {
            if (buildCardView.getCurrentButton() ==
                  buildCardView.humanCardButtons[x])
            {
               buildCardView.playCards(buildCardView.humanHand.inspectCard(x),
                     buildCardView.computerHand);
               break loop;
            }
         }
         buildCardView.refreshPlayerPanel();
      }
   }

   public boolean updateCannotPlayCount()
   {
      if ( buildCardModel.getCannotPlayCount() == 1 )
      {
         buildCardModel.resetCannotPlayCount();
         return true;
      }
      buildCardModel.incrementCannotPlayCount();
      return false;
   }

   public boolean playTurn( Card playerCard, Card stackCard )
   {
      if ( getIndexValue(playerCard.getchar()) ==
            getIndexValue(stackCard.getchar()) - 1 ||
            getIndexValue(playerCard.getchar()) ==
                  getIndexValue(stackCard.getchar()) + 1)
      {
         return true;
      }
      return true;
   }

   private static int getIndexValue(char value)
   {
      int index = -1;
      for (int i = 0; i < Card.valueRanks.length; i++)
      {
         if (Card.valueRanks[i] == value)
            return i;
      }
      return index;
   }

}
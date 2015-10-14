import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class BuildCardView
{

   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static Card[] playedCards = new Card[NUM_PLAYERS];

   static JButton[] humanCardButtons = new JButton[NUM_CARDS_PER_HAND];

   static final int NUM_CARD_IMAGES = 56; // 52 + 4 jokers

   static GUICard cardGUI = new GUICard();
   private static JButton currentButton = new JButton();
   // establish main frame in which program will run
   static CardTable myCardTable
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);

   // Phase 3 Declarations
   static int numPacksPerDeck = 1;
   static int numJokersPerPack = 0;
   static int numUnusedCardsPerPack = 0;
   static Card[] unusedCardsPerPack = null;
   static Card[] winnings = new Card[NUM_CARDS_PER_HAND * 2];
   static int winningTotal = 0;
   static Hand humanHand;
   static Hand computerHand;

   static CardGameFramework highCardGame = new CardGameFramework(
         numPacksPerDeck, numJokersPerPack,
         numUnusedCardsPerPack, unusedCardsPerPack,
         NUM_PLAYERS, NUM_CARDS_PER_HAND);

   public BuildCardView()
   {

      if (!highCardGame.deal())
      {
         //System.out.print("Unable to deal");
         System.exit(1);
      }

      // Loop counter
      int k;

      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      myCardTable.pnlComputerHand.setBorder(
            BorderFactory.createTitledBorder("Computer Hand"));
      myCardTable.pnlHumanHand.setBorder(
            BorderFactory.createTitledBorder("Your Hand"));
      myCardTable.pnlPlayArea.setBorder(
            BorderFactory.createTitledBorder("Playing Area"));
      myCardTable.pnlPlayArea.setLayout(new GridLayout(2, 2));

      // CREATE LABELS ----------------------------------------------------

      // prepare the image label arrays
      computerHand = highCardGame.getHand(0);
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
         computerLabels[k] = new JLabel(cardGUI.getBackCardIcon());

      humanHand = highCardGame.getHand(1);

      highCardGame.sortHands();
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
         humanLabels[k] = new JLabel(cardGUI.getIcon(humanHand.inspectCard(k)));

      // ADD LABELS TO PANELS -----------------------------------------
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
         myCardTable.pnlComputerHand.add(computerLabels[k]);

      setupPlayerHand(humanHand, computerHand);

      setupPlayArea();

      // show everything to the user
      //myCardTable.setVisible(true);
   }


   public static void setupPlayArea()
   {
      for (int k = 0; k < NUM_PLAYERS; k++)
      {
         playedCards[k] = highCardGame.getCardFromDeck();
         playedCardLabels[k] = new JLabel(
               cardGUI.getIcon(playedCards[k]),
               JLabel.CENTER);
      }
      for (int k = 0; k < NUM_PLAYERS; k++)
      {
         myCardTable.pnlPlayArea.add(playedCardLabels[k]);
      }

   }

   public static void setupPlayerHand(
         final Hand humanHand, final Hand computerHand)
   {

      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
         humanCardButtons[k] = new JButton(
               "", cardGUI.getIcon(humanHand.inspectCard(k)));

      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         myCardTable.pnlHumanHand.add(humanCardButtons[k]);
      }

   }

   void addPlayCardListener(ActionListener listenForPlayCard)
   {

      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         currentButton = humanCardButtons[k];
         humanCardButtons[k].addActionListener(listenForPlayCard);
      }

   }

   public JButton getCurrentButton()
   {
      return currentButton;
   }

   public void setCurrentButton(JButton currentButton)
   {
      this.currentButton = currentButton;
   }

   public static void refreshScreen()
   {
      myCardTable.mainPanel.setVisible(false);
      myCardTable.mainPanel.setVisible(true);
   }

   public static void refreshPlayerPanel()
   {
      myCardTable.pnlHumanHand.setVisible(false);
      myCardTable.pnlHumanHand.setVisible(true);
   }

   public static void refreshComputerPanel()
   {
      myCardTable.pnlComputerHand.setVisible(false);
      myCardTable.pnlComputerHand.setVisible(true);
   }

   public static void refreshPlayArea()
   {
      myCardTable.pnlPlayArea.setVisible(false);
      myCardTable.pnlPlayArea.setVisible(true);
   }

   public static void clearPlayArea()
   {

      for (int k = 0; k < NUM_PLAYERS; k++)
      {
         myCardTable.pnlPlayArea.remove(playedCardLabels[k]);
      }
      refreshPlayArea();
   }

   public static void addCardsToPlayArea()
   {

      for (int k = 0; k < NUM_PLAYERS; k++)
      {
         myCardTable.pnlPlayArea.add(playedCardLabels[k]);
      }
      refreshPlayArea();
   }

   public static void playCards(Card playerCard, Hand computerHand)
   {
      clearPlayArea();
      playerPlayCard(playerCard);
      playerWins(playerCard,
            computerPlayCard(playerCard, computerHand));
      addCardsToPlayArea();
      endGame();
   }

   public boolean checkPlayedCard(Card playerCard)
   {
      int value = getIndexValue(playerCard.getchar());
      for (int i = 0 ; i < NUM_PLAYERS ; i++)
      {
         if (value == getIndexValue(playedCards[i].getchar()) - 1 ||
            value == getIndexValue(playedCards[i].getchar()) + 1)
            return true;
      }
      return false;
   }


   public static void endGame()
   {
      int x = 0;
      int k = 0;
      for (k = 0; k < computerLabels.length; k++)
      {
         if (computerLabels[k].getParent() == null)
         {
            x++;
         }
      }

      if (k == x)
      {
         clearPlayArea();
         if (getWinnings() >= 8)
         {
            myCardTable.pnlPlayArea.add(new JLabel("You Win!", JLabel.CENTER));
         } else
         {
            myCardTable.pnlPlayArea.add(new JLabel("You Lose!", JLabel.CENTER));
         }
      }
   }

   public static void playerPlayCard(Card card)
   {

      playedCardLabels[1] = new JLabel(
            cardGUI.getIcon(card), JLabel.CENTER);

      refreshScreen();
   }

   public static Card computerPlayCard(Card playerCard, Hand computerHand)
   {
      //TODO: get card from Computer's hand
      Card computerCard = new Card();
      boolean higherCard = false;

      for (int i = 0; i < computerHand.getNumCards(); i++)
      {
         if (getIndexValue(playerCard.getchar()) <
               getIndexValue(computerHand.inspectCard(i).getchar()))
         {
            computerCard = computerHand.playCard(i);
            higherCard = true;
            break;
         }
      }

      if (!higherCard)
         computerCard = computerHand.playCard(0);

      playedCardLabels[0] = new JLabel(
            cardGUI.getIcon(computerCard), JLabel.CENTER);


      loop:
      for (int k = 0; k < computerLabels.length; k++)
      {
         if (computerLabels[k].getParent() != null)
         {
            myCardTable.pnlComputerHand.remove(computerLabels[k]);
            break loop;
         }
      }
      refreshScreen();
      return computerCard;
   }

   public static void addToWinnings(Card playerCard, Card computerCard)
   {
      winnings[winningTotal] = playerCard;
      winnings[winningTotal + 1] = computerCard;
      winningTotal = winningTotal + 2;
   }

   public static int getWinnings()
   {
      return winningTotal;
   }

   public static boolean playerWins(Card playerCard, Card computerCard)
   {
      int playerValue = getIndexValue(playerCard.getchar());
      int computerValue = getIndexValue(computerCard.getchar());

      if (playerValue >= computerValue)
      {
         addToWinnings(playerCard, computerCard);
         return true;
      }
      return false;
   }

   private static int getIndexValue(char value)
   {
      int index = -1;
      for (int i = 0; i < Card.valueRanks.length; i++)
      {
         if (Card.valueRanks[i] == value)
            return (index = i);
      }
      return index;
   }

   public static void setVisible(boolean bool)
   {
      myCardTable.setVisible(bool);
   }

   static class CardTable extends JFrame
   {
      private int MAX_CARDS_PER_HAND = 56;
      private int MAX_PLAYERS = 2;  // for now, we only allow 2 person games

      private int numCardsPerHand;
      private int numPlayers;

      public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
      public JPanel mainPanel;

      public CardTable(String title, int numCardsPerHand, int numPlayers)
      {
         super(title);
         if (!isValid(title, numCardsPerHand, numPlayers)) return;
         setSize(800, 600);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLayout(new BorderLayout());
         mainPanel = new JPanel();
         pnlComputerHand = new JPanel();
         pnlHumanHand = new JPanel();
         pnlPlayArea = new JPanel();

         add(pnlComputerHand, BorderLayout.NORTH);
         add(pnlPlayArea, BorderLayout.CENTER);
         add(pnlHumanHand, BorderLayout.SOUTH);
      }

      private boolean isValid(String title, int numCardsPerHand, int numPlayers)
      {
         if (title.length() <= 0 ||
               numCardsPerHand <= 0 ||
               numCardsPerHand > MAX_CARDS_PER_HAND ||
               numPlayers <= 0 ||
               numPlayers > MAX_PLAYERS) return false;
         return true;
      }

      public int getNumCardsPerHand()
      {
         return numCardsPerHand;
      }

      public int getNumPlayers()
      {
         return numPlayers;
      }

   }


}


class GUICard
{

   private static Icon[][] iconCards = new ImageIcon[14][4];
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   // Adding this array so we can know what values are valid
   //public static char[] cardValues = {'A', '2', '3', '4', '5', '6', '7', '8',
   //      '9', 'T', 'J', 'Q', 'K', 'X'};

   private static String[] cardSuites = new String[]{"C", "D", "H", "S"};

   public GUICard()
   {
      loadCardIcons();
   }

   static void loadCardIcons()
   {
      if (!iconsLoaded)
      {
         // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
         // in a SHORT loop.  For each file name, read it in and use it to
         // instantiate each of the 57 Icons in the icon[] array.
         //int x = 0;
         //int y = 0;
         for (int x = 0; x < cardSuites.length; x++)
         {
            for (int y = 0; y < Card.Value.length; y++)
            {
               iconCards[y][x] = new ImageIcon(
                     "images/" + Card.Value[y] + cardSuites[x] + ".gif");
            }
         }
         iconBack = new ImageIcon("images/BK.gif");
         iconsLoaded = true;
      }
   }

   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      // an idea for a helper method (do it differently if you wish)
      return String.valueOf(Card.Value[k]);
   }

   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      // an idea for another helper method (do it differently if you wish)
      return cardSuites[j];
   }

   private static int valueAsInt(Card card)
   {
      String values = new String(Card.Value);
      //return Arrays.asList(Card.Value).indexOf(card.getchar());
      return values.indexOf(card.getchar());
   }

   private static int suitAsInt(Card card)
   {
      //String suite;
      return card.getSuit().ordinal();

   }

   static public Icon getIcon(Card card)
   {
      return (Icon) iconCards[valueAsInt(card)][suitAsInt(card)];
   }

   static public Icon getBackCardIcon()
   {
      return (Icon) iconBack;
   }

}


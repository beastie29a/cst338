import javax.swing.*;

import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

   // Instantiate Timer object
   static Clock insertClock = new Clock();

   // Instantiate Cannot Play Button
   static JButton cantPlay = new JButton("I Cannot Play");
   static int cannotPlay = 0;
   static int playerSkipCount = 0;
   static int computerSkipCount = 0;

   // Phase 3 Declarations
   static int numPacksPerDeck = 1;
   static int numJokersPerPack = 0;
   static int numUnusedCardsPerPack = 0;
   static Card[] unusedCardsPerPack = null;
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
      myCardTable.pnlTimer.setBorder(
         BorderFactory.createTitledBorder("Timer"));
      myCardTable.pnlPlayArea.setLayout(new GridLayout(2, 2));
      myCardTable.pnlTimer.setLayout(new GridLayout(3, 1));

      // CREATE LABELS ----------------------------------------------------

      // prepare the image label arrays
      computerHand = highCardGame.getHand(0);
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
         computerLabels[k] = new JLabel(cardGUI.getBackCardIcon());

      humanHand = highCardGame.getHand(1);

      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
         humanLabels[k] = new JLabel(cardGUI.getIcon(humanHand.inspectCard(k)));

      // ADD LABELS TO PANELS -----------------------------------------
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
         myCardTable.pnlComputerHand.add(computerLabels[k]);

      setupPlayerHand(humanHand);
      setupPlayArea();

      // ADD TIMER -----------------------------------------------------
      myCardTable.pnlTimer.add(insertClock.timeText);
      myCardTable.pnlTimer.add(insertClock.startStopButton);

      // Increase timer display font size
      insertClock.timeText.setFont(new Font("Aerial", Font.BOLD, 60));

      // ADD Can't Play Button----------------------------------
      myCardTable.pnlTimer.add(cantPlay);

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
      refreshPlayArea();

   }

   public static void redrawPlayArea()
   {
      for (int k = 0; k < NUM_PLAYERS; k++)
      {
         myCardTable.pnlPlayArea.remove(playedCardLabels[k]);
         playedCardLabels[k] = new JLabel(
               cardGUI.getIcon(playedCards[k]),
               JLabel.CENTER);
      }
      for (int k = 0; k < NUM_PLAYERS; k++)
      {
         myCardTable.pnlPlayArea.add(playedCardLabels[k]);
      }
      refreshPlayArea();
   }

   public static void setupPlayerHand(final Hand humanHand)
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

      cantPlay.addActionListener(listenForPlayCard);

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
      computerPlayCard(computerHand);
      addCardsToPlayArea();
   }

   public static boolean checkPlayedCard(Card playerCard)
   {
      int value = getIndexValue(playerCard.getchar());
      for (int i = 0 ; i < NUM_PLAYERS ; i++)
      {
         if (value == getIndexValue(playedCards[i].getchar()) - 1 ||
            value == getIndexValue(playedCards[i].getchar()) + 1)
         {
            // Assign the value to the array
            cannotPlay = 0;
            myCardTable.pnlPlayArea.remove(playedCardLabels[i]);
            playedCards[i] = new Card(playerCard.getchar(),
               playerCard.getSuit());
            playedCardLabels[i] = new JLabel(
               cardGUI.getIcon(playerCard), JLabel.CENTER);
            return true;
         }
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

      int deckCards = highCardGame.getNumCardsRemainingInDeck();
      System.out.println(deckCards);

      if (deckCards == 0)
      {
         clearPlayArea();
         if (playerSkipCount < computerSkipCount)
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
      for (int i = 0 ; i < humanHand.getNumCards() ; i++)
         if ( humanHand.inspectCard(i) == card)
             highCardGame.playCard(1,i);

      highCardGame.takeCard(1);

      for (int i = 0 ; i < humanHand.getNumCards() ; i++){
         humanLabels[i] = new JLabel(cardGUI.getIcon(humanHand.inspectCard(i)));
         humanCardButtons[i].setIcon(cardGUI.getIcon(humanHand.inspectCard(i)));
      }

      refreshScreen();
   }

   public static Card computerPlayCard(Hand computerHand)
   {
      Card computerCard = new Card();
      boolean cardPlayed = false;

      for (int i = 0; i < computerHand.getNumCards(); i++)
      {
         if (checkPlayedCard(computerHand.inspectCard(i)))
         {
            computerCard = computerHand.playCard(i);
            cardPlayed = true;
            highCardGame.takeCard(0);
            redrawPlayArea();
            break;
         }
      }

      if (!cardPlayed)
      {
         System.out.println("Computer Cannot Play" + computerHand);

         if (cannotPlay == 1)
         {
            cannotPlay = 0;
            clearPlayArea();
            setupPlayArea();
            refreshPlayArea();
         }
         else
         {
            cannotPlay++;

         }
         computerSkipCount++;
      }
      refreshScreen();
      return computerCard;
   }

   public static boolean playerWins(Card playerCard, Card computerCard)
   {
      int playerValue = getIndexValue(playerCard.getchar());
      int computerValue = getIndexValue(computerCard.getchar());

      if (playerValue >= computerValue)
      {
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
      public JPanel mainPanel, pnlTimer;

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
         pnlTimer = new JPanel();

         add(pnlComputerHand, BorderLayout.NORTH);
         add(pnlPlayArea, BorderLayout.CENTER);
         add(pnlHumanHand, BorderLayout.SOUTH);
         add(pnlTimer, BorderLayout.EAST);
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

// Set up Timer and button actions to run on separate thread
class Clock extends JFrame
{
   private int counter = 0;
   private boolean runTimer = false;
   private final int PAUSE = 100; // Milliseconds
   private String start = "START";
   private String stop = "STOP";

   public Timer clockTimer;
   public JButton startStopButton;
   public JLabel timeText;
   public JPanel timerPanel;

   // Default constructor creates GUI
   public Clock()
   {
      // Timer action set to 1000 milliseconds
      clockTimer = new Timer(1000, timerEvent);

      timeText = new JLabel("" + formatToTime(counter));

      startStopButton = new JButton(start);
      startStopButton.addActionListener(buttonEvent);

      /***Display clock in separate window for testing***/
      //timerPanel = new JPanel();
      //timerPanel.add(timeText);
      //timerPanel.setLayout(new BorderLayout());
      //add(timerPanel, BorderLayout.CENTER);
      //timerPanel.add(timeText, BorderLayout.NORTH);
      //timerPanel.add(startStopButton, BorderLayout.SOUTH);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(200, 200);
   }

   // Format timer output to string as minutes:seconds
   public String formatToTime(long seconds)
   {
      long s = seconds % 60;
      long m = (seconds / 60) % 60;
      return String.format("%01d:%02d", m, s);
   }

   // Increment Timer
   private ActionListener timerEvent = new ActionListener()
   {
      public void actionPerformed(ActionEvent e)
      {
         TimerClass timerThread = new TimerClass();
         timerThread.start();
      }
   };

   // Create Timer object and call run method
   private ActionListener buttonEvent = new ActionListener()
   {
      public void actionPerformed(ActionEvent e)
      {
         if (runTimer)
         {
            startStopButton.setText(start);
            clockTimer.stop();
            runTimer = false;
         }
         else if (!runTimer)
         {
            startStopButton.setText(stop);
            clockTimer.start();
            runTimer = true;
            counter = 0;
            timeText.setText("" + formatToTime(counter));
         }
      }
   };

   // Called by ActionListener to start, stop, and display time and buttons
   private class TimerClass extends Thread
   {
      public void run()
      {
         counter++;
         timeText.setText("" + formatToTime(counter));
         doNothing(PAUSE);
      }

      // Pause thread helper method
      public void doNothing(int milliseconds)
      {
         try
         {
            Thread.sleep(milliseconds);
         } catch (InterruptedException e)
         {
            System.out.println("Unexpected interrupt");
            System.exit(0);
         }
      }
   } //End TimerClass inner class
}
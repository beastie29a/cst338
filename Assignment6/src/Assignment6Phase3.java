/**
 * CST 338 - Fall 2015 Session A
 * Assignment 6
 * Phase I
 *
 * @author Robert Contreras
 * @author Ryan Doherty
 * @author Hyo Lee
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;


public class Assignment6Phase3
{

   public static void main(String[] args)
   {
	   BuildCardView theView = new BuildCardView();
	   BuildCardModel theModel = new BuildCardModel();
	   BuildCardController theController =
			new BuildCardController(theView,theModel);
	   
	   theView.setVisible(true);
   }

}


class BuildCardController {

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

							theView.playCards(theView.humanHand.inspectCard(x),
								theView.computerHand);
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
			theView.endGame();
		}
	}
}


class BuildCardModel {

}

/*
   Set valid suits and values for each card in a standard deck
*/

class Card
{
	// Valid suits
	public enum Suit
	{
		clubs, diamonds, hearts, spades
	}

	// Adding this array so we can know what values are valid
	public static char[] Value = {'A', '2', '3', '4', '5', '6', '7', '8',
		'9', 'T', 'J', 'Q', 'K', 'X'};

	public static char[] valueRanks = {'A', '2', '3', '4', '5', '6', '7', '8',
		'9', 'T', 'J', 'Q', 'K', 'X'};

	private char value;
	private Suit suit;
	private boolean errorFlag;

	// Default Constructor
	public Card()
	{
		value = 'A';
		suit = Suit.spades;
		errorFlag = false;
	}

	// Overloaded Constructor
	public Card(char value, Suit suit)
	{
		errorFlag = !set(value, suit);
	}

	static void arraySort(Card[] cards, int arraySize)
	{
		Card temp;   //holding variable

		for (int i = 0; i < arraySize; i++)
		{
			for (int j = 1; j < arraySize - i; j++)
			{
				if (getIndexValue(cards[j - 1].getchar())
					> getIndexValue(cards[j].getchar()))
				{
					temp = cards[j - 1];
					cards[j - 1] = cards[j];
					cards[j] = temp;
				}
			}
		}
	}

	private static int getIndexValue(char value)
	{
		int index = -1;
		for (int i = 0; i < valueRanks.length; i++)
		{
			if (valueRanks[i] == value)
				return (index = i);
		}
		return index;
	}


	// Output message - invalid or display card
	public String toString()
	{
		if (errorFlag)
			return ("[ invalid ]");

		return (value + " of " + suit);
	}

	// Set and check the validity of the card
	public boolean set(char value, Suit suit)
	{
		if (isValid(value, suit))
		{
			this.value = value;
			this.suit = suit;
			return true;
		}
		return false;
	}

	// Accessors for suit, value, and error
	public Suit getSuit()
	{
		return suit;
	}

	public char getchar()
	{
		return value;
	}

	public boolean getErrorFlag()
	{
		return errorFlag;
	}

	// Both this value and suit objects are equal to passed in objects
	public boolean equals(Card card)
	{
		return card.value == value && card.suit == suit;
	}

	// Check if card has valid value and suit
	private boolean isValid(char value, Suit suit)
	{
		boolean valid = false;

		for (char v : Value)
		{
			if (value == v)
			{
				valid = true;
				break;
			}
		}

		if (valid)
		{
			for (Suit s : Suit.values())
			{
				if (suit == s)
					return true;
			}
		}
		return false;
	}
}

/*
   Hand class - Hand size, add cards to hand, and play cards from hands
 */
class Hand
{
	public static final int MAX_CARDS = 100; //Length of array

	private Card[] myCards;
	private int numCards;

	// Default constructor
	public Hand()
	{
		myCards = new Card[MAX_CARDS];   //Length
		numCards = 0;
	}

	// Remove all cards from the hand
	public void resetHand()
	{
		myCards = new Card[MAX_CARDS];
		numCards = 0;
	}

	// Adds a card to the next available position as an object copy
	public boolean takeCard(Card card)
	{
		if (numCards >= MAX_CARDS)
		{
			return false;
		} else
		{
			Card newCard = new Card(card.getchar(), card.getSuit());
			myCards[numCards] = newCard;
			numCards++;
			return true;
		}
	}


	// Returns and removes the card in the top occupied position
	public Card playCard()
	{
		Card card = myCards[numCards - 1];
		numCards--;
		return card;
	}

	// Overloaded playCard() to deal with an index
	public Card playCard(int index)
	{
		if (index > numCards || numCards == 0)
			return new Card();

		if (index == (numCards - 1))
			return playCard();

		Card temp = new Card(myCards[index].getchar(),
			myCards[index].getSuit());

		for (int i = index; i < numCards - 1 ; i++)
		{
			myCards[i].set(
				myCards[i + 1].getchar(), myCards[i + 1].getSuit());
		}

		myCards[numCards - 1] = null;
		numCards--;
		return temp;
	}

	// Output message
	public String toString()
	{
		String result = "Hand = ( ";
		if (numCards == 0)
		{
			result = result + ")";
		} else
		{
			for (int i = 0; i < numCards - 1; i++)
			{
				result = result + myCards[i] + ", ";
			}
			result = result + myCards[numCards - 1] + " )";
		}
		return result;
	}

	// Accessor for number of cards
	public int getNumCards()
	{
		return numCards;
	}

	// Accessor for each card
	public Card inspectCard(int k)
	{
		Card card;
		if (k > numCards || k < 0)
		{
			card = new Card('y', Card.Suit.spades);
		} else
		{
			card = myCards[k];
		}
		return card;
	}

	public void sort()
	{
		Card.arraySort(myCards, numCards);
	}

}

/*
   Deck class - Set size of deck, shuffle, and deal cards
*/

class Deck
{
	private static final int PACK_SIZE = 56;
	public final int MAX_DECKS = 6;
	public final int MAX_CARDS = MAX_DECKS * PACK_SIZE;
	private static Card[] masterPack = new Card[PACK_SIZE];
	public static boolean masterPackAllocated = false;

	private Card[] cards;

	private int topCard;
	private int numPacks;

	// Constructor for initial numPacks
	public Deck(int numPacks)
	{
		//if numPacks is passed an invalid value,
		//default it to 1
		if (numPacks < 0 || numPacks > MAX_DECKS) numPacks = 1;
		this.cards = new Card[numPacks * PACK_SIZE];
		this.numPacks = numPacks;
		this.topCard = numPacks * PACK_SIZE - 1;
		allocateMasterPack();
		init(numPacks);
	}

	// Overloaded Default Constructor
	public Deck()
	{
		this.numPacks = 1;
		this.cards = new Card[PACK_SIZE];
		this.topCard = PACK_SIZE - 1;
		allocateMasterPack();
		init(1);
	}

	// Private helper method called by Constructor to build masterPack only once
	private static void allocateMasterPack()
	{
		if (!masterPackAllocated)
		{
			int x = 0;
			for (Card.Suit suit : Card.Suit.values())
			{
				for (char value : Card.Value)
				{
					masterPack[x] = new Card(value, suit);
					x++;
				}
			}
			masterPackAllocated = true;
		}
	}

	//Initialize and Re-populate cards array
	public void init(int numPacks)
	{

		//if numPacks is passed an invalid value,
		//default it to 1
		if (numPacks < 0 || numPacks > MAX_DECKS) numPacks = 1;
		this.cards = new Card[numPacks * PACK_SIZE];
		this.numPacks = numPacks;
		this.topCard = numPacks * PACK_SIZE - 1;
		int cardNum = 0;
		while (cardNum < numPacks * PACK_SIZE)
		{
			for (int x = 0; x < numPacks; x++)
			{
				for (int y = 0; y < PACK_SIZE; y++)
				{
					cards[cardNum] = masterPack[y];
					cardNum++;
				}
			}
		}
	}

	// Mix up cards with random number generator
	public void shuffle()
	{
		Random rnd = new Random();
		for (int i = cards.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			Card temp = cards[index];
			cards[index] = cards[i];
			cards[i] = temp;
		}
	}

	// Returns and removes the card  at the top position
	public Card dealCard()
	{
		Card dealtCard = new Card(cards[topCard].getchar(),
			cards[topCard].getSuit());
		cards[topCard] = null;
		topCard--;
		return dealtCard;
	}

	// Accessor for topCard
	public int getTopCard()
	{
		return topCard;
	}

	// Accessor for each card
	public Card inspectCard(int k)
	{
		Card testCard;
		if (k > topCard || k < 0)
		{
			testCard = new Card('y', Card.Suit.spades);
		} else
		{
			testCard = cards[k];
		}
		return testCard;
	}

	public boolean addCard(Card card)
	{
		if (Arrays.asList(cards).indexOf(card) > 0)
		{
			int openElement = 0;
			for (int x = 0; x < cards.length; x++)
			{
				if (cards[x] == null)
				{
					openElement = x;
					break;
				}
			}
			cards[openElement] = card;
			topCard = openElement;
			return true;
		}
		return false;
	}


	// you are looking to remove a specific card from the deck.
	// Put the current top card into its place.
	public boolean removeCard(Card card)
	{
		int index = Arrays.asList(cards).indexOf(card);
		if (index > 0)
		{
			Card topCardCopy = new Card(cards[topCard].getchar(),
				cards[topCard].getSuit());
			Card cardCopy = new Card(cards[index].getchar(),
				cards[index].getSuit());
			cards[index] = topCardCopy;
			cards[topCard] = cardCopy;
			topCard--;
			return true;
		}
		return false;
	}

	// put all of the cards in the deck back
	// into the right order according to their values.
	public void sort()
	{
		Card.arraySort(cards, cards.length);
	}

	//return the number of cards remaining in the deck
	public int getNumCards()
	{
		int numCards = 0;
		for (int x = 0; x < cards.length; x++)
		{
			if (cards[x] != null)
			{
				numCards++;
			}
		}
		return numCards;
	}
}

//class CardGameFramework  ----------------------------------------------------
class CardGameFramework
{
	private static final int MAX_PLAYERS = 50;

	private int numPlayers;
	private int numPacks;            // # standard 52-card packs per deck
	// ignoring jokers or unused cards
	private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
	private int numUnusedCardsPerPack;  // # cards removed from each pack
	private int numCardsPerHand;        // # cards to deal each player
	private Deck deck;               // holds the initial full deck and gets
	// smaller (usually) during play
	private Hand[] hand;             // one Hand for each player
	private Card[] unusedCardsPerPack;   // an array holding the cards not used
	// in the game.  e.g. pinochle does not
	// use cards 2-8 of any suit

	public CardGameFramework(int numPacks, int numJokersPerPack,
									 int numUnusedCardsPerPack, Card[] unusedCardsPerPack,
									 int numPlayers, int numCardsPerHand)
	{
		int k;

		// filter bad values
		if (numPacks < 1 || numPacks > 6)
			numPacks = 1;
		if (numJokersPerPack < 0 || numJokersPerPack > 4)
			numJokersPerPack = 0;
		if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
			numUnusedCardsPerPack = 0;
		if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
			numPlayers = 4;
		// one of many ways to assure at least one full deal to all players
		if (numCardsPerHand < 1 ||
			numCardsPerHand > numPacks * (52 - numUnusedCardsPerPack)
				/ numPlayers)
			numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

		// allocate
		this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
		this.hand = new Hand[numPlayers];
		for (k = 0; k < numPlayers; k++)
			this.hand[k] = new Hand();
		deck = new Deck(numPacks);

		// assign to members
		this.numPacks = numPacks;
		this.numJokersPerPack = numJokersPerPack;
		this.numUnusedCardsPerPack = numUnusedCardsPerPack;
		this.numPlayers = numPlayers;
		this.numCardsPerHand = numCardsPerHand;
		for (k = 0; k < numUnusedCardsPerPack; k++)
			this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

		// prepare deck and shuffle
		newGame();
	}

	// constructor overload/default for game like bridge
	public CardGameFramework()
	{
		this(1, 0, 0, null, 4, 13);
	}

	public Hand getHand(int k)
	{
		// hands start from 0 like arrays

		// on error return automatic empty hand
		if (k < 0 || k >= numPlayers)
			return new Hand();

		return hand[k];
	}

	public Card getCardFromDeck()
	{
		return deck.dealCard();
	}

	public int getNumCardsRemainingInDeck()
	{
		return deck.getNumCards();
	}

	public void newGame()
	{
		int k, j;

		// clear the hands
		for (k = 0; k < numPlayers; k++)
			hand[k].resetHand();

		// restock the deck
		deck.init(numPacks);

		// remove unused cards
		for (k = 0; k < numUnusedCardsPerPack; k++)
			deck.removeCard(unusedCardsPerPack[k]);

		// add jokers
		for (k = 0; k < numPacks; k++)
			for (j = 0; j < numJokersPerPack; j++)
				deck.addCard(new Card('X', Card.Suit.values()[j]));

		// shuffle the cards
		deck.shuffle();
	}

	public boolean deal()
	{
		// returns false if not enough cards, but deals what it can
		int k, j;
		boolean enoughCards;

		// clear all hands
		for (j = 0; j < numPlayers; j++)
			hand[j].resetHand();

		enoughCards = true;
		for (k = 0; k < numCardsPerHand && enoughCards; k++)
		{
			for (j = 0; j < numPlayers; j++)
				if (deck.getNumCards() > 0)
					hand[j].takeCard(deck.dealCard());
				else
				{
					enoughCards = false;
					break;
				}
		}

		return enoughCards;
	}

	void sortHands()
	{
		int k;

		for (k = 0; k < numPlayers; k++)
			hand[k].sort();
	}

	Card playCard(int playerIndex, int cardIndex)
	{
		// returns bad card if either argument is bad
		if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
			cardIndex < 0 || cardIndex > numCardsPerHand - 1)
		{
			//Creates a card that does not work
			return new Card('M', Card.Suit.spades);
		}

		// return the card played
		return hand[playerIndex].playCard(cardIndex);

	}


	boolean takeCard(int playerIndex)
	{
		// returns false if either argument is bad
		if (playerIndex < 0 ||  playerIndex > numPlayers - 1)
		{
			return false;
		}

		// Are there enough Cards?
		if (deck.getNumCards() <= 0)
			return false;

		return hand[playerIndex].takeCard(deck.dealCard());
	}
}


class BuildCardView
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

		myCardTable.setSize(900, 600);
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


	public static void clearHumanHandArea()
	{

		for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
		{
			myCardTable.pnlHumanHand.remove(humanCardButtons[k]);
		}
		refreshPlayerPanel();
	}

	public static void clearComputerHandArea()
	{

		for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
		{
			myCardTable.pnlComputerHand.remove(computerLabels[k]);
		}
		refreshComputerPanel();
	}


	public static void clearScreen()
	{
		clearPlayArea();
		clearHumanHandArea();
		clearComputerHandArea();
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
			clearScreen();
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
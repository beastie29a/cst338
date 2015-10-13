import javax.swing.*;

public class BuildCardModel
{
   public static int NUM_CARDS_PER_HAND = 7;
   public static int NUM_PLAYERS = 2;
   public static final int NUM_CARD_IMAGES = 56; // 52 + 4 jokers

   private static int numPacksPerDeck;
   private static int numJokersPerPack;
   private static int numUnusedCardsPerPack;
   private static Card[] unusedCardsPerPack = null;

   private static CardGameFramework buildCardGame;
   private static int cannotPlayCount;

   public BuildCardModel()
   {
      numPacksPerDeck = 1;
      numJokersPerPack = 0;
      numUnusedCardsPerPack = 0;
      unusedCardsPerPack = null;

      buildCardGame = new CardGameFramework(
            numPacksPerDeck, numJokersPerPack,
            numUnusedCardsPerPack, unusedCardsPerPack,
            NUM_PLAYERS, NUM_CARDS_PER_HAND);

      cannotPlayCount = 0;
   }

   public int getCannotPlayCount()
   {
      return cannotPlayCount;
   }

   public void incrementCannotPlayCount()
   {
      cannotPlayCount++;
   }

   public void resetCannotPlayCount()
   {
      cannotPlayCount = 0;
   }

}
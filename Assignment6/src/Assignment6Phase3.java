/**
 * CST 338 - Fall 2015 Session A
 * Assignment 6
 * Phase I
 *
 * @author Robert Contreras
 * @author Ryan Doherty
 * @author Hyo Lee
 */

public class Assignment6Phase3
{

   public static void main(String[] args)
   {
	   BuildCardView theView = new BuildCardView();
	   BuildCardModel theModel = new BuildCardModel();
	   BuildCardController theController = new BuildCardController(theView,theModel);
	   
	   theView.setVisible(true);
   }

}


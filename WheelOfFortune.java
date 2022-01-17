/**
 *
 * @author Jack Boccuzzi
 * @version 1.0
 * @since -March 10th, 2021-
 * WheelOfFortune.java
 *
 * This program allows a user to play Wheel Of Fortune with the computer.
 * --EXPLAIN THE RULES--
 * 
 * The program will randomly select a movie and display the title hidden behind
 * '*''s. The "wheel will spin" and you will find out how much money you can earn
 * per letter. We want to help you win so the program is going to reveal the common
 * letters for you: R, S, T, L, N, E. As the player you have two options:
 *
 * Enter 'A' and try to guess the entire movie phrase, 
 * OR
 * Enter 'B' and guess a letter that has not been revealed yet.
 *
 * If you guess a letter correctly, that letter will be revealed to you and you will
 * be presented with the two options stated above again.
 *
 * The game ends if:
 * 1. You make 3 mistakes (incorrectly guess the letter or movie title)
 * 2. You guess every letter in the phrase
 * 3. You guess the entire movie phrase
 *
 * After the game is over, depending on if you win or not, your earnings will be
 * revealed to you. (If you lose, you get $0)
 *
 */
import java.util.*;
import java.lang.*;

public class WheelOfFortune
{
   //This is an array of Strings
   static String movieList[] = {"STAR WARS: THE LAST JEDI","THE MATRIX","AVATAR","PULP FICTION","CITY LIGHTS",
                                "PARASITE","THE GODFATHER","ALIEN","TOY STORY","GET OUT",
                                "GOODFELLAS","INCEPTION","DIE HARD","BACK TO THE FUTURE","JAWS"};

    static int[] cashValues = {100, 200, 300, 400, 500}; 

    static char[] commonLetters = {'r', 's', 't', 'l', 'n', 'e'};

   /**
    * Entry point of the program
    * @param args input arguments
    */

   String movie;
   StringBuilder shadow;
   int randMovie;
   String selectedMovie;
   int randomCashValue;
   int cashValue;
   int userCashWinnings;
   int numGuessesLeft;

   // Constructor
   public WheelOfFortune() 
   {
    Random rand = new Random();
    
    randMovie = rand.nextInt(movieList.length);

    movie = movieList[randMovie];

    selectedMovie = movieList[randMovie];

    // creates a string with the amount of '*' in the randomly selected movie
    char[] chars = new char[selectedMovie.length()];
    Arrays.fill(chars, '*');
    String s = new String(chars);
    // create a StringBuilder identical to string "s"
    shadow = new StringBuilder(s);
    // reveal non-letter elements
    for (int i = 0; i < movie.length(); i++) {
      if (!(Character.isLetter(movie.charAt(i)))) {
        shadow.setCharAt(i, movie.charAt(i));
      }
    }

    randomCashValue = rand.nextInt(5);
    cashValue = cashValues[randomCashValue];

    userCashWinnings = 0;

    numGuessesLeft = 3;
   }

   //"Getter" Methods
   public String getMovie() {
    return movie;
   }

   public StringBuilder getShadow() {
    return shadow;
   }

   public int getCashValue() {
    return cashValue;
   }

   public int getUserCashWinnings() {
    return userCashWinnings;
   }

   public int getNumGuessesLeft() {
    return numGuessesLeft;
   }

   // Method to decrease the user's remaining guesses
   public void decreaseGuesses() {
    numGuessesLeft = numGuessesLeft - 1;
   }

   // Method to Reveal the Common Letters: R, S, T, L, N, E
   public void revealCommons() {
      for (int i = 0; i < commonLetters.length; i++) {
        for (int j = 0; j < selectedMovie.length(); j++) {
          // if the current index/letter in "selectedMovie" == the current index/letter commonLetters 
          if (Character.toUpperCase(selectedMovie.charAt(j)) == Character.toUpperCase(commonLetters[i])) {
            shadow.setCharAt(j, Character.toUpperCase(commonLetters[i]));
          }
        }
      }    }


   // Method to replace *'s from user's guess
   public void replaceCharacter(char reveal) {
    int letterCounter = 0;
    for (int i = 0; i < selectedMovie.length(); i++) {
      if (Character.toUpperCase(selectedMovie.charAt(i)) == Character.toUpperCase(reveal)) {
        shadow.setCharAt(i, reveal);
        letterCounter++;
      }
    }

    // if letters match / have been found, give them money
    if (letterCounter != 0) {
      userCashWinnings += cashValue * letterCounter;
    }
    else { // no letters match, considered a mistake
      numGuessesLeft--;
      System.out.println("Incorrect Guess!");
      System.out.println("You have " + numGuessesLeft + " guess(es) left.");
    }
  

   }

   // Method to determine whether user guessed the entire movie
   public boolean guessMovie(String guess) { 
    if (selectedMovie.equals(guess)) { 
      return true;
    }
    else {
      return false;
    }
   }

   // Method to print the game's options
   public void gameChoices() {
    System.out.println("Please choose an option (A or B)");
    System.out.println("A. Guess the movie and win");
    System.out.println("B. Guess a letter that has not been revealed");
   }

   public static void main(String[] args)
   {
      Scanner scan = new Scanner(System.in);

      // Start the game
      System.out.println("Welcome to Wheel of Fortune!");
      WheelOfFortune game = new WheelOfFortune();

      System.out.println("Press 'Enter' to start:");
      scan.nextLine();

      // displays initial shadow
      System.out.println(game.getShadow()); 

      // displays random cashvalue
      System.out.println("Spinning the Wheel...");
      System.out.println("You can win $" + game.getCashValue() + " per letter!"); 

      // reveals the common letters
      game.revealCommons();
      System.out.println(game.getShadow());

      // 

      // int userNumGuessesLeft = game.getNumGuessesLeft();
      while (game.getNumGuessesLeft() != 0) {

        // Display the game's options
        game.gameChoices();

        // get a letter option from user and make it uppercase
        String userGuess = scan.next().toUpperCase();
        char userGuessOption = userGuess.charAt(0);
        scan.nextLine(); // grabs the new line which allows for input

        if (userGuessOption == 'A') { 

          // get user input and make it uppercase
          System.out.println("Enter your guess:");
          String userMovieGuess = scan.nextLine().toUpperCase();

          boolean fullGuess = game.guessMovie(userMovieGuess);
          if (fullGuess == true) {
            System.out.println("Congratulations you won! You guessed the entire movie!");
            break; // exits while loop
          }
          else { // fullGuess == false
            game.decreaseGuesses(); // decreases guesses left by 1
            System.out.println("That was incorrect!"); 
            System.out.println("Please try again!");
            System.out.println("You have " + game.getNumGuessesLeft() + " guess(es) left!");
          }

        }

        else if (userGuessOption == 'B') {

          // get user's guess for revealing a letter and uppercase it
          System.out.println("Enter a letter:");
          String letterGuess = scan.next().toUpperCase();
          char userLetterGuess = letterGuess.charAt(0);

          game.replaceCharacter(userLetterGuess);
          System.out.println(game.getShadow());

          // store the shadow in a variable, then convert it to a String 
          StringBuilder shadowMain = game.getShadow();
          String updatedShadow = shadowMain.toString();

          // if user guessed every character correctly
          if (game.guessMovie(updatedShadow)) {
            System.out.println("Congratulations, You have guessed every character!");
            break;
          }

        }

      } // close while loop

      if (game.getNumGuessesLeft() == 0) {
        System.out.println("Game Over! You lost!");
        System.out.println("You receive: $0");
        return; // exits the program if they lose
      }

      // display user earnings after the game
      System.out.println("You have won $" + game.getUserCashWinnings() + "!" );

   }
}





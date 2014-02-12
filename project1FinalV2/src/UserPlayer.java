/*Adrian Campos
  acampo24@uic.edu

  Tianniu Lei
  tlei2@uic.edu

   UserPlayer:

   Handles user's hand and input for the discard component of the game.
 */

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class UserPlayer{

	static int top_of_deck = 0;
	static boolean extra_discard = false;
	static boolean num_in_range = false;
	static boolean check_discarding_ace = false;
	private static int[] sorted = null;
	private static boolean after_discard = false;

	//initiates 5 cards. Not implemented anymore
	/*public static void initiate_hand(List<Card> player_hand, CardPile deck){
		for(int i = 0; i < 5; i++){
			player_hand.add(deck.drawCard());
			//player_hand.get(i).printCard();
			top_of_deck++;
		}
	}*/

	//prints user hand:
	public static void print_phand(List<Card> player_hand){
		System.out.print("Your hand:");
		for (int i = 0; i < player_hand.size(); i++) {
			System.out.print(" " +  (i+1) + ")");
			if(player_hand.get(i).getRank() == 'A')
				extra_discard = true;
			player_hand.get(i).printCard();
		}
		System.out.println("");

		//if player has an Ace message, set extra discard to true
		if(!after_discard){
			if(extra_discard == true){
				System.out.println("Since you have an Ace you can keep the Ace and " +
						"discard the other four cards.\n");
			}
			else
				extra_discard = false;
		}
	}

	//takes user input and determines what cards to discard. Includes the Ace = extra discard condition
	public static void discard_draw(List<Card> player_hand, CardPile deck, CardPile discardpile){
		int numToDiscard = 0;
		int maxDiscard = 3;
		int temp;
		int cardNumber;
		int cardVal;
		char cardRank;
		if(extra_discard == true){
			maxDiscard = 4;
		}

		System.out.println("List the cards numbers you wish to discard. No negative numbers please! > ");
		System.out.println("(enter each card number followed by a space. If no cards need to be discarded, enter a 0)");

		// read a line of input
		Scanner readLine = new Scanner(System.in);
		String line = readLine.nextLine();
		Scanner readInts = new Scanner(line);

		List<Integer> intInput = new ArrayList<Integer>();
		int tempInt;

		// read ints from the line of inputs
		while (readInts.hasNext()) {
			
			
			if(!readInts.hasNextInt()){
				System.out.println("Please enter integers only.");
				line = readLine.next();
				continue;
			}
			else {
				tempInt = readInts.nextInt();
				if (tempInt >= 1 || tempInt <= 5) {
					intInput.add(tempInt);
				}
				else {
					System.out.println("Please enter values between 1-5");
					// clear the input everytime loop runs
					intInput.clear();
					continue;
				}
			}

		}

		// Convert from ArrayList to primitive array to conform to code
		int[] intTokens = new int[intInput.size()];
		for(int i=0;i<intInput.size();i++){
			intTokens[i] = intInput.get(i);
		}

		boolean duplicate = false;

		//checks for in range
		for(int n=0; n<intTokens.length; n++){
			temp = intTokens[n];
			if (temp > 5 || temp < 0) {
				num_in_range = false;
				break;
			}
			else{
				num_in_range = true;
			}
		}
		//checks for duplicate input
		for(int i = 0; i < intTokens.length; i++){
			temp = intTokens[i];
			for(int j = i + 1; j < intTokens.length; j++){
				if(temp == intTokens[j]){
					duplicate = true;
					break;
				}
			}
		}
		//handles the extra discard with an Ace.
		//if users decides to discard the ace they forfeit the extra discard
		for(int n=0; n<intTokens.length; n++){
			cardNumber = intTokens[n];
			cardVal = player_hand.get(cardNumber-1).getValue();
			if (cardVal == 13) {
				System.out.println("Are you sure you want to discard the Ace?");
				System.out.println("You would lose the chance to discard 4 cards.");
				System.out.println("Please confirm by entering your input again");
				check_discarding_ace = false;
				break;
			}
			else{
				check_discarding_ace = true;
			}
		}

		// if the user tries to discard more cards than allowed, keep reading inputs
		while(line.isEmpty() || (intTokens.length > maxDiscard) || num_in_range == false || duplicate == true || check_discarding_ace == false){
			if(intTokens.length > maxDiscard){
				System.out.println("Attempting to discard more than the max number of cards allowed: " + maxDiscard);
			}
			if (num_in_range == false) {
				System.out.println("Input is out of range, please enter only number 1-5");
			}
			if(duplicate == true){
				System.out.println("Attempting to discard same card multiple times, please enter only number 1-5");
				duplicate = false;
			}
			line = readLine.nextLine();
			if(line.isEmpty()){
				System.out.println("Input cannot be blank, please enter the cards again");
				continue;
			}
			
			for(int n=0; n<intTokens.length; n++){
				temp = intTokens[n];
				if (temp > 5 || temp <=0) {
					num_in_range = false;
					break;
				}
				else{
					num_in_range = true;
				}
			}
			for(int i = 0; i < intTokens.length; i++){
				temp = intTokens[i];
				for(int j = i+1; j < intTokens.length; j++){
					if(temp == intTokens[j]){
						duplicate = true;
						break;
					}
				}
			}

			if (check_discarding_ace == false) {
				if (intTokens.length > 3) {
					System.out.println("You have to keep the ace if you wish to discard 4 cards");
					System.out.println("Otherwise, you may discard 3 cards including the Ace ");
					continue;
				}
				else {
					check_discarding_ace = true;
				}

			}


		}

		// now that we have the correct amount of cards to discard, we sort the input
		numToDiscard = intTokens.length;
		Arrays.sort(intTokens);
		// then convert the ints from string to int
		sorted = new int[intTokens.length];
		for(int k=0;k<intTokens.length;k++){
			try {
				sorted[k] = intTokens[k];
			} catch (NumberFormatException nfe) {};
		}

		//If they user doesn't want to discard then they enter 1 0
		//this changes numTodiscard;
		if(intTokens.length == 1 && sorted[0] == 0)
			numToDiscard = 0;

		// test to see the int array
		//System.out.println("The sorted line of input is" + Arrays.toString(sorted));


		int debug_value = 0;
		int index_check = 1;

		for (int i=0;i<numToDiscard;i++){
			debug_value = sorted[i] - index_check;
			player_hand.get(debug_value).printCard();
			// remove from hand
			discardpile.insert_card(player_hand.remove(debug_value));
			index_check++;
		}
		if(numToDiscard != 0)
			System.out.println("are discarded, drawing cards to fill hand\n");
		// draw cards to fill hand

		for(int i=0;i<numToDiscard;i++){
			player_hand.add(deck.drawCard());
			top_of_deck++;
		}
		//resest players hand and sorts it again:
		if(numToDiscard != 0){
			for(int i = 0; i < 5; i++){
				player_hand.get(i).reset_Matching();
			}
			handEvaluator.hand_pairing(player_hand);
		}
		after_discard = true;

	}

}
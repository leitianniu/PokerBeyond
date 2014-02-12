/*Adrian Campos
  acampo24@uic.edu

  Tianniu Lei
  tlei2@uic.edu
	
  Game:
  Initiates the deck and discard pile. Initiates the User hand and based on user input, the opponents.

  After calling evaluation functions, Game determines the winner. In the case of a tie,
  Game determines which players tied and prints info accordingly.
 */
import java.io.*;
import java.util.*;


public class Game{

	//deals with initiating hands, decks, and determines winner
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Hello welcome to 5 Card Poker.");
		System.out.println("How many computer opponents do you want to face (0-3 only)? ");
		int opponents = 0;
		boolean AINumberFlag = true;
		while (scanner.hasNext()) {
			if (!scanner.hasNextInt()) {
				System.out.println("Integers only please");
				scanner.next();
				continue;
			}
			else {
				opponents = scanner.nextInt();
				if(opponents < 0 || opponents > 3){
					System.out.println("Value out of bounds please enter a new value: ");
					continue;
				}
//				else {
//					break;
//				}
//				if(scanner.hasNext()){
//					if(AINumberFlag == true){
//						System.out.println("You may only enter 1 value");
//					}
//					AINumberFlag = false;
//					continue;
//				}
				else {
					break;
				}
			}
		}

		List<List<Card>> all_players = new LinkedList<List<Card>>();
		List<Card> player_hand = new ArrayList<>();
		List<Card> ai_hand1 = new ArrayList<>();
		List<Card> ai_hand2 = new ArrayList<>();
		List<Card> ai_hand3 = new ArrayList<>();
		all_players.add(player_hand);


		//checks how many opponents user choose and adds to a List of List<Card>
		if(opponents == 1){
			all_players.add(ai_hand1);
		}
		else if (opponents == 2){
			all_players.add(ai_hand1);
			all_players.add(ai_hand2);
		}
		else if (opponents == 3){
			all_players.add(ai_hand1);
			all_players.add(ai_hand2);
			all_players.add(ai_hand3);		
		}

		System.out.println("\nThe deck is being shuffled...\n");
		CardPile discardpile = new CardPile();
		CardPile deck = new CardPile(true);
	
		System.out.println("Cards will now be dealt to " + (opponents + 1) + " players.\n");
		deck.deal_cards(all_players);
		

		//Sorting the hands by value/pairings:
		for(int i = 0; i < all_players.size(); i++){
			handEvaluator.hand_pairing(all_players.get(i));
		}

		//printing all the hands 
		UserPlayer.print_phand(player_hand);

		//sets discard
		UserPlayer.discard_draw(player_hand, deck, discardpile);
       	for(int i = 1; i < all_players.size(); i++){
			AIPlayer.discard_draw(all_players.get(i), deck, discardpile, i);
		}


		//Evaluating hands
		int[] val = new int[all_players.size()];
		UserPlayer.print_phand(player_hand);
       	val[0]  = handEvaluator.hand_calculator(player_hand, true);
       	//System.out.println(val[0]);
       	for(int i = 1; i < all_players.size(); i++){
       		System.out.print("Computer hand " + i +": ");
			AIPlayer.print_aihand(all_players.get(i));
			val[i] = handEvaluator.hand_calculator(all_players.get(i), true);
			//System.out.println(val[i]);
		}


		//determines winner
		int winner = 0;
		boolean tie = false;
		int check = 2;
		List<Integer> tie_index = new ArrayList<Integer>();
		for(int i = 1; i < all_players.size(); i++){
			if(val[i] > val[winner]){
				winner = i;
				tie = false;
			}
			else if(val[i] == val[winner]){
				check = handEvaluator.tie_breaker(all_players.get(i), all_players.get(i-1));
				if(check == 1){
					winner = i;
					tie = false;
				}
				else if(check == 0){
					tie = true;
					tie_index.add(i);
				}
			}
		}
		//if a tie happens calls tie_breaker
		if(tie == true){
			tie_index.add(winner);

			System.out.println("There is a tie! The winners are:");
			for(int i = 0; i < tie_index.size(); i++){
				check = tie_index.get(i);
				if(check == 0){
					System.out.println("You the player");
				}
				else if(check == 1){
					System.out.println("Computer 1");
				}
				else if(check == 2){
					System.out.println("Computer 2");
				}		
				else if(check == 3){
					System.out.println("Computer 3");
				}		

			}
		}
		else{
			System.out.println("The winner is: ");

			if(winner == 0){
				System.out.println("You the player");
			}
			else if(winner == 1){
				System.out.println("Computer 1");
			}
			else if(winner == 2){
				System.out.println("Computer 2");
			}		
			else if(winner == 3){
				System.out.println("Computer 3");
			}		
		}

		System.exit(0);
	}
}
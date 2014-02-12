/*Adrian Campos
  acampo24@uic.edu

  Tianniu Lei
  tlei2@uic.edu
	
  CardPile:
  Deals with lists of Cards(deck, discard pile).
  Shuffles deck, deals out cards and allows for card insertion into the piles (discard pile only).
 */
import java.io.*;
import java.util.*;

public class CardPile{
	//Card pile = new Card[52];
	List<Card> pile= new ArrayList<>();
	private int num_of_cards;
	private boolean discard;

	//constructor for discard
	public CardPile(){
		num_of_cards = 0;
		discard = true;
	}

	//constructor for the deck 
	public CardPile(boolean shuffle){
		SuitHandler(pile, 0, 12, 'C');
		SuitHandler(pile, 13, 25, 'H');
		SuitHandler(pile, 26, 38, 'D');
		SuitHandler(pile, 39, 51, 'S');
		num_of_cards = 52;
		discard = false;
		if(shuffle){
			Shuffle_Pile(pile);
		}
	}

	//randomizes the deck
	private void Shuffle_Pile(List<Card> pile){
		Collections.shuffle(pile);
	}

	//helper function that deals with the suit
	private void SuitHandler(List<Card> pile, int lo, int hi, char suit){
		char[] rank = {'A', '2', '3', '4', '5', '6', '7', '8', '9'
						, 'T', 'J', 'Q', 'K'};
		for(int i = lo; i < hi + 1; i++){
			pile.add(new Card(rank[i%13], suit));
		}
	}

	//prints pile
	public void PilePrint(){
		for(int i = 0; i < num_of_cards; i++){
			pile.get(i).printCard();
		}
		System.out.print("\n");
	}

	//hands at one card at a time to all players until each have 5 cards.
	public void deal_cards(List<List<Card>> all_players ){
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < all_players.size(); j++){
				all_players.get(j).add(drawCard());
			}
		}
	}
	//removes card from deck and gives it to player
	public Card drawCard(){
		if(num_of_cards == 0){
			System.out.println("No more cards to draw.");
			return null;
		}
		if(discard == true){
			System.out.println("Cannot draw from this pile.");
			return null;
		}
		num_of_cards--;
		return pile.remove(0);
	}

	//used to insert cards into discard pile
	public void insert_card(Card thrown_away){
		pile.add(thrown_away);
		num_of_cards++;

	}
}
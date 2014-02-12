/*Adrian Campos
  acampo24@uic.edu

  Tianniu Lei
  tlei2@uic.edu
  
  Card:
  Holds important information about a single Card: rank, suit, value

  This class also includes an int matching, that helps determine if 
  the player's hand contain pairs.
 */

import java.io.*;
import java.util.*;
public class Card implements Comparable {
	
	private char rank;
	private char suit;

	//matching is used to determine if a card has
	//the same value as another card in the hand
	//0 matching = no cards of the same kind
	//1 matching = 1 other cards of the same kind
	//2 matching = 2 other cards of the same kind
	private int matching;
	private int value;

	static private boolean init_vflag = false;
	static List<Character> hand_values = new ArrayList<Character>();

	//constructor for card
	public Card(char initRank, char initSuit){
		rank = initRank;
		suit = initSuit;
		matching = 0;

		//since the hand value is static we only need to initiate one time
		//don't need to call it repeatedly for each card
		if(init_vflag == false)
			init_vlist();
		value = hand_values.indexOf(new Character(rank));
	}

	//this overrides the compare function in Comparable library
	//helps sort the hand when callling Collections.sort();
	@Override
	public int compareTo(Object o){
		Card c = (Card) o;

		int check = compare(c.matching, matching);

		return check != 0 ? check
						  : compare(c.value, value);
	}

	//helper function for override compareTo
	private static int compare(int a, int b){
		return a - b;
	}

	//prints rank and suit
	public void printCard(){

		System.out.print(rank + "" + suit + " ");
	}

	//incr matching variable
	public void incr_matching(){
		matching++;
	}

	//gets the rank
	public char getRank(){
		return rank;
	}

	//gets the suit
	public char getSuit(){
		return suit;
	}

	//gets the value
	public int getValue(){
		return value;
	}

	//gets the matchinv value
	public int getMatching(){
		return matching;
	}

	//resets the matching value. used for after discarding and adding new cards
	public void reset_Matching(){
		matching = 0;
	}


	private static void init_vlist(){

		//Since rank uses char have to place actual values into
		// a list and then compare them to the ranks
		hand_values.add(new Character('0'));
		hand_values.add(new Character('2'));
		hand_values.add(new Character('3'));
		hand_values.add(new Character('4'));
		hand_values.add(new Character('5'));
		hand_values.add(new Character('6'));
		hand_values.add(new Character('7'));
		hand_values.add(new Character('8'));
		hand_values.add(new Character('9'));
		hand_values.add(new Character('T'));
		hand_values.add(new Character('J'));
		hand_values.add(new Character('Q'));
		hand_values.add(new Character('K'));
		hand_values.add(new Character('A'));

		init_vflag = true;
	}

}
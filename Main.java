package main;

import java.util.Random;

public class Main {

	/** Number of doors **/
	static final int NB_DOORS = 3;
	
	/** Seed the RNG **/
	static Random rand = new Random();

	/** Random between 1 and NB_DOORS **/
	public static int randomDoor() {
		return rand.nextInt(NB_DOORS) + 1;
	}
	
	/** Behind the door **/
	public enum Prize {
		CAR,
		GOAT
	}
	
	/**
	 * Play the game once.
	 * @return true if switching door is winning
	 */
	public static boolean playMontyGame() {
		/** Prize[0] is unused. Door 1 is Prize[1], door 3 is Prize[3] */
		Prize[] prize = new Prize[NB_DOORS+1];
		
		// All goats except 1 random car
		for (int i = 0; i < prize.length; i++) {
			prize[i] = Prize.GOAT;
		}
		
		int winningDoor = randomDoor();		
		prize[winningDoor] = Prize.CAR;
		//System.out.println("Bonne porte : " + bonnePorte);
		
		// pick a door
		int chosenDoor = randomDoor();
		//System.out.println("Porte choisie : " + chosenDoor);
		
		// reveal a goat => not the chosen door and not a car
		int doorToReveal = randomDoor();
		while (doorToReveal == chosenDoor || prize[doorToReveal] == Prize.CAR) {
			doorToReveal = randomDoor();
		}
		//System.out.println("Porte révélée : " + doorToReveal);
		
		// is switch correct?
		if (chosenDoor == winningDoor) {
			//System.out.println("Ne pas changer de porte");
			return false;
		}
		//System.out.println("Changer de porte");
		return true;
	}
	
	public static void main(String[] args) {
		
		float nbWinBySwitch = 0;
		float nbLoseBySwitch = 0;
		float nbMontyGamesPlayed = 10000000;
		
		for (int i = 0; i < nbMontyGamesPlayed; i++) {
			if (playMontyGame()) {
				nbWinBySwitch++;
			}
			else {
				nbLoseBySwitch++;
			}
		}
		
		System.out.println("nbTimesShouldSwitch : " + nbWinBySwitch);
		System.out.println("nbTimesShouldNotSwitch : " + nbLoseBySwitch);
		System.out.println("Probability to win by switching : " + 100 * nbWinBySwitch/nbMontyGamesPlayed + "%");
	}
}

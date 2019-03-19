package main;

import java.util.Random;

public class Main {

	/** Number of doors **/
	static final int NB_DOORS = 100;
	
	/** Array of prizes behind doors.
	 * Prize[0] is unused. 
	 * Prize[1] is behind door 1
	 * Prize[2] is behind door 2... */
	static Prize[] prize = new Prize[NB_DOORS+1];
	
	/** Seed the RNG **/
	static Random rand = new Random();

	/** Random between 1 and NB_DOORS **/
	public static int randomDoor() {
		return rand.nextInt(NB_DOORS) + 1;
	}
	
	/** Behind the door **/
	public enum Prize {
		CAR,
		GOAT,
		GOAT_REVEALED
	}
	
	/**
	 * Reveal all doors but 2 (chosen door and one unopened door)
	 * @param chosenDoor the door that can't be revealed, other than the car
	 */
	public static void revealDoors(int chosenDoor) {
		
		// Reveal all doors but 2. If NB_DOORS > 3, more than 1 door will be revealed
		for (int i = 2; i < NB_DOORS; i++) {

			// Try a door at random, if doorToReveal is chosenDoor or a car or already revealed, pick another one
			int doorToReveal = randomDoor();
			
			while (prize[doorToReveal] != Prize.GOAT || doorToReveal == chosenDoor) {
				doorToReveal = randomDoor();
			}
			
			// Found an unopened and unchosen door with a goat behind: reveal it
			prize[doorToReveal] = Prize.GOAT_REVEALED;

			//System.out.println("Porte révélée : " + doorToReveal);
		}
	}
	
	/**
	 * Play the game once.
	 * @return true if switching door is winning
	 */
	public static boolean playMontyGame() {
		
		// All goats except 1 random car
		for (int i = 1; i < prize.length; i++) {
			prize[i] = Prize.GOAT;
		}
		
		int winningDoor = randomDoor();		
		prize[winningDoor] = Prize.CAR;
		//System.out.println("Bonne porte : " + bonnePorte);
		
		// Pick a door
		int chosenDoor = randomDoor();
		//System.out.println("Porte choisie : " + chosenDoor);
		
		// Reveal all doors but 2 (chosen door and one unopened door)
		revealDoors(chosenDoor);
		
		// Did we win if we switched?
		return chosenDoor != winningDoor;
	}
	
	/**
	 * main()
	 */
	public static void main(String[] args) {
		
		float nbWinBySwitch = 0;
		float nbLoseBySwitch = 0;
		float nbMontyGamesPlayed = 100000;
		
		for (int i = 0; i < nbMontyGamesPlayed; i++) {
			if (playMontyGame()) {
				nbWinBySwitch++;
				//System.out.println("Changer de porte");
			}
			else {
				nbLoseBySwitch++;
				//System.out.println("Ne pas changer de porte");
			}
		}
		
		System.out.println("nbWinBySwitch : " + nbWinBySwitch);
		System.out.println("nbLoseBySwitch : " + nbLoseBySwitch);
		System.out.println("Probability to win by switching : " + 100 * nbWinBySwitch/nbMontyGamesPlayed + "%");
	}
}

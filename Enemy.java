package Battleship;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;

public class Enemy extends Battleship {

	static Point[] ship2Spot = new Point[2];  //ships location on board
	static Point[] ship3Spot = new Point[3];//ships location on board
	static Point[] ship3sSpot = new Point[3];//ships location on board
	static Point[] ship4Spot = new Point[4];//ships location on board
	static Point[] ship5Spot = new Point[5];//ships location on board

	static int ship2Count = 0; //ships counter variable
	static int ship3Count = 0;//ships counter variable
	static int ship3sCount = 0;//ships counter variable
	static int ship4Count = 0;//ships counter variable
	static int ship5Count = 0;//ships counter variable

	static int ship2hp;  //ships current health
	static int ship3hp;//ships current health
	static int ship4hp;//ships current health
	static int ship5hp;//ships current health
	static int ship3shp;//ships current health


	static int enemySpots[][] = new int[10][10];  // 2d array, represents enemy board, where ships are represented by different numbers




	/* 
	 * The placeShips() method, will be called when the user has placed all their ships and pressed the start button.
	 * This method will randomly place all 5 enemy ships on the 2d array (enemy board): enemySpots[][].
	 * The method will 100% randomize the placement along with the rotation of all 5  enemy ships.
	 */
	public static void placeShips() {
		int ship = 6;    //which ship it is placing
		int length = 3;  // length of the ship it is placing
		boolean available; //if that spot is available for the ship to be placed on (not already taken by another ship)

		//will loop until all ships are placed
		while(ship>1) {
			boolean placing = true;

			//will loop until the selected ship is placed
			while(placing) {
				int x = ((int) (Math.random()*10));     //random number 0-9, represents where on the board the ships X tip will be
				int y = ((int) (Math.random()*10));     //random number 0-9, represents where on the board the ships Y tip will be
				int turn = ((int) (Math.random()*10));  //random number 0-9, represents if the ship is turned or not
				int xb = x;                              //same as variable a, but will be altered dependent on the ships length to check if spot is available
				int yb = y;								//same as variable a, but will be altered dependent on the ships length to place the ship
				available = true;

				//if the ship does not extend out of the board
				if(turn>=5&&10-y>ship || turn<5&&10-x>ship ){
					//Check to see if the random spot is available, meaning no other ship part is already there
					try {
						for(int i = 0;i<length;i++) {
							//if the spot is not 0, that means that another ship is there and the spot is not available
							if(enemySpots[x][y] != 0) {
								available = false;
							}
							//whether the ship is turned 
							if(turn>=5) {
								y++;
							}
							else {
								x++;
							}

						}
					}
					catch(Exception e) {
					}
					//if the spot is available, the ship will be placed there
					if(available) {
						placing = false;
						//the ships number will be put into the 2d array enemy board 
						for(int i = 0;i<length;i++) {
							enemySpots[xb][yb] = ship;
							//whether the ship is turned 
							if(turn>=5) {
								yb++;
							}
							else {
								xb++;
							}

						}
					}
				}
			}
			//switch to the next ship once the previous one has been placed.
			if(ship == 6) {
				length = 5;
			}
			else {
				length--;
			}
			ship--;

		}

		//prints out the 2d board array, for testing purposes
		
		System.out.println("\n"+enemySpots[0][0]+" "+enemySpots[1][0]+" "+enemySpots[2][0]+" "+enemySpots[3][0]+" "+enemySpots[4][0]+" "+enemySpots[5][0]+" "+enemySpots[6][0]+" "+enemySpots[7][0]+" "+enemySpots[8][0]+" "+enemySpots[9][0]+
				"\n"+enemySpots[0][1]+" "+enemySpots[1][1]+" "+enemySpots[2][1]+" "+enemySpots[3][1]+" "+enemySpots[4][1]+" "+enemySpots[5][1]+" "+enemySpots[6][1]+" "+enemySpots[7][1]+" "+enemySpots[8][1]+" "+enemySpots[9][1]+
				"\n"+enemySpots[0][2]+" "+enemySpots[1][2]+" "+enemySpots[2][2]+" "+enemySpots[3][2]+" "+enemySpots[4][2]+" "+enemySpots[5][2]+" "+enemySpots[6][2]+" "+enemySpots[7][2]+" "+enemySpots[8][2]+" "+enemySpots[9][2]+
				"\n"+enemySpots[0][3]+" "+enemySpots[1][3]+" "+enemySpots[2][3]+" "+enemySpots[3][3]+" "+enemySpots[4][3]+" "+enemySpots[5][3]+" "+enemySpots[6][3]+" "+enemySpots[7][3]+" "+enemySpots[8][3]+" "+enemySpots[9][3]+
				"\n"+enemySpots[0][4]+" "+enemySpots[1][4]+" "+enemySpots[2][4]+" "+enemySpots[3][4]+" "+enemySpots[4][4]+" "+enemySpots[5][4]+" "+enemySpots[6][4]+" "+enemySpots[7][4]+" "+enemySpots[8][4]+" "+enemySpots[9][4]+
				"\n"+enemySpots[0][5]+" "+enemySpots[1][5]+" "+enemySpots[2][5]+" "+enemySpots[3][5]+" "+enemySpots[4][5]+" "+enemySpots[5][5]+" "+enemySpots[6][5]+" "+enemySpots[7][5]+" "+enemySpots[8][5]+" "+enemySpots[9][5]+
				"\n"+enemySpots[0][6]+" "+enemySpots[1][6]+" "+enemySpots[2][6]+" "+enemySpots[3][6]+" "+enemySpots[4][6]+" "+enemySpots[5][6]+" "+enemySpots[6][6]+" "+enemySpots[7][6]+" "+enemySpots[8][6]+" "+enemySpots[9][6]+
				"\n"+enemySpots[0][7]+" "+enemySpots[1][7]+" "+enemySpots[2][7]+" "+enemySpots[3][7]+" "+enemySpots[4][7]+" "+enemySpots[5][7]+" "+enemySpots[6][7]+" "+enemySpots[7][7]+" "+enemySpots[8][7]+" "+enemySpots[9][7]+
				"\n"+enemySpots[0][8]+" "+enemySpots[1][8]+" "+enemySpots[2][8]+" "+enemySpots[3][8]+" "+enemySpots[4][8]+" "+enemySpots[5][8]+" "+enemySpots[6][8]+" "+enemySpots[7][8]+" "+enemySpots[8][8]+" "+enemySpots[9][8]+
				"\n"+enemySpots[0][9]+" "+enemySpots[1][9]+" "+enemySpots[2][9]+" "+enemySpots[3][9]+" "+enemySpots[4][9]+" "+enemySpots[5][9]+" "+enemySpots[6][9]+" "+enemySpots[7][9]+" "+enemySpots[8][9]+" "+enemySpots[9][9]);


	}

	/**what is on this spot on the 2d array board
	 * 
	 * @param x spot on board
	 * @param y spot on board
	 * @return  what is on that spot, empty or ship: which ship
	 */
	public static int checkSpot(int x, int y) {
		return enemySpots[x][y];
	}

	/**when a user clicks a spot, the enemy board will be updated where it clicked
	 * 
	 * @param x spot on board
	 * @param y spot on board
	 * @param s which ship
	 */
	public static void attackSpot(int x, int y, int s) {
		if(s==9) {
			switch(enemySpots[x][y]) {
			case 2:
				ship2hp++;
				break;
			case 3:
				ship3hp++;
				break;
			case 6:
				ship3shp++;
				break;
			case 4:
				ship4hp++;
				break;
			case 5:
				ship5hp++;
				break;
			}
		}
		enemySpots[x][y] = s;
	}

	/**
	 * stores ships location on the board
	 */
	public static void shipLocaleSet() {
		for(int i = 0; i<10; i++) {
			for(int t = 0; t<10; t++) {
				switch(enemySpots[i][t]) {
				case 2:
					ship2Spot[ship2Count] = new Point(i,t);
					ship2Count++;
					break;
				case 3:
					ship3Spot[ship3Count] = new Point(i,t);
					ship3Count++;
					break;
				case 6:
					ship3sSpot[ship3sCount] = new Point(i,t);
					ship3sCount++;
					break;
				case 4:
					ship4Spot[ship4Count] = new Point(i,t);
					ship4Count++;
					break;
				case 5:
					ship5Spot[ship5Count] = new Point(i,t);
					ship5Count++;
					break;

				}
			}
		}
	}

	/**
	 * checks each ships health to see if it has been destroyed
	 */
	public static void checkShipHP() {
		if(ship2hp ==2) {
			for(int i = 0; i<2;i++) {
				spotMarker[ship2Spot[i].x+ship2Spot[i].y*10].setBorder(BorderFactory.createLineBorder(new Color(213,27,27), 4));
			}
		}
		if(ship3hp ==3) {
			for(int i = 0; i<3;i++) {
				spotMarker[ship3Spot[i].x+ship3Spot[i].y*10].setBorder(BorderFactory.createLineBorder(new Color(27,27,129), 4));
			}
		}
		if(ship3shp ==3) {
			for(int i = 0; i<3;i++) {
				spotMarker[ship3sSpot[i].x+ship3sSpot[i].y*10].setBorder(BorderFactory.createLineBorder(new Color(141,30,169), 4));
			}
		}
		if(ship4hp ==4) {
			for(int i = 0; i<4;i++) {
				spotMarker[ship4Spot[i].x+ship4Spot[i].y*10].setBorder(BorderFactory.createLineBorder(new Color(30,169,104), 4));
			}
		}
		if(ship5hp ==5) {
			for(int i = 0; i<5;i++) {
				spotMarker[ship5Spot[i].x+ship5Spot[i].y*10].setBorder(BorderFactory.createLineBorder(new Color(188,188,139), 4));
			}
		}
	}

	/** Checks if the user has won the game
	 * 
	 * @return has the user won
	 */
	public static boolean hasPlayerWon() {
		if(ship2hp==2&&
				ship3hp==3&&
				ship3shp==3&&
				ship4hp==4&&
				ship5hp==5) {
			return true;
		}
		else {
			return false;
		}
	}
}

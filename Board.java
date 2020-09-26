package Battleship;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;

public class Board extends Battleship{

	//if the ship is on board, it will stick/clip to grid, otherwise it wont.
	static boolean[] onBoard= {false,false,false,false,false};

	//after releasing each ship, their location will be set to 1 meaning taken
	static int taken[][] = new int[10][10]; 		

	static Point lastShipHit;   //last spot a ship was hit
	static Point lastHit;       //last turns hit
	static Point lastHitex;     //last turns hit in board coordinates
	static int lastHitA;        //what type of hit was last turn, ship or empty
	static boolean shipDestroyed; //has the ship thats it's attacking been destroyed
	static boolean spotAvailable; //is that spot that the AI wants to attack available/ not already been attacked or off board.
	static int lastHitDirec = 0; //which direction was the last ship hit in 
	static int shipAttacking; //which ship is being attacked.
	static boolean shipDead[] = new boolean[]{true,false,false,false,false,false,false};
	static int a; //Whether its a +1 or -1 to the last hit
	static int b; //Whether its for x or for y 
	static int c; //adder/subtracter
	static Point firstShipHit; //the first point a ship was hit

	static Point[] ship2Spot = new Point[2];  //where the ships tip is
	static Point[] ship3Spot = new Point[3];  //where the ships tip is 
	static Point[] ship3sSpot = new Point[3]; //where the ships tip is
	static Point[] ship4Spot = new Point[4];  //where the ships tip is
	static Point[] ship5Spot = new Point[5];  //where the ships tip is

	static int ship2Count = 0; //counter variable for this ship
	static int ship3Count = 0;//counter variable for this ship
	static int ship3sCount = 0;//counter variable for this ship
	static int ship4Count = 0;//counter variable for this ship
	static int ship5Count = 0;//counter variable for this ship

	static int ship2hp;        //how many of its spots have been hit 
	static int ship3hp;//how many of its spots have been hit 
	static int ship4hp;//how many of its spots have been hit 
	static int ship5hp;//how many of its spots have been hit 
	static int ship3shp;//how many of its spots have been hit 

	static int x;  //desired x spot on board
	static int y;  //desired y spot on board

	/**When the user places a ship somewhere, it will be documented on a 2d array board
	 * 
	 * @param x        the ships tip x spot
	 * @param y        the ships tip y spot
	 * @param turned   is the ship turned
	 * @param ship     which ship is being placed
	 * @return         ships location to make sure its proper
	 */
	public static Point setTaken(int x, int y,boolean turned,int ship) {

		int h =0;    //height
		int w =0;    //width
		int repeats = ship;
		if(repeats ==6) {
			repeats = 3;
		}
		if(x>=162) {
			if(!turned) {
				for(int i= 0; i<repeats;i++ ) {

					h =((x-162)/52);
					w =(((y-43)+(i*53))/53);

					if(taken[h][w]==0||taken[h][w]==ship) {

						taken[h][w] = ship;
					}
					else {
						ship-=2;
						onBoard[ship] = false;
						return new Point (0,0);
					}					
				}
			}
			else {
				for(int i= 0; i<repeats;i++ ) {
					h =(((x-162)+(i*52))/52);
					w =((y-43)/53);
					if(taken[h][w]==0||taken[h][w]==ship) {

						taken[h][w] = ship;
					}
					else {
						ship-=2;
						onBoard[ship] = false;
						return new Point (0,0);
					}	
				}
			}
		}
		//printBoard();
		return new Point(x,y);

	}

	/** If the ship has been moved off its previous spot, thos spots will be reset on the 2d array board
	 * 
	 * @param x     the ships old x tip
	 * @param y     the ships old y tip
	 * @param ship  which ship
	 * @return      ships location to make sure its proper
	 */
	public static Point resetTaken(int x, int y,int ship){

		for(int X =0; X<10; X++) {
			for(int Y =0; Y<10; Y++) {
				if(taken[X][Y]==ship) {
					taken[X][Y] =0;
				}
			}
		}

		return new Point(x,y);
	}

	/** when moving the ship, this makes sure the ship is locking onto the board and staying on it
	 * For the ships x position
	 * 
	 * @param ship    which ship is being moved
	 * @param length  the length of the ship
	 * @param shipX   ships current X location
	 * @param turned  is the ship turned
	 * @return        The ships new X location
	 */
	public static int boardSetX(int ship, int length, int shipX, boolean turned){
		ship-=2;
		length/=55;
		int MSX = 0;
		shipX+=17;

		if(shipX>=150) {
			onBoard[ship] = true;
		}
		if(shipX<=170) {
			if(onBoard[ship] ) {
				MSX = 162;
			}
			else {
				if(shipX<=0) {
					MSX = 0;
				}
				else {
					MSX = shipX-17;
				}
			}
		}

		else if(shipX >= (630-((length-1)*52))) {
			if(turned)
				MSX = (630-((length-1)*52));
			else {
				if  (shipX >= 630)
					MSX = 630;
				else
					MSX = gridAlignX(shipX)-8;
			}
		}
		else {

			MSX = gridAlignX(shipX)-8;
		}

		return (int)MSX ;
	}

	/** when moving the ship, this makes sure the ship is locking onto the board and staying on it
	 * For the ships y position
	 * 
	 * @param ship    which ship is being moved
	 * @param length  the length of the ship
	 * @param shipY   ships current Y location
	 * @param turned  is the ship turned
	 * @return        The ships new Y location
	 */
	public static int boardSetY (int ship, int length, int shipY, boolean turned){

		double shipHeldY = shipY;	
		double MSY = 0;
		ship-=2;
		length/=55;
		length--;

		if(shipHeldY >= (520-length*53)) {
			if(turned) {

				if  (shipHeldY >= 520)
					MSY = 520;
				else {
					if(!onBoard[ship]) {
						MSY = shipHeldY;
					}
					else {
						MSY = gridAlignY(shipHeldY);
					}
				}
			}
			else {
				MSY = (520-(length*53));
			}
		}

		else if(!onBoard[ship]) {
			if(shipHeldY <=0) {
				MSY = 0;
			}
			else {
				MSY = shipHeldY;
			}
		}
		else if(shipHeldY <=98) {
			MSY = 43;
		}

		else {

			MSY = gridAlignY(shipHeldY)+53;
		}


		return  (int)MSY ;
	}


	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



	/**aligns the ship to the board grid
	 * 
	 * @param x  ships current x
	 * @return   ships new board aligned x
	 */
	public static int gridAlignX(double x) {
		int GX = (((int)(x/52))*52)+14;
		return GX;
	}
	
	/**aligns the ship to the board grid
	 * 
	 * @param y  ships current y
	 * @return   ships new board aligned y
	 */
	public static int gridAlignY(double y) {
		int GY = (((int)(y/53))*53)-10;
		return GY;
	}


	/** Checks if the board is full
	 * 
	 * @return whether the board is full or not
	 */
	public static boolean boardFull() {
		int ship2 = 0;
		int ship3 = 0;
		int ship3s = 0;
		int ship4 = 0;
		int ship5 = 0;

		for(int i = 0; i<10; i++) {
			for(int t = 0; t<10; t++) {
				switch(taken[i][t]) {
				case 2:
					ship2++;
					break;
				case 3:
					ship3++;
					break;
				case 4:
					ship4++;
					break;
				case 5:
					ship5++;
					break;
				case 6:
					ship3s++;
					break;

				}
			}
		}
		if(ship2 == 2&&
				ship3 == 3&&
				ship3s == 3&&
				ship4 == 4&&
				ship5 == 5) {
			return true;
		}
		else {
			return false;
		}
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
/**
 * sets all positions on the 2d array board to 0
 */
	public static void resetBoard() {
		for(int i = 0; i<10; i++) {
			for(int t = 0; t<10; t++) {
				taken[i][t] = 0;
			}
		}
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//for trouble shooting
//	public static void printBoard() {
//		System.out.println("\n"+taken[0][0]+" "+taken[1][0]+" "+taken[2][0]+" "+taken[3][0]+" "+taken[4][0]+" "+taken[5][0]+" "+taken[6][0]+" "+taken[7][0]+" "+taken[8][0]+" "+taken[9][0]+
//				"\n"+taken[0][1]+" "+taken[1][1]+" "+taken[2][1]+" "+taken[3][1]+" "+taken[4][1]+" "+taken[5][1]+" "+taken[6][1]+" "+taken[7][1]+" "+taken[8][1]+" "+taken[9][1]+
//				"\n"+taken[0][2]+" "+taken[1][2]+" "+taken[2][2]+" "+taken[3][2]+" "+taken[4][2]+" "+taken[5][2]+" "+taken[6][2]+" "+taken[7][2]+" "+taken[8][2]+" "+taken[9][2]+
//				"\n"+taken[0][3]+" "+taken[1][3]+" "+taken[2][3]+" "+taken[3][3]+" "+taken[4][3]+" "+taken[5][3]+" "+taken[6][3]+" "+taken[7][3]+" "+taken[8][3]+" "+taken[9][3]+
//				"\n"+taken[0][4]+" "+taken[1][4]+" "+taken[2][4]+" "+taken[3][4]+" "+taken[4][4]+" "+taken[5][4]+" "+taken[6][4]+" "+taken[7][4]+" "+taken[8][4]+" "+taken[9][4]+
//				"\n"+taken[0][5]+" "+taken[1][5]+" "+taken[2][5]+" "+taken[3][5]+" "+taken[4][5]+" "+taken[5][5]+" "+taken[6][5]+" "+taken[7][5]+" "+taken[8][5]+" "+taken[9][5]+
//				"\n"+taken[0][6]+" "+taken[1][6]+" "+taken[2][6]+" "+taken[3][6]+" "+taken[4][6]+" "+taken[5][6]+" "+taken[6][6]+" "+taken[7][6]+" "+taken[8][6]+" "+taken[9][6]+
//				"\n"+taken[0][7]+" "+taken[1][7]+" "+taken[2][7]+" "+taken[3][7]+" "+taken[4][7]+" "+taken[5][7]+" "+taken[6][7]+" "+taken[7][7]+" "+taken[8][7]+" "+taken[9][7]+
//				"\n"+taken[0][8]+" "+taken[1][8]+" "+taken[2][8]+" "+taken[3][8]+" "+taken[4][8]+" "+taken[5][8]+" "+taken[6][8]+" "+taken[7][8]+" "+taken[8][8]+" "+taken[9][8]+
//				"\n"+taken[0][9]+" "+taken[1][9]+" "+taken[2][9]+" "+taken[3][9]+" "+taken[4][9]+" "+taken[5][9]+" "+taken[6][9]+" "+taken[7][9]+" "+taken[8][9]+" "+taken[9][9]);
//	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**this AI randomly attacks a spot on the players board, if a shit is hit, it will be targeted until destroyed
	 *  
	 * @return if a ship has been hit
	 */
	public static int enemyAttack() {
		x = 0;
		y = 0;
		spotAvailable = false;
		if(shipDead[shipAttacking]) {
			shipAttacking = 0;
			lastHitDirec = 0;
		}
		if(!shipDead[shipAttacking]) {
			if(lastHitDirec ==0||lastHitA==8) {
				while(!spotAvailable) {
					x = firstShipHit.x;
					y = firstShipHit.y;
					System.out.println("up");
					a  =  ((int) (Math.random()*10)); //Whether its a +1 or -1 to the last hit
					b  =  ((int) (Math.random()*10)); //Whether its for x or for y to the last hit
					if(a>=5) {
						c = -1;
					}
					else {
						c = 1;
					}
					if(b>=5) {
						try {
							if(taken[x][y+c]!=9&&taken[x][y+c]!=8) {
								y+=c;
								spotAvailable = true;
								if(c==1) {
									lastHitDirec = 1;
								}
								else {
									lastHitDirec = 2;
								}

							}
						}
						catch(Exception e) {
							spotAvailable = false;
							lastHitDirec = 0;
						}
					}
					else {
						try {
							if(taken[x+c][y]!=9&&taken[x+c][y]!=8) {
								x+=c;
								spotAvailable = true;
								if(c==1) {
									lastHitDirec = 3;
								}
								else {
									lastHitDirec = 4;
								}
							}
						}
						catch(Exception e) {
							spotAvailable = false;
							lastHitDirec = 0;
						}
					}

				}
				if(taken[x][y]==0) {
					lastHitDirec = 0;
				}
			}
			else {
				x = lastShipHit.x;
				y = lastShipHit.y;
				switch(lastHitDirec) {
				case 1:
					try {
						if(taken[x-1][y]==9||taken[x-1][y]==8)
							spotAvailable = false;
						if(taken[x][y+1]!=9&&taken[x][y+1]!=8) {
							y++;
							spotAvailable = true;
						}
					}
					catch(Exception e) {
						spotAvailable = false;
					}
					break;
				case 2:
					try {
						if(taken[x-1][y]==9||taken[x-1][y]==8)
							spotAvailable = false;
						if(taken[x][y-1]!=9&&taken[x][y-1]!=8) {
							y--;
							spotAvailable = true;
						}
					}
					catch(Exception e) {
						spotAvailable = false;
					}
					break;
				case 3:
					try {
						if(taken[x-1][y]==9||taken[x-1][y]==8)
							spotAvailable = false;
						if(taken[x+1][y]!=9&&taken[x+1][y]!=8) {
							x++;
							spotAvailable = true;
						}
					}
					catch(Exception e) {
						spotAvailable = false;
					}
					break;
				case 4:
					try {
						if(taken[x-1][y]==9||taken[x-1][y]==8)
							spotAvailable = false;
						if(taken[x-1][y]!=9&&taken[x-1][y]!=8) {
							x--;
							spotAvailable = true;
						}

					}
					catch(Exception e) {
						spotAvailable = false;
					}
					break;
				}


				if(!spotAvailable) {

					x = firstShipHit.x;
					y = firstShipHit.y;

					switch(lastHitDirec) {

					case 1:
						y--;
						break;
					case 2:
						y++;
						break;
					case 3:
						x--;
						break;
					case 4:
						x++;
						break;
					}
				}
			}
		}
		else {

			x = ((int) (Math.random()*10));     //random number 0-9, represents where on the board the X attacked spot
			y = ((int) (Math.random()*10));     //random number 0-9, represents where on the board the Y attacked spot

			while(taken[x][y]==9||taken[x][y]==8) {
				x = ((int) (Math.random()*10)); 
				y = ((int) (Math.random()*10));  
			}
		}

		if(taken[x][y]==0) {
			lastHit =new Point(x,y);
			lastHitex = new Point((x*52),(y*53));
			lastHitA = 8;
			taken[x][y]=8;
			return 8;

		}
		else {
			if (shipAttacking == 0) {
				firstShipHit=new Point(x,y);

			}
			shipAttacking = taken[x][y];
			attackSpot(shipAttacking);
			lastShipHit=new Point(x,y);
			lastHit =new Point(x,y);
			lastHitA = 9;
			lastHitex = new Point((x*52),(y*53));
			taken[x][y]=9;
			return 9;
		}		
	}

	/**
	 * remembers ships positions
	 */
	public static void shipLocaleSet() {
		for(int i = 0; i<10; i++) {
			for(int t = 0; t<10; t++) {
				switch(taken[i][t]) {
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
	
	/** Attacks a certain ship on the board, update the ships health
	 * 
	 * @param s  which ship was attacked
	 */
	public static void attackSpot(int s) {

		switch(s) {
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
	
	/**
	 * check whether or not a ship has been destroyed
	 */
	public static void checkShipHP() {
		if(ship2hp ==2) {
			for(int i = 0; i<2;i++) {
				shipDead[2]= true;
				enemySpotMarker[ship2Spot[i].x+ship2Spot[i].y*10].setBorder(BorderFactory.createLineBorder(new Color(213,27,27), 4));
			}
		}
		if(ship3hp ==3) {
			for(int i = 0; i<3;i++) {
				shipDead[3]= true;
				enemySpotMarker[ship3Spot[i].x+ship3Spot[i].y*10].setBorder(BorderFactory.createLineBorder(new Color(27,27,129), 4));
			}
		}
		if(ship3shp ==3) {
			for(int i = 0; i<3;i++) {
				shipDead[6]= true;
				enemySpotMarker[ship3sSpot[i].x+ship3sSpot[i].y*10].setBorder(BorderFactory.createLineBorder(new Color(141,30,169), 4));
			}
		}
		if(ship4hp ==4) {
			for(int i = 0; i<4;i++) {
				shipDead[4]= true;
				enemySpotMarker[ship4Spot[i].x+ship4Spot[i].y*10].setBorder(BorderFactory.createLineBorder(new Color(30,169,104), 4));
			}
		}
		if(ship5hp ==5) {
			for(int i = 0; i<5;i++) {
				shipDead[5]= true;
				enemySpotMarker[ship5Spot[i].x+ship5Spot[i].y*10].setBorder(BorderFactory.createLineBorder(new Color(188,188,139), 4));
			}
		}
	}


    //return lastHitex
	public static Point getLastHit() {
		return lastHitex;
	}
	//return lastHit
	public static Point getHitXY() {
		return lastHit;
	}

	/**Checks if the AI has won
	 * 
	 * @return whether the AI has won or not
	 */
	public static boolean hasAIWon() {
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

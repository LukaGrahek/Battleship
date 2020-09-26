package Battleship;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.Dimension;

public class Battleship {

	private JFrame frame;                  //JFrame Application
	static boolean started = false;        //Has the game started (user pressed "start" button)    
	static boolean titlePressed = false;   //Has the "vs comp" button been pressed
	static int mouseX;                     //x location of the mouse
	static int mouseY;                     //y location of the mouse
	static JLabel[] spotMarker;            //when the player attacks a spot
	static JLabel[] enemySpotMarker;       //when the enemy attacks

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Battleship window = new Battleship();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * start the loading screen then
	 * Create the application.
	 */
	public Battleship() {
		Load LOAD = new Load();
		LOAD.setVisible(true);
		start();
		new Thread(new Runnable() {
			public void run() {
				while(!Load.finLoad) {
					frame.setVisible(false);		
				}
				frame.setVisible(true);
			}
		}).start();



	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * Initialize the contents of the frame. layered panes (one pane is for movable objects, one is for non movable objects, and third pane is to go on top of the board)
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private void start() {


		//creating the JFrame
		frame = new JFrame();
		final Dimension paneSize = new Dimension(808,740);   
		frame.getContentPane().setPreferredSize(paneSize);
		frame.setLocation(570, 100);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(Battleship.class.getResource("/Battleship/TLicon.png")));


		//Creating the layered panes
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 818, 750);
		frame.getContentPane().add(layeredPane);
		layeredPane.setLayout(null);

		//back panel (background board and buttons)
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, 818, 750);
		layeredPane.add(mainPanel,1);

		//middle panel for dragging ships
		JPanel dragP = new JPanel();
		dragP.setLayout(null);
		dragP.setBounds(0, 0, 818, 600);
		dragP.setOpaque(false);
		layeredPane.add(dragP,0);

		//top panel for text and spot markers
		JPanel cover = new JPanel();
		cover.setBounds(162,42, 520, 530);
		cover.setLayout(null);
		cover.setVisible(false);
		cover.setOpaque(false);
		layeredPane.add(cover,0);

		/*
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 * Initialize the title Screen
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 */


		//garage door that transitions between title page and board
		JLabel Gdoor = new JLabel("");
		Gdoor.setBounds(0, -751, 860, 751);
		Gdoor.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/GRdoor.jpg")));
		mainPanel.add(Gdoor);

		//title on the title page
		JLabel Title = new JLabel("");
		Title.setBounds(-20, 0, 860, 291);
		Title.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/title.jpg")));
		mainPanel.add(Title);

		//ship gif on the title page
		JLabel ShipGif = new JLabel("");
		ShipGif.setBounds(0, 291, 850, 313);
		ShipGif.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ShipgifFin.gif")));
		mainPanel.add(ShipGif);

		//"vs computer" button on title page
		JLabel VsCompBtn = new JLabel(new ImageIcon(getClass().getResource("/Battleship/compBig.jpg")));
		VsCompBtn.setCursor(Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		VsCompBtn.setBounds(262, 631, 296, 85);
		mainPanel.add(VsCompBtn);

		//background on the title page
		JLabel btnBack = new JLabel(new ImageIcon(getClass().getResource("/Battleship/Buttonback.jpg")));
		btnBack.setBounds(-20, 604, 860, 147);
		mainPanel.add(btnBack);

		/*
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 * Initialize each ships
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 */

		//1x2 ship
		JLabel ship2 = new JLabel("");
		ship2.setCursor(Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		ship2.setBounds(0, 0, 55, 110);
		ship2.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship2.png")));

		//1x3 ship
		JLabel ship3 = new JLabel("");
		ship3.setCursor(Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		ship3.setBounds(55, 0, 55, 165);
		ship3.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship3.png")));

		//1x3 submarine
		JLabel ship3s = new JLabel("");
		ship3s.setCursor(Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		ship3s.setBounds(0, 110, 55, 165);
		ship3s.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship3s.png")));

		//1x4 ship
		JLabel ship4 = new JLabel("");
		ship4.setCursor(Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		ship4.setBounds(55, 165, 55, 230);
		ship4.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship4.png")));

		//1x5 ship
		JLabel ship5 = new JLabel("");
		ship5.setCursor(Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		ship5.setBounds(0, 275, 55, 275);
		ship5.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship5.png")));

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//when the user clicks/ presses/ drags any ship
		final MouseAdapter ma = new MouseAdapter() {
			private JLabel selectedShip = null; //Clicked label.
			private Point selectedShipLocation = null; //Location of label in panel when it was clicked.
			private Point panelClickPoint = null; //Panel's click point.
			//private boolean turning = false;

			//when the user presses the panel it checks to see which ship has been pressed
			@Override
			public void mousePressed(final MouseEvent e) {
				//if the game hasn't started
				if(!started) {
					//Find which ship is being pressed
					final Component pressedComp = dragP.findComponentAt(e.getX(), e.getY());

					//If a ship is pressed, store it here
					if (pressedComp != null && pressedComp instanceof JLabel) {
						selectedShip = (JLabel) pressedComp;
						selectedShipLocation = selectedShip.getLocation();
						panelClickPoint = e.getPoint();
						//Added the following 2 lines in order to make selectedShip
						//paint over all others while it is pressed and dragged:
						dragP.setComponentZOrder(selectedShip, 0);
						selectedShip.repaint();
					}
					else {
						selectedShip = null;
						selectedShipLocation = null;
						panelClickPoint = null;
					}
					//if a ship has been pressed
					if (selectedShip != null
							&& selectedShipLocation != null
							&& panelClickPoint != null) {
						//if the ship is not turned set the new location based of the users mouse location
						if(selectedShip.getHeight()>selectedShip.getWidth()) {
							//since there are two ships with the length of three one of them needs to be called something different, so the submarine is ship 6 instead of 3
							if(selectedShip.equals(ship3s)) 
								selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),6));
							else
								selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),selectedShip.getHeight()/55));

						}
						//if the ship is turned move it differently
						else {
							//if the ship selected is the submarine
							if(selectedShip.equals(ship3s)) 
								selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),6));
							else
								selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),selectedShip.getWidth()/55));
						}
					}
				}
			}

			//when a ship is dragged
			@Override
			public void mouseDragged(final MouseEvent e) {
				if(!started) {
					if (selectedShip != null
							&& selectedShipLocation != null
							&& panelClickPoint != null) {

						final Point newPanelClickPoint = e.getPoint();

						//The new location is the press-location plus the length of the drag for each axis:

						final int newX = selectedShipLocation.x + (newPanelClickPoint.x - panelClickPoint.x),
								newY = selectedShipLocation.y + (newPanelClickPoint.y - panelClickPoint.y);


						if(selectedShip.getHeight()>selectedShip.getWidth()) {
							if(selectedShip.equals(ship3s))
								selectedShip.setLocation(Board.boardSetX(6, selectedShip.getHeight(), newX, false),Board.boardSetY(6, selectedShip.getHeight(), newY, false));
							else
								selectedShip.setLocation(Board.boardSetX(selectedShip.getHeight()/55, selectedShip.getHeight(), newX, false),Board.boardSetY(selectedShip.getHeight()/55, selectedShip.getHeight(), newY, false));

						}
						else {
							if(selectedShip.equals(ship3s))
								selectedShip.setLocation(Board.boardSetX(6, selectedShip.getWidth(), newX, true),Board.boardSetY(6, selectedShip.getHeight(), newY, true));
							else
								selectedShip.setLocation(Board.boardSetX(selectedShip.getWidth()/55, selectedShip.getWidth(), newX, true),Board.boardSetY(selectedShip.getWidth()/55, selectedShip.getHeight(), newY, true));

						}
					}
				}
			}

			//rotating of the ship 90 degrees
			@Override
			public void mouseClicked(final MouseEvent e) {
				if(!started) {
					if (selectedShip != null
							&& selectedShipLocation != null
							&& panelClickPoint != null) {
						int width = selectedShip.getWidth();
						int height =selectedShip.getHeight();
						final Point newPanelClickPoint = e.getPoint();
						//The new location is the press-location plus the length of the drag for each axis:
						final int newX = selectedShipLocation.x + (newPanelClickPoint.x - panelClickPoint.x),
								newY = selectedShipLocation.y + (newPanelClickPoint.y - panelClickPoint.y);

						if(selectedShipLocation.x>=162) {

							if(width<height) {

								if(selectedShip.equals(ship3s)) {
									selectedShip.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship3s_90.png")));
								}
								else {
									selectedShip.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship"+(height/55)+"_90.png")));
								}
							}
							else {
								if(selectedShip.equals(ship3s)) {
									selectedShip.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship3s.png")));
								}
								else {
									selectedShip.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship"+(width/55)+".png")));
								}
							}
							selectedShip.setSize(height,width);

							if(selectedShip.getHeight()>selectedShip.getWidth()) {
								if(selectedShip.equals(ship3s))
									selectedShip.setLocation(Board.boardSetX(6, selectedShip.getHeight(), newX, false),Board.boardSetY(6, selectedShip.getHeight(), newY, false));
								else
									selectedShip.setLocation(Board.boardSetX(selectedShip.getHeight()/55, selectedShip.getHeight(), newX, false),Board.boardSetY(selectedShip.getHeight()/55, selectedShip.getHeight(), newY, false));
							}
							else {
								if(selectedShip.equals(ship3s))
									selectedShip.setLocation(Board.boardSetX(6, selectedShip.getWidth(), newX, true),Board.boardSetY(6, selectedShip.getHeight(), newY, true));
								else
									selectedShip.setLocation(Board.boardSetX(selectedShip.getWidth()/55, selectedShip.getWidth(), newX, true),Board.boardSetY(selectedShip.getWidth()/55, selectedShip.getHeight(), newY, true));
							}
							if(selectedShip.getHeight()>selectedShip.getWidth()) {
								if(selectedShip.equals(ship3s)) 
									selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),6));
								else
									selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),selectedShip.getHeight()/55));

							}
							else {
								if(selectedShip.equals(ship3s)) 
									selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),6));
								else
									selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),selectedShip.getWidth()/55));
							}
							mouseReleased(e);

						}
					}
				}
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				if(!started) {
					if (selectedShip != null
							&& selectedShipLocation != null
							&& panelClickPoint != null) {


						if(selectedShip.getHeight()>selectedShip.getWidth()) {
							if(selectedShip.equals(ship3s)) {
								selectedShip.setLocation(Board.setTaken(selectedShip.getX(),selectedShip.getY(), false,6));
								if(selectedShip.getX() == 0&&selectedShip.getY() == 0) {
									selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),6));	
								}
							}
							else {
								selectedShip.setLocation(Board.setTaken(selectedShip.getX(),selectedShip.getY(), false,selectedShip.getHeight()/55));
								if(selectedShip.getX() == 0&&selectedShip.getY() == 0) {
									selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),selectedShip.getHeight()/55));	
								}
							}
						}
						else {
							if(selectedShip.equals(ship3s)) {
								selectedShip.setLocation(Board.setTaken(selectedShip.getX(),selectedShip.getY(), true,6));
								if(selectedShip.getX() == 0&&selectedShip.getY() == 0) {
									selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),6));
								}
							}
							else {
								selectedShip.setLocation(Board.setTaken(selectedShip.getX(),selectedShip.getY(), true,selectedShip.getWidth()/55));
								if(selectedShip.getX() == 0&&selectedShip.getY() == 0) {
									selectedShip.setLocation(Board.resetTaken(selectedShip.getX(),selectedShip.getY(),selectedShip.getWidth()/55));
								}
							}
						}
					}
				}
			}

		};
		dragP.addMouseMotionListener(ma);      //For mouseDragged(). Adds Mouse Motion Listener to the dragP JPane
		dragP.addMouseListener(ma);            //For mousePressed/Clicked(). Adds Mouse Listener to t he dragP JPane

		/*
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 * Initialize the Garage action, and board components
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 */

		//creates spot markers, X's or explosions
		spotMarker  = new JLabel[100];
		enemySpotMarker = new JLabel[100];


		//when highlighting a spot on the board
		JLabel boardDot = new JLabel("o");
		boardDot.setBounds(200,200, 52, 52);
		boardDot.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/boardHover2.gif")));
		boardDot.setVisible(false);
		boardDot.setOpaque(false);
		cover.add(boardDot);

		//if the game can not be started 
		JLabel startError = new JLabel("Not all ships are on the board");
		startError.setFont(new Font ("Stencil",Font.BOLD, 22));
		startError.setForeground(new Color(255,0,0));
		startError.setBounds(262, 700, 400, 50);
		startError.setOpaque(false);
		startError.setVisible(false);
		mainPanel.add(startError);

		//displays who's turn it is
		JLabel turnDisp = new JLabel("Your ships territory above!");
		turnDisp.setFont(new Font ("Stencil",Font.BOLD, 22));
		turnDisp.setForeground(new Color(0,20,0));
		turnDisp.setBounds(280, 580, 400, 50);
		turnDisp.setOpaque(false);
		turnDisp.setVisible(false);
		mainPanel.add(turnDisp);

		//auto place ships button
		JLabel autopButton = new JLabel("");
		autopButton.setCursor(Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		autopButton.setBounds(150, 620, 85, 85);
		autopButton.setVisible(false);
		autopButton.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/autopL.png")));
		mainPanel.add(autopButton);

		// start the game button, once all ships have been placed
		JLabel startButton = new JLabel("");
		startButton.setCursor(Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		startButton.setBounds(262, 620, 296, 85);
		startButton.setVisible(false);
		startButton.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/startButtonL.png")));
		mainPanel.add(startButton);

		//board background image
		JLabel board = new JLabel("");
		board.setBounds(107,0, 583, 572);
		board.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/Board.jpg")));
		board.setVisible(false);
		mainPanel.add(board);

		//background image
		JLabel boardBack = new JLabel("");
		boardBack.setBounds(0,0, 828, 751);
		boardBack.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/boardBack.jpg")));
		boardBack.setVisible(false);
		mainPanel.add(boardBack);


		/**
		 *  When the button on the title page has been pressed
		 */
		
		VsCompBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				VsCompBtn.setBounds(286, 639, 244, 70);
				VsCompBtn.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/compSmall.jpg")));

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				VsCompBtn.setBounds(262, 631, 296, 85);
				VsCompBtn.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/compBig.jpg")));

				if(!titlePressed) {
					titlePressed = true;
					new Thread(new Runnable() {
						public void run() {							
							for(int i =-751; i<=0; i++) {
								Gdoor.setLocation(-10, i);
								Gdoor.repaint();
								try {
									Thread.sleep(1,500000);
								} catch (InterruptedException e) {

									e.printStackTrace();
								}
							}
							dragP.revalidate();
							VsCompBtn.setVisible(false);
							btnBack.setVisible(false);
							ShipGif.setVisible(false);
							Title.setVisible(false);
							boardBack.setVisible(true);
							board.setVisible(true);
							startButton.setVisible(true);
							turnDisp.setVisible(true);

							for(int u =0; u>=-751; u--) {
								Gdoor.setLocation(-10, u);
								Gdoor.repaint();
								try {
									Thread.sleep(1,500000);
								} catch (InterruptedException e) {

									e.printStackTrace();
								}
							}
							autopButton.setVisible(true);
							dragP.add(ship2);
							dragP.add(ship3);
							dragP.add(ship3s);
							dragP.add(ship4);
							dragP.add(ship5);
							//must move to avoid visibility glitch
							ship2.move(55, 0);
							ship2.move(55, 500);
							ship2.move(0, 0);

						}
					}).start();
				}
			}
		});
		/*
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 * START button, what happens when it's pressed:
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 */

		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(!Board.boardFull()) {
					new Thread(new Runnable() {
						public void run() {
							startError.setVisible(true);
							try {					
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								System.err.println("Thread sleep error");
								e.printStackTrace();
							}	
							startError.setVisible(false);
						}
					}).start();
				}
				else {
					startError.setVisible(false);
					startButton.setBounds(286, 639, 244, 70);
					startButton.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/StartButtonS.png")));

				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(!Board.boardFull()) {
					startError.setVisible(true);
				}
				else {
					started = true;

					cover.setVisible(true);
					cover.setCursor(Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));

					dragP.revalidate();
					startButton.setBounds(262, 620, 296, 85);
					startButton.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/startButtonL.png")));
					new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(400);
								cover.setBorder(BorderFactory.createLineBorder(new Color(120,0,0), 4));
								startButton.setVisible(false);
								autopButton.setVisible(false);
								turnDisp.setText("Enemy field above. Attack!");
								turnDisp.setForeground(new Color(20,0,0));
								ship2.setVisible(false);
								ship3.setVisible(false);
								ship3s.setVisible(false);
								ship4.setVisible(false);
								ship5.setVisible(false);
							} catch (InterruptedException e) {
								System.err.println("Thread sleep error");
								e.printStackTrace();
							}
						}
					}).start();
					Enemy.placeShips();
					Board.shipLocaleSet();
					Enemy.shipLocaleSet();
				}
			}
		});

		/*
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 * AUTO PLACE button, what happens when it's pressed:
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 */

		autopButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				autopButton.setBounds(157, 627, 70, 70);
				autopButton.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/autopS.png")));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				autopButton.setBounds(150, 620, 85, 85);
				autopButton.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/autopL.png")));
				ship2.setLocation(0,0);
				ship3.setLocation(0,0);
				ship3s.setLocation(0,0);
				ship4.setLocation(0,0);
				ship5.setLocation(0,0);

				Board.resetBoard();

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
						int Xf = x;                             //for placing the ship JLabel X position
						int Yf = y;                             //for placing the ship JLabel Y position
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
									if(Board.taken[x][y] != 0) {
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
									Board.taken[xb][yb] = ship;
									//whether the ship is turned 
									if(turn>=5) {
										yb++;
									}
									else {
										xb++;
									}

								}

								switch(ship) {
								case 2:
									ship2.setLocation(((52*Xf)+162),((53*Yf)+43));
									if(turn>=5) {
										ship2.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship2.png")));
										ship2.setSize(55,110);
									}
									else {
										ship2.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship2_90.png")));
										ship2.setSize(110,55);
									}
									break;
								case 3:
									ship3.setLocation(((52*Xf)+162),((53*Yf)+43));
									if(turn>=5) {
										ship3.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship3.png")));
										ship3.setSize(55,165);
									}
									else {
										ship3.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship3_90.png")));
										ship3.setSize(165,55);
									}
									break;
								case 4:
									ship4.setLocation(((52*Xf)+162),((53*Yf)+43));
									if(turn>=5) {
										ship4.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship4.png")));
										ship4.setSize(55,220);
									}
									else {
										ship4.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship4_90.png")));
										ship4.setSize(220,55);
									}
									break;
								case 5:
									ship5.setLocation(((52*Xf)+162),((53*Yf)+43));
									if(turn>=5) {
										ship5.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship5.png")));
										ship5.setSize(55,275);
									}
									else {
										ship5.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship5_90.png")));
										ship5.setSize(275,55);
									}
									break;
								case 6:
									ship3s.setLocation(((52*Xf)+162),((53*Yf)+43));
									if(turn>=5) {
										ship3s.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship3s.png")));
										ship3s.setSize(55,165);
									}
									else {
										ship3s.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ship3s_90.png")));
										ship3s.setSize(165,55);
									}
									break;

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

				//Board.printBoard();
			}
		});

		/*
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 *  User clicks a spot on the enemy board, what happens:
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 */

		for(int i =0; i <=99; i++) {
			spotMarker[i] = new JLabel("×", SwingConstants.CENTER);
			enemySpotMarker[i] = new JLabel("×", SwingConstants.CENTER);
		}



		final MouseAdapter boardCover = new MouseAdapter() {


			boolean turning = false;



			@Override
			public void mousePressed(final MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int xs = ((int)(x/52));
				int ys =((int)(y/53));
				int userSpot =((ys*10)+xs); 
				new Thread(new Runnable() {
					public void run() {
						if(!turning ) {
							if(Enemy.checkSpot(xs,ys)!=9&&Enemy.checkSpot(xs,ys)!=8) {
								turning = true;

								if(!Board.hasAIWon()) {
									if(Enemy.checkSpot(xs,ys)==0) {


										spotMarker[userSpot].setFont(new Font ("ARIAL",Font.PLAIN, 0));
										spotMarker[userSpot].setForeground(new Color(30,60,150));					
										spotMarker[userSpot].setBounds(((int)(x/52))*52,((int)(y/53))*53, 52, 52);					
										spotMarker[userSpot].setOpaque(false);
										cover.add(spotMarker[userSpot]);			
										for(int k = 0;k<=84; k+=2) {
											try {
												Thread.sleep(12);		
											} catch (InterruptedException e) {
												System.err.println("Thread sleep error");
											}
											spotMarker[userSpot].setFont(new Font ("ARIAL",Font.PLAIN, k));
										}

										Enemy.attackSpot(((int)(x/52)),((int)(y/53)), 8);	

									}
									else {

										spotMarker[userSpot].setText("");
										spotMarker[userSpot].setBounds(((int)(x/52))*52,((int)(y/53))*53, 52, 52);
										spotMarker[userSpot].setOpaque(false);
										spotMarker[userSpot].setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/explosionGIF.gif")));
										cover.add(spotMarker[userSpot]);
										spotMarker[userSpot].revalidate();
										try {
											Thread.sleep(500);		
										} catch (InterruptedException e) {
											System.err.println("Thread sleep error");
										}
										spotMarker[userSpot].setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/explosion.png")));

										Enemy.attackSpot(((int)(x/52)),((int)(y/53)), 9);
									}

									Enemy.checkShipHP();
									Board.checkShipHP();
								}

								if(Enemy.hasPlayerWon()) {
									turnDisp.setText("You Have WON!");
									turnDisp.setForeground(new Color(0,200,0));
									turnDisp.setFont(new Font ("Stencil",Font.BOLD, 60));
									turnDisp.setBounds(60, 580, 600, 100);

								}


								if(!Enemy.hasPlayerWon()) {

									try {
										Thread.sleep(1000);		
									} catch (InterruptedException e) {
										System.err.println("Thread sleep error");
									}

									for(int i = 0; i<=99;i++) {
										try {
											spotMarker[i].setVisible(false);
											enemySpotMarker[i].setVisible(true);
										}
										catch(Exception e) {
										}
									}
									cover.setBorder(BorderFactory.createLineBorder(new Color(120,0,0), 0));
									cover.setCursor(Cursor.getPredefinedCursor (Cursor.DEFAULT_CURSOR));
									turnDisp.setText("Your ships field above!");
									turnDisp.setForeground(new Color(0,20,0));
									ship2.setVisible(true);
									ship3.setVisible(true);
									ship3s.setVisible(true);
									ship4.setVisible(true);
									ship5.setVisible(true);

									if(Board.enemyAttack()==8) {
										int a = Board.getHitXY().x+(Board.getHitXY().y*10);

										enemySpotMarker[a].setFont(new Font ("ARIAL",Font.PLAIN, 0));
										enemySpotMarker[a].setForeground(new Color(90,60,150));					
										enemySpotMarker[a].setBounds(0,0, 52, 52);	
										enemySpotMarker[a].setLocation(Board.getLastHit());
										enemySpotMarker[a].setOpaque(false);
										cover.add(enemySpotMarker[a],0);			
										for(int k = 0;k<=84; k+=2) {
											try {
												Thread.sleep(12);		
											} catch (InterruptedException e) {
												System.err.println("Thread sleep error");
											}
											enemySpotMarker[a].setFont(new Font ("ARIAL",Font.PLAIN, k));
										}
									}
									else {
										int b = Board.getHitXY().x+(Board.getHitXY().y*10);

										enemySpotMarker[b].setText("");
										enemySpotMarker[b].setBounds(0,0, 52, 52);	
										enemySpotMarker[b].setLocation(Board.getLastHit());
										enemySpotMarker[b].setOpaque(false);
										enemySpotMarker[b].setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/explosionGIF.gif")));
										cover.add(enemySpotMarker[b],0);
										enemySpotMarker[b].revalidate();
										try {
											Thread.sleep(500);		
										} catch (InterruptedException e) {
											System.err.println("Thread sleep error");
										}
										enemySpotMarker[b].setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/explosion.png")));
									}

									try {
										Thread.sleep(1700);		
									} catch (InterruptedException e) {
										System.err.println("Thread sleep error");
									}

									for(int i = 0; i<=99;i++) {
										try {
											spotMarker[i].setVisible(true);
											enemySpotMarker[i].setVisible(false);
										}
										catch(Exception e) {
										}
									}
									cover.setBorder(BorderFactory.createLineBorder(new Color(120,0,0), 4));
									cover.setCursor(Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
									turnDisp.setText("Enemy field above. Attack!");
									turnDisp.setForeground(new Color(20,0,0));
									ship2.setVisible(false);
									ship3.setVisible(false);
									ship3s.setVisible(false);
									ship4.setVisible(false);
									ship5.setVisible(false);
									turning = false;
								}

								if(Board.hasAIWon()) {
									turnDisp.setText("You Have LOST!");
									turnDisp.setForeground(new Color(200,0,0));
									turnDisp.setFont(new Font ("Stencil",Font.BOLD, 60));
									turnDisp.setBounds(20, 580, 600, 100);

								}
							}
						}
					}
				}).start();	

			}
			@Override
			public void mouseReleased(MouseEvent arg0) {

			}



			@Override
			public void mouseMoved(MouseEvent e)
			{
				if(!turning) {
					int x = e.getX();
					int y = e.getY();

					boardDot.setVisible(true);
					boardDot.setLocation(((int)(x/52))*52,((int)(y/53))*53);

				}
				else {
					boardDot.setVisible(false);
				}
			}
			@Override
			public void mouseExited(MouseEvent evt)
			{
				boardDot.setVisible(false);
			}

		};
		cover.addMouseMotionListener(boardCover);      //For mouseDragged(). Adds Mouse Motion Listener to the dragP JPane
		cover.addMouseListener(boardCover);  


	}// end of start()
}// end of class

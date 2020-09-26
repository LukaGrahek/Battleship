package Battleship;


import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Load extends JFrame {
	static boolean finLoad = false;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Load frame = new Load();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	

	public Load() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(670, 350, 601, 400);
		contentPane = new JPanel();		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(Battleship.class.getResource("/Battleship/TLicon.png")));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setBackground(new Color(0.0f,0.0f,0.0f,0.0f));


		JLabel shipLoad = new JLabel("LOADING");
		shipLoad.setBounds(0, 0, 601, 355);
		shipLoad.setIcon(new javax.swing.ImageIcon(Battleship.class.getResource("/Battleship/ShipLOAD.png")));
		contentPane.add(shipLoad);

		JProgressBar progressBar = new JProgressBar();

		progressBar.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
		progressBar.setBounds(51, 352, 486, 14);
		contentPane.add(progressBar);
		new Thread(new Runnable() {
			public void run() {
				boolean full = false;
				while(!full) {
					for(int i = 0; i< 100;  i++) {
						if(i<=33) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}
						progressBar.setValue(i);
						}
						if(i>=34&&i<=41) {
						try {
							Thread.sleep(130);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}
						progressBar.setValue(i);
						}
						if(i>=42) {
						try {
							Thread.sleep(25);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}
						progressBar.setValue(i);
						}

						if(i==99) {
							full = true;
							finLoad = true;
							contentPane.setVisible(false);
						}
					}
				}
			}
		}).start();
	}
}

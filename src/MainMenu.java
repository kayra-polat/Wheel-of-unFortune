import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

public class MainMenu extends Scene {

	private JButton playButton;
	private JButton exitButton;

	MainMenu() {
		super();
		super.frame.getContentPane().setBackground(new Color(204, 0, 51));
		super.frame.setBounds(550, 300, 800, 450);

		JLabel logo = new JLabel("Wheel of un-Fortune");
		logo.setForeground(new Color(255, 255, 255));
		logo.setHorizontalTextPosition(SwingConstants.CENTER);
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setFont(new Font("Harlow Solid Italic", Font.PLAIN, 85));
		logo.setBounds(10, 11, 764, 202);
		super.frame.getContentPane().add(logo);

		JLabel signature = new JLabel("\u00A9 2020 - by CKB");
		signature.setForeground(new Color(255, 255, 255));
		signature.setHorizontalTextPosition(SwingConstants.CENTER);
		signature.setHorizontalAlignment(SwingConstants.CENTER);
		signature.setFont(new Font("Arial", Font.BOLD, 15));
		signature.setBounds(302, 372, 188, 39);
		super.frame.getContentPane().add(signature);

		initializeMenu();
	}

	private void initializeMenu() {

		playButton = new JButton("PLAY :)");
		playButton.setFocusable(false);
		playButton.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 30));
		playButton.setHorizontalTextPosition(SwingConstants.CENTER);
		playButton.setBackground(new Color(255, 255, 255));
		playButton.setBounds(313, 216, 158, 56);
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // DESTROY THE MAIN MENU AFTER THE PLAYER CLICKED "PLAY GAME
				GamePanel gameScene = new GamePanel();
				gameScene.frame.setVisible(true);
			}
		});

		exitButton = new JButton("exit :/");
		exitButton.setFocusable(false);
		exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
		exitButton.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 20));
		exitButton.setBackground(Color.WHITE);
		exitButton.setBounds(350, 293, 91, 33);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();				
			}
		});

		frame.getContentPane().add(playButton);
		frame.getContentPane().add(exitButton);
		frame.revalidate();
		frame.repaint();
	}
}

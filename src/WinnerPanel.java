import java.awt.Color;
import javax.swing.*;
import java.awt.Font;
import java.io.IOException;

public class WinnerPanel extends Scene {

	private JLabel prize1 = null; //CAR
	private JLabel prize2 = null; //PHONE
	private JLabel prize3 = null; //HOME
	private JLabel prize4 = null; //PS4
	private JLabel prize5 = null; //WASH

	WinnerPanel(int playerNumber, int totalPoint) {
		super();
		super.frame.setBounds(500, 300, 1000, 562);

		JLabel winnerLabel = new JLabel("Winner is PLAYER-" + playerNumber + " with total point of " + totalPoint + ".");
		winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winnerLabel.setForeground(Color.WHITE);
		winnerLabel.setFont(new Font("Forte", Font.BOLD, 40));
		winnerLabel.setBounds(0, 0, 984, 213);
		frame.getContentPane().add(winnerLabel);



		buildWinnerPanelGUI(totalPoint);
	}

	private void buildWinnerPanelGUI(int totalPoint) {

		AssetManager assets = new AssetManager();

		try {
			prize1 = new JLabel(new ImageIcon(assets.getImage("CAR")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		prize1.setBackground(Color.DARK_GRAY);
		prize1.setBounds(322, 226, 364, 274);
		prize1.setVisible(false);
		frame.getContentPane().add(prize1);

		try {
			prize2 = new JLabel(new ImageIcon(assets.getImage("PHONE")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		prize2.setBackground(Color.DARK_GRAY);
		prize2.setBounds(363, 231, 256, 281);
		prize2.setVisible(false);
		frame.getContentPane().add(prize2);

		try {
			prize3 = new JLabel(new ImageIcon(assets.getImage("HOME")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		prize3.setBackground(Color.DARK_GRAY);
		prize3.setBounds(337, 133, 321, 333);
		prize3.setVisible(false);
		frame.getContentPane().add(prize3);

		try {
			prize4 = new JLabel(new ImageIcon(assets.getImage("PS4")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		prize4.setBackground(Color.DARK_GRAY);
		prize4.setBounds(347, 239, 311, 237);
		prize4.setVisible(false);
		frame.getContentPane().add(prize4);

		try {
			prize5 = new JLabel(new ImageIcon(assets.getImage("WASH")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		prize5.setBackground(Color.DARK_GRAY);
		prize5.setBounds(340, 215, 320, 279);
		prize5.setVisible(false);
		frame.getContentPane().add(prize5);

		if (totalPoint <= 5000) {
			prize4.setVisible(true);
		}
		else if (totalPoint > 5000 && totalPoint <= 10000) {
			prize5.setVisible(true);
		}
		else if (totalPoint > 10000 && totalPoint <= 20000) {
			prize2.setVisible(true);
		}
		else if (totalPoint > 20000 && totalPoint < 30000) {
			prize1.setVisible(true);
		}
		else if (totalPoint >= 30000) {
			prize3.setVisible(true);
		}
	}
}

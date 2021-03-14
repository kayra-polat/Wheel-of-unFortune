import java.awt.Color;
import javax.swing.JFrame;

public class Scene {

	protected JFrame frame;

	Scene() {

		//** Main scaffold for Java Window Application
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(255, 255, 255));
		frame.getContentPane().setBackground(new Color(204, 0, 51));
		frame.setBounds(160, 100, 1600, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
}
import java.awt.EventQueue;

public class Driver {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					MainMenu menu = new MainMenu();
					menu.frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println("Trying to start again...");
					MainMenu menu = new MainMenu();
					menu.frame.setVisible(true);
				}
			}
		});
	}
}
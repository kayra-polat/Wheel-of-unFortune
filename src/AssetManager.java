import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class AssetManager {

	private String pathOfWheelObject = "";
	private String pathOfModeratorObject = "";
	private String pathOfWheelAnimation = "";
	private String pathOfLedStrip = "";
	private String pathOfLARGELedStrip = "";
	private String pathOfWarningSymbol = "";
	private String pathOfCar = "";
	private String pathOfHome = "";
	private String pathOfPhone = "";
	private String pathOfPS4 = "";
	private String pathOfWashMachine = "";

	public AssetManager() {
		File rootDir = new File(".");
		String absPath = rootDir.getAbsolutePath();
		absPath = absPath.substring(0, absPath.length() - 2);

		if (System.getProperty("os.name").startsWith("Windows")) {
			pathOfWheelObject = absPath + "\\src\\assets\\wheelImage.png";
			pathOfModeratorObject = absPath + "\\src\\assets\\moderatorImage.png";
			pathOfWheelAnimation = absPath + "\\src\\assets\\wheelAnimationFast.gif";;
			pathOfLedStrip = absPath + "\\src\\assets\\ledAnim.gif";
			pathOfLARGELedStrip = absPath + "\\src\\assets\\ledAnimBig.gif";
			pathOfWarningSymbol = absPath + "\\src\\assets\\warning.png";
			pathOfCar = absPath + "\\src\\assets\\prizes\\car.png";
			pathOfHome = absPath + "\\src\\assets\\prizes\\home.png";
			pathOfPhone = absPath + "\\src\\assets\\prizes\\iphone.png";
			pathOfPS4 = absPath + "\\src\\assets\\prizes\\ps4.png";
			pathOfWashMachine = absPath + "\\src\\assets\\prizes\\washing-machine.png";
		}
		else {
			pathOfWheelObject = absPath + "/src/assets/wheelImage.png";
			pathOfModeratorObject = absPath + "/src/assets/moderatorImage.png";
			pathOfWheelAnimation = absPath + "/src/assets/wheelAnimationFast.gif";;
			pathOfLedStrip = absPath + "/src/assets/ledAnim.gif";
			pathOfLARGELedStrip = absPath + "/src/assets/ledAnimBig.gif";
			pathOfWarningSymbol = absPath + "/src/assets/warning.png";
			pathOfCar = absPath + "/src/assets/prizes/car.png";
			pathOfHome = absPath + "/src/assets/prizes/home.png";
			pathOfPhone = absPath + "/src/assets/prizes/iphone.png";
			pathOfPS4 = absPath + "/src/assets/prizes/ps4.png";
			pathOfWashMachine = absPath + "/src/assets/prizes/washing-machine.png";
		}
	}

	public Image getImage(String imageName) throws IOException {

		if (imageName.equals("MODERATOR")) {

			File modImage = new File(pathOfModeratorObject);
			BufferedImage modImg = ImageIO.read(modImage);
			return modImg.getScaledInstance(265, 410, 1);
		}

		else if (imageName.equals("WHEEL")) {

			File wheelImage = new File(pathOfWheelObject);
			BufferedImage bfimg = ImageIO.read(wheelImage);
			return bfimg.getScaledInstance(450, 450, 2);
		}

		else if (imageName.equals("LED")) {

			File led = new File(pathOfLedStrip);
			BufferedImage bfimg = ImageIO.read(led);
			return bfimg.getScaledInstance(450, 450, 2);
		}

		else if (imageName.equals("WARN")) {
			File warn = new File(pathOfWarningSymbol);
			BufferedImage bfimg = ImageIO.read(warn);
			return bfimg.getScaledInstance(72, 72, 1);
		}

		else if (imageName.equals("CAR")) {
			File img = new File(pathOfCar);
			BufferedImage bfimg = ImageIO.read(img);
			return bfimg;
		}

		else if (imageName.equals("HOME")) {
			File img = new File(pathOfHome);
			BufferedImage bfimg = ImageIO.read(img);
			return bfimg;
		}

		else if (imageName.equals("PHONE")) {
			File img = new File(pathOfPhone);
			BufferedImage bfimg = ImageIO.read(img);
			return bfimg;
		}

		else if (imageName.equals("PS4")) {
			File img = new File(pathOfPS4);
			BufferedImage bfimg = ImageIO.read(img);
			return bfimg;
		}

		else if (imageName.equals("WASH")) {
			File img = new File(pathOfWashMachine);
			BufferedImage bfimg = ImageIO.read(img);
			return bfimg;
		}
		return null;
	}

	public String getPath(String pathName) {

		if (pathName.equals("WHEEL-ANIM")) {
			return pathOfWheelAnimation;
		}

		else if (pathName.equals("LED")) {
			return pathOfLedStrip;
		}

		else if (pathName.equals("LED-BIG")) {
			return pathOfLARGELedStrip;
		}
		return null;
	}
}

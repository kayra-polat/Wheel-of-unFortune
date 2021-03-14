import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

	private static boolean vowelMode = false;

	public static ArrayList<Integer> inputCheck(Word chosenWord, String input, int correctLetterCount) { 

		// return -1 whole word is true (VALID)
		// return 11...39: index numbers of letters (VALID)

		// return -2 no vowel letter (INVALID)
		// return -9: WRONG guess (INVALID)
		// return -10: Empty String (INVALID)

		if (correctLetterCount == chosenWord.getConsonantCount()) {
			vowelMode = true;
		}

		ArrayList<Integer> arr = new ArrayList<Integer>();

		input = input.toUpperCase();

		if (input.equalsIgnoreCase("")) {
			arr.add(-10);
		}

		else if (input.length() > 1) {
			if (input.equalsIgnoreCase(chosenWord.getValue())) 
				arr.add(-1);
			else
				arr.add(-9);
		}

		else if (input.length() == 1) {
			if (!vowelMode) {
				if (input.equals("E") || input.equals("U") || input.equals("I") || input.equals("O") || input.equals("A")) {
					arr.add(-2);
				}
				else if (chosenWord.getValue().contains(input))
					arr = GameLogic.findIndicesOfLetters(chosenWord, input);
				else {
					arr.add(-9);
				}
			}
			else {
				if (chosenWord.getValue().contains(input))
					arr = GameLogic.findIndicesOfLetters(chosenWord, input);
				else {
					arr.add(-9);
				}
			}
		}

		else if (input.length() < 1) {
			arr.add(-9);
		}

		else {
			arr.add(-9);
		}

		return arr;
	}

	public static ArrayList<Integer> prepareGridForWord(Word chosenWord) {

		ArrayList<Integer> indices = new ArrayList<Integer>();
		String[] splitted = chosenWord.getValue().split(" ");

		if (splitted.length == 1) {
			// fill grid indices: 11 ... 18
			for (int i = 11; i < 11+splitted[0].length(); ++i) {
				indices.add(i);
			}
		}
		else if (splitted.length == 2) {
			// fill grid indices: 11 ... 18 (+) 21 ... 28
			for (int i = 11; i < 11+splitted[0].length(); ++i) {
				indices.add(i);
			}
			for (int i = 21; i < 21+splitted[1].length(); ++i) {
				indices.add(i);
			}
		}
		else if (splitted.length == 3) {
			// fill grid indices: 11 ... 18 (+) 21 ... 28 (+) 31 ... 38
			for (int i = 11; i < 11+splitted[0].length(); ++i) {
				indices.add(i);
			}
			for (int i = 21; i < 21+splitted[1].length(); ++i) {
				indices.add(i);
			}
			for (int i = 31; i < 31+splitted[2].length(); ++i) {
				indices.add(i);
			}
		}

		return indices;
	}

	private static ArrayList<Integer> findIndicesOfLetters(Word word, String strLetter) {
		char letter = strLetter.charAt(0);
		char[] letters = word.getValue().toCharArray();
		ArrayList<Integer> indices = new ArrayList<Integer>();

		int index = 0;
		for (char key: letters) {
			if (key == letter)
				indices.add(index);
			++index;
		}
		return indices;
	}

	public static String getRandomWheelCell() {
		Random random = new Random();
		int key = random.nextInt(12);

		switch(key) {
		case 0:
			return "100";
		case 1:
			return "200";
		case 2:
			return "300";
		case 3:
			return "400";
		case 4:
			return "500";
		case 5:
			return "600";
		case 6:
			return "250";
		case 7:
			return "1000";
		case 8:
			return "800";
		case 9:
			return "900";
		case 10:
			return "BANKRUPT";
		case 11:
			return "PASS";

		default:
			return "100";
		}
	}
}


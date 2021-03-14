import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class WordManager {
	/*
	 * !!! SECRET WORD CONSTRAINTS: 
	 * 
	 *  - Max word number: 3
	 *  - Max letter number for each word: 8
	 *  - No integer or symbol character.
	 *  - Only English letters.
	 *  
	 *  */

	private static Word newWord;
	private ArrayList<Word> wordList = new ArrayList<Word>();
	private static String usedWords = "";

	WordManager() {
		try {
			File file;
			File rootDir = new File(".");
			String absPath = rootDir.getAbsolutePath();
			absPath = absPath.substring(0, absPath.length() - 2);

			if (System.getProperty("os.name").startsWith("Windows")) {
				file = new File(absPath + "\\src\\data\\WordList.txt");
			}
			else {
				file = new File(absPath + "/src/data/WordList.txt");
			}

			FileReader text = new FileReader(file);
			BufferedReader bfText = new BufferedReader(text);

			// Create Word objects from WordList.txt
			String theWord = "";
			while ((theWord = bfText.readLine()) != null) {

				Word tempWord = new Word(theWord);

				//** CHECK WORD CONSTRAINTS
				if (tempWord.getWordCount() <= 3) {

					if (tempWord.getWordCount() == 1) {
						if (tempWord.getValue().length() <= 8)
							wordList.add(new Word(theWord));
					}
					else if (tempWord.getWordCount() == 2) {
						if (tempWord.getValue().length() <= 17)
							wordList.add(new Word(theWord));
					}
					else if (tempWord.getWordCount() == 3) {
						if (tempWord.getValue().length() <= 26)
							wordList.add(new Word(theWord));
					}
				}
				else continue;
			}

			bfText.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Word getRandomSecretWord() {

		Random random = new Random();
		newWord = wordList.get(random.nextInt(wordList.size()));

		while(usedWords.contains(newWord.getValue()) || newWord.isUsed()) {
			newWord = wordList.get(random.nextInt(wordList.size()));
		}

		newWord.setUsed(true);
		usedWords += newWord.getValue();
		return newWord;
	}
}

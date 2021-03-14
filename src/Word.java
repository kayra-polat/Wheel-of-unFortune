
public class Word {

	private String value;
	private int count; // length without spaces
	private int consonantCount;
	private boolean isUsed;
	private int wordCount; // number of words in a Word object

	Word(String wordValue) {
		value = new String(wordValue);
		count = this.calculateCount();
		consonantCount = this.calculateConsonants();
		setUsed(false);
	}

	private int calculateConsonants() {

		int k = 0;

		for (int i = 0; i < this.value.length(); ++i) {
			char key = value.charAt(i);
			if (key != 'E' && key != 'U' && key != 'I' && key != 'O' && key != 'A' && key != ' ') {
				++k;
			}
		}

		return k;
	}

	private int calculateCount() {
		int count = 0;

		String[] split = value.split(" ");

		if (split.length == 1) {
			this.wordCount = 1;
			count = value.length();
		}
		else if (split.length == 2) {
			this.wordCount = 2;
			count = value.length() - 1;
		}
		else if (split.length == 3) {
			this.wordCount = 3;
			count = value.length() - 2;
		}
		else {
			count = value.length();
		}

		return count;
	}

	public int getConsonantCount() {
		return this.consonantCount;
	}

	public String getValue() {
		return this.value;
	}

	public int getCount() {
		return this.count;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
}
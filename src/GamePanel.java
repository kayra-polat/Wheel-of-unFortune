import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.util.ArrayList;

public class GamePanel extends Scene {

	private JTextField textField;
	private JPanel wordGrid;
	private static Word chosenWord;
	private static int playerNumber = 1;// 1 or 2
	private static int correctLetterCount = 0;
	private static int matchPointOfFirstPlayer = 0, matchPointOfSecondPlayer = 0;
	private static int roundPointsOfFirstPlayer = 0, roundPointsOfSecondPlayer = 0;
	private static int totalPointsOfFirstPlayer = 0, totalPointsOfSecondPlayer = 0;
	private static int wheelPoint = 0;
	private static String usedLetters = "";
	private JLabel moderatorObject, wheelObject, spinTipLabel, roundPoint1, roundPoint2, 
	matchPoint1, matchPoint2,total1, total2, playerNumberLabel, addPointLabel, warnString;
	private Timer timer, timer2 = null;
	private JButton wheelButton;

	public GamePanel() {
		super();
		buildGamePanelGUI();
	}

	private boolean isUsed(String input) {
		if (usedLetters.contains(input))
			return true;
		else
			return false;
	}

	private void disableUsedLetter(char theLetter) {
		usedLetters += theLetter;
	}

	private void addPoint(int playerNumber, int multiplier) {

		int add = wheelPoint * multiplier;

		if (playerNumber == 1) {
			roundPointsOfFirstPlayer += add;
			totalPointsOfFirstPlayer += add;
		}
		else {
			roundPointsOfSecondPlayer += add;
			totalPointsOfSecondPlayer += add;
		}	
	}

	private void removePoint(int playerNumber, int point) {
		if (playerNumber == 1) {
			roundPointsOfFirstPlayer -= point;
			totalPointsOfFirstPlayer -= point;
		}
		else {
			roundPointsOfSecondPlayer -= point;
			totalPointsOfSecondPlayer -= point;
		}	
	}

	private boolean checkWin(int playerNumber) {
		boolean hasWon = false;

		if (chosenWord.getCount() == correctLetterCount) {

			hasWon = true;
			if (roundPointsOfFirstPlayer > roundPointsOfSecondPlayer)
				++matchPointOfFirstPlayer;

			else if (roundPointsOfFirstPlayer < roundPointsOfSecondPlayer)
				++matchPointOfSecondPlayer;

			else if (roundPointsOfFirstPlayer == roundPointsOfSecondPlayer) {
				timer2 = new Timer(1750, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						warnString.setText("DRAW ROUND");
						timer2.stop();
						warnString.setText("-");
						frame.revalidate();
						frame.repaint();
					}
				});
				timer2.start();
			}
		}

		if (hasWon) {
			roundPointsOfFirstPlayer = 0;
			roundPointsOfSecondPlayer = 0;
			matchPoint1.setText(String.valueOf(matchPointOfFirstPlayer));
			matchPoint2.setText(String.valueOf(matchPointOfSecondPlayer));
			playerNumber = 1;
			correctLetterCount = 0;
			usedLetters = "";
			buildGamePanelGUI();
		}

		if (matchPointOfFirstPlayer == 3 || matchPointOfSecondPlayer == 3) {

			if (matchPointOfFirstPlayer > matchPointOfSecondPlayer)
				setWin(1);
			else
				setWin(2);
		}

		return hasWon;
	}

	private void setWin(int winnerPlayerNumber) {

		int totalPoint = 0;
		if (winnerPlayerNumber == 1)
			totalPoint = totalPointsOfFirstPlayer;
		else
			totalPoint = totalPointsOfSecondPlayer;

		frame.dispose();
		WinnerPanel prizePanel = new WinnerPanel(winnerPlayerNumber, totalPoint);
		prizePanel.frame.setVisible(true);
	}

	private void animateWheel() {

		AssetManager assets = new AssetManager();

		JLabel temp = wheelObject;
		temp.setIcon(new ImageIcon(assets.getPath("WHEEL-ANIM")));

		wheelObject = temp;
		frame.revalidate();
		frame.repaint();

		timer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				wheelButton.setEnabled(false);

				try {
					wheelObject.setIcon(new ImageIcon(assets.getImage("WHEEL")));

					String key = GameLogic.getRandomWheelCell();
					addPointLabel.setText(key);
					int point = 0;

					if (key.equals("BANKRUPT")) {

						warnString.setText("BANKRUPT!");
						timer2 = new Timer(1600, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {

								if (playerNumber == 1) {
									totalPointsOfFirstPlayer -= roundPointsOfFirstPlayer;
									roundPointsOfFirstPlayer = 0;

									roundPoint1.setText(String.valueOf(roundPointsOfFirstPlayer));
									total1.setText(String.valueOf(totalPointsOfFirstPlayer));
									playerNumber = 2;
								}
								else {
									totalPointsOfSecondPlayer -= roundPointsOfSecondPlayer;
									roundPointsOfSecondPlayer = 0;

									roundPoint2.setText(String.valueOf(roundPointsOfSecondPlayer));
									total2.setText(String.valueOf(totalPointsOfSecondPlayer));
									playerNumber = 1;
								}

								timer2.stop();
								warnString.setText("-");
								wheelButton.setEnabled(true);
								playerNumberLabel.setText("PLAYER: " + playerNumber);
								frame.revalidate();
								frame.repaint();
							}
						});
						timer2.start();
					}
					else if (key.equals("PASS")) {

						warnString.setText("PASS!");
						timer2 = new Timer(1500, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {

								// Iterate next player
								if (playerNumber == 1)
									playerNumber = 2;
								else
									playerNumber = 1;

								playerNumberLabel.setText("PLAYER: " + playerNumber);
								wheelButton.setEnabled(true);

								timer2.stop();
								warnString.setText("-");
								frame.revalidate();
								frame.repaint();
							}
						});
						timer2.start();

					}
					else {
						point = Integer.parseInt(key);
						textField.setEditable(true);
						textField.setEnabled(true);
						textField.setFocusable(true);
						spinTipLabel.setVisible(true);
					}
					wheelPoint = point;

				} catch (IOException e2) {
					e2.printStackTrace();
				}
				timer.stop();

				frame.revalidate();
				frame.repaint();
			}
		});
		timer.start();
	}

	private void buildGamePanelGUI() {

		//** Clear whole frame before initializing
		frame.getContentPane().removeAll();
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();

		//**AssetManager object
		AssetManager assets = new AssetManager();

		//** Choose random secret word from Words class
		WordManager words = new WordManager();
		chosenWord = words.getRandomSecretWord();
		System.out.println(chosenWord.getValue());

		JLabel warnImg = null;
		try {
			warnImg = new JLabel(new ImageIcon(assets.getImage("WARN")));
		} catch (IOException e2) {
			warnImg = new JLabel("");
			e2.printStackTrace();
		}
		warnImg.setBackground(new Color(0, 0, 0));
		warnImg.setOpaque(true);
		warnImg.setBounds(1083, 240, 72, 72);
		frame.getContentPane().add(warnImg);		

		//** WARNING INDICATOR
		warnString = new JLabel("-");
		warnString.setBackground(new Color(0, 0, 0));
		warnString.setForeground(new Color(255, 215, 0));
		warnString.setFont(new Font("Gill Sans MT", Font.BOLD, 30));
		warnString.setOpaque(true);
		warnString.setHorizontalAlignment(SwingConstants.CENTER);
		warnString.setBounds(1165, 240, 260, 72);
		frame.getContentPane().add(warnString);

		//** MODERATOR
		try {
			moderatorObject = new JLabel(new ImageIcon(assets.getImage("MODERATOR")));
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		moderatorObject.setBounds(710, 25, 260, 405);
		frame.getContentPane().add(moderatorObject);


		//** Word Grid to show chosen secret word.
		wordGrid = new JPanel();
		wordGrid.setForeground(new Color(255, 255, 255));
		wordGrid.setBorder(new LineBorder(new Color(64, 64, 64), 4));
		wordGrid.setBackground(Color.BLACK);
		wordGrid.setBounds(25, 25, 750, 395);
		frame.getContentPane().add(wordGrid);
		wordGrid.setLayout(new GridLayout(5, 10, 5, 5));

		// Initialize grid cells to deploy letters
		for (int i = 0; i < 50; ++i) {
			JLabel letterCell = new JLabel("");
			letterCell.setOpaque(true);
			letterCell.setBackground(Color.BLUE);
			wordGrid.add(letterCell);
		}

		// Create CYAN cells to deploy letter spaces.
		ArrayList<Integer> indices = GameLogic.prepareGridForWord(chosenWord);

		int k = 0;
		for (int j: indices) {
			String charValue = "";
			if (chosenWord.getValue().charAt(k) == ' ') {
				++k;
			}
			charValue = Character.toString(chosenWord.getValue().charAt(k));
			++k;

			JLabel newLetterCell = new JLabel(charValue);
			newLetterCell.setBackground(Color.CYAN);
			newLetterCell.setHorizontalTextPosition(SwingConstants.CENTER);
			newLetterCell.setHorizontalAlignment(SwingConstants.CENTER);
			newLetterCell.setFont(new Font("Tahoma", Font.PLAIN, 40));
			newLetterCell.setOpaque(true);
			newLetterCell.setForeground(Color.CYAN);

			wordGrid.remove(j);
			wordGrid.add(newLetterCell, j);
			frame.revalidate();
			frame.repaint();
		}

		//** Tip/Advise label after spinning for better UX
		spinTipLabel = new JLabel("You can guess now...");
		spinTipLabel.setForeground(new Color(255, 255, 255));
		spinTipLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		spinTipLabel.setBounds(916, 718, 157, 49);
		spinTipLabel.setVisible(false);
		frame.getContentPane().add(spinTipLabel);

		//** SPINNING WHEEL OBJECT				
		try {
			wheelObject = new JLabel(new ImageIcon(assets.getImage("WHEEL")));
			wheelObject.setBounds(1025, 350, 500, 500);
			frame.getContentPane().add(wheelObject);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//** WHEEL BUTTON
		wheelButton = new JButton("SPIN IT!");
		wheelButton.setFocusable(false);
		wheelButton.setFont(new Font("Consolas", Font.BOLD, 18));
		wheelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// onClick() =>
				wheelButton.setEnabled(false);
				animateWheel();
			}
		});
		wheelButton.setBounds(945, 758, 115, 56);
		frame.getContentPane().add(wheelButton);

		// led animations
		JLabel LED_ANIM = new JLabel("");
		LED_ANIM.setIcon(new ImageIcon(assets.getPath("LED")));
		LED_ANIM.setOpaque(false);
		LED_ANIM.setBounds(867, 560, 178, 100);
		frame.getContentPane().add(LED_ANIM);

		//** WHEEL POINT
		addPointLabel = new JLabel("_");
		addPointLabel.setForeground(new Color(255, 255, 255));
		addPointLabel.setFont(new Font("Forte", Font.ITALIC, 30));
		addPointLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		addPointLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addPointLabel.setBorder(new LineBorder(new Color(255, 255, 255), 5));
		addPointLabel.setBackground(new Color(153, 0, 51));
		addPointLabel.setOpaque(true);
		addPointLabel.setBounds(870, 575, 182, 67);
		frame.getContentPane().add(addPointLabel);

		//** Show who's turn.
		playerNumberLabel = new JLabel("PLAYER: 1");
		playerNumberLabel.setForeground(new Color(255, 255, 255));
		playerNumberLabel.setFont(new Font("Candara Light", Font.BOLD, 55));
		playerNumberLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		playerNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerNumberLabel.setBounds(51, 441, 750, 65);
		frame.getContentPane().add(playerNumberLabel);

		//** A progress bar object for purpose of decoration only.
		JProgressBar decorationBar1 = new JProgressBar();
		decorationBar1.setForeground(new Color(255, 255, 255));
		decorationBar1.setBorder(null);
		decorationBar1.setValue(100);
		decorationBar1.setOpaque(true);
		decorationBar1.setBounds(280, 493, 300, 4);
		frame.getContentPane().add(decorationBar1);

		//** Get player input every turn.
		textField = new JTextField();
		//textField.setRequestFocusEnabled(false);
		textField.setForeground(new Color(255, 255, 255));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBackground(new Color(0, 0, 51));
		textField.setFont(new Font("Tahoma", Font.PLAIN, 40));
		textField.setBounds(63, 517, 738, 56);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setEditable(false);

		ActionListener textFListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String input = textField.getText();

				textField.setText("");
				textField.revalidate();
				textField.repaint();

				ArrayList<Integer> checker = GameLogic.inputCheck(chosenWord, input, correctLetterCount);

				int key = checker.get(0);

				if (key != -1)
					wheelButton.setEnabled(true);

				if (key == -1) {
					correctLetterCount = chosenWord.getCount();
					addPoint(playerNumber, 10);

					warnString.setText("Round Win!");

					Component[] componenents = wordGrid.getComponents();
					ArrayList<Integer> labelIndices = new ArrayList<Integer>();

					int compIndex = 0;
					for (Component comp: componenents) {
						if (comp instanceof JLabel)
							labelIndices.add(compIndex);

						++compIndex;
					}

					for (int gridLocation: labelIndices) {
						// OPEN LETTERS on the wordGrid
						wordGrid.getComponent(gridLocation).setForeground(Color.BLACK);
						wordGrid.getComponent(gridLocation).setBackground(Color.WHITE);
						frame.revalidate();
						frame.repaint();
					}

					timer2 = new Timer(1500, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							checkWin(playerNumber);
							buildGamePanelGUI();

							timer2.stop();
							warnString.setText("-");
							frame.revalidate();
							frame.repaint();
						}
					});
					timer2.start();
				}

				else if (key == -2) {

					removePoint(playerNumber, 500);

					warnString.setText("No Vowel, yet!");
					timer2 = new Timer(1750, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							timer2.stop();
							warnString.setText("-");
							frame.revalidate();
							frame.repaint();
						}
					});
					timer2.start();

					// Iterate next player
					if (playerNumber == 1)
						playerNumber = 2;
					else
						playerNumber = 1;
				}

				//** WRONG GUESS
				else if (key == -9) {
					// Iterate next player
					if (playerNumber == 1)
						playerNumber = 2;
					else
						playerNumber = 1;

					warnString.setText("WRONG!");
					timer2 = new Timer(1500, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							timer2.stop();
							warnString.setText("-");
							frame.revalidate();
							frame.repaint();
						}
					});
					timer2.start();
				}

				//** EMPTY STRING
				else if (key == -10) {
					warnString.setText("Empty String");
					timer2 = new Timer(1350, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							timer2.stop();
							warnString.setText("-");
							frame.revalidate();
							frame.repaint();
						}
					});
					timer2.start();
				}

				else {
					int multiplier = 1;

					if (!isUsed(input.toUpperCase())) {
						for (int charLocation: checker) {

							Component[] componenents = wordGrid.getComponents();
							char theLetter = chosenWord.getValue().charAt(charLocation);
							ArrayList<Integer> labelIndices = new ArrayList<Integer>();

							int compIndex = 0;
							for (Component comp: componenents) {
								if (comp instanceof JLabel) {
									JLabel tmpLabel = (JLabel) comp;
									String labelText = tmpLabel.getText();
									if (input.equalsIgnoreCase(labelText)) {
										labelIndices.add(compIndex);
									}
								}

								++compIndex;
							}

							for (int gridLocation: labelIndices) {
								// OPEN LETTERS on the wordGrid
								wordGrid.getComponent(gridLocation).setForeground(Color.BLACK);
								wordGrid.getComponent(gridLocation).setBackground(Color.WHITE);
								frame.revalidate();
								frame.repaint();
							}

							multiplier = checker.size(); // how many times do the guessed letter repeats

							// Mark the letters as "used".
							++correctLetterCount;										
							disableUsedLetter(theLetter); // next turns anyone cannot guess same letter or word again
						}

						// Give Points depending on letter count(repeat)
						addPoint(playerNumber, multiplier);
						multiplier = 1;
						// WIN CHECK
						checkWin(playerNumber);

					}
					else {
						removePoint(playerNumber, 100);
						warnString.setText("Already used");
						timer2 = new Timer(1350, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								timer2.stop();
								warnString.setText("-");
								frame.revalidate();
								frame.repaint();
							}
						});
						timer2.start();
					}

					spinTipLabel.setVisible(false);
					textField.setEditable(false);

					// Iterate next player
					if (playerNumber == 1)
						playerNumber = 2;
					else
						playerNumber = 1;
				}

				playerNumberLabel.setText("PLAYER: " + playerNumber);

				roundPoint1.setText(String.valueOf(roundPointsOfFirstPlayer));
				roundPoint2.setText(String.valueOf(roundPointsOfSecondPlayer));
				total1.setText(String.valueOf(totalPointsOfFirstPlayer));
				total2.setText(String.valueOf(totalPointsOfSecondPlayer));
				matchPoint1.setText(String.valueOf(matchPointOfFirstPlayer));
				matchPoint2.setText(String.valueOf(matchPointOfSecondPlayer));

				if (correctLetterCount == chosenWord.getConsonantCount()) {
					warnString.setText("Vowel Mode: ON!");
					timer2 = new Timer(4000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							timer2.stop();
							warnString.setText("-");
							frame.revalidate();
							frame.repaint();
						}
					});
					timer2.start();
				}
				else {
					frame.revalidate();
					frame.repaint();
				}

			}
		};

		textField.addActionListener(textFListener);

		JLabel inputTipLabel = new JLabel("Wanna guess? Write above... :)");
		inputTipLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		inputTipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inputTipLabel.setForeground(Color.WHITE);
		inputTipLabel.setFont(new Font("Calibri", Font.BOLD, 15));
		inputTipLabel.setBounds(277, 575, 289, 33);
		frame.getContentPane().add(inputTipLabel);

		//large led strip
		JLabel chartLed = new JLabel(new ImageIcon(assets.getPath("LED-BIG")));
		chartLed.setBounds(111, 609, 602, 260);
		frame.getContentPane().add(chartLed);

		roundPoint1 = new JLabel(String.valueOf(roundPointsOfFirstPlayer));
		roundPoint1.setHorizontalTextPosition(SwingConstants.CENTER);
		roundPoint1.setHorizontalAlignment(SwingConstants.LEFT);
		roundPoint1.setForeground(Color.WHITE);
		roundPoint1.setFont(new Font("Cambria", Font.BOLD, 35));
		roundPoint1.setBounds(309, 664, 143, 56);
		frame.getContentPane().add(roundPoint1);

		roundPoint2 = new JLabel(String.valueOf(roundPointsOfSecondPlayer));
		roundPoint2.setHorizontalTextPosition(SwingConstants.CENTER);
		roundPoint2.setHorizontalAlignment(SwingConstants.LEFT);
		roundPoint2.setForeground(Color.WHITE);
		roundPoint2.setFont(new Font("Cambria", Font.BOLD, 35));
		roundPoint2.setBounds(534, 664, 140, 56);
		frame.getContentPane().add(roundPoint2);

		total1 = new JLabel(String.valueOf(totalPointsOfFirstPlayer));
		total1.setHorizontalTextPosition(SwingConstants.CENTER);
		total1.setHorizontalAlignment(SwingConstants.LEFT);
		total1.setForeground(Color.WHITE);
		total1.setFont(new Font("Cambria", Font.BOLD, 35));
		total1.setBounds(309, 718, 172, 56);
		frame.getContentPane().add(total1);

		total2 = new JLabel(String.valueOf(totalPointsOfSecondPlayer));
		total2.setHorizontalTextPosition(SwingConstants.CENTER);
		total2.setHorizontalAlignment(SwingConstants.LEFT);
		total2.setForeground(Color.WHITE);
		total2.setFont(new Font("Cambria", Font.BOLD, 35));
		total2.setBounds(534, 718, 187, 56);
		frame.getContentPane().add(total2);

		matchPoint1 = new JLabel(String.valueOf(matchPointOfFirstPlayer));
		matchPoint1.setHorizontalTextPosition(SwingConstants.CENTER);
		matchPoint1.setHorizontalAlignment(SwingConstants.LEFT);
		matchPoint1.setForeground(Color.WHITE);
		matchPoint1.setFont(new Font("Cambria", Font.BOLD, 35));
		matchPoint1.setBounds(309, 774, 56, 56);
		frame.getContentPane().add(matchPoint1);

		matchPoint2 = new JLabel(String.valueOf(matchPointOfSecondPlayer));
		matchPoint2.setHorizontalTextPosition(SwingConstants.CENTER);
		matchPoint2.setHorizontalAlignment(SwingConstants.LEFT);
		matchPoint2.setForeground(Color.WHITE);
		matchPoint2.setFont(new Font("Cambria", Font.BOLD, 35));
		matchPoint2.setBounds(534, 774, 56, 56);
		frame.getContentPane().add(matchPoint2);

		JLabel pointHeader1 = new JLabel("PLAYER 1");
		pointHeader1.setForeground(new Color(255, 255, 255));
		pointHeader1.setBackground(new Color(204, 0, 51));
		pointHeader1.setFont(new Font("Cambria", Font.BOLD, 30));
		pointHeader1.setHorizontalTextPosition(SwingConstants.CENTER);
		pointHeader1.setHorizontalAlignment(SwingConstants.CENTER);
		pointHeader1.setBounds(256, 615, 182, 56);
		frame.getContentPane().add(pointHeader1);

		JLabel pointHeader2 = new JLabel("PLAYER 2");
		pointHeader2.setForeground(new Color(255, 255, 255));
		pointHeader2.setBackground(new Color(204, 0, 51));
		pointHeader2.setHorizontalTextPosition(SwingConstants.CENTER);
		pointHeader2.setHorizontalAlignment(SwingConstants.CENTER);
		pointHeader2.setFont(new Font("Cambria", Font.BOLD, 30));
		pointHeader2.setBounds(482, 615, 182, 56);
		frame.getContentPane().add(pointHeader2);

		JProgressBar decorationBar2 = new JProgressBar();
		decorationBar2.setValue(100);
		decorationBar2.setOpaque(true);
		decorationBar2.setForeground(new Color(255, 255, 255));
		decorationBar2.setBorder(null);
		decorationBar2.setBounds(270, 660, 385, 5);
		frame.getContentPane().add(decorationBar2);

		JLabel roundPointLabel = new JLabel("  Round Points:");
		roundPointLabel.setForeground(Color.WHITE);
		roundPointLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		roundPointLabel.setBackground(new Color(153, 0, 51));
		roundPointLabel.setBounds(142, 671, 151, 56);
		frame.getContentPane().add(roundPointLabel);

		JLabel totalPointLabel = new JLabel("Total Points:");
		totalPointLabel.setForeground(Color.WHITE);
		totalPointLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		totalPointLabel.setBackground(new Color(153, 0, 51));
		totalPointLabel.setBounds(148, 718, 151, 56);
		frame.getContentPane().add(totalPointLabel);

		JLabel totalStreakLabel = new JLabel("  TOTAL STREAK:");
		totalStreakLabel.setOpaque(true);
		totalStreakLabel.setForeground(Color.WHITE);
		totalStreakLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		totalStreakLabel.setBackground(new Color(153, 0, 51));
		totalStreakLabel.setBounds(142, 774, 151, 56);
		frame.getContentPane().add(totalStreakLabel);				

		//** Point Chart Body
		JLabel pointChart = new JLabel("");
		pointChart.setBorder(new LineBorder(new Color(255, 255, 255), 5, true));
		pointChart.setForeground(new Color(255, 255, 255));
		pointChart.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		pointChart.setBackground(new Color(153, 0, 51));
		pointChart.setOpaque(true);
		pointChart.setBounds(135, 619, 566, 223);
		frame.getContentPane().add(pointChart);

		JLabel prizeLabel_1 = null;
		try {
			prizeLabel_1 = new JLabel(new ImageIcon(assets.getImage("CAR").getScaledInstance(72, 72, 1)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		prizeLabel_1.setBackground(Color.DARK_GRAY);
		prizeLabel_1.setOpaque(true);
		prizeLabel_1.setBounds(1025, 92, 72, 72);
		frame.getContentPane().add(prizeLabel_1);

		JLabel prizeLabel_2 = null;
		try {
			prizeLabel_2 = new JLabel(new ImageIcon(assets.getImage("PHONE").getScaledInstance(72, 72, 1)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		prizeLabel_2.setBackground(Color.DARK_GRAY);
		prizeLabel_2.setOpaque(true);
		prizeLabel_2.setBounds(1125, 30, 72, 72);
		frame.getContentPane().add(prizeLabel_2);

		JLabel prizeLabel_3 = null;
		try {
			prizeLabel_3 = new JLabel(new ImageIcon(assets.getImage("HOME").getScaledInstance(72, 72, 1)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		prizeLabel_3.setBackground(Color.DARK_GRAY);
		prizeLabel_3.setOpaque(true);
		prizeLabel_3.setBounds(1222, 92, 72, 72);
		frame.getContentPane().add(prizeLabel_3);

		JLabel prizeLabel_4 = null;
		try {
			prizeLabel_4 = new JLabel(new ImageIcon(assets.getImage("PS4").getScaledInstance(72, 72, 1)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		prizeLabel_4.setBackground(Color.DARK_GRAY);
		prizeLabel_4.setOpaque(true);
		prizeLabel_4.setBounds(1319, 30, 72, 72);
		frame.getContentPane().add(prizeLabel_4);

		JLabel prizeLabel_5 = null;
		try {
			prizeLabel_5 = new JLabel(new ImageIcon(assets.getImage("WASH").getScaledInstance(72, 72, 1)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		prizeLabel_5.setBackground(Color.DARK_GRAY);
		prizeLabel_5.setOpaque(true);
		prizeLabel_5.setBounds(1429, 92, 72, 72);
		frame.getContentPane().add(prizeLabel_5);

		// REFRESH GUI
		super.frame.revalidate();
		super.frame.repaint();
	}
}

/*import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.sun.prism.Image;

public class Welcome extends JFrame implements ActionListener {

	// Consist of Deposit and Withdraw Button
	// JPanel depositWithdrawPanel = new JPanel(new BorderLayout());
	JPanel depositWithdrawPanel = new JPanel();
	JButton deposit = new JButton("Deposit");
	JButton withdraw = new JButton("Withdraw");

	// Consist of Balance and Mini statement Button
	// JPanel balanceMiniStatementPanel = new JPanel(new BorderLayout());
	JPanel balanceMiniStatementPanel = new JPanel();
	JButton balance = new JButton("Balance");
	JButton miniStatement = new JButton("Mini Statement");

	// Consist of Account Details and Pin Change
	JPanel detailsPinPanel = new JPanel();
	JButton details = new JButton("Account Details");
	JButton changePin = new JButton("Change Pin");

	// Consist the Symbiosis logo
	JLabel logo;
	JPanel logoPanel = new JPanel();
	JPanel mainPanel = new JPanel(new GridLayout(4, 1));
	// Used to import the Withdraw and Deposit Music
	public AudioInputStream withdrawStream;
	public AudioInputStream depositStream;

	public int welcomeCount = 0;

	Administrator admin;
	User u[] = new User[100];
	Database db = new Database();
	String fileNameAdmin = System.getProperty("user.home") + "/admindb.ser";
	CreateAccount create = new CreateAccount();
	int userAccountNumber, userPin;

	public String COMMA_DELIMITER = ",";
	public String NEW_LINE_SEPARATOR = "\n";
	public String FILE_HEADER = "SR. NO.,TRANSACTION DATE, DEPOSIT, WITHDRAW";

	public Welcome(int userAccountNumber, int userPin) {

		this.userAccountNumber = userAccountNumber;
		this.userPin = userPin;
		// Builds the User Interface
		admin = create.getObject();
		buildWelcomeScreen();

		// To make sure that welcome sound is played only once
		if (welcomeCount == 0) {

			try {
				AudioInputStream audioIn = AudioSystem
						.getAudioInputStream(Welcome.class
								.getResource("WelcomeMusic.wav"));
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} catch (Exception e) {
				System.out.println("Sorry Unable to play deposit music!");
			}
		}

		welcomeCount++;
	}

	public void buildWelcomeScreen() {
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());

		try {

			BufferedImage logoI = ImageIO.read(this.getClass().getResource(
					"logo.png"));
			logo = new JLabel(new ImageIcon(logoI));
		} catch (Exception e) {
			e.getMessage();

		}
		// On Hover Action for Deposit Button
		deposit.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					depositStream = AudioSystem
							.getAudioInputStream(Welcome.class
									.getResource("Deposit.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open(depositStream);
					clip.start();
				} catch (Exception e) {
					System.out.println("Sorry Unable to play music!");
				}

			}
		});
		// On Hover Action for Withdraw Button
		withdraw.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					withdrawStream = AudioSystem
							.getAudioInputStream(Welcome.class
									.getResource("Withdraw.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open(withdrawStream);
					clip.start();
				} catch (Exception e) {
					e.getMessage();
					System.out.println("Sorry Unable to play withdraw music!");
				}

			}
		});

		// TODO Add on hover music for all buttons

		logoPanel.add(logo);

		depositWithdrawPanel.add(deposit);
		depositWithdrawPanel.add(withdraw);

		balanceMiniStatementPanel.add(balance);
		balanceMiniStatementPanel.add(miniStatement);
		// balanceMiniStatementPanel.add(balance,BorderLayout.WEST);
		// balanceMiniStatementPanel.add(miniStatement,BorderLayout.EAST);

		detailsPinPanel.add(details);
		detailsPinPanel.add(changePin);

		deposit.addActionListener(this);
		withdraw.addActionListener(this);
		balance.addActionListener(this);
		miniStatement.addActionListener(this);
		details.addActionListener(this);
		changePin.addActionListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets=new Insets(100,10,10,100);
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(logoPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(depositWithdrawPanel, c);
		c.gridx = 0;
		c.gridy = 2;
		mainPanel.add(balanceMiniStatementPanel, c);
		c.gridx = 0;
		c.gridy = 3;
		mainPanel.add(detailsPinPanel, c);
		add(mainPanel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deposit) {
			// Ask the amount to deposit
			double userAmountToDeposit;
			admin = create.getObject();
			System.out.println(admin.numberOfUsers);
			for (int i = 1; i <= admin.numberOfUsers; i++) {
				System.out.println("Loaded " + admin.numberOfUsers + " users");
				u[i] = admin.u[i];
			}
			for (int j = 1; j <= admin.numberOfUsers; j++) {
				if (u[j].accountNumber == userAccountNumber) {
					if (u[j].atmPin == userPin) {
						if (u[j].accountType == "Saving") {
							u[j].accountBalance = u[j].sa.balance;
							Object result = JOptionPane.showInputDialog(
									deposit, "Enter amount to deposit:");
							String userAmountToDepositInString = result
									.toString();
							userAmountToDeposit = Double
									.parseDouble(userAmountToDepositInString);
							u[j].sa.deposit(userAmountToDeposit);
							u[j].accountBalance = u[j].sa.balance;
							System.out.println(u[j].accountBalance);
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy/MM/dd");
							Date date = new Date();
							int k = u[j].nextIndexInTransactions;
							u[j].transactions[k] = new Transactions();
							u[j].transactions[k].transactionDate = dateFormat
									.format(date);
							u[j].transactions[k].deposit = userAmountToDeposit;
							u[j].nextIndexInTransactions++;
							JOptionPane
									.showMessageDialog(
											deposit,
											userAmountToDeposit
													+ " successfully deposited. New balance is "
													+ u[j].accountBalance);
							admin.u[j].accountBalance = admin.u[j].sa.balance = u[j].accountBalance;
						}
						if (u[j].accountType == "Current") {
							u[j].accountBalance = u[j].ca.balance;
							Object result = JOptionPane.showInputDialog(
									deposit, "Enter amount to deposit:");
							String userAmountToDepositInString = result
									.toString();
							userAmountToDeposit = Double
									.parseDouble(userAmountToDepositInString);
							u[j].ca.deposit(userAmountToDeposit);
							u[j].accountBalance = u[j].ca.balance;
							System.out.println(u[j].accountBalance);
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy/MM/dd");
							Date date = new Date();
							int k = u[j].nextIndexInTransactions;
							u[j].transactions[k] = new Transactions();
							u[j].transactions[k].transactionDate = dateFormat
									.format(date);
							u[j].transactions[k].deposit = userAmountToDeposit;
							u[j].nextIndexInTransactions++;
							JOptionPane
									.showMessageDialog(
											deposit,
											userAmountToDeposit
													+ " successfully deposited. New balance is "
													+ u[j].accountBalance);
							admin.u[j].accountBalance = admin.u[j].ca.balance = u[j].accountBalance;
						}
					}
				}
			}
		}
		if (e.getSource() == withdraw) {// 1
			double userAmountToWithdraw;
			admin = create.getObject();
			System.out.println("Entered Witdraw");
			for (int i = 1; i <= admin.numberOfUsers; i++) {// 2
				u[i] = admin.u[i];
				System.out.println("Loaded " + admin.numberOfUsers
						+ " users while withdrawing");
			}
			for (int j = 1; j <= admin.numberOfUsers; j++) {
				if (u[j].accountNumber == userAccountNumber) {
					if (u[j].atmPin == userPin) {
						System.out.println("Checked ATM Pin in withdraw");
						if (u[j].accountType == "Saving") {
							u[j].accountBalance = u[j].sa.balance;
							Object result = JOptionPane.showInputDialog(
									deposit, "Enter amount to deposit:");
							String userAmountToWithdrawInString = result
									.toString();
							userAmountToWithdraw = Double
									.parseDouble(userAmountToWithdrawInString);
							double returnedBalance = u[j].sa
									.withdraw(userAmountToWithdraw);
							if (returnedBalance == -1) {
								JOptionPane
										.showMessageDialog(
												withdraw,
												userAmountToWithdraw
														+ " is more than balance: "
														+ u[j].accountBalance
														+ ". Please enter a smaller value");
							} else {
								// Withdraw successfull
								DateFormat dateFormat = new SimpleDateFormat(
										"yyyy/MM/dd");
								Date date = new Date();
								int k = u[j].nextIndexInTransactions;
								u[j].transactions[k] = new Transactions();
								u[j].transactions[k].transactionDate = dateFormat
										.format(date);
								u[j].transactions[k].withdraw = userAmountToWithdraw;
								u[j].nextIndexInTransactions++;
								u[j].accountBalance = u[j].sa.balance;
								admin.u[j].accountBalance = admin.u[j].sa.balance = u[j].accountBalance;
								JOptionPane
										.showMessageDialog(
												withdraw,
												userAmountToWithdraw
														+ " successfully withdrawn. New balance is "
														+ u[j].accountBalance);

							}

						}
						if (u[j].accountType == "Current") {
							u[j].accountBalance = u[j].ca.balance;
							Object result = JOptionPane.showInputDialog(
									deposit, "Enter amount to deposit:");
							String userAmountToWithdrawInString = result
									.toString();
							userAmountToWithdraw = Double
									.parseDouble(userAmountToWithdrawInString);
							double returnedBalance = u[j].ca
									.withdraw(userAmountToWithdraw);
							if (returnedBalance == -1) {
								JOptionPane
										.showMessageDialog(
												withdraw,
												userAmountToWithdraw
														+ " is more than balance and overdraft limit: "
														+ (u[j].accountBalance + u[j].overDraftLimit)
														+ ". Please enter a smaller value");
							} else {
								DateFormat dateFormat = new SimpleDateFormat(
										"yyyy/MM/dd");
								Date date = new Date();
								int k = u[j].nextIndexInTransactions;
								u[j].transactions[k] = new Transactions();
								u[j].transactions[k].transactionDate = dateFormat
										.format(date);
								u[j].transactions[k].withdraw = userAmountToWithdraw;
								u[j].nextIndexInTransactions++;
								admin.u[j].accountBalance = admin.u[j].ca.balance = u[j].accountBalance;
								JOptionPane
										.showMessageDialog(
												withdraw,
												userAmountToWithdraw
														+ " successfully withdrawn. New balance is "
														+ u[j].accountBalance);

							}
						}
					}
				}
			}
		}
		if (e.getSource() == balance) {
			admin = create.getObject();
			for (int i = 1; i <= admin.numberOfUsers; i++) {// 2
				u[i] = admin.u[i];
				System.out.println("Loaded " + admin.numberOfUsers
						+ " users while checking balance");
			}
			for (int j = 1; j <= admin.numberOfUsers; j++) {
				if (u[j].accountNumber == userAccountNumber) {
					if (u[j].atmPin == userPin) {
						JOptionPane.showMessageDialog(balance,
								"Your Balance is " + u[j].accountBalance);
					}
				}
			}
		}
		if (e.getSource() == miniStatement) {
			String miniStatementFile = System.getProperty("user.home")
					+ "/ms.csv";
			FileWriter fileWriter = null;
			try {
				fileWriter = new FileWriter(miniStatementFile);
				fileWriter.append(FILE_HEADER);
				fileWriter.append(NEW_LINE_SEPARATOR);

				for (int i = 1; i <= admin.numberOfUsers; i++) {// 2
					u[i] = admin.u[i];
					System.out.println("Loaded " + admin.numberOfUsers
							+ " users while checking balance");
				}
				for (int j = 1; j <= admin.numberOfUsers; j++) {
					if (u[j].accountNumber == userAccountNumber) {
						if (u[j].atmPin == userPin) {
							for (int k = 0; k < u[j].nextIndexInTransactions; k++) {
								fileWriter.append(String.valueOf(j));
								fileWriter.append(COMMA_DELIMITER);

								fileWriter
										.append(String
												.valueOf(u[k].transactions[k].transactionDate));
								fileWriter.append(COMMA_DELIMITER);

								fileWriter.append(String
										.valueOf(u[k].transactions[k].deposit));
								fileWriter.append(COMMA_DELIMITER);
								//
								fileWriter
										.append(String
												.valueOf(u[k].transactions[k].withdraw));
								fileWriter.append(COMMA_DELIMITER);
							}
						}
					}
				}
				System.out.println("USER DATABASE CSV SUCCESSFULLY CREATED!");
			} catch (Exception E) {

				System.out.println("SORRY! CANNOT CREATE FILE. "
						+ E.getMessage());
				E.printStackTrace();
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (Exception e1) {
					System.out
							.println("ERROR WHILE COPYING TO CSV. PLEASE CLOSE THE FILE IF OPEN IN OTHER APPLICATIONS !!!");
					e1.printStackTrace();
				}

			}

		}
		if (e.getSource() == details) {

			for (int i = 1; i <= admin.numberOfUsers; i++) {// 2
				u[i] = admin.u[i];
				System.out.println("Loaded " + admin.numberOfUsers
						+ " users while checking details");
			}
			for (int j = 1; j <= admin.numberOfUsers; j++) {
				if (u[j].accountNumber == userAccountNumber) {
					if (u[j].atmPin == userPin) {
						if (u[j].accountType == "Saving") {
							JOptionPane.showMessageDialog(details, "Name: "
									+ u[j].fName + " " + u[j].lName + "\n"
									+ "Acocunt Number: " + u[j].accountNumber
									+ "\n" + "Balance: " + u[j].accountBalance
									+ "\n" + "Interest Rate: "
									+ u[j].interestRate, "Account Details",
									JOptionPane.INFORMATION_MESSAGE);
						}
						if (u[j].accountType == "Current") {
							JOptionPane.showMessageDialog(details, "Name: "
									+ u[j].fName + " " + u[j].lName + "\n"
									+ "Acocunt Number: " + u[j].accountNumber
									+ "\n" + "Balance: " + u[j].accountBalance
									+ "\n" + "Overdraft Limit: "
									+ u[j].interestRate, "Account Details",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		}
		if(e.getSource() == changePin) {
			Object currentPinObject = JOptionPane.showInputDialog(
					changePin, "Enter current:");
			String currentPinString = currentPinObject
					.toString();
			int currentPinInt = Integer.parseInt(currentPinString);
			for (int i = 1; i <= admin.numberOfUsers; i++) {// 2
				u[i] = admin.u[i];
				System.out.println("Loaded " + admin.numberOfUsers
						+ " users while checking details");
			}
			for (int j = 1; j <= admin.numberOfUsers; j++) {
				if (u[j].accountNumber == userAccountNumber) {
					if (u[j].atmPin == userPin) {
						try {
						if(currentPinInt==u[j].atmPin) {
							Object newPinObject = JOptionPane.showInputDialog(
									changePin, "Enter new pin:");
							String newPinString = newPinObject
									.toString();
							int newPinInt = Integer.parseInt(newPinString);
							int numberOfDigitsInPin = String.valueOf(newPinInt).length();
							
								if(numberOfDigitsInPin == 4)
									u[j].atmPin=newPinInt;
								else
									throw new Exception();
								JOptionPane.showMessageDialog(changePin,"Pin Changed Successfully!", "Change Pin",
										JOptionPane.INFORMATION_MESSAGE);
							}							
							else
							throw new Exception();
							
							}
						catch(Exception e4) {
							JOptionPane.showMessageDialog(changePin,"Invalid Pin!", "Change Pin",
									JOptionPane.INFORMATION_MESSAGE);
							e4.printStackTrace();
						}
					}
				}
			}

		}

	}
	// catch(Exception e5) {
	// e5.printStackTrace();
	// System.out.println("Unable to lookup the user");
	// }
}
*/
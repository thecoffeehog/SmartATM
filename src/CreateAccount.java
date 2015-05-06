import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import sun.audio.AudioStream;

public class CreateAccount extends JFrame implements ActionListener,MouseListener {

	JPanel details = new JPanel(new GridBagLayout());

	JLabel pressEnter = new JLabel(
			"Please press enter after entering every value!");

	JLabel fName = new JLabel("First Name:");
	JTextField firstNameField = new JTextField(10);

	JLabel lName = new JLabel("Last Name:");
	JTextField lastNameField = new JTextField(10);

	JLabel initialDeposit = new JLabel("Initial Deposit: ");
	JTextField initialDepositField = new JTextField(10);

	JLabel extra = new JLabel("Extra Deposit: ");
	JTextField extraField = new JTextField(10);

	String accountTypes[] = { "", "Savings", "Current" };

	JComboBox accountTypesComboBox = new JComboBox(accountTypes);
	JLabel accountType = new JLabel("Account type: ");

	JButton submit = new JButton("SUBMIT");
	JButton review = new JButton("REVIEW");

	public String uAccountType, uFName, uLName;
	public double uInitialDeposit, uOverDraftLimit, uInterestRate;

	public static Administrator admin = new Administrator();
	User u[] = new User[admin.maxNumberOfUsers];
	Transactions transactions[] = new Transactions[5];
	String fileNameAdmin = System.getProperty("user.home") + "/admindb.ser";
	SavingsAccount sa[] = new SavingsAccount[admin.maxNumberOfUsers];
	CurrentAccount ca[] = new CurrentAccount[admin.maxNumberOfUsers];

	Database db = new Database();
	AudioInputStream firstNameLabelStream,lastNameLabelStream,firstNameFieldStream,
	lastNameFieldStream,initialDepositLabelStream,initialDepositFieldStream,accountTypeStream,accountTypeComboBoxStream;
	Clip clipFName,clipFirstNameField,clipLName,clipLastNameField,clipInitialDeposit,clipInitialDepositField,clipAccountType,clipAccountTypeComboBox;
	CreateAccount() {
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildGui();

	}

	public void buildGui() {

		setTitle("New Account Form");

		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(10, 10, 10, 10);
		// Stretch components horizontally (but not vertically)
		c.fill = GridBagConstraints.HORIZONTAL;
		// Components that are too short or narrow for their space
		// Should be pinned to the northwest (upper left) corner
		c.anchor = GridBagConstraints.NORTHWEST;
		// Give the "last" component as much space as possible
		c.weightx = 1.0;

		c.gridx = 0;
		c.gridy = 0;
		details.add(fName, c);

		c.gridx = 1;
		c.gridy = 0;
		details.add(firstNameField, c);

		c.gridx = 0;
		c.gridy = 1;
		details.add(lName, c);

		c.gridx = 1;
		c.gridy = 1;
		details.add(lastNameField, c);

		c.gridx = 0;
		c.gridy = 2;
		details.add(initialDeposit, c);

		c.gridx = 1;
		c.gridy = 2;
		details.add(initialDepositField, c);

		c.gridx = 0;
		c.gridy = 3;
		details.add(accountType, c);

		c.gridx = 1;
		c.gridy = 3;
		details.add(accountTypesComboBox, c);

		c.gridx = 0;
		c.gridy = 4;
		details.add(extra, c);

		c.gridx = 1;
		c.gridy = 4;
		details.add(extraField, c);

		// Adds the fields but makes them invisible and now there visibility
		// depends on choice
		extra.setVisible(false);
		extraField.setVisible(false);

		c.gridx = 0;
		c.gridy = 5;
		details.add(submit, c);
		c.gridx = 1;
		c.gridy = 5;
		details.add(review, c);

		add(details);
		// Adds action listener to text fields
		firstNameField.addActionListener(this);
		lastNameField.addActionListener(this);
		initialDepositField.addActionListener(this);
		accountTypesComboBox.addActionListener(this);
		extraField.addActionListener(this);
		// Adds action listener to buttons
		submit.addActionListener(this);
		review.addActionListener(this);
		
		fName.addMouseListener(this);
		lName.addMouseListener(this);
		initialDeposit.addMouseListener(this);
		accountType.addMouseListener(this);
		extra.addMouseListener(this);
		
		firstNameField.addMouseListener(this);
		lastNameField.addMouseListener(this);
		initialDepositField.addMouseListener(this);
		accountTypesComboBox.addMouseListener(this);
		extraField.addMouseListener(this);
		// Adds action listener to buttons
		submit.addMouseListener(this);
		review.addMouseListener(this);
		
		addOnHoverActionsToComponents();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == firstNameField) {
			try {
				uFName = firstNameField.getText().toString();
				if (!uFName.matches("[A-Za-z]+"))
					throw new Exception();
				// Switches the text field after enter is pressed
				lastNameField.requestFocusInWindow();
			} catch (Exception e1) {
				firstNameField.setText("");
				JOptionPane.showMessageDialog(firstNameField,
						"Please enter a valid name!");
			}
		}
		if (e.getSource() == lastNameField) {
			try {
				uLName = lastNameField.getText().toString();

				if (!uLName.matches("[A-Za-z]+"))
					throw new Exception();
				initialDepositField.requestFocusInWindow();
			} catch (Exception e1) {
				lastNameField.setText("");
				JOptionPane.showMessageDialog(lastNameField,
						"Please enter a valid name!");
			}
		}
		if (e.getSource() == initialDepositField) {
			try {
				String tempString = initialDepositField.getText().toString();
				uInitialDeposit = Double.parseDouble(tempString);
				if (uInitialDeposit <= 0)
					throw new Exception();
				accountTypesComboBox.requestFocusInWindow();
			} catch (Exception e1) {
				initialDepositField.setText("");
				JOptionPane.showMessageDialog(initialDepositField,
						"Please enter a valid amount!");
			}
		}
		if (e.getSource() == accountTypesComboBox) {
			uAccountType = (String) accountTypesComboBox.getSelectedItem();
			if (uAccountType == "Savings") {
				extra.setText("Interest Rate: ");
				extra.setVisible(true);
				extraField.setVisible(true);
				extraField.requestFocus();
			}
			if (uAccountType == "Current") {
				extra.setText("Overdraft Limit: ");
				extra.setVisible(true);
				extraField.setVisible(true);
				extraField.requestFocus();
			}

		}
		if (e.getSource() == extraField) {
			try {
				String temp = extraField.getText().toString();
				if (uAccountType == "Savings") {
					uInterestRate = Double.parseDouble(temp);
					if (uInterestRate < 4 || uInterestRate > 11) {
						throw new Exception();
					}
				}
				if (uAccountType == "Current") {
					uOverDraftLimit = Double.parseDouble(temp);
					if (uOverDraftLimit < 0) {
						throw new ArithmeticException();

					}
				}
			} catch (ArithmeticException e1) {
				extraField.setText("");
				JOptionPane.showMessageDialog(extraField,
						"Please enter a valid amount!");
			} catch (Exception e2) {
				extraField.setText("");
				JOptionPane.showMessageDialog(extraField,
						"Interest Rate Should be between 4% to 11% !");
			}

		}
		if (e.getSource() == review) {
			if (uFName != null && uLName != null && uInitialDeposit != 0) {
				Review reviewDetails = new Review();
				reviewDetails.displayDetails(uFName, uLName, uInitialDeposit,
						uAccountType, uInterestRate, uOverDraftLimit);
				// this.setVisible(false);
				reviewDetails.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(review,
						"Please enter values before reviewing them!");
			}
		}
		if (e.getSource() == submit) {
			if (uFName != null && uLName != null && uInitialDeposit != 0) {
				if (uAccountType == "Savings") {
					Random randomGenerator = new Random();
					// Gets the number of users from file if file exists
					// File f = new File(fileNameAdmin);
					// if(f.exists() && !f.isDirectory()) {
					// admin=db.readFromAdminDatabase();
					// System.out.println("Successfully readed from database!");
					// }
					// else
					// admin=new Administrator();
					System.out.println(admin.numberOfUsers);
					admin.numberOfUsers++;
					admin.numberOfSavingsAccount++;
					u[admin.numberOfUsers] = new User();
					u[admin.numberOfUsers].fName = uFName;
					u[admin.numberOfUsers].lName = uLName;
					u[admin.numberOfUsers].initalDeposit = uInitialDeposit;
					u[admin.numberOfUsers].accountBalance = uInitialDeposit;
					u[admin.numberOfUsers].interestRate = uInterestRate;
					u[admin.numberOfUsers].accountType = "Saving";
					u[admin.numberOfUsers].accountNumber = 690000 + admin.numberOfSavingsAccount;
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date date = new Date();
					u[admin.numberOfUsers].transactions[0] = new Transactions();
					u[admin.numberOfUsers].transactions[0].transactionDate = dateFormat
							.format(date);
					u[admin.numberOfUsers].transactions[0].deposit = uInitialDeposit;
					u[admin.numberOfUsers].transactions[0].withdraw = 0;
					u[admin.numberOfUsers].nextIndexInTransactions = 1;

					// Generates a 4 digit random number which will be used as
					// ATM pin
					u[admin.numberOfUsers].atmPin = randomGenerator
							.nextInt(9999 - 1000) + 1000;

					// A savings account will be created
					sa[admin.numberOfSavingsAccount] = new SavingsAccount(
							u[admin.numberOfUsers].accountNumber,
							u[admin.numberOfUsers].fName,
							u[admin.numberOfUsers].lName,
							u[admin.numberOfUsers].initalDeposit,
							u[admin.numberOfUsers].interestRate);
					// u[admin.numberOfUsers].sa.dateFormat = new
					// SimpleDateFormat("yyyy/MM/dd");
					// u[admin.numberOfUsers].sa.date = new Date();
					// u[admin.numberOfUsers].sa.setCreationDate();
					u[admin.numberOfUsers].sa = sa[admin.numberOfSavingsAccount];
					admin.u[admin.numberOfUsers] = u[admin.numberOfUsers];
					// int k=0;
					// System.out.println(u[0].transactions[0].transactionDate);
					JOptionPane.showMessageDialog(submit,
							"Congratulations! You are now a member of Symbiosis Bank."
									+ "\nYour account number is "
									+ u[admin.numberOfUsers].accountNumber
									+ " and your ATM Pin is "
									+ u[admin.numberOfUsers].atmPin,
							"Account Created", JOptionPane.INFORMATION_MESSAGE);

					try {

						// db.updateAdminDatabase(admin);
						// db.addUserToDatabase(u[admin.numberOfUsers]);
						System.out.println("User with account number "
								+ u[admin.numberOfUsers].accountNumber + " and"
								+ u[admin.numberOfUsers].atmPin + "added");

						dispose();
						setVisible(false);
						InitialInput back = new InitialInput();
						back.setVisible(true);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				else if (uAccountType == "Current") {

					Random randomGenerator = new Random();
					// Gets the number of users from file if file exists
					// File f = new File(fileNameAdmin);
					// if(f.exists() && !f.isDirectory()) {
					// admin=db.readFromAdminDatabase();
					// }
					admin.numberOfCurrentAccount++;
					admin.numberOfUsers++;
					u[admin.numberOfUsers] = new User();
					u[admin.numberOfUsers].fName = uFName;
					u[admin.numberOfUsers].lName = uLName;
					u[admin.numberOfUsers].initalDeposit = uInitialDeposit;
					u[admin.numberOfUsers].overDraftLimit = uOverDraftLimit;
					u[admin.numberOfUsers].accountType = "Current";
					// if(admin.numberOfUsers==0)
					// u[admin.numberOfUsers].accountNumber=696900+admin.numberOfCurrentAccount;
					u[admin.numberOfUsers].accountNumber = 691000 + admin.numberOfCurrentAccount;
					// Generates a 4 digit random number which will be used as
					// ATM pin
					// u[admin.numberOfUsers].ca.setCreationDate();
					u[admin.numberOfUsers].ca = ca[admin.numberOfCurrentAccount];
					u[admin.numberOfUsers].atmPin = randomGenerator
							.nextInt(9999 - 1000) + 1000;
					// Creates a current account
					ca[admin.numberOfCurrentAccount] = new CurrentAccount(
							u[admin.numberOfUsers].accountNumber,
							u[admin.numberOfUsers].fName,
							u[admin.numberOfUsers].lName,
							u[admin.numberOfUsers].initalDeposit,
							u[admin.numberOfUsers].overDraftLimit);

					u[admin.numberOfUsers].ca = ca[admin.numberOfCurrentAccount];
					admin.u[admin.numberOfUsers] = u[admin.numberOfUsers];
					JOptionPane.showMessageDialog(submit,
							"Congratulations! You are now a member of Symbiosis Bank."
									+ "\nYour account number is "
									+ u[admin.numberOfUsers].accountNumber
									+ " and your ATM Pin is "
									+ u[admin.numberOfUsers].atmPin,
							"Account Created", JOptionPane.INFORMATION_MESSAGE);
					try {

						// db.addUserToDatabase(u[admin.numberOfUsers]);
						// db.updateAdminDatabase(admin);
						dispose();
						setVisible(false);
						// Welcome welcome = new Welcome();
						// welcome.setVisible(true);
						InitialInput back = new InitialInput();
						back.setVisible(true);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(review,
							"Please enter values before submiting them!");
				}
			}
		}
	}

	public void printUsersInDatabase() {
		System.out.println("Users in database");
		for (int i = 1; i < admin.numberOfUsers; i++) {
			System.out.println(u[i].accountNumber);
		}
	}
	
	public void addOnHoverActionsToComponents() {
		fName.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					firstNameLabelStream = AudioSystem
							.getAudioInputStream(Welcome.class
									.getResource("hover right to enter first name.wav"));
					clipFName = AudioSystem.getClip();
					clipFName.open(firstNameLabelStream);
					clipFName.start();
				} catch (Exception e) {
					System.out.println("Sorry Unable to play music!");
				}

			}
		});
		lName.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					lastNameLabelStream = AudioSystem
							.getAudioInputStream(Welcome.class
									.getResource("hover right to enter last name.wav"));
					clipLName = AudioSystem.getClip();
					clipLName.open(lastNameLabelStream);
					clipLName.start();
				} catch (Exception e) {
					System.out.println("Sorry Unable to play music!");
				}

			}
		});
		firstNameField.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					firstNameFieldStream = AudioSystem
							.getAudioInputStream(Welcome.class
									.getResource("Click to enter first name.wav"));
					clipFirstNameField = AudioSystem.getClip();
					clipFirstNameField.open(firstNameFieldStream);
					clipFirstNameField.start();
				} catch (Exception e) {
					System.out.println("Sorry Unable to play music!");
				}

			}
		});
		lastNameField.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					lastNameFieldStream = AudioSystem
							.getAudioInputStream(Welcome.class
									.getResource("Click to enter last name.wav"));
					clipLastNameField = AudioSystem.getClip();
					clipLastNameField.open(lastNameFieldStream);
					clipLastNameField.start();
				} catch (Exception e) {
					System.out.println("Sorry Unable to play music!");
				}

			}
		});
		initialDeposit.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					initialDepositLabelStream = AudioSystem
							.getAudioInputStream(Welcome.class
									.getResource("hover right to enter initial deposit.wav"));
					clipInitialDeposit = AudioSystem.getClip();
					clipInitialDeposit.open(initialDepositLabelStream);
					clipInitialDeposit.start();
				} catch (Exception e) {
					System.out.println("Sorry Unable to play music!");
				}

			}
		});
		initialDepositField.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					initialDepositFieldStream = AudioSystem
							.getAudioInputStream(Welcome.class
									.getResource("Click to enter initial deposit.wav"));
					clipInitialDepositField= AudioSystem.getClip();
					clipInitialDepositField.open(initialDepositFieldStream);
					clipInitialDepositField.start();
				} catch (Exception e) {
					System.out.println("Sorry Unable to play music!");
				}

			}
		});
		accountType.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					accountTypeStream = AudioSystem
							.getAudioInputStream(Welcome.class
									.getResource("hover right to select account type.wav"));
					clipAccountType= AudioSystem.getClip();
					clipAccountType.open(accountTypeStream);
					clipAccountType.start();
				} catch (Exception e) {
					System.out.println("Sorry Unable to play music!");
				}

			}
		});
	
	accountTypesComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseEntered(java.awt.event.MouseEvent evt) {
			try {
				accountTypeComboBoxStream = AudioSystem
						.getAudioInputStream(Welcome.class
								.getResource("Click down arrow to choose account type.wav"));
				clipAccountTypeComboBox= AudioSystem.getClip();
				clipAccountTypeComboBox.open(accountTypeComboBoxStream);
				clipAccountTypeComboBox.start();
			} catch (Exception e) {
				System.out.println("Sorry Unable to play music!");
			}

		}
	});
	
}
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == fName)
			clipFName.stop();
		if (e.getSource() == firstNameField)
			clipFirstNameField.stop();
		if (e.getSource() == lName)
			clipLName.stop();
		if (e.getSource() == lastNameField)
			clipLastNameField.stop();
		if (e.getSource() == initialDeposit)
			clipInitialDeposit.stop();
		if (e.getSource() == initialDepositField)
			clipInitialDepositField.stop();
		if (e.getSource() == accountType)
			clipAccountType.stop();
		
	}
	public Administrator getObject() {
		return admin;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

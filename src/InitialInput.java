import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.InputMismatchException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class InitialInput extends JFrame implements ActionListener,
		MouseListener {

	public int numberOfEnters = 0;
	CSVDialog csv = new CSVDialog();
	JLabel logo;
	JPanel logoPanel = new JPanel();

	JPanel askPanel = new JPanel();
	JPanel pinPanel = new JPanel();

	// For adding span
	JPanel emptyPanel = new JPanel();

	JTextField accountNumberField = new JTextField(10);
	JLabel askAccountNumber = new JLabel("Please enter your account Number : ");
	public int userAccountNumber;

	JTextField pinField = new JTextField(10);
	JLabel askPin = new JLabel("Please enter your Pin : ");
	public int userPin;

	JPanel submitPanel = new JPanel();
	JButton submit = new JButton("SUBMIT");

	JButton createAccount = new JButton("CREATE NEW ACCOUNT");
	JPanel mainPanel = new JPanel(new GridBagLayout());

	Database db = new Database();
	Administrator admin = new Administrator();
	String fileNameAdmin = System.getProperty("user.home") + "/admindb.ser";

	User u[] = new User[admin.maxNumberOfUsers];
	Clip clipAccountNumberField, clipPinField, clipWelcome, clipSubmit,
			clipCreateNewAccount, clipAccountNumberLabel, clipPinLabel;

	CreateAccount create = new CreateAccount();
	public String COMMA_DELIMITER = ",";
	public String NEW_LINE_SEPARATOR = "\n";
	public String FILE_HEADER = "ACCOUNT NO.,NAME,ACCOUNT TYPE,INITIAL DEPOSIT,INTEREST,OVERDRAFT LIMIT";
	
	Welcome welcome;

	InitialInput() {

		// Play welcome Music
		try {
			AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(Welcome.class
							.getResource("WelcomeMusic.wav"));
			clipWelcome = AudioSystem.getClip();
			clipWelcome.open(audioIn);
			clipWelcome.start();
		} catch (Exception e) {
			System.out.println("Sorry Unable to play account label music!");
		}

		buildGUI();

	}

	public void buildGUI() {

		// Sets Properties of Frame
		setPropertiesOfFrame();

		// Add Logo to Frame
		addLogo();

		// Add music on Hover
		addOnHoverActionsToComponents();

		// Adds gap between create account and submit button
		FlowLayout flow = new FlowLayout(); // Create a layout manager
		flow.setHgap(35);
		submitPanel.setLayout(flow);

		// Adds symbiosis logo to panel
		logoPanel.add(logo);

		// Adds Mouse listener to labels
		askAccountNumber.addMouseListener(this);
		askPin.addMouseListener(this);

		// Adds Mouse listener to text fields
		accountNumberField.addMouseListener(this);
		pinField.addMouseListener(this);

		// Adds action listener to text fields
		accountNumberField.addActionListener(this);
		pinField.addActionListener(this);

		// Add Mouse listener to buttons
		createAccount.addMouseListener(this);
		submit.addMouseListener(this);

		// Adds action listener to Create Account Button
		createAccount.addActionListener(this);
		submit.addActionListener(this);

		// Adds labels to panel
		askPanel.add(askAccountNumber);
		askPanel.add(accountNumberField);

		// Adds textfields to panel
		pinPanel.add(askPin);
		pinPanel.add(pinField);

		// Adds buttons to panels
		submitPanel.add(createAccount);
		submitPanel.add(submit);

		// Specifies constraints for grid layout
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(logoPanel, c);

		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(askPanel, c);

		c.gridx = 0;
		c.gridy = 2;
		mainPanel.add(emptyPanel, c);

		c.gridx = 0;
		c.gridy = 3;
		mainPanel.add(pinPanel, c);

		c.gridx = 0;
		c.gridy = 4;
		mainPanel.add(emptyPanel, c);

		c.gridx = 0;
		c.gridy = 5;
		mainPanel.add(submitPanel, c);

		add(mainPanel);
		setVisible(true);
	}

	public void setPropertiesOfFrame() {
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Welcome to Symbiosis Bank");
	}

	public void addLogo() {
		try {

			BufferedImage logoI = ImageIO.read(this.getClass().getResource(
					"logo.png"));
			logo = new JLabel(new ImageIcon(logoI));
		} catch (Exception e) {
			e.getMessage();

		}
	}

	public void addOnHoverActionsToComponents() {
		// On Hover Action for labels, fields, and buttons
		askAccountNumber.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(InitialInput.class
							.getResource("LabelMusicToEnterAccountNumber.wav"));
					clipAccountNumberLabel = AudioSystem.getClip();
					clipAccountNumberLabel.open(audioIn);
					clipAccountNumberLabel.start();
				} catch (Exception e) {
					System.out
							.println("Sorry Unable to play account number music!");
				}
			}
		});
		askPin.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(InitialInput.class
									.getResource("LabelMusicToEnterPin.wav"));
					clipPinLabel = AudioSystem.getClip();
					clipPinLabel.open(audioIn);
					clipPinLabel.start();
				} catch (Exception e) {
					System.out
							.println("Sorry Unable to play account number music!");
				}
			}
		});

		accountNumberField.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(InitialInput.class
							.getResource("ClickAndEnterAccountNumber.wav"));
					clipAccountNumberField = AudioSystem.getClip();
					clipAccountNumberField.open(audioIn);
					clipAccountNumberField.start();
				} catch (Exception e) {
					System.out
							.println("Sorry Unable to play account number music!");
				}

			}
		});
		pinField.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(InitialInput.class
									.getResource("ClickAndEnterPin.wav"));
					clipPinField = AudioSystem.getClip();
					clipPinField.open(audioIn);
					clipPinField.start();
				} catch (Exception e) {
					System.out.println("Sorry Unable to play pin music!");
				}

			}
		});
		submit.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					AudioInputStream audioIn = AudioSystem
							.getAudioInputStream(InitialInput.class
									.getResource("ClickToSubmit.wav"));
					clipSubmit = AudioSystem.getClip();
					clipSubmit.open(audioIn);
					clipSubmit.start();
				} catch (Exception e) {
					System.out.println("Sorry Unable to play submit  music!");
				}

			}
		});
		createAccount.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(InitialInput.class
							.getResource("ClickToCreateNewAccount.wav"));
					clipCreateNewAccount = AudioSystem.getClip();
					clipCreateNewAccount.open(audioIn);
					clipCreateNewAccount.start();
				} catch (Exception e) {
					System.out
							.println("Sorry Unable to play new account music!");
				}

			}
		});
	}

	// Actions to be performed after user clicks enter in fields or clicks a
	// button
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == accountNumberField) {
			try {
				numberOfEnters++;
				userAccountNumber = Integer.parseInt(accountNumberField
						.getText());
				int numberOfDigitsInAccountNumber = String.valueOf(
						userAccountNumber).length();

				if (numberOfDigitsInAccountNumber != 6) {
					throw new InputMismatchException();
				}
				if (userAccountNumber == 696969 && numberOfEnters == 6) {
					// TODO Enter the Admin MOde
					
					csv.setVisible(true);
					System.out.println("Admin");
					csv.confirmBtn.addActionListener(this);
				}
				if (userAccountNumber <= 0) {
					throw new Exception();
				}
				pinField.requestFocusInWindow();
			} catch (InputMismatchException e1) {
				accountNumberField.setText("");
				JOptionPane.showMessageDialog(accountNumberField,
						"Please enter a 6 digit account number.");
			} catch (Exception e1) {
				accountNumberField.setText("");
				JOptionPane.showMessageDialog(accountNumberField,
						"Invalid input.");
			}
		} else if (e.getSource() == pinField) {
			try {
				userPin = Integer.parseInt(pinField.getText());
				int numberOfDigitsInPin = String.valueOf(userPin).length();
				if (numberOfDigitsInPin != 4) {
					throw new InputMismatchException();
				}
				if (userPin <= 0) {
					throw new Exception();
				}
				// TODO if(accountNumber) not match in database
			} catch (InputMismatchException e1) {
				pinField.setText("");
				JOptionPane.showMessageDialog(pinField,
						"Please enter a 4 digit pin.");
			} catch (Exception e1) {
				pinField.setText("");
				JOptionPane.showMessageDialog(pinField, "Invalid input.");
			}
		} else if (e.getSource() == createAccount) {
			CreateAccount a = new CreateAccount();
			// TODO Add God Mode
			this.setVisible(false);
			a.setVisible(true);
		} else if (e.getSource() == submit) {
			admin = create.getObject();
			if (userAccountNumber != 0 && userPin != 0) {
				// File f = new File(fileNameAdmin);
				// if(f.exists() && !f.isDirectory()) {

				try {
					// admin=db.readFromAdminDatabase();
					System.out
							.println("No. of users while clicking submit button in initial input: "
									+ admin.numberOfUsers);
					for (int i = 1; i <= admin.numberOfUsers; i++) {
						u[i] = admin.u[i];
						System.out.println("Readed user with account number"
								+ u[i].accountNumber);
					}
					for (int j = 1; j <= admin.numberOfUsers; j++) {
						System.out.println(u[j].accountNumber);
						if (userAccountNumber == u[j].accountNumber) {
							if (u[j].atmPin == userPin) {
								// Close current frame
								dispose();
								setVisible(false);
								// Start Welcome Screen
								welcome = new Welcome(userAccountNumber,
										userPin);
								welcome.setVisible(true);
								System.out.println("Starting Welcome Screen");
								break;

							} else {
								JOptionPane.showMessageDialog(submit,
										"Please enter correct pin");
								pinField.setText("");
							}
						} else {
							JOptionPane
									.showMessageDialog(submit,
											"Account not found. Click on create new account if you don't have a account.");
							accountNumberField.setText("");
						}

					}

				} catch (Exception e3) {
					e3.printStackTrace();
				}
			} else
				JOptionPane.showMessageDialog(submit, "User not found!");
		}
		else if(e.getSource()==csv.confirmBtn) {
			String fileName = System.getProperty("user.home")+"/users.csv";
			FileWriter fileWriter=null;
			//FILE_HEADER = "ACCOUNT NO.,NAME,ACCOUNT TYPE,INITIAL DEPOSIT,INTEREST,OVERDRAFT LIMIT,CREATION DATE";
			try {
				admin = create.getObject();
				fileWriter= new FileWriter(fileName);
				fileWriter.append(FILE_HEADER);
				fileWriter.append(NEW_LINE_SEPARATOR);
				for (int i = 1; i <= admin.numberOfUsers; i++) {
					u[i] = admin.u[i];
					System.out.println("Readed user with account number"
							+ u[i].accountNumber);
				}
				for (int j = 1; j <= admin.numberOfUsers; j++) {					
					fileWriter.append(String.valueOf(u[j].accountNumber));
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append((u[j].fName));
					fileWriter.append(COMMA_DELIMITER);
					
					//fileWriter.append(u[i].name);
					//fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append(String.valueOf(u[j].accountType));
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append(String.valueOf(u[j].initalDeposit));
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append(String.valueOf(u[j].interestRate));
					fileWriter.append(COMMA_DELIMITER);
					
					fileWriter.append(String.valueOf(u[j].overDraftLimit));
					fileWriter.append(NEW_LINE_SEPARATOR);
					
					//fileWriter.append(String.valueOf(u[j].creationDate));
					
				}
				System.out.println("USER DATABASE CSV SUCCESSFULLY CREATED! for "+admin.numberOfUsers+ "users");
			}
			catch (Exception E){
				
				System.out.println("SORRY! CANNOT CREATE FILE. "+E.getMessage());
				E.printStackTrace();
			}
			finally {
				try {
					fileWriter.flush();
					fileWriter.close();
					JOptionPane
					.showMessageDialog(csv.confirmBtn,
							"CSV created successfully.");
					csv.setVisible(false);
				}
				catch (Exception e1) {
					System.out.println("ERROR WHILE COPYING TO CSV. PLEASE CLOSE THE FILE IF OPEN IN OTHER APPLICATIONS !!!");
					e1.printStackTrace();
					}

			}
		}
	
		//else if

		else
			JOptionPane.showMessageDialog(submit,
					"Please enter values before submiting them!");
	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == accountNumberField)
			clipAccountNumberField.stop();
		if (e.getSource() == pinField)
			clipPinField.stop();
		if (e.getSource() == submit)
			clipSubmit.stop();
		if (e.getSource() == createAccount)
			clipCreateNewAccount.stop();
		if (e.getSource() == askAccountNumber)
			clipAccountNumberLabel.stop();
		if (e.getSource() == askPin)
			clipPinLabel.stop();
	}

	public int getUserAccountNumber() {
		return userAccountNumber;
	}

	public int getUserPin() {
		return userPin;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	public void printAllUsers() {

	}
}

class CSVDialog extends JFrame {
	private JLabel save = new JLabel(
			"Click on Save to save CSV file with data of all users");
	public JButton confirmBtn = new JButton("Save");
	JPanel panel = new JPanel(new GridBagLayout());
	public JLabel csv;
	CSVDialog() {
		try {
			BufferedImage logoI = ImageIO.read(this.getClass().getResource(
					"csv2.png"));
			setIconImage(logoI);
			csv = new JLabel(new ImageIcon(logoI));
			csv.setSize(10, 10);
		} catch (Exception e) {
			System.out.println("Unable to load image.");
		}
		//setLayout(new GridBagLayout());
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("GOD MODE");
		GridBagConstraints c = new GridBagConstraints();
		c.insets= new Insets(20,20,20,20);
		c.gridx=0;
		c.gridy=0;
		panel.add(csv,c);
		c.gridx=0;
		c.gridy=1;
		panel.add(save,c);
		c.gridx=0;
		c.gridy=2;
		panel.add(confirmBtn,c);
		add(panel);
	}
}
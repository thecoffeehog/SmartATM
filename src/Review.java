import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Review extends JFrame implements ActionListener {
	CreateAccount form = new CreateAccount();
	Review() {
		setTitle("Please review and press Done");
		setSize(400,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public JButton done = new JButton("DONE");
	public void displayDetails(String uFName,String uLName,double uInitialDeposit,
			String uAccountType,double uInterestRate,double uOverDraftLimit) {
		
		JLabel name = new JLabel("Name: "+uFName+ " "+uLName);
		JLabel initialDeposit = new JLabel("Intital Deposit: "+Double.toString(uInitialDeposit));
		JLabel accountType = new JLabel("Account Type: "+uAccountType);
		JLabel extraField=new JLabel();
		if(uAccountType=="Savings") {
			extraField.setText("Interest Rate: "+uInterestRate+"%");
		}
		if(uAccountType=="Current") {
			extraField.setText("Overdraft Limit: "+uOverDraftLimit);
		}
		JPanel holder = new JPanel(new GridBagLayout());
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets=new Insets(10,10,10,10);
		c.gridx=0;
		c.gridy=0;
		holder.add(name,c);
		c.gridx=0;
		c.gridy=1;
		holder.add(initialDeposit,c);
		c.gridx=0;
		c.gridy=2;
		holder.add(accountType,c);
		c.gridx=0;
		c.gridy=3;
		holder.add(extraField,c);
		c.gridx=0;
		c.gridy=4;
		holder.add(done,c);
		
		done.addActionListener(this);
		add(holder);
		}
      public void actionPerformed(ActionEvent e) {
		if(e.getSource()==done) {
			this.dispose();
			}
	}
	
}

import java.io.Serializable;

public class User implements Serializable{
	public String fName;
	public String lName;
	public String accountType;
	public int accountNumber;
	public double initalDeposit;
	public double accountBalance;
	//public String[] transactions;
	public double interestRate;
	public String creationDate;
	public double overDraftLimit;
	public int atmPin;
	public SavingsAccount sa;
	public CurrentAccount ca;
	Transactions transactions[]=new Transactions[5];
	public int nextIndexInTransactions;
	User() {
		
	}

}
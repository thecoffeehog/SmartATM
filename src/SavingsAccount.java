import java.io.Serializable;


public class SavingsAccount extends Account implements Serializable{
	public double interestRate;
	public static String type="Savings";
	SavingsAccount(int number, String firstName, String lastName, double initialDeposit,double interestRate) {
		super(number,firstName,lastName,type,initialDeposit);
		this.interestRate=interestRate;
	}
	public double withdraw(double withdrawlAmount) {
		if(withdrawlAmount <= balance) {
			super.balance = super.balance - withdrawlAmount;
			return super.balance;
		}
		else {
			return -1;
		}
	}
	public double calculateInterest() {
		double balanceWithInterest = super.balance * this.interestRate * 0.01;
		return balanceWithInterest;
	}
}

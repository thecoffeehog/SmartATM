import java.io.Serializable;


public class CurrentAccount extends Account implements Serializable {
	public double overDraftLimit;
	public static String type="Current";
	CurrentAccount(int number, String firstName, String lastName, double initialDeposit,double overDraftLimit) {
		super(number,firstName,lastName,type,initialDeposit);
		this.overDraftLimit=overDraftLimit;
	}
	public double withdraw(double withdrawlAmount) {
		if(withdrawlAmount <= balance) {
			super.balance = super.balance - withdrawlAmount;
			return super.balance;
		}
		else if(withdrawlAmount <= balance+this.overDraftLimit) {
			double overDraftAmount = withdrawlAmount - balance;
			if(overDraftAmount <= overDraftLimit) {
				overDraftLimit = overDraftLimit - overDraftAmount; 
				super.balance=super.balance - withdrawlAmount;
				return super.balance;
			}
			else
				return -1;
			
		}
		else {
			return -1;
		}
	}
	public double getOverDraftBalance() {
		return overDraftLimit;
	}
}

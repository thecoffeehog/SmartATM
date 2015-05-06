import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class Account {
	public int number;
	public String firstName;
	public String lastName;
	public String type;
	public String creationDate;
	public double initialDeposit;
	public double balance;
	public double atmPin;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date date = new Date();
	Account() {
		
	}
	Account(int number, String firstName, String lastName, String type, double initialDeposit) {
		this.number=number;
		this.firstName=firstName;
		this.lastName=lastName;
		this.type=type;
		this.initialDeposit=initialDeposit;
		this.balance=initialDeposit;
		setCreationDate();
		}
	public abstract double withdraw(double amount);
	public void deposit(double amount) {
		this.balance = this.balance + amount;
	}
	public void changePin(double newPin) {
		this.atmPin=newPin;
	}
	public void setCreationDate() {
		
		this.creationDate=dateFormat.format(date);
	}
}
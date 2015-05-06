import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Administrator implements Serializable {
	public int numberOfSavingsAccount;
	public int numberOfCurrentAccount;
	public int numberOfUsers;
	public int maxNumberOfUsers=100;
	public User u[]= new User[maxNumberOfUsers];
	Administrator() {
		
	}
}
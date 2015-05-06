import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Database implements Serializable {
	String fileName = System.getProperty("user.home")+"/db.ser";
	String fileNameAdmin = System.getProperty("user.home")+"/admindb.ser";
	
	Administrator admin;
	User u[]=new User[100];
	public void addUserToDatabase(User ut){
		boolean exists = new File(fileName).exists();
		FileOutputStream fos;
		try {
			//admin=readFromAdminDatabase();
			//u[admin.numberOfUsers]=ut;
			/*fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = exists ? 
            new ObjectOutputStream(fos) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            }:new ObjectOutputStream(fos);*/
			ObjectOutputStream o1;
			ObjectOutputStream o2;
			if(!exists) {
				o1= new ObjectOutputStream(new FileOutputStream(fileName));
				o1.writeObject(u[admin.numberOfUsers]);
			}
				
			else {
	//			o2 = new AppendObjectOutputStream(new FileOutputStream("1", true));
		//		o2.writeObject(u[admin.numberOfUsers]);
			}
				
			//oos.writeObject(u[admin.numberOfUsers]);
			//oos.flush();
		//	oos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("finally")
	public User readFromUserDatabase() {
		FileInputStream fis;
		User temp = null;
		try {
			
				fis = new FileInputStream(fileName);
				
				ObjectInputStream ois = new ObjectInputStream(fis);
				temp = (User)ois.readObject();
				//System.out.println(temp.fName);
				ois.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			return temp;
		}
	}
	
	public void updateAdminDatabase(Administrator admin) {
			FileOutputStream fos;
			try {
				boolean exists = new File(fileNameAdmin).exists();
				fos = new FileOutputStream(fileNameAdmin);
				/*ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(admin);
				oos.close();*/
				ObjectOutputStream oos =new ObjectOutputStream(fos) {
	                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            } ;
						//exists ? 
//			            new ObjectOutputStream(fos) {
//			                protected void writeStreamHeader() throws IOException {
//			                    reset();
//			                }
//			            }:new ObjectOutputStream(fos);
			            oos.writeObject(admin);
			            oos.flush();
				}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
	@SuppressWarnings("finally")
	public Administrator readFromAdminDatabase() {
		
		Administrator temp = null;
		try {
			
			FileInputStream fis= new FileInputStream(fileNameAdmin);
			ObjectInputStream ois = new ObjectInputStream(fis);
			while(fis.available()>0) {
			
			temp = (Administrator)ois.readObject();
			System.out.println(temp.numberOfUsers);
			
			}
		//	ois.close();
		}
		catch(EOFException e1) {
			e1.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(temp==null)
				System.out.println("Sending Null");
			return temp;
		}
		
	}
	
		
	
}

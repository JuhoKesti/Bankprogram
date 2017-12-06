package bankservice;

/*
 * ATM/Bank program
 * OO Programming Java - 2017 Autumn
 * Studentname(nr): Juhopekka Kesti(1601537)
 * Laurea Lepp�vaara 
 * Tietojenk�sittely koulutusohjelma/Business information technology
 */

public class Account{

	//Defining variables
	int id;
	String LName;
	String FName;
	double balance;

	
	Account(int id, String LName, String FName, double balance){
		
		this.id = id;
		this.LName = LName;
		this.FName = FName;
		this.balance = balance;
	}

	//Getters for variables
	
	public int getId() {
		return id;
	}
	
	public String getLName() {
		return LName;
	}

	public String getFName() {
		return FName;
	}

	public double getBalance() {
		return balance;
	}	
}
package bankservice;

/*
 * ATM/Bank program
 * OO Programming Java - 2017 Autumn
 * Studentname: Juhopekka Kesti
 * Laurea Leppävaara 
 * Tietojenkäsittely koulutusohjelma/Business information technology
 */

import javax.swing.JOptionPane;

public class InsufficientFundsException extends Exception {
		
	//Basic exception message. Implemented in file Bank.java 
	public InsufficientFundsException() {
		JOptionPane pane = new JOptionPane();
		JOptionPane.showMessageDialog(pane, " You have insufficient funds! Deposit more funds to complete the transaction" );
	}
}

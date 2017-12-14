package bankservice;
/*
 * ATM/Bank program
 * OO Programming Java - 2017 Autumn
 * Studentname: Juhopekka Kesti
 * Laurea Leppävaara 
 * Tietojenkäsittely koulutusohjelma/Business information technology
 */

	//Just all required imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;	
	
public class Bank extends JFrame{	//extending Bank class to JFrame

	final static int MAX_QTY = 5; //Maximum amount of accounts in the table (This is for the future implemetations...The plan is to continue practising and further developing this program. For example, I'm thinking about adding a function where you can open new accounts)
	static int dbItems = 0; // Current amount of accounts in the table
	static Account[] myDB = new Account[MAX_QTY]; //myDB is an array of Account objects.Value of MAX_QTY(5) defines the maximum amount of objects in this array
	static JTable tableAccount;
	static JButton btnAddAccount,btnDeposit,btnWithdraw,btnExit,btnTransfer;
	
	
	
	public Bank(){				//Class constructor
		super("Suonpaa banking");	//This sets the title for the JFrame
		
		initiateAccounts();			//This inititates Accounts
		
		
		//JFrame values below
		setDefaultCloseOperation(EXIT_ON_CLOSE);	//Defines the deafult way to terminate this program (pressing the 'X' button)
		getContentPane().setLayout(null); 			//Defines JFrame's container with null layout manager (This means absolute positioning)
		setBounds(0,0,450,300); 					//JFrame size
		setLocationRelativeTo(null);				//Set the location of the JFrame at the center of the screen
		
		//Checkingslabel values below
		JLabel lblCheckings = new JLabel("Note: Your checkings accout id is 7421");
		lblCheckings.setVerticalAlignment(SwingConstants.TOP);
		lblCheckings.setBounds(10, 11, 414, 14);
		getContentPane().add(lblCheckings);
		
		//Savingslabel values below
		JLabel lblSavings = new JLabel("Note: Your savings account id is 4231");
		lblSavings.setBounds(10, 23, 414, 14);
		getContentPane().add(lblSavings);
		
		//ScrollPane values below
		JScrollPane scrollPane = new JScrollPane();	//Setting the container for JTable
		scrollPane.setBounds(10, 48, 352, 101);		//Setting container size
		getContentPane().setLayout(null);			// Absolute positioning
		getContentPane().add(scrollPane);			
		
		//Setting a JTable for displaying the accounts
		tableAccount = new JTable();
		tableAccount.setModel(new DefaultTableModel(
			new Object[MAX_QTY][4] ,	//Setting maximum amount of rows with MAX_QTY's value(5) and also setting 4 columns
			new String[] {
				"Account ID", "Last Name", "First Name", "Balance"	// Column headers listed here
			}
		));
		scrollPane.setViewportView(tableAccount); //Setting the JTable to display in the ScrollPane. (Without this the column headers wouldn't display)
		
		
		//WithdrawButton values with its function
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(10, 160, 204, 44);
		getContentPane().add(btnWithdraw);
		btnWithdraw.addActionListener(new ActionListener(){	//Defining new ActionListener
			public void actionPerformed(ActionEvent myEvent){
				DefaultTableModel model = (DefaultTableModel)tableAccount.getModel(); // Getting the table
				int selectedRowId = tableAccount.getSelectedRow(); //Defining user selected row with variable
				
				String balance = model.getValueAt(selectedRowId, 3).toString();		//Getting value from the JTable and defining it to String variable		
				String withdrawAmount = JOptionPane.showInputDialog(null,"Enter the amout you wish to withdraw: ");	//Asking user for the withdraw amount
				
				double newBalance = Double.parseDouble(balance) - Double.parseDouble(withdrawAmount);	//Using Double.parseDouble() to get the double from the Strings 			
				if (newBalance < 0){							
					try {										
						throw new InsufficientFundsException();	// Throws new InsufficientFundsException if the terms are met in If statement
					} catch (InsufficientFundsException e) {						
						e.printStackTrace();
					}
					
				}else if(selectedRowId == 1){	//If user tries to withdraw from savings the program stops user with warning.
					JOptionPane.showMessageDialog(null, "You are not allowed to withdraw from savings accout!");					
				}else {							//If user tries to withdraw from checkings account and balance doesn't drop below 0,the program allows this transaction
					model.setValueAt(newBalance, selectedRowId, 3);	//Setting value to its respective account in the JTable
				}		
			}
		});
		
		//DepositButton values with its function
		btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(10, 207, 204, 44);
		getContentPane().add(btnDeposit);
		btnDeposit.addActionListener(new ActionListener(){ // New ActionListener
			public void actionPerformed(ActionEvent myEvent){
				DefaultTableModel model = (DefaultTableModel)tableAccount.getModel();
				String balance =  model.getValueAt(0, 3).toString(); // Getting value from the JTable and defining it to String variable
				String depositAmount = JOptionPane.showInputDialog(null,"Enter The amount you wish to deposit: ");		//Asking user for the deposit amount		
				double newBalance = Double.parseDouble(balance) + Double.parseDouble(depositAmount);	//Using Double.parseDouble() to get the double from the Strings 
				model.setValueAt(newBalance, 0, 3);	//Setting value to its respective account in the JTable
				
			}
		});
									
		//ExitButton values with its function
		btnExit = new JButton("Exit");
		btnExit.setBounds(220, 207, 204, 44);
		getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener(){ // New ActionListener
			public void actionPerformed(ActionEvent myEvent){
				System.exit(0);		// Provides alternative way to close program
			}
		});
		
		//TransferButton values with its function
		btnTransfer = new JButton("Transfer to savings");
		btnTransfer.setBounds(220, 160, 204, 44);
		getContentPane().add(btnTransfer);		
		btnTransfer.addActionListener(new ActionListener(){// new ActionListener
			public void actionPerformed(ActionEvent myEvent){
				DefaultTableModel model = (DefaultTableModel)tableAccount.getModel();
				
				String balanceSavings = model.getValueAt(1, 3).toString();	// Getting balance from the SavingsAccount in the JTable and defining it to String variable
				String balanceCheckings = model.getValueAt(0, 3).toString();	// Getting balance from the CheckingsAccount in the JTable and defining it to String variable
				String trasferAmount = JOptionPane.showInputDialog(null,"Enter the amount you wish to trasfer to savings: "); // Asking user for transfer amount
				double newBalanceSavings = Double.parseDouble(balanceSavings) + Double.parseDouble(trasferAmount);	//Using Double.parseDouble() to get the double from the Strings 
				double newBalanceCheckings = Double.parseDouble(balanceCheckings) - Double.parseDouble(trasferAmount);	//Using Double.parseDouble() to get the double from the Strings 	
				
				if(newBalanceCheckings < 0){ // Confirming that the balance on the Checkings account doesn't drop below 0 when funds are transferred
					try {	// Throws new InsufficientFundsException if the terms are met in If statement
						throw new InsufficientFundsException();
					} catch (InsufficientFundsException e) {						
						e.printStackTrace();
					}
				}else{
						//If Checkings balance doesn't drop below 0, program completes transaction
				model.setValueAt(newBalanceSavings, 1, 3);
				model.setValueAt(newBalanceCheckings, 0, 3);
				}
				
				
			}
		});
		
		populateAccountTable();	//Populates the table with items from given 'database'
				
	}
	
	
	private void populateAccountTable() { //This method fills the JTable with data from the array myDB
		for(int row = 0; row < dbItems; row++){
			tableAccount.setValueAt(myDB[row].getId(), row, 0);
			tableAccount.setValueAt(myDB[row].getLName(), row, 1);
			tableAccount.setValueAt(myDB[row].getFName(), row, 2);
			tableAccount.setValueAt(myDB[row].getBalance(), row, 3);
		}	
	}
	
	
	private void initiateAccounts() {	//This method creates two accounts to be used in the JTable and stores then in myDB array
		myDB[0] = new Account(7421,"Brown","Charlie",0);	//AccountID,LastName,FirstName,Balance
		myDB[1] = new Account(4231,"Brown","Charlie",0);	//AccountID,LastName,FirstName,Balance
		dbItems = 2;
	}
}
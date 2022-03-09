package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.Transaction;

public class TransactionDaoFile implements TransactionDao {
	
	public static String fileLocation = "C:\\Users\\dp\\Desktop\\com\\Revature\\test2.txt";

	public List<Transaction> getAllTransactions() {
		List<Account> allAccounts = new ArrayList<Account>();
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLocation));
			allAccounts = (ArrayList<Account>)ois.readObject();
			ois.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		List<Transaction> allTransactions = new ArrayList<Transaction>();
		for(Account account : allAccounts) {
			if (account.getTransactions() != null) {
				List<Transaction> transactions = account.getTransactions();
				for (Transaction t : transactions) {
					allTransactions.add(t);
			}
			
			}
		}
		
		return allTransactions;
	}
	
	

}

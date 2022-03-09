package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.User;

/**
 * Implementation of AccountDAO which reads/writes to files
 */
public class AccountDaoFile implements AccountDao {
	// use this file location to persist the data to
	public static String fileLocation = "src\\users.txt";

	//////////////////////////////////////////////////////////////////////////
	public Account addAccount(Account a) {
		List<Account> allAccounts = getAccounts();
		allAccounts.add(a);
		
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileLocation));
				oos.writeObject(allAccounts);
				oos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		return a;
	}

	///////////////////////////////////////////////////////////////////////////
	public Account getAccount(Integer actId) {
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
		
		for(Account account : allAccounts) {
			int id = account.getId();
			if (id == actId) {
				return account;
			}
		}
		
		return null;
	}

	//////////////////////////////////////////////////////////////////////////////
	public List<Account> getAccounts() {
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
		return allAccounts;
	}

	///////////////////////////////////////////////////////////////////////////////
	public List<Account> getAccountsByUser(User u) {
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
		
		List<Account> userAccounts = new ArrayList<Account>();
		for(Account account : allAccounts) {
			int oid = account.getOwnerId();
			if (oid == u.getId()) {
				userAccounts.add(account);
			}
		}
		
		return userAccounts;
	}

	////////////////////////////////////////////////////////////////////////////////
	public Account updateAccount(Account a) {
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
		
		List<Account> updatedAccounts = new ArrayList<Account>();
		for(Account account : allAccounts) {
			int id = account.getId();
			if (id != a.getId()) {
				updatedAccounts.add(account);
			}
		}
		updatedAccounts.add(a);
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileLocation));
			oos.writeObject(allAccounts);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return a;
	}

	/////////////////////////////////////////////////////////////////////////////
	public boolean removeAccount(Account a) {
		List<Account> allAccounts = new ArrayList<Account>();
		List<Account> accountUpdate = new ArrayList<Account>();

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
		
		
        for(Account account : allAccounts) {
			int id = account.getId();
			if (id != a.getId()) {
				accountUpdate.add(account);
			}
		}
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileLocation));
			oos.writeObject(accountUpdate);
			oos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}

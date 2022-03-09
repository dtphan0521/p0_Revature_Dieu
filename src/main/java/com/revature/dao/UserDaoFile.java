package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.User;

/**
 * Implementation of UserDAO that reads and writes to a file
 */
public class UserDaoFile implements UserDao {
	
	public static String fileLocation = "src\\users.txt";
////////////////////////////////////////////////////////////////////////////////
	public User addUser(User user) {
		List<User> uList = getAllUsers();
		
		uList.add(user);
		
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileLocation));
				oos.writeObject(uList);
				oos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		return user;
	}
////////////////////////////////////////////////////////////////////////////////////
	public User getUser(Integer userId) {
		List<User> allUsers = new ArrayList<User>();

		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLocation));
			allUsers = (ArrayList<User>)ois.readObject();
			ois.close();
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		for(User user : allUsers) {
			int id = user.getId();
			if (id == userId) {
				return user;
			}
		}
		
		
		return null;
	}
//////////////////////////////////////////////////////////////////////////////////////
	public User getUser(String username, String pass) {
		List<User> allUsers = new ArrayList<User>();

		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLocation));
			allUsers = (ArrayList<User>)ois.readObject();
			ois.close();
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		for(User user : allUsers) {
			String n = user.getUsername();
			String p = user.getPassword();
			if (n == username & p == pass) {
				return user;
			}
		}
		
		
		return null;
	}
/////////////////////////////////////////////////////////////////////////////////
	public List<User> getAllUsers() {
		
		List<User> allUsers = new ArrayList<User>();
		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLocation));
			
			allUsers = (ArrayList<User>)ois.readObject();
			ois.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return allUsers;
	}
/////////////////////////////////////////////////////////////////////////////////////
	public User updateUser(User u) {
		List<User> allUsers = new ArrayList<User>();

		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLocation));
			allUsers = (ArrayList<User>)ois.readObject();
			ois.close();
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		for(User user : allUsers) {
			int id = user.getId();
			if (id == u.getId()) {
				allUsers.remove(user);
				allUsers.add(u);
			}
		}
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileLocation));
			oos.writeObject(allUsers);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return u;
	}
///////////////////////////////////////////////////////////////////////////	
	public boolean removeUser(User u) {
		List<User> allUsers = new ArrayList<User>();
		List<User> usersUpdate = new ArrayList<User>();

		try {
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileLocation));
			allUsers = (ArrayList<User>)ois.readObject();
			ois.close();
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
        for(User user : allUsers) {
			int id = user.getId();
			if (id != u.getId()) {
				usersUpdate.add(user);
			}
		}
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileLocation));
			oos.writeObject(usersUpdate);
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

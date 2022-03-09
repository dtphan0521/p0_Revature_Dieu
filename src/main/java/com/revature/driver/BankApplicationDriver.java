package com.revature.driver;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.beans.Account.AccountType;
import com.revature.beans.User.UserType;
import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoDB;
import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoDB;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoDB;
import com.revature.dao.UserDaoFile;
import com.revature.exceptions.OverdraftException;
import com.revature.exceptions.UnauthorizedException;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.utils.SessionCache;
import org.apache.log4j.*;

/**
 * This is the entry point to the application
 */

public class BankApplicationDriver {

	public static void main(String[] args) throws IOException {
			
			Scanner sc = new Scanner(System.in);
			UserDao userDao = new UserDaoDB();
			AccountDao accountDao = new AccountDaoDB();
			UserService userService = new UserService(userDao, accountDao);
			AccountService accountService = new AccountService(accountDao);
			TransactionDao transactionDao = new TransactionDaoDB();
			int ch = 0;
			int id =0;
			do {
				System.out.println("Welcome to Revature Bank");
				System.out.println("==================================");
				System.out.println("Select the Option :");
				System.out.println("==================================");
				System.out.println("1)Login as a Customer");
				System.out.println("2)Login as an Employee");
				System.out.println("3)Register");
				System.out.println("4)View Users ");
				System.out.println("5)Remove User ");
				System.out.println("6)Update User ");
				System.out.println("7)Exit");
				System.out.println("Enter your Choice 1-6");
				System.out.println("==================================");
				System.out.println("If you Don't have registered yet then Register yourself");
				ch = sc.nextInt();
				switch (ch) {
				case 1:
					int c=0;
					int accountType = 0;
					double startingBalance = 0;
					System.out.println("Customer Login Screen");
					System.out.println("Enter your username : ");
					String username1 = sc.next();
					System.out.println("Enter your password:");
					String password1 = sc.next();
					User loggedUser = userService.login(username1, password1);
					if (loggedUser != null) {
						System.out.println("Logged in Successfully!!!");
						SessionCache.setCurrentUser(loggedUser);
					
						do {
							System.out.println("1)Apply Account ");
							System.out.println("2)Deposit");
							System.out.println("3)Withdraw ");
							System.out.println("4)Fund Transfer ");
							System.out.println("5)Logout ");
							System.out.print("Enter your option [1-7]:");
						
							c = sc.nextInt();
							switch (c) {
							case 1:
								System.out.print("select the Account Type [1.Checking/2.Saving]: ");
								accountType = sc.nextInt();
								System.out.print("Enter Starting balance:");
								startingBalance = sc.nextDouble();
								Account account = new Account();
								account.setBalance(startingBalance);
								account.setOwnerId(loggedUser.getId());
								account.setType(accountType == 1 ? AccountType.CHECKING.toString()
										: AccountType.SAVINGS.toString());
								List<Account> accountList = new ArrayList<Account>();
								accountList.add(account);
								loggedUser.setAccounts(accountList);
								accountService.createNewAccount(loggedUser);
								break;
							case 2:
								System.out.println("Available Accounts for this user");
								accountService.getAccounts(loggedUser).forEach(System.out::println);
								System.out.print("Enter Account ID to Deposit :");
								int accountId = 0 ;
								accountId = sc.nextInt();
								System.out.print("Enter the amount to deposit :");
								double amount = 0;
								amount = sc.nextDouble();
								account = accountDao.getAccount(accountId);
								accountService.deposit(account, amount);
								try { accountService.deposit(accountDao.getAccount(accountId), amount);
								System.out.println("you've deposited " + amount); 
								System.out.println("Your balance is $"+ accountDao.getAccount(accountId).getBalance());
							}
							catch (UnsupportedOperationException | UnauthorizedException e) { 
								System.out.println("Sorry! We couldn't make a Deposit. Please try again");
								e.printStackTrace(); 
								
							}
								break;
							case 3:
								System.out.println("Enter your Account Number ");
								int accountId1 = sc.nextInt();
								System.out.println("How much would you like to withdraw?");
								double amount1 = sc.nextDouble();
								try { accountService.withdraw(accountDao.getAccount(accountId1), amount1);
									System.out.println("you've withdrawn " + amount1);
									System.out.println("\nYour new account balance is $" + accountDao.getAccount(accountId1).getBalance());
								} catch (OverdraftException | UnsupportedOperationException | UnauthorizedException e) {
									System.out.println("Sorry, we couldn't make a Withdrawal. Please try again");
									e.printStackTrace();
								}
								break;
							case 4:
								System.out.println("Enter your Account Number ");
								int fromAcc = sc.nextInt();
								System.out.println("Enter Account number of the Account you want to send to");
								int toAcc = sc.nextInt();
								System.out.println("How much would you like to transfer?");
								double amount3 = sc.nextDouble();
								try {accountService.transfer(accountDao.getAccount(fromAcc), accountDao.getAccount(toAcc), amount3);
									System.out.println("you've transferred " + amount3);
									System.out.println("\nYour new account balance from the account you just took money $" +accountDao.getAccount(fromAcc).getBalance());
									System.out.println("\nYour new account balance from the account you just sent to $" +accountDao.getAccount(toAcc).getBalance());
								} catch (UnsupportedOperationException | UnauthorizedException e) {
									System.out.println("INVALID. Please try again ");
									e.printStackTrace();
								}
								break;

							case 5:
								System.out.print("Do you want to Logout? (1.Yes/2.No) :");
								int logout = 0;
								logout = sc.nextInt();
								if (logout == 1) {
									SessionCache.setCurrentUser(null);
								}
								break;
							default:
								System.out.println("Enter a number between 1 to 6");
								break;
								}
						}while (c !=5);
				} else {System.out.println("Password or Username incorrect. Please try to login again");}
						
			break;
							
				case 2:
					int c1=0;
					
					System.out.println("Employee Login Screen");
					System.out.println("Enter your username : ");
					String username2 = sc.next();
					System.out.println("Enter your password:");
					String password2 = sc.next();
					User loggedUser2 = userService.login(username2, password2);
					if (loggedUser2 != null) {
						System.out.println("Logged in Successfully!!!");
						SessionCache.setCurrentUser(loggedUser2);
					
						do {
							System.out.println("1)Approve/Reject an Account ");
							System.out.println("2) View Transaction Log");
							System.out.println("3)Logout ");
							System.out.print("Enter your option [1-3]:");
						
							c1 = sc.nextInt();
							switch (c1) {
							case 1:
								System.out.println("Enter the account ID would like to change the status");
								int approveRejectId = sc.nextInt();
								System.out.println("Enter true or false to change the account status");
								boolean approveReject = sc.nextBoolean();
								try {accountService.approveOrRejectAccount(accountDao.getAccount(approveRejectId), approveReject); 
									System.out.println("you've changed this user's account status to "+accountDao.getAccount(approveRejectId));
								
								} catch (UnauthorizedException e) {
									System.out.println("INVALID. Please try again");
								}
								break;
								
							case 2:
								//System.out.println("Here is the full Transaction Log");
								System.out.println(transactionDao.getAllTransactions());//
								break;
								
							case 3:
								System.out.println("Successfully loged out");
								break;

							default:
								System.out.println("INVALID");
								break;
							}
						} while (c1 != 3);	
					break;						
					}
									
				case 3:
					System.out.println("Enter FirstName :");
					String FirstName = sc.next();
					
					System.out.println("Enter LastName :");
					String LastName = sc.next();
					
					System.out.println("Enter username :");
					String username = sc.next();

					System.out.println("Enter Password :");
					String password = sc.next();
					
					System.out.println("Enter UserType[EMPLOYEE/CUSTOMER]:");
		            String usertype = sc.next();
		            		
					User user = new User(FirstName, LastName, username, password, UserType.valueOf(usertype));
					userService.register(user);
					break;
							
						
				case 4:
					userDao.getAllUsers().forEach(System.out::println);
					break;
				case 5:
					System.out.print("Enter Id of the customer to remove: ");
					id = sc.nextInt();
					User u = userDao.getUser(id);
					System.out.println("Account Successfully removed");
					userDao.removeUser(u);
					break;
				case 6:
					System.out.print("Enter Id of the customer to Update: ");
					id = sc.nextInt();
					System.out.print("Enter First Name to Update :");
					String fName = sc.next();
					System.out.print("Enter Last Name to Update:");
					String lName = sc.next();
					System.out.print("Enter Password to Update:");
					String usern = sc.next();
					System.out.print("Enter Password to Update:");
					String pass = sc.next();
					System.out.println("Account Successfully updated");
					User updatedUser = new User(id,fName,lName,usern,pass,UserType.CUSTOMER);
					userDao.updateUser(updatedUser);
					
					break;
				case 7:
					System.out.println("Thanks for using Revature Bank!!! Have a Nice Day!!!");
					System.exit(0);
					break;

				default:
					System.out.println("Invalid Choice... Please enter input between 1-3");
					break;
						}
			} while (ch != 7);
			sc.close();
				}
			}
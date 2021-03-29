package com.simplilearn.lockme.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.simplilearn.lockme.model.UserCredentials;
import com.simplilearn.lockme.model.Users;


public class Authentication {
	//input data
	private static Scanner keyboard;
	private static Scanner input;
	private static Scanner lockerInput;
	private static Scanner readEntry;
	//output data 
	private static PrintWriter output;
	private static PrintWriter lockerOutput;
	private static PrintWriter logEntry;
	//model to store data.
	private static Users users;
	private static UserCredentials userCredentials;
	
	
	public static void main(String[] args) {
		initApp();
		welcomeScreen();
		signInOptions();

	}
	public static void signInOptions() {
		System.out.println("1 . Registration ");
		System.out.println("2 . Login ");	
		System.out.println("3.  Sort Database");
		int option = keyboard.nextInt();
		switch(option) {
			case 1 : 
				registerUser();
				break;
			case 2 :
				loginUser();
				break;
			case 3 :
				sortDatabase();
				break;
			default :
				System.out.println("Please select 1 to 3");
				break;
		}
		keyboard.close();
		input.close();
	}
	
	public static void lockerOptions(String inpUsername) {
		System.out.println("1 . FETCH ALL STORED CREDENTIALS ");
		System.out.println("2 . STORE CREDENTIALS ");
		System.out.println("3 . DELETE CREDENTIALS ");
		System.out.println("4 . EXIT USER ");
		int option = keyboard.nextInt();
		switch(option) {
			case 1 : 
				fetchCredentials(inpUsername);
				break;
			case 2 :
				storeCredentials(inpUsername);
				break;	
			case 3 : 
				 deleteCredentials(inpUsername);
				 break;
			case 4 :
				logoutUser();
				break;
			default :
				System.out.println("");
				break;
		}
		lockerInput.close();
	}
	
	public static void registerUser() {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO REGISTRATION PAGE	*");
		System.out.println("*					*");
		System.out.println("==========================================");
		
		System.out.println("Enter Username :");
		String username = keyboard.next();
		users.setUsername(username);
		
		System.out.println("Enter Password :");
		String password = keyboard.next();
		users.setPassword(password);
		
		output.println(users.getUsername());
		lockerOutput.println(users.getUsername()+" "+users.getPassword());
		//output.println(users.getPassword());
		
		System.out.println("User Registration Suscessful !");
		output.close();
		lockerOutput.close();
	
	}
	public static void loginUser() {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO LOGIN PAGE	 *");
		System.out.println("*					*");
		System.out.println("==========================================");
		System.out.println("Enter Username :");
		String inpUsername = keyboard.next();
		boolean found = false;
		while(lockerInput.hasNext() && !found) {
			String currentLine = lockerInput.nextLine();
			String[] loginDetail = currentLine.split(" ");
		    String username = loginDetail[0];
		    String passwd = loginDetail[1];
			if(username.equals(inpUsername)) {
				System.out.println("Enter Password :");
				String inpPassword = keyboard.next();
				if(passwd.equals(inpPassword)) {
					System.out.println("Login Successful ! 200OK");
					found = true;
					lockerOptions(inpUsername);
					break;
				}
			}
		}
		if(!found) {
			System.out.println("User Not Found : Login Failure : 404");
			logoutUser();		}
		
	}
	
	public static void welcomeScreen() {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   Welcome To LockMe.com		*");
		System.out.println("*   Your Personal Digital Locaker	*");
		System.out.println("*					*");
		System.out.println("==========================================");
		
	}
	
	public static void logoutUser() {
		System.out.println("=======================================================");
		System.out.println("*					*");
		System.out.println("*   THANKS FOR VISITING LOCK ME - SUCCESSFULL EXIT	*");
		System.out.println("*					*");
		System.out.println("=======================================================");
		
	}
	//store credentails
	public static void storeCredentials(String loggedInUser) {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO DIGITAL LOCKER STORE YOUR CREDS HERE	 *");
		System.out.println("*					*");
		System.out.println("==========================================");
		
		userCredentials.setLoggedInUser(loggedInUser);
		String fileName=loggedInUser+".txt";
		File newUserEntry = new File(fileName);
		PrintWriter out = null;
		try {
			if ( newUserEntry.exists() && !newUserEntry.isDirectory() ) {
				logEntry = new PrintWriter(new FileWriter(new File(fileName), true));
			}
			else {
				logEntry = new PrintWriter( new FileWriter(newUserEntry,true));
			}
		}catch (IOException e) {
			System.out.println("404 : File Not Found ");
		}
		System.out.println("Enter Site Name :");
		String siteName = keyboard.next();
		userCredentials.setSiteName(siteName);
		
		System.out.println("Enter Username :");
		String username = keyboard.next();
		userCredentials.setUsername(username);
		
		System.out.println("Enter Password :");
		String password = keyboard.next();
		userCredentials.setPassword(password);
		
		//logEntry.println(userCredentials.getLoggedInUser());
		logEntry.println(userCredentials.getSiteName());
		logEntry.println(userCredentials.getUsername());
		logEntry.println(userCredentials.getPassword());
		
		System.out.println("YOUR CREDS ARE STORED AND SECURED!");
		logEntry.close();	
		
		logoutUser();
	}
	
	//fetch credentials
	public static void fetchCredentials(String inpUsername) {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO DIGITAL LOCKER 	 *");
		System.out.println("*   YOUR CREDS ARE 	 *");
		System.out.println("*					*");
		System.out.println("==========================================");
		System.out.println(inpUsername);
				
		String fileName=inpUsername+".txt";
		File userEntry = new File(fileName);		
		try {
			if ( userEntry.exists() && !userEntry.isDirectory() ) {				
				readEntry = new Scanner(userEntry);
			}
			else {
				System.out.println("User File is not available ");	
				logoutUser();
				return;
			}
		}catch (IOException e) {
			System.out.println("404 : File Not Found ");
		}
	
		while(readEntry.hasNext()) {
//			System.out.println(lockerInput.hasNext());
			//if(lockerInput.next().equals(inpUsername)) {
				System.out.println("Site Name: "+readEntry.next());
				System.out.println("User Name: "+readEntry.next());
				System.out.println("User Password: "+readEntry.next());
			//}
		}
		readEntry.close();
		logoutUser();
	}
	
	public static void sortDatabase() {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO DIGITAL LOCKER 	 *");
		System.out.println("*   SORTED DATABASE IS 	 *");
		System.out.println("*					*");
		System.out.println("==========================================");
				
		ArrayList<String> entries = new ArrayList<String>();

		if (!input.hasNext()) {
			System.out.println("There is no database entry yet");
			logoutUser();
			return;
		}
		while(input.hasNext()) {
			String currentLine = input.nextLine();			
		    entries.add(currentLine);
		}		
		Collections.sort(entries);
		for (String entry : entries)
		{
			System.out.println(entry);
		}
		input.close();
		logoutUser();
	}
	//fetch credentials
	public static void deleteCredentials(String inpUsername) {
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO DIGITAL LOCKER 	 *");
		System.out.println("*   YOUR CREDS ARE 	 *");
		System.out.println("*					*");
		System.out.println("==========================================");
		System.out.println(inpUsername);
				
		String fileName=inpUsername+".txt";
		File userEntry = new File(fileName);		
		
		if ( userEntry.exists() && !userEntry.isDirectory() ) {				
			userEntry.delete();
			System.out.println("User File is deleted ");	
			logoutUser();
			return;				
		}
		else {
			System.out.println("User File is not available ");	
			logoutUser();
			return;
		}
		
		
	}
	
	
	public static void initApp() {

		File  dbFile = new File("database.txt");
		File  lockerFile = new File("locker-file.txt");
		
		try {
			//read data from db file
			input = new Scanner(dbFile);
			
			//red data from locker file
			lockerInput = new Scanner(lockerFile);
			
			//read data from keyboard
			keyboard = new Scanner(System.in);
			
			//out put 
			output = new PrintWriter( new FileWriter(dbFile,true));
			lockerOutput = new PrintWriter( new FileWriter(lockerFile,true));
			
			users = new Users();
			userCredentials  = new UserCredentials();
			
			
		} catch (IOException e) {
			System.out.println("404 : File Not Found ");
		}
		
	}
/////////sort	
	
}
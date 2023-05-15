package src;
//Title: Banking Application
//Author: Edgar Bahena
//Email: ebahena5@toromail.csudh.edu
//Date: 05/12/2023

import src.ExtraExceptions.AccountClosedException;
import src.ExtraExceptions.InsufficientBalanceException;
import src.ExtraExceptions.NoSuchAccountException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainDriver{
    public static final String ACCOUNT_ID = "%nThank you, the account number is: %d%n%n";
    public static Scanner scnr = new Scanner(System.in);
    public static void main(String[] args){
        int userInput = 0;

        do {

            try {

                userInput = printMenu();
                scnr.nextLine();
                checkChoices(userInput);

            } catch (InputMismatchException ime) {

                System.out.print(ime.getMessage());
                scnr.nextLine();

            }

        } while (userInput != 9);


    }

    public static int printMenu() throws InputMismatchException{
        System.out.println("1 - Open a Checking Account");
        System.out.println("2 - Open a Savings Account");
        System.out.println("3 - List Accounts");
        System.out.println("4 - Account Statements");
        System.out.println("5 - Deposit Funds");
        System.out.println("6 - Withdraw Funds");
        System.out.println("7 - Close an Account");
        System.out.println("8 - Save Transactions");
        System.out.println("9 - Exiting");
        System.out.print("Please Enter your choice: ");
        if(!scnr.hasNextInt()){
            throw new InputMismatchException("Error, input must be an integer\n\n");
        }

        return (scnr.nextInt());
    }

    public static void checkChoices(int userSelection) {
        switch(userSelection) {
                    case 1: openCheckingAccount();
                    break;

                    case 2: openSavingsAccount();
                    break;

                    case 3: listAccounts();
                    break;

                    case 4: accountStatements();
                    break;

                    case 5: depositFunds();
                    break;

                    case 6: withdrawFunds();
                    break;

                    case 7: closeAccount();
                    break;

                    case 8: saveTransactions();
                    break;

                    case 9: System.out.println("Exiting");
                    break;

            }
    }

    public static void openCheckingAccount(){
        try {

            Person customer = createCustomer();
            double overdraft = promptUserOverdraft();
            printId(ACCOUNT_ID, Bank.createCheckingAccount(customer, overdraft));

        } catch (InputMismatchException ime){

            System.out.print(ime.getMessage());
            System.out.println();

        }
    }
    public static void openSavingsAccount(){
        Person customer = createCustomer();
        printId(ACCOUNT_ID, Bank.createSavingsAccount(customer));
    }

    public static void listAccounts() {
        try {

            OutputStream out = System.out;
            Bank.listAccountsFromList(out);

        } catch (IOException ioe) {

            System.out.println(ioe.getMessage());
            System.out.println();

        }
    }

    public static void accountStatements(){
        try {

            System.out.print(Bank.getAccountStatements(promptUserAccountNumber()));

        } catch (InputMismatchException ime) {

            System.out.println(ime.getMessage());
            System.out.println();

        } catch (NoSuchAccountException nsae) {

            System.out.println(nsae.getMessage());

        }
    }

    public static void depositFunds(){
        try {

            int userAccountNumber = promptUserAccountNumber();
            double depositAmount = promptUserDepositAmount();
            System.out.print(Bank.depositIntoAccount(userAccountNumber, depositAmount));

        }  catch (InputMismatchException ime){

            System.out.println(ime.getMessage());
            System.out.println();

        } catch (AccountClosedException e) {

            System.out.println(e.getMessage());

        } catch (NoSuchAccountException | InsufficientBalanceException nsae) {

            System.out.println(nsae.getMessage());

        }
    }
    public static void withdrawFunds(){
        try {

            int userAccountNumber = promptUserAccountNumber();
            double withdrawAmount = promptUserWithdrawAmount();
            System.out.print(Bank.withdrawFromAccount(userAccountNumber, withdrawAmount));

        } catch (InputMismatchException ime) {

            System.out.println(ime.getMessage());
            System.out.println();

        } catch (NoSuchAccountException | AccountClosedException | InsufficientBalanceException nsae) {

            System.out.println(nsae.getMessage());

        }
    }
    public static void closeAccount(){
        try {

            int accountCloseNumber = promptUserCloseAccountNumber();
            System.out.print(Bank.closeAccountInList(accountCloseNumber));


        } catch (InputMismatchException ime) {

            System.out.println(ime.getMessage());
            System.out.println();

        } catch (NoSuchAccountException nsae) {

            System.out.println(nsae.getMessage());

        }
    }

    public static void saveTransactions() {
        try {

            int userAccountNumber = promptUserAccountNumber();
            File file = new File("transactions.txt");
            if(!file.exists()) {
                file.createNewFile();
            }

            OutputStream out = new FileOutputStream(file);
            Bank.listTransactionsFromAccount(userAccountNumber, out);
            System.out.print("Successfully Saved onto transactions.txt\n\n");
            out.close();

        } catch (NoSuchAccountException nse) {

            System.out.println(nse);

        } catch (IOException ioe) {

            System.out.println(ioe.getMessage());

        }
    }

    //END OF SWITCH STATEMENTS


    //METHODS FOR EASIER ACCESS, PROMPTING, AND PRINTING
    public static Person createCustomer(){
        //Returns a Reference to created person obj
        System.out.print("Enter first name: ");
        String firstName = scnr.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scnr.nextLine();
        System.out.print("Enter social security number: ");
        String SSN = scnr.nextLine();
        Person customer = new Person(firstName, lastName, SSN);
        return (customer);
    }

    public static int promptUserAccountNumber() throws InputMismatchException{
        System.out.print("Enter account number: ");
        if(!scnr.hasNextInt()) {

            scnr.nextLine();
            throw new InputMismatchException("Error, input for account number must be an integer\n");

        }

        int accountNumber = scnr.nextInt();
        scnr.nextLine();
        return accountNumber;
    }

    public static double promptUserOverdraft() throws InputMismatchException{
        System.out.print("Enter overdraft limit: ");
        if(!scnr.hasNextDouble()) {

            scnr.nextLine();
            throw new InputMismatchException("Error, input for overdraft must be a number!\n");

        }

        double overdraft = scnr.nextDouble();
        scnr.nextLine();
        return overdraft;
    }

    public static double promptUserDepositAmount() throws InputMismatchException {
        System.out.print("Enter the amount to deposit: ");
        if(!scnr.hasNextDouble()) {

            scnr.nextLine();
            throw new InputMismatchException("Error, input must be a number in order to deposit!\n");

        }

        double depositAmount = scnr.nextDouble();
        scnr.nextLine();
        return depositAmount;
    }

    public static double promptUserWithdrawAmount() throws InputMismatchException{
        System.out.print("Enter the amount to withdrawal amount: ");
        if(!scnr.hasNextDouble()) {

            scnr.nextLine();
            throw new InputMismatchException("Error, input for account number must be a number in order to withdraw!\n");

        }

        double withdrawAmount = scnr.nextDouble();
        scnr.nextLine();
        return withdrawAmount;
    }

    public static int promptUserCloseAccountNumber() throws InputMismatchException{
        System.out.print("Enter account number to close: ");
        if(!scnr.hasNextInt()) {

            scnr.nextLine();
            throw new InputMismatchException("Error, input for closing must be an integer!\n");

        }

        int accountCloseNumber = scnr.nextInt();
        scnr.nextLine();
        return accountCloseNumber;
    }

    public static void printId(String statement, Account info){
        System.out.print(String.format(statement, info.getId()));
    }

}
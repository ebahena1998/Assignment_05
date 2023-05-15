package src;

import src.ExtraExceptions.AccountClosedException;
import src.ExtraExceptions.InsufficientBalanceException;
import src.ExtraExceptions.NoSuchAccountException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Bank{
    public static final ArrayList<Account> ACCOUNT_LIST = new ArrayList<Account>();
    private static int accountId = 100;
    public static Account createCheckingAccount(Person customer, double overdraft){
        Account bankCheckingAccount = new CheckingAccount(customer, accountId++, overdraft);
        ACCOUNT_LIST.add(bankCheckingAccount);
        return bankCheckingAccount;
    }
    public static Account createSavingsAccount(Person customer){
        Account bankSavingsAccount = new SavingsAccount(customer, accountId++);
        ACCOUNT_LIST.add(bankSavingsAccount);
        return bankSavingsAccount;
    }
    public static Account findAccount(int userInput) throws NoSuchAccountException {
        for(Account temp: ACCOUNT_LIST) {
            if (temp.getId() == userInput) {
                return temp;
            }
        }
        throw new NoSuchAccountException("Account not found!");
    }

    public static void listAccountsFromList(OutputStream out) throws IOException {
        out.write((byte) 10);
        for(Account temp: ACCOUNT_LIST) {
            out.write(temp.toString().getBytes());
            out.write((byte) 10);
        }
        out.write((byte) 10);
        out.flush();
    }

    public static String getAccountStatements(int userInput) throws NoSuchAccountException{
        return String.format("%sBalance: %.2f%n%n",
                findAccount(userInput).listOfAccountTransactions(), findAccount(userInput).getBalance());

    }

    public static String depositIntoAccount(int userInput, double amount) throws NoSuchAccountException,
            InsufficientBalanceException, AccountClosedException {

        findAccount(userInput).deposit(amount);
        return (String.format("Deposit successful, the new balance is: %.2f%n%n", findAccount(userInput).getBalance()));

    }

    public static String withdrawFromAccount(int userInput, double amount) throws NoSuchAccountException,
            AccountClosedException, InsufficientBalanceException {

        findAccount(userInput).withdraw(amount);
        return (String.format("Withdraw successful, the new balance is: %.2f%n%n", findAccount(userInput).getBalance()));

    }

    public static String closeAccountInList(int userInput) throws NoSuchAccountException{

        findAccount(userInput).setStatus(false);
        return (String.format("Account closed, current balance is %.2f, deposits are no longer possible!%n%n",
                findAccount(userInput).getBalance()));

    }

    public static void listTransactionsFromAccount(int userInput, OutputStream out) throws NoSuchAccountException,
            IOException {

        findAccount(userInput).saveInFile(out);

    }



}



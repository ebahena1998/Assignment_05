package src;

import src.ExtraExceptions.AccountClosedException;
import src.ExtraExceptions.InsufficientBalanceException;

public class SavingsAccount extends Account{
    public SavingsAccount(Person customer, int id){
        super(customer, id, "(Savings)");
    }

    public void withdraw(double amount) throws InsufficientBalanceException, AccountClosedException {
        if(getBalance() - amount < 0) {
            throw new InsufficientBalanceException(String.format("Withdraw failed, the balance is: %.2f%n%n",
                    super.getBalance()));
        }

        super.withdraw(amount);
    }
}
package drill1;

import java.util.Random;


public class BankAccount
{
    private int ID;
    private int balance;
    private String name;

    public BankAccount(int ID, int balance, String name) {
        this.ID = ID;
        this.balance = balance;
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}

class DepositMoney extends Thread{

    private BankAccount account;
    private Object syncObject;


    public DepositMoney(BankAccount account, Object syncObject) {
        this.account = account;
        this.syncObject = syncObject;
    }

    @Override
    public void run() {
        for (int i = 0; i <20; i++) {
            int balance = account.getBalance();
            int sumOfDeposit = new Random().nextInt(350) + 10;
            account.setBalance(balance + sumOfDeposit);
            System.out.println("after deposit of " + sumOfDeposit + " in this account the balance = " + account.getBalance() +  " --> " +i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (syncObject){
                syncObject.notify();
            }
        }


    }
}

class WithdrawMoney extends Thread{

    private BankAccount account;
    private Object syncObject;

    public WithdrawMoney(BankAccount account, Object syncObject) {
        this.account = account;
        this.syncObject = syncObject;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            int balance = account.getBalance();
            int sumOfWithdraw = new Random().nextInt(1000) + 10;
            if ((balance - sumOfWithdraw) < 0) //במידה ונרצה לחכות עד שיהיה מספיק כסף בחשבון למשיכה הרצויה ניתן להשתמש בלולאת while
            {
                System.out.println("This account doesn't have enough money the withdraw asked = "+ sumOfWithdraw + " and the balance = " + balance+ " --> " +i);
                synchronized (syncObject){
                    try {
                        syncObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            balance = account.getBalance();
            if ((balance - sumOfWithdraw) >= 0)
            {
                account.setBalance((balance - sumOfWithdraw));
                System.out.println("after withdraw of " + sumOfWithdraw + " in this account the balance = " + account.getBalance() + " --> " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

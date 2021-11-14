package drill1;

public class BankAccountMain
{
    public static void main(String[] args) {

        Object syncObject = new Object();
        BankAccount account = new BankAccount(3183, 1000, "Haim");
        DepositMoney d = new DepositMoney(account,syncObject);
        WithdrawMoney w = new WithdrawMoney(account,syncObject);


        w.start();
        d.start();

        System.out.println("I am the main thread!!!");
    }

}

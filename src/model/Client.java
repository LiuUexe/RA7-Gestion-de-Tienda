package model;
 
import main.Payable;
 
public class Client extends Person implements Payable
{
    final private int MEMBER_ID = 456;
    final private Amount BALANCE = new Amount (50);
    private int memberId = MEMBER_ID;
    private Amount balance = new Amount(50);
 
    public Client(String name, int memberId, Amount balance)
    {
        super(name);
        this.memberId = MEMBER_ID;
        this.balance = BALANCE;
    }
 
    public String getName()
    {
        return name;
    }
 
    public void setName(String name)
    {
        this.name = name;
    }
 
    public Amount getBalance()
    {
        return balance;
    }
 
    public void setBalance(Amount balance)
    {
        this.balance = balance;
    }
    @Override
    public boolean pay(Amount amount)
    {
        double wallet = BALANCE.getValue() - amount.getValue();
        if (wallet >= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
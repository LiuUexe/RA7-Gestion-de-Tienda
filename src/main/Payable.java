package main;
import model.Amount;
/**
 *
 * @author ester
 */
public interface Payable {
    public boolean pay(Amount amount);
}

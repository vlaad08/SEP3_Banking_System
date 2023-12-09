package Database.DTOs;

public class CreditInterestDTO {
    private String account_id;
    private double amount;
    private double newBalance;

    public CreditInterestDTO(String account_id, double amount, double newBalance) {
        this.account_id = account_id;
        this.amount = amount;
        this.newBalance = newBalance;
    }

    public String getAccount_id() {
        return account_id;
    }

    public double getAmount() {
        return amount;
    }

    public double getNewBalance() {
        return newBalance;
    }
}

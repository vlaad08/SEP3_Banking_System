package Database.DTOs;

public class DepositRequestDTO {
    private String account_id;
    private double amount;

    public DepositRequestDTO(String account_id, double amount) {
        this.account_id = account_id;
        this.amount = amount;
    }

    public String getAccount_id() {
        return account_id;
    }

    public double getAmount() {
        return amount;
    }
}

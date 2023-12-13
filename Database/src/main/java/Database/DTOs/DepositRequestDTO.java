package Database.DTOs;

public class DepositRequestDTO {
    private String account_id;
    private double amount;
    private double updatedBalance;

    public DepositRequestDTO(String account_id, double amount, double updateBalance) {
        this.account_id = account_id;
        this.amount = amount;
        this.updatedBalance = updateBalance;
    }

    public String getAccount_id() {
        return account_id;
    }

    public double getAmount() {
        return amount;
    }

    public double getUpdatedBalance() {
        return updatedBalance;
    }
}

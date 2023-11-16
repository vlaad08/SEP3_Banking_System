package Database.DTOs;

public class TransferRequestDTO {
    private String senderAccount_id;
    private String recipientAccount_id;
    private double amount;
    private String message;

    public TransferRequestDTO(String senderAccount_id, String recipientAccount_id, double amount, String message) {
        this.senderAccount_id = senderAccount_id;
        this.recipientAccount_id = recipientAccount_id;
        this.amount = amount;
        this.message = message;
    }

    public String getSenderAccount_id() {
        return senderAccount_id;
    }

    public String getRecipientAccount_id() {
        return recipientAccount_id;
    }

    public double getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }
}

package Database.DTOs;

public class UpdatedBalancesForTransferDTO {
    public double newSenderBalance;
    public double newReceiverBalance;
    public String message;
    public String senderId;
    public String receiverId;
    public double amount;

    public UpdatedBalancesForTransferDTO(double newSenderBalance, double newReceiverBalance, String message, String senderId, String receiverId, double amount) {
        this.newSenderBalance = newSenderBalance;
        this.newReceiverBalance = newReceiverBalance;
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }

    public double getNewSenderBalance() {
        return newSenderBalance;
    }

    public void setNewSenderBalance(double newSenderBalance) {
        this.newSenderBalance = newSenderBalance;
    }

    public double getNewReceiverBalance() {
        return newReceiverBalance;
    }

    public void setNewReceiverBalance(double newReceiverBalance) {
        this.newReceiverBalance = newReceiverBalance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
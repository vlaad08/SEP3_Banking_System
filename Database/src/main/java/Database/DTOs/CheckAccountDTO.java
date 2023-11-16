package Database.DTOs;

public class CheckAccountDTO {
    private String recipientAccount_id;

    public CheckAccountDTO(String recipientAccount_id) {
        this.recipientAccount_id = recipientAccount_id;
    }

    public String getRecipientAccount_id() {
        return recipientAccount_id;
    }
}


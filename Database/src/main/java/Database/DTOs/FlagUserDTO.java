package Database.DTOs;

public class FlagUserDTO {
    private int senderId;

    public FlagUserDTO(int senderId) {
        this.senderId = senderId;
    }

    public int getSenderId() {
        return senderId;
    }
}

package Database.DTOs;

public class CheckAccountIdDTO {
    private String srecipientAcoount_id;

    public CheckAccountIdDTO(String srecipientAcoount_id) {
        this.srecipientAcoount_id = srecipientAcoount_id;
    }

    public String getSrecipientAcoount_id() {
        return srecipientAcoount_id;
    }
}

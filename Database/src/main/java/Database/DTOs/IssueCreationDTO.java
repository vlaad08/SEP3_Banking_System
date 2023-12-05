package Database.DTOs;

public class IssueCreationDTO {
    private String title;
    private String body;
    private int ownerId;

    public IssueCreationDTO(String title, String body, int ownerId) {
        this.title = title;
        this.body = body;
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getOwnerId() {
        return ownerId;
    }
}

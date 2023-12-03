package Database.DTOs;

public class MessageDTO {
    private String title;
    private int owner;
    private String body;
    private int issueId;

    public MessageDTO(String title, int owner, String body, int issueId) {
        this.title = title;
        this.owner = owner;
        this.body = body;
        this.issueId = issueId;
    }

    public String getTitle() {
        return title;
    }

    public int getOwner() {
        return owner;
    }

    public String getBody() {
        return body;
    }

    public int getIssueId() {
        return issueId;
    }
}

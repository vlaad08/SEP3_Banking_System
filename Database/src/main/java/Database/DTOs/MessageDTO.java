package Database.DTOs;

import com.google.protobuf.Timestamp;

public class MessageDTO {
    private String title;
    private int owner;
    private String body;
    private int issueId;
    private Timestamp creation_time;

    public MessageDTO(String title, int owner, String body, int issueId, Timestamp creation_time) {
        this.title = title;
        this.owner = owner;
        this.body = body;
        this.issueId = issueId;
        this.creation_time = creation_time;
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

    public Timestamp getCreation_time() {
        return creation_time;
    }
}

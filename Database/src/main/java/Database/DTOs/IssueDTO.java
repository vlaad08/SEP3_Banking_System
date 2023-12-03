package Database.DTOs;

import java.time.LocalDate;

public class IssueDTO {
    private String title;
    private String body;
    private int ownerId;

    public IssueDTO(String title, String body, int ownerId) {
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

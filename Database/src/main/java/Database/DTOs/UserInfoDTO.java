package Database.DTOs;

public class UserInfoDTO
{
    private String email;

    public UserInfoDTO(String email){
            this.email = email;
    }

    public String getEmail(){
        return email;
    }
}

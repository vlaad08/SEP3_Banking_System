package Database.DTOs;

public class UserInfoEmailDTO
{
    private String email;

    public UserInfoEmailDTO(String email){
            this.email = email;
    }

    public String getEmail(){
        return email;
    }
}

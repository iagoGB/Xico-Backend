package br.com.smd.xico.dto;

import java.util.List;

import br.com.smd.xico.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class UserTO {
    private Long id;
    private String name;
    private String lastName;
    private String nickname;
    private String image;
    private String email;
    private String password;
    private String description;
    private String entryDate;
    private List<String> interests;
    private String fb;
    private String ig;
    private List<String> tools;
    private List<PortfolioTO> projects;
    
    public static UserTO parse(UserModel userModel){
        return new UserTO(
            userModel.getId(), 
            userModel.getName(),
            userModel.getLastName(),
            userModel.getNickname(),
            userModel.getImage(),
            userModel.getEmail(), 
            null, 
            userModel.getDescription(),
            userModel.getEntryDate(),
            userModel.getInterests(),
            userModel.getFb(),
            userModel.getIg(), 
            userModel.getTools(), 
            PortfolioTO.parse(userModel.getProjects())
        );

    }
}
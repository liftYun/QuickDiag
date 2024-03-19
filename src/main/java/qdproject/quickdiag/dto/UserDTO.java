package qdproject.quickdiag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import qdproject.quickdiag.entity.UserEntity;

// html의 값을 자동으로 DTO타입으로 읽어준다


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {

    private String user_id;
    private String user_password;
    private String user_name;
    private String user_phone_number;
    private String user_birthday;
    private String user_gender;

    public static UserDTO toUserDTO(UserEntity userEntity){

        UserDTO userDTO = new UserDTO();

        userDTO.setUser_id(userEntity.getUser_id());
        userDTO.setUser_password(userEntity.getUser_password());
        userDTO.setUser_name(userEntity.getUser_name());
        userDTO.setUser_phone_number(userEntity.getUser_phoneNumber());
        userDTO.setUser_birthday(userEntity.getUser_birthday());
        userDTO.setUser_gender(userEntity.getUser_gender());

        return userDTO;


    }
}

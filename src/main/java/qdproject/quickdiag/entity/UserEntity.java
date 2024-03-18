package qdproject.quickdiag.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import qdproject.quickdiag.dto.UserDTO;

@Entity
@Setter
@Getter
@Table(name = "user") //이녀석이 member_table라는 테이블을 생성함
public class UserEntity {

    @Id//pk 지정
    private String user_id;
    @Column
    private String user_password;
    @Column
    private String user_name;
    @Column
    private String user_phoneNumber    ;
    @Column
    private String user_birthday;
    @Column
    private String user_gender;

    public static UserEntity toUserEntity(UserDTO userDTO){
        //DTO를 받아서 Entity로 변환
        UserEntity userEntity = new UserEntity();

        userEntity.setUser_id(userDTO.getUser_id());
        userEntity.setUser_password(userDTO.getUser_password());
        userEntity.setUser_name(userDTO.getUser_name());
        userEntity.setUser_phoneNumber(userDTO.getUser_phone_number());
        userEntity.setUser_birthday(userDTO.getUser_birthday());
        userEntity.setUser_gender(userDTO.getUser_gender());

        return userEntity;
        //dto에서 받은 dto객체를 entity 객체로 변환하는 역하을 하는 메소드
    }
}
package qdproject.quickdiag.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import qdproject.quickdiag.dto.DataDTO;

@Entity
@Setter
@Getter
@Table(name = "data") //이녀석이 member_table라는 테이블을 생성함
public class DataEntity {

    @Id
    private String user_id; // 프라이머리 키

    @Column
    private String user_family;
    @Column
    private String user_smoking;
    @Column
    private String user_esmoking;
    @Column
    private String user_drinking;
    @Column
    private String user_workout;
    @Column
    private String user_agree;

    // DTO를 엔티티로 변환하는 메서드
    public static DataEntity toDataEntity(DataDTO dataDTO){
        //DTO를 받아서 Entity로 변환
        DataEntity dataEntity = new DataEntity();

        // 프라이머리 키 설정
        dataEntity.setUser_id(dataDTO.getUser_id());

        // 나머지 필드 설정
        dataEntity.setUser_family(dataDTO.getUser_family());
        dataEntity.setUser_smoking(dataDTO.getUser_smoking());
        dataEntity.setUser_esmoking(dataDTO.getUser_esmoking());
        dataEntity.setUser_drinking(dataDTO.getUser_drinking());
        dataEntity.setUser_workout(dataDTO.getUser_workout());
        dataEntity.setUser_agree(dataDTO.getUser_agree());

        return dataEntity;
    }
}

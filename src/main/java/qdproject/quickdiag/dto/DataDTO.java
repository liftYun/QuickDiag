package qdproject.quickdiag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import qdproject.quickdiag.entity.DataEntity;

// html의 값을 자동으로 DTO타입으로 읽어준다


@Getter
@Setter
@NoArgsConstructor
@ToString
public class DataDTO {

    private String user_id;
    private String user_family;
    private String user_smoking;
    private String user_esmoking;
    private String user_drinking;
    private String user_workout;
    private String user_agree;

    public static DataDTO toDataDTO(DataEntity dataEntity){

        DataDTO dataDTO = new DataDTO();

        dataDTO.setUser_id(dataEntity.getUser_id());
        dataDTO.setUser_family(dataEntity.getUser_family());
        dataDTO.setUser_smoking(dataEntity.getUser_smoking());
        dataDTO.setUser_esmoking(dataEntity.getUser_esmoking());
        dataDTO.setUser_drinking(dataEntity.getUser_drinking());
        dataDTO.setUser_workout(dataEntity.getUser_workout());
        dataDTO.setUser_agree(dataEntity.getUser_agree());

        return dataDTO;


    }
}

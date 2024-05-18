package qdproject.quickdiag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import qdproject.quickdiag.dto.DataDTO;
import qdproject.quickdiag.entity.DataEntity;
import qdproject.quickdiag.repository.DataRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataService {
    private final DataRepository dataRepository;


    public void save(DataDTO dataDTO) {
        DataEntity dataEntity = DataEntity.toDataEntity(dataDTO);
        dataRepository.save(dataEntity);
    }

    public boolean isDataPresent(String sessionId) {
        System.out.println(sessionId);
        Optional<DataEntity> dataEntity = dataRepository.findById(sessionId); // 데이터 찾기
        return dataEntity.isPresent(); // 데이터의 존재 여부를 확인
    }


    public DataDTO getCheckUserSelectedData(String loginUserId) {
        Optional<DataEntity> optionalDataEntity = dataRepository.findById(loginUserId);
        //아이디에 해당하는 정보를 데이터베이스에서 Entity타입으로 가져옴
        if(optionalDataEntity.isPresent()){
            System.out.println("서비스의 경우" + DataDTO.toDataDTO(optionalDataEntity.get()));
            return DataDTO.toDataDTO(optionalDataEntity.get());
            // 엔티티에 있는 정보를 dto 타입으로 바꾼 뒤 서비스로 반환
        }
        else{
            return null;
        }
    }
}

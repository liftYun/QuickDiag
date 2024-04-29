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

}

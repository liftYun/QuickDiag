package qdproject.quickdiag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import qdproject.quickdiag.dto.DataDTO;
import qdproject.quickdiag.entity.DataEntity;
import qdproject.quickdiag.repository.DataRepository;


@Service
@RequiredArgsConstructor
public class DataService {
    private final DataRepository dataRepository;


    public void save(DataDTO dataDTO) {
        DataEntity dataEntity = DataEntity.toDataEntity(dataDTO);
        dataRepository.save(dataEntity);
    }
}

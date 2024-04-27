package qdproject.quickdiag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qdproject.quickdiag.entity.DataEntity;

public interface DataRepository extends JpaRepository<DataEntity, String> {

}

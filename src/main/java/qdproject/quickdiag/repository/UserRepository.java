package qdproject.quickdiag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import qdproject.quickdiag.dto.UserDTO;
import qdproject.quickdiag.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    boolean existsById(String userId);
    // 해당 엔티티가 데이터베이스에 존재하는지 알아본다. 있으면 true, 없으면 false 반환.

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.user_phoneNumber = :userPhoneNumber")
    boolean existsByUserPhoneNumber(@Param("userPhoneNumber") String userPhoneNumber);

    Optional<UserEntity> findById(String userId);


}

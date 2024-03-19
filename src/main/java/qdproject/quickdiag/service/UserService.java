package qdproject.quickdiag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import qdproject.quickdiag.dto.UserDTO;
import qdproject.quickdiag.entity.UserEntity;
import qdproject.quickdiag.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;


    public void save(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
    }

    public String idCheck(String userId) {
        if (userRepository.existsById(userId)) {
            return "duplicate";
        } else {
            return "ok";
        }
        //Repository로 부터 받은 값에 따라 duplicate, ok를 반환한다.
    }

    public String phoneCheck(String userPhoneNumber) {
        if (userRepository.existsByUserPhoneNumber(userPhoneNumber)) {
            return "duplicate";
        } else {
            return "ok";
        }
    }




}

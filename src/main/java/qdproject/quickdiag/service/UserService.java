package qdproject.quickdiag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import qdproject.quickdiag.dto.UserDTO;
import qdproject.quickdiag.entity.UserEntity;
import qdproject.quickdiag.repository.UserRepository;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(UserDTO userDTO) {

        String hashedPassword = BCrypt.hashpw(userDTO.getUser_password(), BCrypt.gensalt());
        userDTO.setUser_password(hashedPassword);
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

    public UserDTO login(UserDTO userDTO) {

        //로그인 로직 구현
        Optional<UserEntity> byUserId = userRepository.findById(userDTO.getUser_id());
        if(byUserId.isPresent()) {
            UserEntity userEntity = byUserId.get();

            String hashedUserPassword = userEntity.getUser_password();
            // 데이터베이스에 저장되어 있는 해싱된 비밀번호

            String inputPassword = userDTO.getUser_password();
            // 입력된 비밀번호

            if (BCrypt.checkpw(inputPassword, hashedUserPassword)) {
                // 입력된 비밀번호와 해싱된 비밀번호 비교
                UserDTO dto = UserDTO.toUserDTO(userEntity);
                return dto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

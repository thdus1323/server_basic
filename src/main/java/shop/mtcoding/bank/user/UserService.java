package shop.mtcoding.bank.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
//서비스 책임
//1) 비지니스 로직처리
//2) 트랜잭션 처리
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO reqDTO){

        //step 2
        User user = userRepository.findByUsername(reqDTO.getUsername());
        if(user != null) throw new RuntimeException("유저네임 중복되었습니다. :" +reqDTO.getUsername());

        userRepository.save(reqDTO.getUsername(), reqDTO.getPassword(), reqDTO.getEmail(), reqDTO.getFullname());
    }

    public User 로그인(UserRequest.LoginDTO reqDTO){
        return userRepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword());

    }
}

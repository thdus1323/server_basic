package shop.mtcoding.bank.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private  UserRepository userRepository;

    @Test
    public void save_test(){
        //given
        String username = "ssar";
        String password = "1234";
        String email = "ssar@nate.com";
        String fullname = "홍길동";

        //when
        userRepository.save(username, password, email, fullname);
    }

}

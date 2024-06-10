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
    public void findByUsernameAndPassword_test() {
        String username = "ssar";
        String password = "1234";

        User user = userRepository.findByUsernameAndPassword(username, password);
        System.out.println(user.getUsername());
    }

    @Test
    //NOTE : user 클래스에 디폴트 생성자가 있어야 하이버네이트가 new를 해서 파싱해준다.
    public void findByUsernameAndPasswordV2_test() {
        String username = "ssar";
        String password = "1234";

        User user = userRepository.findByUsernameAndPassword(username, password);
        System.out.println(user.getUsername());
    }

    @Test
    public void findByUsernameAndPasswordV3_test() {
        String username = "ssar";
        String password = "1234";

        User user = userRepository.findByUsernameAndPassword(username, password);
        System.out.println(user.getUsername());
    }

    @Test
    public void save_test(){
        //given
        String username = "haha";
        String password = "1234";
        String email = "haha@nate.com";
        String fullname = "하하";

        //when
        userRepository.save(username, password, email, fullname);
    }

    @Test
    public void save_testV2(){
        //given
        String username = "haha";
        String password = "1234";
        String email = "haha@nate.com";
        String fullname = "하하";

        //when
        userRepository.save(username, password, email, fullname);
    }

}

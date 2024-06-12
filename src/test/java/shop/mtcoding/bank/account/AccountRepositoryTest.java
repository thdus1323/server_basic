package shop.mtcoding.bank.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.mtcoding.bank.user.User;

@Import(AccountRepository.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void save_test(){
        // given
        User sessionUser = new User();
        sessionUser.setId(1);
        sessionUser.setUsername("ssar");
        sessionUser.setPassword("1234");
        sessionUser.setEmail("ssar@nate.com");
        sessionUser.setFullname("쌀");

        Account account = new Account();
        account.setNumber("1111");
        account.setPassword("1234");
        account.setBalance(1000);
        account.setUser(sessionUser);

        // when
        accountRepository.save(account);

        // then
        System.out.println("account id : "+account.getId());
        System.out.println("account number : "+account.getNumber());
        System.out.println("account password : "+account.getPassword());
        System.out.println("account balance : "+account.getBalance());
        System.out.println("account userId : "+account.getUser().getId());
    }
}
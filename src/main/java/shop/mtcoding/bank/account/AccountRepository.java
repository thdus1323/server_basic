package shop.mtcoding.bank.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AccountRepository {
    private final EntityManager em;

    public void save(Account account){
        em.persist(account);
    }

    public void saveV2(Account account){
        Query query =
                em.createNativeQuery("insert into account_tb(number, password, balance, user_id) values(?,?,?,?)");
        query.setParameter(1, account.getNumber());
        query.setParameter(2, account.getPassword());
        query.setParameter(3, account.getBalance());
        query.setParameter(4, account.getUser().getId());

        query.executeUpdate();
    }
}
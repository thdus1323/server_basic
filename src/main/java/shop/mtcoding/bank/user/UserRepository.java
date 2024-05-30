package shop.mtcoding.bank.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository //IOC에 띄워줌
public class UserRepository {

    @Autowired // 의존성 주입, em, ac,uc,userrepository
    private EntityManager em; //DB에 접근

    //회원가입
    @Transactional // 끝났을 때 commit
    public void save(String username, String password, String email, String fullname){
            Query query = em.createNativeQuery("insert into user_tb(username, password, email, fullname) values (?,?,?,?)");
        query.setParameter(1, username);
        query.setParameter(2, password);
        query.setParameter(3, email);
        query.setParameter(4, fullname);

        query.executeUpdate();
    }





}

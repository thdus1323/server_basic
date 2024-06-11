package shop.mtcoding.bank.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor // final 변수를 초기화하는 생성자를 만들어준다.
@Repository //IOC에 띄워줌
public class UserRepository {


    @Autowired // 의존성 주입, em, ac,uc,userrepository, httpsession ioc에 등록되어 잇음.
    private EntityManager em; //DB에 접근

    //의존성 주입.
    public UserRepository(EntityManager em) {
        System.out.println("new UserRepository");
    }


    //회원가입
    public void save(String username, String password, String email, String fullname) {
        Query query = em.createNativeQuery("insert into user_tb(username, password, email, fullname) values (?,?,?,?)");
        query.setParameter(1, username);
        query.setParameter(2, password);
        query.setParameter(3, email);
        query.setParameter(4, fullname);

        query.executeUpdate(); // commit이 내장 // write(insert, delete, update)
    }


    public void saveV2(String username, String password, String email, String fullname) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFullname(fullname);

        em.persist(user);
        //담고 아직 순수 객체
    } // 메서드 종료될 대 트랜잭션 있으면 FLUSH 영속화가 됨.

    public User findByUsernameAndPassword(String username, String password) {
        Query query = em.createNativeQuery("select * from user_tb where username =? and password = ?");
        query.setParameter(1, username);
        query.setParameter(2, password);

        try {
            Object[] obs = (Object[]) query.getSingleResult();
            //[0] -> id, [1] -> un, [2] ->pw,  [3]email [4]->fn

            for (Object obj : obs) {
                System.out.println(obj);
            }

            User user = new User();
            user.setId((Integer) obs[0]);
            user.setPassword((String) obs[1]);
            user.setEmail((String) obs[2]);
            user.setFullname((String) obs[3]);
            user.setUsername((String) obs[4]);


            return user;

        } catch (Exception e) {
            return null;
        }
    }

    public User findByUsernameAndPasswordV2(String username, String password) {
        Query query = em.createNativeQuery("select * from user_tb where username =? and password = ?", User.class);
        query.setParameter(1, username);
        query.setParameter(2, password);

        try {
            User user = (User) query.getSingleResult();
            //[0] -> id, [1] -> un, [2] ->pw,  [3]email [4]->fn
            return user;

        } catch (Exception e) {
            return null;
        }
    }

    //객체지향쿼리(JPQL)_오타 날 일 x
    public User findByUsernameAndPasswordV3(String username, String password) {
        Query query =
                em.createQuery("select u.id,u.email from User u where u.username =: username and u.password=:password", User.class);
        //("select * from user_tb where username = ? and password = ?)
        query.setParameter("username", username);
        query.setParameter("password", password);

        try {
            User user = (User) query.getSingleResult();
            //[0] -> id, [1] -> un, [2] ->pw,  [3]email [4]->fn
            return user;

        } catch (Exception e) {
            return null;
        }
    }

    //객체지향쿼리(JPQL)_오타 날 일 x
    public User findByUsername(String username) {
        Query query =
                em.createQuery("select u from User u where u.username=:username", User.class);
        //("select * from user_tb where username = ? and password = ?)
        query.setParameter("username", username);

        try {
            User user = (User) query.getSingleResult();
            //[0] -> id, [1] -> un, [2] ->pw,  [3]email [4]->fn
            return user;

        } catch (Exception e) {
            return null;
        }
    }
}



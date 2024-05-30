package shop.mtcoding.bank.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 목적 : 클래스를 통해 테이블을 자동생성
 */


@Setter //SETTER 자동 생성
@Getter // GETTER 자동 생성
@Table(name = "user_tb")//테이블명 //애는 ioc랑 상관x
@Entity//스프링 시작될 때, 테이블을 만들어 //애는 ioc랑 상관x
public class User {
    @Id // pk 걸어주는 것
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT 적용
    private Integer id; //pk

    @Column(unique = true)
    private String username; // 아이디
    @Column(length = 12)
    private String password; // 비밀번호
    private String email; //이메일
    private String fullname; // 사람이름
}

package shop.mtcoding.bank.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;
import shop.mtcoding.bank.user.User;


@Setter
@Getter
@Table(name = "account_tb") // 안적으면 클래스명으로 감.
@Entity
public class Account {
    @Id // pk 걸어주는 것
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 4)
    private String number; //계좌번호
    @Column(length = 12)
    private String password; //계좌비밀번호

    //1000원 디폴트
    private Integer balance; //잔액(21억보다 많을 수 없다.)

    //fk , db에는 컬렉션, 옵므젝트 담을 수 x

    @ManyToOne
    private User user; //hibernate - orm 기술
}

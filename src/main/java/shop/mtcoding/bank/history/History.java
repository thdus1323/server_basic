package shop.mtcoding.bank.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.bank.account.Account;
import shop.mtcoding.bank.user.User;

import java.sql.Timestamp;

@Setter
@Getter
@Table(name = "history_tb") // 안적으면 클래스명으로 감.
@Entity
public class History {
    @Id // pk 걸어주는 것
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Account withdrawAccountNumber; //출금 계좌(보낸 계좌번호)
    @ManyToOne //FK
    private Account depositAccountNumber; // 입금 계좌(받은 계좌번호)

    private Integer amount; // 이체금액

    private Integer withdrawBalance; // 출금 계좌 잔액

    private Integer depositBalance; // 입금 계좌 잔액

    @CreationTimestamp // insert 할 때, 자동으로 현재시간 들어감.
    private Timestamp createdAt;
}
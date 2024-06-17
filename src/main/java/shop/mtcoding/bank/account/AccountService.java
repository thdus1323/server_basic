package shop.mtcoding.bank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.history.HistoryRepository;
import shop.mtcoding.bank.user.User;

import java.util.List;

//트랜잭션관리(commit, rollback), 비지니스로직 처리(잔액검증, 패스워드 검증)
//화면에 필요한 데이터만 담기
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;

    @Transactional
    public void 계좌이체(AccountRequest.TransferDTO reqDTO){
        //1.출금 계좌 존재 여부

        //2.입금 계좌 존재 여부

        //3. 출금 계좌 잔액 검증(DB 조회가 필요) - amount보다 더 금액이 있는지!. 같아도 상관x
        //0원 들어오면 어디서 막아야 해? 값 검사 > 유효성 검사 > controller에서 하면 됨.
        //-원 들어오면 어디서 막아야 해? 값 검사 > 유효성 검사(값 자체 검사) > controller에서 하면 됨.
        //비밀번호의 길이 검증 경우에도 , db 조회가 필요 없는 것은 서비스에 섞지말고 컨트롤러에서.!!!

        //4. 출금 패스워드 검증 - password

        //5. 출금 계좌 업데이트 --투두

        //6. 입금 계좌 업데이트 --투두

        //7. 히스토리 인서트 -- 아까 만들었음. em.persist

        //(5,6레파지토리 만드삼.)

    }

    public AccountResponse.ListDTO  나의계좌목록(Integer sessionUserId){
       List<Account> accountList = accountRepository.findAll(sessionUserId);
       return new AccountResponse.ListDTO(accountList);

    }

    public List<Account> 나의계좌목록V3(Integer sessionUserId){
        List<Account> accountList = accountRepository.findAll(sessionUserId);
        return accountList;
    }

    @Transactional
    public void 계좌생성(AccountRequest.SaveDTO reqDTO, User sessionUser){

        accountRepository.save(reqDTO.toEntity(sessionUser));
    }
}

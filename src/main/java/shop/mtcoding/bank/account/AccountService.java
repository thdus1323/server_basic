package shop.mtcoding.bank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.history.History;
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
        //1) 레파지토리에서 메서드 들고와서 있으면 계속 실행, 없으면 출력.
        Account wAccount = accountRepository.findByNumber(reqDTO.getWNumber());
        if(wAccount == null) throw new RuntimeException("출금계좌가 없어요"); // 롤백함.

        //2.입금 계좌 존재 여부
        Account dAccount = accountRepository.findByNumber(reqDTO.getDNumber());
        if(dAccount == null) throw new RuntimeException("입금계좌가 없어요"); // 롤백함.

        //3. 출금 계좌 잔액 검증(DB 조회가 필요) - amount보다 더 금액이 있는지!. 같아도 상관x
        //0원 들어오면 어디서 막아야 해? 값 검사 > 유효성 검사 > controller에서 하면 됨.
        //-원 들어오면 어디서 막아야 해? 값 검사 > 유효성 검사(값 자체 검사) > controller에서 하면 됨.
        //비밀번호의 길이 검증 경우에도 , db 조회가 필요 없는 것은 서비스에 섞지말고 컨트롤러에서.!!!
        if (wAccount.getBalance() < reqDTO.getAmount()) throw new RuntimeException("잔액이 부족해요 : 현재잔액 : "+wAccount.getBalance());

        //4. 출금 패스워드 검증 - password
        if(!wAccount.getPassword().equals(reqDTO.getPassword())) throw new RuntimeException("출금계좌의 패스워드 오류");

        //5. 출금 계좌 업데이트 --투두
        wAccount.setBalance(wAccount.getBalance()-reqDTO.getAmount()); // 객체의상태를 바꿈

        //6. 입금 계좌 업데이트 --투두
        dAccount.setBalance(dAccount.getBalance()+reqDTO.getAmount()); //객체의 상태를 바꿈
//        => 영속화,조회하고 pc에 들어감-v


        //7. 히스토리 인서트 -- 아까 만들었음. em.persist
        History history = new History();
        history.setWithdrawAccount(wAccount);
        history.setWithdrawAccount(dAccount);
        history.setWithdrawBalance(wAccount.getBalance());
        history.setDepositBalance(dAccount.getBalance());
        history.setAmount(reqDTO.getAmount());

        historyRepository.save(history);
        //(5,6레파지토리 만드삼.)
//id, 날짜빼고 다 넣음. insert 쿼리가 들어가면서 동기가 됨?
    } // 영속화 된 객체의 상태가 변경되면, update가 일어난다.

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

    public AccountResponse.DetailDTO 계좌상세보기(String number, Integer sessionUserId) {
        // 1. number로 계좌조회 하기 (User Join해서 가져와야함)
        Account account = accountRepository.findByNumberJoinUser(number);
        if(account == null) throw new RuntimeException("조회할 계좌가 없어요");

        // 2. 권한체크
        if(!account.getUser().getId().equals(sessionUserId)) throw new RuntimeException("해당 계좌를 조회할 권한이 없어요");

        // 3. number -> id로 계좌히스토리 조회하기
        List<History> historyList = historyRepository.findByWIdOrDIdJoinAccount(account.getId());

        // 4. Account객체, List<History> 객체 -> 합쳐서 리턴
        return new AccountResponse.DetailDTO(account, historyList);
    }
}

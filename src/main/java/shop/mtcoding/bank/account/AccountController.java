package shop.mtcoding.bank.account;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import shop.mtcoding.bank.user.User;

//스프링이 관리하는 객체가 됨.
@RequiredArgsConstructor
@Controller //컴퍼넌트 스캔(shop.mtcoding.bank 패키지 이하)
public class AccountController {

    private final HttpSession session;
    private final AccountService accountService;

    @PostMapping("account/save")
    public String accountSave(AccountRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다"); // 한 줄은 {} 필요 없음

        accountService.계좌생성(reqDTO, sessionUser);

        return "redirect:/account/list";
    }

    //계좌목록
    @GetMapping("/account/list")
    public String accountList(){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다"); // 한 줄은 {} 필요 없음

      return "account/list"; // templates/home.mustache 파일을 읽어서 응답
  }
    //계좌생성
    @GetMapping("/account/save-form")
    public String accountSaveForm(){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다"); // 한 줄은 {} 필요 없음

        return "/account/save-form";
    }

    //이체하기
    @GetMapping("/account/transfer-form")
    public String accountTransferForm(){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다"); // 한 줄은 {} 필요 없음

        return "/account/transfer-form";
    }

    //상세보기
    @GetMapping("/account/1111")
    public String detail(){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다"); // 한 줄은 {} 필요 없음

        return "account/detail";
    }
}

package shop.mtcoding.bank.account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.user.User;

import java.util.List;

//스프링이 관리하는 객체가 됨.
@RequiredArgsConstructor
@Controller //컴퍼넌트 스캔(shop.mtcoding.bank 패키지 이하)
public class AccountController {

    private final HttpSession session;
    private final AccountService accountService;

//    @GetMapping("/test/account") // uk or pk
//    public @ResponseBody AccountResponse.DetailDTO testDetail(){
//        AccountResponse.DetailDTO resDTO = accountService.계좌상세보기("1111", 1);
//        return resDTO;
//    }

    //상세보기
    @GetMapping("/account/{number}")
    public String detail(@PathVariable("number") String number, HttpServletRequest request){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다"); // 한 줄은 {} 필요 없음

        AccountResponse.DetailDTO resDTO = accountService.계좌상세보기(number,sessionUser.getId()); //권한(자기계좌여야한다.)도 체크해야 한다. 인증이 되었어도.
        request.setAttribute("model",resDTO);
        return "account/detail";
    }


    @PostMapping("/account/transfer")
    public String accountTransfer(AccountRequest.TransferDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다");

        //미션3) 0원 이하 검증 amount가 0원, 0보다 작으면 안돼! (유효성 검사)
        if(reqDTO.getAmount() <= 0) throw new RuntimeException("0원 이하는 이체할 수 없어요");
        //미션4) 비밀번호 길이 검증
        if(reqDTO.getPassword().length() != 4) throw new RuntimeException("비밀번호는 4자입니다.");

        accountService.계좌이체(reqDTO);


        return "redirect:/account/"+reqDTO.getWNumber();
    }

    @PostMapping("/account/save")
    public String accountSave(AccountRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다"); // 한 줄은 {} 필요 없음

        accountService.계좌생성(reqDTO, sessionUser);

        return "redirect:/account/list";
    }

    //계좌목록
    @GetMapping({"/", "/account/list"})
    public String accountList(HttpServletRequest request){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다"); // 한 줄은 {} 필요 없음

        AccountResponse.ListDTO resDTO = accountService.나의계좌목록(sessionUser.getId());
        request.setAttribute("model", resDTO);

      return "account/list"; // templates/home.mustache 파일을 읽어서 응답
  }

    @GetMapping("/account/list/v2")
    public @ResponseBody AccountResponse.ListDTO accountListV2(){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다"); // 한 줄은 {} 필요 없음

        AccountResponse.ListDTO resDTO = accountService.나의계좌목록(sessionUser.getId());

        return resDTO; // templates/home.mustache 파일을 읽어서 응답
    }

    @GetMapping("/account/list/v3")
    public @ResponseBody List<Account> accountListV3(){
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다.");

        List<Account> resDTO = accountService.나의계좌목록V3(sessionUser.getId());

        return resDTO;
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


}

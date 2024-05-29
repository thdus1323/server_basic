package shop.mtcoding.bank.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//스프링이 관리하는 객체가 됨.
@Controller //컴퍼넌트 스캔(shop.mtcoding.bank 패키지 이하)
public class AccountController {

    //@RequestMapping(method = RequestMethod.GET,value = "/home") // => @Getmapping("/home")랑 같은 말
    @GetMapping("/account/list")
    public String accountList(){
      System.out.println("home 호출됨");
      return "account/list"; // templates/home.mustache 파일을 읽어서 응답
  }
}

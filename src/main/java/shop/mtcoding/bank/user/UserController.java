package shop.mtcoding.bank.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class UserController {

//session은 해시 맵 형태.
    private final HttpSession session;
    //step3
    private final UserService userService;

    @GetMapping("/test1")
    public void test1(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/login-form");
          response.setHeader("Location", "http://localhost:8080/login-form");
          response.setStatus(302);
    }

//    @GetMapping("/hello")
//    public void test2(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/login-form");
//    }
//
//    @GetMapping("/hello1")
//    public void test3(HttpServletResponse response) throws IOException {
//        response.setHeader("Location", "http://localhost:8080/login-form");
//        response.setStatus(302);
//    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO reqDTO){
        User sessionUser = userService.로그인(reqDTO);
        if (sessionUser == null){
            throw new RuntimeException("아이디 혹은 패스워드가 틀렸습니다.");
        }else {
            //라카를 주는 것. 라카에 신발 저장.-v
            session.setAttribute("sessionUser", sessionUser);
            return "redirect:/account/list";
        }
    }

    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/login-form";

    }

//    @GetMapping("/hello")
//    public void test1(HttpServletResponse response) throws IOException {
//        response.setHeader("Location", "http://localhost:8080/login");
//        response.setStatus(302);
//    }
//
//    @PostMapping("/login") // 회원가입
//    public String join1(HttpServletRequest request, HttpServletResponse response){
//        return "redirect:/";
//    }


    //http3.0
    //http1.1 -우리가 사용중 인 것. post, get, //put, delete- 아작스 써야 함.
    //http 1.0 - post(insert, delete, update), get(select)
    //post(insert), get(select)
    @PostMapping("/join") // 회원가입
    //controller 책임3 :
    // 1) client의 요청주소 받기
    // 2) client의 바디 데이터 받기(post요청에만 있음 바디_)
    // 3) 적절한 pg 응답하기

    //x-www폼은 class로 받을 수 있다.
    public String join(UserRequest.JoinDTO reqDTO){

        //step
        userService.회원가입(reqDTO);

        return "redirect:/login-form";
    }

    @Autowired
    private UserRepository ur;

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }



}

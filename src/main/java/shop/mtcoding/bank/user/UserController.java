package shop.mtcoding.bank.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class UserController {

//session은 해시 맵 형태.
    private final HttpSession session;
    private final UserRepository userRepository;

    @GetMapping("/test")
    public void test(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/login-form");
        response.setHeader("Location", "http://localhost:8080/login-form");
        response.setStatus(302);
    }

    @GetMapping("/test1")
    public void test1(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login-form");
    }

    @GetMapping("/hello")
    public void test2(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login-form");
    }

    @GetMapping("/hello1")
    public void test3(HttpServletResponse response) throws IOException {
        response.setHeader("Location", "http://localhost:8080/login-form");
        response.setStatus(302);
    }

    @PostMapping("/login")
    public String login(String username, String password){
        User sessionUser = userRepository.findByUsernameAndPassword(username, password);
        if (sessionUser == null){
            throw new RuntimeException("아이디 혹은 패스워드가 틀렸습니다.");
        }else {
            //라카를 주는 것. 라카에 신발 저장.-v
            session.setAttribute("sessionUser", sessionUser);
            return "redirect:/account/list";
        }
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
    public String join(String username, String password,String email,String fullname){
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("email: " + email);
        System.out.println("fullname: " + fullname);

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

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/account/list"; //페이지를 찾는게 아니라,api 주소 찾기
    }

}

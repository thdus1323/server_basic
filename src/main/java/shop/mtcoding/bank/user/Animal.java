package shop.mtcoding.bank.user;

import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class Animal {

    public Animal(){

        System.out.println("컴퍼넌트로 동물 new 되지롱");
    }
}

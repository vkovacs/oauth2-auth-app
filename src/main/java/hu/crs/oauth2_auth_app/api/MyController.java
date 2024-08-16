package hu.crs.oauth2_auth_app.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/oauth2.html
@RestController
public class MyController {

    @GetMapping("/public")
    public String myPublicEndpoint() {
        return "Hello World!";
    }

    @GetMapping("/protected")
    public String myProtectedEndpoint() {
        return "Protected Hello World!";
    }
}
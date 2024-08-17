package hu.crs.oauth2_auth_app.api;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "ouath.decoder.publickey.pem=-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOt7AU3d56tmXAIDrnip9g5tPsjDeZo++HIGJ1vls6VvY/m5dSQy1obrVHqY1Kaa+/jB2ssJquEva5CR26J0qsJGgawZr/fku1+Tcbn+VRemDX0HKYmauNGnUv37Mxt4p1pmxqqgCFRTtw2JbJXqZPlEiSoZLPhqhOELOk3dZzHQIDAQAB-----END PUBLIC KEY-----")
@AutoConfigureMockMvc
class MyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void accessPublic() {
        //given
        //when - then
        mockMvc.perform(get("/public"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));

    }

    @Test
    @SneakyThrows
    void cannotAccessProtected() {
        //given
        //when - then
        mockMvc.perform(get("/protected"))
                .andExpect(status().isUnauthorized());
    }

    //https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/oauth2.html
    @Test
    @SneakyThrows
    void loggedInUserAccessProtected() {
        //given
        //when - then
        mockMvc.perform(get("/protected")
                        .with(oauth2Login()))
                .andExpect(status().isOk())
                .andExpect(content().string("Protected Hello World!"));

    }
}
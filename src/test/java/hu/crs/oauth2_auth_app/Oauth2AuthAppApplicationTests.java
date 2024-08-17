package hu.crs.oauth2_auth_app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = """
	ouath.decoder.publickey.pem=-----BEGIN PUBLIC KEY-----
	MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOt7AU3d56tmXAIDrnip9g5tPs
	jDeZo++HIGJ1vls6VvY/m5dSQy1obrVHqY1Kaa+/jB2ssJquEva5CR26J0qsJGga
	wZr/fku1+Tcbn+VRemDX0HKYmauNGnUv37Mxt4p1pmxqqgCFRTtw2JbJXqZPlEiS
	oZLPhqhOELOk3dZzHQIDAQAB
	-----END PUBLIC KEY-----""")
class Oauth2AuthAppApplicationTests {

	@Test
	void contextLoads() {
	}

}

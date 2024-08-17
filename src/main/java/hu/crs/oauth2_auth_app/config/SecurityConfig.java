package hu.crs.oauth2_auth_app.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class SecurityConfig {
    @Value("${ouath.decoder.publickey.pem}")
    private String publicKeyPem;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/public").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    @SneakyThrows
    public JwtDecoder jwtDecoder() {
        var publicKey = getPublicKeyFromString(publicKeyPem);
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    public static RSAPublicKey getPublicKeyFromString(String key) throws Exception {
        // Remove the first and last lines if the key includes the "-----BEGIN PUBLIC KEY-----"
        // and "-----END PUBLIC KEY-----" parts.
        String strippedPublicKeyPEM = key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", ""); // Remove any extra whitespace/newlines

        // Decode the Base64 encoded string to get the key bytes
        byte[] encoded = Base64.getDecoder().decode(strippedPublicKeyPEM);

        // Create a key specification from the key bytes
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);

        // Create a KeyFactory for the RSA algorithm
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // Generate the PublicKey (which is actually an RSAPublicKey)
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // Ensure it's an RSAPublicKey instance (usually is)
        if (publicKey instanceof RSAPublicKey) {
            return (RSAPublicKey) publicKey;
        } else {
            throw new IllegalArgumentException("The provided key is not an RSAPublicKey.");
        }
    }
}

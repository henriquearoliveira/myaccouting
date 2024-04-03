package br.com.contability.comum;

import br.com.contability.utilitario.CaixaDeFerramentas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaPasswordEncoder implements PasswordEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShaPasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        return getSha512SecurePassword(rawPassword.toString());
    }

    /**
     * @param senha
     * @return ShaPasswordEncoder String
     */
    public static String getSha512Securit(String senha) {
        return new ShaPasswordEncoder().encode(senha);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        final String passwordEncoded = getSha512SecurePassword(rawPassword.toString());
        return passwordEncoded.equals(encodedPassword);
    }

    private String getSha512SecurePassword(String rawPassword) {
        String generatedPassword = "";
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(rawPassword.getBytes());
            final StringBuilder sb = new StringBuilder();

            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Erro no get Sha512", e);
        }
        return generatedPassword;
    }

}

package Security;

import java.security.SecureRandom;

public class ResetCode {
    private static final int TOKEN_LENGTH = 8;
    private static final String CHARACTERS = "lR7a1qEZhKHvCu0sjY6fBTDSrP8MiUJGp9wktV2c3AgNodIL4eOmXbyQxnzF5W";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateCode() {
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int index = secureRandom.nextInt(CHARACTERS.length());
            token.append(CHARACTERS.charAt(index));
        }
        return token.toString();
    }
}

package Security;

import java.util.UUID;

public class ImgNameGenerator {

    public static String generate() {
        // Generate random UUID
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "")+".png"; 
    }
}


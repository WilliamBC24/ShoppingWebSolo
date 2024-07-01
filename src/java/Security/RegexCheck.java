package Security;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexCheck {

    public static boolean match(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }
}

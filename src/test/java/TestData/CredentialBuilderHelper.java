package TestData;

public class CredentialBuilderHelper {
    // Swaps uppercase and lowercase letters while leaving numbers and other characters unchanged.
    public static String swapLetterCase(String value) {
        StringBuilder result = new StringBuilder();

        for (char character : value.toCharArray()) {
            if (Character.isLowerCase(character)) {
                result.append(Character.toUpperCase(character));
            } else if (Character.isUpperCase(character)) {
                result.append(Character.toLowerCase(character));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }
}

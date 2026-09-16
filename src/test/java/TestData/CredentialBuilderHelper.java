package TestData;

public class CredentialBuilderHelper {

    public static String swapLetterCase(String value){

        //Chancing the letters in the valid password from upper to lower and from lower to upper case
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

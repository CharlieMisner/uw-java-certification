package cp120.assignments.assignment005;


public class Crypto {

    /**
     * Encrypts a string following the provided algorithm.
     * @param str
     * @return str
     */
    public static String encrypt(String str) {
        str = pad(str);
        str = rotate(str);
        str = increment(str);
        return str;
    }

    /**
     * Decrypts a string following the provided algorithm.
     * @param str
     * @return str
     */
    public static String decrypt(String str) {
        str = decrement(str);
        str = unrotate(str);
        str = unpad(str);
        return str;
    }

    /**
     * Adds white spaces to the end of string until the length of the string is divisible by 3.
     * @param str
     * @return str
     */
    private static String pad(String str) {
        str += "\u0000";
        while (str.length() % 3 != 0){
            str += "\u0000";
        }
        return str;
    }

    /**
     * Removes white spaces from end of string.
     * @param str
     * @return str
     */
    private static String unpad(String str) {
        return str.trim();
    }

    /**
     * Rotates the elements in a string forward 1 space.
     * @param str
     * @return str
     */
    private static String rotate(String str) {
        // Assemble string into rotated array of characters, grouped by 3.
        String[] rotatedStringArray = new String[str.length()/3];
        for(int i = 0; i < rotatedStringArray.length ; i++){
            rotatedStringArray[(i + 1) % rotatedStringArray.length] = str.substring(i*3, i*3+3);
        }

        // Assemble rotatedStringArray back into a single string.
        str = arrayElementsToString(rotatedStringArray);

        return str;
    }

    /**
     * Rotates the elements of a string forward the entire length of the array - 1 (original position).
     * @param str
     * @return str
     */
    private static String unrotate(String str) {
        // Assemble string into array of characters grouped by 3, rotate through to original position.
        String[] unrotatedStringArray = new String[str.length()/3];
        for(int i = 0; i < unrotatedStringArray.length ; i++){
            unrotatedStringArray[(i + (unrotatedStringArray.length - 1)) % unrotatedStringArray.length] = str.substring(i*3, i*3+3);
        }

        // Assemble un-rotatedStringArray back into a single string.
        str = arrayElementsToString(unrotatedStringArray);

        return str;
    }

    /**
     * Increments each character in a string by its position within the string.
     * @param str
     * @return str
     */
    private static String increment(String str) {
        char[] charArray = str.toCharArray();
        char[] incrementedCharArray = new char[charArray.length];
        for (int i = 0; i < charArray.length; i++){
            int incrementedInteger = (int)charArray[i] + i;
            incrementedCharArray[i] = (char)incrementedInteger;
        }
        str = arrayElementsToString(incrementedCharArray);
        return str;
    }

    /**
     * Decrements each character in a string by its position within the string.
     * @param str
     * @return str
     */
    private static String decrement(String str) {
        char[] charArray = str.toCharArray();
        char[] decrementedCharacterArray = new char[charArray.length];
        for (int i = 0; i < charArray.length; i++){
            int incrementedInteger = (int)charArray[i] - i;
            decrementedCharacterArray[i] = (char)incrementedInteger;
        }
        str = arrayElementsToString(decrementedCharacterArray);

        return str;
    }

    /**
     * Assembles array of strings into a single string excluding commas, brackets and whitespaces.
     * @param arr
     * @return str
     */
    private static String arrayElementsToString(String[] arr) {
        StringBuilder builder = new StringBuilder();
        for (String element: arr) {
            builder.append(element);
        }
        String str = builder.toString();

        return str;
    }

    /**
     * Assembles array of chars into a single string excluding commas, brackets and whitespaces.
     * @param arr
     * @return str
     */
    private static String arrayElementsToString(char[] arr) {
        StringBuilder builder = new StringBuilder();
        for (char element: arr) {
            builder.append(element);
        }
        String str = builder.toString();

        return str;
    }

}

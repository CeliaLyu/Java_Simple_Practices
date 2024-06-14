/**
 * Main idea:
 * - Define a method to sum two large numbers represented as strings.
 * - Implement logic to handle addition digit by digit from the end of the strings.
 * - Handle carryover for digits summing to more than 9.
 * - Use StringBuilder to build the resulting sum string.
 */

public class Main {

    // Method to add two large numbers represented as strings
    public static String addLargeNumbers(String str1, String str2) {
        // Ensure str1 is the longer string
        if (str1.length() < str2.length()) {
            String temp = str1;
            str1 = str2;
            str2 = temp;
        }

        // Initialize variables
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int str1Length = str1.length();
        int str2Length = str2.length();
        int diff = str1Length - str2Length;

        // Add digits from the end of both strings
        for (int i = str2Length - 1; i >= 0; i--) {
            int sum = (str1.charAt(i + diff) - '0') + (str2.charAt(i) - '0') + carry;
            result.append(sum % 10);
            carry = sum / 10;
        }

        // Add remaining digits of str1
        for (int i = diff - 1; i >= 0; i--) {
            int sum = (str1.charAt(i) - '0') + carry;
            result.append(sum % 10);
            carry = sum / 10;
        }

        // Add remaining carry
        if (carry > 0) {
            result.append(carry);
        }

        // Reverse and return the result
        return result.reverse().toString();
    }

    // Main method to test the addLargeNumbers function
    public static void main(String[] args) {
        // Test cases
        String str1 = "3333311111111111";
        String str2 = "44422222221111";
        System.out.println("Sum: " + addLargeNumbers(str1, str2)); // Output: 3377733333332222

        str1 = "7777555511111111";
        str2 = "3332222221111";
        System.out.println("Sum: " + addLargeNumbers(str1, str2)); // Output: 7780887733332222

        // Edge cases
        str1 = "1";
        str2 = "9999";
        System.out.println("Sum: " + addLargeNumbers(str1, str2)); // Output: 10000

        str1 = "0";
        str2 = "0";
        System.out.println("Sum: " + addLargeNumbers(str1, str2)); // Output: 0

        str1 = "12345678901234567890";
        str2 = "98765432109876543210";
        System.out.println("Sum: " + addLargeNumbers(str1, str2)); // Output: 111111111011111111100
    }

    /**
     * Time Complexity: O(max(n, m))
     * - where n and m are the lengths of str1 and str2 respectively.
     * - We traverse both strings once, hence the linear complexity.
     *
     * Space Complexity: O(max(n, m))
     * - The space complexity is primarily due to the StringBuilder used to store the result.
     * - In the worst case, it will store a result of length max(n, m) + 1 (due to possible carry).
     */
}

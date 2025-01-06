package bullscows;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Secret code length
        System.out.println("Input the length of the secret code:");
        String lengthInput = scanner.nextLine();

        // Validate secret code length
        if (!isValidNumber(lengthInput)) {
            System.out.println("Error: \"" + lengthInput + "\" isn't a valid number.");
            return;
        }

        int length = Integer.parseInt(lengthInput);
        if (length <= 0) {
            System.out.println("Error: Length must be a positive number.");
            return;
        }

        // Input: Number of possible symbols
        System.out.println("Input the number of possible symbols in the code:");
        String rangeInput = scanner.nextLine();

        // Validate range of symbols
        if (!isValidNumber(rangeInput)) {
            System.out.println("Error: \"" + rangeInput + "\" isn't a valid number.");
            return;
        }

        int range = Integer.parseInt(rangeInput);
        if (range < length) {
            System.out.println("Error: it's not possible to generate a code with a length of " +
                    length + " with " + range + " unique symbols.");
            return;
        }

        if (range > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        // Generate secret code
        String secretCode = generateSecretCode(length, range);
        System.out.println("The secret is prepared: " + "*".repeat(length) +
                " (0-" + (range <= 10 ? range - 1 : "9, a-" + (char) ('a' + range - 11)) + ").");

        // Start the game
        System.out.println("Okay, let's start a game!");
        playGame(secretCode, scanner);
    }

    public static boolean isValidNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String generateSecretCode(int length, int range) {
        String symbols = "0123456789abcdefghijklmnopqrstuvwxyz";
        List<Character> availableSymbols = new ArrayList<>();

        for (int i = 0; i < range; i++) {
            availableSymbols.add(symbols.charAt(i));
        }
        Collections.shuffle(availableSymbols);

        StringBuilder secretCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            secretCode.append(availableSymbols.get(i));
        }
        return secretCode.toString();
    }

    public static void playGame(String secretCode, Scanner scanner) {
        int turn = 1;

        while (true) {
            System.out.println("Turn " + turn + ":");
            String guess = scanner.next();

            if (!isValidGuess(guess, secretCode.length())) {
                System.out.println("Error: Your guess must have exactly " + secretCode.length() + " characters.");
                continue;
            }

            int bulls = 0;
            int cows = 0;

            for (int i = 0; i < guess.length(); i++) {
                char guessChar = guess.charAt(i);

                if (secretCode.indexOf(guessChar) != -1) {
                    if (secretCode.charAt(i) == guessChar) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }

            System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s)");

            if (bulls == secretCode.length()) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }

            turn++;
        }
    }

    public static boolean isValidGuess(String guess, int length) {
        if (guess.length() != length) {
            return false;
        }
        String symbols = "0123456789abcdefghijklmnopqrstuvwxyz";
        for (char c : guess.toCharArray()) {
            if (!symbols.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }
}

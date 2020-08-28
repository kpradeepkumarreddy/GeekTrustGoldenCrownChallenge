import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Geektrust {
	private static final HashMap<String, String> KINGDOM_VS_EMBLEM = new HashMap<>();
    private static final int ALPHABETS_COUNT = 26, MIN_ALLIES_SUPPORT = 3;

    static {
        // initializing kingdoms versus emblems in a hashmap
        KINGDOM_VS_EMBLEM.put("LAND", "PANDA");
        KINGDOM_VS_EMBLEM.put("WATER", "OCTOPUS");
        KINGDOM_VS_EMBLEM.put("ICE", "MAMMOTH");
        KINGDOM_VS_EMBLEM.put("AIR", "OWL");
        KINGDOM_VS_EMBLEM.put("FIRE", "DRAGON");
    }

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("No input file");
                System.out.println("Usage: java Geektrust 'input.txt'");
                return;
            }

            // read the input from a text file
            File file = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String inputLineStr;
            List<String> allies = null;
            while ((inputLineStr = br.readLine()) != null) {

                // split the input line into 2 parts
                // first part is kingdom name and second part is secret message
                String[] inputLineStrArr = inputLineStr.split(" ");

                // get the cipher key for the input kingdom
                int cipherKey = KINGDOM_VS_EMBLEM.get(inputLineStrArr[0]).length();

                // Decrypt the input message using the cipher key
                StringBuilder decryptedMessage = new StringBuilder();
                String secretMessage = inputLineStrArr[1];
                for (int i = 0; i < secretMessage.length(); i++) {
                    // checking for valid characters
                    if (secretMessage.charAt(i) >= 'A' && secretMessage.charAt(i) <= 'Z') {
                        char c = (char) ((int) secretMessage.charAt(i) - cipherKey);
                        if (c < 'A') {
                            c = (char) ((int) c + ALPHABETS_COUNT);
                        } else if (c > 'Z') {
                            c = (char) ((int) c - ALPHABETS_COUNT);
                        }
                        decryptedMessage.append(c);
                    } else {
                        System.out.println("Invalid character in secret message");
                    }
                }

                // If the decrypted message has all the character of emblem, then print
                // the name of the kingdom to the console
                String emblem = KINGDOM_VS_EMBLEM.get(inputLineStrArr[0]);
                boolean isWonKingdom = true;
                for (int i = 0; i < emblem.length(); i++) {
                    int charIndex = decryptedMessage.indexOf(String.valueOf(emblem.charAt(i)));
                    // if the characters in emblem are not present in decrypted message then break the loop
                    if (charIndex != -1) {
                        decryptedMessage = decryptedMessage.deleteCharAt(charIndex);
                    } else {
                        isWonKingdom = false;
                        break;
                    }
                }
                if (isWonKingdom) {
                    if (allies == null) {
                        allies = new ArrayList<>();
                    }
                    allies.add(inputLineStrArr[0]);
                }
            }

            // SPACE kingdom did not get enough allies support
            if (allies.size() < MIN_ALLIES_SUPPORT) {
                System.out.print("NONE");
            } else {
                System.out.print("SPACE ");
                for (String ally : allies) {
                    System.out.print(ally + " ");
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception = " + ex);

        }
    }
}
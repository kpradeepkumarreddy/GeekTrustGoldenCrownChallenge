import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Geektrust {
private static final HashMap<String, String> kingdomVsEmblem = new HashMap<>();

    static {
        // initializing kingdoms versus emblems in a hashmap
        kingdomVsEmblem.put("LAND", "PANDA");
        kingdomVsEmblem.put("WATER", "OCTOPUS");
        kingdomVsEmblem.put("ICE", "MAMMOTH");
        kingdomVsEmblem.put("AIR", "OWL");
        kingdomVsEmblem.put("FIRE", "DRAGON");
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
            int alliesIndex = 0;
            String[] allies = new String[3];
            while ((inputLineStr = br.readLine()) != null) {
                System.out.println(inputLineStr);

                // split the input line into 2 parts
                // first part is kingdom name and second part is secret message
                String[] inputLineStrArr = inputLineStr.split(" ");
                
                // get the cipher key for the input kingdom
                int cipherKey = kingdomVsEmblem.get(inputLineStrArr[0]).length();
               
                // Decrypt the input message using the cipher key
                StringBuilder decryptedMessage = new StringBuilder();
                String secretMessage = inputLineStrArr[1];
                for (int i = 0; i < secretMessage.length(); i++) {
                    // checking for valid characters
                    if (secretMessage.charAt(i) >= 'A' && secretMessage.charAt(i) <= 'Z') {
                        char c = (char) ((int) secretMessage.charAt(i) - cipherKey);
                        if (c < 'A') {
                            c = (char) ((int) c + 26);
                        } else if (c > 'Z') {
                            c = (char) ((int) c - 26);
                        }
						decryptedMessage.append(c);
                    } else {
                        System.out.println("Invalid character in secret message");
                    }
                }
               
                // If the decrypted message has all the character of emblem, then print
                // the name of the kingdom to the console
                String emblem = kingdomVsEmblem.get(inputLineStrArr[0]);
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
                    allies[alliesIndex++] = inputLineStrArr[0];
                    if (alliesIndex == 3) {
                        System.out.print("SPACE ");
                        for (String ally : allies) {
                            System.out.print(ally + " ");
                        }
                        break;
                    }
                }
            }
            // SPACE kingdom did not get enough allies
            if (alliesIndex < 3) {
                System.out.print("NONE");
            }
        } catch (Exception ex) {
            System.out.println("Exception = " + ex);

        }
    }
}
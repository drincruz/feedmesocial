package com.drincruz.service.feedmesocial.util;

/**
 * Text utility class for text manipulation methods
 *
 * @since 2.3.1
 */
public class TextUtil {
    /**
     * Shorten text to a character limit
     *
     * @param String input
     * @return String
     */
    public static String shorten(String input) {
        if (140 > input.length()) {
            return input;
        }
        int charIndex;
        StringBuffer shortenedInput = new StringBuffer();
        for (charIndex = 0; charIndex < input.length(); charIndex++) {
            shortenedInput.append(input.charAt(charIndex));
            if (139 == charIndex) {
                break;
            }
        }

        while (charIndex < input.length()) {
            if (' ' == input.charAt(charIndex)) {
                shortenedInput.append("...");
                break;
            }
            if ( ('.' == input.charAt(charIndex)) 
                && ((' ' == input.charAt(charIndex+1)) || (charIndex+1 == input.length()))
            ) {
                shortenedInput.append(input.charAt(charIndex));
                break;
            }
            else if ( ('?' == input.charAt(charIndex)) 
                && ((' ' == input.charAt(charIndex+1)) || (charIndex+1 == input.length()))
            ) {
                shortenedInput.append(input.charAt(charIndex));
                break;
            }
            else if ( ('!' == input.charAt(charIndex)) 
                && ((' ' == input.charAt(charIndex+1)) || (charIndex+1 == input.length()))
            ) {
                shortenedInput.append(input.charAt(charIndex));
                break;
            }
            charIndex++;
        }

        return shortenedInput.toString();
    }
}

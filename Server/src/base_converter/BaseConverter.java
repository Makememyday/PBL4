package base_converter;

import java.math.BigInteger;

public class BaseConverter {

    public static String convertBase(String number, int fromBase, int toBase) {
        // Validate input bases
        if (fromBase < 2 || fromBase > 36 || toBase < 2 || toBase > 36) {
            throw new IllegalArgumentException("Base must be between 2 and 36 (inclusive).");
        }

        // Convert the number to decimal first
        BigInteger decimalValue = new BigInteger(number, fromBase);

        // Convert the decimal value to the target base
        return decimalValue.toString(toBase);
    }
}


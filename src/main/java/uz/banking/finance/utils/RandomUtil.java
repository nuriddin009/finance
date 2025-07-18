package uz.banking.finance.utils;


import org.apache.commons.lang3.RandomStringUtils;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;
    private static final int BARCODE_COUNT = 12;

    private RandomUtil() {
    }

    /**
     * Generates a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generates an activation key.
     *
     * @return the generated activation key
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generates an sms activation code.
     *
     * @return the generated activation code
     */
    public static String generateActivationCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    /**
     * Generates a reset key.
     *
     * @return the generated reset key
     */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generates a order public id.
     *
     * @return the generated paublic_id
     */
    public static String generatePublicId() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}

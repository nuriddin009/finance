package uz.aloqabank.template.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import uz.aloqabank.template.constants.Constants;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class RequestIdUtils implements Constants {
    private static final Random random = new Random();
    private static final String letters = "1234567890QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";

    public static String getRequestId() {
        return MDC.get(INTERNAL_REQUEST_ID);
    }

    public static void nextRequestId(String prefix, String requestId) {
        prefix = StringUtils.isNotBlank(prefix) ? prefix : "";

        if (StringUtils.isEmpty(requestId)) {
            requestId = "N/" + UUID.randomUUID() + "/" + prefix;
        } else {
            requestId += "/" + prefix + randomString(letters, 3);
        }
        if (requestId.length() > 36 + 48 + 1) {
            String uuid = requestId.substring( 0, 36 );
            String appends = requestId.substring( 36 );

            appends = "~" + appends.substring( appends.length() - 48 );
            requestId = uuid + appends;
        }
        MDC.put(INTERNAL_REQUEST_ID, requestId);
    }

    public static String randomString(String source, int length) {
        char[] chars = source.toCharArray();
        StringBuilder sb = new StringBuilder(length);
        IntStream.range(0, length).forEach(index -> sb.append(chars[random.nextInt(chars.length)]));
        return sb.toString();
    }
}

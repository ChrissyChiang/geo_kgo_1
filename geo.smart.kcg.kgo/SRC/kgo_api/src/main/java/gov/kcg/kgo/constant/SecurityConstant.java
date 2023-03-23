package gov.kcg.kgo.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityConstant {

    public static final String ROLE_BASE = "ROLE_";

    public static final String BACKEND = "BACKEND";
    
    public static final String FRONTEND = "FRONTEND";

    private static final String HAS_IP_ADDRESS_PREFIX = "hasIpAddress('";

    private static final String HAS_IP_ADDRESS_SUFFIX = "')";

    public static final String BACKEND_ROLE = ROLE_BASE + BACKEND;
    
    public static final String FRONTEND_ROLE = ROLE_BASE + FRONTEND;

    private static final List<String> COMMON_WHITE_LIST = new ArrayList<>();

    public static final String KGO_GOV_WHITE_LIST_JOIN_STRING;

    static {
        // for test
        formatIPAddress("127.0.0.1");
        formatIPAddress("0:0:0:0:0:0:0:1");
        formatIPAddress("163.29.241.10");
        formatIPAddress("163.29.241.120");
        formatIPAddress("163.29.241.66");
        formatIPAddress("223.200.91.112");
        formatIPAddress("223.200.91.113");
        formatIPAddress("223.200.91.114");
        formatIPAddress("223.200.91.115");
        formatIPAddress("128.1.2.2");
        formatIPAddress("128.1.2.3");
        formatIPAddress("128.1.2.42");
        formatIPAddress("128.1.2.78");
        formatIPAddress("10.100.91.112");
        formatIPAddress("10.100.91.113");
        formatIPAddress("10.100.91.114");
        formatIPAddress("10.100.91.115");
        

        KGO_GOV_WHITE_LIST_JOIN_STRING = COMMON_WHITE_LIST.stream().collect(Collectors.joining(" or "));
    }

    private static void formatIPAddress(String s) {
        COMMON_WHITE_LIST.add(HAS_IP_ADDRESS_PREFIX + s + HAS_IP_ADDRESS_SUFFIX);
    }
}

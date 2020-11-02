package com.codergeezer.auth.constant;

/**
 * @author haidv
 * @version 1.0
 */
public class Constant {

    public static final String DESTINATION_SAVE_ACCOUNT = "SAVE_ACCOUNT";

    public static final String MESSAGE_EMPLOYEE_ACCOUNT_PREFIX = "EMPLOYEE_";

    public static final String MESSAGE_CUSTOMER_ACCOUNT_PREFIX = "CUSTOMER_";

    public static final String MESSAGE_CREATE_ACCOUNT_PREFIX = "CREATED_";

    public static final String MESSAGE_UPDATE_ACCOUNT_PREFIX = "UPDATED_";

    public static final String MESSAGE_DELETE_ACCOUNT_PREFIX = "DELETED_";

    public final static String OAUTH2_DEFAULT_SCOPE = "default";

    private Constant() {
        throw new IllegalStateException("Utility class");
    }
}

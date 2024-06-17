package com.vvh.coresv.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateAttribute {
    public static final String EMAIL_REGEX =
            "^[0-9a-zA-Z]+\\w+@\\w+(\\.\\w+)*(\\.[a-zA-Z]{2,6})$";
    public static final Pattern VALID_EMAIL_ADDRESS =
            Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    public static final String PASSWORD_REGEX = ".{8,100}";
    public static final Pattern VALID_PASSWORD =
            Pattern.compile(PASSWORD_REGEX, Pattern.CASE_INSENSITIVE);
}

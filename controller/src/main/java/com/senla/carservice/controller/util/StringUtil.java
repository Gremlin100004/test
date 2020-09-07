package com.senla.carservice.controller.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StringUtil {

    private static final String REPEAT_SYMBOL = " ";
    private static final Logger LOGGER = LogManager.getLogger(StringUtil.class);

    public static String fillStringSpace(String value, int lengthString) {
        LOGGER.debug("Method fillStringSpace");
        LOGGER.trace("Parameter value: " + value);
        LOGGER.trace("Parameter lengthString: " + lengthString);
        StringBuilder stringBuilder = new StringBuilder(value);
        if (value.length() < lengthString) {
            stringBuilder.append(REPEAT_SYMBOL.repeat(lengthString - value.length()));
        }
        return stringBuilder.toString();
    }
}
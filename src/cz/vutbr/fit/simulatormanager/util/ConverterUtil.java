package cz.vutbr.fit.simulatormanager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConverterUtil {

    final static Logger LOG = LoggerFactory.getLogger(ConverterUtil.class);

    public static Float stringToFloat(String strVal) {
	if (strVal != null) {
	    // remove spaces (including non breaking)
	    strVal = strVal.replaceAll("\\s+", "");
	    strVal = strVal.replace("\u00A0", "");
	    return Float.parseFloat(strVal);
	}
	return null;
    }

    public static boolean stringToBoolean(String value) {
	char firstChar = value.charAt(0);
	return (firstChar == '1');
    }

    public static Double stringToDouble(String strVal) {
	if (strVal != null) {
	    return Double.parseDouble(strVal);
	}
	return null;
    }

    public static Integer stringToInt(String strVal) {
	if (strVal != null) {
	    return Integer.parseInt(strVal);
	}
	return null;
    }

}

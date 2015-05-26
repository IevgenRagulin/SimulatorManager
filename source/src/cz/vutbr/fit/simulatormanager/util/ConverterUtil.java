package cz.vutbr.fit.simulatormanager.util;

import java.text.NumberFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.util.converter.StringToFloatConverter;

/**
 * Class which helps convert data between different types
 * 
 * @author zhenia
 *
 */
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

    public static StringToFloatConverter getStringToFloatConverter() {
	StringToFloatConverter plainFloatConverter = new StringToFloatConverter() {
	    private static final long serialVersionUID = -7227277283837048291L;

	    protected java.text.NumberFormat getFormat(Locale locale) {
		NumberFormat format = super.getFormat(locale);
		format.setGroupingUsed(false);
		return format;
	    };
	};
	return plainFloatConverter;
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
	    // remove spaces (including non breaking)
	    strVal = strVal.replaceAll("\\s+", "");
	    // remove all dots (Androids reprecents 1000 as 1.000
	    strVal = strVal.replaceAll("\\.", "");
	    strVal = strVal.replace("\u00A0", "");
	    return Integer.parseInt(strVal);
	}
	return null;
    }

    public static float doubleToFloat(Double value) {
	if (value != null) {
	    return value.floatValue();
	}
	return 0;
    }
}

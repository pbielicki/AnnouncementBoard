package com.bielu.struts2.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class EmptyToNullStringConverter extends StrutsTypeConverter {

	@Override
	@SuppressWarnings("unchecked")
	public Object convertFromString(Map context, String[] values, Class toClass) {
		return values[0];
	}

	@Override
	@SuppressWarnings("unchecked")
	public String convertToString(Map context, Object o) {
		if (o == null) {
			return null;
		}
		
		if (o instanceof String[]) {
			String[] s = (String[]) o;
			if (s.length > 0) {
				if (s[0].trim().length() == 0) {
					return null;
				}
				return s[0];
			}
			return null;

		} else if (o instanceof String) {
			if (o.toString().trim().length() == 0) {
				return null;
			}
			return o.toString();
		}
		return "";
	}
}

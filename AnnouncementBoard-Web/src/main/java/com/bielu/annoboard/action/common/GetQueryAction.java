package com.bielu.annoboard.action.common;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public abstract class GetQueryAction extends ActionSupport {

	private static final long serialVersionUID = -5088259174620913142L;

	private static final Map<Integer, String> ISO_8859_2_CHAR_MAP = new HashMap<Integer, String>();
	static {
		final int A = 165;
		final int a = 185;
		ISO_8859_2_CHAR_MAP.put(A, "Ą");
		ISO_8859_2_CHAR_MAP.put(a, "ą");

		final int C = 198;
		final int c = 230;
		ISO_8859_2_CHAR_MAP.put(C, "Ć");
		ISO_8859_2_CHAR_MAP.put(c, "ć");
		
		final int E = 202;
		final int e = 234;
		ISO_8859_2_CHAR_MAP.put(E, "Ę");
		ISO_8859_2_CHAR_MAP.put(e, "ę");
		
		final int L = 163;
		final int l = 179;
		ISO_8859_2_CHAR_MAP.put(L, "Ł");
		ISO_8859_2_CHAR_MAP.put(l, "ł");
		
		final int N = 209;
		final int n = 241;
		ISO_8859_2_CHAR_MAP.put(N, "Ń");
		ISO_8859_2_CHAR_MAP.put(n, "ń");
		
		final int O = 211;
		final int o = 243;
		ISO_8859_2_CHAR_MAP.put(O, "Ó");
		ISO_8859_2_CHAR_MAP.put(o, "ó");

		final int S = 140;
		final int s = 156;
		ISO_8859_2_CHAR_MAP.put(S, "Ś");
		ISO_8859_2_CHAR_MAP.put(s, "ś");
		
		final int Z = 143;
		final int z = 159;
		ISO_8859_2_CHAR_MAP.put(Z, "Ź");
		ISO_8859_2_CHAR_MAP.put(z, "ź");
		
		final int ZZ = 175;
		final int zz = 191;
		ISO_8859_2_CHAR_MAP.put(ZZ, "Ż");
		ISO_8859_2_CHAR_MAP.put(zz, "ż");
	}
	
	protected String query;
	
	public boolean isQueryEmpty() {
		return query == null || query.trim().length() == 0;
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		StringBuilder sb = new StringBuilder();
		
		for (char c : query.toCharArray()) {
			if (ISO_8859_2_CHAR_MAP.containsKey((int) c)) {
				sb.append(ISO_8859_2_CHAR_MAP.get((int) c));
			} else {
				sb.append(c);
			}
		}
		
		this.query = sb.toString();
	}
}

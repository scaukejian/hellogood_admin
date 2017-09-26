package com.hellogood.utils;

import org.apache.commons.lang.StringUtils;

public class DataUtil {
	
	public static String strToconent(String content){
		content = StringUtils.replace(content, "&amp;lt;", "<");
		content = StringUtils.replace(content, "&amp;gt;", ">");
		content = StringUtils.replace(content, "&lt;", "<");
		content = StringUtils.replace(content, "&gt;", ">");
		//拼音
		content = StringUtils.replace(content, "&amp;aacute;", "á");
		content = StringUtils.replace(content, "&amp;agrave;", "à");
		content = StringUtils.replace(content, "&amp;oacute;", "ó");
		content = StringUtils.replace(content, "&amp;ograve;", "ò");
		content = StringUtils.replace(content, "&amp;ecirc;", "ê");
		content = StringUtils.replace(content, "&amp;eacute;", "é");
		content = StringUtils.replace(content, "&amp;egrave;", "è");
		content = StringUtils.replace(content, "&amp;iacute;", "í");
		content = StringUtils.replace(content, "&amp;igrave;", "ì");
		content = StringUtils.replace(content, "&amp;uacute;", "ú");
		content = StringUtils.replace(content, "&amp;ugrave;", "ù");
		content = StringUtils.replace(content, "&amp;uuml;", "ü");
		
		content = StringUtils.replace(content, "&amp;ang;", "∠");
		content = StringUtils.replace(content, "&amp;isin;", "∈");
		content = StringUtils.replace(content, "&amp;cup;", "∪");
		content = StringUtils.replace(content, "&amp;or;", "∨");
		content = StringUtils.replace(content, "&amp;and;", "∧");
		content = StringUtils.replace(content, "&amp;infin;", "∞");
		content = StringUtils.replace(content, "&amp;prop;", "∝");
		content = StringUtils.replace(content, "&amp;int;", "∫");
		content = StringUtils.replace(content, "&amp;divide;", "÷");
		content = StringUtils.replace(content, "&amp;times;", "×");
		content = StringUtils.replace(content, "&amp;plusmn;", "±");
		content = StringUtils.replace(content, "&amp;ge;", "≥");
		content = StringUtils.replace(content, "&amp;le;", "≤");
		content = StringUtils.replace(content, "&amp;ne;", "≠");
		content = StringUtils.replace(content, "&amp;asymp;", "≈");
		content = StringUtils.replace(content, "&amp;middot;", "·");
		content = StringUtils.replace(content, "&amp;there4;", "∴");
		content = StringUtils.replace(content, "&amp;perp;", "⊥");
		content = StringUtils.replace(content, "&amp;deg;", "°");
		content = StringUtils.replace(content, "&amp;prime;", "′");
		content = StringUtils.replace(content, "&amp;permil;", "‰");
		content = StringUtils.replace(content, "&amp;laquo;", "«");
		content = StringUtils.replace(content, "&amp;raquo;", "»");
		content = StringUtils.replace(content, "&amp;uarr;", "↑");
		content = StringUtils.replace(content, "&amp;darr;", "↓");
		content = StringUtils.replace(content, "&amp;rarr;", "→");
		content = StringUtils.replace(content, "&amp;larr;", "←");
		content = StringUtils.replace(content, "&amp;curren;", "¤");
		content = StringUtils.replace(content, "&amp;Psi;", "Ψ");
		content = StringUtils.replace(content, "&amp;sum;", "∑");
		content = StringUtils.replace(content, "&amp;cap;", "∩");
		content = StringUtils.replace(content, "&amp;xi;", "ξ");
		content = StringUtils.replace(content, "&amp;zeta;", "ζ");
		content = StringUtils.replace(content, "&amp;omega;", "ω");
		content = StringUtils.replace(content, "&amp;prod;", "∏");
		content = StringUtils.replace(content, "&amp;radic;", "√");
		content = StringUtils.replace(content, "&amp;infin;", "∞");
		content = StringUtils.replace(content, "&amp;equiv;", "≡");
		content = StringUtils.replace(content, "&amp;hellip;", "…");
		content = StringUtils.replace(content, "&amp;mdash;", "—");
		content = StringUtils.replace(content, "&amp;quot;", "\"");
		content = StringUtils.replace(content, "&quot;", "\"");
		content = StringUtils.replace(content, "&amp;#39;", "'");
		content = StringUtils.replace(content, "&amp;ldquo;", "“");
		content = StringUtils.replace(content, "&amp;rdquo;", "”");
		content = StringUtils.replace(content, "&amp;lsquo;", "‘");
		content = StringUtils.replace(content, "&amp;rsquo;", "’");
		content = StringUtils.replace(content, "&amp;nbsp;", " ");
		content = StringUtils.replace(content, "&amp;amp;", "&");
		
		//content = StringUtils.replace(content, "&amp;", "\n");
		return content;
	}
}

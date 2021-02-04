package com.example.fanyi.util;

public class ReplaceABC {
	public String mString ;
	public String beforeReplace ;
	public String afterReplace ;
	
	/**
	 * ��һ���ַ����е�ĳЩ�ַ��滻��ָ���ַ�
	 * @param mString Ҫ�滻�����ַ����ַ���
	 * @param beforeReplace Ҫ�滻���ַ�
	 * @param afterReplace �滻�ɵ��ַ�
	 * @return
	 */
	public static String mReplace(String mString,String beforeReplace,String afterReplace){
		String returnStr;
		returnStr = mString.replace(beforeReplace, afterReplace);
		return returnStr;
	}
}

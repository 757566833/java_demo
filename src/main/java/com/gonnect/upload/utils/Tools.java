package com.gonnect.upload.utils;

import java.io.UnsupportedEncodingException;

public class Tools {
	public String gett(String s,String date)
	{
		String str = null;
		try {
			s.getBytes("iso-8859-1");
			str = new String(s.getBytes("iso-8859-1"),date);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}

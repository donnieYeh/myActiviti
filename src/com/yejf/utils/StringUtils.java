package com.yejf.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {
	public InputStream string2inputStream(String str) {
		//1
		InputStream is = new ByteArrayInputStream(str.getBytes());
		//2
		//ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return is;
	}
	
	public String inputStream2string(InputStream in) throws IOException{
		StringBuffer out = new StringBuffer();
		     byte[] b = new byte[4096];
				for (int n; (n = in.read(b)) != -1;) {
				  out.append(new String(b, 0, n));
				 }
		return out.toString();
	}
	
	public String inputStream2string2(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = " ";
		while ((line = in.readLine()) != null){
		buffer.append(line);
		}
		return buffer.toString();
	}
}

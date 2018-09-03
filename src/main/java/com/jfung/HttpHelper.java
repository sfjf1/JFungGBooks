package com.jfung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class HttpHelper {
	public static String getBooks(String encodedUrl) {
		StringBuffer sb = new StringBuffer();

		try {
			HttpGet get = new HttpGet(encodedUrl);
			HttpClient httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(get);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}

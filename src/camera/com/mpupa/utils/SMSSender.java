package com.mpupa.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * 
 * @author paulc
 */
public class SMSSender {

	public String sendSms(String sToPhoneNo, String sMessage) {

		//https://secure.adiqglobal.com/gateway?
		//userName=histscotapi&password=x9fag7Fu&mode=send&action=longsms&campaignId=10643
		//&keywordId=45600&consumerNumber=%2B8618662525509&message=This%20an%20international%20OUTBOUND%20test
		try {
			// Construct data
			String data = "userName="
					+ URLEncoder.encode("histscotapi", "UTF-8");
			data += "&password="
					+ URLEncoder.encode("x9fag7Fu", "UTF-8");
			data += "&mode=" + URLEncoder.encode("send", "UTF-8");
			data += "&action=" + URLEncoder.encode("longsms", "UTF-8");
			data += "&campaignId=" + URLEncoder.encode("10643", "UTF-8");
			data += "&keywordId=" + URLEncoder.encode("45600", "UTF-8");
			data += "&consumerNumber=" + URLEncoder.encode(sToPhoneNo, "UTF-8");
			data += "&message=" + URLEncoder.encode(sMessage, "UTF-8");
			// Send data
			System.out.println(data);
			URL url = new URL("https://secure.adiqglobal.com/gateway");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			String sResult = "";
			while ((line = rd.readLine()) != null) {
				// Process line...
				sResult = sResult + line + " ";
			}
			wr.close();
			rd.close();
			return sResult;
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
			return "Error " + e;
		}
	}
	
	public static void main(String a[]) {
		SMSSender sender = new SMSSender();
		
		//sender.sendSms("8618915745156", "Hi Ray, i'm testing send sms by java, if recieve this message, pls let me know. Shawn");
		String string = sender.sendSms("+8618662525509", "123eee");
		
		System.out.println(string);
	}
}
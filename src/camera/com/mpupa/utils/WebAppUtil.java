package com.mpupa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebAppUtil {

	private static Logger logger = Logger.getLogger(WebAppUtil.class);

	// private static List<ShopAddress> addressesList;

	/**
	 * Transform and output json data.
	 * 
	 * @param obj
	 * @param response
	 * @author Liu Jianwei
	 */
	public static void outputJSONResult(Object obj, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			response.setHeader("ContentType", "text/json");
			response.setCharacterEncoding("utf-8");

			ObjectMapper mapper = new ObjectMapper();
			pw = response.getWriter();
			// System.out.println(mapper.writeValueAsString(obj));
			mapper.writeValue(pw, obj);
			// pw.write(result);
			pw.flush();
			// mapper.
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	/**
	 * Transform and output json data for remote.
	 * 
	 * @param obj
	 * @param response
	 * @author Liu Jianwei
	 */
	public static void outputJSONResultForRemote(Object obj,
			HttpServletResponse response, HttpServletRequest request) {
		PrintWriter pw = null;
		try {
			response.setHeader("ContentType", "text/json");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");

			ObjectMapper mapper = new ObjectMapper();
			pw = response.getWriter();

			String callbackString = request.getParameter("mpupacallback");

			if (null != callbackString && !"".equals(callbackString)) {
				callbackString += "(" + mapper.writeValueAsString(obj) + ")";
				pw.write(callbackString);
			} else {
				mapper.writeValue(pw, obj);
			}
			// System.out.println(mapper.writeValueAsString(obj));
			// mapper.writeValue(pw, obj);
			// pw.write(result);
			pw.flush();
			// mapper.
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	/**
	 * 把json字符串转换成JavaBean
	 * 
	 * @param jsonString
	 * @param clz
	 * @return
	 * @author Shawn
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseJsonToBean(String jsonString, Class<T> clz) {
		Object object = null;
		try {
			// object = clz.newInstance();
			if (null != jsonString && !"".equals(jsonString) && clz != null) {

				ObjectMapper mapper = new ObjectMapper();
				object = mapper.readValue(jsonString, clz);
			} else {
				throw new Exception(
						"All params for method parseJsonToBean() can not be null!");
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return (T) object;
	}

	/**
	 * 把json转换成List<?>
	 * 
	 * @param jsonString
	 * @param clz
	 *            泛型中包含的对象
	 * @return
	 * @author Shawn
	 */
	public static List<?> parseJsonToList(String jsonString, Class clz) {
		List<Object> list = new ArrayList<Object>();

		try {
			if (null != jsonString && !"".equals(jsonString) && clz != null) {
				Object object = new Object();
				ObjectMapper mapper = new ObjectMapper();

				jsonString = jsonString.replace("[", "").replace("]", "");
				jsonString = jsonString.replace("},", "}-");
				String[] array = jsonString.split("-");

				for (String string : array) {
					object = mapper.readValue(string, clz);
					list.add(object);
				}

			} else {
				throw new Exception(
						"All params for method parseJsonToBean() can not be null!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

		return list;
	}

	/**
	 * 发送sms
	 * 
	 * @author shawn
	 * @date 2012 2012-2-6 上午11:57:38
	 */
	public static String sendSms(String sToPhoneNo, String sMessage) {

		try {

			if (null == sToPhoneNo || "".endsWith(sToPhoneNo)) {
				throw new Exception("To phone no can not be null");
			} else {
				if (!sToPhoneNo.startsWith("+")) {
					sToPhoneNo = "+" + sToPhoneNo;
				}
			}
			String data = "https://secure.adiqglobal.com/gateway?userName=histscotapi&password=x9fag7Fu&mode=send&action=longsms&campaignId=10643&keywordId=45600&consumerNumber="
					+ URLEncoder.encode(sToPhoneNo, "utf-8")
					+ "&message="
					+ URLEncoder.encode(sMessage, "utf-8");
			URL url = new URL(data);
			java.net.HttpURLConnection conn = (HttpURLConnection) url
					.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			String sResult = "";
			while ((line = rd.readLine()) != null) {
				sResult = sResult + line + " ";
			}
			rd.close();
			return sResult;
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
			return "Error " + e;
		}
	}

	private static String getCureDate() {
		return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	}

	/**
	 * send email
	 * 
	 * @author shawn
	 * @date 2012-4-19下午1:21:00
	 */
	public static void sendMessage(String content, ServletContext context,
			File f, File fyes) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		MimeMessage msg = senderImpl.createMimeMessage();
		try {
			// if(content.toString().startsWith("There's no transaction in this day!")){
			// return;
			// }else{
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);

			// helper.setTo("toni@m-pupa.com");
			helper.setTo("ray@m-pupa.com");
			String[] ccArray = { "steve@m-pupa.com", "toni@m-pupa.com",
					"shawn@m-pupa.com", "antony@m-pupa.com" };
			helper.setCc(ccArray);
			helper.setSubject("Mcastle Statistik:" + getCureDate());
			helper.setText(content);
			helper.addAttachment(f.getName(), f);
			helper.addAttachment(fyes.getName(), fyes);
			sender.send(msg);
			// }
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String a[]) {
		System.out.println(WebAppUtil.sendSms("+447917845392",
				"message to +447917845392, shawnLiu111"));
		System.out.println(WebAppUtil.sendSms("+4407917845392",
				"message to +4407917845392, shawnLiu222"));
	}
}

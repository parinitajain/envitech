package com.sapient.enviro.utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sapient.enviro.dataobjects.response.Bharat4Standards;
import com.sapient.enviro.dataobjects.response.UserResponse;
import com.sapient.enviro.dataobjects.response.VehiclePollutionData;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class EnviroRestUtility {

	public static VehiclePollutionData getUserALertsData()
			throws UnirestException, JsonParseException, JsonMappingException, IOException {
		VehiclePollutionData vehiclePollutionData = new VehiclePollutionData();
		ObjectMapper objectM = new ObjectMapper();
		HttpResponse<String> httpResponse = Unirest.get("https://tutorial-a111.restdb.io/rest/alert")
				.header("content-type", "application/json").header("x-apikey", "d426afcf53d499d49217ce81b06b7cb3bb559")
				.header("cache-control", "no-cache").asString();

		if (httpResponse.getStatus() == 200) {
			String body = (String) httpResponse.getBody();
			body = body.substring(1);
			body = body.substring(0, body.length() - 1);
			vehiclePollutionData = objectM.readValue(body, VehiclePollutionData.class);
		}

		return vehiclePollutionData;

	}

	public static void insertDBWithUserData(Map<String, Double> mq4GasValueMap, String dbName, String date)
			throws UnirestException {

		Unirest.post("https://tutorial-a111.restdb.io/rest/" + dbName).header("content-type", "application/json")
				.header("x-apikey", "d426afcf53d499d49217ce81b06b7cb3bb559").header("cache-control", "no-cache")
				.body("{\"APIKey\":\"" + "H7S7OEPXUGKTU2M0" + "\",\"DateTime\":\"" + date + "\",\"CO_gas\":\""
						+ mq4GasValueMap.get(Constant.CO) + "\",\"CO2\":\"" + mq4GasValueMap.get(Constant.CO2)
						+ "\",\"NOX\":\"" + mq4GasValueMap.get(Constant.NOX) + "\"," + "\"NH4\":\""
						+ mq4GasValueMap.get(Constant.NH4) + "\"," + "\"C6H6\":\"" + mq4GasValueMap.get(Constant.C6H6)
						+ "\"}")
				.asString();
	}

	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		System.out.println("Calendard Instance " + dateFormat.format(cal.getTime()));
		return date;
	}

	public static Bharat4Standards getThreshHold()
			throws UnirestException, JsonParseException, JsonMappingException, IOException {
		Bharat4Standards bharat4Standards = new Bharat4Standards();
		ObjectMapper objectM = new ObjectMapper();
		HttpResponse<String> httpResponse = Unirest.get("https://tutorial-a111.restdb.io/rest/bharat4standards")
				.header("content-type", "application/json").header("x-apikey", "d426afcf53d499d49217ce81b06b7cb3bb559")
				.header("cache-control", "no-cache").asString();

		if (httpResponse.getStatus() == 200) {
			String body = (String) httpResponse.getBody();
			body = body.substring(1);
			body = body.substring(0, body.length() - 1);
			System.out.println("Body" + body);
			bharat4Standards = objectM.readValue(body, Bharat4Standards.class);
		}
		return bharat4Standards;
	}

	public static String getCountOfAlerts()
			throws UnirestException, JsonParseException, JsonMappingException, IOException {
		HttpResponse<String> httpResponse = Unirest.get("https://tutorial-a111.restdb.io/rest/alert")
				.queryString("q", "{}").queryString("h", "{\"$aggregate\": [\"COUNT:\"]}")
				.header("content-type", "application/json").header("x-apikey", "d426afcf53d499d49217ce81b06b7cb3bb559")
				.header("cache-control", "no-cache").asString();

		if (httpResponse.getStatus() == 200) {
			String body = (String) httpResponse.getBody();
			body = body.substring(body.indexOf(":") + 1, body.length() - 1);
			return body;
		}
		return "Wrong Body";
	}

	public static void updateDBWithUserData(Map<String, Double> mq4GasValueMap, String dbName, String date,
			VehiclePollutionData vehiclePollutionData) throws UnirestException {
		Unirest.put("https://tutorial-a111.restdb.io/rest/" + dbName + "/" + vehiclePollutionData.get_id())
				.header("content-type", "application/json").header("x-apikey", "d426afcf53d499d49217ce81b06b7cb3bb559")
				.header("cache-control", "no-cache")
				.body("{\"APIKey\":\"" + vehiclePollutionData.getAPIKey() + "\",\"DateTime\":\"" + date
						+ "\",\"CO_gas\":\"" + mq4GasValueMap.get(Constant.CO) + "\",\"CO2\":\"" + mq4GasValueMap.get(Constant.CO2)
						+ "\",\"NOX\":\"" + mq4GasValueMap.get(Constant.NOX) + "\"," + "\"NH4\":\"" + mq4GasValueMap.get(Constant.NH4)
						+ "\"," + "\"C6H6\":\"" + mq4GasValueMap.get(Constant.C6H6)
						+ "\"}")
				.asString();

	}

	public static void sendDataOverRest(Map<String, Double> mq4GasValueMap) throws UnirestException {

		Unirest.post("https://api.thingspeak.com/update.json").header("accept", "application/json")
				.field("api_key", "H7S7OEPXUGKTU2M0").field("field1", mq4GasValueMap.get(Constant.NOX))
				.field("field2", mq4GasValueMap.get(Constant.CO)).field("field3", mq4GasValueMap.get(Constant.CO2))
				.field("field4", mq4GasValueMap.get(Constant.NH4)).field("field5", mq4GasValueMap.get(Constant.C6H6)).asString();
	}

	public static UserResponse getUserEmail()
			throws UnirestException, JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectM = new ObjectMapper();
		UserResponse userResponse = new UserResponse();
		HttpResponse<String> httpResponse = Unirest.get("https://tutorial-a111.restdb.io/rest/customer")
				.header("content-type", "application/json").header("x-apikey", "d426afcf53d499d49217ce81b06b7cb3bb559")
				.header("cache-control", "no-cache").asString();

		if (httpResponse.getStatus() == 200) {
			String body = (String) httpResponse.getBody();
			body = body.substring(1);
			body = body.substring(0, body.length() - 1);
			userResponse = objectM.readValue(body, UserResponse.class);
		}
		return userResponse;
	}

	public static void sendTweet(String tweetMessage) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("GclsazKEtN57iI8V320u4baZB")
				.setOAuthConsumerSecret("OzOnFvxkiLvmdN380YHkqdkoTBX8PJv18QWS38n2ZUPFsc3g45")
				.setOAuthAccessToken("992651877035663360-4pG9nzS7PbaO6Ry8sFIsmGWwTuu6n2S")
				.setOAuthAccessTokenSecret("D0I9tSP5e0ZPuryecQDVrAbl2WH8bhQQecGoeKRi2a1Yx");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		Status status;
		try {
			status = twitter.updateStatus(tweetMessage);
			System.out.println("Successfully updated the status to [" + status.getText() + "].");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sendEmail(UserResponse userResponse, Map<String, Double> mq4GasValueMap,
			Bharat4Standards bharat4Standards) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("enviroswachhbharat@gmail.com", "envirosb");
			}
		});

		session.setDebug(true);
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("enviroswachhbharat@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userResponse.getEmail()));
			message.setSubject("ALERT!!!! Car Pollution level increased");
			// message.setText("Your pollution level of vehicle is more than
			// standard value");

			String noxColor = "#7CFC00", coColor = "#7CFC00", co2Color = "#7CFC00", nh4Color = "#7CFC00",c6h6Color ="#7CFC00";

			if (Double.valueOf(mq4GasValueMap.get(Constant.NOX)) > Double.parseDouble(bharat4Standards.getNOX())) {
				noxColor = "#FF0000";
			}

			if (Double.valueOf(mq4GasValueMap.get(Constant.CO)) > Double.parseDouble(bharat4Standards.getCO_gas())) {
				coColor = "#FF0000";
			}

			if (Double.valueOf(mq4GasValueMap.get(Constant.CO2)) > Double.parseDouble(bharat4Standards.getCO2())) {
				co2Color = "#FF0000";
			}
			if (Double.valueOf(mq4GasValueMap.get(Constant.NH4)) > Double.parseDouble(bharat4Standards.getNH4())) {
				nh4Color = "#FF0000";
			}
			if (Double.valueOf(mq4GasValueMap.get(Constant.C6H6)) > Double.parseDouble(bharat4Standards.getC6H6())) {
				c6h6Color = "#FF0000";
			}

			message.setContent("<head>" + "<style>" + "table, th, td {" + "border: 1px solid black;" + "}" + "</style>"
					+ "<head>" + "Your last service is due on " + getDueDate() + " <br><br> " + "Vehicle Details : "
					+ "<br><br>" + "Vehicle Type = " + userResponse.getVType() + " <br> Vehicle Model = "
					+ userResponse.getVModel() + " <br> Vehicle Number = " + userResponse.getVNumber() + " <br> <br>"
					+ "Below is the current pollution level of your vehicle" + "<br><br>" + "</head>"
					+ "<table border=\"1\" style=\"width:60%\">" + "<tr>"
					+ "<th style=\"background-color:AntiqueWhite ;color:black\"><b> NOX </b></th>"
					+ "<th style=\"background-color:AntiqueWhite ;color:black\"><b> CO </b></th>"
					+ "<th style=\"background-color:AntiqueWhite ;color:black\"><b> Benzene </b></th>"
					+"<th style=\"background-color:AntiqueWhite ;color:black\"><b> Ammonia </b></th>"
					+"<th style=\"background-color:AntiqueWhite ;color:black\"><b> CO2 </b></th>"
					+ "</tr>" + "<tr>"
					+ "<th style=\"background-color:" + noxColor + ";color:black\">" + mq4GasValueMap.get(Constant.NOX)
					+ "</th>" + "<th style=\"background-color:" + coColor + ";color:black\">" + mq4GasValueMap.get(Constant.CO)
					+ "</th>" + "<th style=\"background-color:" + c6h6Color + ";color:black\">" + mq4GasValueMap.get(Constant.C6H6)
					+ "</th>" + "<th style=\"background-color:" + nh4Color + ";color:black\">"
					+ mq4GasValueMap.get(Constant.NH4) + "</th>" + "<th style=\"background-color:" + co2Color
					+ ";color:black\">" + mq4GasValueMap.get(Constant.CO2) + "</th>" + "</tr>" + "</table>", "text/html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public static String getDueDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 7);
		String date = dateFormat.format(cal.getTime());
		System.out.println("Calendard Instance " + dateFormat.format(cal.getTime()));
		return date;
	}

}

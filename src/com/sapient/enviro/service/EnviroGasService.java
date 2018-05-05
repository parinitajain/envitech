package com.sapient.enviro.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sapient.enviro.dataobjects.response.Bharat4Standards;
import com.sapient.enviro.dataobjects.response.UserResponse;
import com.sapient.enviro.dataobjects.response.VehiclePollutionData;
import com.sapient.enviro.utility.Constant;
import com.sapient.enviro.utility.EnviroRestUtility;
import com.sapient.enviro.utility.MQUtility;

public class EnviroGasService {

	public static void main(String[] args) throws IOException, InterruptedException, UnirestException {

		System.out.println("Press CTRL+C to abort.");

		short mq4Channel = 0;

		double mq4RoClean = 3.65;

		int mq4RL = 25;

		MQUtility mq4;

		
		mq4 = new MQUtility(mq4Channel, mq4RoClean, mq4RL);

		while (true) {

			Map<String, Double> mq4GasValueMap = mq4.getMQValues();

			// Map<String, Double> mq135GasValueMap = mq135.getMQValues();

			// Print MQ 4

			System.out.println("MQ - 4 \n");

			System.out.print("CO: " + (mq4GasValueMap.get(Constant.CO)) + " ppm \t");

			System.out.print("NOX: " + (mq4GasValueMap.get(Constant.NOX)) + " ppm \t");

			System.out.print("NH4: " + (mq4GasValueMap.get(Constant.NH4)) + " ppm \t");

			System.out.print("CO2: " + (mq4GasValueMap.get(Constant.CO2)) + " ppm \t");
			
			System.out.print("C6H6: " + (mq4GasValueMap.get(Constant.C6H6)) + " ppm \t");

			// Print MQ 135

			// System.out.println("MQ - 135");

			// System.out.print("CO2: " + (mq135GasValueMap.get("CO2")) + " ppm \t");

			// System.out.print("NH4: " + (mq135GasValueMap.get("NH4")) + " ppm \t");

			// System.out.print("MQ-135 CO: " + (mq135GasValueMap.get("MQ135CO")) + " ppm
			// \t");

			String date = EnviroRestUtility.getCurrentTime();
			EnviroRestUtility.sendDataOverRest(mq4GasValueMap);
			EnviroRestUtility.insertDBWithUserData(mq4GasValueMap, "vehiclepollutiondata", date);

			Bharat4Standards bharat4Standards = EnviroRestUtility.getThreshHold();
			if (mq4GasValueMap.get(Constant.CO) > Double.parseDouble(bharat4Standards.getCO_gas())
					|| mq4GasValueMap.get(Constant.NOX) > Double.parseDouble(bharat4Standards.getNOX())
					|| mq4GasValueMap.get(Constant.CO2) > Double.parseDouble(bharat4Standards.getCO2())
					|| mq4GasValueMap.get(Constant.NH4) > Double.parseDouble(bharat4Standards.getNH4())
					|| mq4GasValueMap.get(Constant.C6H6) > Double.parseDouble(bharat4Standards.getC6H6())) {

				// To light up LED
				System.out.println("\n****ALERT****\n");
				Runtime runTime = Runtime.getRuntime();
				runTime.exec("gpio mode 1 out");
				runTime.exec("gpio write 1 1");
				Thread.sleep(500);
				runTime.exec("gpio write 1 0");

				int count = Integer.parseInt(EnviroRestUtility.getCountOfAlerts());
				if (count == 0) {
					// send mails and tweet
					UserResponse userResponse = EnviroRestUtility.getUserEmail();
					EnviroRestUtility.sendEmail(userResponse, mq4GasValueMap, bharat4Standards);
					String tweetMessage = "@" + userResponse.getVBrand() + userResponse.getVType() + "ServiceCenter Vehicle number "
							+ userResponse.getVNumber()
							+ " has Exceeded the pollution level. Please call them for vehicle Service with Due Date "
							+ EnviroRestUtility.getDueDate() +" #"+Math.random();
					EnviroRestUtility.sendTweet(tweetMessage);
					EnviroRestUtility.insertDBWithUserData(mq4GasValueMap, "alert", date);
				} else {
					VehiclePollutionData userAlertData = EnviroRestUtility.getUserALertsData();
					if (mq4GasValueMap.get(Constant.CO)> Double.parseDouble(userAlertData.getCO_gas())
							||  mq4GasValueMap.get(Constant.NOX) > Double.parseDouble(userAlertData.getNOX())
							|| mq4GasValueMap.get(Constant.CO2)> Double.parseDouble(userAlertData.getCO2())
							|| mq4GasValueMap.get(Constant.NH4) > Double.parseDouble(userAlertData.getNH4())
							|| mq4GasValueMap.get(Constant.C6H6) > Double.parseDouble(userAlertData.getC6H6())) {

						EnviroRestUtility.updateDBWithUserData(mq4GasValueMap, "alert", date, userAlertData);

					}

				}

			}

			Thread.sleep(5000);
		}

	}

}

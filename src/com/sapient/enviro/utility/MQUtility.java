/**
 * 
 */
package com.sapient.enviro.utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 *
 */
public class MQUtility {

	/*
	 * ######################### Hardware Related Macros #########################
	 */

	public int RL_VALUE = 20;// For MQ4

	public double RO_CLEAN_AIR_FACTOR = 4.4;// FOR MQ4 ~3.65 For MQ135

	/*
	 * ######################### Software Related Macros #########################
	 */
	static long CALIBARAION_SAMPLE_TIMES = 50; // define how many samples you are going to take in the calibration phase
	static long CALIBRATION_SAMPLE_INTERVAL = 500; // define the time interal(in milisecond) between each samples in the
	// cablibration phase
	static long READ_SAMPLE_INTERVAL = 50; // define how many samples you are going to take in normal operation
	static long READ_SAMPLE_TIMES = 5; // define the time interal(in milisecond) between each samples in
	// normal operation


	// MQ- 4 Curves
	//public double[] lpgCurve = { 2.3, 0.41, -0.31 };
	public double[] coCurve = { 1,0.45,-0.29 };
	//public double[] smokeCurve = { 2.3, 0.59, -0.103 };
	//public double[] CH4Curve = { 2.3, 0.25, -0.36 };
	
	//MQ- 135 curve
	public double[] co2Curve = { 1, 0.36 ,-0.44};
	public double[] NH4Curve = { 1,0.41,-0.50};
	public double[] NOXCurve = { 1,0.20,-0.445};
	public double[] C6H6Curve = { 1,0.17,-0.38};

	public short adcChannel = 0;

	public ADCUtility adc;

	public double Ro = 0;

	/**
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */
	public MQUtility(short adcChannel, double RO_CLEAN_AIR_FACTOR, int RL_VALUE) throws IOException, InterruptedException {

		this.adc = new ADCUtility();
		this.adcChannel = adcChannel;
		this.RO_CLEAN_AIR_FACTOR = RO_CLEAN_AIR_FACTOR;
		this.RL_VALUE = RL_VALUE;
		this.Ro = calibration();
		System.out.println("RO value for Sensor at channel " + adcChannel + " =" + this.Ro);

	}

	public double calibration() throws InterruptedException, IOException {
		double val = 0.0;
		System.out.println("CALIBRATING... channel :"+this.adcChannel);

		for (int i = 0; i <= CALIBARAION_SAMPLE_TIMES; i++) {
			val += mqResistanceCalculation(adcRead());
			Thread.sleep(CALIBRATION_SAMPLE_INTERVAL);
		}

		val = val / CALIBARAION_SAMPLE_TIMES; // calculate the average value

		val = val / this.RO_CLEAN_AIR_FACTOR; // divided by RO_CLEAN_AIR_FACTOR yields the Ro
		// according to the chart in the datasheet
		return val;
	}

	public double mqResistanceCalculation(double adcReadValue) {

		return (double) (this.RL_VALUE * (1023.0 - adcReadValue) / adcReadValue);

	}

	private double adcRead() throws IOException {
		return (double) this.adc.getConversionValue(this.adcChannel);
	}

	public double readRSValue() throws InterruptedException, IOException {
		double rs = 0.0;

		for (int i = 0; i <= READ_SAMPLE_TIMES; i++) {
			rs += mqResistanceCalculation(adcRead());
			Thread.sleep(READ_SAMPLE_INTERVAL);
		}

		rs = rs / READ_SAMPLE_TIMES; // calculate the average value
		return rs;
	}

	public Map<String, Double> getMQValues() throws InterruptedException, IOException {

		Map<String, Double> gasValueMap = new HashMap<String, Double>();

		double rs = readRSValue();
		if(this.adcChannel==0) {
		gasValueMap.put(Constant.CO, getPPMFromStraightLine(rs / this.Ro, this.coCurve));
		gasValueMap.put(Constant.NOX, getPPMFromStraightLine(rs / this.Ro, this.NOXCurve));
		gasValueMap.put(Constant.CO2, getPPMFromStraightLine(rs / this.Ro, this.co2Curve));
		gasValueMap.put(Constant.NH4, getPPMFromStraightLine(rs / this.Ro, this.NH4Curve));
		gasValueMap.put(Constant.C6H6, getPPMFromStraightLine(rs / this.Ro, this.C6H6Curve));
		}
		if(this.adcChannel==1)
		{
			gasValueMap.put("CO2", getPPMFromStraightLine(rs / this.Ro, this.co2Curve));
			gasValueMap.put("NH4", getPPMFromStraightLine(rs / this.Ro, this.NH4Curve));
		}
		return gasValueMap;

	}

	public double getPPMFromStraightLine(double rs_ro_ratio, double[] pcurve) {
		return (Math.pow(10, (((Math.log(rs_ro_ratio) - pcurve[1]) / pcurve[2]) + pcurve[0])));
	}

}
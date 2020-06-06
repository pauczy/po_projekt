package pl.edu.pw.fizyka.pojava.id;

import java.util.Locale;
import java.util.ResourceBundle;

public class Calculator {
	
	public static final double C = 299792458; // m/s
		
	private static double gamma (double v) {
		double denominator = Math.sqrt(1 - (v*v)/(C*C));
		return  1/denominator;
	}
	
	public static double dilation (double t0, double v) { //time dilation
		return t0*gamma(v);
	}
	
	public static double contraction  (double l0, double v) { //length contraction
		return l0/gamma(v);
	}
	
	public static String timeToString(double t) {
		String time = " ";
		Locale currentLocale = new Locale(System.getProperty("user.language"));
		ResourceBundle rb = ResourceBundle.getBundle("LabelsBundle",currentLocale);
		
		int years = (int) (t / (365*24*3600));
		t = t % (365*24*3600);
		int days = (int) (t / (24*3600));
		t = t % (24*3600);
		int hours = (int) (t / 3600);
		t = t % 3600;
		int minutes = (int) (t / 60);
		int seconds = (int) Math.round(t % 60);
		
		if (years != 0) time += String.valueOf(years) + " " + rb.getString("years") + " ";
		if (days != 0) time += String.valueOf(days) + " " + rb.getString("days") + " ";
		if (hours != 0) time += String.valueOf(hours) + " h ";
		if (minutes != 0) time += String.valueOf(minutes) + " min ";
		if (seconds != 0) time += String.valueOf(seconds) + " s ";
		
		return time;
		}
}

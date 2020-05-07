package pl.edu.pw.fizyka.pojava.id;

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
}

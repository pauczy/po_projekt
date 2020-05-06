package pl.edu.pw.fizyka.pojava.id;

public class Calculator {
	
	public static double c = 299792458; // m/s
		
	private double gamma (double v) {
		double denominator = Math.sqrt(1 - (v*v)/(c*c));
		return  1/denominator;
	}
	
	public double dilation (double t0, double v) { //time dilation
		return t0*gamma(v);
	}
	
	public double contraction  (double l0, double v) { //length contraction
		return l0/gamma(v);
	}
}

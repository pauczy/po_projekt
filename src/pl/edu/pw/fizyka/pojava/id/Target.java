package pl.edu.pw.fizyka.pojava.id;

public class Target {
	
	protected String name;
	protected double distance; //in light-years
	public static double lightYear = 9.4607*Math.pow(10, 15); //in metres
	
	public Target (String name, double distance) {
		this.name = name;
		this.distance = distance;
	}
	
	double distanceInMeters(){
		return distance * lightYear;
	}

}

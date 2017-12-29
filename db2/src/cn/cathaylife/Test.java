package cn.cathaylife;

public class Test {

	
	public static void main(String[] args) {
		double[] nums= {
				
				-1825.04,
				-147.37,
				500.00,
				640.00,
				6160.00,
				18812.41,
				-3544.67,
				-2406.12,
				-105.29,
				650.00,
				3180.00,
				3400.00,
				9136.79
				
		};
		double sum=0;
		for(double d:nums) {
			sum+=d;
		}
		System.out.println(sum);
	}
}

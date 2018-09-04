package jags.functions;

import beast.core.Param;
import jags.nodes.JFunction;
import jags.nodes.Transform;

import beast.core.Description;

@Description("Performs the StdDev function")
public class StdDev extends Transform {
	JFunction x;

	public StdDev(@Param(name="x", description="list of values to calulate standard deviation of") JFunction x) {
		this.x = x;
		values = new double[1];
		storedvalues = new double[1];
	}

	@Override
	protected void doTransform() {
		double sum = 0;
		for (int i = 0; i < x.getDimension(); i++) {
			sum += x.getArrayValue(i); 
		}
		double mean = sum / x.getDimension();
		double mean2 = mean * mean;
		
		sum = 0;
		for (int i = 0; i < x.getDimension(); i++) {
			double v = x.getArrayValue(i);
			sum += v * v - mean2; 
		}
		values[0] = Math.sqrt(sum/(x.getDimension() - 1));
	}

	public JFunction getX() {
		return x;
	}
	public void setX(JFunction  x) {
		this.x = x;
		resetValue(1);
	}
}

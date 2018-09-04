package jags.functions;

import beast.core.Param;
import jags.nodes.JFunction;
import jags.nodes.Transform;

import beast.core.Description;

@Description("Performs the Mean function")
public class Mean extends Transform {
	JFunction x;

	public Mean(@Param(name="x", description="list of values to calulate mean of") JFunction x) {
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
		values[0] = sum / x.getDimension();
	}

	public JFunction getX() {
		return x;
	}
	public void setX(JFunction  x) {
		this.x = x;
		resetValue(1);
	}
}

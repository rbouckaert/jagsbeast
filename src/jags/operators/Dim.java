package jags.operators;



import jags.functions.UnivariableFunction;
import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Dim operation")
public class Dim extends UnivariableFunction {

	public Dim(@Param(name="x", description="value or function to take dimentions of") JFunction x) {
		super(x);
		values = new double[x.getDimensionCount()];
		storedvalues = new double[x.getDimensionCount()];
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < values.length; i++) {
			values[i] = x.getDimension(i);
		}
	}

}

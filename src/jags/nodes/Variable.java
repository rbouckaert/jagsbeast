package jags.nodes;

import beast.core.*;
import beast.core.parameter.RealParameter;

@Description("Random variable in graphical model")
public class Variable extends RealParameter {
	
	public Variable(@Param(name="value", description="start values of the Variable") double [] value) {
		super(doubleToDouble(value));
	}

	private static Double[] doubleToDouble(double[] value) {
		Double [] d = new Double[value.length];
		for (int i = 0; i < d.length; i++) {
			d[i] = value[i];
		}
		return d;
	}

}

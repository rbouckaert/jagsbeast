package jags.functions;

import org.apache.commons.math.MathException;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Probit function")
public class Probit extends UnivariableFunction {
	
	public Probit(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double arg = x.getArrayValue(i);
			try {
				System.err.println("Check if this is probit function is correct!");
				values[i] = Math.sqrt(2) * org.apache.commons.math.special.Erf.erf(2*arg - 1);
			} catch (MathException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

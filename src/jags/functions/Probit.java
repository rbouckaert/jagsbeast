package jags.functions;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;

import jags.nodes.JFunction;

public class Probit extends UnivariableFunction {
	NormalDistribution norm;
	
	public Probit(JFunction x) {
		super(x);
		norm = new NormalDistributionImpl();
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double arg = x.getArrayValue(i);
			try {
				System.err.println("Check if this is probit function is correct!");
				values[i] = Math.sqrt(2) * norm.inverseCumulativeProbability(2*arg - 1);
			} catch (MathException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

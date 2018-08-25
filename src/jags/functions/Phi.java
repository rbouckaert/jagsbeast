package jags.functions;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;

import jags.nodes.JFunction;

public class Phi extends UnivariableFunction {
	NormalDistribution norm;
	
	public Phi(JFunction x) {
		super(x);
		norm = new NormalDistributionImpl();
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			double arg = x.getArrayValue(i);
			try {
				values[i] = norm.cumulativeProbability(arg);
			} catch (MathException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

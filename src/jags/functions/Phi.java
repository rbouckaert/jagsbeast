package jags.functions;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Phi function")
public class Phi extends UnivariableFunction {
    public Phi(){}

	NormalDistribution norm;
	
	public Phi(@Param(name="x", description="function or value argument") JFunction x) {
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

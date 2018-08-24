package jags.distributions;

import beast.math.distributions.ParametricDistribution;

abstract public class JAGSDistribution extends ParametricDistribution {

	public abstract String getName();

	public abstract int getParameterCount();
}

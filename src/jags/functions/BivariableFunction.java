package jags.functions;

import beast.core.*;
import jags.nodes.Transform;

@Description("Base class for deterministic function with two arguments")
abstract public class BivariableFunction extends Transform {
	protected Function x;
	protected Function y;
	
	protected BivariableFunction(
			@Param(name="x", description="argument of cosine function") Function x,
			@Param(name="y", description="argument of cosine function") Function y
			) {
		super();
		this.x = x;
		this.x = y;
		dimensionCheck();
		values = new double[x.getDimension()];
		storedvalues = new double[x.getDimension()];
	}
	
	protected void dimensionCheck() {
		if (x.getDimension() != y.getDimension()) {
			throw new IllegalArgumentException("dimensions of x and y should be the same");
		}
	}
		
}

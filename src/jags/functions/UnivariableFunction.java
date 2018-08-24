package jags.functions;

import beast.core.*;
import jags.nodes.Transform;

@Description("Base class for deterministic function")
abstract public class UnivariableFunction extends Transform {
	protected Function x;
	
	protected UnivariableFunction(@Param(name="x", description="argument of cosine function") Function x) {
		super();
		this.x = x;
		values = new double[x.getDimension()];
		storedvalues = new double[x.getDimension()];
	}
		
}

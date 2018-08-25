package jags.functions;


import beast.core.*;
import jags.nodes.*;

@Description("Base class for deterministic function")
abstract public class UnivariableFunction extends Transform {
	protected JFunction x;
	
	protected UnivariableFunction(@Param(name="x", description="argument of cosine function") JFunction x) {
		super();
		this.x = x;
		values = new double[x.getDimension()];
		storedvalues = new double[x.getDimension()];
	}
		
}

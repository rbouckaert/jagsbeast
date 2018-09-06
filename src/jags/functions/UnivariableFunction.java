package jags.functions;


import beast.core.*;
import jags.nodes.*;
import beast.core.Param;

@Description("Base class for deterministic function")
abstract public class UnivariableFunction extends Transform {
	protected JFunction x;
	
	public UnivariableFunction() {}
	
	protected UnivariableFunction(@Param(name="x", description="argument of cosine function") JFunction x) {
		super();
		this.x = x;
		values = new double[x.getDimension()];
		storedvalues = new double[x.getDimension()];
	}
	
	@Override
	public int getDimensionCount() {
		return x.getDimensionCount();
	}
	
	@Override
	public int getDimension(int dim) {
		return x.getDimension(dim);
	}
	
	public JFunction getX() {return x;}
	public void setX(JFunction x) {
		this.x = x;
		resetValue(x.getDimension());
	}
}

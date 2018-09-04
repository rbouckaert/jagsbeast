package jags.functions;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;
import jags.nodes.Transform;


@Description("Represents continuous range from x to y")
public class Range extends Transform {
	JFunction x, y;
	
	public Range(@Param(name="start", description="start of range, is included in range") JFunction x, 
			     @Param(name="end", description="end of range, is included in range") JFunction y) {
		if (x.getDimension() != 1 || y.getDimension() != 1) {
			throw new IllegalArgumentException("Don't know how to handle range with multi-dimensional values");
		}
		int dim = (int)(y.getArrayValue() - x.getArrayValue()) + 1;
		values = new double[dim];
		storedvalues = new double[dim];
		this.x = x;
		this.y = y;
	}

	@Override
	protected void doTransform() {
		int dim = (int)(y.getArrayValue() - x.getArrayValue()) + 1;
		if (values.length != dim) {
			values = new double[dim];
		}
		values[0] = (int) x.getArrayValue();
		for (int i = 1; i < values.length; i++) {
			values[i] = values[i-1] + 1;
		}
	}

	
	public JFunction getStart() {
		return x;
	}
	public void setStart(JFunction x) {
		this.x = x;
	}
	public JFunction getEnd() {
		return y;
	}
	public void setEnd(JFunction y) {
		this.y = y;
	}
}

package jags.functions;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;
import jags.nodes.Transform;

@Description("If/then/else function")
public class IfElse extends Transform {
    public IfElse(){}

	JFunction x, a, b;

	public IfElse(@Param(name="x", description="condition to be tested for non-zeroness") JFunction x,
			@Param(name="a", description="if condition is non-zero, take this value") JFunction a,
			@Param(name="b", description="if condition is zero, take this value") JFunction b
			) {
		this.x = x;
		this.a = a;
		this.b = b;
	}
	
	
	@Override
	protected void doTransform() {
		if (x.getArrayValue() != 0) {
			values = a.getDoubleValues();
		} else {
			values = b.getDoubleValues();
		}
	}


	public JFunction getX() {
		return x;
	}


	public void setX(JFunction x) {
		this.x = x;
	}


	public JFunction getA() {
		return a;
	}


	public void setA(JFunction a) {
		this.a = a;
	}


	public JFunction getB() {
		return b;
	}


	public void setB(JFunction b) {
		this.b = b;
	}

	
}

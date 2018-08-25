package jags.functions;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;
import jags.nodes.Transform;

@Description("If/then/else function")
public class IfElse extends Transform {
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

}

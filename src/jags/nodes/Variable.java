package jags.nodes;

import java.util.Arrays;

import beast.core.*;
import beast.core.parameter.RealParameter;

@Description("Random variable in graphical model")
public class Variable extends RealParameter {
	Function fun;
	
	public Variable(@Param(name="id",description="identifier for this variable") String id, 
			@Param(name="fun", description="function that determines values of the Variable") Function fun) {
		super(funToDouble(fun));
		this.fun = fun;
		setID(id);
	}

	private static Double[] funToDouble(Function fun) {
		Double [] d = new Double[fun.getDimension()];
		for (int i = 0; i < d.length; i++) {
			d[i] = fun.getArrayValue(i);
		}
		return d;
	}
	
	@Override
	public String toString() {
		if (fun.getDimension() == 1) {
			return getID() + " = " + values[0];
		}
		return getID() + " = " + Arrays.toString(values);
	}

}

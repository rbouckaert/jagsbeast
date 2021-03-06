package jags.functions;

import java.util.Arrays;

import jags.nodes.JFunction;
import beast.core.Param;

import beast.core.Description;

@Description("Performs the Sort function")
public class Sort extends UnivariableFunction {
    public Sort(){}


	public Sort(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = x.getArrayValue(i); 
		}
		Arrays.sort(values);
	}

}

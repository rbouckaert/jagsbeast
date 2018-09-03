package jags.functions;

import beast.util.HeapSort;
import jags.nodes.JFunction;
import beast.core.Param;

public class Rank extends UnivariableFunction {
	// inverse of Order(x)
	public Rank(@Param(name="x", description="function or value argument") JFunction x) {
		super(x);
	}

	@Override
	protected void doTransform() {
		int [] indices = new int[values.length];
		for (int i = 0; i < x.getDimension(); i++) {
			values[i] = x.getArrayValue(i);
			indices[i] = i;
		}
		HeapSort.sort(values, indices);
		for (int i = 0; i < x.getDimension(); i++) {
			values[indices[i]] = i + 1;
		}
	}

}

package jags.functions;


import beast.util.HeapSort;
import jags.nodes.JFunction;

public class Order extends UnivariableFunction {

	public Order(JFunction x) {
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
			values[i] = indices[i] + 1;
		}
	}
}

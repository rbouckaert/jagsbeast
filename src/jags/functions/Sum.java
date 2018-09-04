package jags.functions;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;
import jags.nodes.Transform;

@Description("Gets sum of list of functions")
public class Sum extends Transform {
	JFunction [] function;
	
	public Sum(@Param(name="f", description="list of values, or functions") JFunction [] function) {
		super();
		this.function = new JFunction[function.length];
		for (int i = 0; i < function.length; i++) {
			this.function[i] = function[i];
		}
		values = new double[1];
		storedvalues = new double[1];
	}
	
	@Override
	protected void doTransform() {
		double sum = 0;
		for (int i = 0; i < values.length; i++) {
			JFunction f = function[i];
			for (double d : f.getDoubleValues()) {
				sum += d;
			}
		}
		values[0] = sum;
	}

	public JFunction[] getF() {
		return function;
	}
	public void setF(JFunction[] function) {
		this.function = function;
	}
}

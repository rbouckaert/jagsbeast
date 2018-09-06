package jags.functions;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;
import jags.nodes.Transform;

@Description("Gets minimum of list of functions")
public class Min extends Transform {
    public Min(){}

	JFunction [] function;
	
	public Min(@Param(name="f", description="list of values, or functions") JFunction [] function) {
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
		double min = Double.MAX_VALUE;
		for (int i = 0; i < function.length; i++) {
			JFunction f = function[i];
			for (double d : f.getDoubleValues()) {
				min = Math.min(min, d);
			}
		}
		values[0] = min;
		
	}

	public JFunction[] getF() {
		return function;
	}
	public void setF(JFunction[] function) {
		this.function = function;
	}
}

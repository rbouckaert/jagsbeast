package jags.functions;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;
import jags.nodes.Transform;

@Description("Concatenates list of functions into a new array")
public class Concat extends Transform {
	JFunction [] function;
	
	public Concat(@Param(name="f", description="list of values, or functions, making up the new array") JFunction [] function) {
		super();
		this.function = new JFunction[function.length];
		for (int i = 0; i < function.length; i++) {
			this.function[i] = function[i];
		}
		values = new double[function.length];
		storedvalues = new double[function.length];
	}
	
	@Override
	protected void doTransform() {
		for (int i = 0; i < values.length; i++) {
			values[i] = function[i].getArrayValue();
		}
		
	}
	
	@Override
	public int getDimensionCount() {
		if (function[0].getDimensionCount() > 1) {
			return function[0].getDimensionCount() + 1;
		}
		if (function[0].getDimension() > 1) {
			return function[0].getDimensionCount() + 1;
		}
		return 1;
	}
	
	@Override
	public int getDimension(int dim) {
		if (dim == 0) {
			return function.length;
		}
		if (getDimensionCount() > 1) {
			return function[0].getDimension(dim - 1);
		}
		return 0;
	}


}

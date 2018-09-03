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
		int dim = 0;
		for (JFunction f: function) {
			System.out.println(f);
			dim += f.getDimension();
		}
		values = new double[dim];
		storedvalues = new double[dim];
	}
	
	@Override
	protected void doTransform() {
		int k = 0;
		for (JFunction f: function) {
			for (int j = 0; j < f.getDimension(); j++) {
				values[k++] = f.getArrayValue(j);
			}
		}		
	}
	
	@Override
	public int getDimension() {
		return super.getDimension();
	}
	
	@Override
	public int getDimensionCount() {
		if (function[0].getDimensionCount() > 1) {
			return function[0].getDimensionCount() + 1;
		}
		if (function[0].getLength() > 1) {
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

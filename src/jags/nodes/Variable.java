package jags.nodes;

import java.util.Arrays;

import beast.core.*;
import beast.core.parameter.RealParameter;

@Description("Random variable in graphical model")
public class Variable extends RealParameter implements JFunction {
	JFunction fun;
	
	public Variable(@Param(name="id",description="identifier for this variable") String id, 
			@Param(name="fun", description="function that determines values of the Variable") JFunction fun) {
		super(funToDouble(fun));
		this.fun = fun;
		setID(id);
	}

	public Variable(String id, JFunction f, JFunction dimensions) {
		super(toDouble(dimensions));
		setMinorDimension((int) dimensions.getArrayValue(0)); 
		setID(id);
	}

	private static Double[] toDouble(JFunction dimensions) {
		int k = 1;
		for (int i = 0; i < dimensions.getDimension(); i++) {
			k *= dimensions.getArrayValue(i);
		}
		return new Double[k];
	}

	private static Double[] funToDouble(JFunction fun) {
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

	@Override
	public int getDimensionCount() {
		return fun.getDimensionCount();
	}

	@Override
	public int getDimension(int dim) {
		return fun.getDimension(dim);
	}

	public void setValue(JFunction range, JFunction f) {
		int k = (int) range.getArrayValue(0) - 1;
		for (int i = 1; i < range.getDimension(); i++) {
			k = k * getDimension(i) + (int) range.getArrayValue(i) - 1;
		}
		setValue(k, f.getArrayValue());
	}
	
	@Override
	public void setValue(int param, Double value) {
		values[param] = value;		
		super.setValue(param, value);
	}
}

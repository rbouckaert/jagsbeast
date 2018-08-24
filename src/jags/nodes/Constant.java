package jags.nodes;

import java.util.*;

import beast.core.BEASTObject;
import beast.core.Description;
import beast.core.Function;
import beast.core.Param;


@Description("Constant in graphical model")
public class Constant extends BEASTObject implements Function {
	double [] values;

	
	public static Constant createConstant(double [] value) {
		// TODO: get from constantCache
		Constant c = new Constant(value);
		return c;
	}
	
	public Constant(@Param(name="value",description="one or more dimensional value") double [] value) {
		this.values = value;
	}
	
	@Override
	public void initAndValidate() {
	}

	@Override
	public int getDimension() {
		if (values == null) {
			return 0;
		}
		return values.length;
	}

	@Override
	public double getArrayValue() {
		if (values == null) {
			return Double.NaN;
		}
		return values[0];
	}

	@Override
	public double getArrayValue(int dim) {
		if (values == null) {
			return Double.NaN;
		}
		return values[dim];
	}

	
	@Override
	public String toString() {		
		if (values.length == 1) {
			return values[0] + "";
		}
		return Arrays.toString(values);
	}
	
}

package jags.nodes;

import java.util.*;

import beast.core.BEASTObject;
import beast.core.Description;
import beast.core.Function;
import beast.core.Param;


@Description("Constant in graphical model")
public class Constant extends BEASTObject implements Function {
	double [] value;

	
	public static Constant createConstant(double [] value) {
		// TODO: get from constantCache
		Constant c = new Constant(value);
		return c;
	}
	
	public Constant(@Param(name="value",description="one or more dimensional value") double [] value) {
		this.value = value;
	}
	
	@Override
	public void initAndValidate() {
	}

	@Override
	public int getDimension() {
		if (value == null) {
			return 0;
		}
		return value.length;
	}

	@Override
	public double getArrayValue() {
		if (value == null) {
			return Double.NaN;
		}
		return value[0];
	}

	@Override
	public double getArrayValue(int dim) {
		if (value == null) {
			return Double.NaN;
		}
		return value[dim];
	}

	
	@Override
	public String toString() {		
		if (value.length == 1) {
			return value[0] + "";
		}
		return Arrays.toString(value);
	}
	
}

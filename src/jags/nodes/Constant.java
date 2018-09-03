package jags.nodes;

import java.util.*;

import beast.core.BEASTObject;
import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;


@Description("Constant in graphical model")
public class Constant extends BEASTObject implements JFunction {
	double [] values;
	int [] dimensions;

	
	public static Constant createConstant(double [] value) {
		// TODO: get from constantCache
		Constant c = new Constant(value);
		return c;
	}
	
	public Constant(@Param(name="value",description="one or more dimensional value") double [] value) {
		this.values = value;
		this.dimensions = new int [1];
		this.dimensions[0] = values.length;
	}
	
	public Constant(@Param(name="value",description="one or more dimensional value") double [] value, JFunction dimensions) {
		this.values = value;		
		this.dimensions = new int [dimensions.getLength()];
		for (int i = 0; i < this.dimensions.length; i++) {
			this.dimensions[i] = (int) dimensions.getArrayValue(i);
		}
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

	@Override
	public int getDimensionCount() {
		return dimensions.length;
	}

	@Override
	public int getDimension(int dim) {
		if (dim > dimensions.length) {
			return 0;
		}
		return dimensions[dim];
	}
	
}

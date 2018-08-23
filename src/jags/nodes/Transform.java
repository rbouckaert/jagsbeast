package jags.nodes;

import beast.core.CalculationNode;
import beast.core.Description;
import beast.core.Function;

@Description("Functional transformation in graphical model")
public abstract class Transform extends CalculationNode implements Function {
	double [] value;
	double [] storedvalue;
	boolean isUpToDate;
	
	@Override
	public void initAndValidate() {
		isUpToDate = false;
	}
	
	/** perform the actual transformation, and store results in the value array **/
	abstract protected void doTransform();
	
	protected void calc() {
		doTransform();
		isUpToDate = true;
	}
	
	// Function implementation
	@Override
	public int getDimension() {
		if (!isUpToDate) {
			calc();
		}
		if (value == null) {
			return 0;
		}
		return value.length;
	}

	@Override
	public double getArrayValue() {
		if (!isUpToDate) {
			calc();
		}
		if (value == null) {
			return Double.NaN;
		}
		return value[0];
	}

	@Override
	public double getArrayValue(int dim) {
		if (!isUpToDate) {
			calc();
		}
		if (value == null) {
			return Double.NaN;
		}
		return value[dim];
	}

	// CalculationNode implementation
	@Override
	protected void store() {
		if (storedvalue == null || storedvalue.length != value.length) {
			storedvalue = new double[value.length];
		}
		System.arraycopy(value, 0, storedvalue, 0, value.length);
		super.store();
	}
	
	@Override
	protected void restore() {
		double [] tmp = value;
		value = storedvalue;
		storedvalue = tmp;
		super.restore();
	}
	
	@Override
	protected boolean requiresRecalculation() {
		isUpToDate = false;
		return true;
	}

}

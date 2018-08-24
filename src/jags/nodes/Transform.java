package jags.nodes;

import beast.core.CalculationNode;
import beast.core.Description;
import beast.core.Function;

@Description("Functional transformation in graphical model")
public abstract class Transform extends CalculationNode implements Function {
	protected double [] values;
	protected double [] storedvalues;
	private boolean isUpToDate;
	
	public Transform() {
		isUpToDate = false;
	}
	
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
		if (values == null) {
			return 0;
		}
		return values.length;
	}

	@Override
	public double getArrayValue() {
		if (!isUpToDate) {
			calc();
		}
		if (values == null) {
			return Double.NaN;
		}
		return values[0];
	}

	@Override
	public double getArrayValue(int dim) {
		if (!isUpToDate) {
			calc();
		}
		if (values == null) {
			return Double.NaN;
		}
		return values[dim];
	}

	// CalculationNode implementation
	@Override
	protected void store() {
		if (storedvalues == null || storedvalues.length != values.length) {
			storedvalues = new double[values.length];
		}
		System.arraycopy(values, 0, storedvalues, 0, values.length);
		super.store();
	}
	
	@Override
	protected void restore() {
		double [] tmp = values;
		values = storedvalues;
		storedvalues = tmp;
		super.restore();
	}
	
	@Override
	protected boolean requiresRecalculation() {
		isUpToDate = false;
		return true;
	}

}

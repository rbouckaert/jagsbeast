package jags.nodes;

import beast.core.CalculationNode;
import beast.core.Description;
import jags.nodes.JFunction;

@Description("Functional transformation in graphical model")
public abstract class Transform extends CalculationNode implements JFunction {
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
	
	/** calculate values for this Transform 
	 * Implementations should implement doTransform() 
	 **/
	protected void calc() {
		doTransform();
		isUpToDate = true;
	}
	
	// JFunction implementation
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
	public void store() {
		if (storedvalues == null || storedvalues.length != values.length) {
			storedvalues = new double[values.length];
		}
		System.arraycopy(values, 0, storedvalues, 0, values.length);
		super.store();
	}
	
	@Override
	public void restore() {
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

	@Override
	public int getDimensionCount() {
		return 1;
	}

	@Override
	public int getDimension(int dim) {
		if (dim > 0) {
			return 0;
		}
		return getDimension();
	}

	public double [] getValue() {return values;}
	
	public void setValue(double [] values) {
		this.values = values.clone();
	}
	
	public void resetValue(int dim) {
		isUpToDate = false;
		values = new double[dim];
		storedvalues = new double[dim];
	}
	
}

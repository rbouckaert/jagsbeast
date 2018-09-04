package jags.nodes;

import java.lang.reflect.Array;
import java.util.Arrays;

import beast.core.*;
import beast.core.Input.Validate;
import beast.core.parameter.RealParameter;

@Description("Random variable in graphical model")
public class Variable extends RealParameter implements JFunction {
	JFunction fun;
	
	public Variable() {
	}
	
	public Variable(//@Param(name="id",description="identifier for this variable") String id, 
			@Param(name="fun", description="function that determines values of the Variable") JFunction fun) {
		super(funToDouble(fun));
		this.fun = fun;
		initAndValidate();
		//setID(id);
	}

	public Variable(String id, JFunction f, JFunction dimensions) {
		this(f);
		if (dimensions.getDimension() > 1) {
			setMinorDimension((int) dimensions.getArrayValue(1));
		}
		setID(id);
	}

	@Override
	public void initAndValidate() {
		Double [] valuesString = valuesInput.get().toArray(new Double[]{});
		if (valuesString.length == 0) {
			// already initialised
			return;
		}

        int dimension = Math.max(dimensionInput.get(), valuesString.length);
        dimensionInput.setValue(dimension, this);
        values = new Double[dimension];
        storedValues = new Double[dimension];
        for (int i = 0; i < values.length; i++) {
            values[i] = valuesString[i % valuesString.length];
        }

        m_bIsDirty = new boolean[dimensionInput.get()];

        minorDimension = minorDimensionInput.get();
        if (minorDimension > 0 && dimensionInput.get() % minorDimension > 0) {
            throw new IllegalArgumentException("Dimension must be divisible by stride");
        }
        this.storedValues = values.clone();

        valuesInput.get().clear();
		valuesInput.setRule(Validate.OPTIONAL);
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
		if (fun.getLength() == 1) {
			return getID() + " = " + values[0];
		}
		if (fun.getDimensionCount() == 1) {
			return getID() + " = " + Arrays.toString(values);
		}
		
		StringBuilder b = new StringBuilder();
		b.append(getID() + " = [");
		int k = 0;
		for (int i = 0; i < getDimension(0); i++) {
			b.append('[');
			for (int j = 0; j < getDimension(1) - 1; j++) {
				b.append(values[k++]);
				b.append(", ");
			}
			b.append(values[k++]);			
			b.append("] ");
		}
		b.append("]");
		return b.toString();
	}

	@Override
	public int getDimensionCount() {
		return fun.getDimensionCount();
	}

	@Override
	public int getDimension(int dim) {
		return fun.getDimension(dim);
	}
	
	@Override
	public int getDimension() {
		return fun.getDimension();
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
	
	
	public JFunction getFun() {
		return fun;
	}
	public void setFun(JFunction fun) {
		this.fun = fun;		
		Double [] values = funToDouble(fun);
        this.values = values.clone();
        this.storedValues = values.clone();
        m_fUpper = Double.POSITIVE_INFINITY;
        m_fLower = Double.NEGATIVE_INFINITY;
        m_bIsDirty = new boolean[values.length];
        for (Double value : values) {
        	valuesInput.get().add(value);
        }
	}
}

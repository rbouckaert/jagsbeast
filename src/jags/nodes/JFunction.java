package jags.nodes;

import beast.core.Description;
import jags.nodes.JFunction;

@Description("JFunction that supports higher dimensions")
public interface JFunction extends beast.core.Function {
	// get number of dimensions
	int getDimensionCount();
	
	// get range of particular dimension
	int getDimension(int dim);
	
	// access functions
	
	/** @return matrix value at row i, column j **/
	default double matrixValue(int i, int j) {
		return getArrayValue(i * getDimension(0) + j);
	}

	/** @return cube value at row i, column j, depth k **/
	default double cubeValue(int i, int j, int k) {
		return getArrayValue((i * getDimension(0) + j) * getDimension(1) + k);
	}

	/** generic data cube access function
	 * @return value at location indicates by indices in index **/
	default double value(int [] index) {
		int k = index[0];
		for (int i = 0; i < index.length; i++) {
			k  = k * getDimension(i-1) + index[i];
		}
		return getArrayValue(k);		
	}
}

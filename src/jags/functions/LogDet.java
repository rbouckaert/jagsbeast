package jags.functions;

import beast.core.Description;
import beast.core.Param;
import beast.math.matrixalgebra.IllegalDimension;
import beast.math.matrixalgebra.Matrix;
import jags.nodes.JFunction;
import jags.nodes.Transform;

@Description("Calculates log determinant of a matrix")
public class LogDet extends Transform {
	JFunction a;

	public LogDet(@Param(name="a", description="matrix to be inverted") JFunction a) {
		if (a.getDimensionCount() != 2) {
			throw new IllegalArgumentException("Expected a matrix (JFunction with #dims=2)");
		}
		this.a = a;
		values = new double[1];	
		storedvalues = new double[1];	
	}
	
	@Override
	protected void doTransform() {
		int n = a.getDimension(0);
		int m = a.getDimension(1);
		double [][] in = new double[n][m];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				in[i][j] = a.matrixValue(i, j);
			}
		}
		Matrix A = new Matrix(in);
		try {
			values[0] = Math.log(A.determinant());
		} catch (IllegalDimension e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

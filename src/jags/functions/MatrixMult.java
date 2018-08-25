package jags.functions;

import beast.core.Description;
import beast.core.Param;
import beast.math.matrixalgebra.IllegalDimension;
import beast.math.matrixalgebra.Matrix;
import jags.nodes.JFunction;
import jags.nodes.Transform;

@Description("performs multiplications of two matrices")
public class MatrixMult extends Transform {
	JFunction a;
	JFunction b;

	public MatrixMult(@Param(name="a", description="matrix to be multiplied") JFunction a,
			@Param(name="b", description="matrix to be multiplied") JFunction b) {
		if (a.getDimensionCount() != 2) {
			throw new IllegalArgumentException("Expected a matrix (JFunction with #dims=2)");
		}
		if (b.getDimensionCount() != 2) {
			throw new IllegalArgumentException("Expected a matrix (JFunction with #dims=2)");
		}
		if (a.getDimension(1) != b.getDimension(0)) {
			throw new IllegalArgumentException("Incompatible matrices: cannot multiply because a.getDimension(1) != b.getDimension(0)");
		}
		this.a = a;
		this.b = b;
		values = new double[a.getDimension()];	
		storedvalues = new double[a.getDimension()];	
	}
	
	@Override
	protected void doTransform() {
		int n = a.getDimension(0);
		int m = a.getDimension(1);
		int k = b.getDimension(1);
		double [][] in = new double[n][m];		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				in[i][j] = a.matrixValue(i, j);
			}
		}

		double [][] in2 = new double[m][k];		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < k; j++) {
				in2[i][j] = b.matrixValue(i, j);
			}
		}
		
		Matrix A = new Matrix(in);
		Matrix B = new Matrix(in2);
		double[][] Inverse;
		try {
			Inverse = A.product(B).toComponents();
			for (int i = 0; i < m; i++) {
				System.arraycopy(Inverse[i], 0, values, i*n, n);
			}
		} catch (IllegalDimension e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getDimensionCount() {
		return 2;
	}

	@Override
	public int getDimension(int dim) {
		switch (dim) {
		case  0: return	a.getDimension(0);
		case  1: return	b.getDimension(1);
		} 
		return 0;
	}
}

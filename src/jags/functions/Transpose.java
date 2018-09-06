package jags.functions;

import beast.core.Description;
import beast.core.Param;
//import beast.math.matrixalgebra.Matrix;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import jags.nodes.JFunction;
import jags.nodes.Transform;

@Description("performs a matrix transpose")
public class Transpose extends Transform {
    public Transpose(){}

	JFunction a;

	public Transpose(@Param(name="a", description="matrix to be transposed") JFunction a) {
		if (a.getDimensionCount() != 2) {
			throw new IllegalArgumentException("Expected a matrix (JFunction with #dims=2)");
		}
		this.a = a;
		values = new double[a.getDimension()];	
		storedvalues = new double[a.getDimension()];	
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
//		Matrix A = new Matrix(in);
//		double [][] Inverse = A.transpose().toComponents();
//		for (int i = 0; i < m; i++) {
//			System.arraycopy(Inverse[i], 0, values, i*n, n);
//		}
		
		DoubleMatrix2D A = DoubleFactory2D.dense.make(in);
		
		Algebra a = new Algebra();
		double [][] transpose = a.transpose(A).toArray();
		for (int i = 0; i < m; i++) {
			System.arraycopy(transpose[i], 0, values, i*n, n);
		}
	}

	@Override
	public int getDimensionCount() {
		return 2;
	}

	@Override
	public int getDimension(int dim) {
		switch (dim) {
		case  0: return	a.getDimension(1);
		case  1: return	a.getDimension(0);
		} 
		return 0;
	}
	
	public JFunction getA() {
		return a;
	}
	public void setA(JFunction a) {
		this.a = a;
	}
}

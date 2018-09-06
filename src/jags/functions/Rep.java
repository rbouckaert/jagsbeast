package jags.functions;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;
import jags.nodes.Transform;

@Description("Replicates a vector multiple times")
public class Rep extends Transform {
    public Rep(){}

	JFunction x;
	JFunction t;
	
	public Rep(@Param(name="x", description="elements of v are replicaeted") JFunction x, 
			@Param(name="t", description="number of replicants, must be integer") JFunction t) {
		this.x = x;
		this.t = t;
		values = new double[(int)(x.getDimension() * t.getArrayValue())];
	}

	@Override
	protected void doTransform() {
		int n = (int) t.getArrayValue();
		for (int i = 0; i < n; i++) {
			double [] v = x.getDoubleValues();
			System.arraycopy(v, 0, values, i * v.length, v.length);
		}		
	}

	public JFunction getX() {
		return x;
	}

	public void setX(JFunction x) {
		this.x = x;
	}

	public JFunction getT() {
		return t;
	}

	public void setT(JFunction t) {
		this.t = t;
	}

	
	
}

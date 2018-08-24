package jags.functions;

import beast.core.*;
import jags.nodes.Transform;

@Description("Base class for deterministic function with two arguments")
abstract public class BivariableFunction extends Transform {
	protected Function x;
	protected Function y;
	public enum Mode {mode11,modeN1, mode1N, modeNN}
	protected Mode mode;
	
	protected BivariableFunction(
			@Param(name="x", description="argument of cosine function") Function x,
			@Param(name="y", description="argument of cosine function") Function y
			) {
		super();
		this.x = x;
		this.y = y;
		int dim = dimensionCheck();
		values = new double[dim];
		storedvalues = new double[dim];
	}
	
	@Override
	protected void doTransform() {
		switch (mode) {
		case mode11:
			values[0] = doTransform(x.getArrayValue(0), y.getArrayValue(0));
			break;
		case mode1N:
			final double vx = x.getArrayValue(0);
			for (int i = 0; i < x.getDimension(); i++) {
				values[i] = doTransform(vx, y.getArrayValue(i)); 
			}
			break;
		case modeN1:
			final double vy = y.getArrayValue(0);
			for (int i = 0; i < x.getDimension(); i++) {
				values[i] = doTransform(x.getArrayValue(i), vy); 
			}
			break;
		case modeNN:
			for (int i = 0; i < x.getDimension(); i++) {
				values[i] = doTransform(x.getArrayValue(i), y.getArrayValue(i)); 
			}
		}
	}
	
	abstract protected double doTransform(double x, double y);
	
	protected int dimensionCheck() {
		if (x.getDimension() == 1 && y.getDimension() == 1) {
			mode = Mode.mode11; 
			return 1;
		}
		if (x.getDimension() == 1) {
			mode = Mode.mode1N; 
			return y.getDimension();
		}
		if (y.getDimension() == 1) {
			mode = Mode.modeN1; 
			return x.getDimension();
		}
		if (x.getDimension() == y.getDimension()) {
			mode = Mode.modeNN; 
			return x.getDimension();
		}
			
		throw new IllegalArgumentException("dimensions of x and y should be the same");
	}
		
}

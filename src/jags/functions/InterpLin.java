package jags.functions;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;
import jags.nodes.Transform;

@Description("Linear interpolation function (from bugs)")
public class InterpLin extends Transform {
	JFunction e, v1, v2;

	public InterpLin(@Param(name="e", description="scalar") JFunction e,
			@Param(name="v1", description="vector") JFunction v1,
			@Param(name="v2", description="vector conforming with v1") JFunction v2
			) {
		this.e = e;
		this.v1 = v1;
		this.v2 = v2;
		values = new double[1];
		storedvalues = new double[1];
	}

		@Override
		protected void doTransform() {
	        int N = v1.getDimension();
	        double xnew = e.getArrayValue();

	        if (xnew < v1.getArrayValue(0))
	        	values[0] = v2.getArrayValue(0);
	        else if (xnew >= v1.getArrayValue(N-1))
	        	values[0] = v2.getArrayValue(N-1);
	        else {
	        	int i = 0;
	            for (; i < N-1; ++i) {
	                if (xnew >= v1.getArrayValue(i) && xnew < v1.getArrayValue(i+1)) {
	                    break;
	                }
	            }
	            if (i == N-1) {
	                /* This shouldn't happen, but we must guard against an
	                   attempt to access an illegal element of x or y */
	                values[0] = Double.NaN;
	            }
	            else {
	            	values[0] = v2.getArrayValue(i) + 
	                    (xnew - v1.getArrayValue(i)) * (v2.getArrayValue(i+1) - 
	                    		v2.getArrayValue(i)) / (v1.getArrayValue(i+1) - v1.getArrayValue(i));
	            }
	        }
		}

	}

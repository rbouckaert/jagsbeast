package jags.distributions;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.Distribution;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;



@Description("Uniform distribution over a given interval (including lower and upper values)")
public class Uniform extends JAGSDistribution {

    UniformImpl distr = new UniformImpl();

    double _lower, _upper, density;
    JFunction upper, lower;

    private boolean infiniteSupport;
    
    public Uniform(@Param(name="lower", description="lower bound on the interval, default 0", optional=true) JFunction lower,
    		@Param(name="upper", description="upper bound on the interval, default 1", optional=true) JFunction upper) {
    	this.upper = upper;
    	this.lower = lower;
    }

    @Override
    public void initAndValidate() {
        _lower = lower == null ? 0 : lower.getArrayValue();
        _upper = upper == null ? 0 : upper.getArrayValue();
        if (_lower >= _upper) {
            throw new IllegalArgumentException("Upper value should be higher than lower value");
        }
        distr.setBounds(_lower, _upper);
        infiniteSupport = Double.isInfinite(_lower) || Double.isInfinite(_upper);
        if (infiniteSupport) {
            density = 1.0;
        } else {
            density = 1.0 / (_upper - _lower);
        }
    }


    class UniformImpl implements ContinuousDistribution {
        private double lower;
        private double upper;

        public void setBounds(final double lower, final double upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public double cumulativeProbability(double x) throws MathException {
            x = Math.max(x, lower);
            return (x - lower) / (upper - lower);
        }

        @Override
        public double cumulativeProbability(double x0, double x1) throws MathException {
            x0 = Math.max(x0, lower);
            x1 = Math.min(x1, upper);
            if (x1 < lower || x1 > upper) {
                throw new RuntimeException("Value x (" + x1 + ") out of bounds (" + lower + "," + upper + ").");
            }
            return (x1 - x0) / (upper - lower);
        }

        @Override
        public double inverseCumulativeProbability(final double p) throws MathException {
            if (p < 0.0 || p > 1.0) {
                throw new RuntimeException("inverseCumulativeProbability::argument out of range [0...1]");
            }
            if( p == 0 ) {
                // works even when one bound is infinite
                return _lower;
            }
            if( p == 1 ) {
                // works even when one bound is infinite
                return _upper;
            }
            if( infiniteSupport ) {
                 throw new RuntimeException("Inverse Cumulative Probability for 0 < p < 1 and infinite support") ;
            }
            return (upper - lower) * p + lower;
        }

        @Override
        public double density(final double x) {
            if (x >= lower && x <= upper) {
                return density;
            } else {
                return 0;
            }
        }

        @Override
        public double logDensity(final double x) {
            return Math.log(density(x));
        }
    } // class UniformImpl


    @Override
    public Distribution getDistribution() {
        return distr;
    }

    @Override
    public double density(final double x) {
        if (x >= _lower && x <= _upper) {
            // (BUG)?? why does this not return this.density??? (JH)
            return 1;
        } else {
            return 0;
        }
    }
    
    @Override
    protected double getMeanWithoutOffset() {
    	if (Double.isInfinite(_lower) || Double.isInfinite(_upper)) {
    		return Double.NaN;
    	}
    	return (_upper + _lower)/2;
    }

	@Override
	public String getName() {
		return "duniform";
	}

	@Override
	public int getParameterCount() {
		return 2;
	}

	public JFunction getUpper() {
		return upper;
	}

	public void setUpper(JFunction upper) {
		this.upper = upper;
		initAndValidate();
	}

	public JFunction getLower() {
		return lower;
	}

	public void setLower(JFunction lower) {
		this.lower = lower;
		initAndValidate();
	}
	
	@Override
	protected boolean requiresRecalculation() {
		initAndValidate();
		return super.requiresRecalculation();
	}
	
}

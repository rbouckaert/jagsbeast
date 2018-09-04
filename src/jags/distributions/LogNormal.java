package jags.distributions;


import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.Distribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;
import beast.core.parameter.RealParameter;



/**
 * @author Alexei Drummond
 */
@Description("A log-normal distribution with mean and variance parameters.")
public class LogNormal extends JAGSDistribution {
//    final public Input<RealParameter> MParameterInput = new Input<>("M", "M parameter of lognormal distribution. Equal to the mean of the log-transformed distribution.");
//    final public Input<RealParameter> SParameterInput = new Input<>("S", "S parameter of lognormal distribution. Equal to the standard deviation of the log-transformed distribution.");
//    final public Input<Boolean> hasMeanInRealSpaceInput = new Input<>("meanInRealSpace", "Whether the M parameter is in real space, or in log-transformed space. Default false = log-transformed.", false);

    JFunction M, S;
    
    public LogNormal(@Param(name="mu", description="M parameter of lognormal distribution. Equal to the mean of the log-transformed distribution.") JFunction M,
    		@Param(name="sigma", description="M parameter of lognormal distribution. Equal to the mean of the log-transformed distribution.") JFunction S) {
    	this.M = M;
    	this.S = S;
    }
    		
    
    boolean hasMeanInRealSpace;
    LogNormalImpl dist = new LogNormalImpl(0, 1);

    @Override
	public void initAndValidate() {
        hasMeanInRealSpace = false; //hasMeanInRealSpaceInput.get();
        if (this.M != null && this.M instanceof RealParameter) {
            if (((RealParameter)this.M).getLower() == null) {
            	((RealParameter)this.M).setLower(Double.NEGATIVE_INFINITY);
            }
            if (((RealParameter)this.M).getUpper() == null) {
            	((RealParameter)this.M).setUpper(Double.POSITIVE_INFINITY);
            }
        }

        if (this.S != null) {
            if (((RealParameter)this.S).getLower() == null) {
            	((RealParameter)this.S).setLower(0.0);
            }
            if (((RealParameter)this.S).getUpper() == null) {
            	((RealParameter)this.S).setUpper(Double.POSITIVE_INFINITY);
            }
        }
        refresh();
    }

    /**
     * make sure internal state is up to date *
     */
    void refresh() {
        double mean;
        double sigma;
        if (this.S == null) {
            sigma = 1;
        } else {
            sigma = this.S.getArrayValue();
        }
        if (this.M == null) {
            mean = 0;
        } else {
            mean = this.M.getArrayValue();
        }
        if (hasMeanInRealSpace) {
            mean = Math.log(mean) - (0.5 * sigma * sigma);
        }
        dist.setMeanAndStdDev(mean, sigma);
    }

    @Override
    public Distribution getDistribution() {
        refresh();
        return dist;
    }

    public class LogNormalImpl implements ContinuousDistribution {
        double m_fMean;
        double m_fStdDev;
        NormalDistributionImpl m_normal = new NormalDistributionImpl(0, 1);

        public LogNormalImpl(double mean, double stdDev) {
            setMeanAndStdDev(mean, stdDev);
        }

        @SuppressWarnings("deprecation")
		void setMeanAndStdDev(double mean, double stdDev) {
            m_fMean = mean;
            m_fStdDev = stdDev;
            m_normal.setMean(mean);
            m_normal.setStandardDeviation(stdDev);
        }

        @Override
        public double cumulativeProbability(double x) throws MathException {
            return m_normal.cumulativeProbability(Math.log(x));
        }

        @Override
        public double cumulativeProbability(double x0, double x1) throws MathException {
            return cumulativeProbability(x1) - cumulativeProbability(x0);
        }

        @Override
        public double inverseCumulativeProbability(double p) throws MathException {
            return Math.exp(m_normal.inverseCumulativeProbability(p));
        }

        @Override
        public double density(double x) {
            if( x <= 0 ) {
                return 0;
            }
            return m_normal.density(Math.log(x)) / x;
        }

        @Override
        public double logDensity(double x) {
            if( x <= 0 ) {
                return  Double.NEGATIVE_INFINITY;
            }
            return m_normal.logDensity(Math.log(x)) - Math.log(x);
        }
    } // class LogNormalImpl

    @Override
    protected double getMeanWithoutOffset() {
    	if (hasMeanInRealSpace) {
    		if (this.M != null) {
    			return this.M.getArrayValue();
    		} else {
    			return 0.0;
    		}
    	} else {
    		double s = this.S.getArrayValue();
    		double m = this.M.getArrayValue();
    		return Math.exp(m + s * s/2.0);
    		//throw new RuntimeException("Not implemented yet");
    	}
    }

	@Override
	public String getName() {
		return "dlnorm";
	}

	@Override
	public int getParameterCount() {
		return 2;
	}

	
	public JFunction getMu() {
		return M;
	}
	public void setMu(JFunction m) {
		M = m;
	}
	public JFunction getSigma() {
		return S;
	}
	public void setSigma(JFunction s) {
		S = s;
	}
}

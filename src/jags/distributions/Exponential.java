package jags.distributions;

import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.ExponentialDistributionImpl;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;
import beast.core.util.Log;


@Description("Exponential distribution.  f(x;\\lambda) = 1/\\lambda e^{-x/\\lambda}, if x >= 0 " +
        "If the input x is a multidimensional parameter, each of the dimensions is considered as a " +
        "separate independent component.")
public class Exponential extends JAGSDistribution {
    
    JFunction lambda;
    
    public Exponential(@Param(name="mean", description="mean of the distribution") JFunction lambda) {
    	this.lambda = lambda;
    }

    static org.apache.commons.math.distribution.ExponentialDistribution m_dist = new ExponentialDistributionImpl(1);

    @Override
    public void initAndValidate() {
        refresh();
    }

    /**
     * make sure internal state is up to date *
     */
    @SuppressWarnings("deprecation")
	void refresh() {
        double lambda;
        if (this.lambda == null) {
            lambda = 1;
        } else {
            lambda = this.lambda.getArrayValue();
            if (lambda < 0) {
                Log.err.println("Exponential::Lambda should be positive not " + lambda + ". Assigning default value.");
                lambda = 1;
            }
        }
        m_dist.setMean(lambda);
    }

    @Override
    public ContinuousDistribution getDistribution() {
        refresh();
        return m_dist;
    }
    
    @Override
    protected double getMeanWithoutOffset() {
    	return m_dist.getMean();
    }

	@Override
	public String getName() {
		return "dexp";
	}
	
	@Override
	public int getParameterCount() {
		return 1;
	}

} // class Exponential

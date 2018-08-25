package jags.distributions;

import org.apache.commons.math.distribution.BetaDistributionImpl;
import org.apache.commons.math.distribution.ContinuousDistribution;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Input;
import beast.core.Param;
import beast.core.parameter.RealParameter;



@Description("Beta distribution, used as prior.  p(x;alpha,beta) = \frac{x^{alpha-1}(1-x)^{beta-1}} {B(alpha,beta)} " +
        "where B() is the beta function. " +
        "If the input x is a multidimensional parameter, each of the dimensions is considered as a " +
        "separate independent component.")
public class Beta extends JAGSDistribution {

	JFunction alpha, beta;

    static org.apache.commons.math.distribution.BetaDistribution m_dist = new BetaDistributionImpl(1, 1);

    
    public Beta(@Param(name="alpha", description="first shape parameter, defaults to 1") JFunction alpha,
    		@Param(name="beat", description="the other shape parameter, defaults to 1") JFunction beta
    		) {
    	this.alpha = alpha;
    	this.beta = beta;
    }
    @Override
    public void initAndValidate() {
        refresh();
    }

    /**
     * make sure internal state is up to date *
     */
    @SuppressWarnings("deprecation")
	void refresh() {
        double alpha;
        double beta;
        if (this.alpha == null) {
            alpha = 1;
        } else {
            alpha = this.alpha.getArrayValue();
        }
        if (this.beta == null) {
            beta = 1;
        } else {
            beta = this.beta.getArrayValue();
        }
        m_dist.setAlpha(alpha);
        m_dist.setBeta(beta);
    }

    @Override
    public ContinuousDistribution getDistribution() {
        refresh();
        return m_dist;
    }

    @Override
    protected double getMeanWithoutOffset() {
    	return m_dist.getAlpha() / (m_dist.getAlpha() + m_dist.getBeta());
    }

    @Override
	public String getName() {
		return "dbeta";
	}
    
	@Override
	public int getParameterCount() {
		return 2;
	}
	
} // class Beta

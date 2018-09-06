package jags.distributions;


import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.WeibullDistributionImpl;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;



@Description("Weibull distribution")
public class Weibull extends JAGSDistribution {
    public Weibull(){}


	JFunction alpha, beta;

    static org.apache.commons.math.distribution.WeibullDistribution m_dist = new WeibullDistributionImpl(1, 1);

    
    public Weibull(@Param(name="alpha", description="shape parameter, defaults to 1") JFunction alpha,
    		@Param(name="beta", description="scale parameter, defaults to 1") JFunction beta
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
        m_dist.setScale(alpha);
        m_dist.setShape(beta);
    }

    @Override
    public ContinuousDistribution getDistribution() {
        refresh();
        return m_dist;
    }

    @Override
    protected double getMeanWithoutOffset() {
    	double scale = alpha == null ? 1 : alpha.getArrayValue();
    	double shape = beta == null ? 1 : beta.getArrayValue();
    	return scale * Math.exp(org.apache.commons.math.special.Gamma.logGamma(1 + 1.0/shape));
    }

    @Override
	public String getName() {
		return "dweibull";
	}
    
	@Override
	public int getParameterCount() {
		return 2;
	}

	
	public JFunction getAlpha() {
		return alpha;
	}
	public void setAlpha(JFunction alpha) {
		this.alpha = alpha;
	}
	public JFunction getBeta() {
		return beta;
	}
	public void setBeta(JFunction beta) {
		this.beta = beta;
	}
} // class Weibull



package jags.distributions;



import org.apache.commons.math.distribution.PascalDistributionImpl;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;



@Description("Negative binomial distribution, of the number of successes in a sequence of independent and identically "
		+ "distributed Bernoulli trials before a specified (non-random) number of failures occurs. "
		+ "Also known as Pascal distribution.")
public class NegativeBinomial extends JAGSDistribution {
    public NegativeBinomial(){}


	JFunction r, p;

    static org.apache.commons.math.distribution.PascalDistribution m_dist = new PascalDistributionImpl(1, 1);

    
    public NegativeBinomial(@Param(name="r", description="the number of succsses before failuer occurs") JFunction r,
    		@Param(name="p", description="the probability of success") JFunction p
    		) {
    	this.r = r;
    	this.p = p;
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
        double r = this.r.getArrayValue();
        double p = this.p.getArrayValue();
        m_dist.setNumberOfSuccesses((int) r);
        m_dist.setProbabilityOfSuccess(p);
    }

    @Override
    public org.apache.commons.math.distribution.Distribution getDistribution() {
        refresh();
        return m_dist;
    }

    @Override
    protected double getMeanWithoutOffset() {
    	double _p = p.getArrayValue();
    	double _r = r.getArrayValue();
    	return _p * _r / (1 - _p);
    }

    @Override
	public String getName() {
		return "dnbinom";
	}
    
	@Override
	public int getParameterCount() {
		return 2;
	}

	
	public JFunction getR() {
		return r;
	}
	public void setR(JFunction r) {
		this.r = r;
	}
	public JFunction getP() {
		return p;
	}
	public void setP(JFunction p) {
		this.p = p;
	}
} // class NegativeBinomial

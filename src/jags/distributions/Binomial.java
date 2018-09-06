
package jags.distributions;



import org.apache.commons.math.distribution.BinomialDistributionImpl;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;



@Description("Binomial distribution of the number of successes in a sequence of n independent experiments "
		+ "with probability p of success")
public class Binomial extends JAGSDistribution {
    public Binomial(){}


	JFunction n, p;

    static org.apache.commons.math.distribution.BinomialDistribution m_dist = new BinomialDistributionImpl(1, 1);

    
    public Binomial(@Param(name="n", description="the number of trials") JFunction n,
    		@Param(name="p", description="the probability of success") JFunction p
    		) {
    	this.n = n;
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
        double n = this.n.getArrayValue();
        double p = this.p.getArrayValue();
        m_dist.setNumberOfTrials((int) n);
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
    	double _n = n.getArrayValue();
    	return _p * _n;
    }

    @Override
	public String getName() {
		return "dbinom";
	}
    
	@Override
	public int getParameterCount() {
		return 2;
	}

	
	public JFunction getN() {
		return n;
	}
	public void setN(JFunction n) {
		this.n = n;
	}
	public JFunction getP() {
		return p;
	}
	public void setP(JFunction p) {
		this.p = p;
	}
} // class Binomial

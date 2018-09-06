
package jags.distributions;



import org.apache.commons.math.distribution.HypergeometricDistributionImpl;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;



@Description("Hypergeometric distribution that describes the probability of k successes in n draws, "
		+ "without replacement, from a finite population of size N that contains exactly K objects "
		+ "with that feature.")
public class Hypergeometric extends JAGSDistribution {
    public Hypergeometric(){}


	JFunction popSize, sampleSize, successCount;

    static org.apache.commons.math.distribution.HypergeometricDistribution m_dist = new HypergeometricDistributionImpl(1, 1, 1);

    
    public Hypergeometric(@Param(name="popSize", description="the numerator degrees of freedom") JFunction popSize,
    		@Param(name="successCount", description="the denominator degrees of freedom") JFunction successCount,
    		@Param(name="sampleSize", description="the denominator degrees of freedom") JFunction sampleSize
    		) {
    	this.popSize = popSize;
    	this.sampleSize = sampleSize;
    	this.successCount = successCount;
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
        double popSize = this.popSize.getArrayValue();
        double sampleSize = this.sampleSize.getArrayValue();
        double successCount = this.successCount.getArrayValue();
        m_dist.setNumberOfSuccesses((int) successCount);
        m_dist.setPopulationSize((int) popSize);
        m_dist.setSampleSize((int) sampleSize);
    }

    @Override
    public org.apache.commons.math.distribution.Distribution getDistribution() {
        refresh();
        return m_dist;
    }

    @Override
    protected double getMeanWithoutOffset() {
    	// TODO: implement
    	return Double.NaN;
    }

    @Override
	public String getName() {
		return "dhypergeometric";
	}
    
	@Override
	public int getParameterCount() {
		return 3;
	}

	
	public JFunction getPopSize() {
		return popSize;
	}
	public void setPopSize(JFunction popSize) {
		this.popSize = popSize;
	}
	public JFunction getSampleSize() {
		return sampleSize;
	}
	public void setSampleSize(JFunction sampleSize) {
		this.sampleSize = sampleSize;
	}
	public JFunction getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(JFunction successCount) {
		this.successCount = successCount;
	}
} // class F

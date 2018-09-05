
package jags.distributions;


import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.FDistributionImpl;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;



@Description("F distribution, also known as Snedecor's F distribution or the Fisher–Snedecor distribution.")
public class F extends JAGSDistribution {

	JFunction numdf, denomdf;

    static org.apache.commons.math.distribution.FDistribution m_dist = new FDistributionImpl(1, 1);

    
    public F(@Param(name="numdf", description="the numerator degrees of freedom") JFunction numdf,
    		@Param(name="denomdf", description="the denominator degrees of freedom") JFunction denomdf
    		) {
    	this.numdf = numdf;
    	this.denomdf = denomdf;
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
        double numdf;
        double denomdf;
        if (this.numdf == null) {
            numdf = 1;
        } else {
            numdf = this.numdf.getArrayValue();
        }
        if (this.denomdf == null) {
            denomdf = 1;
        } else {
            denomdf = this.denomdf.getArrayValue();
        }
        m_dist.setNumeratorDegreesOfFreedom(numdf);
        m_dist.setDenominatorDegreesOfFreedom(denomdf);
    }

    @Override
    public ContinuousDistribution getDistribution() {
        refresh();
        return m_dist;
    }

    @Override
    protected double getMeanWithoutOffset() {
    	double d2 = denomdf == null ? 1 : denomdf.getArrayValue();
    	return d2 > 2 ? d2/(d2-2) : Double.NaN;
    }

    @Override
	public String getName() {
		return "df";
	}
    
	@Override
	public int getParameterCount() {
		return 2;
	}

	
	public JFunction getNumdf() {
		return numdf;
	}
	public void setNumdf(JFunction numdf) {
		this.numdf = numdf;
	}
	public JFunction getDenomdf() {
		return denomdf;
	}
	public void setDenomdf(JFunction denomdf) {
		this.denomdf = denomdf;
	}
} // class F

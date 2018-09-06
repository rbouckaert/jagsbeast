
package jags.distributions;


import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.TDistributionImpl;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;



@Description("Student's T distribution, a symmetric and bell-shaped distribution, like the normal distribution, but has heavier tails.")
public class StudentT extends JAGSDistribution {
    public StudentT(){}


	JFunction df;

    static org.apache.commons.math.distribution.TDistribution m_dist = new TDistributionImpl(1);

    
    public StudentT(@Param(name="df", description="the numerator degrees of freedom") JFunction df
    		) {
    	this.df = df;
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
        double df;
        if (this.df == null) {
            df = 1;
        } else {
            df = this.df.getArrayValue();
        }
        m_dist.setDegreesOfFreedom(df);
    }

    @Override
    public ContinuousDistribution getDistribution() {
        refresh();
        return m_dist;
    }

    @Override
    protected double getMeanWithoutOffset() {
    	return df.getArrayValue() > 1 ? 0 : Double.NaN;
    }

    @Override
	public String getName() {
		return "dt";
	}
    
	@Override
	public int getParameterCount() {
		return 1;
	}

	
	public JFunction getDf() {
		return df;
	}
	public void setDf(JFunction df) {
		this.df = df;
	}
} // class F

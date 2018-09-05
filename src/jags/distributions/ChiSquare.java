package jags.distributions;



import org.apache.commons.math.distribution.ChiSquaredDistributionImpl;
import org.apache.commons.math.distribution.ContinuousDistribution;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;



@Description("Chi square distribution, f(x; k) = \\frac{1}{2^{k/2}Gamma(k/2)} x^{k/2-1} e^{-x/2} " +
        "If the input x is a multidimensional parameter, each of the dimensions is considered as a " +
        "separate independent component.")
public class ChiSquare extends JAGSDistribution {

    static org.apache.commons.math.distribution.ChiSquaredDistribution m_dist = new ChiSquaredDistributionImpl(1);

    JFunction df;
    
    public ChiSquare(@Param(name="df", description="degrees if freedin, defaults to 1", optional=true) JFunction df) {
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
        int dF;
        if (this.df == null) {
            dF = 1;
        } else {
            dF = (int) this.df.getArrayValue();
            if (dF <= 0) {
                dF = 1;
            }
        }
        m_dist.setDegreesOfFreedom(dF);
    }

    @Override
    public ContinuousDistribution getDistribution() {
        refresh();
        return m_dist;
    }

	@Override
	public String getName() {
		return "chisquare";
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

} // class ChiSquare

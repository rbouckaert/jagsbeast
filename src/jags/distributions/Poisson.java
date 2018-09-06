package jags.distributions;



import org.apache.commons.math.distribution.PoissonDistributionImpl;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;


@Description("Poisson distribution, used as prior  f(k; lambda)=\\frac{lambda^k e^{-lambda}}{k!}  " +
        "If the input x is a multidimensional parameter, each of the dimensions is considered as a " +
        "separate independent component.")
public class Poisson extends JAGSDistribution {

	static org.apache.commons.math.distribution.PoissonDistribution dist = new PoissonDistributionImpl(1);
    JFunction lambda;

    // Must provide empty constructor for construction by XML. Note that this constructor DOES NOT call initAndValidate();
    public Poisson() {
    }

    public Poisson(@Param(name="lambda", description="rate parameter, defaults to 1", optional=true) JFunction lambda) {
    	this.lambda = lambda;
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
        double m_fLambda;
        if (lambda == null) {
            m_fLambda = 1;
        } else {
            m_fLambda = lambda.getArrayValue();
            if (m_fLambda < 0) {
                m_fLambda = 1;
            }
        }
        dist.setMean(m_fLambda);
    }

    @Override
    public org.apache.commons.math.distribution.Distribution getDistribution() {
        refresh();
        return dist;
    }
    
    @Override
    public double getMeanWithoutOffset() {
    	return lambda != null ? lambda.getArrayValue() : 1;
    }

	@Override
	public String getName() {
		return "poisson";
	}

	@Override
	public int getParameterCount() {
		return 1;
	}
    
    public JFunction getLambda() {
		return lambda;
	}

	public void setLambda(JFunction lambda) {
		this.lambda = lambda;
	}

} // class Poisson

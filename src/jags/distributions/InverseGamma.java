package jags.distributions;



import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.Distribution;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;



@Description("Inverse Gamma distribution, used as prior.    for x>0  f(x; alpha, beta) = \frac{beta^alpha}{Gamma(alpha)} (1/x)^{alpha + 1}exp(-beta/x) " +
        "If the input x is a multidimensional parameter, each of the dimensions is considered as a " +
        "separate independent component.")
public class InverseGamma extends JAGSDistribution {
    public InverseGamma(){}


    InverseGammaImpl dist = new InverseGammaImpl(2, 2);
    JFunction alpha, beta;


	public InverseGamma(@Param(name="alpha", description="shape parameter, defaults to 2", optional=true) JFunction alpha,
    		@Param(name="beta", description="scale parameter, defaults to 2", optional=true) JFunction beta) {
    	this.alpha = alpha;
    	this.beta = beta;
    }
    
    @Override
    public void initAndValidate() {
        refresh();
    }

    /**
     * ensure internal state is up to date *
     */
    void refresh() {
        double alpha;
        double beta;
        if (this.alpha == null) {
            alpha = 2;
        } else {
            alpha = this.alpha.getArrayValue();
        }
        if (this.beta == null) {
            beta = 2;
        } else {
            beta = this.beta.getArrayValue();
        }
        dist.setAlphaBeta(alpha, beta);
    }

    @Override
    public Distribution getDistribution() {
        refresh();
        return dist;
    }

    class InverseGammaImpl implements ContinuousDistribution {
        double m_fAlpha;
        double m_fBeta;
        // log of the constant beta^alpha/Gamma(alpha)
        double C;

        InverseGammaImpl(double alpha, double beta) {
            setAlphaBeta(alpha, beta);
        }

        void setAlphaBeta(double alpha, double beta) {
            m_fAlpha = alpha;
            m_fBeta = beta;
            C = m_fAlpha * Math.log(m_fBeta) - org.apache.commons.math.special.Gamma.logGamma(m_fAlpha);
        }

        @Override
        public double cumulativeProbability(double x) throws MathException {
            throw new MathException("Not implemented yet");
        }

        @Override
        public double cumulativeProbability(double x0, double x1) throws MathException {
            throw new MathException("Not implemented yet");
        }

        @Override
        public double inverseCumulativeProbability(double p) throws MathException {
            throw new MathException("Not implemented yet");
        }

        @Override
        public double density(double x) {
            double logP = logDensity(x);
            return Math.exp(logP);
        }

        @Override
        public double logDensity(double x) {
            double logP = -(m_fAlpha + 1.0) * Math.log(x) - (m_fBeta / x) + C;
            return logP;
        }
    } // class OneOnXImpl


    @Override
    public double getMeanWithoutOffset() {
    	return (beta != null ? beta.getArrayValue() : 2.0) / (alpha != null ? (alpha.getArrayValue() - 1.0) : 2.0);
    }

	@Override
	public String getName() {
		return "igamma";
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

} // class InverseGamma

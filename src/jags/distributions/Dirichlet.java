package jags.distributions;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.Distribution;

import beast.core.Description;
import beast.core.Function;
import jags.nodes.JFunction;
import beast.core.Input;
import beast.core.Input.Validate;
import beast.core.Param;
import beast.core.parameter.RealParameter;



@Description("Dirichlet distribution.  p(x_1,...,x_n;alpha_1,...,alpha_n) = 1/B(alpha) prod_{i=1}^K x_i^{alpha_i - 1} " +
        "where B() is the beta function B(alpha) = prod_{i=1}^K Gamma(alpha_i)/ Gamma(sum_{i=1}^K alpha_i}. ")
public class Dirichlet extends JAGSDistribution {
	JFunction alpha;

    public Dirichlet(@Param(name="alpha", description="oefficients of the Dirichlet distribution") JFunction alpha) {
    	this.alpha = alpha;
    }
    
    @Override
    public void initAndValidate() {
    }

    @Override
    public Distribution getDistribution() {
        return null;
    }

    class DirichletImpl implements ContinuousDistribution {
        Double[] m_fAlpha;

        void setAlpha(Double[] alpha) {
            m_fAlpha = alpha;
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
            return Double.NaN;
        }

        @Override
        public double logDensity(double x) {
            return Double.NaN;
        }
    } // class DirichletImpl


    @Override
    public double calcLogP(Function pX) {
        double[] alpha = this.alpha.getDoubleValues();
        if (this.alpha.getDimension() != pX.getDimension()) {
            throw new IllegalArgumentException("Dimensions of alpha and x should be the same, but dim(alpha)=" + this.alpha.getDimension()
                    + " and dim(x)=" + pX.getDimension());
        }
        double logP = 0;
        double sumAlpha = 0;
        for (int i = 0; i < pX.getDimension(); i++) {
            double x = pX.getArrayValue(i);
            logP += (alpha[i] - 1) * Math.log(x);
            logP -= org.apache.commons.math.special.Gamma.logGamma(alpha[i]);
            sumAlpha += alpha[i];
        }
        logP += org.apache.commons.math.special.Gamma.logGamma(sumAlpha);
        return logP;
    }

	@Override
	public String getName() {
		return "ddirich";
	}

	@Override
	public int getParameterCount() {
		return 1;
	}

}

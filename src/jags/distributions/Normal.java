package jags.distributions;



import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;



@Description("Normal distribution.  f(x) = frac{1}{\\sqrt{2\\pi\\sigma^2}} e^{ -\\frac{(x-\\mu)^2}{2\\sigma^2} } " +
        "If the input x is a multidimensional parameter, each of the dimensions is considered as a " +
        "separate independent component.")
public class Normal extends JAGSDistribution {
//    final public Input<RealParameter> meanInput = new Input<>("mean", "mean of the normal distribution, defaults to 0");
//    final public Input<RealParameter> sigmaInput = new Input<>("sigma", "standard deviation of the normal distribution, defaults to 1");
//    final public Input<RealParameter> tauInput = new Input<>("tau", "precission of the normal distribution, defaults to 1", Validate.XOR, sigmaInput);


	JFunction mean, sigma;
	
    public Normal(@Param(name="mean", description="mean of the normal distribution, defaults to 0") JFunction mean,
    		@Param(name="sigma", description="standard deviation of the normal distribution, defaults to 1") JFunction sigma
    		) {
    	this.mean = mean;
    	this.sigma = sigma;
	}
    
    
    static org.apache.commons.math.distribution.NormalDistribution dist = new NormalDistributionImpl(0, 1);

    @Override
    public void initAndValidate() {
        refresh();
    }

    /**
     * make sure internal state is up to date *
     */
    @SuppressWarnings("deprecation")
	void refresh() {
        double mean;
        double sigma;
        if (this.mean == null) {
            mean = 0;
        } else {
            mean = this.mean.getArrayValue();
        }
        if (this.sigma == null) {
//        	if (tauInput.get() == null) {
        		sigma = 1;
//        	} else {
//                sigma = Math.sqrt(1.0/tauInput.get().getValue());
//        	}
        } else {
            sigma = this.sigma.getArrayValue();
        }
        dist.setMean(mean);
        dist.setStandardDeviation(sigma);
    }

    @Override
    public ContinuousDistribution getDistribution() {
        refresh();
        return dist;
    }

    @Override
    public double getMeanWithoutOffset() {
        if (this.mean == null) {
        	return 0.0;
        } else {
        	return this.mean.getArrayValue();
        }
    }

	@Override
	public String getName() {
		return "dnorm";
	}

	@Override
	public int getParameterCount() {
		return 2;
	}
} // class Normal

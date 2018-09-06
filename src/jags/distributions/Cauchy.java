package jags.distributions;



import org.apache.commons.math.distribution.CauchyDistributionImpl;
import org.apache.commons.math.distribution.ContinuousDistribution;

import beast.core.Description;
import beast.core.Param;
import jags.nodes.JFunction;



@Description("Cacuhy distribution, used as prior. " +
        "If the input x is a multidimensional parameter, each of the dimensions is considered as a " +
        "separate independent component.")
public class Cauchy extends JAGSDistribution {
    public Cauchy(){}


	    static org.apache.commons.math.distribution.CauchyDistribution m_dist = new CauchyDistributionImpl(0, 1);
	    
	    
	    JFunction median, scale;
	    
	    public Cauchy(@Param(name="median", description="median of the distribution, defaults to 0", defaultValue="0", optional=true) JFunction median,
	    		@Param(name="median", description="scale parameter, defaults to 1", defaultValue="0", optional=true) JFunction scale) {
	    	this.median = median;
	    	this.scale = scale;
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
	        double median;
	        double scale;
	        if (this.median == null) {
	            median = 0.0;
	        } else {
	            median = this.median.getArrayValue();
	        }
	        if (this.scale == null) {
	            scale = 1;
	        } else {
	            scale = this.scale.getArrayValue();
	        }
	        m_dist.setMedian(median);
	        m_dist.setScale(scale);
	    }

	    @Override
	    public ContinuousDistribution getDistribution() {
	        refresh();
	        return m_dist;
	    }

	    @Override
	    public double getMeanWithoutOffset() {
	    	return Double.NaN;
	    }

	    public JFunction getMedian() {
			return median;
		}
		public void setMedian(JFunction median) {
			this.median = median;
		}
		public JFunction getScale() {
			return scale;
		}
		public void setScale(JFunction scale) {
			this.scale = scale;
		}

		@Override
		public String getName() {
			return "cauchy";
		}

		@Override
		public int getParameterCount() {
			return 2;
		}

} // class Cauchy


package jags.distributions;



import org.apache.commons.math.distribution.ZipfDistributionImpl;

import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;



@Description("Zipf distribution: based on an empirical law called Zipf's law.")
public class Zipf extends JAGSDistribution {

	JFunction elementCount, exponent;

    static org.apache.commons.math.distribution.ZipfDistribution m_dist = new ZipfDistributionImpl(1, 2);

    
    public Zipf(@Param(name="elementCount", description="the number of elements") JFunction elementCount,
    		@Param(name="exponent", description="exponent parameter of the distribution") JFunction exponent
    		) {
    	this.elementCount = elementCount;
    	this.exponent = exponent;
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
        double elementCount = this.elementCount.getArrayValue();
        double exponent = this.exponent.getArrayValue();
        m_dist.setExponent(exponent);
        m_dist.setNumberOfElements((int) elementCount);
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
		return "dzipf";
	}
    
	@Override
	public int getParameterCount() {
		return 2;
	}

	
	public JFunction getElementCount() {
		return elementCount;
	}
	public void setElementCount(JFunction elementCount) {
		this.elementCount = elementCount;
	}
	public JFunction getExponent() {
		return exponent;
	}
	public void setExponent(JFunction exponent) {
		this.exponent = exponent;
	}
} // class Zipf

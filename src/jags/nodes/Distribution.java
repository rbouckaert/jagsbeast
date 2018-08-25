package jags.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math.MathException;

import beast.core.BEASTInterface;
import beast.core.Description;
import jags.nodes.JFunction;
import beast.core.Param;
import beast.core.State;
import beast.core.parameter.IntegerParameter;
import beast.core.parameter.RealParameter;
import beast.math.distributions.ParametricDistribution;

@Description("Distribution in graphical model")
public class Distribution extends beast.core.Distribution {
	ParametricDistribution distr;
	JFunction x;
		
	public Distribution(@Param(name="distr", description="distribution used to calculate prior, e.g. normal, beta, gamma.") ParametricDistribution distr,
			@Param(name="x", description="point at which the density is calculated") JFunction x) {
		this.distr = distr;
		this.x = x;
	}

	@Override
    public double calculateLogP() {
        if (x instanceof RealParameter || x instanceof IntegerParameter) {
            // test that parameter is inside its bounds
            double l = 0.0;
            double h = 0.0;
            if (x instanceof RealParameter) {
                l = ((RealParameter) x).getLower();
                h = ((RealParameter) x).getUpper();
            } else {
                l = ((IntegerParameter) x).getLower();
                h = ((IntegerParameter) x).getUpper();
            }
            for (int i = 0; i < x.getDimension(); i++) {
                double value = x.getArrayValue(i);
                if (value < l || value > h) {
                    logP = Double.NEGATIVE_INFINITY;
                    return Double.NEGATIVE_INFINITY;
                }
            }
        }
        logP = distr.calcLogP(x);
        if (logP == Double.POSITIVE_INFINITY) {
            logP = Double.NEGATIVE_INFINITY;
        }
        return logP;
    }
	  
    @Override
    public void sample(State state, Random random) {

        if (sampledFlag)
            return;

        sampledFlag = true;

        // Cause conditional parameters to be sampled
        sampleConditions(state, random);

        // sample distribution parameters
        Double[] newx;
        try {
            newx = distr.sample(1)[0];

            if (x instanceof RealParameter) {
                for (int i = 0; i < newx.length; i++) {
                    ((RealParameter) x).setValue(i, newx[i]);
                }
            } else if (x instanceof IntegerParameter) {
                for (int i = 0; i < newx.length; i++) {
                    ((IntegerParameter) x).setValue(i, (int)Math.round(newx[i]));
                }
            }

        } catch (MathException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to sample!");
        }
    }

    @Override
    public List<String> getConditions() {
        List<String> conditions = new ArrayList<>();
        conditions.add(distr.getID());
        return conditions;
    }

    @Override
    public List<String> getArguments() {
        List<String> arguments = new ArrayList<>();

        if (x != null && x instanceof BEASTInterface) {
            arguments.add(((BEASTInterface)x).getID());
        }

        return arguments;
    }

}

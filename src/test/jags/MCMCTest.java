package test.jags;

import org.junit.Test;

import beast.app.beauti.BeautiDoc;
import beast.core.Logger;
import beast.core.MCMC;
import beast.core.util.Log;
import beast.evolution.operators.ScaleOperator;
import beast.util.LogAnalyser;
import jags.CalculatorListenerImpl;
import jags.CalculatorParsingException;
import jags.nodes.Distribution;
import jags.nodes.Variable;
import junit.framework.TestCase;

public class MCMCTest extends TestCase {

	
	final static String FILE_NAME = "jagsbeast.log";
	
	@Test
	public void testMCMC() {
		String cmd = "a = 0.1  a ~ dnorm(100, 1)";
		try {
			BeautiDoc doc = new BeautiDoc();
			CalculatorListenerImpl parser = new CalculatorListenerImpl(doc);
			parser.parse("model{" + cmd + "}");
			Distribution logP = (Distribution) doc.pluginmap.get("logP.a");
			Variable a = (Variable) doc.pluginmap.get("a");
			ScaleOperator operator = new ScaleOperator();
			operator.initByName("parameter", a, "weight", 1.0, "scaleFactor", 0.75);
			Logger logger = new Logger();
			logger.initByName("log", a, "log", logP, "fileName", FILE_NAME, "logEvery", 10);
			
			Logger.FILE_MODE = Logger.FILE_MODE.overwrite;
			MCMC mcmc = new MCMC();
			mcmc.initByName("distribution", logP, "operator", operator, "logger", logger, "chainLength", 100000L);

			mcmc.run();
			
			
			LogAnalyser traceLog = new LogAnalyser(FILE_NAME);
			double aMean = traceLog.getMean("a");
			assertEquals(aMean, 100, 1e-1);
			double logPMean = traceLog.getMean("logP.a");
			assertEquals(logPMean, -1.419, 1e-2);
			
			
		} catch (CalculatorParsingException e) {
			Log.info("model{" + cmd + "}");
			Log.info(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace(Log.err);
			Log.info("Error: " + e.getMessage());
		}
	}
	
}

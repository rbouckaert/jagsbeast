package jags;

import java.io.*;

import beast.app.beauti.BeautiConfig;
import beast.app.beauti.BeautiDoc;
import beast.core.MCMC;
import beast.core.util.Log;
import beast.util.XMLProducer;

/** A simple Read-Eval-Print-Loop for the JAGS language for BEAST **/ 
public class REPL {
	BeautiDoc doc;

	public REPL() {
		doc = new BeautiDoc();
		doc.beautiConfig = new BeautiConfig();
		doc.beautiConfig.initAndValidate();
	}
	
	public REPL(BeautiDoc doc) {
		this.doc = doc;
	}

	public void doREPL() {
		while (true) {
			System.out.print(">");
			try {
				String cmd = (new BufferedReader(new InputStreamReader(System.in))).readLine();
				processCmd(cmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void processCmd(String cmd) {
		if (cmd == null) {
			return;
		}
		if (cmd.startsWith("quit") || cmd.startsWith("end") || cmd == null) {
			System.exit(0);
		} else if (cmd.startsWith("save")) {
			save(cmd);
		} else {
			try {
				CalculatorListenerImpl parser = new CalculatorListenerImpl(doc);
				parser.parse(cmd);
			} catch (CalculatorParsingException e) {
				Log.info(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace(Log.err);
				Log.info("Error: " + e.getMessage());
			}
		}
	}

	private void save(String cmd) {
		String [] strs = cmd.trim().split("\\s+");
		if (strs.length != 2) {
			Log.warning("Expected 'save <filename>' but got " + cmd);
			Log.warning("File is not saved");
			return;
		}
		try {
			XMLProducer xmlProducer = new XMLProducer();
			MCMC mcmc = (MCMC) doc.mcmc.get();

			FileWriter outfile = new FileWriter(new File(strs[1]));
	        outfile.write(xmlProducer.toXML(mcmc));
	        outfile.close();
	        System.err.println("Results in " + strs[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		System.out.println("A simple Read-Eval-Print-Loop for the Compact Analysis language for BEAST");
		REPL repl = new REPL();
		repl.doREPL();
	}

}

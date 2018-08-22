package jags;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

import beast.core.BEASTObject;
import beast.core.util.Log;
import jags.CalculatorParser.AtomContext;
import jags.CalculatorParser.ConstantContext;
import jags.CalculatorParser.EquationContext;


public class CalculatorListenerImpl extends CalculatorBaseListener {	
	public class CalculatorASTVisitor extends CalculatorBaseVisitor<BEASTObject> {
		
		@Override
		public BEASTObject visitAtom(AtomContext ctx) {
			// TODO Auto-generated method stub
			return super.visitAtom(ctx);
		}
		
		@Override
		public BEASTObject visitEquation(EquationContext ctx) {
			// TODO Auto-generated method stub
			return super.visitEquation(ctx);
		}
		
		@Override
		public BEASTObject visitConstant(ConstantContext ctx) {
			// TODO Auto-generated method stub
			return super.visitConstant(ctx);
		}
	
	}

	public void parse(String CASentence) {
        // Custom parse/lexer error listener
        BaseErrorListener errorListener = new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer,
                                    Object offendingSymbol,
                                    int line, int charPositionInLine,
                                    String msg, RecognitionException e) {
                throw new CalculatorParsingException(msg, charPositionInLine, line);
            }
        };

        // Get our lexer
        CalculatorLexer lexer = new CalculatorLexer(new ANTLRInputStream(CASentence));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

	    // Get a list of matched tokens
	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	 
	    // Pass the tokens to the parser
	    CalculatorParser parser = new CalculatorParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
	 
        ParseTree parseTree = parser.equation();
//	    // Specify our entry point
//	    CasentenceContext CASentenceContext = parser.casentence();
//	 
//	    // Walk it and attach our listener
//	    ParseTreeWalker walker = new ParseTreeWalker();
//	    AntlrCompactAnalysisListener listener = new AntlrCompactAnalysisListener();
//	    walker.walk(listener, CASentenceContext);


        // Traverse parse tree, constructing BEAST tree along the way
        CalculatorASTVisitor visitor = new CalculatorASTVisitor();

        visitor.visit(parseTree);
	}
	
}

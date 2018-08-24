package jags;



import java.util.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

import beast.app.beauti.BeautiDoc;
import beast.core.BEASTObject;
import beast.core.Function;
import beast.math.distributions.ParametricDistribution;
import jags.CalculatorParser.*;
import jags.nodes.*;
import jags.functions.*;
import jags.operators.*;
import jags.distributions.*;


public class CalculatorListenerImpl extends CalculatorBaseListener {
	BeautiDoc doc;
	
	CalculatorListenerImpl(BeautiDoc doc) {
		this.doc = doc;
	}
	public class CalculatorASTVisitor extends CalculatorBaseVisitor<BEASTObject> {
		BEASTObject expression;
		List<Distribution> distributions = new ArrayList<>();
		Set<String> bivarOperators;
		Set<String> univarDistirbutions;
		Set<String> bivarDistirbutions;
		Set<String> trivarDistirbutions;
		
		public CalculatorASTVisitor() {
			bivarOperators = new HashSet<>();
			for (String s : new String[]{"+","-","*","/","**","&&","||","<=","<",">=",">","!=","==","&","|","<<",">>",">>>"}) {
				bivarOperators.add(s);
			}
			
			univarDistirbutions = new HashSet<>();
			bivarDistirbutions = new HashSet<>();
			trivarDistirbutions = new HashSet<>();
			for (String s : new String[]{"dchisq" ,"dexp" ,"dpois" ,"dgeom","ddirich"}){
				univarDistirbutions.add(s);
			}
			for (String s : new String[]{"dnorm" ,"dlnorm" ,"dbeta" ,"dnchisq" ,"dnt" ,"dbinom" ,"dnbinom" ,"dnbinom_mu" ,"dcauchy" ,"df" ,"dgamma" ,"dunif" ,"dweibull" ,"dlogis" ,"dsignrank"}){
				bivarDistirbutions.add(s);
			}
			for (String s : new String[]{"dnbeta" ,"dnf" ,"dhyper" ,"dwilcox"}){
				trivarDistirbutions.add(s);			
			}

		}
		
		@Override
		public BEASTObject visitInput(InputContext ctx) {
			// TODO Auto-generated method stub
			return super.visitInput(ctx);
		}
		
		@Override
		public BEASTObject visitConstant(ConstantContext ctx) {
			String text = ctx.getText();
			double d = 0;
			try {
				d = Double.parseDouble(text);
			} catch (NumberFormatException e) {
				try {
					d = Long.parseLong(text);
				} catch (NumberFormatException e2) {
					d = Boolean.parseBoolean(text) ? 1.0 : 0.0;
				}
			}
			Constant c = Constant.createConstant(new double[]{d});
			System.out.println("value = " + text + " = " + d);
			expression = c;
			return c;
		}
	
		@Override
		public BEASTObject visitDeterm_relation(Determ_relationContext ctx) {
			String id = ctx.children.get(0).getText();
			super.visitDeterm_relation(ctx);
			Variable c = new Variable(id, (Function) expression);
			c.setID(id);
			return c;
		}
		
		@Override
		public BEASTObject visitExpression(ExpressionContext ctx) {
			super.visitExpression(ctx);
			Transform transform = null;
			if (ctx.getChildCount() >= 2) {
				String s = ctx.getChild(1).getText();
				if (bivarOperators.contains(s)) {
					Function f1 = (Function) visit(ctx.getChild(2));
					Function f2 = (Function) visit(ctx.getChild(3));


					switch (s) {
					case "+": transform = new Plus(f1,f2); break;
					case "-": transform = new Minus(f1,f2); break;
					case "*": transform = new Times(f1,f2); break;
					case "/": transform = new Div(f1,f2); break;
					case "**": transform = new Pow(f1,f2); break;
					case "&&": transform = new And(f1,f2); break;
					case "||": transform = new Or(f1,f2); break;
					case "<=": transform = new LE(f1,f2); break;
					case "<": transform = new LT(f1,f2); break;
					case ">=": transform = new GE(f1,f2); break;
					case ">": transform = new GT(f1,f2); break;
					case "!=": transform = new Ne(f1,f2); break;
					case "==": transform = new Eq(f1,f2); break;
					case "%": transform = new Modulo(f1,f2); break;

					case "&": transform = new BitwiseAnd(f1,f2); break;
					case "|": transform = new BitwiseOr(f1,f2); break;
					case "^": transform = new BitwiseXOr(f1,f2); break;
					case "<<": transform = new LeftShift(f1,f2); break;
					case ">>": transform = new RightShift(f1,f2); break;
					case ">>>": transform = new ZeroFillRightShift(f1,f2); break;
					}
				} else if (s.equals("!")) {
					Function f1 = (Function) visit(ctx.getChild(2));
					transform = new Not(f1);
				} else if (s.equals("~")) {
					Function f1 = (Function) visit(ctx.getChild(2));
					transform = new Complement(f1);
				} else if (s.equals("[")) {
					Function var = (Function) visit(ctx.getChild(0));
					Function f1 = (Function) visit(ctx.getChild(2));
					transform = new Index(var, f1);
				}
			}
			return transform; 
		}
		
		@Override
		public BEASTObject visitDistribution(DistributionContext ctx) {
			super.visitDistribution(ctx);
			String name = ctx.getChild(0).getText();
			ParametricDistribution distr = null;
			if (univarDistirbutions.contains(name)) {
				Function f1 = (Function) visit(ctx.getChild(2));
				switch (name) {
				//case "dchisq": distr = new Chisq(f1); break;
				case "dexp": distr = new Exponential(f1); break;
				case "ddirich": distr = new Dirichlet(f1); break;
				//case "dpois": distr = new Pois(f1); break;
				//case "dgeom": distr = new Geom(f1); break;
				}
				
			} else if (bivarDistirbutions.contains(name)) {
				Function f1 = (Function) visit(ctx.getChild(2));
				Function f2 = (Function) visit(ctx.getChild(3));
				switch (name) {
				case "dnorm": distr = new Normal(f1,f2); break;
				case "dlnorm": distr = new LogNormal(f1,f2); break;
				case "dbeta": distr = new Beta(f1,f2); break;
				//case "dnchisq": distr = new Nchisq(f1,f2); break;
				//case "dnt": distr = new Nt(f1,f2); break;
				//case "dbinom": distr = new Binom(f1,f2); break;
				//case "dnbinom": distr = new Nbinom(f1,f2); break;
				//case "dnbinom_mu": distr = new Nbinom_mu(f1,f2); break;
				//case "dcauchy": distr = new Cauchy(f1,f2); break;
				//case "df": distr = new F(f1,f2); break;
				//case "dgamma": distr = new Gamma(f1,f2); break;
				//case "dunif": distr = new Unif(f1,f2); break;
				//case "dweibull": distr = new Weibull(f1,f2); break;
				//case "dlogis": distr = new Logis(f1,f2); break;
				//case "dsignrank": distr = new Signrank(f1,f2); break;
				}
			} else if (trivarDistirbutions.contains(name)) {
				Function f1 = (Function) visit(ctx.getChild(2));
				Function f2 = (Function) visit(ctx.getChild(3));
				Function f3 = (Function) visit(ctx.getChild(4));
				
				switch (name) {				
					//case "dnbeta": distr = new Nbeta(f1,f2,f3); break;
					//case "dnf": distr = new Nf(f1,f2,f3); break;
					//case "dhyper": distr = new Hyper(f1,f2,f3); break;
					//case "dwilcox": distr = new Wilcox(f1,f2,f3); break;
				}
				
			} else {
				throw new IllegalArgumentException("Unknown distributions. Choose one of " +
						Arrays.toString(univarDistirbutions.toArray()) + 
						Arrays.toString(bivarDistirbutions.toArray()) + 
						Arrays.toString(trivarDistirbutions.toArray()) 
						);
			}
			if (distr == null) {
				throw new IllegalArgumentException("Distributions not implemented yet");
			}
			return distr; 
		}
		
		
		@Override
		public BEASTObject visitMethodCall(MethodCallContext ctx) {
			super.visitMethodCall(ctx);
			Transform transform;
			String functionName = ctx.children.get(0).getText();
			
			Function f1 = null, f2 = null;
			switch (ctx.children.size()) {
				case 4 :  f1 = (Function) visit(ctx.getChild(3));
				case 3 :  f1 = (Function) visit(ctx.getChild(2));
			}
			
			switch (functionName) {
				// Univariable functions
				case "cos": transform = new Cos(f1);break;
				case "sin": transform = new Sin(f1);break;
				case "tan": transform = new Tan(f1);break;
				case "abs": transform = new Abs(f1);break;
				case "acos": transform = new Acos(f1);break;
				case "asin": transform = new Asin(f1);break;
				case "atan": transform = new Atan(f1);break;
				case "sinh": transform = new Sinh(f1);break;
				case "cosh": transform = new Cosh(f1);break;
				case "tanh": transform = new Tanh(f1);break;

				case "cbrt": transform = new Cbrt(f1);break;
				case "sqrt": transform = new Sqrt(f1);break;
				case "exp": transform = new Exp(f1);break;
				case "expm1": transform = new Expm1(f1);break;
				case "log": transform = new jags.functions.Log(f1);break;
				case "log10": transform = new Log10(f1);break;
				case "log1p": transform = new Log1p(f1);break;
				case "ceil": transform = new Ceil(f1);break;
				case "floor": transform = new Floor(f1);break;
				case "round": transform = new Round(f1);break;
				case "signum": transform = new Signum(f1);break;
				
				// Bivariable functions
				case "hypot": transform = new Hypot(f1, f2);break;
				case "atan2": transform = new Atan2(f1, f2);break;
				case "min": transform = new Min(f1, f2);break;
				case "max": transform = new Max(f1, f2);break;
				case "pow": transform = new Pow(f1, f2);break;
				
				default:
					throw new IllegalArgumentException("Unknown function : " + functionName);
			}
			
			expression = transform;
			return transform;
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
	 
        ParseTree parseTree = parser.input();
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

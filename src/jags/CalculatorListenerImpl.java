package jags;



import java.util.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

import beast.app.beauti.BeautiDoc;
import beast.core.util.Log;
import jags.nodes.JFunction;
import beast.math.distributions.ParametricDistribution;
import jags.parser.*;
import jags.parser.CalculatorParser.*;
import jags.nodes.*;
import jags.functions.*;
import jags.operators.*;
import jags.distributions.*;
 

public class CalculatorListenerImpl extends CalculatorBaseListener {
	BeautiDoc doc;
	static private Set<String> bivarOperators;
	static private Set<String> univarDistirbutions;
	static private Set<String> bivarDistirbutions;
	static private Set<String> trivarDistirbutions;
	
	public CalculatorListenerImpl(BeautiDoc doc) {
		this.doc = doc;
	}
	
	// we want to return JFunction and JFunction[] -- so make it a visitor of Object and cast to expected type
	public class CalculatorASTVisitor extends CalculatorBaseVisitor<Object> {
		List<Distribution> distributions = new ArrayList<>();
		
		Map<String, Integer> iteratorValue = new HashMap<>();
		Map<String, Integer> iteratorDimension = new HashMap<>();
		
		public CalculatorASTVisitor() {
			bivarOperators = new HashSet<>();
			for (String s : new String[]{"+","-","*","/","**","&&","||","<=","<",">=",">","%",":","^","!=","==","&","|","<<",">>",">>>"}) {
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
		public Object visitConstant(CalculatorParser.ConstantContext ctx) {
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
			return c;
		}
	
		@Override
		public Object visitDeterm_relation(CalculatorParser.Determ_relationContext ctx) {
			JFunction f = (JFunction) visit(ctx.getChild(2));
			String id = ctx.children.get(0).getText();
			if (id.indexOf('[') >= 0) {
				id = ctx.getChild(0).getChild(0).getText();
				JFunction range = (JFunction) visit(ctx.getChild(0).getChild(2));
				Variable c = null;
				if (doc.pluginmap.containsKey(id)) {
					c = (Variable) doc.pluginmap.get(id);
					c.setValue(range, f);
				} else {
					throw new IllegalArgumentException("Variable " + id + " should have been declared before using [] notation");
					//c = new Variable(id, f, dimensions);
					//c.setValue(range, f);
				}
				return c;
			}

			Variable c = new Variable(id, f);
			c.setID(id);
			doc.registerPlugin(c);
			System.out.println(c);			
			return c;
		}
		
		@Override
		public Object visitStoch_relation(CalculatorParser.Stoch_relationContext ctx) {
			ParametricDistribution distr = (ParametricDistribution) visit(ctx.getChild(2));
			String id = ctx.getChild(0).getText();
			JFunction f;
			if (id.indexOf('[') == -1) {
				f = (JFunction) doc.pluginmap.get(id);
			} else {
				f = (JFunction) visit(ctx.getChild(0));
			}
			
			Distribution distribution = new Distribution(distr, f);
			distribution.setID("logP." + ctx.getChild(0).getText());
			
			distributions.add(distribution);
			doc.registerPlugin(distribution);
			
			return distribution;
		}		
		
		@Override
		public Object visitExpression(CalculatorParser.ExpressionContext ctx) {
			if (ctx.getChildCount() == 1) {
				String key = ctx.getChild(0).getText();
				if (doc.pluginmap.containsKey(key)) {
					return doc.pluginmap.get(key);
				}
				if (iteratorValue.containsKey(key)) {
					return new JFunction() {						
						@Override
						public int getDimension() {return 1;}
						
						@Override
						public double getArrayValue(int dim) {
							return iteratorValue.get(key);
						}
						
						@Override
						public double getArrayValue() {
							return iteratorValue.get(key);
						}
						
						@Override
						public int getDimensionCount() {return 1;}
						
						@Override
						public int getDimension(int dim) {return 1;}
					};
				}
				return visit(ctx.getChild(0));
			}
			Transform transform = null;
			if (ctx.getChildCount() >= 2) {
				String s = ctx.getChild(1).getText();
				if (bivarOperators.contains(s)) {
					JFunction f1 = (JFunction) visit(ctx.getChild(0));
					JFunction f2 = (JFunction) visit(ctx.getChild(ctx.getChildCount() - 1));


					switch (s) {
					case "+": transform = new Plus(f1,f2); break;
					case "-": transform = new Minus(f1,f2); break;
					case "*": transform = new Times(f1,f2); break;
					case "/": transform = new Div(f1,f2); break;
					case "**": transform = new Pow(f1,f2); break;
					case "&&": transform = new And(f1,f2); break;
					case "||": transform = new Or(f1,f2); break;
					case "<=": transform = new LE(f1,f2); break;
					case "<": 
						switch (ctx.getChildCount()) {
						case 3:
							transform = new LT(f1,f2); break;
						case 4:
							transform = new LeftShift(f1,f2); break;
						} 
						break;
					case ">=": transform = new GE(f1,f2); break;
					case ">":
						switch (ctx.getChildCount()) {
						case 3:
							transform = new GT(f1,f2); break;
						case 4:
							transform = new RightShift(f1,f2); break;
						case 5:
							transform = new ZeroFillRightShift(f1,f2); break;
						} 
						break;
					case "!=": transform = new Ne(f1,f2); break;
					case "==": transform = new Eq(f1,f2); break;
					case "%": transform = new Modulo(f1,f2); break;

					case "&": transform = new BitwiseAnd(f1,f2); break;
					case "|": transform = new BitwiseOr(f1,f2); break;
					case "^": transform = new BitwiseXOr(f1,f2); break;
					case "<<": transform = new LeftShift(f1,f2); break;
					case ">>": transform = new RightShift(f1,f2); break;
					case ">>>": transform = new ZeroFillRightShift(f1,f2); break;
					case ":": transform = new Range(f1,f2); break;
					}
				} else if (s.equals("!")) {
					JFunction f1 = (JFunction) visit(ctx.getChild(2));
					transform = new Not(f1);
				} else if (s.equals("~")) {
					JFunction f1 = (JFunction) visit(ctx.getChild(2));
					transform = new Complement(f1);
				} else if (s.equals("[")) {
					JFunction var = (JFunction) visit(ctx.getChild(0));
					JFunction f1 = (JFunction) visit(ctx.getChild(2));
					transform = new Index(var, f1);
				}
			}
			return transform; 
		}
		
		@Override
		public Object visitDistribution(CalculatorParser.DistributionContext ctx) {
			super.visitDistribution(ctx);
			String name = ctx.getChild(0).getText();
			ParametricDistribution distr = null;
			
			JFunction [] f = (JFunction[]) visit(ctx.getChild(2));
			
			if (univarDistirbutions.contains(name)) {
				switch (name) {
				//case "dchisq": distr = new Chisq(f1); break;
				case "dexp": distr = new Exponential(f[0]); break;
				case "ddirich": distr = new Dirichlet(f[0]); break;
				//case "dpois": distr = new Pois(f1); break;
				//case "dgeom": distr = new Geom(f1); break;
				}
				
			} else if (bivarDistirbutions.contains(name)) {
				switch (name) {
				case "dnorm": distr = new Normal(f[0],f[1]); break;
				case "dlnorm": distr = new LogNormal(f[0],f[1]); break;
				case "dbeta": distr = new Beta(f[0],f[1]); break;
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
		

		@Override // for_loop: counter relations 
		public Object visitFor_loop(CalculatorParser.For_loopContext ctx) {
			ParseTree counter = ctx.getChild(0);
			// counter: FOR '(' NAME IN range_element ')'
			String name = counter.getChild(2).getText();
			JFunction range = (JFunction) visit(counter.getChild(4));
			iteratorDimension.remove(range.getDimension());
			for (int i = 0; i < range.getDimension(); i++) {
				int value = (int) range.getArrayValue(i);
				iteratorValue.put(name, value);
				visit(ctx.getChild(1));
			}
			iteratorValue.remove(name);
			iteratorDimension.remove(name);
			return null;
		}
		
		@Override // counter: FOR '(' NAME IN range_element ')'
		public Object visitCounter(CalculatorParser.CounterContext ctx) {
			return super.visitCounter(ctx);
		}
		
		@Override // range_element: | expression 
		public Object visitRange_element(CalculatorParser.Range_elementContext ctx) {
			if (ctx.getChildCount() == 0) {
				return null;
			}
			return visit(ctx.getChild(0));
		}
		
		@Override // expression_list : expression (',' expression)*
		public Object visitExpression_list(CalculatorParser.Expression_listContext ctx) {
			JFunction [] f = new JFunction[ctx.getChildCount()/2+1];
			for (int i = 0; i < f.length; i++) {
				f[i] = (JFunction) visit(ctx.getChild(i*2));
			}
			return f;
		}
		
		@Override
		public Object visitMethodCall(CalculatorParser.MethodCallContext ctx) {
			Transform transform;
			String functionName = ctx.children.get(0).getText();
			
			if (functionName.equals("c")) {
				JFunction [] f= (JFunction []) visit(ctx.getChild(2));				
				Concat c = new Concat(f);
				return c;
			}
			
			// process expression_list
			JFunction [] f =  (JFunction[]) visit(ctx.getChild(2));
			
			switch (functionName) {
				// Univariable functions
				case "length": transform = new Length(f[0]);break;
				case "dim": transform = new Dim(f[0]);break;

				case "sort": transform = new Sort(f[0]);break;
				case "rank": transform = new Rank(f[0]);break;
				case "order": transform = new Order(f[0]);break;
				case "inverse": transform = new Inverse(f[0]);break;
				case "t": transform = new Transpose(f[0]);break;
				
				case "abs": transform = new Abs(f[0]);break;
				case "cos": transform = new Cos(f[0]);break;
				case "sin": transform = new Sin(f[0]);break;
				case "tan": transform = new Tan(f[0]);break;
				case "arccos": 
				case "acos": transform = new Acos(f[0]);break;
				case "arcsin": 
				case "asin": transform = new Asin(f[0]);break;
				case "arctan": 
				case "atan": transform = new Atan(f[0]);break;
				case "sinh": transform = new Sinh(f[0]);break;
				case "cosh": transform = new Cosh(f[0]);break;
				case "tanh": transform = new Tanh(f[0]);break;
				case "arcsinh": 
				case "asinh": transform = new Asinh(f[0]);break;
				case "arccosh": 
				case "acosh": transform = new Acosh(f[0]);break;
				case "arctanh": 
				case "atanh": transform = new Atanh(f[0]);break;

				case "cbrt": transform = new Cbrt(f[0]);break;
				case "cloglog": transform = new CLogLog(f[0]);break;
				case "sqrt": transform = new Sqrt(f[0]);break;
				case "exp": transform = new Exp(f[0]);break;
				case "expm1": transform = new Expm1(f[0]);break;
				case "log": transform = new jags.functions.Log(f[0]);break;
				case "log10": transform = new Log10(f[0]);break;
				case "log1p": transform = new Log1p(f[0]);break;
				case "logdet": transform = new LogDet(f[0]);break;
				case "loggamm": transform = new LogGamma(f[0]);break;
				case "logit": transform = new Logit(f[0]);break;
				case "logfact": transform = new LogFact(f[0]);break;
				case "probit": transform = new Probit(f[0]);break;
				case "ceil": transform = new Ceil(f[0]);break;
				case "trunc":
				case "floor": transform = new Floor(f[0]);break;
				case "round": transform = new Round(f[0]);break;
				case "signum": transform = new Signum(f[0]);break;
				case "step": transform = new Step(f[0]);break;
				case "mean": transform = new Mean(f[0]);break;
				case "sd": transform = new StdDev(f[0]);break;
				
				// Bivariable functions
				case "hypot": transform = new Hypot(f[0], f[1]);break;
				case "atan2": transform = new Atan2(f[0], f[1]);break;
				case "pow": transform = new Pow(f[0], f[1]);break;
				case "rep": transform = new Rep(f[0], f[1]);break;
				case "prod":
				case "%*%": transform = new MatrixMult(f[0], f[1]);break;
				case "equals": transform = new Eq(f[0], f[1]);break;
				case "inprod": transform = new Times(f[0], f[1]); break;
				
				case "ifelse": transform = new IfElse(f[0], f[1], f[2]);break;
				case "interp.lin": transform = new InterpLin(f[0], f[1], f[2]);break;
				
				case "min": transform = new Min(f);break;
				case "max": transform = new Max(f);break;
				case "sum": transform = new Sum(f);break;

				default:
					throw new IllegalArgumentException("Unknown function : " + functionName);
			}
			
			return transform;
		}
		
	}

	public void parse(String CASentence) {
        // Custom parse/lexer error listener
        BaseErrorListener errorListener = new BaseErrorListener() {
        	@Override
        	public void syntaxError(Recognizer<?, ?> recognizer, 
        			Object offendingSymbol, int line, int charPositionInLine,
        			String msg, RecognitionException e) {
        		e.printStackTrace();
        	    if ( e instanceof NoViableAltException ) {
        	    	NoViableAltException nvae = (NoViableAltException)e;
        	    	Log.info(nvae.getLocalizedMessage());
//              msg = "X no viable alt; token="+nvae.token+
//                 " (decision="+nvae.decisionNumber+
//                 " state "+nvae.stateNumber+")"+
//                 " decision=<<"+nvae.grammarDecisionDescription+">>";
           }
           else {
           }
        	    throw new CalculatorParsingException(msg, charPositionInLine, line);
        	}

//            @Override
//            public void syntaxError(Recognizer<?, ?> recognizer,
//                                    Object offendingSymbol,
//                                    int line, int charPositionInLine,
//                                    String msg, RecognitionException e) {
//                throw new CalculatorParsingException(msg, charPositionInLine, line);
//            }
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

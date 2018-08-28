// Generated from Calculator.g4 by ANTLR 4.7.1
package jags.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CalculatorParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CalculatorVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInput(CalculatorParser.InputContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#var_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_stmt(CalculatorParser.Var_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#dec_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec_list(CalculatorParser.Dec_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#node_dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNode_dec(CalculatorParser.Node_decContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#dim_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDim_list(CalculatorParser.Dim_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#data_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitData_stmt(CalculatorParser.Data_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#model_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModel_stmt(CalculatorParser.Model_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#relations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelations(CalculatorParser.RelationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#relation_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelation_list(CalculatorParser.Relation_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#relation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelation(CalculatorParser.RelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#for_loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_loop(CalculatorParser.For_loopContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#counter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCounter(CalculatorParser.CounterContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(CalculatorParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#determ_relation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeterm_relation(CalculatorParser.Determ_relationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#stoch_relation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStoch_relation(CalculatorParser.Stoch_relationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#truncated}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTruncated(CalculatorParser.TruncatedContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#interval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterval(CalculatorParser.IntervalContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(CalculatorParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#range_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange_list(CalculatorParser.Range_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#range_element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange_element(CalculatorParser.Range_elementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(CalculatorParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression_list(CalculatorParser.Expression_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(CalculatorParser.MethodCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#distribution}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDistribution(CalculatorParser.DistributionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(CalculatorParser.ExpressionContext ctx);
}
// Generated from Calculator.g4 by ANTLR 4.7.1
package jags.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CalculatorParser}.
 */
public interface CalculatorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#input}.
	 * @param ctx the parse tree
	 */
	void enterInput(CalculatorParser.InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#input}.
	 * @param ctx the parse tree
	 */
	void exitInput(CalculatorParser.InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#var_stmt}.
	 * @param ctx the parse tree
	 */
	void enterVar_stmt(CalculatorParser.Var_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#var_stmt}.
	 * @param ctx the parse tree
	 */
	void exitVar_stmt(CalculatorParser.Var_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#dec_list}.
	 * @param ctx the parse tree
	 */
	void enterDec_list(CalculatorParser.Dec_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#dec_list}.
	 * @param ctx the parse tree
	 */
	void exitDec_list(CalculatorParser.Dec_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#node_dec}.
	 * @param ctx the parse tree
	 */
	void enterNode_dec(CalculatorParser.Node_decContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#node_dec}.
	 * @param ctx the parse tree
	 */
	void exitNode_dec(CalculatorParser.Node_decContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#dim_list}.
	 * @param ctx the parse tree
	 */
	void enterDim_list(CalculatorParser.Dim_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#dim_list}.
	 * @param ctx the parse tree
	 */
	void exitDim_list(CalculatorParser.Dim_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#data_stmt}.
	 * @param ctx the parse tree
	 */
	void enterData_stmt(CalculatorParser.Data_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#data_stmt}.
	 * @param ctx the parse tree
	 */
	void exitData_stmt(CalculatorParser.Data_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#model_stmt}.
	 * @param ctx the parse tree
	 */
	void enterModel_stmt(CalculatorParser.Model_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#model_stmt}.
	 * @param ctx the parse tree
	 */
	void exitModel_stmt(CalculatorParser.Model_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#relations}.
	 * @param ctx the parse tree
	 */
	void enterRelations(CalculatorParser.RelationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#relations}.
	 * @param ctx the parse tree
	 */
	void exitRelations(CalculatorParser.RelationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#relation_list}.
	 * @param ctx the parse tree
	 */
	void enterRelation_list(CalculatorParser.Relation_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#relation_list}.
	 * @param ctx the parse tree
	 */
	void exitRelation_list(CalculatorParser.Relation_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelation(CalculatorParser.RelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelation(CalculatorParser.RelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#for_loop}.
	 * @param ctx the parse tree
	 */
	void enterFor_loop(CalculatorParser.For_loopContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#for_loop}.
	 * @param ctx the parse tree
	 */
	void exitFor_loop(CalculatorParser.For_loopContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#counter}.
	 * @param ctx the parse tree
	 */
	void enterCounter(CalculatorParser.CounterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#counter}.
	 * @param ctx the parse tree
	 */
	void exitCounter(CalculatorParser.CounterContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(CalculatorParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(CalculatorParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#determ_relation}.
	 * @param ctx the parse tree
	 */
	void enterDeterm_relation(CalculatorParser.Determ_relationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#determ_relation}.
	 * @param ctx the parse tree
	 */
	void exitDeterm_relation(CalculatorParser.Determ_relationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#stoch_relation}.
	 * @param ctx the parse tree
	 */
	void enterStoch_relation(CalculatorParser.Stoch_relationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#stoch_relation}.
	 * @param ctx the parse tree
	 */
	void exitStoch_relation(CalculatorParser.Stoch_relationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#truncated}.
	 * @param ctx the parse tree
	 */
	void enterTruncated(CalculatorParser.TruncatedContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#truncated}.
	 * @param ctx the parse tree
	 */
	void exitTruncated(CalculatorParser.TruncatedContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#interval}.
	 * @param ctx the parse tree
	 */
	void enterInterval(CalculatorParser.IntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#interval}.
	 * @param ctx the parse tree
	 */
	void exitInterval(CalculatorParser.IntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(CalculatorParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(CalculatorParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#range_list}.
	 * @param ctx the parse tree
	 */
	void enterRange_list(CalculatorParser.Range_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#range_list}.
	 * @param ctx the parse tree
	 */
	void exitRange_list(CalculatorParser.Range_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#range_element}.
	 * @param ctx the parse tree
	 */
	void enterRange_element(CalculatorParser.Range_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#range_element}.
	 * @param ctx the parse tree
	 */
	void exitRange_element(CalculatorParser.Range_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(CalculatorParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(CalculatorParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void enterExpression_list(CalculatorParser.Expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void exitExpression_list(CalculatorParser.Expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(CalculatorParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(CalculatorParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#distribution}.
	 * @param ctx the parse tree
	 */
	void enterDistribution(CalculatorParser.DistributionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#distribution}.
	 * @param ctx the parse tree
	 */
	void exitDistribution(CalculatorParser.DistributionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CalculatorParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CalculatorParser.ExpressionContext ctx);
}
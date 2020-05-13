/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.analysis;

import P4.CodeGenarator.SemanticException;
import P4.Sable.node.*;
import P4.contextualAnalysis.TypeException;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node) throws TypeException;
    void caseAProg(AProg node) throws TypeException;
    void caseASetup(ASetup node) throws TypeException;
    void caseAClassBody(AClassBody node) throws TypeException;
    void caseASubclass(ASubclass node) throws TypeException;
    void caseAListExpr(AListExpr node) throws TypeException;
    void caseAMultOpExpr(AMultOpExpr node) throws TypeException;
    void caseALiteralExpr(ALiteralExpr node) throws TypeException;
    void caseAConstruct(AConstruct node) throws TypeException;
    void caseAValueExpr(AValueExpr node) throws TypeException;
    void caseAAddOpExpr(AAddOpExpr node) throws TypeException;
    void caseARelationExpr(ARelationExpr node) throws TypeException;
    void caseAEqualityExpr(AEqualityExpr node) throws TypeException;
    void caseABoolOpExpr(ABoolOpExpr node) throws TypeException;
    void caseAIntLiteral(AIntLiteral node);
    void caseAFloatLiteral(AFloatLiteral node);
    void caseAStringLiteral(AStringLiteral node);
    void caseABoolLiteral(ABoolLiteral node);
    void caseAVal(AVal node) throws TypeException;
    void caseAFieldCallField(AFieldCallField node) throws TypeException;
    void caseACallCallField(ACallCallField node) throws TypeException;
    void caseASingleDcl(ASingleDcl node) throws TypeException;
    void caseAListType(AListType node) throws TypeException;
    void caseAVarType(AVarType node) throws TypeException;
    void caseAElseIf(AElseIf node) throws TypeException;
    void caseADclStmt(ADclStmt node) throws TypeException;
    void caseAAssignStmt(AAssignStmt node) throws TypeException;
    void caseACallStmt(ACallStmt node) throws TypeException;
    void caseAIfStmt(AIfStmt node) throws TypeException;
    void caseASwitchStmt(ASwitchStmt node) throws TypeException;
    void caseAForStmt(AForStmt node) throws TypeException;
    void caseAForeachStmt(AForeachStmt node) throws TypeException;
    void caseAWhileStmt(AWhileStmt node) throws TypeException;
    void caseAReturnStmt(AReturnStmt node) throws TypeException;
    void caseACaseCase(ACaseCase node) throws TypeException;
    void caseADefaultCase(ADefaultCase node) throws TypeException;
    void caseAMethodDcl(AMethodDcl node) throws TypeException;
    void caseAParamDcl(AParamDcl node) throws TypeException;

    void caseTBlank(TBlank node);
    void caseTComment(TComment node);
    void caseTBoolLiteral(TBoolLiteral node);
    void caseTAddOp(TAddOp node);
    void caseTMultOp(TMultOp node);
    void caseTIntLiteral(TIntLiteral node);
    void caseTFloatLiteral(TFloatLiteral node);
    void caseTString(TString node);
    void caseTBoolOp(TBoolOp node);
    void caseTRelationOp(TRelationOp node);
    void caseTAssign(TAssign node);
    void caseTEqualOp(TEqualOp node);
    void caseTLParen(TLParen node);
    void caseTRParen(TRParen node);
    void caseTLBrack(TLBrack node);
    void caseTRBrack(TRBrack node);
    void caseTSetup(TSetup node);
    void caseTSeparator(TSeparator node);
    void caseTDot(TDot node);
    void caseTMoves(TMoves node);
    void caseTLBox(TLBox node);
    void caseTRBox(TRBox node);
    void caseTReturn(TReturn node);
    void caseTSemi(TSemi node);
    void caseTIf(TIf node);
    void caseTElse(TElse node);
    void caseTSwitch(TSwitch node);
    void caseTCase(TCase node);
    void caseTDefault(TDefault node);
    void caseTColon(TColon node);
    void caseTWhile(TWhile node);
    void caseTFor(TFor node);
    void caseTForeach(TForeach node);
    void caseTTurn(TTurn node);
    void caseTEndCon(TEndCon node);
    void caseTIn(TIn node);
    void caseTCard(TCard node);
    void caseTLArr(TLArr node);
    void caseTRArr(TRArr node);
    void caseTList(TList node);
    void caseTTypeof(TTypeof node);
    void caseTFun(TFun node);
    void caseTInc(TInc node);
    void caseTSub(TSub node);
    void caseTGame(TGame node);
    void caseTPlayer(TPlayer node);
    void caseTConst(TConst node);
    void caseTId(TId node);
    void caseEOF(EOF node);
    void caseInvalidToken(InvalidToken node);
}

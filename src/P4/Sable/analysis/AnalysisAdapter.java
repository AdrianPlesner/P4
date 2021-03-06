/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.analysis;

import java.util.*;

import P4.CodeGenarator.SemanticException;
import P4.Sable.node.*;
import P4.contextualAnalysis.TypeException;

public class AnalysisAdapter implements Analysis
{
    private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    @Override
    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    @Override
    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    @Override
    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    @Override
    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }

    @Override
    public void caseStart(Start node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAProg(AProg node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseASubclass(ASubclass node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAListExpr(AListExpr node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAMultOpExpr(AMultOpExpr node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseALiteralExpr(ALiteralExpr node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAConstruct(AConstruct node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAValueExpr(AValueExpr node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAAddOpExpr(AAddOpExpr node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseARelationExpr(ARelationExpr node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAEqualityExpr(AEqualityExpr node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseABoolOpExpr(ABoolOpExpr node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAIntLiteral(AIntLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFloatLiteral(AFloatLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStringLiteral(AStringLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABoolLiteral(ABoolLiteral node) throws SemanticException {
        defaultCase(node);
    }


    @Override
    public void caseAVal(AVal node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAFieldCallField(AFieldCallField node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseACallCallField(ACallCallField node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAListType(AListType node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAVarType(AVarType node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAElseIf(AElseIf node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAForeachStmt(AForeachStmt node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseACaseCase(ACaseCase node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseADefaultCase(ADefaultCase node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseAParamDcl(AParamDcl node) throws TypeException, SemanticException {
        defaultCase(node);
    }

    @Override
    public void caseTBlank(TBlank node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTComment(TComment node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBoolLiteral(TBoolLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAddOp(TAddOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMultOp(TMultOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIntLiteral(TIntLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFloatLiteral(TFloatLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTString(TString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBoolOp(TBoolOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRelationOp(TRelationOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAssign(TAssign node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEqualOp(TEqualOp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLParen(TLParen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRParen(TRParen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLBrack(TLBrack node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRBrack(TRBrack node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSetup(TSetup node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSeparator(TSeparator node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDot(TDot node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMoves(TMoves node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLBox(TLBox node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRBox(TRBox node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTReturn(TReturn node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSemi(TSemi node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIf(TIf node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTElse(TElse node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSwitch(TSwitch node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCase(TCase node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDefault(TDefault node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTColon(TColon node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTWhile(TWhile node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFor(TFor node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTForeach(TForeach node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTurn(TTurn node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEndCon(TEndCon node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIn(TIn node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCard(TCard node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLArr(TLArr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRArr(TRArr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTList(TList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTypeof(TTypeof node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFun(TFun node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTInc(TInc node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSub(TSub node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTGame(TGame node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPlayer(TPlayer node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTConst(TConst node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTId(TId node)
    {
        defaultCase(node);
    }

    @Override
    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    @Override
    public void caseInvalidToken(InvalidToken node)
    {
        defaultCase(node);
    }

    public void defaultCase(@SuppressWarnings("unused") Node node)
    {
        // do nothing
    }
}

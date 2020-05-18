/* This file was generated by SableCC (http://www.sablecc.org/). */

package P4.Sable.analysis;

import java.util.*;

import P4.CodeGenarator.SemanticException;
import P4.Sable.node.*;
import P4.contextualAnalysis.TypeException;

public class ReversedDepthFirstAdapter extends AnalysisAdapter
{
    public void inStart(Start node)
    {
        defaultIn(node);
    }

    public void outStart(Start node)
    {
        defaultOut(node);
    }

    public void defaultIn(@SuppressWarnings("unused") Node node)
    {
        // Do nothing
    }

    public void defaultOut(@SuppressWarnings("unused") Node node)
    {
        // Do nothing
    }

    @Override
    public void caseStart(Start node) throws TypeException, SemanticException {
        inStart(node);
        node.getEOF().apply(this);
        node.getPProg().apply(this);
        outStart(node);
    }

    public void inAProg(AProg node)
    {
        defaultIn(node);
    }

    public void outAProg(AProg node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAProg(AProg node) throws TypeException, SemanticException {
        inAProg(node);
        {
            List<PMethodDcl> copy = new ArrayList<PMethodDcl>(node.getMethods());
            Collections.reverse(copy);
            for(PMethodDcl e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getEndCondition());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getTurn());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PMethodDcl> copy = new ArrayList<PMethodDcl>(node.getMoves());
            Collections.reverse(copy);
            for(PMethodDcl e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getSetup() != null)
        {
            node.getSetup().apply(this);
        }
        {
            List<TId> copy = new ArrayList<TId>(node.getIncludes());
            Collections.reverse(copy);
            for(TId e : copy)
            {
                e.apply(this);
            }
        }
        outAProg(node);
    }

    public void inASetup(ASetup node)
    {
        defaultIn(node);
    }

    public void outASetup(ASetup node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException, SemanticException {
        inASetup(node);
        if(node.getPlayer() != null)
        {
            node.getPlayer().apply(this);
        }
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getGame());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getCard() != null)
        {
            node.getCard().apply(this);
        }
        outASetup(node);
    }

    public void inAClassBody(AClassBody node)
    {
        defaultIn(node);
    }

    public void outAClassBody(AClassBody node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException, SemanticException {
        inAClassBody(node);
        {
            List<PSubclass> copy = new ArrayList<PSubclass>(node.getSubclasses());
            Collections.reverse(copy);
            for(PSubclass e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PMethodDcl> copy = new ArrayList<PMethodDcl>(node.getMethods());
            Collections.reverse(copy);
            for(PMethodDcl e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getConstruct() != null)
        {
            node.getConstruct().apply(this);
        }
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getDcls());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        outAClassBody(node);
    }

    public void inASubclass(ASubclass node)
    {
        defaultIn(node);
    }

    public void outASubclass(ASubclass node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASubclass(ASubclass node) throws TypeException, SemanticException {
        inASubclass(node);
        if(node.getBody() != null)
        {
            node.getBody().apply(this);
        }
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        outASubclass(node);
    }

    public void inAConstruct(AConstruct node)
    {
        defaultIn(node);
    }

    public void outAConstruct(AConstruct node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAConstruct(AConstruct node) throws TypeException, SemanticException {
        inAConstruct(node);
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getBody());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PParamDcl> copy = new ArrayList<PParamDcl>(node.getParams());
            Collections.reverse(copy);
            for(PParamDcl e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        outAConstruct(node);
    }

    public void inAListExpr(AListExpr node)
    {
        defaultIn(node);
    }

    public void outAListExpr(AListExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAListExpr(AListExpr node) throws TypeException, SemanticException {
        inAListExpr(node);
        {
            List<PExpr> copy = new ArrayList<PExpr>(node.getElements());
            Collections.reverse(copy);
            for(PExpr e : copy)
            {
                e.apply(this);
            }
        }
        outAListExpr(node);
    }

    public void inAMultOpExpr(AMultOpExpr node)
    {
        defaultIn(node);
    }

    public void outAMultOpExpr(AMultOpExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMultOpExpr(AMultOpExpr node) throws TypeException, SemanticException {
        inAMultOpExpr(node);
        if(node.getR() != null)
        {
            node.getR().apply(this);
        }
        if(node.getOperator() != null)
        {
            node.getOperator().apply(this);
        }
        if(node.getL() != null)
        {
            node.getL().apply(this);
        }
        outAMultOpExpr(node);
    }

    public void inALiteralExpr(ALiteralExpr node)
    {
        defaultIn(node);
    }

    public void outALiteralExpr(ALiteralExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseALiteralExpr(ALiteralExpr node) throws TypeException, SemanticException {
        inALiteralExpr(node);
        if(node.getValue() != null)
        {
            node.getValue().apply(this);
        }
        outALiteralExpr(node);
    }

    public void inAValueExpr(AValueExpr node)
    {
        defaultIn(node);
    }

    public void outAValueExpr(AValueExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAValueExpr(AValueExpr node) throws TypeException, SemanticException {
        inAValueExpr(node);
        if(node.getVal() != null)
        {
            node.getVal().apply(this);
        }
        outAValueExpr(node);
    }

    public void inAAddOpExpr(AAddOpExpr node)
    {
        defaultIn(node);
    }

    public void outAAddOpExpr(AAddOpExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAddOpExpr(AAddOpExpr node) throws TypeException, SemanticException {
        inAAddOpExpr(node);
        if(node.getR() != null)
        {
            node.getR().apply(this);
        }
        if(node.getOperator() != null)
        {
            node.getOperator().apply(this);
        }
        if(node.getL() != null)
        {
            node.getL().apply(this);
        }
        outAAddOpExpr(node);
    }

    public void inARelationExpr(ARelationExpr node)
    {
        defaultIn(node);
    }

    public void outARelationExpr(ARelationExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseARelationExpr(ARelationExpr node) throws TypeException, SemanticException {
        inARelationExpr(node);
        if(node.getR() != null)
        {
            node.getR().apply(this);
        }
        if(node.getOperator() != null)
        {
            node.getOperator().apply(this);
        }
        if(node.getL() != null)
        {
            node.getL().apply(this);
        }
        outARelationExpr(node);
    }

    public void inAEqualityExpr(AEqualityExpr node)
    {
        defaultIn(node);
    }

    public void outAEqualityExpr(AEqualityExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAEqualityExpr(AEqualityExpr node) throws TypeException, SemanticException {
        inAEqualityExpr(node);
        if(node.getR() != null)
        {
            node.getR().apply(this);
        }
        if(node.getOperator() != null)
        {
            node.getOperator().apply(this);
        }
        if(node.getL() != null)
        {
            node.getL().apply(this);
        }
        outAEqualityExpr(node);
    }

    public void inABoolOpExpr(ABoolOpExpr node)
    {
        defaultIn(node);
    }

    public void outABoolOpExpr(ABoolOpExpr node)
    {
        defaultOut(node);
    }

    @Override
    public void caseABoolOpExpr(ABoolOpExpr node) throws TypeException, SemanticException {
        inABoolOpExpr(node);
        if(node.getR() != null)
        {
            node.getR().apply(this);
        }
        if(node.getOperator() != null)
        {
            node.getOperator().apply(this);
        }
        if(node.getL() != null)
        {
            node.getL().apply(this);
        }
        outABoolOpExpr(node);
    }

    public void inAIntLiteral(AIntLiteral node)
    {
        defaultIn(node);
    }

    public void outAIntLiteral(AIntLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIntLiteral(AIntLiteral node)
    {
        inAIntLiteral(node);
        if(node.getValue() != null)
        {
            node.getValue().apply(this);
        }
        outAIntLiteral(node);
    }

    public void inAFloatLiteral(AFloatLiteral node)
    {
        defaultIn(node);
    }

    public void outAFloatLiteral(AFloatLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFloatLiteral(AFloatLiteral node)
    {
        inAFloatLiteral(node);
        if(node.getValue() != null)
        {
            node.getValue().apply(this);
        }
        outAFloatLiteral(node);
    }

    public void inAStringLiteral(AStringLiteral node)
    {
        defaultIn(node);
    }

    public void outAStringLiteral(AStringLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAStringLiteral(AStringLiteral node)
    {
        inAStringLiteral(node);
        if(node.getValue() != null)
        {
            node.getValue().apply(this);
        }
        outAStringLiteral(node);
    }

    public void inABoolLiteral(ABoolLiteral node)
    {
        defaultIn(node);
    }

    public void outABoolLiteral(ABoolLiteral node)
    {
        defaultOut(node);
    }

    @Override
    public void caseABoolLiteral(ABoolLiteral node)
    {
        inABoolLiteral(node);
        if(node.getValue() != null)
        {
            node.getValue().apply(this);
        }
        outABoolLiteral(node);
    }

    public void inAVal(AVal node)
    {
        defaultIn(node);
    }

    public void outAVal(AVal node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVal(AVal node) throws TypeException, SemanticException {
        inAVal(node);
        {
            List<PCallField> copy = new ArrayList<PCallField>(node.getCallField());
            Collections.reverse(copy);
            for(PCallField e : copy)
            {
                e.apply(this);
            }
        }
        outAVal(node);
    }

    public void inAFieldCallField(AFieldCallField node)
    {
        defaultIn(node);
    }

    public void outAFieldCallField(AFieldCallField node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFieldCallField(AFieldCallField node)
    {
        inAFieldCallField(node);
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        outAFieldCallField(node);
    }

    public void inACallCallField(ACallCallField node)
    {
        defaultIn(node);
    }

    public void outACallCallField(ACallCallField node)
    {
        defaultOut(node);
    }

    @Override
    public void caseACallCallField(ACallCallField node) throws TypeException, SemanticException {
        inACallCallField(node);
        {
            List<PExpr> copy = new ArrayList<PExpr>(node.getParams());
            Collections.reverse(copy);
            for(PExpr e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        outACallCallField(node);
    }

    public void inASingleDcl(ASingleDcl node)
    {
        defaultIn(node);
    }

    public void outASingleDcl(ASingleDcl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException, SemanticException {
        inASingleDcl(node);
        if(node.getExpr() != null)
        {
            node.getExpr().apply(this);
        }
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        outASingleDcl(node);
    }

    public void inAListType(AListType node)
    {
        defaultIn(node);
    }

    public void outAListType(AListType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAListType(AListType node)
    {
        inAListType(node);
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
        outAListType(node);
    }

    public void inAVarType(AVarType node)
    {
        defaultIn(node);
    }

    public void outAVarType(AVarType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVarType(AVarType node)
    {
        inAVarType(node);
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
        outAVarType(node);
    }

    public void inAElseIf(AElseIf node)
    {
        defaultIn(node);
    }

    public void outAElseIf(AElseIf node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAElseIf(AElseIf node) throws TypeException, SemanticException {
        inAElseIf(node);
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getThen());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getPredicate() != null)
        {
            node.getPredicate().apply(this);
        }
        outAElseIf(node);
    }

    public void inADclStmt(ADclStmt node)
    {
        defaultIn(node);
    }

    public void outADclStmt(ADclStmt node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException, SemanticException {
        inADclStmt(node);
        {
            List<PSingleDcl> copy = new ArrayList<PSingleDcl>(node.getDcls());
            Collections.reverse(copy);
            for(PSingleDcl e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
        outADclStmt(node);
    }

    public void inAAssignStmt(AAssignStmt node)
    {
        defaultIn(node);
    }

    public void outAAssignStmt(AAssignStmt node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException, SemanticException {
        inAAssignStmt(node);
        if(node.getExpr() != null)
        {
            node.getExpr().apply(this);
        }
        if(node.getOperation() != null)
        {
            node.getOperation().apply(this);
        }
        if(node.getVar() != null)
        {
            node.getVar().apply(this);
        }
        outAAssignStmt(node);
    }

    public void inACallStmt(ACallStmt node)
    {
        defaultIn(node);
    }

    public void outACallStmt(ACallStmt node)
    {
        defaultOut(node);
    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException, SemanticException {
        inACallStmt(node);
        if(node.getVal() != null)
        {
            node.getVal().apply(this);
        }
        outACallStmt(node);
    }

    public void inAIfStmt(AIfStmt node)
    {
        defaultIn(node);
    }

    public void outAIfStmt(AIfStmt node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException, SemanticException {
        inAIfStmt(node);
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getElse());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PElseIf> copy = new ArrayList<PElseIf>(node.getElseifs());
            Collections.reverse(copy);
            for(PElseIf e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getThen());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getPredicate() != null)
        {
            node.getPredicate().apply(this);
        }
        outAIfStmt(node);
    }

    public void inASwitchStmt(ASwitchStmt node)
    {
        defaultIn(node);
    }

    public void outASwitchStmt(ASwitchStmt node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException, SemanticException {
        inASwitchStmt(node);
        {
            List<PCase> copy = new ArrayList<PCase>(node.getCases());
            Collections.reverse(copy);
            for(PCase e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getVariable() != null)
        {
            node.getVariable().apply(this);
        }
        outASwitchStmt(node);
    }

    public void inAForStmt(AForStmt node)
    {
        defaultIn(node);
    }

    public void outAForStmt(AForStmt node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException, SemanticException {
        inAForStmt(node);
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getThen());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getUpdate() != null)
        {
            node.getUpdate().apply(this);
        }
        if(node.getPredicate() != null)
        {
            node.getPredicate().apply(this);
        }
        if(node.getInit() != null)
        {
            node.getInit().apply(this);
        }
        outAForStmt(node);
    }

    public void inAForeachStmt(AForeachStmt node)
    {
        defaultIn(node);
    }

    public void outAForeachStmt(AForeachStmt node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAForeachStmt(AForeachStmt node) throws TypeException, SemanticException {
        inAForeachStmt(node);
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getThen());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getList() != null)
        {
            node.getList().apply(this);
        }
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        outAForeachStmt(node);
    }

    public void inAWhileStmt(AWhileStmt node)
    {
        defaultIn(node);
    }

    public void outAWhileStmt(AWhileStmt node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException, SemanticException {
        inAWhileStmt(node);
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getThen());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getPredicate() != null)
        {
            node.getPredicate().apply(this);
        }
        outAWhileStmt(node);
    }

    public void inAReturnStmt(AReturnStmt node)
    {
        defaultIn(node);
    }

    public void outAReturnStmt(AReturnStmt node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException, SemanticException {
        inAReturnStmt(node);
        if(node.getExpr() != null)
        {
            node.getExpr().apply(this);
        }
        outAReturnStmt(node);
    }

    public void inACaseCase(ACaseCase node)
    {
        defaultIn(node);
    }

    public void outACaseCase(ACaseCase node)
    {
        defaultOut(node);
    }

    @Override
    public void caseACaseCase(ACaseCase node) throws TypeException, SemanticException {
        inACaseCase(node);
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getThen());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getCase() != null)
        {
            node.getCase().apply(this);
        }
        outACaseCase(node);
    }

    public void inADefaultCase(ADefaultCase node)
    {
        defaultIn(node);
    }

    public void outADefaultCase(ADefaultCase node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADefaultCase(ADefaultCase node) throws TypeException, SemanticException {
        inADefaultCase(node);
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getThen());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        outADefaultCase(node);
    }

    public void inAMethodDcl(AMethodDcl node)
    {
        defaultIn(node);
    }

    public void outAMethodDcl(AMethodDcl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException, SemanticException {
        inAMethodDcl(node);
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getBody());
            Collections.reverse(copy);
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getReturntype() != null)
        {
            node.getReturntype().apply(this);
        }
        {
            List<PParamDcl> copy = new ArrayList<PParamDcl>(node.getParams());
            Collections.reverse(copy);
            for(PParamDcl e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        outAMethodDcl(node);
    }

    public void inAParamDcl(AParamDcl node)
    {
        defaultIn(node);
    }

    public void outAParamDcl(AParamDcl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAParamDcl(AParamDcl node) throws TypeException, SemanticException {
        inAParamDcl(node);
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
        outAParamDcl(node);
    }
}

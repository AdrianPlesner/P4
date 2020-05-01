package P4.contextualAnalysis;
import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;

public class TokenFinder extends DepthFirstAdapter {

    private Token t;

    public Token getToken(){
        return t;
    }

    @Override
    public void caseAAddOpExpr(AAddOpExpr node) throws TypeException {
        node.getL().apply(this);
    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException {
        node.getVar().apply(this);
    }

    @Override
    public void caseABoolLiteral(ABoolLiteral node) {
        t = node.getValue();
    }

    @Override
    public void caseABoolOpExpr(ABoolOpExpr node) throws TypeException {
        node.getL().apply(this);
    }

    @Override
    public void caseACallCallField(ACallCallField node) throws TypeException {
        t = node.getId();
    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException {
        node.getVal().apply(this);
    }

    @Override
    public void caseACaseCase(ACaseCase node) throws TypeException {
        node.getCase().apply(this);
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException {
        node.getDcls().getFirst().apply(this);
    }

    @Override
    public void caseAConstruct(AConstruct node) throws TypeException {
        t = node.getName();
    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException {
        node.getType().apply(this);
    }

    @Override
    public void caseADefaultCase(ADefaultCase node) throws TypeException {
        node.getThen().getFirst().apply(this);
    }

    @Override
    public void caseAElseIf(AElseIf node) throws TypeException {
        node.getPredicate().apply(this);
    }

    @Override
    public void caseAEqualityExpr(AEqualityExpr node) throws TypeException {
        node.getL().apply(this);
    }

    @Override
    public void caseAFieldCallField(AFieldCallField node) throws TypeException {
        t = node.getId();
    }

    @Override
    public void caseAFloatLiteral(AFloatLiteral node) {
        t = node.getValue();
    }

    @Override
    public void caseAForeachStmt(AForeachStmt node) throws TypeException {
        t = node.getId();
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException {
        node.getInit().apply(this);
    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException {
        node.getPredicate().apply(this);
    }

    @Override
    public void caseAIntLiteral(AIntLiteral node) {
        t = node.getValue();
    }

    @Override
    public void caseAListExpr(AListExpr node) throws TypeException {
        node.getElements().getFirst().apply(this);
    }

    @Override
    public void caseAListType(AListType node) throws TypeException {
        t = node.getType();
    }

    @Override
    public void caseALiteralExpr(ALiteralExpr node) throws TypeException {
        node.getValue().apply(this);
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException {
        t = node.getName();
    }

    @Override
    public void caseAMultOpExpr(AMultOpExpr node) throws TypeException {
        node.getL().apply(this);
    }

    @Override
    public void caseAParamDcl(AParamDcl node) throws TypeException {
        node.getType().apply(this);
    }

    @Override
    public void caseAProg(AProg node) throws TypeException {
        t = node.getIncludes().getFirst();
    }

    @Override
    public void caseARelationExpr(ARelationExpr node) throws TypeException {
        node.getL().apply(this);
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException {
        node.getExpr().apply(this);
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException {
        node.getCard().apply(this);
    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException {
        t = node.getId();
    }

    @Override
    public void caseAStringLiteral(AStringLiteral node) {
        t = node.getValue();
    }

    @Override
    public void caseASubclass(ASubclass node) throws TypeException {
        t = node.getName();
    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException {
        node.getVariable().apply(this);
    }

    @Override
    public void caseAVal(AVal node) throws TypeException {
        node.getCallField().getFirst().apply(this);
    }

    @Override
    public void caseAValueExpr(AValueExpr node) throws TypeException {
        node.getVal().apply(this);
    }

    @Override
    public void caseAVarType(AVarType node) throws TypeException {
        t = node.getType();
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException {
        node.getPredicate().apply(this);
    }

    @Override
    public void caseTAddOp(TAddOp node) {
        t = node;
    }

    @Override
    public void caseTAssign(TAssign node) {
        t = node;
    }

    @Override
    public void caseTBlank(TBlank node) {
        t = node;
    }

    @Override
    public void caseTBoolLiteral(TBoolLiteral node) {
        t = node;
    }

    @Override
    public void caseTBoolOp(TBoolOp node) {
        t = node;
    }

    @Override
    public void caseTCard(TCard node) {
        t = node;
    }

    @Override
    public void caseTCase(TCase node) {
        t = node;
    }

    @Override
    public void caseTColon(TColon node) {
        t = node;
    }

    @Override
    public void caseTComment(TComment node) {
        t = node;
    }

    @Override
    public void caseTConst(TConst node) {
        t = node;
    }

    @Override
    public void caseTDefault(TDefault node) {
        t = node;
    }

    @Override
    public void caseTDot(TDot node) {
        t = node;
    }

    @Override
    public void caseTElse(TElse node) {
        t = node;
    }

    @Override
    public void caseTEndCon(TEndCon node) {
        t = node;
    }

    @Override
    public void caseTEqualOp(TEqualOp node) {
        t = node;
    }

    @Override
    public void caseTFloatLiteral(TFloatLiteral node) {
        t = node;
    }

    @Override
    public void caseTFor(TFor node) {
        t = node;
    }

    @Override
    public void caseTForeach(TForeach node) {
        t = node;
    }

    @Override
    public void caseTFun(TFun node) {
        t = node;
    }

    @Override
    public void caseTGame(TGame node) {
        t = node;
    }

    @Override
    public void caseTId(TId node) {
        t = node;
    }

    @Override
    public void caseTIf(TIf node) {
        t = node;
    }

    @Override
    public void caseTIn(TIn node) {
        t = node;
    }

    @Override
    public void caseTInc(TInc node) {
        t = node;
    }

    @Override
    public void caseTIntLiteral(TIntLiteral node) {
        t = node;
    }

    @Override
    public void caseTLArr(TLArr node) {
        t = node;
    }

    @Override
    public void caseTLBox(TLBox node) {
        t = node;
    }

    @Override
    public void caseTLBrack(TLBrack node) {
        t = node;
    }

    @Override
    public void caseTList(TList node) {
        t = node;
    }

    @Override
    public void caseTLParen(TLParen node) {
        t = node;
    }

    @Override
    public void caseTMoves(TMoves node) {
        t = node;
    }

    @Override
    public void caseTMultOp(TMultOp node) {
        t = node;
    }

    @Override
    public void caseTPlayer(TPlayer node) {
        t = node;
    }

    @Override
    public void caseTRArr(TRArr node) {
        t = node;
    }

    @Override
    public void caseTRBox(TRBox node) {
        t = node;
    }

    @Override
    public void caseTRBrack(TRBrack node) {
        t = node;
    }

    @Override
    public void caseTRelationOp(TRelationOp node) {
        t = node;
    }

    @Override
    public void caseTReturn(TReturn node) {
        t = node;
    }

    @Override
    public void caseTRParen(TRParen node) {
        t = node;
    }

    @Override
    public void caseTSemi(TSemi node) {
        t = node;
    }

    @Override
    public void caseTSeparator(TSeparator node) {
        t = node;
    }

    @Override
    public void caseTSetup(TSetup node) {
        t = node;
    }

    @Override
    public void caseTString(TString node) {
        t = node;
    }

    @Override
    public void caseTSub(TSub node) {
        t = node;
    }

    @Override
    public void caseTSwitch(TSwitch node) {
        t = node;
    }

    @Override
    public void caseTTurn(TTurn node) {
        t = node;
    }

    @Override
    public void caseTTypeof(TTypeof node) {
        t = node;
    }

    @Override
    public void caseTWhile(TWhile node) {
        t = node;
    }

    @Override
    public void caseStart(Start node) throws TypeException {
        node.getPProg().apply(this);
    }

}

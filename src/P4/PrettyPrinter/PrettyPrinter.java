package P4.PrettyPrinter;

import P4.CodeGenarator.SemanticException;
import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;
import P4.contextualAnalysis.TypeException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PseudoColumnUsage;

public class PrettyPrinter extends DepthFirstAdapter {

    private String name;
    private String path;
    private File file;
    private FileWriter fw;
    private String s = "";
    private int tabs = 0;

    private void emitTabs(String n){
        for(int i = 0; i < tabs; i++){
            s=s.concat("\t");
        }
        emit(n);
    }

    private void emit(String n){
        s = s.concat(n);
    }

    public PrettyPrinter(String name,String path){
        this.name = name;
        this.path = path;
    }

    public void Print(Start ast) throws IOException, SemanticException, TypeException {
        file = new File(path +"/" + name + "PP" + ".cl");

        ast.apply(this);

        if(file.exists()){
            if(!file.delete()){
                throw new IOException("Could not delete " + file.getAbsolutePath());
            }
        }
        if( file.createNewFile()) {
            fw = new FileWriter(file);
            fw.write(s);
            fw.close();
        }
        else{
            throw new IOException("Could not create directory: " + file.getAbsolutePath());
        }

    }

    @Override
    public void caseStart(Start node) throws TypeException, SemanticException {
        node.getPProg().apply(this);
    }

    @Override
    public void defaultOut(Node node) {
        tabs--;
        emitTabs("}\n");
    }

    @Override
    public void caseAProg(AProg node) throws TypeException, SemanticException {
        for(TId inc : node.getIncludes()){
            emitTabs("include " + inc.getText() + ";\n");
        }
        node.getSetup().apply(this);

        emitTabs("Moves{\n");
        tabs++;
        for(PMethodDcl m : node.getMoves()){
            m.apply(this);
        }
        defaultOut(node);

        emitTabs("Turn{\n");
        tabs++;
        for(PStmt s : node.getTurn()){
            s.apply(this);
        }
        defaultOut(node);

        emitTabs("EndCondition{\n");
        tabs++;
        for(PStmt s: node.getEndCondition()){
            s.apply(this);
        }
        defaultOut(node);

        for(PMethodDcl m : node.getMethods()){
            m.apply(this);
        }
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException, SemanticException {
        inASetup(node);
        emitTabs("Card{\n");
        tabs++;
        node.getCard().apply(this);
        defaultOut(node);

        emitTabs("Player{\n");
        tabs++;
        node.getPlayer().apply(this);
        defaultOut(node);

        emitTabs("Game{\n");
        tabs++;
        for(PStmt s: node.getGame()){
            s.apply(this);
        }
        defaultOut(node);

        outASetup(node);
    }

    @Override
    public void inASetup(ASetup node) {
        emitTabs("Setup{\n");
        tabs++;
    }


    @Override
    public void caseAClassBody(AClassBody node) throws TypeException, SemanticException {

        for(PStmt s: node.getDcls()){
            s.apply(this);
        }
        if(node.getConstruct()!= null) {
            node.getConstruct().apply(this);
        }

        for(PMethodDcl m : node.getMethods()){
            m.apply(this);
        }

        for(PSubclass s : node.getSubclasses()){
            s.apply(this);
        }
    }

    @Override
    public void caseASubclass(ASubclass node) throws TypeException, SemanticException {
        inASubclass(node);
        node.getBody().apply(this);
        outASubclass(node);
    }

    @Override
    public void inASubclass(ASubclass node) {
        emitTabs("Subclass " + node.getName().getText() + "{\n");
        tabs++;
    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException, SemanticException {
        emitTabs("");
        node.getType().apply(this);
        for(PSingleDcl s : node.getDcls()){
            s.apply(this);
            if(s != node.getDcls().getLast()){
                emit(", ");
            }
        }
        emit(";\n");
    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException, SemanticException {
        emit(" ");
        node.getId().apply(this);
        if(node.getExpr() != null) {
            emit(" = ");
            node.getExpr().apply(this);
        }
    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException, SemanticException {
        emitTabs("");
        node.getVal().apply(this);
        emit(";\n");
    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException, SemanticException {
        emitTabs("");
        node.getVar().apply(this);
        emit(" ");
        node.getOperation().apply(this);
        emit(" ");
        node.getExpr().apply(this);
        emit(";\n");
    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException, SemanticException {
        emitTabs("if ");
        node.getPredicate().apply(this);
        emit(" {\n");
        tabs++;
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        defaultOut(node);
        for(PElseIf e : node.getElseifs()){
            e.apply(this);
        }
        if(node.getElse().size() > 0) {
            emitTabs("else {\n");
            tabs++;
            for (PStmt s : node.getElse()) {
                s.apply(this);
            }
            outAIfStmt(node);
        }
    }

    @Override
    public void caseAElseIf(AElseIf node) throws TypeException, SemanticException {
        emitTabs("else if ");
        node.getPredicate().apply(this);
        emit(" {\n");
        tabs++;
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        outAElseIf(node);
    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException, SemanticException {
        emitTabs("switch ");
        node.getVariable().apply(this);
        emit(" {\n");
        tabs++;
        for(PCase c : node.getCases()){
            c.apply(this);
        }
        outASwitchStmt(node);
    }

    @Override
    public void caseACaseCase(ACaseCase node) throws TypeException, SemanticException {
        emitTabs("case ");
        node.getCase().apply(this);
        emit(" :{\n");
        tabs++;
        for(PStmt s: node.getThen()){
            s.apply(this);
        }
        outACaseCase(node);
    }

    @Override
    public void caseADefaultCase(ADefaultCase node) throws TypeException, SemanticException {
        emitTabs("default :{\n");
        tabs++;
        for(PStmt s: node.getThen()){
            s.apply(this);
        }
        outADefaultCase(node);
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException, SemanticException {
        emitTabs("return ");
        node.getExpr().apply(this);
        emit(";\n");
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException, SemanticException {
        emitTabs("while ");
        node.getPredicate().apply(this);
        emit(" {\n");
        tabs++;
        for (PStmt s : node.getThen()){
            s.apply(this);
        }
        outAWhileStmt(node);
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException, SemanticException {
        emitTabs("for ");
        ADclStmt init = (ADclStmt) node.getInit();
        init.getType().apply(this);
        for(PSingleDcl s : init.getDcls()){
            s.apply(this);
        }
        emit(" ; ");

        node.getPredicate().apply(this);
        emit(" ; ");
        AAssignStmt ass = (AAssignStmt) node.getUpdate();
        ass.getVar().apply(this);
        emit(" ");
        ass.getOperation().apply(this);
        emit(" ");
        ass.getExpr().apply(this);
        emit( " {\n");
        tabs++;
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        outAForStmt(node);
    }

    @Override
    public void caseAForeachStmt(AForeachStmt node) throws TypeException, SemanticException {
        emitTabs("for ");
        node.getId().apply(this);
        emit(" in ");
        node.getList().apply(this);
        emit(" {\n");
        tabs++;
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        outAForeachStmt(node);
    }

    @Override
    public void caseAVal(AVal node) throws TypeException, SemanticException {
        emit("");
        for(PCallField c : node.getCallField()){
            c.apply(this);
            if(c != node.getCallField().getLast()){
                emit(".");
            }
        }
    }

    @Override
    public void caseACallCallField(ACallCallField node) throws TypeException, SemanticException {
        node.getId().apply(this);
        emit("(");
        for(PExpr e : node.getParams()){
            e.apply(this);
            if(e != node.getParams().getLast()){
                emit(", ");
            }
        }
        emit(")");
    }

    @Override
    public void caseAFieldCallField(AFieldCallField node) throws TypeException, SemanticException {
        node.getId().apply(this);
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException, SemanticException {
        emitTabs("Function ");
        node.getName().apply(this);
        emit("(");
        for(PParamDcl p : node.getParams()){
            p.apply(this);
            if(p != node.getParams().getLast()){
                emit(", ");
            }
        }
        emit(") typeof ");
        node.getReturntype().apply(this);
        emit("{\n");
        tabs++;
        for(PStmt s : node.getBody()){
            s.apply(this);
        }
        outAMethodDcl(node);
    }

    @Override
    public void caseAListType(AListType node) throws TypeException, SemanticException {
        emit("List typeof ");
        node.getType().apply(this);
    }

    @Override
    public void caseAVarType(AVarType node) throws TypeException, SemanticException {
        node.getType().apply(this);
    }

    @Override
    public void caseAListExpr(AListExpr node) throws TypeException, SemanticException {
        emit("{ ");
        for(PExpr e : node.getElements()){
            e.apply(this);

            emit("; ");

        }
        emit("}");
    }

    @Override
    public void caseALiteralExpr(ALiteralExpr node) throws TypeException, SemanticException {
        node.getValue().apply(this);
    }

    @Override
    public void caseAValueExpr(AValueExpr node) throws TypeException, SemanticException {
        node.getVal().apply(this);
    }

    @Override
    public void caseAEqualityExpr(AEqualityExpr node) throws TypeException, SemanticException {
        emit("(");
        node.getL().apply(this);
        emit(" ");
        node.getOperator().apply(this);
        emit(" ");
        node.getR().apply(this);
        emit(")");
    }

    @Override
    public void caseARelationExpr(ARelationExpr node) throws TypeException, SemanticException {
        emit("(");
        node.getL().apply(this);
        emit(" ");
        node.getOperator().apply(this);
        emit(" ");
        node.getR().apply(this);
        emit(")");
    }

    @Override
    public void caseAAddOpExpr(AAddOpExpr node) throws TypeException, SemanticException {
        emit("(");
        node.getL().apply(this);
        emit(" ");
        node.getOperator().apply(this);
        emit(" ");
        node.getR().apply(this);
        emit(")");
    }

    @Override
    public void caseABoolOpExpr(ABoolOpExpr node) throws TypeException, SemanticException {
        emit("(");
        node.getL().apply(this);
        emit(" ");
        node.getOperator().apply(this);
        emit(" ");
        node.getR().apply(this);
        emit(")");
    }

    @Override
    public void caseAMultOpExpr(AMultOpExpr node) throws TypeException, SemanticException {
        emit("(");
        node.getL().apply(this);
        emit(" ");
        node.getOperator().apply(this);
        emit(" ");
        node.getR().apply(this);
        emit(")");
    }

    @Override
    public void caseAParamDcl(AParamDcl node) throws TypeException, SemanticException {
        node.getType().apply(this);
        emit(" ");
        node.getName().apply(this);
    }

    @Override
    public void caseABoolLiteral(ABoolLiteral node) throws SemanticException {
        node.getValue().apply(this);
    }

    @Override
    public void caseAFloatLiteral(AFloatLiteral node) {
        node.getValue().apply(this);
    }

    @Override
    public void caseAIntLiteral(AIntLiteral node) {
        node.getValue().apply(this);
    }

    @Override
    public void caseAStringLiteral(AStringLiteral node) {
        node.getValue().apply(this);
    }

    @Override
    public void caseAConstruct(AConstruct node) throws TypeException, SemanticException {
        emitTabs("Construct ");
        node.getName().apply(this);
        emit("(");
        for(PParamDcl p : node.getParams()){
            p.apply(this);
            if(p != node.getParams().getLast()){
                emit(", ");
            }
        }
        emit(") {\n");
        tabs++;
        for(PStmt s : node.getBody()){
            s.apply(this);
        }
        outAConstruct(node);
    }

    @Override
    public void caseTId(TId node) {
        emit(node.getText());
    }

    @Override
    public void caseTRelationOp(TRelationOp node) {
        emit(node.getText());
    }

    @Override
    public void caseTMultOp(TMultOp node) {
        emit(node.getText());
    }

    @Override
    public void caseTEqualOp(TEqualOp node) {
        emit(node.getText());
    }

    @Override
    public void caseTBoolOp(TBoolOp node) {
        emit(node.getText());
    }

    @Override
    public void caseTAddOp(TAddOp node) {
        emit(node.getText());
    }

    @Override
    public void caseTIntLiteral(TIntLiteral node) {
         emit(node.getText());
    }

    @Override
    public void caseTFloatLiteral(TFloatLiteral node) {
        emit(node.getText());
    }

    @Override
    public void caseTBoolLiteral(TBoolLiteral node) {
        emit(node.getText());
    }

    @Override
    public void caseTAssign(TAssign node) {
        emit(node.getText());
    }

    @Override
    public void caseTString(TString node) {
        emit(node.getText());
    }
}

package P4.CodeGenarator;

import P4.Sable.node.*;
import P4.contextualAnalysis.SymbolTable;
import P4.contextualAnalysis.TypeException;

import java.util.HashMap;

public class MethodGenerator extends CodeGenerator {
    public MethodGenerator(HashMap<String,String> h){
        files = h;
    }

    private StackCounter sc = new StackCounter();

    private Boolean Static = false;

    public void SetStatic(Boolean b){
        Static = b;
    }

    @Override
    public void caseAProg(AProg node) throws TypeException {
        for(Start s: node.includes){
            s.getPProg().apply(this);
        }
        node.getSetup().apply(this);

    }

    @Override
    public void caseASetup(ASetup node) throws TypeException {
        SetStatic(false);
        current = "card";
        node.getCard().apply(this);
        current = "player";
        node.getPlayer().apply(this);
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException {
        var cons = node.getConstruct();
        if(cons == null){
            // default constructor
            emit(".method public <init>()V \n" +
                    "    aload_0\n" +
                    "    invokenonvirtual java/lang/Object/<init>()V\n" +
                    "    return\n" +
                    ".end method\n"
            );
        }
        else{
            cons.apply(this);
        }
        for(PMethodDcl md : node.getMethods()){
            md.apply(this);
        }
    }

    @Override
    public void caseAConstruct(AConstruct node) throws TypeException {
        inAConstruct(node);
        emit("(");
        for(PParamDcl p : node.getParams()){
            p.apply(this);
        }
        emit(")V\n");
        emit(".limit stack " + sc.Count(node) + "\n");
        for(PStmt s : node.getBody()){
            s.apply(this);
        }
        outAConstruct(node);
    }

    @Override
    public void inAConstruct(AConstruct node) {
        emit(".method public <init>");
    }

    @Override
    public void inAMethodDcl(AMethodDcl node) {
        emit(".method public ");
        if(Static){
            emit("static ");
        }
        emit(node.getName().getText());
    }


    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException {
        inAMethodDcl(node);
        emit("(");
        for(PParamDcl pd : node.getParams()){
            pd.apply(this);
        }
        emit(")");
        node.getReturntype().apply(this);

        emit(".limit stack " + sc.Count(node) + "\n");
        for(PStmt st : node.getBody()){
            st.apply(this);
        }
        outAMethodDcl(node);
    }

    @Override
    public void outAConstruct(AConstruct node) {
        emit(".end method\n\n");
    }

    @Override
    public void outAMethodDcl(AMethodDcl node) {
        emit(".end method\n\n");
    }

}

package P4.CodeGenarator;

import P4.Sable.node.*;
import P4.contextualAnalysis.SymbolTable;
import P4.contextualAnalysis.TypeException;

import java.util.HashMap;

public class MethodGenerator extends CodeGenerator {
    public MethodGenerator(HashMap<String,String> h, String n){
        files = h;
        name = n;
    }

    private String current;
    private Boolean Static = false;

    public void SetStatic(Boolean b){
        Static = b;
    }

    private void emit(String s){
        super.emit(current,s);
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
        }
        else{
            cons.apply(this);
        }
        for(PMethodDcl md : node.getMethods()){
            md.apply(this);
        }
    }

    @Override
    public void inAMethodDcl(AMethodDcl node) {
        emit(".method public ");
        if(Static){
            emit("static ");
        }
        emit(node.getName().getText() + "(");
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException {
        inAMethodDcl(node);
        for(PParamDcl pd : node.getParams()){
            pd.apply(this);
        }
        emit(")");
        node.getReturntype().apply(this);
        emit("\n");
        //TODO: get stack limit

        // emit(".limit stack " + sc.count(node) + "\n");
        for(PStmt st : node.getBody()){
            st.apply(this);
        }

        emit(".end method");
    }
}

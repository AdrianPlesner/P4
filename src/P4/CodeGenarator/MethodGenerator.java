package P4.CodeGenarator;

import P4.Sable.node.*;
import P4.contextualAnalysis.Symbol.Function;
import P4.contextualAnalysis.Symbol.SubClass;
import P4.contextualAnalysis.SymbolTable;
import P4.contextualAnalysis.TypeException;

import java.util.HashMap;

public class MethodGenerator extends CodeGenerator {
    public MethodGenerator(HashMap<String,String> h){
        new MethodGenerator(h,null);
    }

    public MethodGenerator(HashMap<String,String> h, SymbolTable st){
        files = h;
        this.st = st;
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
        //TODO: count locals
        emit(".limit locals " +((Function)st.retrieveSymbol(current, Function.class)).getLocals() + "\n");
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
        // emit method head
        inAMethodDcl(node);
        emit("(");
        // emit parameters
        for(PParamDcl pd : node.getParams()){
            pd.apply(this);
        }
        emit(")");
        // emit return type
        node.getReturntype().apply(this);
        emit("\n");
        // count stack size
        emit(".limit stack " + sc.Count(node) + "\n");
        var fun = st.retrieveSymbol(node.getName().getText());
        if(fun == null){
            var c = st.retrieveSymbol(current,SubClass.class);
            if(c == null){
                //TODO: some error
            }
            else{
                fun = ((SubClass)c).containsMethod(node.getName().getText());
            }
        }
        emit(".limit locals " +((Function)fun).getLocals() + "\n");
        // emit body
        for(PStmt st : node.getBody()){
            st.apply(this);
        }
        // emit method tail
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

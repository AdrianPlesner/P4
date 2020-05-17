package P4.CodeGenarator;

import P4.Sable.node.*;
import P4.contextualAnalysis.SymbolTable;
import P4.contextualAnalysis.TypeException;

import java.lang.reflect.Field;
import java.util.HashMap;

public class SubClassGenerator extends CodeGenerator {
    public SubClassGenerator(HashMap<String,String> h){
        files = h;
        fg = new FieldGenerator(files);
        mg = new MethodGenerator(files);
    }

    private FieldGenerator fg;
    private MethodGenerator mg;

    @Override
    public void caseAProg(AProg node) throws TypeException {
        for(Start s : node.includes){
            s.getPProg().apply(this);
        }
        node.getSetup().apply(this);
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException {
        current = "player";
        for(PSubclass s : ((AClassBody) node.getPlayer()).getSubclasses()){
            s.apply(this);
        }
        current = "card";
        for(PSubclass s : ((AClassBody) node.getCard()).getSubclasses()){
            s.apply(this);
        }
    }

    @Override
    public void caseASubclass(ASubclass node) throws TypeException {
        var name = node.getName().getText();
        files.put(name,"");
        String s = ".class " + name + "\n"
                + ".super " + current + "\n";
        current = name;
        emit(s);
        node.getBody().apply(this);
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException {
        fg.current = current;
        node.apply(fg);
        mg.current = current;
        node.apply(mg);
        for(PSubclass s : node.getSubclasses()){
            node.apply(this);
        }
    }
}

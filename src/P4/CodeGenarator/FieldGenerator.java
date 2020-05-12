package P4.CodeGenarator;

import P4.Sable.node.AClassBody;
import P4.Sable.node.AProg;
import P4.Sable.node.ASetup;
import P4.Sable.node.Start;
import P4.contextualAnalysis.SymbolTable;
import P4.contextualAnalysis.TypeException;

import java.util.HashMap;

public class FieldGenerator extends CodeGenerator {
    public FieldGenerator(HashMap<String,String> h){
        files = h;
    }

    @Override
    public void caseAProg(AProg node) throws TypeException {
        node.getSetup().apply(this);
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException {
        node.getCard().apply(this);
        node.getPlayer().apply(this);
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException {

    }
}


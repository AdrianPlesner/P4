package P4.CodeGenarator;

import P4.Sable.node.AProg;
import P4.Sable.node.Start;
import P4.contextualAnalysis.SymbolTable;
import P4.contextualAnalysis.TypeException;

import java.util.HashMap;

public class SubClassGenerator extends CodeGenerator {
    public SubClassGenerator(HashMap<String,String> h){
        files = h;
    }

    @Override
    public void caseAProg(AProg node) throws TypeException {

    }
}

package P4.CodeGenarator;

import P4.Sable.node.Start;
import P4.contextualAnalysis.SymbolTable;

import java.util.HashMap;

public class MethodGenerator extends CodeGenerator {
    public MethodGenerator(HashMap<String,String> h){
        files = h;
    }
}

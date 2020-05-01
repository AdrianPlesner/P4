package P4.CodeGenarator;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.Start;
import P4.contextualAnalysis.SymbolTable;

public class CodeGenerator extends DepthFirstAdapter {

    private Start ast;
    private SymbolTable st;

    public CodeGenerator(Start ast, SymbolTable st) {
        this.ast = ast;
        this.st = st;
    }

    public void generate(){

    }

    private void emit(String s){
        
    }
}

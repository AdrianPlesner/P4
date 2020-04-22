package P4.contextualAnalysis;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.Start;

public class TypeChecker extends DepthFirstAdapter {

    private Start ast;
    private SymbolTable st;

    public TypeChecker(Start ast, SymbolTable st) {
        this.ast = ast;
        this.st = st;
    }
}

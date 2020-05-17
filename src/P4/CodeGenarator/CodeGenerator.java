package P4.CodeGenarator;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;
import P4.contextualAnalysis.SymbolTable;
import P4.contextualAnalysis.TypeException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CodeGenerator extends DepthFirstAdapter {

    private Start ast;
    protected SymbolTable st;

    protected String name;
    protected String current;

    protected HashMap<String,String> files = new HashMap<>();

    private FieldGenerator fg;
    private MethodGenerator mg;
    private SubClassGenerator sg;

    public CodeGenerator(){}

    public CodeGenerator(Start ast, SymbolTable st, String n) {
        this.ast = ast;
        this.st = st;
        if(n == null){
            name = "Game";
        }
        else{
            name = n;
        }
        files.put(name,"");
        fg = new FieldGenerator(files);
        mg = new MethodGenerator(files,st);
        sg = new SubClassGenerator(files);
    }

    public void generate() throws TypeException {
        ast.apply(this);
    }

    public void writeFiles() throws IOException {
        writeFiles("");
    }

    public final void writeFiles(String path) throws IOException {
        if(path.equals("")){
            path = System.getProperty("user.dir");
        }
        for(var key : files.keySet()){
            File f = new File(path +"/" + key + ".j");

            if(f.exists()){
                if(!f.delete()){
                    throw new IOException("Could not delete " + f.getAbsolutePath());
                }
            }
            if(f.createNewFile()) {

                FileWriter fw = new FileWriter(f);
                fw.write(files.get(key));
                fw.close();
            }
            else{
                throw new IOException("Could not create directory: " + f.getAbsolutePath());
            }
        }
    }

    protected void emit(String file, String s){
        files.put(file,files.get(file).concat(s));
    }
    protected void emit(String s){
        emit(current,s);
    }

    @Override
    public void inAProg(AProg node) {
        String s = ".class " + name + "\n"
                + ".super java/lang/Object\n"
                + ".method public <init>()V\n"
                    + "aload_0\n"
                    + "invokeonvirtual java/lang/Object/<init>()V\n"
                    + "return\n"
                + ".end method\n\n";
        emit(name,s);
        files.put("player","");
        s = ".class player\n"
            + ".super java/lang/Object\n";
        emit("player",s);
        files.put("card","");
        s = ".class card\n"
            + ".super java/Lang/Object\n";
        emit("card",s);
        s = ".method public static main([Ljava/lang/String)V\n";
        emit(name,s);
    }

    @Override
    public void caseAProg(AProg node) throws TypeException {
        inAProg(node);
        // fields fase
        node.apply(fg);
        // Methods fase
        node.apply(mg);
        // Subclasses fase
        node.apply(sg);
        current = name;
        //todo: emit stack and locals sizes

        node.getSetup().apply(this);

        //TODO: turn - endcondition loop

        // main end
        emit(name,".end method\n\n");
        mg.current = name;
        mg.SetStatic(true);
        for(PMethodDcl s : node.getMethods()){
            s.apply(mg);
        }
        for(PMethodDcl s : node.getMoves()){
            s.apply(mg);
        }
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException {
        for(PStmt s : node.getGame()){
            s.apply(this);
        }
    }

    @Override
    public void caseAVarType(AVarType node) throws TypeException {
        String t, type = node.getType().getText();

        switch(type) {
            case "int":
                t = "I";
                break;
            case "float":
                t = "F";
                break;
            case "string":
                t = "Ljava/lang/String;";
                break;
            case "bool":
                t = "Z";
                break;
            case "void":
                t = "V";
                break;
            default:
                t = "L"+type+";";
                break;
        }
        emit(current, t);
    }

    @Override
    public void caseAListType(AListType node) {
        emit(current, "Ljava/util/LinkedList;");
    }
}

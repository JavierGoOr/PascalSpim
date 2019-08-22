import java.util.Vector;

public class SymbolTable {
    private static SymbolTable instance;
    private boolean error;
    private Namespace namespace;

    private SymbolTable() {
        error = false;
        namespace = new Namespace("1");
    }

    public static SymbolTable getInstance() {
        if (instance == null)
            instance = new SymbolTable();
        return instance;
    }

    public void dump() {
        namespace.dump();
    }

    public void addSymbol(SymbolTableEntry entry) {
        namespace.addSymbol(entry);
    }

    public SymbolTableEntry getSymbol(String name, String scope) {
        return namespace.getSymbol(name, scope);
    }

    public boolean canBeOveridden(String name, String scope) {
        return namespace.canBeOveridden(name, scope);
    }

    public void generateCode() {
        namespace.generateCode();
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Vector getLocalVariables(String scope) {
        return namespace.getLocalVariables(scope);
    }
}
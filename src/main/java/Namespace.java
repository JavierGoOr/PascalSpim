import java.util.Vector;

public class Namespace {
    private Vector symbols = new Vector();
    private Vector namespaces = new Vector();
    private String scope;

    public Namespace(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void dump() {
        SymbolTableEntry ste;
        for (int i = 0; i < symbols.size(); i++) {
            ste = (SymbolTableEntry) symbols.elementAt(i);
            System.out.println(ste.getName() + " " + ste.getScope() + " " + ste.getObject());
        }
        for (int i = 0; i < namespaces.size(); i++) {
            ((Namespace) namespaces.elementAt(i)).dump();
        }
    }

    public void addSymbol(SymbolTableEntry ste) {
        if (ste.getScope().startsWith(scope)) {
            if (scope.compareTo(ste.getScope()) == 0)
                symbols.add(ste);
            else {
                String subscope = ste.getScope().substring(scope.length() + 1);
                int sc;
                Namespace nm;
                if (subscope.indexOf('-') != -1)
                    sc = Integer.parseInt(subscope.substring(0, subscope.indexOf('-')));
                else
                    sc = Integer.parseInt(subscope);
                if (sc < namespaces.size())
                    nm = (Namespace) namespaces.elementAt(sc);
                else {
                    namespaces.setSize(sc + 1);
                    nm = new Namespace(scope + "-" + sc);
                    namespaces.setElementAt(nm, sc);
                }
                nm.addSymbol(ste);
            }
        }
    }

    public Vector getLocalVariables(String fscope) {
        Vector result = new Vector();
        if (scope.compareTo(fscope) != 0) {
            String subscope = fscope.substring(scope.length() + 1);
            int sc;
            Namespace nm;
            if (subscope.indexOf('-') != -1)
                sc = Integer.parseInt(subscope.substring(0, subscope.indexOf('-')));
            else
                sc = Integer.parseInt(subscope);
            if (sc < namespaces.size()) {
                nm = (Namespace) namespaces.elementAt(sc);
                if (nm != null)
                    result = nm.getLocalVariables(fscope);
                else
                    result = new Vector();
            }
        } else {
            result = new Vector();
            SymbolTableEntry entry;
            Variable v;
            for (int i = 0; i < symbols.size(); i++) {
                entry = (SymbolTableEntry) symbols.elementAt(i);
                if (entry.getObject() instanceof Variable) {
                    v = (Variable) entry.getObject();
                    if (v.getIsLocal())
                        result.add(entry);
                }
            }
        }
        return result;
    }

    public SymbolTableEntry getSymbol(String name, String stescope) {
        SymbolTableEntry entry = null;
        boolean found;
        int i;
        if (stescope.startsWith(scope)) {
            if (scope.compareTo(stescope) != 0) {
                String subscope = stescope.substring(scope.length() + 1);
                int sc;
                Namespace nm;
                if (subscope.indexOf('-') != -1)
                    sc = Integer.parseInt(subscope.substring(0, subscope.indexOf('-')));
                else
                    sc = Integer.parseInt(subscope);
                if (sc < namespaces.size()) {
                    nm = (Namespace) namespaces.elementAt(sc);
                    if (nm != null)
                        entry = nm.getSymbol(name, stescope);
                }
            }
            if (entry == null) {
                i = 0;
                found = false;
                while (i < symbols.size() & !found) {
                    entry = (SymbolTableEntry) symbols.elementAt(i);
                    if (entry.getName().compareTo(name) == 0)
                        found = true;
                    i++;
                }
                if (!found)
                    entry = null;
            }
        }
        return entry;
    }

    public boolean canBeOveridden(String name, String stescope) {
        SymbolTableEntry entry = null;
        boolean found, result = true;
        int i;
        if (stescope.startsWith(scope)) {
            if (scope.compareTo(stescope) != 0) {
                String subscope = stescope.substring(scope.length() + 1);
                int sc;
                Namespace nm;
                if (subscope.indexOf('-') != -1)
                    sc = Integer.parseInt(subscope.substring(0, subscope.indexOf('-')));
                else
                    sc = Integer.parseInt(subscope);
                if (sc < namespaces.size()) {
                    nm = (Namespace) namespaces.elementAt(sc);
                    if (nm != null)
                        result = nm.canBeOveridden(name, stescope);
                }
            } else {
                i = 0;
                found = false;
                while (i < symbols.size() & !found) {
                    entry = (SymbolTableEntry) symbols.elementAt(i);
                    if (entry.getName().compareTo(name) == 0)
                        found = true;
                    i++;
                }
                if (found)
                    result = false;
            }
        }
        return result;
    }

    public void generateCode() {
        Code code = Code.getInstance();
        if (scope.compareTo("1") == 0) {
            SymbolTableEntry entry;
            String lineBegin;
            for (int i = 0; i < symbols.size(); i++) {
                entry = (SymbolTableEntry) symbols.elementAt(i);
                lineBegin = "\t_" + entry.getName() + ":\t";
                if (entry.getObject() instanceof Variable) {
                    Variable var = (Variable) entry.getObject();
                    if ((var.getType() instanceof IntegerType) ||
                            (var.getType() instanceof CharType) ||
                            (var.getType() instanceof BooleanType)) {
                        code.addDataSentence(lineBegin + ".word 0");
                    } else if (var.getType() instanceof RealType) {
                        code.addDataSentence(lineBegin + ".double 0.0");
                    } else if (var.getType() instanceof ArrayType) {
                        ArrayType at = (ArrayType) var.getType();
                        if ((at.getFinalElsType() instanceof IntegerType) ||
                                (at.getFinalElsType() instanceof CharType) ||
                                (at.getFinalElsType() instanceof BooleanType)) {
                            code.addDataSentence(lineBegin + ".word 0:" + at.getTotalNumberOfElements());
                        } else if (at.getFinalElsType() instanceof RealType) {
                            code.addDataSentence(lineBegin + ".space " + (at.getTotalNumberOfElements() * 8));
                        }
                    }
                }
				/*else if(entry.getObject() instanceof StringConstant)
				{
					StringConstant cte = (StringConstant) entry.getObject();
					code.addDataSentence(lineBegin + ".asciiz \"" + cte.getValue()  + "\"");
				}*/
            }
        }
    }
}
package org.pascal2spim;

import org.pascal2spim.types.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Namespace {
    private List<SymbolTableEntry> symbols = new ArrayList<>();
    private Map<Integer, Namespace> namespaces = new HashMap<>();
    private String scope;

    public Namespace(String scope) {
        this.scope = scope;
    }

    public void dump() {
        for (SymbolTableEntry ste : symbols) {
            System.out.println(ste.getName() + " " + ste.getScope() + " " + ste.getObject());
        }
        for (Namespace namespace : namespaces.values()) {
            namespace.dump();
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
                if (namespaces.containsKey(sc))
                    nm = namespaces.get(sc);
                else {
                    nm = new Namespace(scope + "-" + sc);
                    namespaces.put(sc, nm);
                }
                nm.addSymbol(ste);
            }
        }
    }

    public List<SymbolTableEntry> getLocalVariables(String fscope) {
        List<SymbolTableEntry> result = new ArrayList<>();
        if (scope.compareTo(fscope) != 0) {
            String subscope = fscope.substring(scope.length() + 1);
            int sc;
            Namespace nm;
            if (subscope.indexOf('-') != -1)
                sc = Integer.parseInt(subscope.substring(0, subscope.indexOf('-')));
            else
                sc = Integer.parseInt(subscope);
            if (namespaces.containsKey(sc)) {
                nm = namespaces.get(sc);
                result = nm.getLocalVariables(fscope);
            }
        } else {
            result = new ArrayList<>();
            for (SymbolTableEntry entry : symbols) {
                if (entry.getObject() instanceof Variable) {
                    Variable v = (Variable) entry.getObject();
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
                if (namespaces.containsKey(sc)) {
                    nm = namespaces.get(sc);
                    entry = nm.getSymbol(name, stescope);
                }
            }
            if (entry == null) {
                i = 0;
                found = false;
                while (i < symbols.size() & !found) {
                    entry = symbols.get(i);
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
                if (namespaces.containsKey(sc)) {
                    nm = namespaces.get(sc);
                    result = nm.canBeOveridden(name, stescope);
                }
            } else {
                i = 0;
                found = false;
                while (i < symbols.size() & !found) {
                    entry = symbols.get(i);
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

    public void generateCode(GeneratedAssembly generatedAssembly) {
        if (scope.compareTo("1") == 0) {
            String lineBegin;
            for (SymbolTableEntry entry : symbols) {
                lineBegin = "\t_" + entry.getName() + ":\t";
                if (entry.getObject() instanceof Variable) {
                    Variable var = (Variable) entry.getObject();
                    if ((var.getType() instanceof IntegerType) ||
                            (var.getType() instanceof CharType) ||
                            (var.getType() instanceof BooleanType)) {
                        generatedAssembly.addDataDefinition(lineBegin + ".word 0");
                    } else if (var.getType() instanceof RealType) {
                        generatedAssembly.addDataDefinition(lineBegin + ".double 0.0");
                    } else if (var.getType() instanceof ArrayType) {
                        ArrayType at = (ArrayType) var.getType();
                        if ((at.getFinalElsType() instanceof IntegerType) ||
                                (at.getFinalElsType() instanceof CharType) ||
                                (at.getFinalElsType() instanceof BooleanType)) {
                            generatedAssembly.addDataDefinition(lineBegin + ".word 0:" + at.getTotalNumberOfElements());
                        } else if (at.getFinalElsType() instanceof RealType) {
                            generatedAssembly.addDataDefinition(lineBegin + ".space " + (at.getTotalNumberOfElements() * 8));
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
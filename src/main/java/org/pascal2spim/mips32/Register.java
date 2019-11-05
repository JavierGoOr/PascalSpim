package org.pascal2spim.mips32;

import org.pascal2spim.language.variables.Variable;
import org.pascal2spim.language.variables.VariableApparition;

public class Register {
    private boolean free;
    private boolean fpoint;
    private String name;
    private VariableApparition variable;
    private Register index;
    private int ordering;
    private boolean notStore;

    public Register(String name, boolean fpoint) {
        free = true;
        this.fpoint = fpoint;
        this.name = name;
        variable = null;
        index = null;
        ordering = -1;
        notStore = false;
    }

    public boolean getNotStore() {
        return notStore;
    }

    public void setNotStore(boolean ns) {
        notStore = ns;
    }

    public boolean isFPoint() {
        return fpoint;
    }

    public int getOrdering() {
        return ordering;
    }

    public VariableApparition getVariable() {
        return variable;
    }

    public void setVariable(VariableApparition variable, RegisterManager registerManager) {
        this.variable = variable;
        ordering = registerManager.giveNumber();
    }

    public void changeState(VariableApparition variable, Register index, int ordering, boolean free, boolean ns) {
        this.variable = variable;
        this.index = index;
        this.ordering = ordering;
        this.free = free;
        notStore = ns;
    }

    public void setVariable(VariableApparition variable, int newOrdering) {
        this.variable = variable;
        ordering = newOrdering;
    }

    public Register getIndex() {
        return index;
    }

    public void setIndex(Register ind) {
        index = ind;
    }

    public boolean getFree() {
        return free;
    }

    public void checkAndLiberate(GeneratedAssembly generatedAssembly) {
        if (variable == null || index != null) {
            if (index != null)
                index.liberate(generatedAssembly);
            this.liberate(generatedAssembly);
        }
    }

    public void liberate(GeneratedAssembly generatedAssembly) {
        this.free = true;
        if (variable != null) {
            Variable vaux = (Variable) variable.getDescription().getObject();
            if (fpoint) {
                if (index == null) {
                    if (vaux.getIsLocal())
                        generatedAssembly.addCodeLine("s.d " + name + ", " + (vaux.getDisplacement() * -1) + "($fp)");
                    else {
                        generatedAssembly.addCodeLine("s.d " + name + ", _" + variable.getDescription().getName());
                    }
                }
            } else {
                if (index == null) {
                    if (vaux.getIsLocal())
                        generatedAssembly.addCodeLine("sw " + name + ", " + (vaux.getDisplacement() * -1) + "($fp)");
                    else {
                        generatedAssembly.addCodeLine("sw " + name + ", _" + variable.getDescription().getName());
                    }
                }
            }
            variable = null;
            index = null;
            ordering = -1;
        }
        if (index != null)
            index.checkAndLiberate(generatedAssembly);
    }

    public void save(GeneratedAssembly generatedAssembly) {
        if (variable != null) {
            Variable vaux = (Variable) variable.getDescription().getObject();
            if (fpoint) {
                if (index == null) {
                    if (vaux.getIsLocal())
                        generatedAssembly.addCodeLine("s.d " + name + ", " + (vaux.getDisplacement() * -1) + "($fp)");
                    else {
                        generatedAssembly.addCodeLine("s.d " + name + ", _" + variable.getDescription().getName());
                    }
                }
            } else {
                if (index == null) {
                    if (vaux.getIsLocal())
                        generatedAssembly.addCodeLine("sw " + name + ", " + (vaux.getDisplacement() * -1) + "($fp)");
                    else {
                        generatedAssembly.addCodeLine("sw " + name + ", _" + variable.getDescription().getName());
                    }
                }
            }
        }
    }

    public void liberateIfGlobalVariable(GeneratedAssembly generatedAssembly) {
        if (variable != null && (variable.getDescription().getScope().compareTo("1") == 0)) {
            if (fpoint) {
                if (index == null) {
                    generatedAssembly.addCodeLine("s.d " + name + ", _" + variable.getDescription().getName());
                }
            } else {
                if (index == null) {
                    generatedAssembly.addCodeLine("sw " + name + ", _" + variable.getDescription().getName());
                }
            }
            //free = true;
            //variable = null;
            //index = null;
            //ordering = -1;
        }
    }

    public void reloadIfGlobalVariable(GeneratedAssembly generatedAssembly) {
        if (variable != null && (variable.getDescription().getScope().compareTo("1") == 0)) {
            Variable vaux = (Variable) variable.getDescription().getObject();
            if (fpoint) {
                if (index == null) {
                    generatedAssembly.addCodeLine("l.d " + name + ", _" + variable.getDescription().getName());
                }
            } else {
                if (index == null) {
                    generatedAssembly.addCodeLine("lw " + name + ", _" + variable.getDescription().getName());
                }
            }
        }
    }

    public void reserve() {
        free = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Register getState() {
        Register result = new Register(name, fpoint);
        result.ordering = ordering;
        result.free = free;
        result.variable = variable;
        result.index = index;
        result.notStore = notStore;
        return result;
    }
}
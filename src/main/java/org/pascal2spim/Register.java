package org.pascal2spim;

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

    public void setVariable(VariableApparition variable) {
        RegisterManager rm = RegisterManager.getInstance();
        this.variable = variable;
        ordering = rm.giveNumber();
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

    public void checkAndLiberate() {
        if (variable == null || index != null) {
            if (index != null)
                index.liberate();
            this.liberate();
        }
    }

    public void liberate() {
        this.free = true;
        if (variable != null) {
            Variable vaux = (Variable) variable.getDescription().getObject();
            Code code = Code.getInstance();
            if (fpoint) {
                if (index == null) {
                    if (vaux.getIsLocal())
                        code.addSentence("s.d " + name + ", " + (vaux.getDisplacement() * -1) + "($fp)");
                    else {
                        code.addSentence("s.d " + name + ", _" + variable.getDescription().getName());
                    }
                }
            } else {
                if (index == null) {
                    if (vaux.getIsLocal())
                        code.addSentence("sw " + name + ", " + (vaux.getDisplacement() * -1) + "($fp)");
                    else {
                        code.addSentence("sw " + name + ", _" + variable.getDescription().getName());
                    }
                }
            }
            variable = null;
            index = null;
            ordering = -1;
        }
        if (index != null)
            index.checkAndLiberate();
    }

    public void save() {
        if (variable != null) {
            Variable vaux = (Variable) variable.getDescription().getObject();
            Code code = Code.getInstance();
            if (fpoint) {
                if (index == null) {
                    if (vaux.getIsLocal())
                        code.addSentence("s.d " + name + ", " + (vaux.getDisplacement() * -1) + "($fp)");
                    else {
                        code.addSentence("s.d " + name + ", _" + variable.getDescription().getName());
                    }
                }
            } else {
                if (index == null) {
                    if (vaux.getIsLocal())
                        code.addSentence("sw " + name + ", " + (vaux.getDisplacement() * -1) + "($fp)");
                    else {
                        code.addSentence("sw " + name + ", _" + variable.getDescription().getName());
                    }
                }
            }
        }
    }

    public void liberateIfGlobalVariable() {
        if (variable != null && (variable.getDescription().getScope().compareTo("1") == 0)) {
            Variable vaux = (Variable) variable.getDescription().getObject();
            Code code = Code.getInstance();
            if (fpoint) {
                if (index == null) {
                    code.addSentence("s.d " + name + ", _" + variable.getDescription().getName());
                }
            } else {
                if (index == null) {
                    code.addSentence("sw " + name + ", _" + variable.getDescription().getName());
                }
            }
            //free = true;
            //variable = null;
            //index = null;
            //ordering = -1;
        }
    }

    public void reloadIfGlobalVariable() {
        if (variable != null && (variable.getDescription().getScope().compareTo("1") == 0)) {
            Variable vaux = (Variable) variable.getDescription().getObject();
            Code code = Code.getInstance();
            if (fpoint) {
                if (index == null) {
                    code.addSentence("l.d " + name + ", _" + variable.getDescription().getName());
                }
            } else {
                if (index == null) {
                    code.addSentence("lw " + name + ", _" + variable.getDescription().getName());
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
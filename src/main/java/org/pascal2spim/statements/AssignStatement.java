package org.pascal2spim.statements;

import org.pascal2spim.*;
import org.pascal2spim.types.RealType;

public class AssignStatement extends Statement {
    private SymbolTableEntry functName; //function name
    private VariableApparition var;     //variable : only one of these two will have an actual value
    private Expression value;

    public AssignStatement(SymbolTableEntry functName, Expression e) {
        this.functName = functName;
        this.var = null;
        this.value = e;
    }

    public AssignStatement(VariableApparition var, Expression e) {
        this.functName = null;
        this.var = var;
        this.value = e;
    }

    public Statement hasReturnStatement() {
        if (functName != null)
            return this;
        else
            return null;
    }

    public SymbolTableEntry getFunctName() {
        return functName;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    public void generateCode() {
        RegisterManager rm = RegisterManager.getInstance();
        Code code = Code.getInstance();
        value.generateCode();
        if (functName != null) {
            Function f = (Function) functName.getObject();
            if (f.getReturnType() instanceof RealType) {
                if (value.getRegister().isFPoint()) {
                    value.getRegister().checkAndLiberate();
                    code.addSentence("mov.d $f30, " + value.getRegister().getName());
                } else {
                    value.getRegister().checkAndLiberate();
                    code.addSentence("mtc1 " + value.getRegister().getName() + ", $f30");
                    code.addSentence("cvt.d.w $f30, $f30");
                }
            } else {
                value.getRegister().checkAndLiberate();
                code.addSentence("move $v0, " + value.getRegister().getName());
            }
        } else if (var != null) {
            Register reg = rm.getRegisterOfVar(var);
            if (reg == null) {
                reg = value.getRegister();
                if (var instanceof IndexedVariable) //array, I assume
                {
                    ((IndexedVariable) var).generateCode(true); //only the index, variable is not loaded
                    if (reg.isFPoint()) {
                        reg.checkAndLiberate();
                        code.addSentence("s.d " + reg.getName() + ", (" + var.getRegister().getIndex().getName() + ")");
                    } else {
                        if (var.getType() instanceof RealType) {
                            reg.checkAndLiberate();
                            code.addSentence("mtc1 " + reg.getName() + ", " + var.getRegister().getName());
                            code.addSentence("cvt.d.w " + var.getRegister().getName() + ", " + var.getRegister().getName());
                            code.addSentence("s.d " + var.getRegister().getName() + ", (" + var.getRegister().getIndex().getName() + ")");
                        } else {
                            reg.checkAndLiberate();
                            code.addSentence("sw " + reg.getName() + ", (" + var.getRegister().getIndex().getName() + ")");
                        }
                    }
                    var.getRegister().liberate();
                } else if (var.getType() instanceof RealType) {
                    if (reg.isFPoint())
                        reg.setVariable(var);
                    else {
                        reg.checkAndLiberate();
                        Register r2 = rm.getFreeFloatRegister();
                        code.addSentence("mtc1 " + reg.getName() + ", " + r2.getName());
                        code.addSentence("cvt.d.w " + r2.getName() + ", " + r2.getName());
                        r2.setVariable(var);
                    }
                } else
                    reg.setVariable(var);
            } else {
                if (var.getType() instanceof RealType) {
                    if (value.getRegister().isFPoint()) {
                        value.getRegister().checkAndLiberate();
                        code.addSentence("mov.d " + reg.getName() + ", " + value.getRegister().getName());
                    } else {
                        value.getRegister().checkAndLiberate();
                        code.addSentence("mtc1 " + value.getRegister().getName() + ", " + reg.getName());
                        code.addSentence("cvt.d.w " + reg.getName() + ", " + reg.getName());
                    }
                } else {
                    value.getRegister().checkAndLiberate();
                    code.addSentence("move " + reg.getName() + ", " + value.getRegister().getName());
                }
            }
        }
    }
}
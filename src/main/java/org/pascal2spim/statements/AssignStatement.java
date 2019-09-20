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

    public void generateCode(Code code, RegisterManager registerManager) {
        value.generateCode(code, registerManager);
        if (functName != null) {
            Function f = (Function) functName.getObject();
            if (f.getReturnType() instanceof RealType) {
                if (value.getRegister().isFPoint()) {
                    value.getRegister().checkAndLiberate(code);
                    code.addSentence("mov.d $f30, " + value.getRegister().getName());
                } else {
                    value.getRegister().checkAndLiberate(code);
                    code.addSentence("mtc1 " + value.getRegister().getName() + ", $f30");
                    code.addSentence("cvt.d.w $f30, $f30");
                }
            } else {
                value.getRegister().checkAndLiberate(code);
                code.addSentence("move $v0, " + value.getRegister().getName());
            }
        } else if (var != null) {
            Register reg = registerManager.getRegisterOfVar(var);
            if (reg == null) {
                reg = value.getRegister();
                if (var instanceof IndexedVariable) //array, I assume
                {
                    ((IndexedVariable) var).generateCode(code, registerManager, true); //only the index, variable is not loaded
                    if (reg.isFPoint()) {
                        reg.checkAndLiberate(code);
                        code.addSentence("s.d " + reg.getName() + ", (" + var.getRegister().getIndex().getName() + ")");
                    } else {
                        if (var.getType() instanceof RealType) {
                            reg.checkAndLiberate(code);
                            code.addSentence("mtc1 " + reg.getName() + ", " + var.getRegister().getName());
                            code.addSentence("cvt.d.w " + var.getRegister().getName() + ", " + var.getRegister().getName());
                            code.addSentence("s.d " + var.getRegister().getName() + ", (" + var.getRegister().getIndex().getName() + ")");
                        } else {
                            reg.checkAndLiberate(code);
                            code.addSentence("sw " + reg.getName() + ", (" + var.getRegister().getIndex().getName() + ")");
                        }
                    }
                    var.getRegister().liberate(code);
                } else if (var.getType() instanceof RealType) {
                    if (reg.isFPoint())
                        reg.setVariable(var, registerManager);
                    else {
                        reg.checkAndLiberate(code);
                        Register r2 = registerManager.getFreeFloatRegister(code);
                        code.addSentence("mtc1 " + reg.getName() + ", " + r2.getName());
                        code.addSentence("cvt.d.w " + r2.getName() + ", " + r2.getName());
                        r2.setVariable(var, registerManager);
                    }
                } else
                    reg.setVariable(var, registerManager);
            } else {
                if (var.getType() instanceof RealType) {
                    if (value.getRegister().isFPoint()) {
                        value.getRegister().checkAndLiberate(code);
                        code.addSentence("mov.d " + reg.getName() + ", " + value.getRegister().getName());
                    } else {
                        value.getRegister().checkAndLiberate(code);
                        code.addSentence("mtc1 " + value.getRegister().getName() + ", " + reg.getName());
                        code.addSentence("cvt.d.w " + reg.getName() + ", " + reg.getName());
                    }
                } else {
                    value.getRegister().checkAndLiberate(code);
                    code.addSentence("move " + reg.getName() + ", " + value.getRegister().getName());
                }
            }
        }
    }
}
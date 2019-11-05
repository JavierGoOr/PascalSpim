package org.pascal2spim.language.statements;

import org.pascal2spim.language.expressions.Expression;
import org.pascal2spim.language.functions.Function;
import org.pascal2spim.language.types.RealType;
import org.pascal2spim.language.variables.IndexedVariable;
import org.pascal2spim.language.variables.VariableApparition;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.Register;
import org.pascal2spim.mips32.RegisterManager;
import org.pascal2spim.symboltable.SymbolTableEntry;

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

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        value.generateCode(generatedAssembly, registerManager);
        if (functName != null) {
            Function f = (Function) functName.getObject();
            if (f.getReturnType() instanceof RealType) {
                if (value.getRegister().isFPoint()) {
                    value.getRegister().checkAndLiberate(generatedAssembly);
                    generatedAssembly.addCodeLine("mov.d $f30, " + value.getRegister().getName());
                } else {
                    value.getRegister().checkAndLiberate(generatedAssembly);
                    generatedAssembly.addCodeLine("mtc1 " + value.getRegister().getName() + ", $f30");
                    generatedAssembly.addCodeLine("cvt.d.w $f30, $f30");
                }
            } else {
                value.getRegister().checkAndLiberate(generatedAssembly);
                generatedAssembly.addCodeLine("move $v0, " + value.getRegister().getName());
            }
        } else if (var != null) {
            Register reg = registerManager.getRegisterOfVar(var);
            if (reg == null) {
                reg = value.getRegister();
                if (var instanceof IndexedVariable) //array, I assume
                {
                    ((IndexedVariable) var).generateCode(generatedAssembly, registerManager, true); //only the index, variable is not loaded
                    if (reg.isFPoint()) {
                        reg.checkAndLiberate(generatedAssembly);
                        generatedAssembly.addCodeLine("s.d " + reg.getName() + ", (" + var.getRegister().getIndex().getName() + ")");
                    } else {
                        if (var.getType() instanceof RealType) {
                            reg.checkAndLiberate(generatedAssembly);
                            generatedAssembly.addCodeLine("mtc1 " + reg.getName() + ", " + var.getRegister().getName());
                            generatedAssembly.addCodeLine("cvt.d.w " + var.getRegister().getName() + ", " + var.getRegister().getName());
                            generatedAssembly.addCodeLine("s.d " + var.getRegister().getName() + ", (" + var.getRegister().getIndex().getName() + ")");
                        } else {
                            reg.checkAndLiberate(generatedAssembly);
                            generatedAssembly.addCodeLine("sw " + reg.getName() + ", (" + var.getRegister().getIndex().getName() + ")");
                        }
                    }
                    var.getRegister().liberate(generatedAssembly);
                } else if (var.getType() instanceof RealType) {
                    if (reg.isFPoint())
                        reg.setVariable(var, registerManager);
                    else {
                        reg.checkAndLiberate(generatedAssembly);
                        Register r2 = registerManager.getFreeFloatRegister(generatedAssembly);
                        generatedAssembly.addCodeLine("mtc1 " + reg.getName() + ", " + r2.getName());
                        generatedAssembly.addCodeLine("cvt.d.w " + r2.getName() + ", " + r2.getName());
                        r2.setVariable(var, registerManager);
                    }
                } else
                    reg.setVariable(var, registerManager);
            } else {
                if (var.getType() instanceof RealType) {
                    if (value.getRegister().isFPoint()) {
                        value.getRegister().checkAndLiberate(generatedAssembly);
                        generatedAssembly.addCodeLine("mov.d " + reg.getName() + ", " + value.getRegister().getName());
                    } else {
                        value.getRegister().checkAndLiberate(generatedAssembly);
                        generatedAssembly.addCodeLine("mtc1 " + value.getRegister().getName() + ", " + reg.getName());
                        generatedAssembly.addCodeLine("cvt.d.w " + reg.getName() + ", " + reg.getName());
                    }
                } else {
                    value.getRegister().checkAndLiberate(generatedAssembly);
                    generatedAssembly.addCodeLine("move " + reg.getName() + ", " + value.getRegister().getName());
                }
            }
        }
    }
}
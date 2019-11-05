package org.pascal2spim.language.expressions;

import org.pascal2spim.language.operators.Operator;
import org.pascal2spim.language.types.Type;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.Register;
import org.pascal2spim.mips32.RegisterManager;

import java.util.Vector;

public class Expression extends FactorObject {
    private Register register = null;
    private SimpleExpression firstExpr;
    private Vector otherExprs = new Vector();

    public Expression() {
    }

    public Expression(SimpleExpression firstExpr) {
        this.firstExpr = firstExpr;
    }

    public Register getRegister() {
        return register;
    }

    public SimpleExpression getFirstExpr() {
        return firstExpr;
    }

    public void setFirstExpr(SimpleExpression firstExpr) {
        this.firstExpr = firstExpr;
    }

    public void addOtherExpr(Operator op, SimpleExpression se) {
        otherExprs.add(op);
        otherExprs.add(se);
    }

    public Type getType() {
        int i = 0;
        Type typeAux = firstExpr.getType();
        Operator op;
        SimpleExpression se;
        while (i < otherExprs.size()) {
            op = (Operator) otherExprs.elementAt(i);
            se = (SimpleExpression) otherExprs.elementAt(i + 1);
            typeAux = op.resultType(typeAux, se.getType());
            i += 2;
        }
        return typeAux;
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        boolean fpoint = false;
        String assemblyOp;
        Operator op;
        Register r1 = null, r2 = null;
        SimpleExpression se2;
        firstExpr.generateCode(generatedAssembly, registerManager);
        register = firstExpr.getRegister();
        for (int i = 0; i < otherExprs.size(); i += 2) {
            fpoint = false;
            op = (Operator) otherExprs.elementAt(i);
            se2 = (SimpleExpression) otherExprs.elementAt(i + 1);
            register.setNotStore(true);
            se2.generateCode(generatedAssembly, registerManager);
            register.setNotStore(false);
            if (register.isFPoint() || se2.getRegister().isFPoint()) {
                fpoint = true;
                if (!register.isFPoint()) {
                    r1 = registerManager.getFreeFloatRegister(generatedAssembly);
                    generatedAssembly.addCodeLine("mtc1 " + register.getName() + ", " + r1.getName());
                    generatedAssembly.addCodeLine("cvt.d.w " + r1.getName() + ", " + r1.getName());
                    r2 = se2.getRegister();
                } else if (!se2.getRegister().isFPoint()) {
                    r2 = registerManager.getFreeFloatRegister(generatedAssembly);
                    generatedAssembly.addCodeLine("mtc1 " + se2.getRegister().getName() + ", " + r2.getName());
                    generatedAssembly.addCodeLine("cvt.d.w " + r2.getName() + ", " + r2.getName());
                    r1 = register;
                } else {
                    r1 = register;
                    r2 = se2.getRegister();
                }
                r1.checkAndLiberate(generatedAssembly);
                r2.checkAndLiberate(generatedAssembly);
            } else {
                r1 = register;
                r2 = se2.getRegister();
            }
            register.checkAndLiberate(generatedAssembly);
            se2.getRegister().checkAndLiberate(generatedAssembly);
            assemblyOp = op.getAssemblyOp(fpoint);
            register = registerManager.getFreeRegister(generatedAssembly);
            if (fpoint) {
                generatedAssembly.addCodeLine(assemblyOp + " " + r1.getName() + ", " + r2.getName());
                boolean inverse = false;
                int n = generatedAssembly.giveSequenceValue();
                if ((op.getKind().compareTo(">") == 0) ||
                        (op.getKind().compareTo(">=") == 0) ||
                        (op.getKind().compareTo("<>") == 0)) {
                    inverse = true;
                }
                if (inverse)
                    generatedAssembly.addCodeLine("bc1t flt_else" + n);
                else
                    generatedAssembly.addCodeLine("bc1f flt_else" + n);
                generatedAssembly.addCodeLine("li " + register.getName() + ", 1");
                generatedAssembly.addCodeLine("b flt_end" + n);
                generatedAssembly.addLabel("flt_else" + n);
                generatedAssembly.addCodeLine("li " + register.getName() + ", 0");
                generatedAssembly.addLabel("flt_end" + n);
            } else
                generatedAssembly.addCodeLine(assemblyOp + " " + register.getName() + ", " + r1.getName() + ", " + r2.getName());
        }
    }
}
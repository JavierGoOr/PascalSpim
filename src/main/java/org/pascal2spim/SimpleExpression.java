package org.pascal2spim;

import org.pascal2spim.operators.Operator;
import org.pascal2spim.types.Type;

import java.util.Vector;

public class SimpleExpression {
    private Register register = null;
    private Operator firstOperator;
    private Term firstTerm;
    private Vector otherTerms = new Vector();

    public SimpleExpression(Operator firstOperator, Term firstTerm) {
        this.firstOperator = firstOperator;
        this.firstTerm = firstTerm;
    }

    public Register getRegister() {
        return register;
    }

    public Term getFirstTerm() {
        return firstTerm;
    }

    public void setFirstTerm(Term firstTerm) {
        this.firstTerm = firstTerm;
    }

    public Operator getFirstOperator() {
        return firstOperator;
    }

    public void setFirstOperator(Operator firstOperator) {
        this.firstOperator = firstOperator;
    }

    public void addOtherTerm(Operator op, Term t) {
        otherTerms.add(op);
        otherTerms.add(t);
    }

    public Type getType() {
        Type typeAux = firstTerm.getType();
        int i = 0;
        Operator op;
        Term te;
        if (firstOperator != null)
            typeAux = firstOperator.resultType(typeAux);
        while (i < otherTerms.size()) {
            op = (Operator) otherTerms.elementAt(i);
            te = (Term) otherTerms.elementAt(i + 1);
            typeAux = op.resultType(typeAux, te.getType());
            i += 2;
        }
        return typeAux;
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        String assemblyOp;
        Operator op;
        Register r1 = null, r2 = null;
        boolean fpoint = false;
        Term t2;
        firstTerm.generateCode(generatedAssembly, registerManager);
        register = firstTerm.getRegister();
        if (firstOperator != null) {
            if (firstOperator.getKind().compareTo("-") == 0) {
                if (register.isFPoint()) {
                    register.setNotStore(true);
                    Register raux_int = registerManager.getFreeRegister(generatedAssembly);
                    Register raux_float = registerManager.getFreeFloatRegister(generatedAssembly);
                    generatedAssembly.addCodeLine("li " + raux_int.getName() + ", -1");
                    generatedAssembly.addCodeLine("mtc1 " + raux_int.getName() + ", " + raux_float.getName());
                    generatedAssembly.addCodeLine("cvt.d.w " + raux_float.getName() + ", " + raux_float.getName());
                    generatedAssembly.addCodeLine("mul.d " + register.getName() + ", " + register.getName() + ", " + raux_float.getName());
                    raux_int.checkAndLiberate(generatedAssembly);
                    raux_float.checkAndLiberate(generatedAssembly);
                } else
                    generatedAssembly.addCodeLine("mul " + register.getName() + ", " + register.getName() + ", -1");
            }
        }
        for (int i = 0; i < otherTerms.size(); i += 2) {
            fpoint = false;
            op = (Operator) otherTerms.elementAt(i);
            t2 = (Term) otherTerms.elementAt(i + 1);
            register.setNotStore(true);
            t2.generateCode(generatedAssembly, registerManager);
            r1 = register;
            r2 = t2.getRegister();
            register.setNotStore(false);
            if (register.isFPoint() || t2.getRegister().isFPoint()) {
                fpoint = true;
                if (!register.isFPoint()) {
                    r1 = registerManager.getFreeFloatRegister(generatedAssembly);
                    generatedAssembly.addCodeLine("mtc1 " + register.getName() + ", " + r1.getName());
                    generatedAssembly.addCodeLine("cvt.d.w " + r1.getName() + ", " + r1.getName());
                }
                if (!t2.getRegister().isFPoint()) {
                    r2 = registerManager.getFreeFloatRegister(generatedAssembly);
                    generatedAssembly.addCodeLine("mtc1 " + t2.getRegister().getName() + ", " + r2.getName());
                    generatedAssembly.addCodeLine("cvt.d.w " + r2.getName() + ", " + r2.getName());
                }
                r1.checkAndLiberate(generatedAssembly);
                r2.checkAndLiberate(generatedAssembly);
            }
            register.checkAndLiberate(generatedAssembly);
            t2.getRegister().checkAndLiberate(generatedAssembly);
            assemblyOp = op.getAssemblyOp(fpoint);
            if (fpoint)
                register = registerManager.getFreeFloatRegister(generatedAssembly);
            else
                register = registerManager.getFreeRegister(generatedAssembly);
            generatedAssembly.addCodeLine(assemblyOp + " " + register.getName() + ", " + r1.getName() + ", " + r2.getName());
        }
    }
}
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

    public void generateCode() {
        RegisterManager rm = RegisterManager.getInstance();
        Code code = Code.getInstance();
        String assemblyOp;
        Operator op;
        Register r1 = null, r2 = null;
        boolean fpoint = false;
        Term t2;
        firstTerm.generateCode();
        register = firstTerm.getRegister();
        if (firstOperator != null) {
            if (firstOperator.getKind().compareTo("-") == 0) {
                if (register.isFPoint()) {
                    register.setNotStore(true);
                    Register raux_int = rm.getFreeRegister();
                    Register raux_float = rm.getFreeFloatRegister();
                    code.addSentence("li " + raux_int.getName() + ", -1");
                    code.addSentence("mtc1 " + raux_int.getName() + ", " + raux_float.getName());
                    code.addSentence("cvt.d.w " + raux_float.getName() + ", " + raux_float.getName());
                    code.addSentence("mul.d " + register.getName() + ", " + register.getName() + ", " + raux_float.getName());
                    raux_int.checkAndLiberate();
                    raux_float.checkAndLiberate();
                } else
                    code.addSentence("mul " + register.getName() + ", " + register.getName() + ", -1");
            }
        }
        for (int i = 0; i < otherTerms.size(); i += 2) {
            fpoint = false;
            op = (Operator) otherTerms.elementAt(i);
            t2 = (Term) otherTerms.elementAt(i + 1);
            register.setNotStore(true);
            t2.generateCode();
            r1 = register;
            r2 = t2.getRegister();
            register.setNotStore(false);
            if (register.isFPoint() || t2.getRegister().isFPoint()) {
                fpoint = true;
                if (!register.isFPoint()) {
                    r1 = rm.getFreeFloatRegister();
                    code.addSentence("mtc1 " + register.getName() + ", " + r1.getName());
                    code.addSentence("cvt.d.w " + r1.getName() + ", " + r1.getName());
                }
                if (!t2.getRegister().isFPoint()) {
                    r2 = rm.getFreeFloatRegister();
                    code.addSentence("mtc1 " + t2.getRegister().getName() + ", " + r2.getName());
                    code.addSentence("cvt.d.w " + r2.getName() + ", " + r2.getName());
                }
                r1.checkAndLiberate();
                r2.checkAndLiberate();
            }
            register.checkAndLiberate();
            t2.getRegister().checkAndLiberate();
            assemblyOp = op.getAssemblyOp(fpoint);
            if (fpoint)
                register = rm.getFreeFloatRegister();
            else
                register = rm.getFreeRegister();
            code.addSentence(assemblyOp + " " + register.getName() + ", " + r1.getName() + ", " + r2.getName());
        }
    }
}
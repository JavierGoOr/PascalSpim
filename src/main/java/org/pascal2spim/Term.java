package org.pascal2spim;

import org.pascal2spim.operators.Operator;
import org.pascal2spim.types.Type;

import java.util.Vector;

public class Term {
    private Register register = null;
    private Factor firstFactor;
    private Vector otherFactors = new Vector();

    public Term() {
    }

    public Term(Factor firstFactor) {
        this.firstFactor = firstFactor;
    }

    public Register getRegister() {
        return register;
    }

    public Factor getFirstFactor() {
        return firstFactor;
    }

    public void setFirstFactor(Factor firstFactor) {
        this.firstFactor = firstFactor;
    }

    public void addOtherFactor(Operator op, Factor f) {
        otherFactors.add(op);
        otherFactors.add(f);
    }

    public Type getType() {
        Type typeAux = firstFactor.getType();
        int i = 0;
        Operator op;
        Factor f;
        while (i < otherFactors.size()) {
            op = (Operator) otherFactors.elementAt(i);
            f = (Factor) otherFactors.elementAt(i + 1);
            typeAux = op.resultType(typeAux, f.getType());
            i += 2;
        }
        return typeAux;
    }

    public void generateCode() {
        RegisterManager rm = RegisterManager.getInstance();
        Code code = Code.getInstance();
        String assemblyOp;
        boolean fpoint = false;
        Operator op;
        Factor f2;
        Register r1 = null, r2 = null;
        firstFactor.generateCode();
        register = firstFactor.getRegister();
        for (int i = 0; i < otherFactors.size(); i += 2) {
            register.setNotStore(true);
            fpoint = false;
            op = (Operator) otherFactors.elementAt(i);
            f2 = (Factor) otherFactors.elementAt(i + 1);
            f2.generateCode();
            register.setNotStore(false);
            r1 = register;
            r2 = f2.getRegister();
            if (register.isFPoint() || f2.getRegister().isFPoint() || (op.getKind().compareTo("/") == 0)) {
                fpoint = true;
                if (!register.isFPoint()) {
                    r1 = rm.getFreeFloatRegister();
                    code.addSentence("mtc1 " + register.getName() + ", " + r1.getName());
                    code.addSentence("cvt.d.w " + r1.getName() + ", " + r1.getName());
                }
                if (!f2.getRegister().isFPoint()) {
                    r2 = rm.getFreeFloatRegister();
                    code.addSentence("mtc1 " + f2.getRegister().getName() + ", " + r2.getName());
                    code.addSentence("cvt.d.w " + r2.getName() + ", " + r2.getName());
                }
                r1.checkAndLiberate();
                r2.checkAndLiberate();
            }
            register.checkAndLiberate();
            f2.getRegister().checkAndLiberate();
            assemblyOp = op.getAssemblyOp(fpoint);
            if (fpoint)
                register = rm.getFreeFloatRegister();
            else
                register = rm.getFreeRegister();
            code.addSentence(assemblyOp + " " + register.getName() + ", " + r1.getName() + ", " + r2.getName());
        }
    }
}
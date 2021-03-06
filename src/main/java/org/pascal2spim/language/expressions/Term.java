package org.pascal2spim.language.expressions;

import org.pascal2spim.language.operators.Operator;
import org.pascal2spim.language.types.Type;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.Register;
import org.pascal2spim.mips32.RegisterManager;

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

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        String assemblyOp;
        boolean fpoint = false;
        Operator op;
        Factor f2;
        Register r1 = null, r2 = null;
        firstFactor.generateCode(generatedAssembly, registerManager);
        register = firstFactor.getRegister();
        for (int i = 0; i < otherFactors.size(); i += 2) {
            register.setNotStore(true);
            fpoint = false;
            op = (Operator) otherFactors.elementAt(i);
            f2 = (Factor) otherFactors.elementAt(i + 1);
            f2.generateCode(generatedAssembly, registerManager);
            register.setNotStore(false);
            r1 = register;
            r2 = f2.getRegister();
            if (register.isFPoint() || f2.getRegister().isFPoint() || (op.getKind().compareTo("/") == 0)) {
                fpoint = true;
                if (!register.isFPoint()) {
                    r1 = registerManager.getFreeFloatRegister(generatedAssembly);
                    generatedAssembly.addCodeLine("mtc1 " + register.getName() + ", " + r1.getName());
                    generatedAssembly.addCodeLine("cvt.d.w " + r1.getName() + ", " + r1.getName());
                }
                if (!f2.getRegister().isFPoint()) {
                    r2 = registerManager.getFreeFloatRegister(generatedAssembly);
                    generatedAssembly.addCodeLine("mtc1 " + f2.getRegister().getName() + ", " + r2.getName());
                    generatedAssembly.addCodeLine("cvt.d.w " + r2.getName() + ", " + r2.getName());
                }
                r1.checkAndLiberate(generatedAssembly);
                r2.checkAndLiberate(generatedAssembly);
            }
            register.checkAndLiberate(generatedAssembly);
            f2.getRegister().checkAndLiberate(generatedAssembly);
            assemblyOp = op.getAssemblyOp(fpoint);
            if (fpoint)
                register = registerManager.getFreeFloatRegister(generatedAssembly);
            else
                register = registerManager.getFreeRegister(generatedAssembly);
            generatedAssembly.addCodeLine(assemblyOp + " " + register.getName() + ", " + r1.getName() + ", " + r2.getName());
        }
    }
}
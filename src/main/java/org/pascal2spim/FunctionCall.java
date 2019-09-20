package org.pascal2spim;

import org.pascal2spim.types.ArrayType;
import org.pascal2spim.types.RealType;
import org.pascal2spim.types.Type;

import java.util.Vector;

public class FunctionCall extends VariableApparition {
    private Vector parameters = new Vector();

    public FunctionCall(SymbolTableEntry description) {
        super(description);
    }

    public void setParameter(int n, Expression parameter) {
        if (parameters.size() < n + 1)
            parameters.setSize(n + 1);
        parameters.setElementAt(parameter, n);
    }

    public Expression getParameter(int n) {
        return (Expression) parameters.elementAt(n);
    }

    public Type getType() {
        Function f = (Function) description.getObject();
        return f.getReturnType();
    }

    public boolean isCorrect() {
        FunctOrProc fp = (FunctOrProc) description.getObject();
        boolean correct = false;
        int i = 0;
        Expression e;
        Variable v;
        SymbolTableEntry ste;

        if (parameters.size() < fp.getNumberOfParameters())
            correct = false; //not enough parameters
        else if (parameters.size() > fp.getNumberOfParameters())
            correct = false; //too many parameters
        else {
            correct = true;
            while (i < parameters.size() && correct) {
                e = this.getParameter(i);
                ste = fp.getParameter(i);
                v = (Variable) ste.getObject();
                if (!v.getType().isCompatibleWith(e.getType()))
                    correct = false; //type mismatch
                i++;
            }
        }
        return correct;
    }

    public String toString() {
        String result = description.getName() + "(";
        Expression e;
        boolean onePar = false;
        for (int i = 0; i < parameters.size(); i++) {
            onePar = true;
            e = this.getParameter(i);
            result = result + e.getType().toString() + ", ";
        }
        if (onePar)
            result = result.substring(0, result.length() - 2) + ")";
        else
            result += ")";
        return result;
    }

    public boolean isIndexable() {
        return false;
    }

    public String getFunctionString() {
        return description.getName() + description.getObject();
    }

    public void generateCode(Code code, RegisterManager registerManager) {
        int displ = 0, tdispl = 0;
        Expression e;
        Register reg;
        FunctOrProc f = (FunctOrProc) description.getObject();
        //rm.saveGlobalVariables();
        registerManager.saveRegisters(code);
        Register[] state = registerManager.storeInStack(code);
        for (int i = 0; i < parameters.size(); i++) {
            e = (Expression) parameters.elementAt(i);
            e.generateCode(code, registerManager);
            reg = e.getRegister();
            Variable v = (Variable) f.getParameter(i).getObject();
            if (v.getType() instanceof ArrayType) {
                displ = 4;
                reg.checkAndLiberate(code);
                code.addSentence("subu $sp, $sp, " + displ);
                code.addSentence("sw " + reg.getName() + ", ($sp)");
            } else if (v.getType() instanceof RealType) {
                displ = 8;
                reg.checkAndLiberate(code);
                Register aux;
                code.addSentence("subu $sp, $sp, " + displ);
                if (reg.isFPoint()) {
                    code.addSentence("s.d " + reg.getName() + ", ($sp)");
                } else {
                    aux = registerManager.getFreeFloatRegister(code);
                    code.addSentence("mtc1 " + reg.getName() + ", " + aux.getName());
                    code.addSentence("cvt.d.w " + aux.getName() + ", " + aux.getName());
                    aux.liberate(code);
                    code.addSentence("s.d " + aux.getName() + ", ($sp)");
                }
            } else {
                displ = 4;
                reg.checkAndLiberate(code);
                code.addSentence("subu $sp, $sp, " + displ);
                code.addSentence("sw " + reg.getName() + ", ($sp)");
            }
            tdispl += displ;
        }
        code.addSentence("jal _" + description.getName());
        code.addSentence("addu $sp, $sp, " + tdispl);
        registerManager.recoverFromStack(state, code);
        registerManager.reloadGlobalVariables(code);
        if (f instanceof Function) {
            if (((Function) f).getReturnType() instanceof RealType) {
                register = registerManager.getFreeFloatRegister(code);
                code.addSentence("mov.d " + register.getName() + ", " + registerManager.getFloatResult().getName());
            } else {
                register = registerManager.getFreeRegister(code);
                code.addSentence("move " + register.getName() + ", " + registerManager.getIntResult().getName());
            }
        }
    }
}
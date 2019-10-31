package org.pascal2spim;

import org.pascal2spim.types.*;

import java.util.Vector;

/*
An object of this kind will store the expressions found
within one pair of brackets ([e1, e2, ..., eN])
*/
public class IndexedVariable extends VariableApparition {
    private VariableApparition indexedVar;
    private Vector indices = new Vector();
    private Register indexReg = null;

    public IndexedVariable(VariableApparition indexedVar) {
        super(indexedVar.getDescription());
        this.indexedVar = indexedVar;
    }

    public void setIndex(int n, Expression index) {
        if (indices.size() < (n + 1))
            indices.setSize((n + 1));
        indices.setElementAt(index, n);
    }

    public Expression getIndex(int n) {
        return (Expression) indices.elementAt(n);
    }

    public Type getType() {
        Type t = this.getIndexedType();
        return t.getElsType();
    }

    public boolean isCorrect() {
        boolean correct = true;
        Type t = this.getIndexedType();
        if (!t.isIndexable())
            correct = false; //not an indexable type
        else {
            int dims = 0;
            if (t instanceof ArrayType) {
                ArrayType at = (ArrayType) t;
                dims = at.getNumberOfDimensions();
            } else if (t instanceof StringType) {
                dims = 1;
            }
            if (dims != indices.size()) //not enough or too many
                correct = false;
            else {
                int i = 0;
                Expression e;
                Type intType = new IntegerType();
                while (i < indices.size() && correct) {
                    e = this.getIndex(i);
                    if (intType.isCompatibleWith(e.getType()))
                        correct = false;
                    i++;
                }
            }
			/*
				Controlling if the indices are out of bounds will make necessary evaluation
				of expressions at compile time.
			*/
        }
        return correct;
    }

    public int getIndexLevel() {
        return indexedVar.getIndexLevel() + 1;
    }

    public Type getIndexedType() //null if there is any error
    {
        return indexedVar.getType();
    }

    public Register getRegister() {
        return indexedVar.getRegister();
    }

    public VariableApparition getVarApparition() {
        if (indexedVar instanceof IndexedVariable)
            return ((IndexedVariable) indexedVar).getVarApparition();
        else
            return indexedVar;
    }

    public int getElementsLastDim() {
        ArrayType tp = (ArrayType) getIndexedType();
        int n = tp.getNumberOfDimensions() - 1;
        return (tp.getDimensionMax(n) - tp.getDimensionMin(n) + 1);
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        generateCode(generatedAssembly, registerManager, false);
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager, boolean onlyIndex) {
        ArrayType tp = (ArrayType) getIndexedType();
        Register regAux, varReg = null;
        int i = 0;
        Expression e1 = this.getIndex(0), e2;
        if (indexedVar instanceof IndexedVariable) {
            IndexedVariable iv = (IndexedVariable) indexedVar;
            iv.generateCode(generatedAssembly, registerManager);
            indexReg = iv.indexReg;
            indexReg.setNotStore(true);
            if (iv.getElementsLastDim() > 1)
                generatedAssembly.addCodeLine("mul " + indexReg.getName() + ", " + indexReg.getName() + ", " + iv.getElementsLastDim());
        }
        e1.generateCode(generatedAssembly, registerManager);
        e1.getRegister().checkAndLiberate(generatedAssembly);
        if (indexReg == null) {
            indexReg = registerManager.getFreeRegister(generatedAssembly);
            generatedAssembly.addCodeLine("move " + indexReg.getName() + ", " + e1.getRegister().getName());
        } else {
            indexReg.setNotStore(false);
            generatedAssembly.addCodeLine("add " + indexReg.getName() + ", " + indexReg.getName() + ", " + e1.getRegister().getName());
        }
        if (tp.getDimensionMin(0) != 0)
            generatedAssembly.addCodeLine("sub " + indexReg.getName() + ", " + indexReg.getName() + ", " + tp.getDimensionMin(0));
        for (i = 1; i < indices.size(); i++) {
            if (tp.getDimensionMax(i) != tp.getDimensionMin(i))
                generatedAssembly.addCodeLine("mul " + indexReg.getName() + ", " + indexReg.getName() + ", " + (tp.getDimensionMax(i) - tp.getDimensionMin(i) + 1));
            e2 = this.getIndex(i);
            indexReg.setNotStore(true);
            e2.generateCode(generatedAssembly, registerManager);
            indexReg.setNotStore(false);
            e2.getRegister().checkAndLiberate(generatedAssembly);
            generatedAssembly.addCodeLine("add " + indexReg.getName() + ", " + indexReg.getName() + ", " + e2.getRegister().getName());
            if (tp.getDimensionMin(i) != 0)
                generatedAssembly.addCodeLine("sub " + indexReg.getName() + ", " + indexReg.getName() + ", " + tp.getDimensionMin(i));
        }
        if (!(indexedVar instanceof IndexedVariable)) {
            varReg = null;
            if (((ArrayType) indexedVar.getType()).getFinalElsType() instanceof RealType)
                varReg = registerManager.getFreeFloatRegister(generatedAssembly);
            else
                varReg = indexReg;
            indexedVar.setRegister(varReg, registerManager);
            varReg.setIndex(indexReg);
        }
        if (getIndexedType().toString().indexOf("array") == getIndexedType().toString().lastIndexOf("array")) //the last in the hierarchy
        {
            VariableApparition va = getVarApparition();
            Variable v = (Variable) va.getDescription().getObject();
            varReg = va.getRegister();
            Register reg = registerManager.getFreeRegister(generatedAssembly);
            if (varReg.isFPoint()) {
                generatedAssembly.addCodeLine("mul " + indexReg.getName() + ", " + indexReg.getName() + ", 8");
                if (v.getIsLocal()) {
                    if (v.getIsParameter())
                        generatedAssembly.addCodeLine("lw " + reg.getName() + ", " + (v.getDisplacement() * -1) + "($fp)");
                    else
                        generatedAssembly.addCodeLine("add " + reg.getName() + ", $fp, " + (v.getDisplacement() * -1));
                } else {
                    generatedAssembly.addCodeLine("la " + reg.getName() + ", _" + indexedVar.getDescription().getName());
                }
                reg.liberate(generatedAssembly);
                generatedAssembly.addCodeLine("add " + indexReg.getName() + ", " + indexReg.getName() + ", " + reg.getName());
                if (!onlyIndex) {
                    generatedAssembly.addCodeLine("l.d " + varReg.getName() + ", (" + indexReg.getName() + ")");
                }
            } else {
                generatedAssembly.addCodeLine("mul " + indexReg.getName() + ", " + indexReg.getName() + ", 4");
                if (v.getIsLocal()) {
                    if (v.getIsParameter())
                        generatedAssembly.addCodeLine("lw " + reg.getName() + ", " + (v.getDisplacement() * -1) + "($fp)");
                    else
                        generatedAssembly.addCodeLine("add " + reg.getName() + ", $fp, " + (v.getDisplacement() * -1));
                } else {
                    generatedAssembly.addCodeLine("la " + reg.getName() + ", _" + indexedVar.getDescription().getName());
                }
                reg.liberate(generatedAssembly);
                generatedAssembly.addCodeLine("add " + indexReg.getName() + ", " + indexReg.getName() + ", " + reg.getName());
                if (!onlyIndex)
                    generatedAssembly.addCodeLine("lw " + varReg.getName() + ", (" + indexReg.getName() + ")");
            }
        }
    }
}
package org.pascal2spim.language.functions;

import org.pascal2spim.language.types.*;
import org.pascal2spim.language.variables.Variable;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.Register;
import org.pascal2spim.mips32.RegisterManager;
import org.pascal2spim.symboltable.SymbolTable;
import org.pascal2spim.symboltable.SymbolTableEntry;
import org.pascal2spim.symboltable.SymbolTableObject;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctOrProc implements SymbolTableObject {
    protected List<SymbolTableEntry> parameters = new ArrayList<>();
    protected Block block;
    protected String scope;
    protected String specialFunc = null;

    public void setSpecialFunc(String sf) {
        specialFunc = sf;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String sc) {
        scope = sc;
    }

    public void addParameter(SymbolTableEntry ste) {
        parameters.add(ste);
    }

    public SymbolTableEntry getParameter(int n) {
        return parameters.get(n);
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public int getNumberOfParameters() {
        return parameters.size();
    }

    public String toString() {
        String result = "(";
        Variable v;
        SymbolTableEntry ste;
        boolean onePar = false;
        for (int i = 0; i < parameters.size(); i++) {
            onePar = true;
            ste = parameters.get(i);
            v = (Variable) ste.getObject();
            result = result + v.getType() + ", ";
        }
        if (onePar)
            return result.substring(0, result.length() - 2) + ")";
        else
            return "()";
    }

    public void generateCode(String funcName, GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        int space;
        generatedAssembly.addLabel("_" + funcName);
        generatedAssembly.addCodeLine("subu $sp, $sp, 8");
        generatedAssembly.addCodeLine("sw $ra, 4($sp)");
        generatedAssembly.addCodeLine("sw $fp, 0($sp)");
        generatedAssembly.addCodeLine("addu $fp, $sp, 8");
        space = indexVariables();
        takeParameters(generatedAssembly, registerManager);
        generatedAssembly.addCodeLine("subu $sp, $sp, " + space);
        if (specialFunc == null)
            block.generateCode(generatedAssembly, registerManager);
        else if (specialFunc.compareTo("writeint") == 0) {
            generatedAssembly.addCodeLine("lw $a0, -12($fp)");
            generatedAssembly.addCodeLine("li $v0, 1");
            generatedAssembly.addCodeLine("syscall");
        } else if (specialFunc.compareTo("writeintln") == 0) {
            generatedAssembly.addCodeLine("lw $a0, -12($fp)");
            generatedAssembly.addCodeLine("li $v0, 1");
            generatedAssembly.addCodeLine("syscall");
            generatedAssembly.addCodeLine("la $a0, w_ln");
            generatedAssembly.addCodeLine("li $v0, 4");
            generatedAssembly.addCodeLine("syscall");
        } else if (specialFunc.compareTo("writereal") == 0) {
            generatedAssembly.addCodeLine("l.d $f12, -16($fp)");
            generatedAssembly.addCodeLine("li $v0, 3");
            generatedAssembly.addCodeLine("syscall");
        } else if (specialFunc.compareTo("writerealln") == 0) {
            generatedAssembly.addCodeLine("l.d $f12, -16($fp)");
            generatedAssembly.addCodeLine("li $v0, 3");
            generatedAssembly.addCodeLine("syscall");
            generatedAssembly.addCodeLine("la $a0, w_ln");
            generatedAssembly.addCodeLine("li $v0, 4");
            generatedAssembly.addCodeLine("syscall");
        } else if (specialFunc.compareTo("writechar") == 0) {
            generatedAssembly.addCodeLine("la $a0, w_char");
            generatedAssembly.addCodeLine("lw $v1, -12($fp)");
            generatedAssembly.addCodeLine("sb $v1, ($a0)");
            generatedAssembly.addCodeLine("li $v0, 4");
            generatedAssembly.addCodeLine("syscall");
        } else if (specialFunc.compareTo("writecharln") == 0) {
            generatedAssembly.addCodeLine("la $a0, w_char");
            generatedAssembly.addCodeLine("lw $v1, -12($fp)");
            generatedAssembly.addCodeLine("sb $v1, ($a0)");
            generatedAssembly.addCodeLine("li $v0, 4");
            generatedAssembly.addCodeLine("syscall");
            generatedAssembly.addCodeLine("la $a0, w_ln");
            generatedAssembly.addCodeLine("li $v0, 4");
            generatedAssembly.addCodeLine("syscall");
        } else if (specialFunc.compareTo("writeboolean") == 0) {
            int n = generatedAssembly.giveSequenceValue();
            generatedAssembly.addCodeLine("lw $a0, -12($fp)");
            generatedAssembly.addCodeLine("beqz $a0, __else" + n);
            generatedAssembly.addCodeLine("la $a0, w_true");
            generatedAssembly.addCodeLine("b __ifend" + n);
            generatedAssembly.addLabel("__else" + n);
            generatedAssembly.addCodeLine("la $a0, w_false");
            generatedAssembly.addLabel("__ifend" + n);
            generatedAssembly.addCodeLine("li $v0, 4");
            generatedAssembly.addCodeLine("syscall");
        } else if (specialFunc.compareTo("writebooleanln") == 0) {
            int n = generatedAssembly.giveSequenceValue();
            generatedAssembly.addCodeLine("lw $a0, -12($fp)");
            generatedAssembly.addCodeLine("beqz $a0, __else" + n);
            generatedAssembly.addCodeLine("la $a0, w_true");
            generatedAssembly.addCodeLine("b __ifend" + n);
            generatedAssembly.addLabel("__else" + n);
            generatedAssembly.addCodeLine("la $a0, w_false");
            generatedAssembly.addLabel("__ifend" + n);
            generatedAssembly.addCodeLine("li $v0, 4");
            generatedAssembly.addCodeLine("syscall");
            generatedAssembly.addCodeLine("la $a0, w_ln");
            generatedAssembly.addCodeLine("syscall");
        }
        registerManager.saveVariables(generatedAssembly);
        generatedAssembly.addCodeLine("lw $ra, -4($fp)");
        generatedAssembly.addCodeLine("move $sp, $fp");
        generatedAssembly.addCodeLine("lw $fp, -8($fp)");
        generatedAssembly.addCodeLine("jr $ra");
    }

    //Store in the variables the positions in memory where they have to be placed
    public int indexVariables() {
        SymbolTable st = SymbolTable.getInstance();
        List<SymbolTableEntry> localVariables = st.getLocalVariables(scope);
        int totalSpace = 0;
        SymbolTableEntry ste;
        Variable v;
        int varspace = 0;
        if (localVariables == null)
            localVariables = new ArrayList<>();
        for (int i = 0; i < localVariables.size(); i++) {
            ste = localVariables.get(i);
            v = (Variable) ste.getObject();
            if (v.getIsParameter()) {
                if ((v.getType() instanceof BooleanType) ||
                        (v.getType() instanceof CharType) ||
                        (v.getType() instanceof ArrayType) ||
                        (v.getType() instanceof IntegerType)) {
                    varspace = 4;
                } else if (v.getType() instanceof RealType)
                    varspace = 8;
            } else {
                if ((v.getType() instanceof BooleanType) ||
                        (v.getType() instanceof CharType) ||
                        (v.getType() instanceof IntegerType)) {
                    varspace = 4;
                } else if (v.getType() instanceof RealType)
                    varspace = 8;
                else if (v.getType() instanceof ArrayType) {
                    int num = ((ArrayType) v.getType()).getTotalNumberOfElements();
                    Type els = ((ArrayType) v.getType()).getFinalElsType();
                    if (els instanceof RealType)
                        varspace = num * 8;
                    else
                        varspace = num * 4;
                }
            }
            totalSpace += varspace;
            v.setDisplacement(8 + totalSpace);
        }
        return totalSpace;
    }

    public void takeParameters(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        SymbolTableEntry ste;
        Variable v;
        int regCounter = 0;
		/*boolean [] inRegister = new boolean[parameters.size()];
		for(int i = 0; i < parameters.size(); i++)
			inRegister[i] = false;
		for(int i = 0; i < parameters.size() && (regCounter < 4); i++)
		{
			ste = (SymbolTableEntry) parameters.elementAt(i);
			v = (Variable) ste.getObject();
			if(!(v.getType() instanceof RealType))
			{
				code.addSentence("sw $a" + regCounter + ", " + (v.getDisplacement() * -1) + "($fp)");
				regCounter++;
				inRegister[i] = true;
			}
		}
		if(parameters.size() > regCounter) //there are parameters in stack
		{*/
        List<Integer> stackdispl = new ArrayList<>();
        List<Integer> inversedispl = new ArrayList<>();
        for (int i = 0; i < parameters.size(); i++) //get displacements
        {
            ste = parameters.get(i);
            v = (Variable) ste.getObject();
            //if(!inRegister[i])
            //{
            if (v.getType() instanceof RealType)
                stackdispl.add(8);
            else
                stackdispl.add(4);
            //}
        }
        int displ = 0;
        int aux, counter = 0;
        for (int i = (stackdispl.size() - 1); i >= 0; i--) //accumulate them
        {
            inversedispl.add(displ);
            aux = stackdispl.get(i);
            displ += aux;
        }
        Register raux;
        for (int i = 0; i < parameters.size(); i++) //load parameters
        {
            ste = parameters.get(i);
            v = (Variable) ste.getObject();
            //if(!inRegister[i])
            //{
            aux = inversedispl.get(parameters.size() - counter - 1);
            counter++;
            if (v.getType() instanceof RealType) {
                raux = registerManager.getFreeFloatRegister(generatedAssembly);
                generatedAssembly.addCodeLine("l.d " + raux.getName() + ", " + aux + "($fp)");
                raux.liberate(generatedAssembly);
                generatedAssembly.addCodeLine("s.d " + raux.getName() + ", " + (v.getDisplacement() * -1) + "($fp)");
            } else {
                raux = registerManager.getFreeRegister(generatedAssembly);
                generatedAssembly.addCodeLine("lw " + raux.getName() + ", " + aux + "($fp)");
                raux.liberate(generatedAssembly);
                generatedAssembly.addCodeLine("sw " + raux.getName() + ", " + (v.getDisplacement() * -1) + "($fp)");
            }
            //}
        }
        //}
    }
}
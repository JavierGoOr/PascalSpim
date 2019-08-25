package org.pascal2spim;

import java.util.Vector;

public abstract class FunctOrProc implements SymbolTableObject {
    protected Vector parameters = new Vector();
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
        return (SymbolTableEntry) parameters.elementAt(n);
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
            ste = (SymbolTableEntry) parameters.elementAt(i);
            v = (Variable) ste.getObject();
            result = result + v.getType() + ", ";
        }
        if (onePar)
            return result.substring(0, result.length() - 2) + ")";
        else
            return "()";
    }

    public void generateCode(String funcName) {
        int space;
        Code code = Code.getInstance();
        RegisterManager rm = RegisterManager.getInstance();
        code.addLabel("_" + funcName);
        code.addSentence("subu $sp, $sp, 8");
        code.addSentence("sw $ra, 4($sp)");
        code.addSentence("sw $fp, 0($sp)");
        code.addSentence("addu $fp, $sp, 8");
        space = indexVariables();
        takeParameters();
        code.addSentence("subu $sp, $sp, " + space);
        if (specialFunc == null)
            block.generateCode();
        else if (specialFunc.compareTo("writeint") == 0) {
            code.addSentence("lw $a0, -12($fp)");
            code.addSentence("li $v0, 1");
            code.addSentence("syscall");
        } else if (specialFunc.compareTo("writeintln") == 0) {
            code.addSentence("lw $a0, -12($fp)");
            code.addSentence("li $v0, 1");
            code.addSentence("syscall");
            code.addSentence("la $a0, w_ln");
            code.addSentence("li $v0, 4");
            code.addSentence("syscall");
        } else if (specialFunc.compareTo("writereal") == 0) {
            code.addSentence("l.d $f12, -16($fp)");
            code.addSentence("li $v0, 3");
            code.addSentence("syscall");
        } else if (specialFunc.compareTo("writerealln") == 0) {
            code.addSentence("l.d $f12, -16($fp)");
            code.addSentence("li $v0, 3");
            code.addSentence("syscall");
            code.addSentence("la $a0, w_ln");
            code.addSentence("li $v0, 4");
            code.addSentence("syscall");
        } else if (specialFunc.compareTo("writechar") == 0) {
            code.addSentence("la $a0, w_char");
            code.addSentence("lw $v1, -12($fp)");
            code.addSentence("sb $v1, ($a0)");
            code.addSentence("li $v0, 4");
            code.addSentence("syscall");
        } else if (specialFunc.compareTo("writecharln") == 0) {
            code.addSentence("la $a0, w_char");
            code.addSentence("lw $v1, -12($fp)");
            code.addSentence("sb $v1, ($a0)");
            code.addSentence("li $v0, 4");
            code.addSentence("syscall");
            code.addSentence("la $a0, w_ln");
            code.addSentence("li $v0, 4");
            code.addSentence("syscall");
        } else if (specialFunc.compareTo("writeboolean") == 0) {
            int n = code.getConsecutive();
            code.addSentence("lw $a0, -12($fp)");
            code.addSentence("beqz $a0, __else" + n);
            code.addSentence("la $a0, w_true");
            code.addSentence("b __ifend" + n);
            code.addLabel("__else" + n);
            code.addSentence("la $a0, w_false");
            code.addLabel("__ifend" + n);
            code.addSentence("li $v0, 4");
            code.addSentence("syscall");
        } else if (specialFunc.compareTo("writebooleanln") == 0) {
            int n = code.getConsecutive();
            code.addSentence("lw $a0, -12($fp)");
            code.addSentence("beqz $a0, __else" + n);
            code.addSentence("la $a0, w_true");
            code.addSentence("b __ifend" + n);
            code.addLabel("__else" + n);
            code.addSentence("la $a0, w_false");
            code.addLabel("__ifend" + n);
            code.addSentence("li $v0, 4");
            code.addSentence("syscall");
            code.addSentence("la $a0, w_ln");
            code.addSentence("syscall");
        }
        rm.saveVariables();
        code.addSentence("lw $ra, -4($fp)");
        code.addSentence("move $sp, $fp");
        code.addSentence("lw $fp, -8($fp)");
        code.addSentence("jr $ra");
    }

    //Store in the variables the positions in memory where they have to be placed
    public int indexVariables() {
        SymbolTable st = SymbolTable.getInstance();
        Vector localVariables = st.getLocalVariables(scope);
        int totalSpace = 0;
        SymbolTableEntry ste;
        Variable v;
        int varspace = 0;
        if (localVariables == null)
            localVariables = new Vector();
        for (int i = 0; i < localVariables.size(); i++) {
            ste = (SymbolTableEntry) localVariables.elementAt(i);
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

    public void takeParameters() {
        Code code = Code.getInstance();
        RegisterManager rm = RegisterManager.getInstance();
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
        int displ = 0;
        Vector stackdispl = new Vector();
        Vector inversedispl = new Vector();
        for (int i = 0; i < parameters.size(); i++) //get displacements
        {
            ste = (SymbolTableEntry) parameters.elementAt(i);
            v = (Variable) ste.getObject();
            //if(!inRegister[i])
            //{
            if (v.getType() instanceof RealType)
                stackdispl.add(new Integer(8));
            else
                stackdispl.add(new Integer(4));
            //}
        }
        displ = 0;
        int aux, counter = 0;
        for (int i = (stackdispl.size() - 1); i >= 0; i--) //accumulate them
        {
            inversedispl.add(displ);
            aux = ((Integer) stackdispl.elementAt(i)).intValue();
            displ += aux;
        }
        Register raux;
        for (int i = 0; i < parameters.size(); i++) //load parameters
        {
            ste = (SymbolTableEntry) parameters.elementAt(i);
            v = (Variable) ste.getObject();
            //if(!inRegister[i])
            //{
            aux = ((Integer) inversedispl.elementAt(parameters.size() - counter - 1)).intValue();
            counter++;
            if (v.getType() instanceof RealType) {
                raux = rm.getFreeFloatRegister();
                code.addSentence("l.d " + raux.getName() + ", " + aux + "($fp)");
                raux.liberate();
                code.addSentence("s.d " + raux.getName() + ", " + (v.getDisplacement() * -1) + "($fp)");
            } else {
                raux = rm.getFreeRegister();
                code.addSentence("lw " + raux.getName() + ", " + aux + "($fp)");
                raux.liberate();
                code.addSentence("sw " + raux.getName() + ", " + (v.getDisplacement() * -1) + "($fp)");
            }
            //}
        }
        //}
    }
}
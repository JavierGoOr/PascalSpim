package org.pascal2spim;

public class RegisterManager {
    int n = 0; //number used to identify the order of loaded variables
    private Register[] registers;
    private Register[] fregisters;
    private Register fResult = null, iResult = null;

    public RegisterManager() {
        registers = new Register[20];
        fregisters = new Register[15];
		/*for(int i = 0; i < 2; i++)
		{
			registers[i] = new Register("$v" + i, false);
		}*/
        //for(int i = 2; i < 6; i++)
        for (int i = 0; i < 2; i++) {
            registers[i] = new Register("$a" + (i + 2), false);
        }
        for (int i = 2; i < 12; i++) {
            registers[i] = new Register("$t" + (i - 2), false);
        }
        for (int i = 12; i < 20; i++) {
            registers[i] = new Register("$s" + (i - 12), false);
        }
        for (int i = 0; i < fregisters.length; i++) {
            fregisters[i] = new Register("$f" + (i * 2), true);
        }
        iResult = new Register("$v0", false);
        fResult = new Register("$f30", true);
    }

    public int giveNumber() {
        int result = n;
        n++;
        return result;
    }

    public int giveCurrentNumber() {
        return n;
    }

    public Register getFreeRegister(GeneratedAssembly generatedAssembly) {
        boolean found = false;
        Register result = null;
        int i = 0;
        while (i < registers.length && !found) {
            if (registers[i].getFree()) {
                found = true;
                result = registers[i];
                registers[i].reserve();
            } else
                i++;
        }
        if (!found) {
            i = 0;
            while (i < registers.length && !found) {
                if ((registers[i].getVariable() != null) && (!registers[i].getNotStore())) {
                    found = true;
                    result = registers[i];
                    registers[i].liberate(generatedAssembly);
                    registers[i].reserve();
                } else
                    i++;
            }
        }
        return result;
    }

    public Register getFreeFloatRegister(GeneratedAssembly generatedAssembly) {
        boolean found = false;
        Register result = null;
        int i = 0;
        while (i < fregisters.length && !found) //$f30 is excluded
        {
            if (fregisters[i].getFree()) {
                found = true;
                result = fregisters[i];
                fregisters[i].reserve();
            } else
                i++;
        }
        if (!found) {
            i = 0;
            while (i < fregisters.length && !found) {
                if ((fregisters[i].getVariable() != null) && (!fregisters[i].getNotStore())) {
                    found = true;
                    result = fregisters[i];
                    fregisters[i].liberate(generatedAssembly);
                    fregisters[i].reserve();
                } else
                    i++;
            }
        }
        return result;
    }

    public Register getRegisterOfVar(VariableApparition var) {
        Register result = null;
        boolean found = false;
        int i = 0;
        if (!(var instanceof IndexedVariable)) {
            while (i < registers.length && !found) {
                if (registers[i].getVariable() != null) {
                    if (registers[i].getVariable().isEqualTo(var)) {
                        found = true;
                        result = registers[i];
                    }
                }
                i++;
            }
            if (!found) {
                i = 0;
                while (i < fregisters.length && !found) {
                    if (fregisters[i].getVariable() != null) {
                        if (fregisters[i].getVariable().isEqualTo(var)) {
                            found = true;
                            result = fregisters[i];
                        }
                    }
                    i++;
                }
            }
        }
        return result;
    }

    public void storeLoadedVars(int startNumber, GeneratedAssembly generatedAssembly) {
        for (int i = 0; i < registers.length; i++) {
            if (registers[i].getVariable() != null)
                if (registers[i].getOrdering() >= startNumber)
                    registers[i].liberate(generatedAssembly);
        }
        for (int i = 0; i < fregisters.length; i++) {
            if (fregisters[i].getVariable() != null)
                if (fregisters[i].getOrdering() >= startNumber)
                    fregisters[i].liberate(generatedAssembly);
        }
        n = startNumber;
    }

    public Register[] saveStateOfRegisters() {
        Register[] result = new Register[registers.length + fregisters.length];
        for (int i = 0; i < registers.length; i++) {
            result[i] = registers[i].getState();
        }
        for (int i = 0; i < fregisters.length; i++) {
            result[registers.length + i] = fregisters[i].getState();
        }
        return result;
    }

    public void recoverStateOfRegisters(Register[] state, GeneratedAssembly generatedAssembly) {
        Register rg;
        Variable aux = null;
        for (int i = 0; i < registers.length; i++) {
            if (state[i].getVariable() != null) {
                if ((registers[i].getVariable() == null) || (!registers[i].getVariable().isEqualTo(state[i].getVariable()))) {
                    rg = getRegisterOfVar(state[i].getVariable());
                    if (rg != null)
                        rg.liberate(generatedAssembly);
                    registers[i].liberate(generatedAssembly);
                    registers[i].reserve();
                    registers[i].setVariable(state[i].getVariable(), state[i].getOrdering());
                    aux = (Variable) state[i].getVariable().getDescription().getObject();
                    if (aux.getIsLocal())
                        generatedAssembly.addCodeLine("lw " + registers[i].getName() + ", " + (aux.getDisplacement() * -1) + "($fp)");
                    else {
                        generatedAssembly.addCodeLine("lw " + registers[i].getName() + ", _" + registers[i].getVariable().getDescription().getName());
                    }
                }
            }
        }
        for (int i = 0; i < fregisters.length; i++) {
            if (state[registers.length + i].getVariable() != null) {
                if ((fregisters[i].getVariable() == null) || (!fregisters[i].getVariable().isEqualTo(state[registers.length + i].getVariable()))) {
                    rg = getRegisterOfVar(state[registers.length + i].getVariable());
                    if (rg != null)
                        rg.liberate(generatedAssembly);
                    fregisters[i].liberate(generatedAssembly);
                    fregisters[i].reserve();
                    fregisters[i].setVariable(state[registers.length + i].getVariable(), state[registers.length + i].getOrdering());
                    aux = (Variable) state[registers.length + i].getVariable().getDescription().getObject();
                    if (aux.getIsLocal())
                        generatedAssembly.addCodeLine("l.d " + fregisters[i].getName() + ", " + (aux.getDisplacement() * -1) + "($fp)");
                    else {
                        generatedAssembly.addCodeLine("l.d " + fregisters[i].getName() + ", _" + fregisters[i].getVariable().getDescription().getName());
                    }
                }
            }
        }
    }

    public void saveVariables(GeneratedAssembly generatedAssembly) {
        for (int i = 0; i < registers.length; i++) {
            registers[i].liberate(generatedAssembly);
        }
        for (int i = 0; i < fregisters.length; i++) {
            fregisters[i].liberate(generatedAssembly);
        }
    }

    public void saveRegisters(GeneratedAssembly generatedAssembly) {
        for (int i = 0; i < registers.length; i++) {
            registers[i].save(generatedAssembly);
        }
        for (int i = 0; i < fregisters.length; i++) {
            fregisters[i].save(generatedAssembly);
        }
    }

    public void saveGlobalVariables(GeneratedAssembly generatedAssembly) {
        for (int i = 0; i < registers.length; i++) {
            registers[i].liberateIfGlobalVariable(generatedAssembly);
        }
        for (int i = 0; i < fregisters.length; i++) {
            fregisters[i].liberateIfGlobalVariable(generatedAssembly);
        }
    }

    public void reloadGlobalVariables(GeneratedAssembly generatedAssembly) {
        for (int i = 0; i < registers.length; i++) {
            registers[i].reloadIfGlobalVariable(generatedAssembly);
        }
        for (int i = 0; i < fregisters.length; i++) {
            fregisters[i].reloadIfGlobalVariable(generatedAssembly);
        }
    }

    public Register[] storeInStack(GeneratedAssembly generatedAssembly) {
        Register[] result = saveStateOfRegisters();
        for (int i = 0; i < registers.length; i++) {
            if (!registers[i].getFree()) {
                generatedAssembly.addCodeLine("subu $sp, $sp, 4");
                generatedAssembly.addCodeLine("sw " + registers[i].getName() + ", ($sp)");
                registers[i].changeState(null, null, -1, true, false);
            }
        }
        for (int i = 0; i < fregisters.length; i++) {
            if (!fregisters[i].getFree()) {
                generatedAssembly.addCodeLine("subu $sp, $sp, 8");
                generatedAssembly.addCodeLine("s.d " + fregisters[i].getName() + ", ($sp)");
                fregisters[i].changeState(null, null, -1, true, false);
            }
        }
        return result;
    }

    public void recoverFromStack(Register[] state, GeneratedAssembly generatedAssembly) {
        for (int i = (fregisters.length - 1); i >= 0; i--) {
            if (!state[registers.length + i].getFree()) {
                generatedAssembly.addCodeLine("l.d " + fregisters[i].getName() + ", ($sp)");
                generatedAssembly.addCodeLine("addu $sp, $sp, 8");
                fregisters[i].changeState(state[registers.length + i].getVariable(), state[registers.length + i].getIndex(),
                        state[registers.length + i].getOrdering(), state[registers.length + i].getFree(), state[registers.length + i].getNotStore());
            }
        }
        for (int i = (registers.length - 1); i >= 0; i--) {
            if (!state[i].getFree()) {
                generatedAssembly.addCodeLine("lw " + registers[i].getName() + ", ($sp)");
                generatedAssembly.addCodeLine("addu $sp, $sp, 4");
                registers[i].changeState(state[i].getVariable(), state[i].getIndex(), state[i].getOrdering(), state[i].getFree(), state[i].getNotStore());
            }
        }

    }

    public Register getFloatResult() {
        return fResult;
    }

    public Register getIntResult() {
        return iResult;
    }
}
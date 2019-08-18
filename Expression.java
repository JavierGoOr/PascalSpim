import java.util.Vector;
public class Expression extends FactorObject
{
	private Register register = null;
	private SimpleExpression firstExpr;
	private Vector otherExprs = new Vector();
	
	public Expression() {}
	public Expression(SimpleExpression firstExpr)
	{
		this.firstExpr = firstExpr;
	}

	public Register getRegister()
	{
		return register;
	}
	public SimpleExpression getFirstExpr()
	{
		return firstExpr;
	}
	public void setFirstExpr(SimpleExpression firstExpr)
	{
		this.firstExpr = firstExpr;
	}
	public void addOtherExpr(Operator op, SimpleExpression se)
	{
		otherExprs.add(op);
		otherExprs.add(se);
	}
	public Type getType()
	{
		int i = 0;
		Type typeAux = firstExpr.getType();
		Operator op;
		SimpleExpression se;
		while(i < otherExprs.size())
		{
			op = (Operator) otherExprs.elementAt(i);
			se = (SimpleExpression) otherExprs.elementAt(i + 1);
			typeAux = op.resultType(typeAux, se.getType());
			i += 2;
		}
		return typeAux;
	}
	public void generateCode()
	{
		RegisterManager rm = RegisterManager.getInstance();
		Code code = Code.getInstance();
		boolean fpoint = false;
		String assemblyOp;
		Operator op;
		Register r1 = null, r2 = null;
		SimpleExpression se2;
		firstExpr.generateCode();
		register = firstExpr.getRegister();
		for(int i = 0; i < otherExprs.size(); i += 2)
		{
			fpoint = false;
			op = (Operator) otherExprs.elementAt(i);
			se2 = (SimpleExpression) otherExprs.elementAt(i + 1);
			register.setNotStore(true);
			se2.generateCode();
			register.setNotStore(false);
			if(register.isFPoint() || se2.getRegister().isFPoint())
			{
				fpoint = true;
				if(!register.isFPoint())
				{
					r1 = rm.getFreeFloatRegister();
					code.addSentence("mtc1 " + register.getName() + ", " + r1.getName());
					code.addSentence("cvt.d.w " + r1.getName() + ", " + r1.getName());
					r2 = se2.getRegister();
				}
				else if(!se2.getRegister().isFPoint())
				{
					r2 = rm.getFreeFloatRegister();
					code.addSentence("mtc1 " + se2.getRegister().getName() + ", " + r2.getName());
					code.addSentence("cvt.d.w " + r2.getName() + ", " + r2.getName());
					r1 = register;
				}
				else
				{
					r1 = register;
					r2 = se2.getRegister();
				}
				r1.checkAndLiberate();
				r2.checkAndLiberate();
			}
			else
			{
				r1 = register;
				r2 = se2.getRegister();
			}
			register.checkAndLiberate();
			se2.getRegister().checkAndLiberate();
			assemblyOp = op.getAssemblyOp(fpoint);
			register = rm.getFreeRegister();
			if(fpoint)
			{
				code.addSentence(assemblyOp + " " + r1.getName() + ", " + r2.getName());
				boolean inverse = false;
				int n = code.getConsecutive();
				if((op.getKind().compareTo(">") == 0) ||
					(op.getKind().compareTo(">=") == 0) ||
					(op.getKind().compareTo("<>") == 0))
				{
					inverse = true;
				}
				if(inverse)
					code.addSentence("bc1t flt_else" + n);
				else
					code.addSentence("bc1f flt_else" + n);
				code.addSentence("li " + register.getName() + ", 1");
				code.addSentence("b flt_end" + n);
				code.addLabel("flt_else" + n);
				code.addSentence("li " + register.getName() + ", 0");
				code.addLabel("flt_end" + n);
			}
			else
				code.addSentence(assemblyOp + " " + register.getName() + ", " + r1.getName() + ", " + r2.getName());
		}
	}
}
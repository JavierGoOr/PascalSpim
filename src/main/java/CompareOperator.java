public class CompareOperator extends Operator
{
	public Type resultType(Type op1, Type op2)
	{
		Type result = null;
		if((kind.compareTo("<") == 0) ||
			(kind.compareTo(">") == 0) ||
			(kind.compareTo("<=") == 0) ||
			(kind.compareTo(">=") == 0))
		{
			if(op1.isEqualTo(new RealType()) || op1.isEqualTo(new IntegerType()))
				if(op2.isEqualTo(new RealType()) || op2.isEqualTo(new IntegerType()))
					result = new BooleanType();
		}
		else if(op1.isCompatibleWith(op2) || op2.isCompatibleWith(op1))
			result = new BooleanType();
		return result;
	}
	public Type resultType(Type op1)
	{
		return null;
	}
	public String getAssemblyOp(boolean fpoint)
	{
		String result = "";
		if(fpoint)
		{
			if(kind.compareTo("=") == 0)
				result = "c.eq.d ";
			else if(kind.compareTo("<") == 0)
				result = "c.lt.d";
			else if(kind.compareTo(">") == 0)
				result = "c.le.d";
			else if(kind.compareTo("<>") == 0)
				result = "c.eq.d ";
			else if(kind.compareTo("<=") == 0)
				result = "c.le.d";
			else if(kind.compareTo(">=") == 0)
				result = "c.lt.d";
		}
		else
		{
			if(kind.compareTo("=") == 0)
				result = "seq";
			else if(kind.compareTo("<") == 0)
				result = "slt";
			else if(kind.compareTo(">") == 0)
				result = "sgt";
			else if(kind.compareTo("<>") == 0)
				result = "sne";
			else if(kind.compareTo("<=") == 0)
				result = "sle";
			else if(kind.compareTo(">=") == 0)
				result = "sge";
		}
		return result;
	}
}
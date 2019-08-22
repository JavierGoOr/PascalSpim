import java.util.Vector;
public class BlockStatement extends Statement
{
	private Vector statements = new Vector();
	private String fname = null;
	
	public BlockStatement(){}
	public BlockStatement(String fname)
	{
		this.fname = fname;
	}
	
	public void addStatement(Statement st)
	{
		statements.add(st);
	}
	public Statement getStatement(int n)
	{
		return (Statement) statements.elementAt(n);
	}
	public void generateCode()
	{
		Statement st;
		for(int i = 0; i < statements.size(); i++)
		{
			st = (Statement) statements.elementAt(i);
			st.generateCode();
		}
	}
	public Statement hasReturnStatement()
	{
		Statement result = null, aux;
		boolean found = false;
		int i = 0;
		SymbolTableEntry ste;
		if(statements.size() > 0)
		{
			aux = (Statement) statements.elementAt(statements.size() - 1);
			if(aux instanceof AssignStatement)
				if( ((AssignStatement)aux).getFunctName() != null)
					result = aux;
		}
		return result;
	}
	public int getLine()
	{
		Statement st = hasReturnStatement();
		if(st != null)
			return st.getLine();
		else
			return this.getLine();
	}
	public int getColumn()
	{
		Statement st = hasReturnStatement();
		if(st != null)
			return st.getColumn();
		else
			return this.getColumn();
	}
}
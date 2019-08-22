public abstract class Statement
{
	protected int line;
	protected int column;
	
	public int getLine()
	{
		return line;
	}
	public void setLine(int l)
	{
		line = l;
	}
	public int getColumn()
	{
		return column;
	}
	public void setColumn(int c)
	{
		column = c;
	}
	abstract public void generateCode();
	public Statement hasReturnStatement()
	{
		return null;
	}
}

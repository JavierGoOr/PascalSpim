import java.util.Vector;
public class Code
{
	private Vector dataSegm = new Vector();
	private Vector sentences = new Vector();
	private String label = null;
	private static Code instance;
	int n = 0;
	int constN = 0;
	
	private Code(){}
	
	public static Code getInstance()
	{
		if(instance == null)
			instance = new Code();
		return instance;
	}
	
	public int getConsecutive()
	{
		int result = n;
		n++;
		return result;
	}
	public int getConsecutiveForConst()
	{
		int result = constN;
		constN++;
		return result;
	}
	public void addDataSentence(String sentence)
	{
		dataSegm.add(sentence);
	}
	public void addSentence(String sentence)
	{
		if(label == null)
			sentences.add("\t" + sentence);
		else
		{
			sentences.add(label +":\t" + sentence);
			label = null;
		}
	}
	public void addLabel(String lab)
	{
		if(label != null)
			sentences.add(label + ":");
		label = lab;
	}
	
	public boolean writeCode(String path)
	{
		FicheroEscritura fw = new FicheroEscritura(path);
		boolean opened = fw.abrir();
		if(opened)
		{
			int i = 0;
			String result = "";
			if(dataSegm.size() > 0)
			{
				fw.escribirLinea(".data");
				while(i < dataSegm.size())
				{
					fw.escribirLinea(dataSegm.elementAt(i).toString());
					i++;
				}
			}
			fw.escribirLinea(".text");
			fw.escribirLinea("\t.globl _main_");
			fw.escribir("_main_: ");
			i = 0;
			while(i < sentences.size())
			{
				fw.escribirLinea(sentences.elementAt(i).toString());
				i++;
			}
			fw.cerrar();
		}
		return opened;
	}
}
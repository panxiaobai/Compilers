import java.util.ArrayList;
import java.util.List;


public class Main {

	public static void main(String[] args) {
		List<String> strs=new ArrayList<String>();
		/*
		strs.add("S-aAbDe");
		strs.add("S-d");
		strs.add("A-BSD");
		strs.add("A-e");
		strs.add("B-SAc");
		strs.add("B-cD");
		strs.add("B-.");
		strs.add("D-Se");
		strs.add("D-.");
		*/
		strs.add("E-TA");
		strs.add("A-+TA");
		strs.add("A-.");
		strs.add("T-FB");
		strs.add("B-*FB");
		strs.add("B-.");
		strs.add("F-i");
		strs.add("F-(E)");
		
		Parser parser=new Parser(strs);
		parser.init();
		System.out.println("(i+i)*i#"+parser.analysis("(i+i)*i#"));
		System.out.println("i+i*i#"+parser.analysis("i+i*i#"));
		System.out.println("(i+*i#"+parser.analysis("(i+*i#"));
	}

}

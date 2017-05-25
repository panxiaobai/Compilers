import java.util.ArrayList;
import java.util.List;


public class Main {

	public static void main(String[] args) {
		List<String> list=new ArrayList<String>();
		list.add("S-E");
		list.add("E-aA");
		list.add("E-bB");
		list.add("A-cA");
		list.add("A-d");
		list.add("B-cB");
		list.add("B-d");
		LR0 lr0=new LR0();
		lr0.setExpression(list);
		lr0.init();

	}

}

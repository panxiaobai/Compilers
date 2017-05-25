import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ItemSet {
	private int no;
	private Map<Character,String> core=new HashMap<Character,String>();
	private Set<Map<Character,String>> set=new HashSet<Map<Character,String>>();
	private Map<Character,ItemSet> go=new HashMap<Character,ItemSet>();
	
	
	
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public Map<Character, String> getCore() {
		return core;
	}
	public void setCore(Map<Character, String> core) {
		this.core = core;
	}
	public Set<Map<Character, String>> getSet() {
		return set;
	}
	public void setSet(Set<Map<Character, String>> set) {
		this.set = set;
	}
	public Map<Character, ItemSet> getGo() {
		return go;
	}
	public void setGo(Map<Character, ItemSet> go) {
		this.go = go;
	}
	
	
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LR0 {
	//起始符
	private char startChar;
	
	private List<String> expressionString;
	
	private Map<Character,List<String>> expressionMap=new HashMap<Character,List<String>>();
	
	private List<Character> unterminators=new ArrayList<Character>();
	
	
	private Map<Character,List<String>> item=new HashMap<Character,List<String>>();
	
	public LR0(){
		
	}
	
	public void init(){
		expressionStringToMap();
		getItem();
		getItemSet();
	}
	
	public void getItemSet(){
		ItemSet first=new ItemSet();
		List<String> firstList=item.get(startChar);
		for(String s:firstList){
			if(s.charAt(0)=='.'){
				first.setNo(0);
				HashMap map=new HashMap<Character,String>();
				map.put(startChar, s);
				first.setCore(map);
				
				break;
			}
		}
	}
	
	
	public void getItem(){
		for(Character a:unterminators){
			List<String> result=new ArrayList<String>();
			List<String> list=expressionMap.get(a);
			for(String s:list){
				for(int i=0;i<s.length()+1;i++){
					String ss=s.substring(0, i)+'.'+s.substring(i,s.length());
					
					result.add(ss);
				}
			}
			item.put(a, result);
		}
		System.out.println("项目集Item:"+item);
	}
	
	//设置未经处理的正规文法
		public void setExpression(List<String> expressionString){
			this.expressionString=expressionString;
		}
		
		private void expressionStringToMap(){
			for(int i=0;i<this.expressionString.size();i++){
				String expression=expressionString.get(i);
				String[] strs=expression.split("-");
				char key=strs[0].charAt(0);
				if(i==0){
					startChar=key;
				}
				String value=strs[1];
				System.out.println(strs[0]+" "+strs[1]);
				if(expressionMap.containsKey(key)){
					expressionMap.get(key).add(value);
				}else{
					List<String> values=new ArrayList<String>();
					values.add(value);
					expressionMap.put(key, values);
					unterminators.add(key);
				}
			}
			System.out.println("起始符:"+startChar);
			System.out.println("文法Map:"+expressionMap);
			System.out.println("非终结符:"+unterminators);
		}
}

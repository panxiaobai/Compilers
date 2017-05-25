import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class Parser {
	//起始符
	private char startChar;
	
	//未经处理的正规文法
	private List<String> expressionString;
	
	//处理后的正规文法
	private Map<Character,List<String>> expressionMap=new HashMap<Character,List<String>>();
	
	private List<Character> unterminators=new ArrayList<Character>();
	//First集
	private Map<Character,List<Character>> first=new HashMap<Character,List<Character>>();
	
	//Follow集
	private Map<Character,List<Character>> follow=new HashMap<Character,List<Character>>();
	
	//Select集
	private Map<String,List<Character>> select=new HashMap<String,List<Character>>();
	
	private Map<Map<Character,Character>,String> form=new HashMap<Map<Character,Character>,String>();
	
	
	public void init(){
		expressionStringToMap();
		getFirst();
		getFollow();
		getSelect();
		if(isLL1()){
			System.out.println("是LL（1）型");
			getForm();
		}else{
			System.out.println("不是LL（1）型");
		}
		
	}
	//传入正规文法的构造函数
	public Parser(List<String> expressionString){
		this.expressionString=expressionString;
	}
	
	//默认构造函数
	public Parser(){
		
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
	
	private void getFirst(){
		for(int i=0;i<unterminators.size();i++){
			List<Character> values=new ArrayList<Character>();
			first.put(unterminators.get(i),values);
		}
		boolean flag=false;
		while(!flag){
			flag=true;
			for(int i=0;i<unterminators.size();i++){
				//System.out.println(unterminators.get(i));
				if(!first(unterminators.get(i))){
					flag=false;
				}
			}
		}
		
		System.out.println("first:"+first);
	}
	
	private boolean first(char key){
		boolean flag=true;
		List<String> strings=expressionMap.get(key);
		List<Character> values=first.get(key);
		for(int i=0;i<strings.size();i++){
			char firstChar=strings.get(i).charAt(0);
			if(!unterminators.contains(firstChar)){
				if(!values.contains(firstChar)){
					values.add(firstChar);
					flag=false;
				}
			}else{
				List<Character> chars=first.get(firstChar);
				for(int j=0;j<chars.size();j++){
					if(chars.get(j)!='.'&&!values.contains(chars.get(j))){
						values.add(chars.get(j));
						flag=false;
					}
				}
			}
		}
		//System.out.println(first);
		
		return flag;
	}
	
	private boolean follow(char key){
		boolean flag=true;
		List<String> strings=expressionMap.get(key);
		for(int i=0;i<strings.size();i++){
			String str=strings.get(i);
			//System.out.println(str+" ");
			for(int j=0;j<str.length();j++){
				char dealChar=str.charAt(j);
				if(unterminators.contains(dealChar)){
					List<Character> dealValues=follow.get(dealChar);
					if(j==str.length()-1){
						List<Character> chars=follow.get(key);
						for(int m=0;m<chars.size();m++){
							if(!dealValues.contains(chars.get(m))){
								dealValues.add(chars.get(m));
								flag=false;
							}
						}
					}else{
						//后一位为非终止符
						if(unterminators.contains(str.charAt(j+1))){
							int index=j+1;
							boolean f=false;
							while(!f){
								f=true;
								if(unterminators.contains(str.charAt(index))){
									List<Character> chars=first.get(str.charAt(index));
									for(int m=0;m<chars.size();m++){
										
										if(chars.get(m)!='.'&&!dealValues.contains(chars.get(m))){
											dealValues.add(chars.get(m));
											flag=false;
										}
										if(chars.get(m)=='.'){
											index++;
											f=false;
										}
									}
								}else{
									if(!dealValues.contains(str.charAt(index))){
										dealValues.add(str.charAt(index));
										flag=false;
									}
								}
								if(index==str.length()){
									List<Character> chars=follow.get(key);
									for(int m=0;m<chars.size();m++){
										if(!dealValues.contains(chars.get(m))){
											dealValues.add(chars.get(m));
											flag=false;
										}
									}
									break;
								}
							}
							
						}else{
							if(!dealValues.contains(str.charAt(j+1))){
								dealValues.add(str.charAt(j+1));
								flag=false;
							}
						}
					}
				}
			}
		}
		//System.out.println(follow);
		return flag;
	}
	
	private void getFollow(){
		for(int i=0;i<unterminators.size();i++){
			List<Character> values=new ArrayList<Character>();
			if(unterminators.get(i)==startChar){
				values.add('#');
			}
			follow.put(unterminators.get(i),values);
		}
		boolean flag=false;
		while(!flag){
			flag=true;
			for(int i=0;i<unterminators.size();i++){
				//System.out.println(unterminators.get(i));
				if(!follow(unterminators.get(i))){
					flag=false;
				}
			}
		}
		
		System.out.println("follow:"+follow);
	}
	
	private void getSelect(){
		for(int i=0;i<unterminators.size();i++){
			List<String> strings=expressionMap.get(unterminators.get(i));
			for(int j=0;j<strings.size();j++){
				List<Character> values=new ArrayList<Character>();
				select.put(unterminators.get(i)+"-"+strings.get(j), values);
				select(strings.get(j),unterminators.get(i));
			}
		}
		System.out.println("select:"+select);
	}
	
	private void select(String express,char key){
		List<Character> values=select.get(key+"-"+express);
		char firstChar=express.charAt(0);
		if(firstChar=='.'){
			List<Character> chars=follow.get(key);
			for(int i=0;i<chars.size();i++){
				if(!values.contains(chars.get(i))){
					values.add(chars.get(i));
				}
			}
		}else if(unterminators.contains(firstChar)){
			int index=0;
			boolean flag=false;
			while(!flag){
				flag=true;
				if(unterminators.contains(firstChar)){
					List<Character> chars=first.get(firstChar);
					for(int i=0;i<chars.size();i++){
						if(!values.contains(chars.get(i))){
							if(chars.get(i)=='.'){
								flag=false;
							}else{
								values.add(chars.get(i));
							}
						}
					}
				}else{
					if(!values.contains(firstChar)){
						values.add(firstChar);
					}
				}
				index++;
				if(index>=express.length()){
					break;
				}else{
					firstChar=express.charAt(index);
				}
			}
		}else{
			if(!values.contains(firstChar)){
				values.add(firstChar);
			}
		}
	}
	
	private boolean isLL1(){
		
		boolean flag=true;
		for(int i=0;i<unterminators.size();i++){
			char key=unterminators.get(i);
			List<String> strings=expressionMap.get(key);
			Map<Character,Integer> map=new HashMap<Character,Integer>();
			for(int j=0;j<strings.size();j++){
				String value=strings.get(j);
				List<Character> chars=select.get(key+"-"+value);
				for(int m=0;m<chars.size();m++){
					if(map.containsKey(chars.get(m))){
						flag=false;
					}else{
						map.put(chars.get(m), 0);
					}
					if(flag==false){
						break;
					}
				}
				if(flag==false){
					break;
				}
			}
			if(flag==false){
				break;
			}
		}
		return flag;
	}
	
	private void getForm(){
		for(int i=0;i<unterminators.size();i++){
			char key=unterminators.get(i);
			List<String> strings=expressionMap.get(key);
			for(int j=0;j<strings.size();j++){
				String value=strings.get(j);
				List<Character> chars=select.get(key+"-"+value);
				for(int m=0;m<chars.size();m++){
					Map<Character,Character> map=new HashMap<Character,Character>();
					map.put(key, chars.get(m));
					form.put(map, value);
				}
			}	
		}
		System.out.println("form:"+form);
	}
	
	public boolean analysis(String express){
		boolean flag=true;
		Stack<Character> stack=new Stack<Character>();
		stack.push('#');
		stack.push(startChar);
		int index=0;
		int n=0;
		while(!stack.isEmpty()){
			char dealChar=express.charAt(index);
			char topChar=stack.peek();
			Map<Character,Character> map=new HashMap<Character,Character>();
			map.put(topChar,dealChar);
			if(dealChar==topChar){
				stack.pop();
				index++;
			}else if(form.containsKey(map)){
				String value=form.get(map);
				stack.pop();
				for(int i=value.length()-1;i>-1;i--){
					if(value.charAt(i)!='.'){
						//System.out.println(value.charAt(i));
						stack.push(value.charAt(i));
					}
				}
			}else{
				flag=false;
				break;
			}
			n++;
		}
		if(!stack.isEmpty()||index!=express.length()){
			flag=false;
		}
		return flag;
	}
}

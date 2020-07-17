package trans;

import java.time.Clock;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.security.auth.x500.X500Principal;

public class lambda {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
//		Converter<String, Integer> converter = (from) -> Integer.valueOf(from); //lambda表达式，实现借口的方式，不再是用implement
//		Converter<String, Integer> converter = Integer::valueOf; //方法和构造器的引用，用::符号传递
//		System.out.println(converter.convert("123"));
//		List<Integer> a = Arrays.asList(100,200,300,400);
//        System.out.println(a.stream().reduce(0, Integer::sum));
//		a.stream().map((cost)-> cost + .12*cost).forEach((n)-> System.out.println(n));//使用lambda实现map，reduce() 是将集合中所有值结合进一个，Reduce类似SQL语句中的sum(), avg() 或count(),
//		List<Integer> temp = a.stream().filter(k -> k > 200).collect(Collectors.toList()); //应用过滤器，只有满足要求的才会被包含，注意原集合不会变
//		System.out.printf("temp: %s",temp);
//		List<String> b = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
//		b.stream().map(x -> x.toUpperCase()).collect(Collectors.toList()).forEach(System.out::println);//对集合中每个元素应用函数
//		System.out.printf("list: %s",temp);//格式化输出列表
//		test();
//		supplier();
//		test2();
		Map<String,Long> m = new HashMap<>();
		m.put("熊节庆",1L);
		m.put("熊",2L);
		m.put("节庆",3L);
        System.out.println(m);
        m.keySet().stream().filter(e -> e.equals("庆")).findFirst().ifPresent(System.out::print);
//		System.out.printf("list: %s", findFamidsByname("熊",m));
//		remove();
	}
	interface Converter<F,T>{
		T convert(F f);
	}
	public static void test(){
		Map<Integer,Integer> m = new HashMap<>();
		m.put(1,1);
		m.put(2,2);
		m.put(3,3);
		for(Map.Entry<Integer, Integer> entry : m.entrySet()){
			entry.setValue(entry.getValue() * 2);
		}
		System.out.printf("%s",m.values());
	}
	public static void test2(){
		List<Integer> list = Arrays.asList(30,45,60,75,90);
		Set<Integer> set = new HashSet<>();
		set.addAll(list);
		java.util.List<Integer> levels = set.stream().filter(p -> p <35).collect(Collectors.toList());
		System.out.println(Collections.max(levels));
	}
	public static void supplier(){
		Stream<Long> natural = Stream.generate(new NaturalSupplier());
		natural.map((x)->{return x*x;}).limit(10).forEach(System.out::println);
	}

	public static List<Long> findFamidsByname(String name, Map<String, Long> toFind){
		List<Long> result = toFind.entrySet().stream().filter(e-> e.getKey().contains(name)).map(e -> e.getValue()).collect(Collectors.toList());
		return result;
	}

	public static void remove(){
		Set<Integer> s1 = new HashSet<>();
		s1.add(1);
		s1.add(2);
		s1.add(3);
		Set<Integer> s2 = new HashSet<>();
		s2.add(3);
		s2.add(4);
		System.out.println(s2.stream().filter(i-> !s1.contains(i)).collect(Collectors.toList()));
	}
}
class NaturalSupplier implements Supplier<Long>{ //Supplier是java函数式编程提供的接口,function包提供的
	long value = 0;
	@Override
	public Long get() {
		this.value = this.value + 1;
		return this.value;
	}


}


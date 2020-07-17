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
//		Converter<String, Integer> converter = (from) -> Integer.valueOf(from); //lambda���ʽ��ʵ�ֽ�ڵķ�ʽ����������implement
//		Converter<String, Integer> converter = Integer::valueOf; //�����͹����������ã���::���Ŵ���
//		System.out.println(converter.convert("123"));
//		List<Integer> a = Arrays.asList(100,200,300,400);
//        System.out.println(a.stream().reduce(0, Integer::sum));
//		a.stream().map((cost)-> cost + .12*cost).forEach((n)-> System.out.println(n));//ʹ��lambdaʵ��map��reduce() �ǽ�����������ֵ��Ͻ�һ����Reduce����SQL����е�sum(), avg() ��count(),
//		List<Integer> temp = a.stream().filter(k -> k > 200).collect(Collectors.toList()); //Ӧ�ù�������ֻ������Ҫ��ĲŻᱻ������ע��ԭ���ϲ����
//		System.out.printf("temp: %s",temp);
//		List<String> b = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
//		b.stream().map(x -> x.toUpperCase()).collect(Collectors.toList()).forEach(System.out::println);//�Լ�����ÿ��Ԫ��Ӧ�ú���
//		System.out.printf("list: %s",temp);//��ʽ������б�
//		test();
//		supplier();
//		test2();
		Map<String,Long> m = new HashMap<>();
		m.put("�ܽ���",1L);
		m.put("��",2L);
		m.put("����",3L);
        System.out.println(m);
        m.keySet().stream().filter(e -> e.equals("��")).findFirst().ifPresent(System.out::print);
//		System.out.printf("list: %s", findFamidsByname("��",m));
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
class NaturalSupplier implements Supplier<Long>{ //Supplier��java����ʽ����ṩ�Ľӿ�,function���ṩ��
	long value = 0;
	@Override
	public Long get() {
		this.value = this.value + 1;
		return this.value;
	}


}


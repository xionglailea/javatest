package trans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arctest on 2015/12/25.
 */
public class EffectJava {
    public static void main(String[] args){
        Favorites fav = new Favorites();
        fav.putFavorite(String.class, "xiong");

    }
    public static void swap(List<?> list,int i, int j){
        swapHelper(list,i,j);
    }
    private static <E> void swapHelper(List<E> list, int i, int j){ //��ʹ�÷��͵�ʱ��ע��Ҫ���������ͱ���
        list.set(i,list.set(j,list.get(i)));
    }
    //�칹������������ͬ���͵�
    public static class Favorites{
        private Map<Class<?>, Object> favorites = new HashMap<>();
        public <T> void putFavorite(Class<T> type, T instance){
            if(type == null){
                throw new NullPointerException("Type is null");
            }
            favorites.put(type,type.cast(instance)); //��̬ת��
        }
        public <T> T getFavorite(Class<T> type){
            return type.cast(favorites.get(type));
        }

    }

}

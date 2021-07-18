package asmtest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Test {

    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ExtraFeature.feature();
        BeCheckedClass beCheckedClasss = Generator.generate();
        System.out.println(beCheckedClasss.getClass().getName());
        beCheckedClasss.operation();
        beCheckedClasss.print();
    }
}

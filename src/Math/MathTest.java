package Math;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jieqinxiong on 2017/6/6.
 */
public class MathTest {
    public static void main(String[] args) {
//        Vector3 v1 = new Vector3(220.841522,27.299557,103.286766);
//        Vector3 v2 = new Vector3(215.702983,27.299557,97.155256);
//        System.out.println(v1.getSubXZMagnitude(v2));
//        float a = 5000;
//        long b = 200;
//        double c = b/a;
//        System.out.println(c );
        Vector3 v = new Vector3(0,0,1);
        for(int i = 0; i < 10; i++){
            float b = 45;
            float a = randomRange(-b, b);
            System.out.println(a);
            System.out.println(v.rotateXZAngle(a));
        }

    }

    public static void testInRect(){
        Vector3 v1 = new Vector3(1,0,0);
        System.out.println(v1.rotateXZ(new Vector3(-1,0,1)));

        Rect rect = new Rect();
        rect.min = new Vector3(-1,0,0);
        rect.max = new Vector3(1,2,8);
        Vector3 basePosition = new Vector3(205.43, 23.92, 303.88);
        Vector3 direction = new Vector3(-0.64,0,0.76);
        Vector3 target = new Vector3(202.78,24,307.23);
        System.out.println(inRectangle(rect, 1, basePosition, direction, target));
    }

    public static boolean inRectangle(Rect hitzone, double modelScale, Vector3 basePosition, Vector3 direction, Vector3 target) {
        Vector3 c2t = target.sub(basePosition);
        final double bodyRadius = 0;
        if(c2t.getXZMagnitude() <= bodyRadius){
            return true;
        }
        Vector3 rotatec2t = c2t.rotateXZ(direction);
        System.out.println(rotatec2t);
        return (rotatec2t.x >= hitzone.min.x && rotatec2t.x <= hitzone.max.x) && (rotatec2t.y >= hitzone.min.y && rotatec2t.y <= hitzone.max.y) &&
                (rotatec2t.z >= hitzone.min.z && rotatec2t.z <= hitzone.max.z);
    }

    static class Rect{
        public Vector3 min;
        public Vector3 max;
    }

    public static float randomRange(float min, float max){
        return min + (max - min) * random().nextFloat();
    }
    public static Random random() {
        return ThreadLocalRandom.current();
    }



}

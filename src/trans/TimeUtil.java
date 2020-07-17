package trans;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by arctest on 2015/11/24.
 */
public class TimeUtil {
    public final static long DAY_MILLISECONDS = 86400000;
    public final static long HOUR_MILLISECONDS = 3600000;
    public final static long MINUTE_MILLISECONDS= 60000;

    public static void main(String[] args) {
//ʹ��Ĭ��ʱ�������Ի������һ������
//        int i = 0;
//        for(; i < 3 ; i++){
//            break;
//        }
//        float j = 15f;
//        float f1 = 16777215f;
//        for (int i = 0; i < 10; i++) {
//            System.out.println(f1);
//            f1++;
//        }
//        System.out.println(i + j);
//        System.out.println(1*100000/100);
//        System.out.println("i is " + i);
        Calendar cale = Calendar.getInstance();
//        cale.set(Calendar.DAY_OF_MONTH, 8);
//        cale.set(Calendar.HOUR_OF_DAY, 12);
//        cale.clear(Calendar.MINUTE);
//        cale.clear(Calendar.SECOND);
//        cale.clear(Calendar.MILLISECOND);
//        long time = cale.getTimeInMillis();
//        System.out.println(time);
//        long c = cale.getTimeZone().getRawOffset();
//        long a = cale.getTimeInMillis();
//        long d = 1499846803456L;
//        long e = 1503989006396L; //now
//        cale.setTimeInMillis(e);
////��Calendar����ת����Date����
//        Date tasktime=cale.getTime();
////������������ĸ�ʽ
//        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////��ʽ�����
//        System.out.println(df.format(tasktime));

//        cale.add(cale.HOUR_OF_DAY,12);
//        long b = cale.getTimeInMillis();
//        System.out.println(df.format(cale.getTime()));
//        System.out.println(isSameDay(a+c,b+c));
//        SimpleDateFormat b=new SimpleDateFormat("MM-dd");
//        System.out.println(b.format(tasktime));
//        System.out.println(cale.get(cale.DAY_OF_MONTH));
//        System.out.println(getCurrentMonthDay());
//        System.out.println(getDaysByYearMonth(2015, 2));
//        System.currentTimeMillis();
//        Date d= new Date(System.currentTimeMillis());
//        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(df.format(d));
//        Map<Integer, Integer> m = new HashMap<>();
//        System.out.println(m.size());
//        int[] pos = new int[m.size()];
//        for (int i : pos) {
//            System.out.println(i);
//        }
//        refreshLimitExpireInfo();
//        nextHourMinuteOfDay(0,0);
//        TimeUtil eff = new TimeUtil();
//        cla test = eff.new clb();
//        clb testaa = (clb) test; //ǿ��ת���ɶ�Ӧ������;
//        System.out.println(testaa.c);
//        System.out.println(isRecentAddSign(Arrays.asList(1,2,3,5,6,7),8,8));
//        System.out.println(isRecentAddSign(Arrays.asList(1,2,3,5,6,7),4,8));
//        System.out.println(isRecentAddSign(Arrays.asList(1,2,3,5,6,7),1,8));
//        System.out.println(isRecentAddSign(Arrays.asList(1,2,3,4,5,6,7),1,8));
//        System.out.println(random01());
//        long now = System.currentTimeMillis();
//        todayZeroTime(getStart());
//        System.out.println(getDayOfWeek(getStart()));
//        getStart();
//        printtime(getNextMondayBeginTime(now - DAY_MILLISECONDS * 5));
//        printtime(getMondayOfThisWeek());
//        System.out.println(calDayInWeek(now + 10 * HOUR_MILLISECONDS));
//        System.out.println(getTodayHour(System.currentTimeMillis()));
//        System.out.println(getTodayMinute(System.currentTimeMillis()));
//        printtime(1525937829547L);
//      printtime(1574864220739L);
//        System.out.println(getTime(2017, 2, 2, 0,0));
//        testFixIntervalCalculator(System.currentTimeMillis() + DAY_MILLISECONDS + HOUR_MILLISECONDS * 3);
      System.out.println(System.currentTimeMillis());
    }

    public static boolean isSameDay(long time1, long time2) {//�ж��Ƿ���ͬһ����Ҫ����ʱ����ƫ��
        return (time1 ) / DAY_MILLISECONDS == (time2) / DAY_MILLISECONDS;
    }
    //�õ������0�㣬���ܵ�0�㣬���µ�0��
    public static void refreshLimitExpireInfo() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        final long dayZeroTime = cal.getTimeInMillis();
        //cal.get
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        final long weekZeroTime = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        final long monthZeroTime = cal.getTimeInMillis();


        printtime(dayZeroTime);
        printtime(weekZeroTime);
        printtime(monthZeroTime);

    }

    public static void printtime(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(sdf.format(cal.getTime()));
    }
    public static Calendar nextHourMinuteOfDay(int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (cal.before(Calendar.getInstance())) // 如果第一次的时间比当前时间早，推到明天��
            cal.add(Calendar.DAY_OF_MONTH, 1); // tomorrow!
        printtime(cal.getTimeInMillis());
        return cal;
    }

    class cla{

    }
    class clb extends cla{
        public int c = 1;
    }


    static Random random = new Random();

    public static Random random() {
        return random;
    }

    public static double random01() {
        return random.nextDouble();
    }

    /**
     * ��ȡ��������
     * @return
     */
    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        Date tasktime=a.getTime();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(tasktime));
        a.roll(Calendar.DATE,false);
        tasktime=a.getTime();
        System.out.println(df.format(tasktime));
        a.roll(Calendar.DATE, -1);  //����rollֻ���ƶ��ķ�Χ�����仯����ÿ�µľ������������ظı��·ݣ�
        // ,�������Ѿ�Ϊ1�ˣ������������һ�죬�൱���ֻص��˱��µ����һ�졣
        tasktime=a.getTime();
        System.out.println(df.format(tasktime));
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * ������ �� ��ȡ��Ӧ���·� ����
     * */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);//DAY_OF_MONTH Ҳ����
        return maxDate;
    }

    public static void getDayInWeek(){
        Calendar a = Calendar.getInstance();
        int dayinweek = a.get(Calendar.DAY_OF_WEEK);
        a.set(Calendar.DAY_OF_MONTH,21);
        int dayinweek1 = a.get(Calendar.DAY_OF_WEEK_IN_MONTH);//�ӱ��µ�һ�쿪ʼ���ܣ�ÿ7��Ϊһ��
        int dayinweek2 = a.get(Calendar.WEEK_OF_MONTH);//���������������������ڵڼ���
        System.out.println(dayinweek);
        System.out.println(dayinweek1);
        System.out.println(dayinweek2);
    }
    //ע�������������˳�� Ϊ���ա���һ���ܶ����������ֱ��Ӧ�ķ�������Ϊ1��2��3��4��5��6��7

    public final static long TIMEZONE_OFFSET = Calendar.getInstance().getTimeZone().getRawOffset();
    public static long todayZeroTime(long time) {
        long zero =  (time + TIMEZONE_OFFSET) / DAY_MILLISECONDS * DAY_MILLISECONDS - TIMEZONE_OFFSET;
        return zero;
    }
    public static int getDayOfWeek(long time){
        int left = (int)(todayZeroTime(time) / DAY_MILLISECONDS % 7 + 5);
        return left > 7 ? left - 7 : left;
    }

    public static long getStart(){
        Calendar a = Calendar.getInstance();
        a.set(1970,Calendar.JANUARY,1);
        a.set(Calendar.HOUR_OF_DAY,0);
        a.set(Calendar.MINUTE,0);
        a.set(Calendar.SECOND,0);
        a.set(Calendar.MILLISECOND,0);
        System.out.println(a.get(Calendar.DAY_OF_WEEK));
        System.out.println(a.getTimeInMillis());
        printtime(a.getTimeInMillis());
        return a.getTimeInMillis();
    }

    public static long getNextMondayBeginTime(long time) {
        int curDayInWeek = getDayOfWeek(time);
        System.out.println(curDayInWeek);
        return todayZeroTime(time) + DAY_MILLISECONDS * (8 - curDayInWeek);
    }

    /**
     * �õ�������һ
     *
     * @return yyyy-MM-dd
     */
    public static long getMondayOfThisWeek() {
        return getNextMondayBeginTime(System.currentTimeMillis()) - 7 * DAY_MILLISECONDS;
    }

    /**
     * cale.getTimeInMillis(); ����ֱ��get�����Ǹ�������ʱ�䣬��һ������ʱ�䣬Ҫ�õ�����ʱ����ʱ�䣬��Ҫ����һ��ʱ��
     * ����ʱ����Ǹ�������ʱ���Ӧ���������ڵ�ʱ��
     * @param time
     * @return
     */
    public static int calDayInWeek(long time){
        int left =(int)( (time + TIMEZONE_OFFSET) / DAY_MILLISECONDS % 7) + 4;
        return left > 7 ? left - 7 : left ;
    }

    public static int getTodayHour(long time){
        long interval = time - todayZeroTime(time);
        return (int)(interval / HOUR_MILLISECONDS);
    }

    /**
     *��ȡ��ǰʱ��ķ���
     * @return
     */
    public static int getTodayMinute(long time){
        long interval = time - todayZeroTime(time);
        long left = interval % HOUR_MILLISECONDS;
        return (int)(left / MINUTE_MILLISECONDS);
    }

    public static long getTime(int year, int month, int day, int hour, int minute) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DAY_OF_MONTH, day);
        a.set(Calendar.HOUR_OF_DAY, hour);
        a.set(Calendar.MINUTE, minute);
        a.clear(Calendar.SECOND);
        a.clear(Calendar.MILLISECOND);
        return a.getTimeInMillis();
    }

    public static void test(){
        Calendar a = Calendar.getInstance();
//        a.setTimeInMillis(System.currentTimeMillis());
        System.out.println(a.get(Calendar.HOUR_OF_DAY));
    }

    public static long startTime = 1516006800000L;
    public static long endTime = startTime + HOUR_MILLISECONDS;
    public static long sustainedTime = HOUR_MILLISECONDS; //ÿ�ο����ĳ���ʱ�� endtime - starttime
    public static long intervalTime = HOUR_MILLISECONDS; //���ʱ��

    public static void testFixIntervalCalculator(long time){
        if(time < startTime) {
            System.out.println("start time is ");
            printtime(startTime);
            System.out.println("end time is ");
            printtime(endTime);
        }else {
            long gap = time - startTime;
            long times = gap / (sustainedTime + intervalTime);
            long remainder = gap - times * (sustainedTime + intervalTime);
            long resultStart;
            if(remainder < sustainedTime) {
                resultStart = startTime + (sustainedTime + intervalTime) * times;
            }else {
                resultStart = startTime + (sustainedTime + intervalTime) * (times + 1);
            }
            System.out.println("start time is ");
            printtime(resultStart);
            System.out.println("end time is ");
            printtime(resultStart + sustainedTime);
        }
    }


}

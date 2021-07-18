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
//使用默认时区和语言环境获得一个日历
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
////将Calendar类型转换成Date类型
//        Date tasktime=cale.getTime();
////设置日期输出的格式
//        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////格式化输出
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
//        clb testaa = (clb) test; //强制转换成对应的子类;
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

    public static boolean isSameDay(long time1, long time2) {//判断是否在同一天需要加上时区的偏差
        return (time1 ) / DAY_MILLISECONDS == (time2) / DAY_MILLISECONDS;
    }
    //得到当天的0点，本周的0点，本月的0点
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
        if (cal.before(Calendar.getInstance())) // 濡傛灉绗竴娆＄殑鏃堕棿姣斿綋鍓嶆椂闂存棭锛屾帹鍒版槑澶┿€
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
     * 获取当月天数
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
        a.roll(Calendar.DATE, -1);  //这里roll只在制定的范围内做变化，如每月的具体天数（不回改变月份）
        // ,当日期已经为1了，如果再往后退一天，相当于又回到了本月的最后一天。
        tasktime=a.getTime();
        System.out.println(df.format(tasktime));
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     * */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);//DAY_OF_MONTH 也可以
        return maxDate;
    }

    public static void getDayInWeek(){
        Calendar a = Calendar.getInstance();
        int dayinweek = a.get(Calendar.DAY_OF_WEEK);
        a.set(Calendar.DAY_OF_MONTH,21);
        int dayinweek1 = a.get(Calendar.DAY_OF_WEEK_IN_MONTH);//从本月第一天开始记周，每7天为一周
        int dayinweek2 = a.get(Calendar.WEEK_OF_MONTH);//按日历从上往下数，落在第几行
        System.out.println(dayinweek);
        System.out.println(dayinweek1);
        System.out.println(dayinweek2);
    }
    //注意在西方世界的顺序 为周日、周一、周二。。。。分别对应的返回数字为1，2，3，4，5，6，7

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
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static long getMondayOfThisWeek() {
        return getNextMondayBeginTime(System.currentTimeMillis()) - 7 * DAY_MILLISECONDS;
    }

    /**
     * cale.getTimeInMillis(); 这里直接get到的是格林威治时间，是一个绝对时间，要得到我们时区的时间，还要加上一个时差
     * 加上时差，才是格林威治时间对应到我们现在的时间
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
     *获取当前时间的分钟
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
    public static long sustainedTime = HOUR_MILLISECONDS; //每次开启的持续时间 endtime - starttime
    public static long intervalTime = HOUR_MILLISECONDS; //间隔时间

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

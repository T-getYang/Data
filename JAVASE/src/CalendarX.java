import java.util.Scanner;
import java.util.Calendar;
public class CalendarX {
    public static void main(String[] args) {
        // 控制台输入年份和月份
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入年份：");
        int year = sc.nextInt();

        System.out.println("请输入月份：");
        int month = sc.nextInt();

        System.out.println("请输入日份：");
        int day = sc.nextInt();

        System.out.println("请输选择1.周一开始2.周日开始");
        int ele = sc.nextInt();
        System.err.printf("%32s\n","当前日历:"+ year + "-" + month + "-" + day +" "+getWeek(year,month,day) + " 返回今天:"+getNow());
        String[] weeks ;
        int week;
        if(ele == 1){
            week = getMonday(year, month);
            weeks = new String[]{"星期一", "星期二", "星期三", "星期四",
                    "星期五", "星期六", "星期日"};
        }else{
            week = getSunDay(year, month);
            weeks = new String[]{ "星期日", "星期一", "星期二", "星期三",
                    "星期四", "星期五", "星期六" };
        }
        //打印头
        for (int index = 0; index < weeks.length; ++index) {
            System.out.printf("%-5s", weeks[index]);
        }
        System.out.println();
        //打印内容
        for (int index = 0; index < week; ++index) {
            System.out.printf("%-8s",  " ");
        }
        int days = getDays(year, month);
        for (int index = week+1,newDay = 1;index < days + week+1; ++index,newDay++) {
            if (index % 7 == 0) {
                System.out.printf("%-8d\n",newDay); //换行
            } else {
                if(newDay == day){
                    System.out.printf("%-8s",newDay+"*");
                }else{
                    System.out.printf("%-8d",newDay);
                }
            }
        }
    }

    //计算是否闰年
    public static boolean isLeap(int year) {
        if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))){
            return true;
        }
        return false;
    }
    //　计算本月天数
    public static int getDays(int year,int month) {
        int days = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 ||
                month == 8 || month == 10 || month == 12) {
            days = 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            days = 30;
        } else {
            if (isLeap(year)) { //判断是否闰年
                days = 28;
            } else {
                days = 29;
            }
        }
        return days;
    }


    public static int getSunDay(int year,int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1,1);
        int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        System.out.println("week"+week);
        return week;
    }

    public static int getMonday(int year,int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1,0);
        int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        System.out.println("week"+week);
        return week;
    }


    public static String getWeek(int year,int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1,day);
        int week = (calendar.get(Calendar.DAY_OF_WEEK)-1);
        return week == 1 ? "星期一" :
                week == 2 ? "星期二" : week == 3 ? "星期三":
                        week == 4 ? "星期四" : week == 5 ? "星期五" : week == 6 ? "星期六" :"星期日" ;
    }

    public static String getNow(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month =calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = (calendar.get(Calendar.DAY_OF_WEEK)-1);
        String nowWeek = nowWeek  = week == 1 ? "星期一" :
                week == 2 ? "星期二" : week == 3 ? "星期三":
                        week == 4 ? "星期四" : week == 5 ? "星期五" : week == 6 ? "星期六" :"星期日" ;
        return year + "-" + month + "-" + day +" "+nowWeek;
    }


}

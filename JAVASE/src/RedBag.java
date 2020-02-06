import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.Scanner;

public class RedBag {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入红包金额");
        double money = sc.nextDouble();
        System.out.println("请输入多少包");
        int num = sc.nextInt();
        if(num==1){
            System.out.println("红包数量为1");
            return;
        }
        if(isOK(money,num)){
            getRedBag(money,num);
        }else{
            System.out.println("红包分配不均匀或超过红包最大额度");
        }
    }
    public static double getRandomMoney(int count,double remainMoney) {
        Random random = new Random();
        //最低分配余额
        double min = 0.01;
        //最高分配余额（概率剩余平均值*2）
        double max = remainMoney / count * 2;
        double money = random.nextDouble() * max;
        money = money <= min ? 0.01: money;
        money = Math.floor(money * 100) / 100;
        return money;
    }

    private static void getRedBag(double redMoney,int num) {
        NumberFormat formatter = new DecimalFormat("#.##");
        for (int i = num,x=1; i > 1; i--,x++) {
            double money = getRandomMoney(i, redMoney);
            //数值转换
            redMoney = Double.valueOf(formatter.format(redMoney -money));

            System.out.println("第" + x + "个红包：" + money + "元 ,余额：" + redMoney);
        }
        System.out.println("最后个红包：" + redMoney + "元 ,余额：" + (redMoney - redMoney));
    }

    private static boolean isOK(double money, int count) {
        final double MINMONEY = 0.01;
        //最大红包额度
        final int MAXMONEY = 200 ;
        double avg = money / count;
        if (avg < MINMONEY) {
            return false;
        }
        if (avg > MAXMONEY) {
            return false;
        }
        return true;
    }

}

import java.util.Scanner;

/**
 * 迷宫
 * @ClassName MiGong
 * @author :mykang
 * @since :2020年3月14日 晚上19：45
 * **/
public class MiGong {
    static int n;
    static int[][] a;
    static int min;
    static int index=2;

    public static void main(String[] args) {
        create();
        /*坐标原点*/
        int x=1;
        int y=1;
        /*取一个最小值*/
        min=n*n;

        findpath(x,y,0,n);
        /*判断是否有最小路径*/
        if(min!=n*n){
            System.out.println(min);
        }else {
            System.out.println("No solution");
        }
    }
    /**
    * 创建迷宫
    */
    public static void create(){
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        a = new int[n][n];

        /*输入迷宫数组*/
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = scanner.nextInt();
            }
        }

    }
    /**
     * 向右开始顺时针走向,寻找路径。
     * */
    public static void findpath(int x,int y,int count,int n){

        if (x==n-index && y==n-index){
            /*判断最小值并赋值*/
            min = Math.min(count,min);
        }else {
            /*将走过的位置设置为1，防止重复走*/
            a[x][y]=1;
            /*向右*/
            if(y<n-1 && a[x][y+1]==0){
                findpath(x,y+1,count+1,n);
            }
            /*向下*/
            if (x<n-1 && a[x+1][y]==0) {
                findpath(x+1, y, count+1,n);
            }
            /*向左*/
            if (y>1 && a[x][y-1]==0) {
                findpath(x, y-1, count+1,n);
            }
            /*向上*/
            if (x>1 && a[x-1][y]==0){
                findpath(x-1, y,count+1,n);
            }
            /*回溯*/
            a[x][y]=0;
        }
    }
}

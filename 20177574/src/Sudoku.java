import java.io.*;
import java.util.ArrayList;


/**
 * 注: Sudoku.java使用了未经检查或不安全的操作。
 * 注: 有关详细信息, 请使用 -Xlint:unchecked 重新编译。
 * 解决方法：
 * 在类前加上@SuppressWarnings("unchecked")
 **/

@SuppressWarnings("unchecked")//告诉编译器忽略 unchecked 警告信息
public class Sudoku {
    public static int m;
    public static int n;
    public static String i_name;
    public static String o_name;
    public static String path = System.getProperty("user.dir");


    public static void main(String[] args) {

        //java Sudoku -m 3 -n 5 -i input.txt -o output.txt
        m = Integer.parseInt(args[1]);
        n = Integer.parseInt(args[3]);
        i_name = args[5];
        o_name = args[7];
        int a[][] = new int[m][m];

        if (!args[0].equals("-m") || !args[2].equals("-n") || !args[4].equals("-i") || !args[6].equals("-o")) {
            System.out.println("参数输入错误！！");
        } else {
            if (m == 3) {
                if (n > 0) {
                    input(a, m);
                } else {
                    System.out.println("输入的参数n有误！");
                    System.exit(0);
                }
            } else {
                System.out.println("输入的参数m有误！当前版本仅支持3宫格数独");
                System.exit(0);
            }
        }
    }

    public static void input(int a[][], int m) {
        boolean flag = false;
        int num = 0;
        ArrayList<String> list = new ArrayList();
        File file = new File(path + "\\" + i_name);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                if (!"".equals(str)) {
                    list.add(str);
                }
            }
            String str2 = null;
            int k = 0;
            for (int i = 0; i < list.size(); i++) {
                if (i > 0 && i % m == 0) {
                    k = 0;
                    a = save(a, m);
                    flag = check(a, m);
                    if (flag) {
                        output(a, m);
                    }
                    System.out.println();
                }
                str2 = list.get(i);
                int l = 0;
                for (int j = 0; j < str2.length(); j++) {
                    if (str2.charAt(j) != ' ') {
                        num = Integer.parseInt(String.valueOf(str2.charAt(j)));
                        if (num >= 0 && num <= m) {  //添加一个判断读取出的数独盘的数是否符合规则
                            a[k][l] = num;
                            l++;
                        } else {
                            System.out.println("数独盘内容错误！题目错误！");
                            System.exit(0);
                        }
                    }
                }
                k++;
            }
            a = save(a, m);
            flag = check(a, m);
            if (flag) {
                output(a, m);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件目录输入错误！未找到文件！");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("输入异常！");
            System.exit(0);
        }
    }

    public static int[][] save(int a[][], int m) {
        ArrayList<int[]> list = new ArrayList();
        int[] b = new int[3];
        int p = 1;
		int g=1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i][j] == 0) {
                    a[i][j] = number(i, j, a, p, m);

                    if (a[i][j] == 0 && g!=1) {   //用于判断这一步是否没有数可填，如果为0，就放回到上一步判断的地方，再次进行判断
                        b = list.get(list.size() - 1);
                        i = b[0];
                        j = b[1];
                        if (b[2] + 1 > 3) {
                            System.out.println("该数据题目错误！");
                            System.exit(0);
                        } else {
                            a[i][j] = number(i, j, a, b[2] + 1, m);
                            continue;
                        }
                    }
					else if(g == 1 && a[i][j] == 0){
						System.out.println("该数据题目错误！");
						System.exit(0);
					}
					else {
                        b[0] = i;
                        b[1] = j;
                        b[2] = a[i][j];
                        list.add(b);
                    }
                }
				g++;
            }
        }

        return a;
    }

    public static int number(int i, int j, int a[][], int p, int m) { //判断该填的数字 p为上次填的数，如果是第一次p还是从1开始
        int num = 0;
        for (int k = p; k <= m; k++) {
            if (a[i][0] != k && a[i][1] != k && a[i][2] != k) {
                if (a[0][j] != k && a[1][j] != k && a[2][j] != k) {
                    num = k;
                    break;
                }
            }
        }
        return num;
    }

    public static void output(int a[][], int m) { //将结果输出到指定的文件内
        File file = new File(path + "\\" + o_name);
        try {
            FileWriter fw = new FileWriter(file, true);
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    if (j == m - 1) {
                        fw.write(a[i][j] + "");

                    } else {
                        fw.write(a[i][j] + " ");

                    }
                    System.out.print(a[i][j] + " ");
                }
                fw.write("\r\n");
                System.out.println();
            }
            fw.write("\r\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("文件目录错误！");
            System.exit(0);
        }
    }

    public static boolean check(int a[][], int m) {//检查最后输出的结果是否正确
        boolean flag = false;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i][j] == '0') {
                    System.out.println("输入内容有误，无答案!");
                    flag = false;
                    System.exit(0);
                } else {
                    flag = true;
                }
            }
        }
        return flag;
    }
}

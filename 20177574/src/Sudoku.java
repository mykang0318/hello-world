import java.io.*;
import java.util.ArrayList;


/**
 * 注: Sudoku.java使用了未经检查或不安全的操作。
 * 注: 有关详细信息, 请使用 -Xlint:unchecked 重新编译。
 * 解决方法：
 * 在类前加上@SuppressWarnings("unchecked")
 **/

@SuppressWarnings("unchecked")
public class Sudoku {
    public static int m;
    public static int n;
    public static String i_name;
    public static String o_name;
    public static String path = System.getProperty("user.dir");


    public static void main(String[] args) {

        // java Sudoku -m 3 -n 5 -i input.txt -o output.txt
        m = Integer.parseInt(args[1]);
        n = Integer.parseInt(args[3]);
        i_name = args[5];
        o_name = args[7];
        int a[][] = new int[m][m];
        if (m == 3) {
            if (n > 0) {
                intput(a);
            } else {
                System.out.println("输入的参数n有误！");
                System.exit(0);
            }
        } else {
            System.out.println("输入的参数m有误！当前版本仅支持3宫格数独");
            System.exit(0);
        }

    }

    public static void intput(int a[][]) {
        boolean flag = false;
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
            ArrayList<String[]> list2 = new ArrayList<>();
            int k = 0;
            for (int i = 0; i < list.size(); i++) {
                if (i > 0 && i % m == 0) {
                    k = 0;
                    a = save(a);
                    flag = check(a);
                    if (flag) {
                        output(a);
                    }
                    System.out.println();
                }
                str2 = list.get(i);
                int l = 0;
                for (int j = 0; j < str2.length(); j++) {
                    if (str2.charAt(j) != ' ') {
                        a[k][l] = Integer.parseInt(String.valueOf(str2.charAt(j)));
                        l++;
                    }
                }
                k++;
            }
            a = save(a);
            flag = check(a);
            if (flag) {
                output(a);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] save(int a[][]) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i][j] == 0) {
                    a[i][j] = number(i, j, a);
                }
            }
        }
        return a;
    }

    public static int number(int i, int j, int a[][]) {
        int num = 0;
        for (int k = 1; k <= m; k++) {
            if (a[i][0] != k && a[i][1] != k && a[i][2] != k) {
                if (a[0][j] != k && a[1][j] != k && a[2][j] != k) {
                    num = k;
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return num;
    }

    public static void output(int a[][]) {
        File file = new File(path + "\\" + o_name);
        try {
            FileWriter fw = new FileWriter(file, true);
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a.length; j++) {
                    if (j == a.length - 1) {
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
            e.printStackTrace();
        }
    }

    public static boolean check(int a[][]) {
        boolean flag = false;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
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

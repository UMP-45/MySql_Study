// 注册登录的入口,提供main方法
package hrms;
import java.util.Scanner;

public class MainLogin {
  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    while(true) {
      System.out.println("*****欢迎登录人力资源管理系统*****");
      System.out.println("*1.注册|2.登录|3.修改密码|4.退出*");
      System.out.print("请选择: ");
      int num = sc.nextInt();
      if(num == 4) {
        System.out.println("\n 谢谢使用!");
        break;
      }else {
        switch(num) {
          case 1 -> Login.register();
          case 2 -> Login.login();
          case 3 -> Login.updatePassword();
          default -> System.out.println("请重新输入!");
        }
      }
    }
    sc.close();
  }
}

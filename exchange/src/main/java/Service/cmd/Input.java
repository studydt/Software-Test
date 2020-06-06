package Service.cmd;

import Dao.FilesDAO;
import Dao.ParameterDAO;
import Service.find.FindCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
    static ParameterDAO parameterDAO;
    static FilesDAO filesDAO;

    public static void main(String[] args) throws IOException {
        System.out.println("=========================Hello=============================");
        System.out.println("| 欢迎使用变异测试工具, 功能如下:                              |");
        System.out.println("| 11: 将条件指令的布尔表达式替换为“true”                       |");
        System.out.println("| 12: 将条件指令的布尔表达式替换为“false”                      |");
        System.out.println("| 2:  删除void方法体中所有指令                                |");
        System.out.println("| 31: 由一个“返回true”指令替换一个布尔方法的方法体               |");
        System.out.println("| 32: 由一个“返回false”指令替换一个布尔方法的方法体              |");
        System.out.println("===========================================================");

        filesDAO = new FilesDAO();
        CmdInput();
        FindCode findCode = new FindCode(parameterDAO,filesDAO);


    }

    public static void CmdInput() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        String str = bufferedReader.readLine();
        System.out.println("选择功能序号(多选以逗号相隔): ");
        String id = bufferedReader.readLine();
//        String id = "11,2,32";
        parameterDAO = new ParameterDAO(id);

        System.out.println("输入需要修改代码的路径: ");
        String input = bufferedReader.readLine();
//        String input = "D:\\study\\program\\Java\\Software-Test\\Software-Test\\exchange\\src\\test\\java\\code";
        parameterDAO.SetInput(input);
        filesDAO.SetInPackage(input.replace(parameterDAO.GetPath(),""));

        System.out.println("输入需要存放修改完成代码的文件名: ");
        String output = bufferedReader.readLine();
//        String output =  "code_out\\";
        filesDAO.SetOutPackage(output);
        output = parameterDAO.GetPath()+ output;
        parameterDAO.SetOutput(output);
    }
}

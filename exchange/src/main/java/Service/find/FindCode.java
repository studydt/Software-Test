package Service.find;

import Dao.FilesDAO;
import Dao.ParameterDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindCode {
    private static final String REGEX_boolean = "if.*\\{";
    private static final String REGEX_void = "void";
    private static final String REGEX_package = "package.*?;";
    private static final String REGEX_function = "boolean.*\\{";

    private static final String REPLACE_boolean_true = "if (true){";
    private static final String REPLACE_boolean_false = "if (false){";
    private static final String REPLACE_return_true = "return true;";
    private static final String REPLACE_return_false = "return false;";
    private static final String REPLACE_void = "";

    FilesDAO filesDAO;
    ParameterDAO parameterDAO;

    String pathout;
    String pathin;
    String[] filenames;

    public FindCode(ParameterDAO parameterDAO, FilesDAO filesDAO) throws IOException {
        this.filesDAO = filesDAO;
        this.parameterDAO = parameterDAO;
        filesDAO.findinput(parameterDAO.GetInput());
        //检查outpath是否存在, 不存在则创建
        filesDAO.findoutput(parameterDAO.GetOutput());
        pathin = parameterDAO.GetInput();
        pathout = parameterDAO.GetOutput();
        filenames = filesDAO.GetInput();

        out_package(filesDAO.GetInPackage(), filesDAO.GetOutPackage());
        select();
        System.out.println("请前往 "+ pathout+" 查看生成的代码");
    }

    public void select() throws IOException {
        String[] ids = parameterDAO.GetId();
        System.out.println("=========================Thank=============================");
        System.out.println("| 已完成功能 :                                              |");

        for (String id : ids) {
            if (id.equals("11")) {
                analyzeboolean(REPLACE_boolean_true);
                System.out.println("| 11: 将条件指令的布尔表达式替换为“true”                        |");
            }else if(id.equals("12")){
                analyzeboolean(REPLACE_boolean_false);
                System.out.println("| 12: 将条件指令的布尔表达式替换为“false”                       |");
            }
            if (id.equals("2")) {
                analyzevoid(REPLACE_void);
                System.out.println("| 2:  删除void方法体中所有指令                                |");
            }
            if (id.equals("31")) {
                analyzefunction(REPLACE_return_true);
                System.out.println("| 31: 由一个“返回true”指令替换一个布尔方法的方法体                |");
            }else if(id.equals("32")){
                analyzefunction(REPLACE_return_false);
                System.out.println("| 32: 由一个“返回false”指令替换一个布尔方法的方法体               |");
            }
        }
        System.out.println("===========================================================");

    }

    public void out_package(String inpackage, String outpackage) throws IOException {


        Pattern p = Pattern.compile(REGEX_package);

        for (String filename : filenames) {
            String code = filesDAO.ReadFile(pathin, filename);
            // get a matcher object
            Matcher m = p.matcher(code);
            if (m.find()) {
                String in_package = m.group(0);
                in_package = in_package.replaceAll(inpackage, outpackage);
                code = m.replaceAll(in_package);
//                System.out.println(code);
                filesDAO.WriteFile(pathout, filename, code);
            }
        }
    }

    public void analyzeboolean(String REPLACE_boolean) throws IOException {

        Pattern p = Pattern.compile(REGEX_boolean);
        for (String filename : filenames) {
            String code = filesDAO.ReadFile(pathout, filename);
            // get a matcher object
            Matcher m = p.matcher(code);
            code = m.replaceAll(REPLACE_boolean);
//            System.out.println(code);
            filesDAO.WriteFile(pathout, filename, code);

        }
    }

    public void analyzevoid(String replace_void) throws IOException {

        for (String filename : filenames) {
            String code = filesDAO.ReadFile(pathout, filename);
            String code_copy = code;

            ArrayList<Integer> wherevoid = new ArrayList<Integer>();
            Pattern p = Pattern.compile(REGEX_void);
            Matcher m = p.matcher(code);
            while (m.find()) {
                //查找返回值为void的函数数量, 并记录开始位置
                wherevoid.add(m.start(0));
            }
            code = FindAndReplace(wherevoid, code_copy, code, replace_void);
//            System.out.println(code);
            filesDAO.WriteFile(pathout, filename, code);

        }
    }

    public void analyzefunction(String replace_bool) throws IOException {

        for (String filename : filenames) {
            String code = filesDAO.ReadFile(pathout, filename);
            String code_copy = code;

            ArrayList<Integer> wherebool = new ArrayList<Integer>();
            Pattern p = Pattern.compile(REGEX_function);
            Matcher m = p.matcher(code);
            while (m.find()) {
                //查找返回值为boolean的函数数量, 并记录开始位置
                wherebool.add(m.start(0));
            }
            code = FindAndReplace(wherebool, code_copy, code, replace_bool);
//            System.out.println(code);
            filesDAO.WriteFile(pathout, filename, code);
        }
    }

    private String FindAndReplace(ArrayList<Integer> where, String code_copy, String code, String replace) {
        int[] index = new int[2];
        for (int j = 0; j < where.size(); ++j) {
            //记录boolean函数体大括号的位置
            int flag = code_copy.indexOf("{", where.get(j));
            int flag_match = flag;
            index[0] = flag + 1;
            while (true) {
                flag = code_copy.indexOf("{", flag + 1);
                flag_match = code_copy.indexOf("}", flag_match + 1);
                if (flag > flag_match || flag == -1) {
                    index[1] = flag_match;
                    break;
                }
            }

            String sub = code_copy.substring(index[0], index[1]);
            code = code.replace(sub, replace);
        }
        return code;
    }
}

package Dao;

import Beans.Files;

import java.io.*;

public class FilesDAO {

    private Files files;

    public FilesDAO() {
        files = new Files();
    }

    public String ReadFile(String path ,String filename) throws IOException {
        File f = new File(path + filename);
        FileInputStream fip = new FileInputStream(f);
        // 构建FileInputStream对象

        InputStreamReader reader = new InputStreamReader(fip, "UTF-8");
        // 构建InputStreamReader对象,编码与写入相同

        StringBuffer sb = new StringBuffer();
        while (reader.ready()) {
            sb.append((char) reader.read());
            // 转成char加到StringBuffer对象中
        }
        String sb_str = sb.toString();
//        System.out.println(sb.toString());
        reader.close();
        // 关闭读取流

        fip.close();
        // 关闭输入流,释放系统资源

        return sb_str;
    }

    public void WriteFile(String path,String filename, String code) throws IOException {
        File f = new File(path+filename);
        OutputStream fop = new FileOutputStream(f);
        // 构建FileOutputStream对象,文件不存在会自动新建
        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
// 构建OutputStreamWriter对象,参数可以指定编码,默认为操作系统默认编码,windows上是gbk
        writer.append(code);
// 写入到缓冲区
        writer.close();
// 关闭写入流,同时会把缓冲区内容写入文件,所以上面的注释掉
        fop.close();
// 关闭输出流,释放系统资源
    }

    public boolean findinput(String dirname) {
        File f1 = new File(dirname);
        if (f1.isDirectory()) {
//            System.out.println("目录 " + dirname);
            String s[] = f1.list();
            for (int i = 0; i < s.length; i++) {
                File f = new File(dirname + "/" + s[i]);
                if (f.isDirectory()) {
//                    System.out.println(s[i] + " 是一个目录");
                } else {
//                    System.out.println(s[i] + " 是一个文件");
                }
            }
            files.setFilesName(s);
            return true;
        } else {
            System.out.println(dirname + " 不是一个目录");
            return false;
        }
    }

    public boolean findoutput(String dirname) {
        File f2 = new File(dirname);
        if (f2.isDirectory()) {
            System.out.println("目录 " + dirname);
            return true;
        } else {
            System.out.println(dirname + " 不是一个目录");
            return false;
        }
    }

    public String[] GetInput() {
        return files.getFilesName();
    }
    public String GetOutPackage() {
        return files.getOut_package();
    }
    public String GetInPackage() {
        return files.getIn_package();
    }

    public void  SetOutPackage(String output) {
        if(output.charAt(output.length()-1)=='\\'){
            output = output.substring(0,output.length()-1);
        }
        files.setOut_package(output);
    }
    public void  SetInPackage(String input) {
        if(input.charAt(input.length()-1)=='\\'){
            input = input.substring(0,input.length()-1);
        }
        files.setIn_package(input);
    }
}

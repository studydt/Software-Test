package Dao;

import Beans.Parameter;

public class ParameterDAO {

    private Parameter parameter;

    public ParameterDAO(String id) {
        Parameter parameter = new Parameter();
        String[] ids = id.split(",");
        parameter.setId(ids);
        this.parameter = parameter;
    }
    public String GetPath(String str){
        String[] path = str.split("\\\\");
        int len = path.length-1;
        int i =str.indexOf(path[len]);
        return str.substring(0,i);

    }
    public String[] GetId(){
        return parameter.getId();
    }
    public String GetInput() {
        return parameter.getINPUT_PATH();
    }

    public String GetOutput() {
        return parameter.getOUTPUT_PATH();
    }

    public void SetInput(String input) {
        if(input.charAt(input.length()-1)!='\\'){
            input = input +"\\";
        }
        parameter.setINPUT_PATH(input);
    }

    public void SetOutput(String output) {
        if(output.charAt(output.length()-1)!='\\'){
            output = output+"\\";
        }
        parameter.setOUTPUT_PATH(output);
    }

}


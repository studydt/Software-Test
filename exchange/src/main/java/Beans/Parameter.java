package Beans;

public class Parameter {
    private String INPUT_PATH = "";
    private String OUTPUT_PATH = "";
    private String[] id= new String[3];

    public String getINPUT_PATH() {
        return INPUT_PATH;
    }

    public void setINPUT_PATH(String INPUT_PATH) {
        this.INPUT_PATH = INPUT_PATH;
    }

    public String getOUTPUT_PATH() {
        return OUTPUT_PATH;
    }

    public void setOUTPUT_PATH(String OUTPUT_PATH) {
        this.OUTPUT_PATH = OUTPUT_PATH;
    }

    public String[] getId() {
        return id;
    }

    public void setId(String[] id) {
        this.id = id;
    }
}

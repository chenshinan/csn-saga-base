package com.chenshinan.sagabase.saga.bean;

/**
 * @author shinan.chen
 * @date 2018/9/3
 */
public class SagaTaskInstanceBean {
    private String code;
    private String input;
    private String output;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}

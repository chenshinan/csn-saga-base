package com.chenshinan.sagabase.saga.bean;

/**
 * @author shinan.chen
 * @date 2018/9/3
 */
public class SagaTaskInstanceBean {
    private Long id;
    private String sagaCode;
    private String sagaTaskCode;
    private int seq;
    private String status;
    private String input;
    private String output;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSagaCode() {
        return sagaCode;
    }

    public void setSagaCode(String sagaCode) {
        this.sagaCode = sagaCode;
    }

    public String getSagaTaskCode() {
        return sagaTaskCode;
    }

    public void setSagaTaskCode(String sagaTaskCode) {
        this.sagaTaskCode = sagaTaskCode;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

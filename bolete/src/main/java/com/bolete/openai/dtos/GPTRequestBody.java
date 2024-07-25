package com.bolete.openai.dtos;

public class GPTRequestBody {
    
    private String userMessagePrompt;
    private int systemPromptNum;

    public GPTRequestBody(String userMessagePrompt, int systemPromptNum) {
        this.userMessagePrompt = userMessagePrompt;
        this.systemPromptNum = systemPromptNum;
    }

    public String getUserMessagePrompt() {
        return userMessagePrompt;
    }
    public void setUserMessagePrompt(String userMessagePrompt) {
        this.userMessagePrompt = userMessagePrompt;
    }
    public int getSystemPromptNum() {
        return systemPromptNum;
    }
    public void setSystemPromptNum(int systemPromptNum) {
        this.systemPromptNum = systemPromptNum;
    }

}

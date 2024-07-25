package com.bolete.openai.dtos;


import java.util.ArrayList;

public class GPTRequest {
    private String model;
    private ArrayList<RequestMessage> messages;
    private int n;
    private double temperature;

    public GPTRequest() {}

    public GPTRequest(String model, String userPrompt, String systemPrompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new RequestMessage("user", userPrompt));
        this.messages.add(new RequestMessage("system", systemPrompt));

        this.n = 1;
    }

    public GPTRequest(String model, String userPrompt, String systemPrompt, int n) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new RequestMessage("user", userPrompt));
        this.messages.add(new RequestMessage("system", systemPrompt));
        
        this.n = n;
        // set the temperature to provide variation
        this.temperature = 0.5;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public ArrayList<RequestMessage> getMessages() {
        return messages;
    }
    public void setMessages(ArrayList<RequestMessage> messages) {
        this.messages = messages;
    }

    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }


}

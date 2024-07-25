package com.bolete.openai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bolete.openai.dtos.GPTRequest;
import com.bolete.openai.dtos.GPTRequestBody;
import com.bolete.openai.enums.SystemPrompt;



@Service
public class GPTRequestService {

    @Value("${openai.model}")
    private String model;
    @Autowired
    private SystemPromptService systemPromptService;
   
    public GPTRequest getGPTRequest(GPTRequestBody requestBody) {
        GPTRequest request = null;
        String userMessagePrompt = requestBody.getUserMessagePrompt();
        int systemPromptNum = requestBody.getSystemPromptNum();
        SystemPrompt systemPrompt = systemPromptService.getSystemPrompt(systemPromptNum);
        String systemPromptMessage = systemPromptService.getMessage(systemPrompt);

        // a criação de request pode ser customizada aqui

        // switch(systemPrompt) {
        //     case HARD_SKILLS:
        //         return new GPTRequest(model, userMessagePrompt, systemPromptMessage);                 
        // }
        request = new GPTRequest(model, userMessagePrompt, systemPromptMessage);
        return request;
    }
}


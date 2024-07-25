package com.bolete.openai.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolete.openai.dtos.Completion;
import com.bolete.openai.dtos.GPTRequest;
import com.bolete.openai.dtos.GPTRequestBody;

@Service
public class ExtrairCamposService {

    @Autowired
    private GPTRequestService requestService;

    @Autowired
    private GPTCompletionService completionService;

    // promptService só vai ser usado quando tiver mais prompts a fazer para o gpt
    @Autowired
    private SystemPromptService promptService;

    public void extrairCampos(String fullText) {
        GPTRequestBody body = new GPTRequestBody(fullText, 0);
        GPTRequest request = requestService.getGPTRequest(body);
        Completion completion = completionService.getCompletion(request);
        String resposta = completion.choices().get(0).message().content().trim();

        System.out.println("RESPOSTA DA EXTRAÇÃO DE CAMPOS: " + resposta);
    }

}

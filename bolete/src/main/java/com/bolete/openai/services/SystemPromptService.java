package com.bolete.openai.services;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.bolete.openai.enums.SystemPrompt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class SystemPromptService {
    private Map<SystemPrompt, String> messages;
    public SystemPromptService() {
        // Load JSON data and initialize messages map
        loadMessagesFromJson();
    }
    private void loadMessagesFromJson() {
        try {
            // Load JSON data from file
            ClassPathResource resource = new ClassPathResource("static/system-prompts.json");
            String jsonData = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            // Parse JSON data into map
            ObjectMapper mapper = new ObjectMapper();
            messages = mapper.readValue(jsonData, new TypeReference<Map<SystemPrompt, String>>() {});
        } catch (IOException e) {
            System.out.println( 
                "Group4 says: Error in reading system-prompt.json!"
            + "\nAre you sure the the SystemPrompt Enum and the JSON contents are consistent?");
            
            e.printStackTrace();
        }
    }

    public String getMessage(SystemPrompt systemPrompt) {
        return messages.get(systemPrompt);
    }
    
    public SystemPrompt getSystemPrompt(int systemPromptNum) {
        // Use the ordinal value of the enum constants to map the integer value
        SystemPrompt[] values = SystemPrompt.values();
        if (systemPromptNum >= 0 && systemPromptNum < values.length) {
            return values[systemPromptNum];
        } else {
            throw new IllegalArgumentException("Invalid systemPromptNum: " + systemPromptNum);
        }
    }
}
package com.bolete.openai.dtos;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Choice(String finishReason, Message message) {}

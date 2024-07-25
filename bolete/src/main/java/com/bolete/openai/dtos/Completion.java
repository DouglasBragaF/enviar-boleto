package com.bolete.openai.dtos;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Completion(String id, ArrayList<Choice> choices, Integer created) {}

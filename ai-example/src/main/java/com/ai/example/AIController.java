package com.ai.example;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AIController {

    private OllamaChatClient client;

    private static final String PROMPT = "what is java language";

    public AIController(OllamaChatClient client) {
        this.client = client;
    }

    @GetMapping("/prompt")
    public Flux<ChatResponse> promptResponse(
            @RequestParam("prompt") String prompt
    ) {
        Prompt promptOb = new Prompt(prompt);
        Flux<ChatResponse> response = client.stream(promptOb);
        return response;

    }

}



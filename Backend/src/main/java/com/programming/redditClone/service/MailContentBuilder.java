package com.programming.redditClone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailContentBuilder {
    private final TemplateEngine templateEngine;
// for mail template build
    String Build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("MailTemplate", context);
    }
}

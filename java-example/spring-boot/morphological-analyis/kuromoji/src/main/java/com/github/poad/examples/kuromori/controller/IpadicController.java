package com.github.poad.examples.kuromori.controller;

import com.github.poad.examples.kuromori.model.AnalyzeRequest;
import com.github.poad.examples.kuromori.model.AnalyzeResponse;
import com.github.poad.examples.kuromori.service.KuromojiIpadicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/kuromoji/ipadic", produces = {MediaType.APPLICATION_JSON_VALUE})
public class IpadicController extends KuromojiController<KuromojiIpadicService> {
    protected IpadicController(@Autowired KuromojiIpadicService service) {
        super(service);
    }

    @GetMapping
    public AnalyzeResponse analyze(@RequestParam(name = "text") String text) {
        return analyzeInternal(text);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AnalyzeResponse analyze(@Valid @RequestBody AnalyzeRequest request) {
        return analyzeInternal(request.getText());
    }
}

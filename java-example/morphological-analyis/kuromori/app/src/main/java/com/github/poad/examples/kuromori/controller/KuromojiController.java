package com.github.poad.examples.kuromori.controller;

import com.github.poad.examples.kuromori.model.AnalyzeResponse;
import com.github.poad.examples.kuromori.service.MorphologicalAnalysisService;

import java.util.stream.Collectors;

abstract class KuromojiController<S extends MorphologicalAnalysisService> {
    private final S service;

    protected KuromojiController(S service) {
        this.service = service;
    }

    protected AnalyzeResponse analyzeInternal(String text) {
        return new AnalyzeResponse(service.parse(text).stream()
                .filter(item -> !item.getKeyword().isBlank())
                .map(item -> new AnalyzeResponse.Result(
                        item.getKeyword(),
                        new AnalyzeResponse.Result.Attributes(
                                item.getAttributes().getPos(),
                                item.getAttributes().getWord()
                        )
                ))
                .collect(Collectors.toList()));
    }
}

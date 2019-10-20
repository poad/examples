package com.github.poad.examples.kuromori.service;

import com.atilika.kuromoji.TokenBase;
import com.github.poad.examples.kuromori.morphogical.Kuromoji;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

abstract class KuromojiService<T extends TokenBase> implements MorphologicalAnalysisService {

    private static final Logger logger = LogManager.getLogger(MorphologicalAnalysisService.class);

    private final Kuromoji<? extends TokenBase> kuromoji;

    protected KuromojiService(Kuromoji<? extends TokenBase> kuromoji) {
        this.kuromoji = kuromoji;
    }

    @Override
    public List<MorphologicalAnalysisService.AnalyzeResult> parse(String text) {
        List<? extends TokenBase> tokens = kuromoji.parse(text);
        return tokens.stream().map(token -> {
            String keyword = ((TokenBase) token).getSurface();
            String[] features = ((TokenBase) token).getAllFeaturesArray();

            List<String> pos = Arrays.stream(features, 0, 6)
                    .filter(f -> !f.equals("*"))
                    .filter(f -> !f.isBlank())
                    .collect(Collectors.toList());
            List<String> word = Arrays.stream(features, 6, features.length)
                    .filter(f -> !f.equals("*"))
                    .filter(f -> !f.isBlank())
                    .collect(Collectors.toList());
            AnalyzeResult.Attributes attributes = new AnalyzeResult.Attributes(
                    pos,
                    word
            );
            return new AnalyzeResult(keyword, attributes);
        })
        .collect(Collectors.toList());
    }
}

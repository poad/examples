package com.github.poad.examples.kuromori.service;

import com.atilika.kuromoji.TokenBase;
import com.atilika.kuromoji.unidic.Token;
import com.github.poad.examples.kuromori.morphogical.Kuromoji;
import com.github.poad.examples.kuromori.morphogical.Unidic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KuromojiUnidicService implements MorphologicalAnalysisService {
    private static final Logger logger = LogManager.getLogger(MorphologicalAnalysisService.class);

    private final Kuromoji<Token> kuromoji;

    public KuromojiUnidicService(@Autowired Unidic kuromoji) {
        this.kuromoji = kuromoji;
    }

    @Override
    public List<AnalyzeResult> parse(String text) {
        List<Token> tokens = kuromoji.parse(text);
        return tokens.stream().map(token -> {
            String keyword = ((TokenBase) token).getSurface();
            String[] features = ((TokenBase) token).getAllFeaturesArray();

            List<String> pos1 = Arrays.stream(features, 0, 6)
                    .filter(f -> !f.equals("*"))
                    .filter(f -> !f.isBlank())
                    .collect(Collectors.toList());
            List<String> pos2 = Arrays.stream(features, 12, features.length)
                    .filter(f -> !f.equals("*"))
                    .filter(f -> !f.isBlank())
                    .collect(Collectors.toList());
            List<String> word = Arrays.stream(features, 6, 12)
                    .filter(f -> !f.equals("*"))
                    .filter(f -> !f.isBlank())
                    .collect(Collectors.toList());
            List<String> pos = List.of(pos1, pos2).stream().flatMap(List::stream).collect(Collectors.toList());

            AnalyzeResult.Attributes attributes = new AnalyzeResult.Attributes(
                    pos,
                    word
            );
            return new AnalyzeResult(keyword, attributes);
        })
        .collect(Collectors.toList());
    }
}

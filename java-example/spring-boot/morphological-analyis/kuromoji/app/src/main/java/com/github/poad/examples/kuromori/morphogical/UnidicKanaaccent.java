package com.github.poad.examples.kuromori.morphogical;

import com.atilika.kuromoji.unidic.kanaaccent.Token;
import com.atilika.kuromoji.unidic.kanaaccent.Tokenizer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("singleton")
public class UnidicKanaaccent implements Kuromoji<Token> {
   @Override
    public List<Token> parse(String text) {
        return new Tokenizer().tokenize(text);
    }
}

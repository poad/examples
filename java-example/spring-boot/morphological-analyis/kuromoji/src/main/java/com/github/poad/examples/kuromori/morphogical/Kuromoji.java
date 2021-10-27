package com.github.poad.examples.kuromori.morphogical;

import com.atilika.kuromoji.TokenBase;

import java.util.List;

public interface Kuromoji<T extends TokenBase> {
    List<T> parse(String text);
}

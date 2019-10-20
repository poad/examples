package com.github.poad.examples.kuromori.service;

import com.atilika.kuromoji.naist.jdic.Token;
import com.github.poad.examples.kuromori.morphogical.NaistJdic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KuromojiNaistJdicService extends KuromojiService<Token> {
    public KuromojiNaistJdicService(@Autowired NaistJdic kuromoji) {
        super(kuromoji);
    }
}

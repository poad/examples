package com.github.poad.examples.kuromori.service;

import com.atilika.kuromoji.ipadic.Token;
import com.github.poad.examples.kuromori.morphogical.Ipadic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KuromojiIpadicService extends KuromojiService<Token> {
    public KuromojiIpadicService(@Autowired Ipadic kuromoji) {
        super(kuromoji);
    }
}

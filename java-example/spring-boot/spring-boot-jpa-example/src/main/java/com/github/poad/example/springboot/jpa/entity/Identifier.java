package com.github.poad.example.springboot.jpa.entity;

import com.github.poad.example.springboot.jpa.exception.UncheckedGeneralSecurityException;

import jakarta.persistence.*;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ken-yo on 2016/08/06.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Identifier implements Entity, Serializable {
    @Column(name = "id")
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String generateID() {
        try {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
                    oos.writeObject(this);
                    oos.flush();
                }
                return new BigInteger(1, MessageDigest.getInstance("MD5").digest(out.toByteArray())).toString(16);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (NoSuchAlgorithmException e) {
            // ありえない
            throw new UncheckedGeneralSecurityException(e);
        }
    }
}

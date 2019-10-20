package com.github.poad.examples.kuromori.model;

import java.util.List;

public class AnalyzeResponse {
    private final List<Result> results;

    public AnalyzeResponse(List<Result> results) {
        this.results = results;
    }

    public List<Result> getResults() {
        return results;
    }

    public static class Result {
        private final String keyword;
        private final Attributes attributes;

        public Result(String keyword, Attributes attributes) {
            this.keyword = keyword;
            this.attributes = attributes;
        }

        public String getKeyword() {
            return keyword;
        }

        public Attributes getAttributes() {
            return attributes;
        }

        public static class Attributes {
            private final List<String> pos;
            private final List<String> word;

            public Attributes(List<String> pos, List<String> word) {
                this.pos = pos;
                this.word = word;
            }

            public List<String> getPos() {
                return pos;
            }

            public List<String> getWord() {
                return word;
            }
        }
    }
}

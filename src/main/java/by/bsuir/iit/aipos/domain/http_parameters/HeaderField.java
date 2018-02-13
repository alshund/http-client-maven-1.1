package by.bsuir.iit.aipos.domain.http_parameters;

public enum HeaderField {

    DATE {
        @Override
        public String getPatternCode() {
            return "httpPatterns.date";
        }
    },

    PRAGMA {
        @Override
        public String getPatternCode() {
            return "httpPatterns.pragma";
        }
    },

    AUTHORIZATION {
        @Override
        public String getPatternCode() {
            return "httpPatterns.authorization";
        }
    },

    FROM {
        @Override
        public String getPatternCode() {
            return "httpPatterns.from";
        }
    },

    IF_MODIFIED_SINCE {
        @Override
        public String getPatternCode() {
            return "httpPatterns.ifModifiedSince";
        }
    },

    REFERER {
        @Override
        public String getPatternCode() {
            return "httpPatterns.referer";
        }
    },

    USER_AGENT {
        @Override
        public String getPatternCode() {
            return "httpPatterns.userAgent";
        }
    },

    ALLOW {
        @Override
        public String getPatternCode() {
            return "httpPatterns.allow";
        }
    },

    CONTENT_ENCODING {
        @Override
        public String getPatternCode() {
            return "httpPatterns.contentEncoding";
        }
    },

    CONTENT_LENGTH {
        @Override
        public String getPatternCode() {
            return "httpPatterns.contentLength";
        }
    },

    CONTENT_TYPE {
        @Override
        public String getPatternCode() {
            return "httpPatterns.contentType";
        }
    },

    EXPIRES {
        @Override
        public String getPatternCode() {
            return "httpPatterns.expires";
        }
    },

    LAST_MODIFIED {
        @Override
        public String getPatternCode() {
            return "httpPatterns.lastModified";
        }
    };

    public abstract String getPatternCode();
}

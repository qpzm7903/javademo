package com.qpzm7903.behavioural_patterns.chain_of_responsibility.template;

public interface Message {
    String getMessage();

    int getCode();

    class DefaultMessage implements Message {
        private String message;
        private int code;

        public DefaultMessage(String message, int code) {
            this.message = message;
            this.code = code;
        }

        @Override
        public String getMessage() {
            return this.message;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String toString() {
            return "DefaultMessage{" +
                    "message='" + message + '\'' +
                    ", code=" + code +
                    '}';
        }
    }

}

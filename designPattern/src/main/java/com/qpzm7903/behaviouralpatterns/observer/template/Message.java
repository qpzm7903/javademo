package com.qpzm7903.behaviouralpatterns.observer.template;

public interface Message {
    String getMessage();

    class DefaultMessage implements Message {
        private final String message;

        public DefaultMessage(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return this.message;
        }

    }
}

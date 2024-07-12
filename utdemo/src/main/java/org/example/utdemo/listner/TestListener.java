package org.example.utdemo.listner;

import org.example.utdemo.domain.User;
import org.example.utdemo.event.TestEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener implements ApplicationListener<TestEvent> {
    @Override
    public void onApplicationEvent(TestEvent event) {
        System.out.println(event.getWhat());
    }
}

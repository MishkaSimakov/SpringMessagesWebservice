package com.example.messages.controllers;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessagesApiController {
    private final List<String> messages = new ArrayList<>();

    // curl -X GET http://localhost:8080/messages
    // curl -X GET http://localhost:8080/messages?start=hello
    @GetMapping("messages")
    public ResponseEntity<List<String>> getMessages(@RequestParam(value = "start", required = false) String start) {
        if (start == null)
            return ResponseEntity.ok(messages);

        List<String> filteredMessages = new ArrayList<>();

        for (String message: messages) {
            if (message.startsWith(start))
                filteredMessages.add(message);
        }

        return ResponseEntity.ok(filteredMessages);
    }

    // curl -d "Hello" -X POST http://localhost:8080/messages
    @PostMapping("messages")
    public ResponseEntity<Void> addMessage(@RequestBody String text) {
        messages.add(text);
        return ResponseEntity.accepted().build();
    }

    // curl -X GET http://localhost:8080/messages/0
    @GetMapping("messages/{index}")
    public ResponseEntity<String> getMessage(@PathVariable("index") Integer index) {
        return ResponseEntity.ok(messages.get(index));
    }

    // curl -X DELETE http://localhost:8080/messages/0
    @DeleteMapping("messages/{index}")
    public ResponseEntity<Void> deleteText(@PathVariable("index") Integer index) {
        messages.remove((int) index);
        return ResponseEntity.noContent().build();
    }

    // curl -d "hello" -X PUT http://localhost:8080/messages/0
    @PutMapping("messages/{index}")
    public ResponseEntity<Void> updateMessage(@PathVariable("index") Integer i, @RequestBody String message) {
        messages.remove((int) i);
        messages.add(i, message);
        return ResponseEntity.accepted().build();
    }

    // curl -X GET http://localhost:8080/messages/search/Hello
    @GetMapping("messages/search/{text}")
    public ResponseEntity<Integer> searchMessage(@PathVariable("text") String text) {
        for (int i = 0; i < messages.size(); ++i) {
            if (messages.get(i).contains(text))
                return ResponseEntity.ok(i);
        }

        return ResponseEntity.ok(-1);
    }

    // curl -X GET http://localhost:8080/messages/count
    @GetMapping("messages/count")
    public ResponseEntity<Integer> messagesCount() {
        return ResponseEntity.ok(messages.size());
    }

    // curl -d "Hello" -X POST http://localhost:8080/messages/0/create
    @PostMapping("messages/{index}/create")
    public ResponseEntity<Void> addMessageWithIndex(@RequestBody String text, @PathVariable("index") Integer index) {
        messages.add(index, text);

        return ResponseEntity.accepted().build();
    }

    // curl -X DELETE http://localhost:8080/message/search/Hello
    @DeleteMapping("messages/search/{text}")
    public ResponseEntity<Void> deleteMessagesWithText(@PathVariable("text") String text) {
        CollectionUtils.filter(messages, m -> !m.contains(text));

        return ResponseEntity.noContent().build();
    }
}
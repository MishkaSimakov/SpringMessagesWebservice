package com.example.messages.controllers;

import com.example.messages.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsersApiController {
    private final List<User> users = new ArrayList<>();

    // curl -X POST -H "Content-Type: application/json" http://localhost:8080/users -d '{"name": "Misha", "age": 17}'
    @PostMapping("users")
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        users.add(user);
        return ResponseEntity.accepted().build();
    }

    // curl -X DELETE -H "Content-Type: application/json" http://localhost:8080/users/0
    @DeleteMapping("users/{index}")
    public ResponseEntity<Void> deleteUser(@PathVariable("index") Integer index) {
        users.remove((int) index);
        return ResponseEntity.accepted().build();
    }

    // curl -X GET -H "Content-Type: application/json" http://localhost:8080/users/0
    @GetMapping("users/{index}")
    public ResponseEntity<String> getUser(@PathVariable("index") Integer index) {
        User user = users.get(index);

        return ResponseEntity.ok(user.toString());
    }

    // curl -X GET -H "Content-Type: application/json" http://localhost:8080/users
    @GetMapping("users")
    public ResponseEntity<String> getUsersList() {
        StringBuilder usersString = new StringBuilder();

        for (User user : users)
            usersString.append(user.toString())
                    .append("\n");

        return ResponseEntity.ok(usersString.toString());
    }

    // curl -X PUT -H "Content-Type: application/json" http://localhost:8080/users/0/age -d '{"age": 18}'
    @PutMapping("users/{index}/age")
    public ResponseEntity<Void> updateUserAge(@PathVariable("index") Integer index, @RequestBody User newUser) {
        User user = users.get(index);

        users.remove((int) index);

        user.setAge(newUser.getAge());
        users.add(index, user);

        return ResponseEntity.accepted().build();
    }
}

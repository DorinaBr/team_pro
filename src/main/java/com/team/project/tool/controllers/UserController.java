package com.team.project.tool.controllers;

import com.team.project.tool.models.dtos.ReadUserDTO;
import com.team.project.tool.models.dtos.WriteUserDTO;
import com.team.project.tool.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ReadUserDTO> createUser(@RequestBody WriteUserDTO writeUserDto) {
        return ResponseEntity.ok(userService.createUser(writeUserDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ReadUserDTO> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ReadUserDTO> updateUser(@PathVariable Long userId, @RequestBody WriteUserDTO writeUserDto) {
        return ResponseEntity.ok(userService.updateUser(userId, writeUserDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}

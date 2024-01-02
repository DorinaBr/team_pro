package com.team.project.tool.controllers;

import com.team.project.tool.models.dtos.ReadUserDTO;
import com.team.project.tool.models.dtos.WriteUserDTO;
import com.team.project.tool.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(HttpServletRequest request, @RequestBody WriteUserDTO writeUserDto) {
        Long id = userService.createUser(writeUserDto);
        return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadUserDTO> readUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.readUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") Long id, @RequestBody WriteUserDTO writeUserDto) {
        userService.updateUser(id, writeUserDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}

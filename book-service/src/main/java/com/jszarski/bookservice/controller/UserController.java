package com.jszarski.bookservice.controller;


import com.jszarski.bookservice.service.UserService;
import com.jszarski.common.model.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void registerNewUser(@RequestBody UserDTO userDTO){
        userService.register(userDTO, false);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public void registerNewAdmin(@RequestBody UserDTO userDTO){
        userService.register(userDTO, true);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable("username") String username){
        userService.delete(username);
    }
}

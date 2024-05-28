package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@Validated
@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers(Principal principal, Model model) {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<HttpStatus> addNewUser(@Valid @RequestBody User user, BindingResult bindingResult) {
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        userService.saveUser(user);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<?> addNewUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        return getResponse(user, bindingResult);
    }

//    @PutMapping
//    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        userService.saveUser(user);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        return getResponse(user, bindingResult);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id) {
        User user = userService.getUser(id).get();
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<?> getResponse(User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            allErrors.forEach(err -> {
                FieldError fieldError = (FieldError) err;
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


//    @GetMapping("/addNewUser")
//    public String addNewUser(Principal principal, Model model) {
//        User admin = userService.findByUsername(principal.getName());
//        model.addAttribute("adminUser", admin);
//        User user = new User();
//        model.addAttribute("user", user);
//        List<Role> roles = roleService.getAllRoles();
//        model.addAttribute("roles", roles);
//        return "add-new-user";
//    }
//
//    @PostMapping("/saveUser")
//    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam("role") String roleName, Model model, Principal principal) {
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) {
//            User admin = userService.findByUsername(principal.getName());
//            model.addAttribute("adminUser", admin);
//            List<Role> roles = roleService.getAllRoles();
//            model.addAttribute("roles", roles);
//            return "add-new-user";
//        }
//        userService.saveUser(user, roleName);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/deleteUser")
//    public String deleteUser(@RequestParam("id") long id) {
//        userService.deleteUser(id);
//        return "redirect:/admin";
//    }
}

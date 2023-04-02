package com.cydeo.controller;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

   private RoleService roleService;
   private UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createUser(Model model){

        model.addAttribute("user",new UserDTO());
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("users",userService.findAll());
        return "/user/create";
    }

    @PostMapping("/create")
    public String saveUser(@ModelAttribute("user") UserDTO user, Model model){
       // model.addAttribute("user",new UserDTO());
       // model.addAttribute("roles",roleService.findAll());
        userService.save(user);
       // model.addAttribute("users",userService.findAll());
        return "redirect:/user/create";
    }

    @GetMapping("/update/{userName}")
    public String editUser(@PathVariable("userName") String username,Model model){

        model.addAttribute("user",userService.findById(username));
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("users",userService.findAll());
        return "/user/update";
    }

    @PostMapping("/update")
    public String updateUser(UserDTO user){
        userService.update(user);
        return "redirect:/user/create";
    }

    @GetMapping("/delete/{userName}")
    public String deleteUser(@PathVariable("userName") String username){
        userService.deleteById(username);
        return "redirect:/user/create";
    }


}

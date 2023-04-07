package com.cydeo.controller;


import com.cydeo.dto.TaskDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskController {

    TaskService taskService;
    ProjectService projectService;
    UserService userService;

    public TaskController(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createTask(Model model){
        model.addAttribute("task",new TaskDTO());
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("employees",userService.findAll());
        model.addAttribute("tasks", taskService.findAll());
        return "/task/create";
    }

    @PostMapping("/create")
    public String saveTask(Model model,TaskDTO task){
        model.addAttribute("task",taskService.save(task));
        return "redirect:/task/create";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(@ModelAttribute("taskId") Long taskId){
        taskService.deleteById(taskId);
        return "redirect:/task/create";
    }

    @GetMapping("/update/{taskId}")
    public String editTask(@ModelAttribute("taskId") Long taskId,Model model){
        model.addAttribute("task",taskService.findById(taskId));
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("employees",userService.findAll());
        model.addAttribute("tasks", taskService.findAll());
        return "/task/update";
    }

    @PostMapping("/update/{taskId}")
    public String updateTask(@PathVariable("taskId") Long taskId, TaskDTO taskDTO){

        taskDTO.setId(taskId);

        taskService.update(taskDTO);

        return "redirect:/task/create";
    }

}

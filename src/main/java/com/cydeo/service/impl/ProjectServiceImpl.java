package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO,String> implements ProjectService {


    TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO save(ProjectDTO object) {

        if(object.getProjectStatus()==null){
            object.setProjectStatus(Status.OPEN);
        }

        return super.save(object.getProjectCode(),object);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public ProjectDTO findById(String s) {
        return super.findBy(s);
    }

    @Override
    public void update(ProjectDTO object) {
        ProjectDTO newProject = findById(object.getProjectCode());

        if(object.getProjectStatus()==null){
            object.setProjectStatus(newProject.getProjectStatus());
        }
        super.update(object.getProjectCode(),object);
    }

    @Override
    public void complete(ProjectDTO projectDTO) {
        projectDTO.setProjectStatus(Status.COMPLETE);
        super.save(projectDTO.getProjectCode(),projectDTO);
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectsDTO(UserDTO manager) {

        List<ProjectDTO> projectList =
                findAll().stream().filter(p -> p.getAssignedManager().equals(manager))
                        .map(p ->{

                            List<TaskDTO> taskList = taskService.findTaskByManager(manager);

                            int completeTaskCounts = (int)taskList.stream().filter(t -> t.getProject().equals(p)
                            && t.getStatus() == Status.COMPLETE).count();
                            int unfinishedTaskCounts = (int)taskList.stream().filter(t -> t.getProject().equals(p)
                                    && t.getStatus() != Status.COMPLETE).count();

                            p.setCompleteTaskCounts(completeTaskCounts);
                            p.setUnfinishedTaskCounts(unfinishedTaskCounts);

                            return p;
                        }).collect(Collectors.toList());

        return projectList;
    }
}

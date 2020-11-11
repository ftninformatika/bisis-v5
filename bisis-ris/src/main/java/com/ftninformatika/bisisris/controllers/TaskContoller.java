package com.ftninformatika.bisisris.controllers;

import com.ftninformatika.bisisris.models.Task;
import com.ftninformatika.bisisris.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskContoller {
    @Autowired
    TaskRepository tr;
    @PostMapping(value = "/add")
    public boolean saveTasks(@RequestBody List<Task> tasks){
        try{
            for(Task t:tasks) {
                tr.save(t);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @GetMapping(value = "/getToday/{librarian}")
    public List<Task> getTodayTasksByLibrarian(@PathVariable("librarian") String librarian){
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE,0);
        today.set(Calendar.SECOND,0);
        Sort sort = new Sort(Sort.Direction.DESC, "dateOfService");
        return tr.getTaskByLibrarianAndDateOfServiceAfter(librarian,today.getTime(),sort);
    }
}

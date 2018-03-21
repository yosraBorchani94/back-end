package org.sid.web;
import java.util.List;

import org.sid.dao.TaskRepository;
import org.sid.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class TaskRestController {
    
	@Autowired
	private TaskRepository taskRepository;
	@GetMapping("/tasks")
	public List<Task> listTasks(){
		return taskRepository.findAll();
	}
	
	@GetMapping("/tasks/{id}")
	public Task getTask(@PathVariable Long id){
		return taskRepository.findOne(id);
	}
		
	@PostMapping("/tasks")
	public Task save(@RequestBody Task t){
		return taskRepository.save(t);
	}
	
	@PutMapping("/tasks/{id}")
	public Task update (@PathVariable Long id,@RequestBody Task t) {
		t.setId(id);
		return taskRepository.save(t);
	}
	
	@DeleteMapping("/tasks/{id}")
	public boolean delete(@PathVariable Long id){
		taskRepository.delete(id);
		return true ;
	}
}

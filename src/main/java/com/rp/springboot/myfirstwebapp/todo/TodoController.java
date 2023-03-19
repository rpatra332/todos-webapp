package com.rp.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

//@Controller
@SessionAttributes("name")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap modelMap) {
		String username = getLogggedInUsername(modelMap);
		List<Todo> todos = todoService.findByUsername(username);
		modelMap.put("todos", todos);
		return "listTodos";
	}

	@RequestMapping(value = "add-todo", method = RequestMethod.GET)
	public String showNewTodoPage(ModelMap modelMap) {
		String username = getLogggedInUsername(modelMap);
		Todo todo = new Todo(0,username,null,LocalDate.now().plusYears(1), false);
		modelMap.put("todo", todo);
		return "todo";
	}

	@RequestMapping(value = "add-todo", method = RequestMethod.POST)
	public String addNewTodo(ModelMap modelMap, @Valid Todo todo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "todo";
		}
		String username = getLogggedInUsername(modelMap);
		todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), false);
		return "redirect:list-todos"; 
	}
	
	@RequestMapping(value = "delete-todo")
	public String deleteTodo(@RequestParam int id) {
		todoService.deleteTodoById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value = "update-todo", method=RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap modelMap) {
		Todo todo = todoService.findById(id);
		modelMap.addAttribute("todo",todo);
		return "todo";
	}
	
	@RequestMapping(value = "update-todo", method=RequestMethod.POST)
	public String updateExistingTodo(ModelMap modelMap, @Valid Todo todo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "todo";
		}
		String username = getLogggedInUsername(modelMap);
		todo.setUserName(username);
		todoService.updateTodo(todo);
		return "redirect:list-todos";
	}
	
	private String getLogggedInUsername(ModelMap modelMap) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}

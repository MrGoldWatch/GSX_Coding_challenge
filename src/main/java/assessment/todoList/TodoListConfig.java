package assessment.todoList;

import assessment.openapi.models.Todolist;
import assessment.openapi.models.TodolistCreate;
import io.micronaut.http.annotation.*;
import io.micronaut.http.HttpResponse;
//import io.micronaut.http.annotation.RequestAttribute;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Controller("/todolist")
public class TodoListConfig {

  @Inject
  private TodoListService service;

  @Post
  public HttpResponse addTodoList(TodolistCreate todolistCreate) {
      service.addTodoList(todolistCreate);
    return HttpResponse.ok();
  }

  @Get("/{email}")
  public List<Todolist> getAllTodoLists(@NotNull @PathVariable String email) throws SQLException {
    return service.getAllTodoLists(email);
  }

  @Patch("/{id}")
  public void updateTodoList(@NotNull @PathVariable UUID id, TodolistCreate todolistCreate) {
    service.updateTodoList(id, todolistCreate);
  }

  @Delete("/{id}")
  public void removeTodoList(@NotNull @PathVariable UUID id) {
    service.removeTodoList(id);
  }

}

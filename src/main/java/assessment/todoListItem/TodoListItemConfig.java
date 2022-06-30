package assessment.todoListItem;

import assessment.openapi.models.Todolist;
import assessment.openapi.models.TodolistCreate;
import assessment.openapi.models.TodolistItemCreate;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Controller("/todolistitem")
public class TodoListItemConfig {

  @Inject
  private TodoListItemService service;

  @Post
  public HttpResponse addTodoListItem(TodolistItemCreate todolistItemCreate) throws SQLException {
//      service.addShipment(shipment);
    UUID id = service.addTodoListItem(todolistItemCreate);
    return HttpResponse.ok().body(id);
  }

  @Patch("/{id}")
  public void updateTodoListItem(@NotNull @PathVariable UUID id, TodolistItemCreate todolistItemCreate) {
    service.updateTodoListItem(id,todolistItemCreate);
  }

  @Delete("/{id}")
  public void removeTodoListItem(@NotNull @PathVariable UUID id) {
    service.removeTodoListItem(id);
  }

}

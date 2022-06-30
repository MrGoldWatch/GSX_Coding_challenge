package assessment.todoListItem;

import assessment.openapi.models.TodolistCreate;
import assessment.openapi.models.TodolistItemCreate;
import io.micronaut.http.annotation.PathVariable;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Singleton
public class TodoListItemService {

  // @formatter:off
  @Inject private JdbcTemplate jdbcTemplate;
  // @formatter:on

  public UUID addTodoListItem(TodolistItemCreate todolistItemCreate) {
//    UUID id = UUID.fromString(todolistItemCreate.getLable());
    UUID id = UUID.randomUUID();
    String label = todolistItemCreate.getLable();
    boolean completed = todolistItemCreate.getCompleted();
    UUID parentId = todolistItemCreate.getParentId();
    long date_created = Instant.now().getEpochSecond();
    String sql = "INSERT INTO todolistitem " +
        "(id, label, completed, todolist_id, date_added)" +
        "VALUES (?,?,?,?,?) ON CONFLICT (id) DO NOTHING";
    jdbcTemplate.update(sql, id, label, completed, parentId, date_created);
    return id;
  }

  public void updateTodoListItem( UUID id, TodolistItemCreate todolistItemCreate) {
    long modifiedTs = Instant.now().getEpochSecond();

    Map<String,Object> row = jdbcTemplate.queryForMap("SELECT * FROM todolistitem WHERE id=?", id);

    boolean state = (boolean) row.get("completed");
    if (todolistItemCreate.getCompleted() != null) {
      state = todolistItemCreate.getCompleted();
    }

    String label = row.get("label").toString();
    if (todolistItemCreate.getLable() != null) {
      label = todolistItemCreate.getLable().toString();
    }


    UUID todolist_id = (UUID) row.get("todolist_id");
    if (todolistItemCreate.getParentId() != null) {
      todolist_id = (UUID) todolistItemCreate.getParentId();
    }

    jdbcTemplate.update("UPDATE todolistitem SET label=?, updateTs=?, completed=?, todolist_id=? WHERE id=?",
        label, modifiedTs, state,todolist_id, id);
  }

  public void removeTodoListItem( UUID id) {
    jdbcTemplate.update("DELETE from todolistitem WHERE id=?", id);
  }
}

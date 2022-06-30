package assessment.todoList;

import assessment.openapi.models.*;
import io.micronaut.http.annotation.PathVariable;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

@Singleton
public class TodoListService {

  // @formatter:off
  @Inject private JdbcTemplate jdbcTemplate;
  // @formatter:on

  public void addTodoList(TodolistCreate todolistCreate) {
//    UUID todolist_id = UUID.fromString(todolistCreate.getEmail());
    UUID todolist_id = UUID.randomUUID();
    String name = todolistCreate.getName();
    long date_created = Instant.now().getEpochSecond();
    String owner = todolistCreate.getEmail();
    String sql = "INSERT INTO todolist " +
        "(name, date_created, owner, todolist_id)" +
        "VALUES (?,?,?, ?::uuid) ON CONFLICT (todolist_id) DO NOTHING";
    jdbcTemplate.update(sql, name, date_created, owner, todolist_id);
  }

  public List<Todolist> getAllTodoLists(String email) {
    List<Todolist> l = new ArrayList<>();
    String sql = "SELECT * FROM todolist WHERE owner=?";
    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, email);
    for (Map<String, Object> map : rows) {
      Todolist todoList = new Todolist();
      todoList.setCreationTs((long) map.get("date_created"));
      todoList.setId((UUID) map.get("todolist_id"));
      todoList.setName((String) map.get("name"));
      todoList.setOwner((String) map.get("owner"));
      if (map.get("updateTs") != null) {
        todoList.setUpdateTs((long) map.get("updateTs"));
      }

      // add the items
      List<TodolistItem> todolistItems = new ArrayList<>();
      String itemSql = "SELECT * FROM todolistitem WHERE todolist_id=?";
      List<Map<String, Object>> itemRows = jdbcTemplate.queryForList(itemSql, (UUID) map.get("todolist_id"));
      for (Map<String, Object> itemMap : itemRows) {
        TodolistItem todoListItem = new TodolistItem();
        todoListItem.setCreationTs((long) itemMap.get("date_added"));
        todoListItem.setId((UUID) itemMap.get("id"));
        todoListItem.setLable((String) itemMap.get("label"));
        todoListItem.setCompleted((boolean) itemMap.get("completed"));
        todoListItem.setParentId((UUID) itemMap.get("todolist_id"));

        // add the items
        todolistItems.add(todoListItem);
      }
      todoList.setItems(todolistItems);
      l.add(todoList);
    }
    return l;
  }

  public void updateTodoList(UUID id, TodolistCreate todolistCreate) {
    Map<String,Object> row = jdbcTemplate.queryForMap("SELECT * FROM todolist WHERE todolist_id=?", id);

    String name = row.get("name").toString();
    if (todolistCreate.getName()!= null) {
      name = todolistCreate.getName();
    }

    String email = row.get("owner").toString();
    if (todolistCreate.getEmail() != null) {
      email = todolistCreate.getEmail();
    }

    jdbcTemplate.update("UPDATE todolist SET name=?, owner=? WHERE todolist_id=?",
        name, email, id);
  }

  public void removeTodoList( UUID id) {
    jdbcTemplate.update("DELETE from todolistitem WHERE todolist_id=?", id);

    jdbcTemplate.update("DELETE from todolist WHERE todolist_id=?", id);
  }
}

GeoSensorX
# Software Requirements Specification
## Purpose
The purpose of this document is to build a RESTful API to support a to-do list application.
## Project Scope
The API should expose methods for creating and managing to-do lists, adding/editing/deleting items
from to-do lists, mark to-do list items as completed. The API should have an in-memory persistence
layer that could be easily replaced with another, more permanent provider.
## Product Perspective
The API will expose two controllers as RESTful resources: One will handle ToDoList objects, another will
handle ToDoListItem objects.
End user should be able to create, edit and delete ToDoList objects. Also the end user should be able to
retrieve all ToDoList objects associated with him/her.
End user should also be able to add or remove ToDoListItem objects to individual ToDoList objects, edit
the list items and mark them as completed.
## Notes
This task is designed to evaluate the candidate’s problem-solving and coding skills, as related to Java
Spring. Due to this fact no UI is required, but if provided, will be considered favorably.

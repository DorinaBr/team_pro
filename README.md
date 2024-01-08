# TeamPro - a team project tool

### Description
This is an individual project meant to practice and integrate knowledge about REST APIs, Spring Boot, Hibernate, PostgresSQL and testing.

The application was designed for a better organization of team workflows. It can be used by any team regardless of their field of work.

The application let's you sign up users, create boards, create statuses such as " In progress", "Done", create tasks and assign users to it, in a user friendly manner so that a team can have a better view of their workflow, improving their project efficiency.


### Tech Stack
- Spring Boot
- Java 17
- Maven
- PostgreSQL
- H2 in-memory database
- MockMVC
- Mockito
- Hibernate
  

### How to use

- Access http://localhost:8080/swagger-ui/index.html
- Create a user/ or multiple users
- Create a board
    - you can assign an owner to it by entering the ownerId, which it's the id of a previously create user
- Create a status using a board id    
- Create a task/tasks using a board id
- You can also perform Read, Update and Delete operations on user, board, task and status

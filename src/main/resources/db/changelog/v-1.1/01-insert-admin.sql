INSERT INTO t_user(id, password, username)
VALUES (1, '$2a$10$ixjC71.8jcwduzl4L99pwufULz1HviHqmbOnNlw36Uh2X/cmkoPu2', 'admin');

INSERT INTO t_user_roles(user_id, roles_id)
VALUES (1, 2);
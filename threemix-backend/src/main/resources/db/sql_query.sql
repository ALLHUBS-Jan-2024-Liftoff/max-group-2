
--Generate playlist
    -- users table
    insert into users VALUES ("nagotiya",now());

    --genre_stats table
    insert into threemix.genre_stats VALUES (001, "rock", "classic", "Pop");
    insert into threemix.genre_stats VALUES (002, "work-out", "Classic", "world-music");

    -- user_history table
    insert into user_history VALUES (0001, "nagotiya", "gymt-playlist", 1, now());
     insert into user_history VALUES (0002, "nagotiya", "Workout-playlist", 2, now());


     -- Select Query reference for user_Id
    Select * from users usr
    inner join user_history usrh ON usr.user_id = usrh.user_id
    inner join genre_stats gen ON gen.id = usrh.stats_id
    where usr.user_id = "nagotiya";
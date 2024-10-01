-- Lecture 1 삽입
INSERT INTO Lecture (title, description, lecturer)
VALUES ('낙관락 알고쓰자', '낙관락에 대한 강의', '허재');

-- Lecture 2 삽입
INSERT INTO Lecture (title, description, lecturer)
VALUES ('비관락 알고쓰자', '비관락에 대한 강의', '하헌우');


-- Lecture 1의 강의 시간대 삽입
INSERT INTO LECTURE_DETAIL (lecture_id, start_time, capacity)
VALUES (1, '2024-10-01 15:00:00', 30);

INSERT INTO LECTURE_DETAIL (lecture_id, start_time, capacity)
VALUES (1, '2024-10-02 17:00:00', 30);

INSERT INTO LECTURE_DETAIL (lecture_id, start_time, capacity)
VALUES (1, '2024-10-03 19:00:00', 30);

-- Lecture 2의 강의 시간대 삽입
INSERT INTO LECTURE_DETAIL (lecture_id, start_time, capacity)
VALUES (2, '2024-10-01 15:00:00', 30);

INSERT INTO LECTURE_DETAIL (lecture_id, start_time, capacity)
VALUES (2, '2024-10-02 17:00:00', 30);

INSERT INTO LECTURE_DETAIL (lecture_id, start_time, capacity)
VALUES (2, '2024-10-03 19:00:00', 30);
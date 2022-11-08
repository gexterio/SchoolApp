create table IF NOT EXISTS courses
(
    course_id integer AUTO_INCREMENT NOT NULL,
    course_name character varying(32) NOT NULL,
    course_description character varying(256) NOT NULL,
    PRIMARY KEY (course_id)
);

create table IF NOT EXISTS groups
(
    group_id integer AUTO_INCREMENT NOT NULL,
    group_name character varying(5) NOT NULL,
    PRIMARY KEY (group_id)
);

create table IF NOT EXISTS students
(
    student_id integer AUTO_INCREMENT not null,
    first_name character varying(32) not null,
    last_name character varying(32) not null,
    group_id integer,
     primary key (student_id),
     foreign key (group_id)
        references groups (group_id)
);

create table if not exists personal_courses
(
    student_id integer not null,
    course_id integer not null,
   primary key (student_id, course_id),
    foreign key (course_id)
        references courses (course_id),
   foreign key (student_id)
        references students (student_id)
);



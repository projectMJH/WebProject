-- 댓글
create table reply(
    rno number,
    fno number,
    id varchar2(20),
    name varchar2(51) constraint reply_name_nn not null,
    msg clob constraint reply_msg_nn not null,
    regdate date default sysdate,
    constraint reply_rno_pk primary key(rno),
    constraint replay_fno_fk foreign key(fno)
    references food_menupan(fno),
    constraint reply_id_fk foreign key(id)
    references member(id)
);
create sequence reply_rno_seq
    start with 1
    increment by 1
    nocache
    nocycle;

-- 자료실
create table databoard(
    no number,
    name varchar2(51) constraint db_name_nn not null,
    subject varchar2(2000) constraint db_sub_nn not null,
    content clob constraint db_cont_nn not null,
    pwd varchar2(10) constraint db_pwd_nn not null,
    regdate date default sysdate,
    hit number default 0,
    filename varchar2(260),
    filesize number default 0,
    constraint db_no_pk primary key(no)
)
create sequence db_no_seq
    start with 1
    increment by 1
    nocycle
    nocache;
/*
    화면 분할 = BootStrap
               --------- 반응형
*/    
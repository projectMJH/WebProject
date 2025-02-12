-- 자료실
-- 맛집(지도), 결재 모듈 , 다음 우편번호, 아이디 중복
-- 아이디 찾기 / 비밀번호 -> 메일 전송
-- 장바구니, 예약하기, 추천 (네이버 카페)
create table jspDataBoard(
    no number,
    name varchar2(51) constraint jdb_name_nn not null,
    subject varchar2(2000) constraint jdb_sub_nn not null,
    content clob constraint jdb_cont_nn not null,
    pwd varchar2(10) constraint jdb_pwd_nn not null,
    regdate date default sysdate,
    hit number default 0,
    filename varchar2(260),
    filesize number default 0,
    constraint jdb_no_pk primary key(no)
);

-- 댓글 : session
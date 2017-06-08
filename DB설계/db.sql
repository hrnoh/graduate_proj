-- cretae DB
CREATE SCHEMA `doorlockDB` DEFAULT CHARACTER SET utf8 ;

-- use DB
use doorlockDB;

-- admin
create table admin (
	aNum int(11) unsigned NOT NULL auto_increment,
    name varchar(32) NOT NULL,
    age int(11) unsigned NOT NULL,
    phoneNum varchar(32),
    primary key(aNum)
);

-- department
-- 부서 코드, 부서 이름
create table department (
	dno int(11) unsigned NOT NULL auto_increment,
    deptName varchar(32) NOT NULL,
    key(dno),
    primary key(dno)
);

insert into department(deptName) values ('총무팀');
insert into department(deptName) values ('경리부');
insert into department(deptName) values ('경영지원팀');
insert into department(deptName) values ('경영개선팀');
insert into department(deptName) values ('인사과');
insert into department(deptName) values ('인사팀');
insert into department(deptName) values ('재경부');
insert into department(deptName) values ('고객만족팀');
insert into department(deptName) values ('구매부서');
insert into department(deptName) values ('관리부서');
insert into department(deptName) values ('기술지원팀');
insert into department(deptName) values ('기획팀');
insert into department(deptName) values ('전략기획팀');
insert into department(deptName) values ('연구기획팀');
insert into department(deptName) values ('연구개발팀');
insert into department(deptName) values ('비서실');
insert into department(deptName) values ('생산관리팀');
insert into department(deptName) values ('시설관리팀');
insert into department(deptName) values ('연구실');

-- worker
-- 키, 이름, 나이, 폰, 직위, 레벨, 부서 번호, 지문, 사진
-- status : 사원의 상태 (safe : 안전, warning : 주의, danger : 위험)
create table employee (
	eno int(11) unsigned NOT NULL auto_increment,
	name varchar(32) NOT NULL,
    age int(11) unsigned NOT NULL,
    phoneNum varchar(32),
    position varchar(10) NOT NULL,
    status varchar(10) NOT NULL DEFAULT 'safe',
    dno int(11) unsigned NOT NULL,
    level int(11) unsigned NOT NULL,
    -- fingerPrint blob NOT NULL,
    -- face blob NOT NULL,
    key(eno),	-- 인덱스 생성
    primary key(eno),
    foreign key(dno) references department(dno)
);

insert into employee(name, age, phoneNum, position, dno, level)
values ('노형래', 23, '010-8281-9331', '사장', 15, 3); 
insert into employee(name, age, phoneNum, position, dno, level)
values ('김동겸', 23, '010-0000-0000', '부장', 15, 3); 

-- doorlock
-- 도어락 번호, 상태, 위치, 레벨
create table doorlock (
	dno int(11) unsigned not null auto_increment,
    mac varchar(32) not null,
    location varchar(32) not null,
    level int(11) unsigned not null,
    key(dno),
    primary key(dno)
);

insert into doorlock (mac, location, level) values('b4:74:9f:af:72:ed', '형래집', 1);

-- log
-- 사원 번호, 도어락 번호, 출입 시간
-- result는 success or fail
create table log (
	lno int(11) unsigned not null auto_increment,
    eno int(11) unsigned not null,
    dno int(11) unsigned not null,
    result varchar(32) not null,
    time datetime default now(),
    key(lno),
    primary key(lno),
    foreign key(eno) references employee(eno)
);

insert into log (eno, dno, result) values (1, 1, "success");
insert into log (eno, dno, result) values (2, 1, "success");

-- rule (수정 필요)
create table rule (
	rNum int(11) unsigned not null auto_increment,
    level int(11) unsigned not null,
    normal_mRate double not null, -- 보통 영상인증 인식률(matching rate)
    security_mRate double not null,	-- 전단계 영상인증 실패 시 인식률(matching rate)
    finger_use int(11) unsigned not null,	-- 0 : 사용안함, 1 : 무조건 사용, 2 : 전단계 실패시 사용
    is_use bool not null,	-- 룰 사용중 여부
);

-- 사원 view sql문 (view name : emp_full)
select eno, name, age, phoneNum, position, status, level, deptName
from employee join department on employee.dno = department.dno;

        
-- 로그 view sql문
select * from emp_full
join (
	select log.eno, log.dno, mac, location, result, time from log 
    join doorlock on log.dno = doorlock.dno
) as doorlockLog
on emp_full.eno = doorlockLog.eno;

-- DB name
use graduatedb;

-- admin
create table admin (
	aNum int(11) unsigned NOT NULL auto_increment,
    name varchar(32) NOT NULL,
    age int(11) unsigned NOT NULL,
    phoneNum varchar(32),
    primary key(aNum)
);

-- worker
-- 키, 이름, 나이, 폰, 직위, 레벨, 부서 번호, 지문
create table worker (
	wNum int(11) unsigned NOT NULL auto_increment,
	name varchar(32) NOT NULL,
    age int(11) unsigned NOT NULL,
    phoneNum varchar(32),
    position varchar(10) NOT NULL,
    dNum int(11) unsigned NOT NULL,
    level int(11) unsigned NOT NULL,
    fingerPrint blob NOT NULL,
    key(wNum),	-- 인덱스 생성
    primary key(wNum),
    foreign key(dNum) references department(dNum)
);

-- department
-- 부서 코드, 부서 이름
create table department (
	dNum int(11) unsigned NOT NULL auto_increment,
    dept_name varchar(32) NOT NULL,
    key(dNum),
    primary key(dNum)
);

-- picture
-- 사진 코드, 사원 코드, 사진
create table picture (
	pNum int(11) unsigned not null auto_increment,
    wNum int(11) unsigned not null,
    picture blob not null,
    primary key (pNum),
    foreign key (wNum) references worker(wNum) on delete cascade on update cascade
);

-- doorlock
-- 도어락 번호, 상태, 위치, 레벨
create table doorlock (
	dNum int(11) unsigned not null auto_increment,
    stat varchar(32) not null,
    location varchar(32) not null,
    level int(11) unsigned not null,
    key(dNum),
    primary key(dNum)
);

-- log
-- 사원 번호, 도어락 번호, 출입 시간
create table log (
	wNum int(11) unsigned not null,
    dNum int(11) unsigned not null,
    time datetime not null,
    foreign key(wNum) references worder(wNum),
    foreign key(dNum) references doolock(dNum)
);

-- rule (수정 필요)
create table rule (
	rNum int(11) unsigned not null auto_increment,
    level int(11) unsigned not null,
    normal_mRate double not null, -- 보통 영상인증 인식률(matching rate)
    security_mRate double not null,	-- 전단계 영상인증 실패 시 인식률(matching rate)
    finger_use int(11) unsigned not null,	-- 0 : 사용안함, 1 : 무조건 사용, 2 : 전단계 실패시 사용
    is_use bool not null,	-- 룰 사용중 여부
);


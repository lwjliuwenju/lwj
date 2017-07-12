-- 2017年4月13日 10:07:33
-- 菜单表
CREATE TABLE HOP_MENU (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	
	  MENU_LEVEL_ int(2),
	  NAME_ varchar(255),
	  IMAGE_ varchar(255),
	  FOLDER_ID_ bigint,
	  LINK_URL_ varchar(255),
	  PRIMARY KEY (ID_)
); 
-- 角色表
CREATE TABLE HOP_ROLE (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	
	  NAME_ varchar(255),
	  PRIMARY KEY (ID_)
); 
-- 用户表
CREATE TABLE HOP_USER (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	
	  USER_ID_ bigInt,
	  USER_NAME_ varchar(100),
	  PASSWORD_ varchar(255),
	  EMAIL_ varchar(50),
	  NUMBER_ varchar(20),
	  SEX_ char(1),
	  NIKE_ varchar(100),
	  PRIMARY KEY (ID_)
); 
-- 增加唯一索引（用户名）
ALTER TABLE `HOP_USER` ADD UNIQUE (`JOB_NUMBER_ `);
-- 角色菜单映射表
CREATE TABLE HOP_MENU_OF_ROLE (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	
	  MENU_ID_ bigint,
	  ROLE_ID_ bigint,
	  PRIMARY KEY (ID_)
); 
-- 角色用户映射表
CREATE TABLE HOP_USER_OF_ROLE (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	
	  USER_ID_ bigint,
	  ROLE_ID_ bigint,
	  PRIMARY KEY (ID_)
);

-- 插入初始数据
INSERT INTO HOP_MENU(NAME_,IMAGE_,FOLDER_ID_,MENU_LEVEL_) VALUES('用户管理','default.png',-1,-1); 
INSERT INTO HOP_MENU(NAME_,IMAGE_,FOLDER_ID_,MENU_LEVEL_) VALUES('角色管理','default.png',-1,-1); 
INSERT INTO HOP_MENU(NAME_,IMAGE_,FOLDER_ID_,MENU_LEVEL_) VALUES('参数管理','default.png',-1,-1);
INSERT INTO HOP_ROLE(NAME_) VALUES('超级管理员');
INSERT INTO HOP_MENU_OF_ROLE(MENU_ID_,ROLE_ID_) VALUES(1,1);
INSERT INTO HOP_MENU_OF_ROLE(MENU_ID_,ROLE_ID_) VALUES(2,1);
INSERT INTO HOP_MENU_OF_ROLE(MENU_ID_,ROLE_ID_) VALUES(3,1);

-- 2017年4月18日 14:28:40 lwj
-- 超级管理员独立密码
CREATE TABLE HOP_ADMIN_PASS (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	
	  PASSWORD_ varchar(255),
	  PRIMARY KEY (ID_)
);
INSERT INTO HOP_ADMIN_PASS(PASSWORD_) VALUES('oqGiG3w2C/s4l945xI++My4Wpv2cCyLi');

-- 2017年4月19日 15:56:13 lwj
-- 文件夹实体累
CREATE TABLE HOP_FOLDER_ENTITY (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	
	  FOLDER_NAME_ varchar(255),
	  IMAGE_ varchar(255),
	  PRIMARY KEY (ID_)
);
 INSERT INTO HOP_FOLDER_ENTITY(FOLDER_NAME_,ID_) VALUES('默认菜单',-1);
-- 2017年4月27日16:56:16 lwj
-- 部门表
CREATE TABLE `HOP_DEP` (
  	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
  	  `DEP_NAME_` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  	  `DEP_PHONE_` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  	  `USER_ID_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  	  `DEP_STATE_` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  	  `mark` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  	  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- 2017年4月28日10:44:16 hlh
-- 申请表
DROP TABLE IF EXISTS `hop_proposer`;
CREATE TABLE `hop_proposer` (
  `ID_` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ENABLE_` char(1) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_STAMP_` datetime DEFAULT NULL,
  `MODIFY_STAMP_` datetime DEFAULT NULL,
  
  `PROJECT_ID_` bigint(20) DEFAULT NULL,
  `DEP_NAME_` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `DEP_ID_` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `PROJECT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REPONSE_DEP_ID_` bigint(20) DEFAULT NULL,
  `USER_ID_` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `USER_NAME_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REPAIR_FLAG_` int(2) DEFAULT NULL,
  `REPAIR_REASON_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `APPRAISE_` int(2) DEFAULT NULL,
  `RESPONSE_DEPNAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `STATUS_` int(3) DEFAULT NULL,
  `REMARK_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MARK_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROPOSER_CONTENT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_DATE_` bigint(20) DEFAULT NULL,
  `APPRAISE_MARK_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CHECK_CONTENT` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CHECKFALG_` int(11) DEFAULT NULL,
  `PROPOSER_ID_` bigint(20) DEFAULT NULL,
  `OUTSOURCING_FLAG_` int(5) DEFAULT NULL COMMENT '修外标志',
  `RESPONSE_TIMES_` int(5) DEFAULT NULL COMMENT '响应次数',
  `RESPONSE_START_TIME_` datetime DEFAULT NULL,
  `RESPONSE_END_TIME_` datetime DEFAULT NULL,
  `TERMINATION_REASON_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OUT_REPAIR_REASON_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TERMINATION_REASON_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TERMINATION_REASON_USER_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
	  
-- 2017年4月28日15:42:16 
-- 服务表
CREATE TABLE HOP_PROJECT (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	  
	  PROJECT_NAME_ VARCHAR(30),
	  RESPONSE_DEPT_NAME_ VARCHAR(20),
	  RESPONSE_DEPT_ID_ VARCHAR(5),
	  GRADE_ INT(2),
	  STANDARD_HOUR_ Int(3),
	  PRIMARY KEY (ID_)
);
-- 2017年5月4日 15:47:18 lwj
-- 文件夹增加 文件夹级别
alter HOP_MENU t1 add column MENU_LEVEL_ int(2) not null;

-- 2017年5月18日 09:13:32 lwj
-- 增加耗材表
CREATE TABLE HOP_SUPPLIES (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	  
	  NAME_ VARCHAR(50),
	  VALUE_ VARCHAR(20),
	  DEP_ID_ bigint,
	  PRIMARY KEY (ID_)
);
-- 2017年5月19日10:41:51
-- 耗材单 HLH
CREATE TABLE HOP_SUPPLIES_PROPOSER (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	  
	  PROPOSER_ID_ bigint,
	  SUPPLIES_ID_ bigint,
	  NAME_ VARCHAR(50),
	  STATUS_ INT(3),
	  SUPPLIES_NUM_ INT(3),
	  PRIMARY KEY (ID_)
);

-- 2017年5月23日 10:18:49
-- lwj 参数
CREATE TABLE CON_PARAMETER (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	  
	  PAR_NAME_ varchar(200),
	  PAR_MARK_ varchar(200),
	  PAR_VAL_ varchar(200),
	  PRIMARY KEY (ID_)
);
ALTER TABLE `CON_PARAMETER` ADD UNIQUE (`PAR_MARK_`);

-- 2017年6月12日08:59:44 lwj
-- 人员响应单中间表
CREATE TABLE HOP_STAFF_OF_PROPOSER (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	  
	  STAFF_ID_ bigint,
	  PROPOSER_ID_ bigint,
	  COMPLETE_ int(1),
	  PRIMARY KEY (ID_)
);

-- 2017年6月14日8:43:02 HLH
-- 物流跟踪 物品表
CREATE TABLE HOP_GOODS (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	  
	  PROPOSER_ID_ bigint,
	  STAFF_ID_ bigint,
	  DEP_ID_ bigint,
	  BEDNO_ VARCHAR(255),
	  HOSPITALIZATION_NUMBER_ VARCHAR(255),
	  BLOOD_COUNT_ VARCHAR(255),
	  BLOOD_DONOR_ VARCHAR(20),
	  DRAWBLOOD_TIME_ DATETIME,
	  TRANSPORT_ BOOLEAN,
	  PRIMARY KEY (ID_)
);

CREATE TABLE HOP_USER_OF_DEP_ADMIN (
	  ID_ bigint NOT NULL AUTO_INCREMENT,
	  ENABLE_ CHAR(1),
	  CREATE_STAMP_ DATETIME,
	  MODIFY_STAMP_ DATETIME,
	  
	  USER_ID_ bigint,
	  DEP_ID_ bigint,
	  PRIMARY KEY (ID_)
);
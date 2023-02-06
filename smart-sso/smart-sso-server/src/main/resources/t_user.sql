--用户表结构，在部署时需要初始化用户表（pg数据库，mysql数据库按照字段建表）
-- Drop table

-- DROP TABLE public.t_user;

CREATE TABLE public.t_user (
	id varchar(32) NOT NULL,
	user_name varchar(255) NOT NULL,
	login_name varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_login_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	deleted int4 NOT NULL DEFAULT 0,
	pwd_val_time timestamp NOT NULL,
	belong_code varchar(8) NULL,
	belong_name varchar(255) NULL,
	data_type varchar(255) NULL,
	phone varchar(255) NULL,
	CONSTRAINT t_user_pkey PRIMARY KEY (id)
);
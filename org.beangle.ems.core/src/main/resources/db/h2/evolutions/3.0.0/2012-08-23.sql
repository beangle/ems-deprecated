--sequence 
alter table se_data_fields rename to se_profile_fields;

create table se_data_types (id bigint primary key,name varchar2(50),key_name varchar2(20),properties varchar2(100),type_name varchar2(100));

insert into se_data_types(id,name,key_name,properties,type_name)
select id,name,key_name,property_names,value_type from se_profile_fields;

create sequence seq_se_data_types;

alter table se_profile_fields add column type_id bigint;
update se_profile_fields a set a.type_id=a.id;
alter table se_profile_fields drop column value_type;
alter table se_profile_fields drop column key_name;
alter table se_profile_fields drop column property_names;

alter table se_profile_fields alter column remark rename to title;

create table se_data_fields (id bigint primary key,name varchar2(50),title varchar2(50),type_id bigint,resource_id bigint);
create sequence seq_se_data_fields;

alter table  se_user_properties alter column meta_id rename to field_id;
alter table  se_role_properties alter column meta_id rename to field_id;


alter table se_session_stats drop column server_name;

update se_resources set name='/security/menu' where name='/security/nav/menu';
update se_menus set entry='/security/menu' where entry='/security/nav/menu';

update se_resources set name='/security/menu-profile' where name='/security/nav/profile';
update se_menus set entry='/security/menu-profile' where entry='/security/nav/profile';

update se_resources set name='/security/menu-nav' where name='/security/nav/index';
update se_menus set entry='/security/menu-nav' where entry='/security/nav/index';

update se_resources set name='/security/monitor' where name='/security/session/monitor';
update se_menus set entry='/security/monitor' where entry='/security/session/monitor';

update se_resources set name='/security/sessioninfo-log' where name='/security/session/log';
update se_menus set entry='/security/sessioninfo-log' where entry='/security/session/log';

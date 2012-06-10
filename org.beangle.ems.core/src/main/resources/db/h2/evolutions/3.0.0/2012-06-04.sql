alter table se_permissions add column actions varchar2(100);
alter table se_permissions add column effective_at date;
alter table se_permissions add column invalid_at date;
select * from property;
select * from address;
select * from greater_area;

describe property;
describe area;
describe suburb;


select u.username, r.name as role, p.name as permission
from users u, role r, permission p, user_role ur, permission_role pr
where u.id = ur.user_id
and r.id = ur.role_id
and p.id = pr.permission_id
and r.id = pr.role_id
and u.id = 2;


select * from users;
select * from groups;
select * from role;
select * from permission;
select * from permission_role;
select * from user_role;
select * from audit_activity;
select * from milestone;
select * from property;
select * from property_flip;
select * from address;
select * from area;
select * from suburb;
select * from greater_area;

show tables;

use jericho;
show tables;
describe user;
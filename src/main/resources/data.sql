INSERT INTO employees (first_name, last_name, address, city, salary, employee_type) VALUES
  ('Ante', 'Radic', 'Oak Street 7', 'Chicago', 25000, 'Manager'),
  ('Bruno', 'Mars', 'Windy Road 25', 'NYC', 12000, 'Engineer'),
  ('Gill', 'Harrison', 'Windy Road 33', 'NYC', 35000, 'Director'),
  ('Lana', 'Johnson', 'Miller Road 17', 'Denver', 9000, 'Secretary');

INSERT INTO departments (name) VALUES
    ('Mobile'),
    ('Web development'),
    ('Artificial intelligence');

INSERT INTO projects (short_name, name, description) VALUES
    ('ProjectName', 'ProjectNameLong', 'Some project description'),
    ('OtherName', 'OtherNameLong', 'Some other project description'),
    ('ThirdName', 'ThirdNameLong', 'Some third project description');

INSERT INTO employee_departments (employee_id, department_id) VALUES
    (1,3),
    (2,1),
    (1,1);

INSERT INTO employee_projects (employee_id, project_id) VALUES
    (1,1),
    (2,1),
    (1,2),
    (2,3);

INSERT INTO cdr_files(name, place, region, type)
VALUES ('Poland 800', 'Archive', 'Poland', 'road map'),
       ('Warsaw 30', 'Archive', 'Warsaw', 'city map'),
       ('Cracow 20', 'Archive', 'Cracow', 'city map'),
       ('Warsaw Center 15', 'Archive', 'Warsaw', 'city map'),
       ('Cracow Center 10', 'Archive', 'Cracow', 'city map'),
       ('Italy 750', 'Archive', 'Italy', 'road map'),
       ('Rome 40', 'Archive', 'Rome', 'road map');

-- raks_code has to be 12 characters long to ensure application work properly

INSERT INTO raks_codes(raks_code, user_name, job_type)
VALUES  ('Italy._FOLD8', null, null),
        ('Poland_FOLD6', null, null),
        ('Warsaw_FOLD6', null, null),
        ('Warsaw_POCKT', null, null),
        ('Cracow_FOLD6', null, null),
        ('Cracow_POCKT', null, null);

INSERT INTO raks_codes_cdr_files(raks_code_id, cdr_file_id)
VALUES (1, 6),
       (1, 7),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 2),
       (3, 4),
       (4, 4),
       (5, 3),
       (5, 5),
       (6, 5);
INSERT INTO cdr_files(name, place, region, type)
VALUES ('Poland 800', 'Adrian', 'Poland', 'road map'),
       ('Warsaw 30', 'Adrian', 'Warsaw', 'city map'),
       ('Cracow 20', 'Adrian', 'Cracow', 'city map'),
       ('Warsaw Center 15', 'Adrian', 'Warsaw', 'city map'),
       ('Cracow Center 10', 'Adrian', 'Cracow', 'city map'),
       ('Italy 750', 'Adrian', 'Italy', 'road map'),
       ('Rome 40', 'Adrian', 'Rome', 'road map');

-- raks_code has to be 12 characters long to ensure application work properly

INSERT INTO raks_codes(raks_code, user_name, job_type)
VALUES  ('Poland_FOLD6', null, null),
        ('Italy._FOLD8', null, null),
        ('Warsaw_FOLD6', null, null),
        ('Warsaw_POCKT', null, null),
        ('Cracow_FOLD6', null, null),
        ('Cracow_POCKT', null, null);

INSERT INTO raks_codes_cdr_files(raks_code_id, cdr_file_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 6),
       (2, 7),
       (3, 2),
       (3, 4),
       (4, 4),
       (5, 3),
       (5, 5),
       (6, 5);
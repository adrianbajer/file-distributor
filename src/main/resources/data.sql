INSERT INTO cdr_files(name, place, region, type)
VALUES ('Poland 800', 'Archive', 'Poland', 'road map'),
       ('Warsaw 30', 'Archive', 'Warsaw', 'city map'),
       ('Cracow 20', 'Archive', 'Cracow', 'city map'),
       ('Warsaw Center 15', 'Archive', 'Warsaw', 'city map'),
       ('Cracow Center 10', 'Archive', 'Cracow', 'city map'),
       ('Italy 750', 'Archive', 'Italy', 'road map');

INSERT INTO raks_codes(raks_code, user_name, job_type)
VALUES ('Poland_FOLD6', null, null);

INSERT INTO raks_codes_cdr_files(raks_code_id, cdr_file_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5);
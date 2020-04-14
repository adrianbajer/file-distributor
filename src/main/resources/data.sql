INSERT INTO cdr_files(name, place, region, type)
VALUES ('Poland 800', 'Adrian', 'Poland', 'road map'),
       ('Warsaw 30', 'Adrian', 'Warsaw', 'city map'),
       ('Cracow 20', 'Adrian', 'Cracow', 'city map'),
       ('Warsaw Center 15', 'Adrian', 'Warsaw', 'city map'),
       ('Cracow Center 10', 'Adrian', 'Cracow', 'city map'),
       ('Italy 750', 'Adrian', 'Italy', 'road map'),
       ('Rome 40', 'Adrian', 'Rome', 'road map');

INSERT INTO polygons(left_upper_lat, left_upper_lon, right_lower_lat, right_lower_lon)
VALUES (54.9, 14.0, 48.9, 24.25),
       (47.25, 6.2, 36.4, 19.05),
       (52.37, 20.83, 52.10, 21.25),
       (52.29, 20.9, 52.15, 21.13),
       (50.13, 19.75, 49.97, 20.22),
       (50.09, 19.87, 50.02, 20.04);


-- raks_code has to be 12 characters long to ensure application work properly

INSERT INTO raks_codes(raks_code, user_name, job_type, polygon_id)
VALUES  ('Poland_FOLD6', null, null, 1),
        ('Italy._FOLD8', null, null, 2),
        ('Warsaw_FOLD6', null, null, 3),
        ('Warsaw_POCKT', null, null, 4),
        ('Cracow_FOLD6', null, null, 5),
        ('Cracow_POCKT', null, null, 6);

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

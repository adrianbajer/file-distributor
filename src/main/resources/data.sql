INSERT INTO cdr_files(name, place, region, type)
VALUES ('Poland 800', 'Adrian', 'Poland', 'road map'),
       ('Warsaw 30', 'Adrian', 'Warsaw', 'city map'),
       ('Cracow 20', 'Adrian', 'Cracow', 'city map'),
       ('Warsaw Center 15', 'Adrian', 'Warsaw', 'city map'),
       ('Cracow Center 10', 'Adrian', 'Cracow', 'city map'),
       ('Italy 750', 'Adrian', 'Italy', 'road map'),
       ('Rome 40', 'Adrian', 'Rome', 'road map');

-- raks_code has to be 12 characters long to ensure application work properly

--

-- INSERT INTO raks_codes(raks_code, user_name, job_type)
-- VALUES  ('Poland_FOLD6', null, null),
--         ('Italy._FOLD8', null, null),
--         ('Warsaw_FOLD6', null, null),
--         ('Warsaw_POCKT', null, null),
--         ('Cracow_FOLD6', null, null),
--         ('Cracow_POCKT', null, null);

INSERT INTO polygons(left_upper_lat, left_upper_lon, right_lower_lat, right_lower_lon)
VALUES (54.559322, -5.767822, 58.1210604, -3.021240),
       (56.559322, -5.767822, 60.1210604, -3.021240),
       (58.559322, -5.767822, 62.1210604, -3.021240),
       (60.559322, -5.767822, 64.1210604, -3.021240),
       (62.559322, -5.767822, 66.1210604, -3.021240),
       (64.559322, -5.767822, 68.1210604, -3.021240);

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



-- INSERT INTO polygons(left_upper_lat, left_upper_lon, right_lower_lat, right_lower_lon, raks_code_id)
-- VALUES (54.559322, -5.767822, 58.1210604, -3.021240, 1),
--        (56.559322, -5.767822, 60.1210604, -3.021240, 2),
--        (58.559322, -5.767822, 62.1210604, -3.021240, 3),
--        (60.559322, -5.767822, 64.1210604, -3.021240, 4),
--        (62.559322, -5.767822, 66.1210604, -3.021240, 5),
--        (64.559322, -5.767822, 68.1210604, -3.021240, 6);
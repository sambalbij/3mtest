INSERT INTO events (`name`, `description`, `finished`)
VALUES (
  'Training Software Engineering',
  'Introductie software engineering voor Young Colfield',
  0
);

INSERT INTO events (`name`, `description`, `finished`)
VALUES (
  'Etentje',
  'Eten om elkaar beter te leren kennen',
  0
);

INSERT INTO participants (`name`, `event_id`)
VALUES
  ('Mark', 1),
  ('Maarten', 1),
  ('Martijn', 1),
  ('Jettro', 1);

INSERT INTO activities (`name`, `description`, `cost`, `event_id`)
VALUES
  ('koffie drinken', 'even buiten de deur met zijn allen een bakkie doen', 15, 1),
  ('Huren van de zaal', 'Kosten voor de cursus ruimte', 125, 1),
  ('Lunchen buiten de deur', 'Afsluiting van de cursus', 65, 1);

INSERT INTO activity_participant (`activity_id`, `participant_id`)
VALUES
  (1, 1),
  (1, 2),
  (1, 4),
  (2, 1),
  (2, 2),
  (2, 3),
  (2, 4),
  (3, 1),
  (3, 2),
  (3, 3),
  (3, 4);

INSERT INTO items (`name`, `description`, `cost`, `activity_id`)
VALUES
  ('Koffie Jettro', 'lekker bakkie pleur', 2, 1),
  ('Koffie plus koek Mark', 'Kofie alleen is niet genoeg', 5, 1),
  ('Capucino Maarten', 'Met een beetje melk', 3, 1),
  ('Zaalhuur is inclusief', 'Jettro betaalt de zaal', 125, 2),
  ('Wij tracteren', 'jettro wordt getrakterd op de lunch', 65, 3);

INSERT INTO participant_item (`item_id`, `participant_id`)
VALUES
  (1, 4),
  (2, 1),
  (3, 2),
  (4, 4),
  (5, 1),
  (5, 2),
  (5, 3);


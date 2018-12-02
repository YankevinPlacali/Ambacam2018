INSERT INTO PUBLIC.ACTION(ID, DESCRIPTION, NOM) VALUES
(1, 'Deletion action', 'DELETE'),
(2, 'Update action', 'UPDATE');
INSERT INTO PUBLIC.AUTORITE(ID, NOM, POSTE, PRENOM) VALUES
(1, 'Sop Sandjong', 'Head of community', 'Jean'),
(2, 'Sokoudjou', 'CEO', 'Simon');
INSERT INTO PUBLIC.PAYS(ID, DESCRIPTION, NOM) VALUES
(1, 'Cameroon', 'Cameroon'),
(2, '', 'Ivory Coast'),
(3, NULL, 'Togo'),
(4, NULL, 'Benin'),
(5, NULL, 'Burkina Faso'),
(6, NULL, 'Sudan');
INSERT INTO PUBLIC.MOTIF_SUPPRESSION(ID, DESCRIPTION, NOM) VALUES
(1, 'There is an error in the data', 'ERREUR_DE_DONNEE');
INSERT INTO PUBLIC.STATUS_REQUETE(ID, DESCRIPTION, NOM) VALUES
(2, 'Generally default status at the creation', 'DRAFT'),
(3, 'The documents have been sent to Cameroon', 'SENT_TO_CAMEROON'),
(4, 'The documents have been received in Cameroon', 'RECEIVED_IN_CAMEROON'),
(5, 'The document is almost ready and waiting for the signature', 'WAITING_FOR_SIGNATURE'),
(6, 'The document is ready and has been sent to the country where the application was created', 'SENT_TO_THE_CORRESPONDING_COUNTRY'),
(7, 'The document has been received in the country of application but is not yet ready for collection', 'RECEIVED_IN_THE_APPLICATION_COUNTRY'),
(8, 'The document can now be collected', 'READY_FOR_COLLECTION'),
(9, NULL, 'draft');
INSERT INTO PUBLIC.TYPE_REQUETE(ID, DESCRIPTION, NOM) VALUES
(1, 'Application for a visa', 'VISA_APPLICATION'),
(2, 'Application for a passport', 'PASSPORT_APPLICATION');
INSERT INTO PUBLIC.OPERATEUR(ID, CREE_LE, NOM, PASSWORD, PRENOM, SEXE, USERNAME, CREE_PAR_ID, NATIONALITE_ID) VALUES
(1, TIMESTAMP '2018-12-02 10:35:20.101', 'Admin', 'admin', 'Admin', 'f', 'admin', NULL, 1),
(2, TIMESTAMP '2018-12-02 11:00:54.49', 'Onana Atangana', 'germain', 'Germain', 'm', 'germain', 1, 1),
(3, TIMESTAMP '2018-12-02 11:01:38.853', 'Kamdem Dongmo', 'steve', 'Steve Brice', 'm', 'steve', 1, 1),
(4, TIMESTAMP '2018-12-02 11:02:14.708', 'Ngo Eyenga', 'marie', 'Marie-Jeanne', 'f', 'marie', 1, 1),
(5, TIMESTAMP '2018-12-02 11:05:30.515', 'Endale Din', 'henry', 'Henry', 'm', 'henry', 1, 1);
INSERT INTO PUBLIC.REQUERANT(ID, CREE_LE, DATE_NAISSANCE, LIEU_NAISSANCE, NOM, PRENOM, PROFESSION, SEXE, CREE_PAR_ID, NATIONALITE_ID) VALUES
(1, TIMESTAMP '2018-12-02 11:06:31.071', TIMESTAMP '2018-12-21 01:00:00', 'Bafoussam', 'Kamga', 'Raymon', 'Etudiant', 'M', 1, 1),
(2, TIMESTAMP '2018-12-02 11:07:24.214', TIMESTAMP '2018-02-05 01:00:00', 'Douala', 'Essome Ahanda', 'Ludwin', 'Inspecteur de police', 'F', 1, 1),
(3, TIMESTAMP '2018-12-02 11:10:00.494', TIMESTAMP '2018-12-12 01:00:00', 'Yokadouma', 'Nga Onana', 'Stella', 'Etudiante', 'F', 1, 1);
INSERT INTO PUBLIC.REQUETE(ID, CREE_LE, OPERATEUR_ID, REQUERANT_ID, REQUETE_GROUPE_ID, STATUS_REQUETE_ID, TYPE_REQUETE_ID) VALUES
(1, TIMESTAMP '2018-12-02 11:25:20.692', 1, 1, NULL, 9, 2);
INSERT INTO PUBLIC.PASSPORT(ID, DATE_DELIVRANCE, DATE_EXPIRATION, LIEU_DELIVRANCE, NUMERO, URL_PHOTO, AUTORITE_ID, REQUETE_ID) VALUES
(1, TIMESTAMP '2018-12-11 01:00:00', TIMESTAMP '2018-12-28 01:00:00', 'Yaounde', '012489', NULL, 1, 1);

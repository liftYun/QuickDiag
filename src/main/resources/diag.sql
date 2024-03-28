CREATE TABLE BodyParts (
                           BodyPartID INT PRIMARY KEY,
                           BodyPartName VARCHAR(100)
);

CREATE TABLE Symptoms (
                          SymptomID INT PRIMARY KEY,
                          SymptomName VARCHAR(100),
                          BodyPartID INT,
                          FOREIGN KEY (BodyPartID) REFERENCES BodyParts(BodyPartID)
);
show tables;

INSERT INTO BodyParts (BodyPartID, BodyPartName) VALUES (1001, 'head');
INSERT INTO BodyParts (BodyPartID, BodyPartName) VALUES (1002, 'neck');
INSERT INTO BodyParts (BodyPartID, BodyPartName) VALUES (1003, 'shoulder');
INSERT INTO BodyParts (BodyPartID, BodyPartName) VALUES (1004, 'chest');
INSERT INTO BodyParts (BodyPartID, BodyPartName) VALUES (1005, 'stomach');
INSERT INTO BodyParts (BodyPartID, BodyPartName) VALUES (1006, 'ass');
INSERT INTO BodyParts (BodyPartID, BodyPartName) VALUES (1007, 'waist');
INSERT INTO BodyParts (BodyPartID, BodyPartName) VALUES (1008, 'arm');
INSERT INTO BodyParts (BodyPartID, BodyPartName) VALUES (1009, 'leg');

INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (1, 'pail', 1001);
INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (2, 'fever', 1001);
INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (3, 'anemia', 1001);

/*INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (1, 'pail', 1002);
INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (1, 'pail', 1003);
INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (1, 'pail', 1004);
INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (1, 'pail', 1005);
INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (1, 'pail', 1006);
INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (1, 'pail', 1007);
INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (1, 'pail', 1008);
INSERT INTO Symptoms (SymptomID, SymptomName, BodyPartID)
VALUES (1, 'pail', 1009);*/

select * from user;
select * from Symptoms;
select * from BodyParts;
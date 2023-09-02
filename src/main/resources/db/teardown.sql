CREATE TABLE IF NOT EXISTS "PAGE" (
  "id" VARCHAR(36) NOT NULL,
  "title" VARCHAR(255) NOT NULL,
  "content" VARCHAR(4000),
  "parentId" VARCHAR(36),
  PRIMARY KEY ("id"),
  FOREIGN KEY ("parentId") REFERENCES "PAGE"("id")
);

SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE "PAGE";
SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('45e4609d-726d-4703-9242-18e86f6b9a0b', '페이지 1', '페이지 1의 내용', NULL);

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('f1e47c1f-9504-4b69-af65-44e529fca1a1', '페이지 2', '페이지 2의 내용', NULL);

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('a98e6a21-6f80-4b71-aa3b-e5c96f6a2537', '페이지 3', '페이지 3의 내용', NULL);

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('3ea95b02-6d47-4c02-ae38-1592aee1f4d3', '페이지 4', '페이지 4의 내용', NULL);

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('793c61dd-b2cd-4c22-9f60-87ab40db7a6b', '페이지 5', '페이지 5의 내용', NULL);

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('79f2275d-68c7-4b24-9d5f-d741a344a4a5', '페이지 1의 서브페이지 1', '서브페이지 1의 내용', '45e4609d-726d-4703-9242-18e86f6b9a0b'),
('3c9e8ff9-d94a-4e0e-b3b5-5d6e7cbe19a1', '페이지 1의 서브페이지 2', '서브페이지 2의 내용', '45e4609d-726d-4703-9242-18e86f6b9a0b');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('d074e8e7-b15d-4f8e-a1ec-68c9258925f6', '페이지 2의 서브페이지 1', '서브페이지 1의 내용', 'f1e47c1f-9504-4b69-af65-44e529fca1a1'),
('1b3b692c-6ed5-4db9-bf7a-0470706b0423', '페이지 2의 서브페이지 2', '서브페이지 2의 내용', 'f1e47c1f-9504-4b69-af65-44e529fca1a1');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('4e7468eb-1449-4f07-b982-ae1f33abfdf4', '페이지 3의 서브페이지 1', '서브페이지 1의 내용', 'a98e6a21-6f80-4b71-aa3b-e5c96f6a2537'),
('f26106d4-07d1-43bf-9d91-26be1f8c9e86', '페이지 3의 서브페이지 2', '서브페이지 2의 내용', 'a98e6a21-6f80-4b71-aa3b-e5c96f6a2537');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('f083ee10-7f0e-4ce0-8c22-ef0405ed3b8c', '페이지 4의 서브페이지 1', '서브페이지 1의 내용', '3ea95b02-6d47-4c02-ae38-1592aee1f4d3'),
('4da54e31-85a2-4a8a-9a12-678d8c78f8f5', '페이지 4의 서브페이지 2', '서브페이지 2의 내용', '3ea95b02-6d47-4c02-ae38-1592aee1f4d3');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('aad716f7-d4cf-47e7-baa7-5fc2f40d8d3b', '페이지 5의 서브페이지 1', '서브페이지 1의 내용', '793c61dd-b2cd-4c22-9f60-87ab40db7a6b'),
('4e5c6b6e-8db2-47eb-88de-66e97babb1f4', '페이지 5의 서브페이지 2', '서브페이지 2의 내용', '793c61dd-b2cd-4c22-9f60-87ab40db7a6b');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('a1e3db98-e184-48ea-8ca2-e30c0a90f33c', '페이지 1의 서브페이지 1의 서브페이지 1', '페이지 1의 서브페이지 1의 서브페이지 1의 내용', '79f2275d-68c7-4b24-9d5f-d741a344a4a5'),
('d03a8166-79e4-4392-ba9c-7c2e1ca6f731', '페이지 1의 서브페이지 1의 서브페이지 2', '페이지 1의 서브페이지 1의 서브페이지 2의 내용', '79f2275d-68c7-4b24-9d5f-d741a344a4a5'),
('5923b998-106f-4c57-9241-34c6283b282c', '페이지 1의 서브페이지 2의 서브페이지 1', '페이지 1의 서브페이지 2의 서브페이지 1의 내용', '3c9e8ff9-d94a-4e0e-b3b5-5d6e7cbe19a1'),
('a19e2f13-e667-4b91-a450-243e17ebedc0', '페이지 1의 서브페이지 2의 서브페이지 2', '페이지 1의 서브페이지 2의 서브페이지 2의 내용', '3c9e8ff9-d94a-4e0e-b3b5-5d6e7cbe19a1');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('a83b1a98-4e3d-4cd3-94b0-c77fde30db2c', '페이지 2의 서브페이지 1의 서브페이지 1', '페이지 2의 서브페이지 1의 서브페이지 1의 내용', 'd074e8e7-b15d-4f8e-a1ec-68c9258925f6'),
('c4e8e80b-7ea9-4d0f-b6db-4e431d1e6470', '페이지 2의 서브페이지 1의 서브페이지 2', '페이지 2의 서브페이지 1의 서브페이지 2의 내용', 'd074e8e7-b15d-4f8e-a1ec-68c9258925f6'),
('51d24a06-d9c9-4a9d-930e-1df98657a13b', '페이지 2의 서브페이지 2의 서브페이지 1', '페이지 2의 서브페이지 2의 서브페이지 1의 내용', '1b3b692c-6ed5-4db9-bf7a-0470706b0423'),
('d9d96317-5564-4424-bd88-abb0ad4cc477', '페이지 2의 서브페이지 2의 서브페이지 2', '페이지 2의 서브페이지 2의 서브페이지 2의 내용', '1b3b692c-6ed5-4db9-bf7a-0470706b0423');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('eff293d2-d200-4807-9efb-ff9909408ce4', '페이지 3의 서브페이지 1의 서브페이지 1', '페이지 3의 서브페이지 1의 서브페이지 1의 내용', '4e7468eb-1449-4f07-b982-ae1f33abfdf4'),
('2c8015a3-76f0-4d2d-afed-4b7fb8ea8527', '페이지 3의 서브페이지 1의 서브페이지 2', '페이지 3의 서브페이지 1의 서브페이지 2의 내용', '4e7468eb-1449-4f07-b982-ae1f33abfdf4'),
('adf77eef-c91c-42b6-8f85-4c37da7f598a', '페이지 3의 서브페이지 2의 서브페이지 1', '페이지 3의 서브페이지 2의 서브페이지 1의 내용', 'f26106d4-07d1-43bf-9d91-26be1f8c9e86'),
('c2555f5b-3a2b-44a4-a6ef-6d2f1e1b235e', '페이지 3의 서브페이지 2의 서브페이지 2', '페이지 3의 서브페이지 2의 서브페이지 2의 내용', 'f26106d4-07d1-43bf-9d91-26be1f8c9e86');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('65f8d4b0-9c6c-46f5-a4ce-172874905c05', '페이지 4의 서브페이지 1의 서브페이지 1', '페이지 4의 서브페이지 1의 서브페이지 1의 내용', 'f083ee10-7f0e-4ce0-8c22-ef0405ed3b8c'),
('4f30ab24-3f8c-4e41-9bc9-44a7ae93aa03', '페이지 4의 서브페이지 1의 서브페이지 2', '페이지 4의 서브페이지 1의 서브페이지 2의 내용', 'f083ee10-7f0e-4ce0-8c22-ef0405ed3b8c'),
('d8277687-c651-4be2-8f59-75b62e9cb04f', '페이지 4의 서브페이지 2의 서브페이지 1', '페이지 4의 서브페이지 2의 서브페이지 1의 내용', '4da54e31-85a2-4a8a-9a12-678d8c78f8f5'),
('671a70ed-dc53-44c3-936c-26cd5d1953a1', '페이지 4의 서브페이지 2의 서브페이지 2', '페이지 4의 서브페이지 2의 서브페이지 2의 내용', '4da54e31-85a2-4a8a-9a12-678d8c78f8f5');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('ebc99513-817c-4f36-a8a0-4ec2ed6ee374', '페이지 5의 서브페이지 1의 서브페이지 1', '페이지 5의 서브페이지 1의 서브페이지 1의 내용', 'aad716f7-d4cf-47e7-baa7-5fc2f40d8d3b'),
('a0a25e08-4d8e-4b22-9ff0-ffb2f4f081f2', '페이지 5의 서브페이지 1의 서브페이지 2', '페이지 5의 서브페이지 1의 서브페이지 2의 내용', 'aad716f7-d4cf-47e7-baa7-5fc2f40d8d3b'),
('e8eef24f-34fb-4d7e-b5ac-3e7b32e50f14', '페이지 5의 서브페이지 2의 서브페이지 1', '페이지 5의 서브페이지 2의 서브페이지 1의 내용', '4e5c6b6e-8db2-47eb-88de-66e97babb1f4'),
('2d6b1a2d-b828-4ebc-b287-03f09c771d79', '페이지 5의 서브페이지 2의 서브페이지 2', '페이지 5의 서브페이지 2의 서브페이지 2의 내용', '4e5c6b6e-8db2-47eb-88de-66e97babb1f4');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('a96c3191-9b02-463e-b245-60c2010499f3', '페이지 1의 서브페이지 1의 서브페이지 1의 서브페이지 1', '페이지 1의 서브페이지 1의 서브페이지 1의 서브페이지 1의 내용', 'a1e3db98-e184-48ea-8ca2-e30c0a90f33c');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('d6f4226a-17ed-4f52-85bb-5c163e8d6a07', '페이지 1의 서브페이지 1의 서브페이지 1의 서브페이지 2', '페이지 1의 서브페이지 1의 서브페이지 1의 서브페이지 2의 내용', 'a1e3db98-e184-48ea-8ca2-e30c0a90f33c');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('c9aaeb64-61c4-4a4a-9074-2c19d8e27b17', '페이지 2의 서브페이지 1의 서브페이지 1의 서브페이지 1', '페이지 2의 서브페이지 1의 서브페이지 1의 서브페이지 1의 내용', 'a83b1a98-4e3d-4cd3-94b0-c77fde30db2c');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('3a59e62d-684e-4d6a-b2c7-3c8a94e49a9d', '페이지 2의 서브페이지 1의 서브페이지 1의 서브페이지 2', '페이지 2의 서브페이지 1의 서브페이지 1의 서브페이지 2의 내용', 'a83b1a98-4e3d-4cd3-94b0-c77fde30db2c');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('8f60ac0e-87a7-4a9b-b02e-0ca7e7d10076', '페이지 3의 서브페이지 1의 서브페이지 1의 서브페이지 1', '페이지 3의 서브페이지 1의 서브페이지 1의 서브페이지 1의 내용', 'eff293d2-d200-4807-9efb-ff9909408ce4');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('d5622d38-2061-4ab0-943e-2dbb57b14ea9', '페이지 3의 서브페이지 1의 서브페이지 1의 서브페이지 2', '페이지 3의 서브페이지 1의 서브페이지 1의 서브페이지 2의 내용', 'eff293d2-d200-4807-9efb-ff9909408ce4');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('fb2e65b9-4304-43e1-95ab-5a23a6f8e8f6', '페이지 4의 서브페이지 1의 서브페이지 1의 서브페이지 1', '페이지 4의 서브페이지 1의 서브페이지 1의 서브페이지 1의 내용', '65f8d4b0-9c6c-46f5-a4ce-172874905c05');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('6b1f7c92-19c0-42ac-b740-28709b27f3f7', '페이지 4의 서브페이지 1의 서브페이지 1의 서브페이지 2', '페이지 4의 서브페이지 1의 서브페이지 1의 서브페이지 2의 내용', '65f8d4b0-9c6c-46f5-a4ce-172874905c05');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('d946a6b2-139c-4ff4-8211-44e3d81069f1', '페이지 5의 서브페이지 1의 서브페이지 1의 서브페이지 1', '페이지 5의 서브페이지 1의 서브페이지 1의 서브페이지 1의 내용', 'ebc99513-817c-4f36-a8a0-4ec2ed6ee374');

INSERT INTO "PAGE" ("id", "title", "content", "parentId") VALUES
('4f0e5061-03c3-480a-91e3-3ea99d1c983e', '페이지 5의 서브페이지 1의 서브페이지 1의 서브페이지 2', '페이지 5의 서브페이지 1의 서브페이지 1의 서브페이지 2의 내용', 'ebc99513-817c-4f36-a8a0-4ec2ed6ee374');
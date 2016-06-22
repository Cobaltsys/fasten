--liquibase formatted sql logicalFilePath:/fasten

--changeset cobalt:1
CREATE TABLE "public"."customer" (
  "id" int8 NOT NULL,
  "email" varchar(255) COLLATE "default",
  "password" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."customer" ADD PRIMARY KEY ("id") NOT DEFERRABLE INITIALLY IMMEDIATE;
--rollback drop table user;

--changeset cobalt:2
CREATE TABLE "public"."token_history" (
  "id" int8 NOT NULL,
  "expiredate" timestamp(6) NULL,
  "logindate" timestamp(6) NULL,
  "token" varchar(255) COLLATE "default",
  "action" varchar(255) COLLATE "default",
  "user_id" int8
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."token_history" ADD PRIMARY KEY ("id") NOT DEFERRABLE INITIALLY IMMEDIATE;
--rollback drop table token_history;

--changeset cobalt:3
CREATE TABLE "public"."token" (
  "id" int8 NOT NULL,
  "expiredate" timestamp(6) NULL,
  "logindate" timestamp(6) NULL,
  "token" varchar(255) COLLATE "default",
  "user_id" int8
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."token" ADD PRIMARY KEY ("id") NOT DEFERRABLE INITIALLY IMMEDIATE;
--rollback drop table token;

--changeset cobalt:4
ALTER TABLE "public"."token_history" ADD CONSTRAINT "fk_th_u" FOREIGN KEY ("user_id") REFERENCES "public"."customer" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;
ALTER TABLE "public"."token" ADD CONSTRAINT "fk_t_u" FOREIGN KEY ("user_id") REFERENCES "public"."customer" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION NOT DEFERRABLE INITIALLY IMMEDIATE;
--rollback select 1;

--changeset cobalt:5
INSERT INTO "public"."customer" VALUES ('1','SUCCESS_USER','PASSWORD');
--rollback select 1;
-- Adminer 4.8.1 PostgreSQL 15.3 (Debian 15.3-1.pgdg120+1) dump

DROP TABLE IF EXISTS "cat";
DROP SEQUENCE IF EXISTS cat_id_seq;
CREATE SEQUENCE cat_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."cat"
(
    "id"    bigint DEFAULT nextval('cat_id_seq') NOT NULL,
    "name"  text                                 NOT NULL,
    "color" text                                 NOT NULL,
    "breed" text                                 NOT NULL,
    "age"   integer                              NOT NULL,
    CONSTRAINT "cat_pkey" PRIMARY KEY ("id")
) WITH (oids = false);


-- 2023-11-19 18:13:49.301264+00
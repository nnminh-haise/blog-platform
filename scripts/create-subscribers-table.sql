-- Table: public.categories

-- DROP TABLE IF EXISTS public.categories;

CREATE TABLE IF NOT EXISTS public.categories
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    name text COLLATE pg_catalog."default" NOT NULL,
    slug text COLLATE pg_catalog."default" NOT NULL,
    create_at timestamp without time zone NOT NULL,
    update_at timestamp without time zone NOT NULL,
    delete_at timestamp without time zone,
    CONSTRAINT categories_pkey PRIMARY KEY (id),
    CONSTRAINT "unique-categories-name" UNIQUE (name),
    CONSTRAINT "unique-categories-slug" UNIQUE (slug),
    CONSTRAINT "valid-create-and-update-timestamp" CHECK (create_at <= update_at)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.categories
    OWNER to postgres;
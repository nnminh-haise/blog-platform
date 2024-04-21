CREATE TABLE IF NOT EXISTS public.category_details
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    category_id uuid NOT NULL,
    blog_id uuid NOT NULL,
    create_at timestamp without time zone NOT NULL,
    update_at timestamp without time zone NOT NULL,
    delete_at timestamp without time zone,
    CONSTRAINT category_details_pkey PRIMARY KEY (id),
    CONSTRAINT "reference-to-blogs-id-field" FOREIGN KEY (blog_id)
        REFERENCES public.blogs (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT "reference-to-categories-id-field" FOREIGN KEY (category_id)
        REFERENCES public.categories (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT "valid-create-and-update-timestamp" CHECK (create_at <= update_at)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.category_details
    OWNER to postgres;
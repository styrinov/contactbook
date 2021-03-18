CREATE SEQUENCE public.contacts_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.contacts_id_seq
    OWNER TO postgres;

DROP TABLE public.contacts;

CREATE TABLE public.contacts
(
    id bigint NOT NULL DEFAULT nextval('contacts_id_seq'::regclass),
    first_name text NOT NULL,
    last_name text NOT NULL,
    phone text,
    CONSTRAINT pk_contacts PRIMARY KEY (id)

)

INSERT INTO public.contacts (first_name, last_name, phone) VALUES ('Сергей', 'Бобров', '0505555555'), ('Андрей', 'Иванов', '0635445251')

SELECT * FROM  public.contacts;
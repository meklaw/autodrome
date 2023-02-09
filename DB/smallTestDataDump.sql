--
-- Data for Name: enterprise; Type: TABLE DATA; Schema: public; Owner: postgres
-- non driver, gps_point, trip, vehicle

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: autodrome; Type: DATABASE; Schema: -; Owner: postgres
--

-- CREATE DATABASE autodrome  ENCODING = 'UTF8' ;
--
--
-- ALTER DATABASE autodrome OWNER TO postgres;
--
-- \connect autodrome

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: enterprise; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enterprise (
                                   id bigint NOT NULL,
                                   name character varying NOT NULL,
                                   city character varying NOT NULL,
                                   founded date NOT NULL,
                                   time_zone character varying(255) DEFAULT 'UTC'::character varying
);


ALTER TABLE public.enterprise OWNER TO postgres;

--
-- Name: enterprise_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.enterprise ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.enterprise_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: enterprise_manager; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enterprise_manager (
                                           manager_id bigint NOT NULL,
                                           enterprise_id bigint NOT NULL
);


ALTER TABLE public.enterprise_manager OWNER TO postgres;

--
-- Name: manager; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.manager (
    id bigint NOT NULL
);


ALTER TABLE public.manager OWNER TO postgres;

--
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
                               id bigint NOT NULL,
                               username character varying(255) NOT NULL,
                               password character varying(255) NOT NULL,
                               role character varying(255) DEFAULT 'ROLE_USER'::character varying
);


ALTER TABLE public.person OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.person ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: vehicle_brands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicle_brands (
                                       id bigint NOT NULL,
                                       name character varying NOT NULL,
                                       type character varying NOT NULL,
                                       tank double precision NOT NULL,
                                       load_capacity double precision NOT NULL,
                                       number_of_places integer NOT NULL
);


ALTER TABLE public.vehicle_brands OWNER TO postgres;

--
-- Name: vehicle_brands_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.vehicle_brands ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.vehicle_brands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: enterprise; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.enterprise VALUES (1, 'ABC Company', 'New York', '2010-01-01', 'UTC');
INSERT INTO public.enterprise VALUES (3, '123 Inc.', 'Chicago', '2008-03-01', 'UTC');
INSERT INTO public.enterprise VALUES (2, 'XYZ Enterprises', 'Los Angeles', '2012-05-15', 'America/Los_Angeles');


--
-- Data for Name: enterprise_manager; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.enterprise_manager VALUES (1, 1);
INSERT INTO public.enterprise_manager VALUES (1, 2);
INSERT INTO public.enterprise_manager VALUES (2, 2);
INSERT INTO public.enterprise_manager VALUES (2, 3);


--
-- Data for Name: manager; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.manager VALUES (1);
INSERT INTO public.manager VALUES (2);


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.person VALUES (1, 'man1', '$2a$12$37JAhhj.dfM4zGUIswuwZeXcGmZmD4gMBIYjbo2VoQXctfasA/17u', 'ROLE_MANAGER');
INSERT INTO public.person VALUES (2, 'man2', '$2a$12$5Bq7ozHZ.SJ8dMMramu4jurIOSkc0WcqTdVa0ULX9L2N4gox63fhm', 'ROLE_MANAGER');


--
-- Data for Name: vehicle_brands; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vehicle_brands VALUES (1, 'Toyota', 'CAR', 50, 1000, 5);
INSERT INTO public.vehicle_brands VALUES (2, 'Ford', 'TRUCK', 60, 2000, 3);
INSERT INTO public.vehicle_brands VALUES (3, 'Honda', 'MOTORCYCLE', 10, 500, 2);


--
-- Name: enterprise_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.enterprise_id_seq', 3, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 7, true);


--
-- Name: vehicle_brands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vehicle_brands_id_seq', 3, true);


--
-- Name: enterprise_manager enterprise_manager_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enterprise_manager
    ADD CONSTRAINT enterprise_manager_pkey PRIMARY KEY (manager_id, enterprise_id);


--
-- Name: enterprise enterprise_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enterprise
    ADD CONSTRAINT enterprise_pkey PRIMARY KEY (id);


--
-- Name: manager manager_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_pkey PRIMARY KEY (id);


--
-- Name: person users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: person users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: vehicle_brands vehicle_brands_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle_brands
    ADD CONSTRAINT vehicle_brands_pkey PRIMARY KEY (id);


--
-- Name: enterprise_manager enterprise_manager_enterprise_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enterprise_manager
    ADD CONSTRAINT enterprise_manager_enterprise_id_fkey FOREIGN KEY (enterprise_id) REFERENCES public.enterprise(id);


--
-- Name: enterprise_manager enterprise_manager_manager_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enterprise_manager
    ADD CONSTRAINT enterprise_manager_manager_id_fkey FOREIGN KEY (manager_id) REFERENCES public.manager(id);


--
-- Name: manager manager_person_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_person_id_fk FOREIGN KEY (id) REFERENCES public.person(id);


--
-- PostgreSQL database dump complete
--


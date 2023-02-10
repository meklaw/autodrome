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

CREATE DATABASE autodrome WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';


ALTER DATABASE autodrome OWNER TO postgres;

\connect autodrome

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
-- Name: driver; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.driver (
    id bigint NOT NULL,
    name character varying NOT NULL,
    salary integer NOT NULL,
    is_active boolean DEFAULT false,
    enterprise_id bigint,
    vehicle_id bigint
);


ALTER TABLE public.driver OWNER TO postgres;

--
-- Name: driver_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.driver ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.driver_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


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
-- Name: gps_point; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gps_point (
    id bigint NOT NULL,
    vehicle_id bigint NOT NULL,
    date_time timestamp with time zone NOT NULL,
    x double precision NOT NULL,
    y double precision NOT NULL
);


ALTER TABLE public.gps_point OWNER TO postgres;

--
-- Name: gps_point_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.gps_point ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.gps_point_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


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
-- Name: trip; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trip (
    id bigint NOT NULL,
    vehicle_id bigint NOT NULL,
    start_time_utc timestamp with time zone NOT NULL,
    end_time_utc timestamp with time zone NOT NULL,
    length_km integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.trip OWNER TO postgres;

--
-- Name: trip_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.trip ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.trip_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


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
-- Name: vehicle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicle (
    id bigint NOT NULL,
    number character varying NOT NULL,
    cost integer NOT NULL,
    year_of_production integer NOT NULL,
    mileage integer NOT NULL,
    brand_id bigint,
    enterprise_id bigint,
    buy_date_time_utc timestamp without time zone DEFAULT now()
);


ALTER TABLE public.vehicle OWNER TO postgres;

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
-- Name: vehicle_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.vehicle ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.vehicle_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: driver; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.driver VALUES (1, 'John Smith', 50000, true, 1, 1);
INSERT INTO public.driver VALUES (2, 'Jane Doe', 40000, true, 2, 2);
INSERT INTO public.driver VALUES (3, 'Bob Johnson', 60000, false, 3, 3);
INSERT INTO public.driver VALUES (30, 'Bob 10', 360, false, 1, 53);
INSERT INTO public.driver VALUES (32, 'Bob 12', 763, false, 1, 55);
INSERT INTO public.driver VALUES (35, 'Bob 15', 802, false, 1, 58);
INSERT INTO public.driver VALUES (5031, 'Bob 21', 648, false, 2, 5054);
INSERT INTO public.driver VALUES (5032, 'Bob 22', 74, false, 2, 5055);
INSERT INTO public.driver VALUES (5033, 'Bob 23', 581, false, 2, 5056);
INSERT INTO public.driver VALUES (5034, 'Bob 24', 470, true, 2, 5057);
INSERT INTO public.driver VALUES (10030, 'Bob 30', 274, false, 3, 10053);
INSERT INTO public.driver VALUES (10031, 'Bob 31', 554, false, 3, 10054);
INSERT INTO public.driver VALUES (10032, 'Bob 32', 761, false, 3, 10055);


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
-- Data for Name: gps_point; Type: TABLE DATA; Schema: public; Owner: postgres
--



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
-- Data for Name: trip; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: vehicle; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vehicle VALUES (3, '123ABC', 20000, 2012, 80000, 3, 3, '2023-01-17 16:42:15.974574');
INSERT INTO public.vehicle VALUES (53, 'Car 10', 1454, 1961, 60507, 1, 1, '2023-01-22 07:42:15.974574');
INSERT INTO public.vehicle VALUES (2, 'XYZ456', 15000, 2018, 50000, 2, 2, '2023-02-04 23:42:15.974574');
INSERT INTO public.vehicle VALUES (1, 'ABC123', 10000, 2015, 100000, 1, 1, '2023-01-22 07:42:15.974574');
INSERT INTO public.vehicle VALUES (5055, 'Car 22', 5081, 2008, 77593, 3, 2, '2023-02-04 23:42:15.974574');
INSERT INTO public.vehicle VALUES (5054, 'Car 21', 7258, 1933, 28538, 2, 2, '2023-02-04 23:42:15.974574');
INSERT INTO public.vehicle VALUES (10053, 'Car 30', 4818, 1930, 62433, 1, 3, '2023-01-17 16:42:15.974574');
INSERT INTO public.vehicle VALUES (10054, 'Car 31', 429, 2009, 28665, 2, 3, '2023-01-17 16:42:15.974574');
INSERT INTO public.vehicle VALUES (10055, 'Car 32', 770, 1902, 67607, 3, 3, '2023-01-17 16:42:15.974574');
INSERT INTO public.vehicle VALUES (58, 'Car 15', 790, 1918, 75076, 3, 2, '2023-02-04 23:42:15.974574');
INSERT INTO public.vehicle VALUES (5056, 'Car 23', 8039, 2015, 60951, 1, 2, '2023-02-04 23:42:15.974574');
INSERT INTO public.vehicle VALUES (5057, 'Car 24', 7412, 1986, 11175, 2, 2, '2023-02-04 23:42:15.974574');
INSERT INTO public.vehicle VALUES (55, 'Car 12', 4791, 1941, 38582, 3, 1, '2023-01-22 07:42:15.974574');


--
-- Data for Name: vehicle_brands; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vehicle_brands VALUES (1, 'Toyota', 'CAR', 50, 1000, 5);
INSERT INTO public.vehicle_brands VALUES (2, 'Ford', 'TRUCK', 60, 2000, 3);
INSERT INTO public.vehicle_brands VALUES (3, 'Honda', 'MOTORCYCLE', 10, 500, 2);


--
-- Name: driver_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.driver_id_seq', 15029, true);


--
-- Name: enterprise_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.enterprise_id_seq', 3, true);


--
-- Name: gps_point_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gps_point_id_seq', 339494, true);


--
-- Name: trip_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.trip_id_seq', 2582, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 7, true);


--
-- Name: vehicle_brands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vehicle_brands_id_seq', 3, true);


--
-- Name: vehicle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vehicle_id_seq', 15054, true);


--
-- Name: driver driver_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.driver
    ADD CONSTRAINT driver_pkey PRIMARY KEY (id);


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
-- Name: gps_point gps_point_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gps_point
    ADD CONSTRAINT gps_point_pkey PRIMARY KEY (id);


--
-- Name: manager manager_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_pkey PRIMARY KEY (id);


--
-- Name: trip trip_end_time_utc_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trip
    ADD CONSTRAINT trip_end_time_utc_key UNIQUE (end_time_utc);


--
-- Name: trip trip_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trip
    ADD CONSTRAINT trip_pkey PRIMARY KEY (id);


--
-- Name: trip trip_start_time_utc_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trip
    ADD CONSTRAINT trip_start_time_utc_key UNIQUE (start_time_utc);


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
-- Name: vehicle vehicle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vehicle_pkey PRIMARY KEY (id);


--
-- Name: gps_point_vehicle_id_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX gps_point_vehicle_id_idx ON public.gps_point USING btree (vehicle_id);


--
-- Name: driver driver_enterprise_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.driver
    ADD CONSTRAINT driver_enterprise_id_fkey FOREIGN KEY (enterprise_id) REFERENCES public.enterprise(id);


--
-- Name: driver driver_vehicle_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.driver
    ADD CONSTRAINT driver_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES public.vehicle(id);


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
-- Name: gps_point gps_point_vehicle_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gps_point
    ADD CONSTRAINT gps_point_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES public.vehicle(id);


--
-- Name: manager manager_person_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_person_id_fk FOREIGN KEY (id) REFERENCES public.person(id);


--
-- Name: trip trip_vehicle_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trip
    ADD CONSTRAINT trip_vehicle_id_fkey FOREIGN KEY (vehicle_id) REFERENCES public.vehicle(id);


--
-- Name: vehicle vehicle_brand_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vehicle_brand_id_fkey FOREIGN KEY (brand_id) REFERENCES public.vehicle_brands(id);


--
-- Name: vehicle vehicle_enterprise_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicle
    ADD CONSTRAINT vehicle_enterprise_id_fkey FOREIGN KEY (enterprise_id) REFERENCES public.enterprise(id);


--
-- PostgreSQL database dump complete
--

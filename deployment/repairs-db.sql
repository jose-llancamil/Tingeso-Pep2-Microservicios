-- Conectar a la base de datos `postgres`
\c postgres;

-- Crear la nueva base de datos
CREATE DATABASE "repairs-db";

-- Conectar a la nueva base de datos
\c "repairs-db";
--
-- PostgreSQL database dump
--

-- Dumped from database version 12.19
-- Dumped by pg_dump version 12.19

-- Started on 2024-07-06 03:22:12

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
-- TOC entry 207 (class 1259 OID 138671)
-- Name: discount_coupons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.discount_coupons (
    id bigint NOT NULL,
    marca character varying(50),
    monto double precision,
    cantidad integer
);


ALTER TABLE public.discount_coupons OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 138669)
-- Name: discount_coupons_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.discount_coupons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.discount_coupons_id_seq OWNER TO postgres;

--
-- TOC entry 2845 (class 0 OID 0)
-- Dependencies: 206
-- Name: discount_coupons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.discount_coupons_id_seq OWNED BY public.discount_coupons.id;


--
-- TOC entry 205 (class 1259 OID 138663)
-- Name: repair_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repair_details (
    id bigint NOT NULL,
    repair_id bigint NOT NULL,
    tipo_reparacion character varying(255) NOT NULL,
    fecha_reparacion date NOT NULL,
    hora_reparacion time without time zone NOT NULL,
    monto_reparacion double precision
);


ALTER TABLE public.repair_details OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 138661)
-- Name: repair_details_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repair_details_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repair_details_id_seq OWNER TO postgres;

--
-- TOC entry 2846 (class 0 OID 0)
-- Dependencies: 204
-- Name: repair_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repair_details_id_seq OWNED BY public.repair_details.id;


--
-- TOC entry 203 (class 1259 OID 138655)
-- Name: repairs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repairs (
    id bigint NOT NULL,
    vehiculo_id bigint NOT NULL,
    fecha_ingreso date NOT NULL,
    hora_ingreso time without time zone NOT NULL,
    monto_total_reparaciones double precision,
    monto_recargos double precision,
    monto_descuentos double precision,
    monto_iva double precision,
    costo_total double precision,
    fecha_salida date,
    hora_salida time without time zone,
    fecha_retiro date,
    hora_retiro time without time zone,
    monto_descuento_cupon double precision
);


ALTER TABLE public.repairs OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 138653)
-- Name: repairs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repairs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repairs_id_seq OWNER TO postgres;

--
-- TOC entry 2847 (class 0 OID 0)
-- Dependencies: 202
-- Name: repairs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repairs_id_seq OWNED BY public.repairs.id;


--
-- TOC entry 2701 (class 2604 OID 138674)
-- Name: discount_coupons id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discount_coupons ALTER COLUMN id SET DEFAULT nextval('public.discount_coupons_id_seq'::regclass);


--
-- TOC entry 2700 (class 2604 OID 138666)
-- Name: repair_details id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repair_details ALTER COLUMN id SET DEFAULT nextval('public.repair_details_id_seq'::regclass);


--
-- TOC entry 2699 (class 2604 OID 138658)
-- Name: repairs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repairs ALTER COLUMN id SET DEFAULT nextval('public.repairs_id_seq'::regclass);


--
-- TOC entry 2839 (class 0 OID 138671)
-- Dependencies: 207
-- Data for Name: discount_coupons; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.discount_coupons (id, marca, monto, cantidad) FROM stdin;
1	Toyota	70000	20
2	Ford	50000	20
3	Hyundai	30000	20
4	Honda	40000	20
\.


--
-- TOC entry 2837 (class 0 OID 138663)
-- Dependencies: 205
-- Data for Name: repair_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repair_details (id, repair_id, tipo_reparacion, fecha_reparacion, hora_reparacion, monto_reparacion) FROM stdin;
\.


--
-- TOC entry 2835 (class 0 OID 138655)
-- Dependencies: 203
-- Data for Name: repairs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repairs (id, vehiculo_id, fecha_ingreso, hora_ingreso, monto_total_reparaciones, monto_recargos, monto_descuentos, monto_iva, costo_total, fecha_salida, hora_salida, fecha_retiro, hora_retiro, monto_descuento_cupon) FROM stdin;
\.


--
-- TOC entry 2848 (class 0 OID 0)
-- Dependencies: 206
-- Name: discount_coupons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.discount_coupons_id_seq', 4, true);


--
-- TOC entry 2849 (class 0 OID 0)
-- Dependencies: 204
-- Name: repair_details_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repair_details_id_seq', 1, false);


--
-- TOC entry 2850 (class 0 OID 0)
-- Dependencies: 202
-- Name: repairs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repairs_id_seq', 1, false);


--
-- TOC entry 2707 (class 2606 OID 138676)
-- Name: discount_coupons discount_coupons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discount_coupons
    ADD CONSTRAINT discount_coupons_pkey PRIMARY KEY (id);


--
-- TOC entry 2705 (class 2606 OID 138668)
-- Name: repair_details repair_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repair_details
    ADD CONSTRAINT repair_details_pkey PRIMARY KEY (id);


--
-- TOC entry 2703 (class 2606 OID 138660)
-- Name: repairs repairs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repairs
    ADD CONSTRAINT repairs_pkey PRIMARY KEY (id);


-- Completed on 2024-07-06 03:22:13

--
-- PostgreSQL database dump complete
--


--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2 (Debian 16.2-1.pgdg120+2)

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
-- Name: product; Type: TABLE; Schema: public; Owner: nonroot
--

CREATE TABLE public.product (
    id bigint NOT NULL,
    create_date timestamp(6) without time zone NOT NULL,
    description character varying(255),
    last_modified_date timestamp(6) without time zone,
    name character varying(255)
);


ALTER TABLE public.product OWNER TO nonroot;

--
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: nonroot
--

CREATE SEQUENCE public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.product_id_seq OWNER TO nonroot;

--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nonroot
--

ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;


--
-- Name: vending_item; Type: TABLE; Schema: public; Owner: nonroot
--

CREATE TABLE public.vending_item (
    id bigint NOT NULL,
    create_date timestamp(6) without time zone NOT NULL,
    identifier smallint,
    last_modified_date timestamp(6) without time zone,
    price integer,
    quantity integer NOT NULL,
    product_id bigint,
    CONSTRAINT vending_item_identifier_check CHECK (((identifier >= 0) AND (identifier <= 11)))
);


ALTER TABLE public.vending_item OWNER TO nonroot;

--
-- Name: vending_item_id_seq; Type: SEQUENCE; Schema: public; Owner: nonroot
--

CREATE SEQUENCE public.vending_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vending_item_id_seq OWNER TO nonroot;

--
-- Name: vending_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nonroot
--

ALTER SEQUENCE public.vending_item_id_seq OWNED BY public.vending_item.id;


--
-- Name: product id; Type: DEFAULT; Schema: public; Owner: nonroot
--

ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.product_id_seq'::regclass);


--
-- Name: vending_item id; Type: DEFAULT; Schema: public; Owner: nonroot
--

ALTER TABLE ONLY public.vending_item ALTER COLUMN id SET DEFAULT nextval('public.vending_item_id_seq'::regclass);


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: nonroot
--

COPY public.product (id, create_date, description, last_modified_date, name) FROM stdin;
1	2024-03-21 17:01:18.814737	A tasty drink	\N	Coke
2	2024-03-22 20:12:42.427946	A sugary candy bar	\N	Twix
3	2024-03-22 20:13:24.917531	Your kind's favorite snack	\N	Pomber
4	2024-03-22 20:20:08.462228	Your kind's favorite snack	\N	Hrus-Hrus
5	2024-03-22 20:52:15.54783	Something your aunty would eat for breakfast and hope for a quick weight reduction	\N	Belvita
6	2024-03-22 20:54:26.203281	Premium spirits drink made in a village near Plovdiv.	\N	Vodka Beluga
7	2024-03-22 20:56:18.557195	Ein leckeres Weissbier	\N	Weisser Hase
8	2024-03-22 22:11:25.594512	Beverage largely consumed near the Bulgarian seaside. Often causes side effects such as nausea, vomiting, headache and dehydration. Must be the ice.	\N	Vodka Smirnoff
\.


--
-- Data for Name: vending_item; Type: TABLE DATA; Schema: public; Owner: nonroot
--

COPY public.vending_item (id, create_date, identifier, last_modified_date, price, quantity, product_id) FROM stdin;
12	2024-03-22 22:31:54.331978	9	\N	10000	10	8
9	2024-03-22 22:30:48.727311	1	\N	250	9	2
10	2024-03-22 22:31:00.493412	11	\N	500	8	7
11	2024-03-22 22:31:26.583535	4	\N	230	9	5
1	2024-03-21 17:01:44.494191	0	\N	500	18	1
\.


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nonroot
--

SELECT pg_catalog.setval('public.product_id_seq', 10, true);


--
-- Name: vending_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nonroot
--

SELECT pg_catalog.setval('public.vending_item_id_seq', 12, true);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: nonroot
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: vending_item uk_5xl17fj9p1ftg3yt1tre2d8dw; Type: CONSTRAINT; Schema: public; Owner: nonroot
--

ALTER TABLE ONLY public.vending_item
    ADD CONSTRAINT uk_5xl17fj9p1ftg3yt1tre2d8dw UNIQUE (product_id);


--
-- Name: vending_item uk_c6pw6e8diul59va4q83x31xho; Type: CONSTRAINT; Schema: public; Owner: nonroot
--

ALTER TABLE ONLY public.vending_item
    ADD CONSTRAINT uk_c6pw6e8diul59va4q83x31xho UNIQUE (identifier);


--
-- Name: product uk_jmivyxk9rmgysrmsqw15lqr5b; Type: CONSTRAINT; Schema: public; Owner: nonroot
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT uk_jmivyxk9rmgysrmsqw15lqr5b UNIQUE (name);


--
-- Name: vending_item vending_item_pkey; Type: CONSTRAINT; Schema: public; Owner: nonroot
--

ALTER TABLE ONLY public.vending_item
    ADD CONSTRAINT vending_item_pkey PRIMARY KEY (id);


--
-- Name: vending_item fk9goq859ew2qf7s3s12q2p1vgb; Type: FK CONSTRAINT; Schema: public; Owner: nonroot
--

ALTER TABLE ONLY public.vending_item
    ADD CONSTRAINT fk9goq859ew2qf7s3s12q2p1vgb FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- PostgreSQL database dump complete
--


--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.12
-- Dumped by pg_dump version 9.5.12

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu (
    idmenu integer NOT NULL,
    men_idmenu integer,
    nom character varying(254),
    cle character varying(254)
);


ALTER TABLE public.menu OWNER TO postgres;

--
-- Name: menu_idmenu_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.menu_idmenu_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.menu_idmenu_seq OWNER TO postgres;

--
-- Name: menu_idmenu_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.menu_idmenu_seq OWNED BY public.menu.idmenu;


--
-- Name: operations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.operations (
    idoperations integer NOT NULL,
    idutilisateur integer NOT NULL,
    nom character varying(254),
    cible character varying(254),
    date date,
    heure time without time zone,
    adresseip character varying(254)
);


ALTER TABLE public.operations OWNER TO postgres;

--
-- Name: operations_idoperations_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.operations_idoperations_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.operations_idoperations_seq OWNER TO postgres;

--
-- Name: operations_idoperations_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.operations_idoperations_seq OWNED BY public.operations.idoperations;


--
-- Name: privileges; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.privileges (
    idutilisateur integer NOT NULL,
    idmenu integer NOT NULL
);


ALTER TABLE public.privileges OWNER TO postgres;

--
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utilisateur (
    idutilisateur integer NOT NULL,
    nom character varying(254),
    prenom character varying(254),
    datenais date,
    lieunais character varying(254),
    sexe character varying(254),
    photo character varying(254),
    login character varying(254),
    mdp character varying(254)
);


ALTER TABLE public.utilisateur OWNER TO postgres;

--
-- Name: idmenu; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu ALTER COLUMN idmenu SET DEFAULT nextval('public.menu_idmenu_seq'::regclass);


--
-- Name: idoperations; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operations ALTER COLUMN idoperations SET DEFAULT nextval('public.operations_idoperations_seq'::regclass);


--
-- Name: pk_menu; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu
    ADD CONSTRAINT pk_menu PRIMARY KEY (idmenu);


--
-- Name: pk_operations; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operations
    ADD CONSTRAINT pk_operations PRIMARY KEY (idoperations);


--
-- Name: pk_privileges; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.privileges
    ADD CONSTRAINT pk_privileges PRIMARY KEY (idutilisateur, idmenu);


--
-- Name: pk_utilisateur; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT pk_utilisateur PRIMARY KEY (idutilisateur);


--
-- Name: acceder_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX acceder_fk ON public.privileges USING btree (idutilisateur);


--
-- Name: association1_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX association1_fk ON public.operations USING btree (idutilisateur);


--
-- Name: association4_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX association4_fk ON public.menu USING btree (men_idmenu);


--
-- Name: est_accessible_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX est_accessible_fk ON public.privileges USING btree (idmenu);


--
-- Name: menu_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX menu_pk ON public.menu USING btree (idmenu);


--
-- Name: operations_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX operations_pk ON public.operations USING btree (idoperations);


--
-- Name: privileges_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX privileges_pk ON public.privileges USING btree (idutilisateur, idmenu);


--
-- Name: utilisateur_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX utilisateur_pk ON public.utilisateur USING btree (idutilisateur);


--
-- Name: fk_menu_associati_menu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu
    ADD CONSTRAINT fk_menu_associati_menu FOREIGN KEY (men_idmenu) REFERENCES public.menu(idmenu) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: fk_operatio_associati_utilisat; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operations
    ADD CONSTRAINT fk_operatio_associati_utilisat FOREIGN KEY (idutilisateur) REFERENCES public.utilisateur(idutilisateur) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: fk_privileg_acceder_utilisat; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.privileges
    ADD CONSTRAINT fk_privileg_acceder_utilisat FOREIGN KEY (idutilisateur) REFERENCES public.utilisateur(idutilisateur) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: fk_privileg_est_acces_menu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.privileges
    ADD CONSTRAINT fk_privileg_est_acces_menu FOREIGN KEY (idmenu) REFERENCES public.menu(idmenu) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


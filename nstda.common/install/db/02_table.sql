--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.2
-- Dumped by pg_dump version 9.5.2

-- Started on 2016-11-03 17:09:39

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 387 (class 1259 OID 164114)
-- Name: pb2_exp_brw; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_exp_brw (
    id character varying(50) NOT NULL,
    total double precision,
    folder_ref character varying(255),
    doc_ref character varying(255),
    workflow_ins_id character varying(50),
    waiting_level integer,
    status character varying(2),
    req_by character varying(20),
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    objective_type character varying,
    objective character varying(1000),
    reason character varying(1000),
    budget_cc_type character varying(1),
    budget_cc integer,
    cost_control_type_id integer,
    cost_control_id integer,
    bank_type character varying,
    date_back timestamp with time zone,
    bank integer,
    fund_id integer
);


ALTER TABLE pb2_exp_brw OWNER TO alfresco;

--
-- TOC entry 388 (class 1259 OID 164124)
-- Name: pb2_exp_brw_attendee; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_exp_brw_attendee (
    id bigint DEFAULT nextval('pb2_exp_brw_voyager_id_seq'::regclass) NOT NULL,
    master_id character varying(50) NOT NULL,
    code character varying,
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    fname character varying,
    unit_type character varying,
    "position" character varying,
    type character varying,
    position_id integer,
    lname character varying,
    title character varying
);


ALTER TABLE pb2_exp_brw_attendee OWNER TO alfresco;

--
-- TOC entry 398 (class 1259 OID 164745)
-- Name: pb2_exp_brw_dtl; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_exp_brw_dtl (
    id bigint NOT NULL,
    master_id character varying(50) NOT NULL,
    activity character varying,
    amount double precision,
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    act_grp_id integer,
    act_id integer,
    condition_1 character varying
);


ALTER TABLE pb2_exp_brw_dtl OWNER TO alfresco;

--
-- TOC entry 397 (class 1259 OID 164743)
-- Name: pb2_exp_brw_dtl_id_seq; Type: SEQUENCE; Schema: public; Owner: alfresco
--

CREATE SEQUENCE pb2_exp_brw_dtl_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pb2_exp_brw_dtl_id_seq OWNER TO alfresco;

--
-- TOC entry 2977 (class 0 OID 0)
-- Dependencies: 397
-- Name: pb2_exp_brw_dtl_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alfresco
--

ALTER SEQUENCE pb2_exp_brw_dtl_id_seq OWNED BY pb2_exp_brw_dtl.id;


--
-- TOC entry 380 (class 1259 OID 164086)
-- Name: pb2_main_master; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_main_master (
    id bigint NOT NULL,
    type character varying(10),
    code character varying(40),
    name character varying(255),
    is_active boolean,
    flag1 character varying(255),
    flag2 character varying(255),
    flag3 character varying(255),
    flag4 character varying(255),
    flag5 character varying(255),
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20)
);


ALTER TABLE pb2_main_master OWNER TO alfresco;

--
-- TOC entry 361 (class 1259 OID 72599)
-- Name: pb2_main_workflow; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_main_workflow (
    id bigint NOT NULL,
    type character varying(10),
    master_id character varying(50) NOT NULL,
    workflow_ins_id character varying(255),
    status character varying(255),
    by character varying(20),
    by_time timestamp with time zone,
    task_id character varying(255),
    created_time timestamp with time zone,
    created_by character varying(20),
    assignee character varying(20),
    execution_id character varying,
    status_th character varying
);


ALTER TABLE pb2_main_workflow OWNER TO alfresco;

--
-- TOC entry 365 (class 1259 OID 72615)
-- Name: pb2_main_workflow_next_actor; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_main_workflow_next_actor (
    id bigint NOT NULL,
    master_id character varying(20),
    level integer,
    actor_group character varying(500),
    actor_user character varying(500),
    created_time timestamp with time zone,
    created_by character varying(20),
    actor character varying
);


ALTER TABLE pb2_main_workflow_next_actor OWNER TO alfresco;

--
-- TOC entry 367 (class 1259 OID 72623)
-- Name: pb2_main_workflow_reviewer; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_main_workflow_reviewer (
    id bigint NOT NULL,
    master_id character varying(20),
    level integer,
    reviewer_group character varying(500),
    reviewer_user character varying(500),
    percent double precision,
    rewarning integer,
    hint character varying(255),
    created_time timestamp with time zone,
    created_by character varying(20)
);


ALTER TABLE pb2_main_workflow_reviewer OWNER TO alfresco;

--
-- TOC entry 389 (class 1259 OID 164140)
-- Name: pb2_exp_use; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_exp_use (
    id character varying(50) NOT NULL,
    total double precision,
    folder_ref character varying(255),
    doc_ref character varying(255),
    workflow_ins_id character varying(50),
    waiting_level integer,
    status character varying(2),
    req_by character varying(20),
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    objective character varying(1000),
    budget_cc_type character varying(1),
    budget_cc integer,
    cost_control_type_id integer,
    cost_control_id integer,
    cost_control character varying,
    cost_control_from timestamp with time zone,
    cost_control_to timestamp with time zone,
    bank_type character varying,
    bank integer,
    pay_type character varying,
    pay_dtl1 character varying,
    pay_dtl2 character varying,
    pay_dtl3 character varying,
    fund_id integer,
    reason character varying
);


ALTER TABLE pb2_exp_use OWNER TO alfresco;

--
-- TOC entry 391 (class 1259 OID 164166)
-- Name: pb2_exp_use_attendee; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_exp_use_attendee (
    id bigint DEFAULT nextval('pb2_exp_use_voyager_id_seq'::regclass) NOT NULL,
    master_id character varying(50) NOT NULL,
    code character varying,
    fname character varying,
    unit_type character varying,
    "position" character varying,
    type character varying,
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    position_id integer,
    lname character varying,
    title character varying
);


ALTER TABLE pb2_exp_use_attendee OWNER TO alfresco;

--
-- TOC entry 390 (class 1259 OID 164150)
-- Name: pb2_exp_use_dtl; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_exp_use_dtl (
    id bigint DEFAULT nextval('pb2_exp_use_dtl_id_seq'::regclass) NOT NULL,
    master_id character varying(50) NOT NULL,
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    condition_1 character varying,
    amount double precision,
    condition_2 character varying,
    "position" character varying,
    uom character varying,
    act_id integer,
    act_grp_id integer,
    activity character varying
);


ALTER TABLE pb2_exp_use_dtl OWNER TO alfresco;

--
-- TOC entry 358 (class 1259 OID 72581)
-- Name: pb2_main_complete_notification; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_main_complete_notification (
    id bigint NOT NULL,
    receiver character varying(255),
    task_id character varying(100),
    template character(1),
    created_time timestamp with time zone,
    created_by character varying(20)
);


ALTER TABLE pb2_main_complete_notification OWNER TO alfresco;

--
-- TOC entry 359 (class 1259 OID 72584)
-- Name: pb2_main_complete_notification_id_seq; Type: SEQUENCE; Schema: public; Owner: alfresco
--

CREATE SEQUENCE pb2_main_complete_notification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pb2_main_complete_notification_id_seq OWNER TO alfresco;

--
-- TOC entry 2978 (class 0 OID 0)
-- Dependencies: 359
-- Name: pb2_main_complete_notification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alfresco
--

ALTER SEQUENCE pb2_main_complete_notification_id_seq OWNED BY pb2_main_complete_notification.id;


--
-- TOC entry 381 (class 1259 OID 164094)
-- Name: pb2_main_master_id_seq; Type: SEQUENCE; Schema: public; Owner: alfresco
--

CREATE SEQUENCE pb2_main_master_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pb2_main_master_id_seq OWNER TO alfresco;

--
-- TOC entry 2979 (class 0 OID 0)
-- Dependencies: 381
-- Name: pb2_main_master_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alfresco
--

ALTER SEQUENCE pb2_main_master_id_seq OWNED BY pb2_main_master.id;


--
-- TOC entry 379 (class 1259 OID 163981)
-- Name: pb2_main_master_old; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_main_master_old (
    id bigint NOT NULL,
    type character varying(10),
    code character varying(40),
    name character varying(255),
    is_active boolean,
    flag1 character varying(255),
    flag2 character varying(255),
    flag3 character varying(255),
    flag4 character varying(255),
    flag5 character varying(255),
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20)
);


ALTER TABLE pb2_main_master_old OWNER TO alfresco;

--
-- TOC entry 360 (class 1259 OID 72596)
-- Name: pb2_main_msg; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_main_msg (
    user_ character varying(100),
    msg character varying(255)
);


ALTER TABLE pb2_main_msg OWNER TO alfresco;

--
-- TOC entry 362 (class 1259 OID 72605)
-- Name: pb2_main_workflow_history; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_main_workflow_history (
    id bigint NOT NULL,
    master_id bigint,
    "time" timestamp with time zone,
    by character varying(50),
    action character varying(255),
    task character varying(255),
    comment character varying,
    level integer,
    status character varying,
    action_th character varying,
    task_th character varying
);


ALTER TABLE pb2_main_workflow_history OWNER TO alfresco;

--
-- TOC entry 363 (class 1259 OID 72611)
-- Name: pb2_main_workflow_history_id_seq; Type: SEQUENCE; Schema: public; Owner: alfresco
--

CREATE SEQUENCE pb2_main_workflow_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pb2_main_workflow_history_id_seq OWNER TO alfresco;

--
-- TOC entry 2980 (class 0 OID 0)
-- Dependencies: 363
-- Name: pb2_main_workflow_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alfresco
--

ALTER SEQUENCE pb2_main_workflow_history_id_seq OWNED BY pb2_main_workflow_history.id;


--
-- TOC entry 364 (class 1259 OID 72613)
-- Name: pb2_main_workflow_id_seq; Type: SEQUENCE; Schema: public; Owner: alfresco
--

CREATE SEQUENCE pb2_main_workflow_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pb2_main_workflow_id_seq OWNER TO alfresco;

--
-- TOC entry 2981 (class 0 OID 0)
-- Dependencies: 364
-- Name: pb2_main_workflow_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alfresco
--

ALTER SEQUENCE pb2_main_workflow_id_seq OWNED BY pb2_main_workflow.id;


--
-- TOC entry 366 (class 1259 OID 72621)
-- Name: pb2_main_workflow_next_actor_id_seq; Type: SEQUENCE; Schema: public; Owner: alfresco
--

CREATE SEQUENCE pb2_main_workflow_next_actor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pb2_main_workflow_next_actor_id_seq OWNER TO alfresco;

--
-- TOC entry 2982 (class 0 OID 0)
-- Dependencies: 366
-- Name: pb2_main_workflow_next_actor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alfresco
--

ALTER SEQUENCE pb2_main_workflow_next_actor_id_seq OWNED BY pb2_main_workflow_next_actor.id;


--
-- TOC entry 368 (class 1259 OID 72629)
-- Name: pb2_main_workflow_reviewer_id_seq; Type: SEQUENCE; Schema: public; Owner: alfresco
--

CREATE SEQUENCE pb2_main_workflow_reviewer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pb2_main_workflow_reviewer_id_seq OWNER TO alfresco;

--
-- TOC entry 2983 (class 0 OID 0)
-- Dependencies: 368
-- Name: pb2_main_workflow_reviewer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alfresco
--

ALTER SEQUENCE pb2_main_workflow_reviewer_id_seq OWNED BY pb2_main_workflow_reviewer.id;


--
-- TOC entry 369 (class 1259 OID 72631)
-- Name: pb2_pcm_ord; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_pcm_ord (
    id character varying(50) NOT NULL,
    total double precision,
    folder_ref character varying(255),
    doc_ref character varying(255),
    status character varying(2),
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    waiting_level integer,
    workflow_ins_id character varying(50),
    objective character varying,
    section_id integer,
    pr_id character varying,
    doc_type character varying,
    app_by character varying
);


ALTER TABLE pb2_pcm_ord OWNER TO alfresco;

--
-- TOC entry 370 (class 1259 OID 72639)
-- Name: pb2_pcm_req; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_pcm_req (
    id character varying(50) NOT NULL,
    total double precision,
    folder_ref character varying(255),
    doc_ref character varying(255),
    workflow_ins_id character varying(50),
    waiting_level integer,
    status character varying(2),
    req_by character varying(20),
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    objective_type character varying(10),
    objective character varying(1000),
    reason character varying(1000),
    currency character varying(10),
    currency_rate real,
    prototype character varying(30),
    location character varying(1000),
    across_budget double precision,
    ref_id character varying(50),
    method_cond2_rule character varying(255),
    method_cond2 character varying(255),
    method_cond2_dtl character varying(1000),
    vat real,
    is_stock character varying(1),
    is_prototype character varying(1),
    is_across_budget character varying(1),
    is_ref_id character varying(1),
    vat_id integer,
    prototype_contract_no character varying(50),
    budget_cc_type character varying(1),
    budget_cc integer,
    pcm_section_id integer,
    req_section_id integer,
    stock_section_id integer,
    cost_control_type_id integer,
    cost_control_id integer,
    prweb_method_id bigint,
    ref_doc_ref character varying,
    contract_date timestamp with time zone,
    total_cnv double precision,
    fund_id integer
);


ALTER TABLE pb2_pcm_req OWNER TO alfresco;

--
-- TOC entry 2984 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.total; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.total IS 'Total';


--
-- TOC entry 2985 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.folder_ref; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.folder_ref IS 'Folder Node Ref.';


--
-- TOC entry 2986 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.doc_ref; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.doc_ref IS 'Document Node Ref.';


--
-- TOC entry 2987 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.workflow_ins_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.workflow_ins_id IS 'Workflow Instance ID';


--
-- TOC entry 2988 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.waiting_level; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.waiting_level IS 'Workflow Waiting Level';


--
-- TOC entry 2989 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.status; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.status IS 'PR Status';


--
-- TOC entry 2990 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.req_by; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.req_by IS 'Requeted by';


--
-- TOC entry 2991 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.created_time; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.created_time IS 'Created on';


--
-- TOC entry 2992 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.created_by; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.created_by IS 'Created by';


--
-- TOC entry 2993 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.updated_time; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.updated_time IS 'Last Updated on';


--
-- TOC entry 2994 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.updated_by; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.updated_by IS 'Last Updated by';


--
-- TOC entry 2995 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.objective_type; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.objective_type IS 'Objective Type';


--
-- TOC entry 2996 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.objective; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.objective IS 'Objective';


--
-- TOC entry 2997 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.reason; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.reason IS 'Reason';


--
-- TOC entry 2998 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.currency; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.currency IS 'Currency Unit';


--
-- TOC entry 2999 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.currency_rate; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.currency_rate IS 'Currency Rate';


--
-- TOC entry 3000 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.prototype; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.prototype IS 'Prototype';


--
-- TOC entry 3001 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.location; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.location IS 'Delivery Address';


--
-- TOC entry 3002 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.across_budget; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.across_budget IS 'Across Budget';


--
-- TOC entry 3003 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.ref_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.ref_id IS 'Reference ID';


--
-- TOC entry 3004 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.method_cond2_rule; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.method_cond2_rule IS 'Purchase Method Condition Rule';


--
-- TOC entry 3005 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.method_cond2; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.method_cond2 IS 'Purchase Method Condition';


--
-- TOC entry 3006 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.method_cond2_dtl; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.method_cond2_dtl IS 'Purchase Method Condition Detail';


--
-- TOC entry 3007 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.vat; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.vat IS 'Vat';


--
-- TOC entry 3008 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.vat_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.vat_id IS 'VAT ID';


--
-- TOC entry 3009 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.prototype_contract_no; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.prototype_contract_no IS 'Prototype Contract No.';


--
-- TOC entry 3010 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.budget_cc_type; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.budget_cc_type IS 'Type of budget (Unit/Project)';


--
-- TOC entry 3011 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.budget_cc; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.budget_cc IS 'Budget Section ID';


--
-- TOC entry 3012 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.pcm_section_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.pcm_section_id IS 'Procuement Section ID';


--
-- TOC entry 3013 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.req_section_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.req_section_id IS 'Requester Section ID';


--
-- TOC entry 3014 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.stock_section_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.stock_section_id IS 'Stock Section ID';


--
-- TOC entry 3015 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.cost_control_type_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.cost_control_type_id IS 'Cost Control Type';


--
-- TOC entry 3016 (class 0 OID 0)
-- Dependencies: 370
-- Name: COLUMN pb2_pcm_req.cost_control_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req.cost_control_id IS 'Cost Control';


--
-- TOC entry 371 (class 1259 OID 72647)
-- Name: pb2_pcm_req_committee_dtl; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_pcm_req_committee_dtl (
    id bigint NOT NULL,
    master_id bigint NOT NULL,
    first_name character varying,
    "position" character varying,
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    last_name character varying,
    employee_code character varying,
    title character varying
);


ALTER TABLE pb2_pcm_req_committee_dtl OWNER TO alfresco;

--
-- TOC entry 3017 (class 0 OID 0)
-- Dependencies: 371
-- Name: COLUMN pb2_pcm_req_committee_dtl.master_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_dtl.master_id IS 'Committee Header ID';


--
-- TOC entry 3018 (class 0 OID 0)
-- Dependencies: 371
-- Name: COLUMN pb2_pcm_req_committee_dtl.first_name; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_dtl.first_name IS 'First Name';


--
-- TOC entry 3019 (class 0 OID 0)
-- Dependencies: 371
-- Name: COLUMN pb2_pcm_req_committee_dtl."position"; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_dtl."position" IS 'Position';


--
-- TOC entry 3020 (class 0 OID 0)
-- Dependencies: 371
-- Name: COLUMN pb2_pcm_req_committee_dtl.created_time; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_dtl.created_time IS 'Created On';


--
-- TOC entry 3021 (class 0 OID 0)
-- Dependencies: 371
-- Name: COLUMN pb2_pcm_req_committee_dtl.created_by; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_dtl.created_by IS 'Created By';


--
-- TOC entry 3022 (class 0 OID 0)
-- Dependencies: 371
-- Name: COLUMN pb2_pcm_req_committee_dtl.updated_time; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_dtl.updated_time IS 'Last Updated On';


--
-- TOC entry 3023 (class 0 OID 0)
-- Dependencies: 371
-- Name: COLUMN pb2_pcm_req_committee_dtl.updated_by; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_dtl.updated_by IS 'Last Updated By';


--
-- TOC entry 3024 (class 0 OID 0)
-- Dependencies: 371
-- Name: COLUMN pb2_pcm_req_committee_dtl.last_name; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_dtl.last_name IS 'Last Name';


--
-- TOC entry 372 (class 1259 OID 72655)
-- Name: pb2_pcm_req_committee_dtl_id_seq; Type: SEQUENCE; Schema: public; Owner: alfresco
--

CREATE SEQUENCE pb2_pcm_req_committee_dtl_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pb2_pcm_req_committee_dtl_id_seq OWNER TO alfresco;

--
-- TOC entry 3025 (class 0 OID 0)
-- Dependencies: 372
-- Name: pb2_pcm_req_committee_dtl_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alfresco
--

ALTER SEQUENCE pb2_pcm_req_committee_dtl_id_seq OWNED BY pb2_pcm_req_committee_dtl.id;


--
-- TOC entry 373 (class 1259 OID 72657)
-- Name: pb2_pcm_req_committee_hdr; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_pcm_req_committee_hdr (
    id bigint NOT NULL,
    pcm_req_id character varying(50) NOT NULL,
    committee character varying(255),
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    committee_id integer
);


ALTER TABLE pb2_pcm_req_committee_hdr OWNER TO alfresco;

--
-- TOC entry 3026 (class 0 OID 0)
-- Dependencies: 373
-- Name: COLUMN pb2_pcm_req_committee_hdr.pcm_req_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_hdr.pcm_req_id IS 'PR Number';


--
-- TOC entry 3027 (class 0 OID 0)
-- Dependencies: 373
-- Name: COLUMN pb2_pcm_req_committee_hdr.committee; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_hdr.committee IS 'Committee Name';


--
-- TOC entry 3028 (class 0 OID 0)
-- Dependencies: 373
-- Name: COLUMN pb2_pcm_req_committee_hdr.created_time; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_hdr.created_time IS 'Created on';


--
-- TOC entry 3029 (class 0 OID 0)
-- Dependencies: 373
-- Name: COLUMN pb2_pcm_req_committee_hdr.created_by; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_hdr.created_by IS 'Created by';


--
-- TOC entry 3030 (class 0 OID 0)
-- Dependencies: 373
-- Name: COLUMN pb2_pcm_req_committee_hdr.updated_time; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_hdr.updated_time IS 'Last Updated on';


--
-- TOC entry 3031 (class 0 OID 0)
-- Dependencies: 373
-- Name: COLUMN pb2_pcm_req_committee_hdr.updated_by; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_committee_hdr.updated_by IS 'Last Updated by';


--
-- TOC entry 374 (class 1259 OID 72662)
-- Name: pb2_pcm_req_committee_hdr_id_seq; Type: SEQUENCE; Schema: public; Owner: alfresco
--

CREATE SEQUENCE pb2_pcm_req_committee_hdr_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pb2_pcm_req_committee_hdr_id_seq OWNER TO alfresco;

--
-- TOC entry 3032 (class 0 OID 0)
-- Dependencies: 374
-- Name: pb2_pcm_req_committee_hdr_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alfresco
--

ALTER SEQUENCE pb2_pcm_req_committee_hdr_id_seq OWNED BY pb2_pcm_req_committee_hdr.id;


--
-- TOC entry 375 (class 1259 OID 72664)
-- Name: pb2_pcm_req_dtl; Type: TABLE; Schema: public; Owner: alfresco
--

CREATE TABLE pb2_pcm_req_dtl (
    id bigint NOT NULL,
    master_id character varying(50) NOT NULL,
    description text,
    quantity double precision,
    created_time timestamp with time zone DEFAULT statement_timestamp(),
    created_by character varying(20),
    updated_time timestamp with time zone DEFAULT statement_timestamp(),
    updated_by character varying(20),
    price double precision,
    total double precision,
    unit_id integer,
    act_grp_id integer,
    fiscal_year integer
);


ALTER TABLE pb2_pcm_req_dtl OWNER TO alfresco;

--
-- TOC entry 3033 (class 0 OID 0)
-- Dependencies: 375
-- Name: COLUMN pb2_pcm_req_dtl.master_id; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_dtl.master_id IS 'PR Number';


--
-- TOC entry 3034 (class 0 OID 0)
-- Dependencies: 375
-- Name: COLUMN pb2_pcm_req_dtl.description; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_dtl.description IS 'Product';


--
-- TOC entry 3035 (class 0 OID 0)
-- Dependencies: 375
-- Name: COLUMN pb2_pcm_req_dtl.quantity; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_dtl.quantity IS 'Quantity';


--
-- TOC entry 3036 (class 0 OID 0)
-- Dependencies: 375
-- Name: COLUMN pb2_pcm_req_dtl.created_time; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_dtl.created_time IS 'Created on';


--
-- TOC entry 3037 (class 0 OID 0)
-- Dependencies: 375
-- Name: COLUMN pb2_pcm_req_dtl.created_by; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_dtl.created_by IS 'Created by';


--
-- TOC entry 3038 (class 0 OID 0)
-- Dependencies: 375
-- Name: COLUMN pb2_pcm_req_dtl.updated_time; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_dtl.updated_time IS 'Last Updated on';


--
-- TOC entry 3039 (class 0 OID 0)
-- Dependencies: 375
-- Name: COLUMN pb2_pcm_req_dtl.updated_by; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_dtl.updated_by IS 'Last Updated by';


--
-- TOC entry 3040 (class 0 OID 0)
-- Dependencies: 375
-- Name: COLUMN pb2_pcm_req_dtl.price; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_dtl.price IS 'Price';


--
-- TOC entry 3041 (class 0 OID 0)
-- Dependencies: 375
-- Name: COLUMN pb2_pcm_req_dtl.total; Type: COMMENT; Schema: public; Owner: alfresco
--

COMMENT ON COLUMN pb2_pcm_req_dtl.total IS 'Total';


--
-- TOC entry 376 (class 1259 OID 72672)
-- Name: pb2_pcm_req_dtl_id_seq; Type: SEQUENCE; Schema: public; Owner: alfresco
--

CREATE SEQUENCE pb2_pcm_req_dtl_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pb2_pcm_req_dtl_id_seq OWNER TO alfresco;

--
-- TOC entry 3042 (class 0 OID 0)
-- Dependencies: 376
-- Name: pb2_pcm_req_dtl_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alfresco
--

ALTER SEQUENCE pb2_pcm_req_dtl_id_seq OWNED BY pb2_pcm_req_dtl.id;


--
-- TOC entry 2773 (class 2604 OID 164748)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_exp_brw_dtl ALTER COLUMN id SET DEFAULT nextval('pb2_exp_brw_dtl_id_seq'::regclass);


--
-- TOC entry 2737 (class 2604 OID 72674)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_complete_notification ALTER COLUMN id SET DEFAULT nextval('pb2_main_complete_notification_id_seq'::regclass);


--
-- TOC entry 2759 (class 2604 OID 164096)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_master ALTER COLUMN id SET DEFAULT nextval('pb2_main_master_id_seq'::regclass);


--
-- TOC entry 2738 (class 2604 OID 72676)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_workflow ALTER COLUMN id SET DEFAULT nextval('pb2_main_workflow_id_seq'::regclass);


--
-- TOC entry 2739 (class 2604 OID 72677)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_workflow_history ALTER COLUMN id SET DEFAULT nextval('pb2_main_workflow_history_id_seq'::regclass);


--
-- TOC entry 2740 (class 2604 OID 72678)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_workflow_next_actor ALTER COLUMN id SET DEFAULT nextval('pb2_main_workflow_next_actor_id_seq'::regclass);


--
-- TOC entry 2741 (class 2604 OID 72679)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_workflow_reviewer ALTER COLUMN id SET DEFAULT nextval('pb2_main_workflow_reviewer_id_seq'::regclass);


--
-- TOC entry 2748 (class 2604 OID 72680)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_req_committee_dtl ALTER COLUMN id SET DEFAULT nextval('pb2_pcm_req_committee_dtl_id_seq'::regclass);


--
-- TOC entry 2751 (class 2604 OID 72681)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_req_committee_hdr ALTER COLUMN id SET DEFAULT nextval('pb2_pcm_req_committee_hdr_id_seq'::regclass);


--
-- TOC entry 2754 (class 2604 OID 72682)
-- Name: id; Type: DEFAULT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_req_dtl ALTER COLUMN id SET DEFAULT nextval('pb2_pcm_req_dtl_id_seq'::regclass);


--
-- TOC entry 2966 (class 0 OID 164114)
-- Dependencies: 387
-- Data for Name: pb2_exp_brw; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_exp_brw (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, bank_type, date_back, bank, fund_id) VALUES ('AV17000292', 100000, 'workspace://SpacesStore/2df773ce-b6ab-436a-9d7b-003b94a59441', 'workspace://SpacesStore/6ed1b464-4299-4bdb-84cf-127510988474', 'activiti$445829', 4, 'C2', '001509', '2016-11-01 10:59:45.394881+07', '002648', '2016-11-01 11:18:21.579718+07', '002648', 'attend_seminar', 'Odoo 2016', 'ติดภารกิจ จะรีบเคลียร์ภายในวันพรุ่งนี้', 'U', 59, 2, 207, '0', '2016-12-04 00:00:00+07', NULL, 1);
INSERT INTO pb2_exp_brw (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, bank_type, date_back, bank, fund_id) VALUES ('AV17000289', 30000, 'workspace://SpacesStore/8e31dcae-a7b7-4a03-b5a2-da511ed8d6e3', 'workspace://SpacesStore/5aa98c86-59ff-42f6-b3d2-fdc1eadfe301', 'activiti$444660', 4, 'C2', '002648', '2016-10-29 19:38:47.421071+07', '002648', '2016-10-29 19:45:03.094587+07', '002648', 'buy_product', 'ยืมเงินเพื่อทดสอบทำรายการเคลียร์เงินยืม', 'ใกล้จะคีนแล้วค่ะ', 'U', 59, NULL, NULL, '0', NULL, NULL, 1);
INSERT INTO pb2_exp_brw (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, bank_type, date_back, bank, fund_id) VALUES ('AV17000291', 30000, 'workspace://SpacesStore/6b1bd0d2-7797-411a-b033-12cb54eec662', NULL, NULL, NULL, 'D', '002648', '2016-11-01 07:54:20.41293+07', '002648', '2016-11-01 07:54:20.41293+07', '002648', 'buy_product', 'ยืมเงินเพื่อทดสอบทำรายการเคลียร์เงินยืม', 'ใกล้จะคีนแล้วค่ะ', 'U', 59, NULL, NULL, '0', NULL, NULL, 1);
INSERT INTO pb2_exp_brw (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, bank_type, date_back, bank, fund_id) VALUES ('AV17000287', 15000, 'workspace://SpacesStore/c00882ff-2e96-48b8-a8c1-f327ec159619', 'workspace://SpacesStore/4c6522bb-fc81-4f13-b12c-8759c8dd7214', 'activiti$439246', 4, 'C2', '002648', '2016-10-24 17:46:42.605361+07', '002648', '2016-10-24 17:50:23.98425+07', '002648', 'buy_product', 'ใช้งบหน่วยงานตัวเอง', 'ใกล้จะคีนแล้วค่ะ', 'U', 59, NULL, NULL, '0', NULL, NULL, 1);
INSERT INTO pb2_exp_brw (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, bank_type, date_back, bank, fund_id) VALUES ('AV17000293', 100000, 'workspace://SpacesStore/cb6f5e54-a4ab-4349-88cc-6b95058f3eb2', 'workspace://SpacesStore/b94abd5f-22b9-4cc6-8c64-629944db757e', 'activiti$446049', 2, 'W1', '002648', '2016-11-01 11:05:53.254467+07', '002648', '2016-11-01 11:13:41.244791+07', '002648', 'attend_seminar', 'Odoo 2016', 'ติดภารกิจ จะรีบเคลียร์ภายในวันพรุ่งนี้', 'U', 59, 2, 207, '0', '2016-12-04 00:00:00+07', NULL, 1);
INSERT INTO pb2_exp_brw (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, bank_type, date_back, bank, fund_id) VALUES ('AV17000288', 15000, 'workspace://SpacesStore/5099c1e0-b98c-4215-b63c-971ac91aa661', 'workspace://SpacesStore/3dff7b2a-e8c9-4093-8e3b-fe013e70d679', 'activiti$441655', 4, 'X2', '002648', '2016-10-24 17:52:40.76309+07', '002648', '2016-10-25 09:08:27.159056+07', '002648', 'buy_product', 'ใช้งบหน่วยงานอื่น', 'ใกล้จะคีนแล้วค่ะ', 'U', 60, NULL, NULL, '0', NULL, NULL, 1);
INSERT INTO pb2_exp_brw (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, bank_type, date_back, bank, fund_id) VALUES ('AV17000290', 15000, 'workspace://SpacesStore/342deab8-1dc7-41f8-b57d-a86bb0097cf7', 'workspace://SpacesStore/e5ddaf76-bd3c-4f27-81d3-0d8b4adabd2b', 'activiti$445429', 4, 'C2', '002648', '2016-10-29 19:51:25.294247+07', '002648', '2016-11-01 07:58:15.670177+07', '002648', 'buy_product', 'ยืมเงินเพื่อซื้อของที่ระลึก', 'ใกล้จะคีนแล้วค่ะ', 'U', 59, NULL, NULL, '0', NULL, NULL, 1);


--
-- TOC entry 2967 (class 0 OID 164124)
-- Dependencies: 388
-- Data for Name: pb2_exp_brw_attendee; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (505, 'AV17000287', '004026', '2016-10-24 17:46:42.607796+07', '002648', '2016-10-24 17:46:42.607796+07', '002648', 'ฐาปณัฐ', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'โสภณ', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (506, 'AV17000287', '004783', '2016-10-24 17:46:42.609497+07', '002648', '2016-10-24 17:46:42.609497+07', '002648', 'ภูริเดช', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'โพธิ์พิพัฒน์', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (509, 'AV17000288', '004026', '2016-10-25 09:05:55.950264+07', '002648', '2016-10-25 09:05:55.950264+07', '002648', 'ฐาปณัฐ', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'โสภณ', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (510, 'AV17000288', '004783', '2016-10-25 09:05:55.951193+07', '002648', '2016-10-25 09:05:55.951193+07', '002648', 'ภูริเดช', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'โพธิ์พิพัฒน์', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (513, 'AV17000289', '004026', '2016-10-29 19:42:36.358527+07', '002648', '2016-10-29 19:42:36.358527+07', '002648', 'ฐาปณัฐ', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'โสภณ', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (514, 'AV17000289', '004783', '2016-10-29 19:42:36.359509+07', '002648', '2016-10-29 19:42:36.359509+07', '002648', 'ภูริเดช', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'โพธิ์พิพัฒน์', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (515, 'AV17000289', '', '2016-10-29 19:42:36.360038+07', '002648', '2016-10-29 19:42:36.360038+07', '002648', 'คนนอก', 'Ecosoft', 'ที่ปรึกษา', 'O', NULL, 'สวทช', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (519, 'AV17000291', '004026', '2016-11-01 07:54:20.470784+07', '002648', '2016-11-01 07:54:20.470784+07', '002648', 'ฐาปณัฐ', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'โสภณ', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (520, 'AV17000291', '004783', '2016-11-01 07:54:20.472095+07', '002648', '2016-11-01 07:54:20.472095+07', '002648', 'ภูริเดช', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'โพธิ์พิพัฒน์', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (521, 'AV17000291', '', '2016-11-01 07:54:20.472623+07', '002648', '2016-11-01 07:54:20.472623+07', '002648', 'คนนอก', 'Ecosoft', 'ที่ปรึกษา', 'O', NULL, 'สวทช', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (522, 'AV17000290', '004026', '2016-11-01 07:55:14.435311+07', '002648', '2016-11-01 07:55:14.435311+07', '002648', 'ฐาปณัฐ', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'โสภณ', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (523, 'AV17000290', '004783', '2016-11-01 07:55:14.43613+07', '002648', '2016-11-01 07:55:14.43613+07', '002648', 'ภูริเดช', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'โพธิ์พิพัฒน์', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (524, 'AV17000290', '', '2016-11-01 07:55:14.43664+07', '002648', '2016-11-01 07:55:14.43664+07', '002648', 'คนนอก', '', 'ที่ปรึกษา', 'O', NULL, 'สวทช', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (528, 'AV17000292', '001509', '2016-11-01 11:00:07.148993+07', '002648', '2016-11-01 11:00:07.148993+07', '002648', 'เมทินี', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'รองผู้อำนวยการฝ่าย ', 'E', 52, 'ประภาประไพ', 'นางสาว');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (529, 'AV17000292', '002390', '2016-11-01 11:00:07.150131+07', '002648', '2016-11-01 11:00:07.150131+07', '002648', 'สรรชัย', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'นิจสุนกิจ', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (530, 'AV17000292', '', '2016-11-01 11:00:07.150773+07', '002648', '2016-11-01 11:00:07.150773+07', '002648', 'เล็ก', 'Ecosoft', 'Consultant', 'O', NULL, 'ปปปป', 'นางสาว');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (537, 'AV17000293', '001509', '2016-11-01 11:06:47.804961+07', '002648', '2016-11-01 11:06:47.804961+07', '002648', 'เมทินี', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'รองผู้อำนวยการฝ่าย ', 'E', 52, 'ประภาประไพ', 'นางสาว');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (538, 'AV17000293', '002390', '2016-11-01 11:06:47.806002+07', '002648', '2016-11-01 11:06:47.806002+07', '002648', 'สรรชัย', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', 55, 'นิจสุนกิจ', 'นาย');
INSERT INTO pb2_exp_brw_attendee (id, master_id, code, created_time, created_by, updated_time, updated_by, fname, unit_type, "position", type, position_id, lname, title) VALUES (539, 'AV17000293', '', '2016-11-01 11:06:47.806616+07', '002648', '2016-11-01 11:06:47.806616+07', '002648', 'เล็ก', '', 'Consultant', 'O', NULL, 'ปปปป', 'นางสาว');


--
-- TOC entry 2972 (class 0 OID 164745)
-- Dependencies: 398
-- Data for Name: pb2_exp_brw_dtl; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (894, 'AV17000287', 'ค่าวัสดุ ', 15000, '2016-10-24 17:46:42.606673+07', '002648', '2016-10-24 17:46:42.606673+07', '002648', 14, 378, '');
INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (896, 'AV17000288', 'ค่าวัสดุ ', 15000, '2016-10-25 09:05:55.949445+07', '002648', '2016-10-25 09:05:55.949445+07', '002648', 14, 378, '');
INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (898, 'AV17000289', 'ค่าวัสดุ ', 15000, '2016-10-29 19:42:36.357162+07', '002648', '2016-10-29 19:42:36.357162+07', '002648', 14, 378, '');
INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (899, 'AV17000289', 'ไปไป กลับกลับ', 15000, '2016-10-29 19:42:36.358064+07', '002648', '2016-10-29 19:42:36.358064+07', '002648', 11, 297, '');
INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (902, 'AV17000291', 'ค่าวัสดุ ', 15000, '2016-11-01 07:54:20.466292+07', '002648', '2016-11-01 07:54:20.466292+07', '002648', 14, 378, '');
INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (903, 'AV17000291', 'ไปไป กลับกลับ', 15000, '2016-11-01 07:54:20.467479+07', '002648', '2016-11-01 07:54:20.467479+07', '002648', 11, 297, '');
INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (904, 'AV17000290', 'ค่าวัสดุ ', 15000, '2016-11-01 07:55:14.434841+07', '002648', '2016-11-01 07:55:14.434841+07', '002648', 14, 378, '');
INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (907, 'AV17000292', '4 วัน 3 คืน 2 ห้อง 3 คน', 50000, '2016-11-01 11:00:07.147587+07', '002648', '2016-11-01 11:00:07.147587+07', '002648', 9, 198, 'ราชอาณาจักรเบลเยียม');
INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (908, 'AV17000292', 'กกกก', 50000, '2016-11-01 11:00:07.148467+07', '002648', '2016-11-01 11:00:07.148467+07', '002648', 9, 194, 'ราชอาณาจักรเบลเยียม');
INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (913, 'AV17000293', '4 วัน 3 คืน 2 ห้อง 3 คน', 50000, '2016-11-01 11:06:47.801779+07', '002648', '2016-11-01 11:06:47.801779+07', '002648', 9, 198, 'ราชอาณาจักรเบลเยียม');
INSERT INTO pb2_exp_brw_dtl (id, master_id, activity, amount, created_time, created_by, updated_time, updated_by, act_grp_id, act_id, condition_1) VALUES (914, 'AV17000293', 'กกกก', 50000, '2016-11-01 11:06:47.802255+07', '002648', '2016-11-01 11:06:47.802255+07', '002648', 9, 194, 'ราชอาณาจักรเบลเยียม');


--
-- TOC entry 3043 (class 0 OID 0)
-- Dependencies: 397
-- Name: pb2_exp_brw_dtl_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_exp_brw_dtl_id_seq', 914, true);


--
-- TOC entry 2968 (class 0 OID 164140)
-- Dependencies: 389
-- Data for Name: pb2_exp_use; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000384', 4500, 'workspace://SpacesStore/2f71c10d-5bf8-4ed6-b162-5f01973240e7', 'workspace://SpacesStore/6d39b1f1-ccb9-4f0d-af5d-8b676314b4cf', 'activiti$440036', NULL, 'X1', '001509', '2016-10-25 08:41:45.931994+07', '002648', '2016-10-25 08:43:13.633728+07', '002648', 'ทำแทนผู้ขอเบิก #3', 'U', 59, NULL, NULL, NULL, NULL, NULL, '', NULL, '1', 'Ecosoft', NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000383', 4500, 'workspace://SpacesStore/f3fedfc9-6aa9-45b1-b047-40b127189dbf', 'workspace://SpacesStore/601de05c-bd2c-4a19-bd89-dbf22805ccea', 'activiti$439942', NULL, 'X1', '002648', '2016-10-25 08:40:29.598409+07', '002648', '2016-10-25 08:43:33.778287+07', '002648', 'ทำแทนผู้ขอเบิก #2', 'U', 59, NULL, NULL, NULL, NULL, NULL, '', NULL, '1', 'Ecosoft', NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000380', 4500, 'workspace://SpacesStore/701b68cb-528b-43af-9f9b-f3191576206f', 'workspace://SpacesStore/8108c41d-4694-462f-89b0-5a0aeb3a3143', 'activiti$439571', 2, 'X2', '002648', '2016-10-24 17:55:26.439887+07', '002648', '2016-10-24 18:07:07.502318+07', '002648', 'ทดสอบเบิกใช้งบหน่วยงานตัวเอง', 'U', 59, NULL, NULL, NULL, NULL, NULL, '0', NULL, '0', NULL, NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000382', 4500, 'workspace://SpacesStore/0f6f42d0-5f95-4c46-a997-519827e99471', 'workspace://SpacesStore/a9f2e533-c5f2-4dc4-9537-87b10646a2a2', 'activiti$439848', NULL, 'X1', '001509', '2016-10-25 08:39:22.119092+07', '002648', '2016-10-25 08:44:01.77366+07', '002648', 'ทำแทนผู้ขอเบิก', 'U', 60, NULL, NULL, NULL, NULL, NULL, '', NULL, '1', 'Ecosoft', NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000392', 0, 'workspace://SpacesStore/cb2cbe01-a40b-4729-bfd6-3d20c75d7b0e', 'workspace://SpacesStore/11fe0ca1-fd0d-4d63-b58d-a930dbc9857f', 'activiti$445691', 2, 'C2', '002648', '2016-11-01 08:00:38.84602+07', '002648', '2016-11-01 08:02:50.422963+07', '002648', 'เคลียร์เงินยืมพนักงาน ซื้อของที่ระลึก', 'U', 59, NULL, NULL, NULL, NULL, NULL, '0', NULL, '2', 'AV17000290', NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000387', 225000, 'workspace://SpacesStore/e4f120a2-8ad2-4d1b-8cc8-d926ba24077f', 'workspace://SpacesStore/a30eefe8-0d07-41f0-a606-53d1c4690945', 'activiti$440883', NULL, 'X1', '000090', '2016-10-25 08:52:41.43516+07', '000090', '2016-10-25 09:03:37.146358+07', '000090', 'ผู้ขอเบิก ไม่เป็น PM + มีวงเงินพิเศษ ที่ไม่พอ (Edited Version)', 'P', 74, NULL, NULL, NULL, NULL, NULL, '0', NULL, '0', NULL, NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000385', 25000, 'workspace://SpacesStore/8d333774-ff49-4e68-af70-369e67ebe1a0', 'workspace://SpacesStore/bf959dfe-5ed2-4fc3-aa2c-e3f56fbdf70a', 'activiti$440485', NULL, 'X1', '000511', '2016-10-25 08:46:39.243836+07', '000511', '2016-10-25 08:47:37.680143+07', '000511', 'ผู้ขอเบิกเป็น PM', 'P', 74, NULL, NULL, NULL, NULL, NULL, '0', NULL, '0', NULL, NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000381', 4500, 'workspace://SpacesStore/534bab6d-e171-4298-86a2-f9d6f9e34a98', 'workspace://SpacesStore/fc7d2f50-b2d8-497a-a0b7-627550f6d26b', 'activiti$439709', 2, 'C2', '002648', '2016-10-24 18:09:30.53447+07', '002648', '2016-10-25 08:37:27.187239+07', '002648', 'ทดสอบเบิกใช้งบหน่วยงานอื่น', 'U', 60, NULL, NULL, NULL, NULL, NULL, '', NULL, '1', 'Ecosoft', NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000390', 15000, 'workspace://SpacesStore/dcaabbfc-7b0e-4a86-8f02-69fcb33bd9fe', 'workspace://SpacesStore/ecdf869f-5da8-4779-84d6-6ccd94b9feda', 'activiti$444401', 2, 'C2', '002648', '2016-10-28 17:19:37.474674+07', '002648', '2016-10-29 19:36:19.984554+07', '002648', 'ทดสอบเบิกจ่ายเพิ่มฟิลด์รายละเอียด', 'U', 59, NULL, NULL, NULL, NULL, NULL, '0', NULL, '0', NULL, NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000388', 225000, 'workspace://SpacesStore/ce083276-f8d0-4f5e-907f-232544b1ef5e', 'workspace://SpacesStore/1143b1bf-1721-48f3-be46-7eca317e2901', 'activiti$440977', NULL, 'X1', '000090', '2016-10-25 08:58:21.17586+07', '000090', '2016-10-25 08:59:36.786969+07', '000090', 'ผู้ขอเบิก ไม่เป็น PM + ไม่มีวงเงินพิเศษ', 'P', 74, NULL, NULL, NULL, NULL, NULL, '0', NULL, '0', NULL, NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000391', 15000, 'workspace://SpacesStore/474da23d-b8ec-4818-81ae-2ae41d41407a', NULL, NULL, NULL, 'D', '001509', '2016-10-29 19:36:41.889381+07', '001509', '2016-10-29 19:36:41.889381+07', '001509', 'ทดสอบเบิกจ่ายเพิ่มฟิลด์รายละเอียด', 'U', 59, NULL, NULL, NULL, NULL, NULL, '0', NULL, '0', NULL, NULL, NULL, 1, NULL);
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000393', 50000, 'workspace://SpacesStore/41dcc376-7eb6-43e9-9261-59b8719eb250', 'workspace://SpacesStore/10ec8ca8-9d7a-440b-bfae-2abe80b99315', 'activiti$446432', 1, 'W1', '001509', '2016-11-01 11:23:20.356091+07', '002648', '2016-11-01 11:23:22.364922+07', '002648', 'เคลียร์เงินยืม odoo 2016', 'U', 59, 2, 207, NULL, NULL, NULL, '0', NULL, '2', 'AV17000273', NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000389', 4500, 'workspace://SpacesStore/e62cf5e0-e03b-4392-ae10-5d8619986899', 'workspace://SpacesStore/737c4649-c6cf-4053-b840-98720e482092', 'activiti$442301', 1, 'W1', '002648', '2016-10-25 16:38:20.498161+07', '002648', '2016-10-29 20:08:21.273914+07', '002648', 'ทดสอบเบิก', 'U', 59, NULL, NULL, NULL, NULL, NULL, '', NULL, '4', NULL, NULL, NULL, 1, '');
INSERT INTO pb2_exp_use (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective, budget_cc_type, budget_cc, cost_control_type_id, cost_control_id, cost_control, cost_control_from, cost_control_to, bank_type, bank, pay_type, pay_dtl1, pay_dtl2, pay_dtl3, fund_id, reason) VALUES ('EX17000386', 25000, 'workspace://SpacesStore/4fc2c3b6-8075-4f65-9f95-1e53978e1173', 'workspace://SpacesStore/8192d0cb-1970-4c44-9527-bbdf14ebd875', 'activiti$440684', NULL, 'X1', '000090', '2016-10-25 08:50:33.310253+07', '000090', '2016-10-25 08:51:54.202867+07', '000090', 'ผู้ขอเบิก ไม่เป็น PM + มีวงเงินพิเศษ ที่พอ', 'P', 74, NULL, NULL, NULL, NULL, NULL, '0', NULL, '0', NULL, NULL, NULL, 1, '');


--
-- TOC entry 2970 (class 0 OID 164166)
-- Dependencies: 391
-- Data for Name: pb2_exp_use_attendee; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_exp_use_attendee (id, master_id, code, fname, unit_type, "position", type, created_time, created_by, updated_time, updated_by, position_id, lname, title) VALUES (1777, 'EX17000390', '004026', 'ฐาปณัฐ', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', '2016-10-29 19:34:00.585717+07', '002648', '2016-10-29 19:34:00.585717+07', '002648', 55, 'โสภณ', 'นาย');
INSERT INTO pb2_exp_use_attendee (id, master_id, code, fname, unit_type, "position", type, created_time, created_by, updated_time, updated_by, position_id, lname, title) VALUES (1778, 'EX17000390', '004783', 'ภูริเดช', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', '2016-10-29 19:34:00.587011+07', '002648', '2016-10-29 19:34:00.587011+07', '002648', 55, 'โพธิ์พิพัฒน์', 'นาย');
INSERT INTO pb2_exp_use_attendee (id, master_id, code, fname, unit_type, "position", type, created_time, created_by, updated_time, updated_by, position_id, lname, title) VALUES (1779, 'EX17000391', '004026', 'ฐาปณัฐ', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', '2016-10-29 19:36:41.892864+07', '001509', '2016-10-29 19:36:41.892864+07', '001509', 55, 'โสภณ', 'นาย');
INSERT INTO pb2_exp_use_attendee (id, master_id, code, fname, unit_type, "position", type, created_time, created_by, updated_time, updated_by, position_id, lname, title) VALUES (1780, 'EX17000391', '004783', 'ภูริเดช', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', '2016-10-29 19:36:41.893467+07', '001509', '2016-10-29 19:36:41.893467+07', '001509', 55, 'โพธิ์พิพัฒน์', 'นาย');
INSERT INTO pb2_exp_use_attendee (id, master_id, code, fname, unit_type, "position", type, created_time, created_by, updated_time, updated_by, position_id, lname, title) VALUES (1781, 'EX17000389', '001509', 'เมทินี', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'รองผู้อำนวยการฝ่าย ', 'E', '2016-10-29 20:08:21.276694+07', '002648', '2016-10-29 20:08:21.276694+07', '002648', 52, 'ประภาประไพ', 'นางสาว');
INSERT INTO pb2_exp_use_attendee (id, master_id, code, fname, unit_type, "position", type, created_time, created_by, updated_time, updated_by, position_id, lname, title) VALUES (1782, 'EX17000389', '001660', 'บดินทร์', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', '2016-10-29 20:08:21.277598+07', '002648', '2016-10-29 20:08:21.277598+07', '002648', 55, 'กิจศิริเจริญชัย', 'นาย');
INSERT INTO pb2_exp_use_attendee (id, master_id, code, fname, unit_type, "position", type, created_time, created_by, updated_time, updated_by, position_id, lname, title) VALUES (1783, 'EX17000389', '002390', 'สรรชัย', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', '2016-10-29 20:08:21.278239+07', '002648', '2016-10-29 20:08:21.278239+07', '002648', 55, 'นิจสุนกิจ', 'นาย');
INSERT INTO pb2_exp_use_attendee (id, master_id, code, fname, unit_type, "position", type, created_time, created_by, updated_time, updated_by, position_id, lname, title) VALUES (1784, 'EX17000392', '004026', 'ฐาปณัฐ', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', '2016-11-01 08:00:38.849054+07', '002648', '2016-11-01 08:00:38.849054+07', '002648', 55, 'โสภณ', 'นาย');
INSERT INTO pb2_exp_use_attendee (id, master_id, code, fname, unit_type, "position", type, created_time, created_by, updated_time, updated_by, position_id, lname, title) VALUES (1785, 'EX17000392', '004783', 'ภูริเดช', '[105015] งานบริหารระบบสารสนเทศเพื่อการจัดการ', 'วิศวกร', 'E', '2016-11-01 08:00:38.850127+07', '002648', '2016-11-01 08:00:38.850127+07', '002648', 55, 'โพธิ์พิพัฒน์', 'นาย');
INSERT INTO pb2_exp_use_attendee (id, master_id, code, fname, unit_type, "position", type, created_time, created_by, updated_time, updated_by, position_id, lname, title) VALUES (1786, 'EX17000392', '', 'คนนอก', '', 'ที่ปรึกษา', 'O', '2016-11-01 08:00:38.850688+07', '002648', '2016-11-01 08:00:38.850688+07', '002648', NULL, 'สวทช', 'นาย');


--
-- TOC entry 2969 (class 0 OID 164150)
-- Dependencies: 390
-- Data for Name: pb2_exp_use_dtl; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (878, 'EX17000380', '2016-10-24 17:55:26.441246+07', '002648', '2016-10-24 17:55:26.441246+07', '002648', '', 4500, '', '', '', 480, 34, NULL);
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (882, 'EX17000381', '2016-10-25 08:35:41.085029+07', '002648', '2016-10-25 08:35:41.085029+07', '002648', '', 4500, '', '', '', 480, 34, NULL);
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (884, 'EX17000382', '2016-10-25 08:40:11.751969+07', '002648', '2016-10-25 08:40:11.751969+07', '002648', '', 4500, '', '', '', 480, 34, NULL);
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (886, 'EX17000383', '2016-10-25 08:41:02.001133+07', '002648', '2016-10-25 08:41:02.001133+07', '002648', '', 4500, '', '', '', 480, 34, NULL);
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (888, 'EX17000384', '2016-10-25 08:42:12.301008+07', '002648', '2016-10-25 08:42:12.301008+07', '002648', '', 4500, '', '', '', 480, 34, NULL);
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (890, 'EX17000385', '2016-10-25 08:46:48.041552+07', '000511', '2016-10-25 08:46:48.041552+07', '000511', '', 25000, '', '', '', 237, 10, NULL);
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (892, 'EX17000386', '2016-10-25 08:51:02.489106+07', '000090', '2016-10-25 08:51:02.489106+07', '000090', '', 25000, '', '', '', 237, 10, NULL);
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (896, 'EX17000388', '2016-10-25 08:58:49.962615+07', '000090', '2016-10-25 08:58:49.962615+07', '000090', '', 225000, '', '', '', 237, 10, NULL);
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (897, 'EX17000387', '2016-10-25 09:00:35.514869+07', '000090', '2016-10-25 09:00:35.514869+07', '000090', '', 225000, '', '', '', 237, 10, NULL);
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (901, 'EX17000390', '2016-10-29 19:34:00.584169+07', '002648', '2016-10-29 19:34:00.584169+07', '002648', '', 15000, '', '', '', 378, 14, 'ค่าวัสดุใช้ในการประชุมหารือปฏิบัติงาน');
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (902, 'EX17000391', '2016-10-29 19:36:41.986631+07', '001509', '2016-10-29 19:36:41.986631+07', '001509', '', 15000, '', '', '', 378, 14, 'ค่าวัสดุใช้ในการประชุมหารือปฏิบัติงาน');
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (903, 'EX17000389', '2016-10-29 20:08:21.275606+07', '002648', '2016-10-29 20:08:21.275606+07', '002648', '', 4500, '', '', '', 480, 34, 'ค่าส่งเสริมการขายจ่ายเบ็ดเตล็ด');
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (904, 'EX17000392', '2016-11-01 08:00:38.847362+07', '002648', '2016-11-01 08:00:38.847362+07', '002648', '', 15000, '', '', '', 378, 14, 'ค่าวัสดุ ');
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (905, 'EX17000393', '2016-11-01 11:23:20.357031+07', '002648', '2016-11-01 11:23:20.357031+07', '002648', 'ราชอาณาจักรเบลเยียม', 40000, '', '', '', 198, 9, 'ห้อง คนื กดกดก');
INSERT INTO pb2_exp_use_dtl (id, master_id, created_time, created_by, updated_time, updated_by, condition_1, amount, condition_2, "position", uom, act_id, act_grp_id, activity) VALUES (906, 'EX17000393', '2016-11-01 11:23:20.358083+07', '002648', '2016-11-01 11:23:20.358083+07', '002648', 'เครือรัฐโดมินิกา', 10000, '', '', '', 194, 9, 'กกกก');


--
-- TOC entry 2944 (class 0 OID 72581)
-- Dependencies: 358
-- Data for Name: pb2_main_complete_notification; Type: TABLE DATA; Schema: public; Owner: alfresco
--



--
-- TOC entry 3044 (class 0 OID 0)
-- Dependencies: 359
-- Name: pb2_main_complete_notification_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_main_complete_notification_id_seq', 1, false);


--
-- TOC entry 2964 (class 0 OID 164086)
-- Dependencies: 380
-- Data for Name: pb2_main_master; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (233, 'A', 'SYSTEM', 'System Config', true, 'กำหนดค่า', '', NULL, NULL, NULL, '2015-05-03 17:50:14.6244+07', NULL, '2015-05-03 17:50:14.6244+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (110, 'SYSTEM', 'PCM_REQ_CRITERIA_1', 'Pcm Req Criteria 1', true, 'Purchase Type', 'objective_type,200,200', 'main/master?orderBy=flag1', 'type=''PC''', '', '2015-05-20 10:35:54.838422+07', 'admin', '2015-05-20 10:35:54.838422+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (111, 'SYSTEM', 'PCM_REQ_CRITERIA_2', 'Pcm Req Criteria 2', true, 'Status', 'status', 'main/master?orderBy=flag1', 'type=''PR_ST''', '', '2015-05-20 10:35:54.838422+07', 'admin', '2015-05-20 10:35:54.838422+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (499, 'A', 'MP', 'แบบฟอร์มราคากลาง', true, '', '', '', '', '', '2016-04-02 18:40:43.806146+07', 'admin', '2016-04-02 18:40:43.806146+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (170, 'SYSTEM', 'PCM_REQ_MAIL_NOTIFY', 'Pcm Req Mail Notify', true, '0', '', '', '', '', '2015-08-26 15:33:59.285866+07', 'admin', '2015-08-26 15:33:59.285866+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (505, 'A', 'PTT', 'ครุภัณฑ์ต้นแบบ', true, '', '', '', '', '', '2016-04-04 13:58:41.291261+07', 'admin', '2016-04-04 13:58:41.291261+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (171, 'SYSTEM', 'PCM_REQ_MAIL_TEMPLATE', 'Pcm Req Mail Template', true, 'workspace://SpacesStore/33126264-7462-4b99-962c-cb3c9745a78b', '', '', '', '', '2015-08-26 15:36:13.883887+07', 'admin', '2015-08-26 15:36:13.883887+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (172, 'SYSTEM', 'PCM_REQ_MAIL_SUBJECT', 'Pcm Req Mail Subject', true, 'กรุณาอนุมัติใบบันทึก (Memo) เลขที่ ${memoId}', '', '', '', '', '2015-08-26 15:36:48.066462+07', 'admin', '2015-08-26 15:36:48.066462+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (173, 'SYSTEM', 'PCM_REQ_MAIL_FROM', 'Pcm Req Mail From Label', true, 'Localhost', '', '', '', '', '2015-08-26 15:37:20.17415+07', 'admin', '2015-08-26 15:37:20.17415+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (491, 'A', 'TR', 'จัดฝึกอบรม', true, '', '', '', '', '', '2016-04-02 18:30:17.735904+07', 'admin', '2016-04-02 18:30:17.735904+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (201, 'SYSTEM', 'PCM_ORD_PATH_FORMAT', 'Pcm Order Path Format', true, 'PD/${fiscal_year[yyyy]}/${id}', '', '', '', '', '2016-03-19 18:16:44.558704+07', 'admin', '2016-03-19 18:16:44.558704+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (228, 'A', 'A', 'Master Type', true, 'ประเภทข้อมูลหลัก', '', NULL, NULL, NULL, '2015-04-28 18:50:30.125156+07', NULL, '2015-04-28 18:50:30.125156+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (544, 'SYSTEM', 'MAIN_RESET_SEQUENCE', 'Main Reset Sequence Names', true, 'pb2_pcm_req_id_seq', '', '', '', '', '2016-05-27 10:14:33.078138+07', 'admin', '2016-05-27 10:14:33.078138+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (533, 'A', 'PD_ST', 'PD Workflow Status', true, 'สถานะ Workflow PD', '', '', '', '', '2016-05-05 16:47:38.031627+07', 'admin', '2016-05-05 16:47:38.031627+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (213, 'A', 'PR_ST', 'PR Workflow Status', true, 'สถานะ Workflow PR', '', '', '', '', '2015-03-30 09:17:50.759+07', NULL, '2015-03-30 09:17:50.759+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (531, 'SYSTEM', 'PCM_ORD_CRITERIA_1', 'Pcm Order Criteria 1', true, 'Method', 'doc_type,500,500', 'main/master?orderBy=flag1', 'type=''PD''', '', '2016-05-05 15:38:29.529432+07', 'admin', '2016-05-05 15:38:29.529432+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (545, 'SYSTEM', 'EXP_BRW_RPT_TAB', 'Expense Borrow Report Tab', true, '0', '', '', '', '', '2016-05-28 14:03:24.156682+07', 'admin', '2016-05-28 14:03:24.156682+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (100, 'SYSTEM', 'PCM_REQ_SITE_ID', 'Pcm Req Site ID', true, 'pcm', '', '', '', '', '2016-03-01 18:21:46.282169+07', 'admin', '2016-03-01 18:21:46.282169+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (102, 'SYSTEM', 'PCM_REQ_ID_FORMAT', 'Pcm Req ID Format', true, 'PR${fiscal_year?["yy"]}${running_no?["000000"]}', '', '', '', '', '2016-03-01 17:52:55.201313+07', 'admin', '2016-03-01 17:52:55.201313+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (200, 'SYSTEM', 'PCM_ORD_SITE_ID', 'Pcm Order Site ID', true, 'pcm', '', '', '', '', '2016-03-19 18:22:24.096585+07', 'admin', '2016-03-19 18:22:24.096585+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (1, 'SYSTEM', 'MAIN_PAGING_SIZE', 'Main Paging Size', true, '40', '', '', '', '', '2015-05-21 15:32:24.553387+07', 'admin', '2015-05-21 15:32:24.553387+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (2, 'SYSTEM', 'MAIN_TEMP_PATH', 'Main Temp Path', true, 'workspace://SpacesStore/71ce6549-63b7-454c-96c1-5f98ae3e1b50', '', '', '', '', '2015-05-27 18:02:54.070201+07', 'admin', '2015-05-27 18:02:54.070201+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (3, 'SYSTEM', 'MAIN_SIGNATURE_PATH', 'Main Signature Path', true, 'workspace://SpacesStore/d3c83cb5-a3e5-47ec-9005-89dd5dc2103a', '', '', '', '', '2015-05-10 16:00:30.913119+07', 'admin', '2015-05-10 16:00:30.913119+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (101, 'SYSTEM', 'PCM_REQ_PATH_FORMAT', 'Pcm Req Path Format', true, 'PR/${fiscal_year[yyyy]}/${id}', '${objective_type}/${fiscal_year[yyyy]}/${id}', '', '', '', '2016-03-01 17:55:04.196065+07', 'admin', '2016-03-01 17:55:04.196065+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (500, 'MP', 'MP1', 'งานจ้างก่อสร้าง', true, 'workspace://SpacesStore/e3911456-981b-4ab4-ba19-4415ade8dfdf', '', '', '', '', '2016-04-02 20:18:42.320346+07', 'admin', '2016-04-02 20:18:42.320346+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (501, 'MP', 'MP2', 'การจ้างควบคุมงาน', true, 'workspace://SpacesStore/7808ad78-e4ab-4ab4-926b-b5be87ac91d9', '', '', '', '', '2016-04-02 20:18:56.956525+07', 'admin', '2016-04-02 20:18:56.956525+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (512, 'MP', 'MP4', 'การจ้างพัฒนาระบบคอมพิวเตอร์', true, 'workspace://SpacesStore/6c148cca-6889-4038-ad49-a4ca9acd67ab', '', '', '', '', '2016-04-05 08:14:16.789411+07', 'admin', '2016-04-05 08:14:16.789411+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (513, 'MP', 'MP5', 'การจ้างออกแบบ', true, 'workspace://SpacesStore/2d5addb6-a45f-4d68-bd42-a4a5aefb4622', '', '', '', '', '2016-04-05 08:14:34.15099+07', 'admin', '2016-04-05 08:14:34.15099+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (514, 'MP', 'MP6', 'การจ้างงานวิจัยหรือเงินสนับสนุนให้ทุนการวิจัย', true, 'workspace://SpacesStore/c30a371e-fe6a-4c76-a674-4cc074b0dc54', '', '', '', '', '2016-04-05 08:14:49.938248+07', 'admin', '2016-04-05 08:14:49.938248+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (150, 'SYSTEM', 'PCM_REQ_SEARCH_GRID_ORDER_BY', 'Pcm Req Search Grid Order by', true, 'ORDER_FIELD, updated_time DESC', '', '', '', '', '2016-01-07 12:24:43.579596+07', 'admin', '2016-01-07 12:24:43.579596+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (506, 'PTT', 'PTT1', 'ต้นแบบโครงการ (วิจัย)', true, '1', 'ต้นแบบโครงการ (วิจัย)', '', '', '', '2016-04-04 13:59:11.691689+07', 'admin', '2016-04-04 13:59:11.691689+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (220, 'SYSTEM', 'PCM_ORD_GRID_FIELD_2', 'Pcm Order Grid Field 2', true, 'org', 'org_name', '', '', '', '2016-03-14 13:24:41.634559+07', 'admin', '2016-03-14 13:24:41.634559+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (546, 'SYSTEM', 'EXP_BRW_GRID_FIELD_1', 'Expense Borrow Grid Field 1', true, 'avNo', 'id,100,0', '', '', '', '2016-05-29 13:43:11.271047+07', 'admin', '2016-05-29 13:43:11.271047+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (532, 'SYSTEM', 'PCM_ORD_CRITERIA_2', 'Pcm Order Criteria 2', true, 'สถานะ', 'status', 'main/master?orderBy=flag1', 'type=''PD_ST''', '', '2016-05-05 16:45:36.718636+07', 'admin', '2016-05-05 16:45:36.718636+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (120, 'SYSTEM', 'PCM_REQ_GRID_FIELD_1', 'Pcm Request Grid Field 1', true, 'prNo', 'id,100,,0', '', '', '', '2016-03-24 13:49:37.508309+07', 'admin', '2016-03-24 13:49:37.508309+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (223, 'SYSTEM', 'PCM_ORD_GRID_FIELD_4', 'Pcm Order Grid Field 4', true, 'objective', 'objective', '', '', '', '2016-03-14 13:26:11.68346+07', 'admin', '2016-03-14 13:26:11.68346+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (540, 'SYSTEM', 'PCM_REQ_GRID_FIELD_8', 'Pcm Request Grid Field 8', true, 'preparer', 'created_by,80', '', '', '', '2016-05-08 16:38:51.070593+07', 'admin', '2016-05-08 16:38:51.070593+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (541, 'SYSTEM', 'PCM_ORD_GRID_FIELD_1', 'Pcm Order Grid Field 1', true, 'pdNo', 'id,150', '', '', '', '2016-05-10 10:42:23.863062+07', 'admin', '2016-05-10 10:42:23.863062+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (542, 'SYSTEM', 'PCM_ORD_GRID_FIELD_7', 'Pcm Order Grid Field 7', true, 'requestTime', 'created_time_show,130', '', '', '', '2016-05-10 10:44:31.582372+07', 'admin', '2016-05-10 10:44:31.582372+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (590, 'SYSTEM', 'EXP_USE_GRID_FIELD_3', 'Expense Use Grid Field 3', true, 'budget', 'budget_cc_name,250', '', '', '', '2016-06-07 15:39:16.454573+07', 'admin', '2016-06-07 15:39:16.454573+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (595, 'SYSTEM', 'EXP_USE_GRID_FIELD_8', 'Expense Use Grid Field 8', true, 'requestTime', 'created_time_show,120', '', '', '', '2016-06-07 15:42:50.411647+07', 'admin', '2016-06-07 15:42:50.411647+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (508, 'A', 'PR_RSN', 'PR Reason', true, '', '', '', '', '', '2016-04-04 15:08:56.891023+07', 'admin', '2016-04-04 15:08:56.891023+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (203, 'PR_ST', 'D', 'Draft', true, '1', 'Draft', '', '', '', '2015-03-26 10:54:24.7+07', NULL, '2015-03-26 10:54:24.7+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (204, 'PR_ST', 'W1', 'รอการอนุมัติ', true, '3', 'Wait for Approval', '', '', '', '2015-03-26 10:54:42.32+07', NULL, '2015-03-26 10:54:42.32+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (205, 'PR_ST', 'C1', 'รอพัสดุรับงาน', true, '5', 'Wait for Acceptance', '', '', '', '2015-03-26 10:55:15.436+07', NULL, '2015-03-26 10:55:15.436+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (206, 'PR_ST', 'C2', 'พัสดุรับงาน', true, '6', 'Accepted', '', '', '', '2015-03-26 10:55:24.561+07', NULL, '2015-03-26 10:55:24.561+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (503, 'PR_ST', 'X1', 'ยกเลิกโดย ผู้ขอ', true, '7', 'Cancelled by Requester', '', '', '', '2016-04-02 21:10:44.023056+07', 'admin', '2016-04-02 21:10:44.023056+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (534, 'PD_ST', 'D', 'Draft', true, '1', 'Draft', '', '', '', '2016-05-05 16:50:04.098353+07', 'admin', '2016-05-05 16:50:04.098353+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (539, 'PD_ST', 'X1', 'ยกเลิกโดย พัสดุ', true, '6', 'Cancelled By Procurement', '', '', '', '2016-05-05 16:52:28.490405+07', 'admin', '2016-05-05 16:52:28.490405+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (225, 'SYSTEM', 'PCM_ORD_GRID_FIELD_6', 'Pcm Order Grid Field 6', true, 'preparer', 'created_by,80', '', '', '', '2016-03-14 13:28:16.89383+07', 'admin', '2016-03-14 13:28:16.89383+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (507, 'PTT', 'PTT2', 'ต้นแบบส่งมอบ', true, '2', 'ต้นแบบส่งมอบ', '', '', '', '2016-04-04 13:59:25.448892+07', 'admin', '2016-04-04 13:59:25.448892+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (151, 'SYSTEM', 'PCM_REQ_MSG_MISSING_NEXT_APP', 'Pcm Req Message Missing Next Approver', true, 'Next Approver', '', '', '', '', '2016-02-12 16:58:36.218211+07', 'admin', '2016-02-12 16:58:36.218211+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (152, 'SYSTEM', 'PCM_REQ_WF_FAIL_COND_1', 'Pcm Req Fail Condition 1', true, '2,field3=''IC''|field9!=''true'',Temporary Fix', '', '', '', '', '2016-02-15 13:05:22.242698+07', 'admin', '2016-02-15 13:05:22.242698+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (492, 'A', 'PU', 'หน่วยงานจัดซื้อจัดจ้าง', true, '', '', '', '', '', '2016-04-02 18:30:42.678997+07', 'admin', '2016-04-02 18:30:42.678997+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (375, 'R', 'R02', 'IC - สรุปสถิติ แยกตามทีม', true, 'IC', 'R02 Title', '', '', '', '2015-12-08 14:56:00.132702+07', 'walai', '2015-12-08 14:56:00.132702+07', 'walai');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (376, 'R', 'R03', 'IC - สรุปสถิติ แยกระดับผลกระทบ', true, 'IC', 'R03 Title', '', '', '', '2015-12-08 14:56:19.736133+07', 'walai', '2015-12-08 14:56:19.736133+07', 'walai');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (413, 'A', 'R', 'Report', true, '', '', '', '', '', '2016-02-04 11:17:35.663821+07', 'admin', '2016-02-04 11:17:35.663821+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (493, 'TR', 'TR1', 'อบรม 1', true, '', '', '', '', '', '2016-04-02 18:34:47.48904+07', 'admin', '2016-04-02 18:34:47.48904+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (494, 'TR', 'TR2', 'อบรม 2', true, '', '', '', '', '', '2016-04-02 18:35:06.077229+07', 'admin', '2016-04-02 18:35:06.077229+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (495, 'TR', 'TR3', 'อบรม 3', true, '', '', '', '', '', '2016-04-02 18:35:17.156982+07', 'admin', '2016-04-02 18:35:17.156982+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (454, 'FUND', 'C', 'Cost Center', true, '', '', '', '', '', '2016-02-26 19:19:51.48891+07', 'admin', '2016-02-26 19:19:51.48891+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (451, 'BA', '105014', '105014', true, '', '', '', '', '', '2016-02-26 19:19:03.012544+07', 'admin', '2016-02-26 19:19:03.012544+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (455, 'FUND', 'P', 'Project', true, '', '', '', '', '', '2016-02-26 19:20:48.172425+07', 'admin', '2016-02-26 19:20:48.172425+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (436, 'A', 'PC', 'Purchasing Type', true, 'PC', '', '', '', '', '2016-02-24 08:59:52.801248+07', 'admin', '2016-02-24 08:59:52.801248+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (452, 'BA', '105015', '105015', true, '', '', '', '', '', '2016-02-26 19:19:16.885364+07', 'admin', '2016-02-26 19:19:16.885364+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (453, 'BA', '105046', '105046', true, '', '', '', '', '', '2016-02-26 19:19:28.815388+07', 'admin', '2016-02-26 19:19:28.815388+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (556, 'A', 'BRW_TYPE', 'รายการยืมเงิน', true, '', '', '', '', '', '2016-05-30 11:43:06.286876+07', 'admin', '2016-05-30 11:43:06.286876+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (441, 'A', 'CUR', 'Currency', true, 'CUR', '', '', '', '', '2016-02-26 18:51:50.294962+07', 'admin', '2016-02-26 18:51:50.294962+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (442, 'CUR', 'THB', 'Thai Baht', true, '', '', '', '', '', '2016-02-26 18:52:15.503968+07', 'admin', '2016-02-26 18:52:15.503968+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (443, 'CUR', 'USD', 'U.S. Dollar', true, '', '', '', '', '', '2016-02-26 18:52:56.294799+07', 'admin', '2016-02-26 18:52:56.294799+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (444, 'A', 'STOCK', 'คลังจัดเก็บพัสดุ', true, '', '', '', '', '', '2016-02-26 19:16:36.851021+07', 'admin', '2016-02-26 19:16:36.851021+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (445, 'A', 'FUND', 'ศูนย์เงินทุน', true, '', '', '', '', '', '2016-02-26 19:16:57.893776+07', 'admin', '2016-02-26 19:16:57.893776+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (446, 'A', 'BA', 'หน่วยงาน', true, '', '', '', '', '', '2016-02-26 19:17:17.179928+07', 'admin', '2016-02-26 19:17:17.179928+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (448, 'STOCK', 'ST2', 'Biotec', true, '', '', '', '', '', '2016-02-26 19:17:57.239506+07', 'admin', '2016-02-26 19:17:57.239506+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (449, 'STOCK', 'ST3', 'Mtec', true, '', '', '', '', '', '2016-02-26 19:18:10.768522+07', 'admin', '2016-02-26 19:18:10.768522+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (450, 'STOCK', 'ST4', 'Nectec', true, '', '', '', '', '', '2016-02-26 19:18:31.676963+07', 'admin', '2016-02-26 19:18:31.676963+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (447, 'STOCK', 'ST1', 'Center', true, '', '', '', '', '', '2016-02-26 19:17:46.345281+07', 'admin', '2016-02-26 19:17:46.345281+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (543, 'SYSTEM', 'MAIN_INF_CHECK_BUDGET', 'Main Interface Check Budget', true, '0', '', '', '', '', '2016-05-26 17:01:53.789761+07', 'admin', '2016-05-26 17:01:53.789761+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (559, 'SYSTEM', 'EXP_BRW_ID_FORMAT', 'Expense Borrow ID Format', true, 'AV${fiscal_year?["yy"]}${running_no?["000000"]}', '', '', '', '', '2016-06-05 18:14:29.726702+07', 'admin', '2016-06-05 18:14:29.726702+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (563, 'SYSTEM', 'EXP_BRW_SITE_ID', 'Expense Borrow Site ID', true, 'pcm', '', '', '', '', '2016-06-05 18:21:00.63458+07', 'admin', '2016-06-05 18:21:00.63458+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (564, 'SYSTEM', 'EXP_USE_SITE_ID', 'Expense Use Site ID', true, 'pcm', '', '', '', '', '2016-06-05 18:21:16.866552+07', 'admin', '2016-06-05 18:21:16.866552+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (552, 'SYSTEM', 'EXP_BRW_CRITERIA_1', 'Expense Borrow Criteria 1', true, 'Type', 'objective_type', 'main/master?orderBy=flag1', 'type=''BRW_TYPE''', '', '2016-05-30 08:05:49.167603+07', 'admin', '2016-05-30 08:05:49.167603+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (551, 'SYSTEM', 'EXP_BRW_CRITERIA_2', 'Expense Borrow Criteria 2', true, 'Status', 'status', 'main/master?orderBy=flag1', 'type=''AV_ST''', '', '2016-05-29 15:27:28.804631+07', 'admin', '2016-05-29 15:27:28.804631+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (572, 'A', 'AV_ST', 'AV Workflow Status', true, 'สถานะ Workflow AV', '', '', '', '', '2016-06-07 12:27:23.289443+07', 'admin', '2016-06-07 12:27:23.289443+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (103, 'SYSTEM', 'PCM_REQ_WF_DESC_FORMAT', 'Pcm Req Workflow Description Format', true, '${id} » ${objective_type} ${objective} ${reason}', '', '', '', '', '2016-03-01 18:58:33.61801+07', 'admin', '2016-03-01 18:58:33.61801+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (456, 'A', 'PD', 'วิธีการจัดหา', true, '', '', '', '', '', '2016-02-27 11:34:37.423435+07', 'admin', '2016-02-27 11:34:37.423435+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (502, 'MP', 'MP3', 'การจ้างที่ปรึกษา', true, 'workspace://SpacesStore/a77fb4e1-1aca-47ab-a917-3e55416d5fdb', '', '', '', '', '2016-04-02 20:19:09.133286+07', 'admin', '2016-04-02 20:19:09.133286+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (515, 'MP', 'MP7', 'การจัดซื้อจัดจ้างที่มิใช่งานก่อสร้าง', true, 'workspace://SpacesStore/b2aa3d55-d09d-45a2-b6dd-e04bb21b8277', '', '', '', '', '2016-04-05 08:15:06.247067+07', 'admin', '2016-04-05 08:15:06.247067+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (224, 'SYSTEM', 'PCM_ORD_GRID_FIELD_5', 'Pcm Order Grid Field 5', true, 'total', 'total,,right', 'number', '', '', '2016-03-14 13:27:20.729331+07', 'admin', '2016-03-14 13:27:20.729331+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (562, 'SYSTEM', 'EXP_USE_PATH_FORMAT', 'Expense Use Path Format', true, 'EX/${fiscal_year[yyyy]}/${id}', '${objective_type}/${fiscal_year[yyyy]}/${id}', '', '', '', '2016-06-05 18:19:49.560886+07', 'admin', '2016-06-05 18:19:49.560886+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (207, 'SYSTEM', 'PCM_ORD_WF_DESC_FORMAT', 'Pcm Order Workflow Description Format', true, '${id} » ${objective}', '${id} » ${doc_type} » ${objective}', '', '', '', '2016-03-19 18:22:57.191277+07', 'admin', '2016-03-19 18:22:57.191277+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (124, 'SYSTEM', 'PCM_REQ_GRID_FIELD_5', 'Pcm Request Grid Field 5', true, 'amount', 'total,90,right', 'number', '', '', '2016-03-24 13:55:21.457562+07', 'admin', '2016-03-24 13:55:21.457562+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (566, 'SYSTEM', 'EXP_BRW_GRID_FIELD_3', 'Expense Borrow Grid Field 3', true, 'budget', 'budget_cc_name,250', '', '', '', '2016-06-07 09:30:00.139489+07', 'admin', '2016-06-07 09:30:00.139489+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (437, 'PC', 'ซื้อ', 'ซื้อ', true, '1', 'Buy', '', '', '', '2016-02-24 09:00:49.350937+07', 'admin', '2016-02-24 09:00:49.350937+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (438, 'PC', 'จ้าง', 'จ้าง', true, '2', 'Hire', '', '', '', '2016-02-24 09:01:07.075668+07', 'admin', '2016-02-24 09:01:07.075668+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (440, 'PC', 'เช่า', 'เช่า', true, '3', 'Rent', '', '', '', '2016-02-26 13:26:52.271699+07', 'admin', '2016-02-26 13:26:52.271699+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (504, 'PR_ST', 'X2', 'ยกเลิกโดย พัสดุ', true, '8', 'Cancelled by Procurement', '', '', '', '2016-04-02 21:11:04.270543+07', 'admin', '2016-04-02 21:11:04.270543+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (458, 'PD', 'PD2', 'วิธีสอบราคา', true, '2', 'วิธีสอบราคา - ราคาเกิน 300,000 บาท แต่ไม่เกิน 2,000,000 บาท', '', '', '', '2016-02-27 11:45:32.303578+07', 'admin', '2016-02-27 11:45:32.303578+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (549, 'SYSTEM', 'EXP_USE_GRID_FIELD_1', 'Expense Use Grid Field 1', true, 'exNo', 'id,100,0', '', '', '', '2016-05-29 15:05:32.188785+07', 'admin', '2016-05-29 15:05:32.188785+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (536, 'PD_ST', 'W2', 'ไม่อนุมัติ', true, '2', 'Rejected', '', '', '', '2016-05-05 16:50:49.434759+07', 'admin', '2016-05-05 16:50:49.434759+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (574, 'AV_ST', 'D', 'Draft', true, '1', 'Draft', '', '', '', '2016-06-07 12:29:28.341165+07', 'admin', '2016-06-07 12:29:28.341165+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (584, 'AP_ST', 'W1', 'รอการอนุมัติ', true, '3', 'Wait for Approval', '', '', '', '2016-06-07 13:01:56.423916+07', 'admin', '2016-06-07 13:01:56.423916+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (125, 'SYSTEM', 'PCM_REQ_GRID_FIELD_6', 'Pcm Request Grid Field 6', true, 'currency', 'currency,50', '', '', '', '2016-03-24 14:19:15.250307+07', 'admin', '2016-03-24 14:19:15.250307+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (457, 'PD', 'PD1', 'วิธีตกลงราคา', true, '1', 'วิธีตกลงราคา - ราคาไม่เกิน 300,000 บาท', '', '', '', '2016-02-27 11:35:24.518942+07', 'admin', '2016-02-27 11:35:24.518942+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (557, 'BRW_TYPE', 'buy_product', 'ยืมเพื่อซื้อวัสดุ/สินค้า', true, '1', 'For material/product', '', '', '', '2016-05-30 11:44:42.137594+07', 'admin', '2016-05-30 11:44:42.137594+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (517, 'PU', '34', 'พัสดุ - ศช.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (518, 'PU', '35', 'พัสดุ - ศว.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (519, 'PU', '36', 'พัสดุ - ศอ.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (520, 'PU', '37', 'พัสดุ - ศจ.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (521, 'PU', '38', 'พัสดุ - ศจ. (SWP)', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (522, 'PU', '39', 'พัสดุ - ศจ. (NNSTDA)', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (523, 'PU', '40', 'พัสดุ - ศน.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (516, 'PU', '33', 'พัสดุ - สก.', true, '', '', '', '', '', '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (573, 'A', 'AP_ST', 'AP Workflow Status', true, 'สถานะ Workflow AP', '', '', '', '', '2016-06-07 12:27:44.222844+07', 'admin', '2016-06-07 12:27:44.222844+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (548, 'SYSTEM', 'EXP_USE_RPT_TAB', 'Expense Use Report Tab', true, '0', '', '', '', '', '2016-05-29 15:05:04.615301+07', 'admin', '2016-05-29 15:05:04.615301+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (561, 'SYSTEM', 'EXP_BRW_PATH_FORMAT', 'Expense Borrow Path Format', true, 'AV/${fiscal_year[yyyy]}/${id}', '${objective_type}/${fiscal_year[yyyy]}/${id}', '', '', '', '2016-06-05 18:18:21.164077+07', 'admin', '2016-06-05 18:18:21.164077+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (597, 'SYSTEM', 'EXP_USE_CRITERIA_2', 'Expense Borrow Criteria 2', true, 'Status', 'status', 'main/master?orderBy=flag1', 'type=''AP_ST''', '', '2016-06-08 12:33:13.251551+07', 'admin', '2016-06-08 12:33:13.251551+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (598, 'A', 'EXP_TYPE', 'วิธีเบิกจ่าย/หักล้างค่าใช้จ่าย (เงินยืม)', true, '', '', '', '', '', '2016-06-09 14:07:33.066737+07', 'admin', '2016-06-09 14:07:33.066737+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (527, 'PR_ST', 'W2', 'ไม่อนุมัติ', true, '2', 'Rejected', '', '', '', '2016-04-20 17:01:33.729903+07', 'admin', '2016-04-20 17:01:33.729903+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (594, 'SYSTEM', 'EXP_USE_GRID_FIELD_7', 'Expense Use Grid Field 7', true, 'preparer', 'created_by,90', '', '', '', '2016-06-07 15:41:49.633299+07', 'admin', '2016-06-07 15:41:49.633299+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (570, 'SYSTEM', 'EXP_BRW_GRID_FIELD_7', 'Expense Borrow Grid Field 7', true, 'preparer', 'created_by,90', '', '', '', '2016-06-07 09:34:04.355433+07', 'admin', '2016-06-07 09:34:04.355433+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (571, 'SYSTEM', 'EXP_BRW_GRID_FIELD_8', 'Expense Borrow Grid Field 8', true, 'requestTime', 'created_time_show,90', '', '', '', '2016-06-07 09:34:38.318858+07', 'admin', '2016-06-07 09:34:38.318858+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (550, 'SYSTEM', 'EXP_USE_GRID_FIELD_2', 'Expense Use Grid Field 2', true, 'exType', 'pay_type_name,100', '', '', '', '2016-05-29 15:06:05.686669+07', 'admin', '2016-05-29 15:06:05.686669+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (565, 'SYSTEM', 'EXP_BRW_GRID_FIELD_2', 'Expense Borrow Grid Field 2', true, 'avType', 'objective_type_name,140,center', '', '', '', '2016-06-07 09:29:03.768615+07', 'admin', '2016-06-07 09:29:03.768615+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (568, 'SYSTEM', 'EXP_BRW_GRID_FIELD_5', 'Expense Borrow Grid Field 5', true, 'amount', 'total,100,right', 'number', '', '', '2016-06-07 09:31:28.792279+07', 'admin', '2016-06-07 09:31:28.792279+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (4, 'SYSTEM', 'PCM_ODOO_URL', 'PCM Odoo URL', true, 'http://10.226.202.133:8069', '', 'http://xx103.253.145.215:8069', '', '', '2016-05-02 17:15:32.465576+07', 'admin', '2016-05-02 17:15:32.465576+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (560, 'SYSTEM', 'EXP_USE_ID_FORMAT', 'Expense Use ID Format', true, 'EX${fiscal_year?["yy"]}${running_no?["000000"]}', '', '', '', '', '2016-06-05 18:14:56.843335+07', 'admin', '2016-06-05 18:14:56.843335+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (459, 'PD', 'PD4', 'วิธีประกวดราคา', true, '3', 'วิธีประกวดราคา - ราคาตั้งแต่ 2,000,000 บาท ขึ้นไป', '', '', '', '2016-02-27 11:46:02.052579+07', 'admin', '2016-02-27 11:46:02.052579+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (122, 'SYSTEM', 'PCM_REQ_GRID_FIELD_3', 'Pcm Request Grid Field 3', true, 'budget', 'budget_cc_name,250', '', '', '', '2016-03-24 13:53:11.602934+07', 'admin', '2016-03-24 13:53:11.602934+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (569, 'SYSTEM', 'EXP_BRW_GRID_FIELD_6', 'Expense Borrow Grid Field 6', true, 'requester', 'req_by,90', '', '', '', '2016-06-07 09:32:09.456639+07', 'admin', '2016-06-07 09:32:09.456639+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (592, 'SYSTEM', 'EXP_USE_GRID_FIELD_5', 'Expense Use Grid Field 5', true, 'amount', 'total,100,right', 'number', '', '', '2016-06-07 15:40:04.575948+07', 'admin', '2016-06-07 15:40:04.575948+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (593, 'SYSTEM', 'EXP_USE_GRID_FIELD_6', 'Expense Use Grid Field 6', true, 'requester', 'req_by,90', '', '', '', '2016-06-07 15:41:24.056436+07', 'admin', '2016-06-07 15:41:24.056436+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (528, 'PR_ST', 'S', 'ขอคำปรึกษา', true, '4', 'Consulting', '', '', '', '2016-04-20 17:01:57.563096+07', 'admin', '2016-04-20 17:01:57.563096+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (462, 'PD', 'PD3', 'วิธีประกวดราคาด้วยวิธีการอิเล็กทรอนิกส์', true, '6', 'วิธีประกวดราคาด้วยวิธีการอิเล็กทรอนิกส์ - ราคาตั้งแต่ 2,000,000 บาท ขึ้นไป', '', '', '', '2016-02-27 11:48:29.768209+07', 'admin', '2016-02-27 11:48:29.768209+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (599, 'EXP_TYPE', '0', 'พนักงาน', true, '', 'Employee', '', '', '', '2016-06-09 14:14:01.524964+07', 'admin', '2016-06-09 14:14:01.524964+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (537, 'PD_ST', 'S', 'ขอคำปรึกษา', true, '4', 'Consulting', '', '', '', '2016-05-05 16:51:13.740745+07', 'admin', '2016-05-05 16:51:13.740745+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (538, 'PD_ST', 'C1', 'ออก PO', true, '5', 'PO Created', '', '', '', '2016-05-05 16:51:53.574348+07', 'admin', '2016-05-05 16:51:53.574348+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (575, 'AV_ST', 'W1', 'รอการอนุมัติ', true, '3', 'Wait for Approval', '', '', '', '2016-06-07 12:31:44.937149+07', 'admin', '2016-06-07 12:31:44.937149+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (576, 'AV_ST', 'W2', 'ไม่อนุมัติ', true, '2', 'Rejected', '', '', '', '2016-06-07 12:32:48.156929+07', 'admin', '2016-06-07 12:32:48.156929+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (577, 'AV_ST', 'C1', 'รอการเงินรับงาน', true, '5', 'Wait for Acceptance', '', '', '', '2016-06-07 12:33:24.767754+07', 'admin', '2016-06-07 12:33:24.767754+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (578, 'AV_ST', 'C2', 'การเงินรับงาน', true, '6', 'Accepted', '', '', '', '2016-06-07 12:59:45.500232+07', 'admin', '2016-06-07 12:59:45.500232+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (579, 'AV_ST', 'X1', 'ยกเลิกโดย ผู้ขอ', true, '7', 'Cancelled By Requester', '', '', '', '2016-06-07 12:59:59.685094+07', 'admin', '2016-06-07 12:59:59.685094+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (580, 'AV_ST', 'X2', 'ยกเลิกโดย การเงิน', true, '8', 'Cancelled By Accounting', '', '', '', '2016-06-07 13:00:13.660825+07', 'admin', '2016-06-07 13:00:13.660825+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (581, 'AV_ST', 'S', 'ขอคำปรึกษา', true, '4', 'Consulting', '', '', '', '2016-06-07 13:00:35.013478+07', 'admin', '2016-06-07 13:00:35.013478+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (582, 'AP_ST', 'D', 'Draft', true, '1', 'Draft', '', '', '', '2016-06-07 13:01:28.23601+07', 'admin', '2016-06-07 13:01:28.23601+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (583, 'AP_ST', 'W2', 'ไม่อนุมัติ', true, '2', 'Rejected', '', '', '', '2016-06-07 13:01:41.116981+07', 'admin', '2016-06-07 13:01:41.116981+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (585, 'AP_ST', 'S', 'ขอคำปรึกษา', true, '4', 'Consulting', '', '', '', '2016-06-07 13:02:13.403795+07', 'admin', '2016-06-07 13:02:13.403795+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (586, 'AP_ST', 'C1', 'รอการเงินรับงาน', true, '5', 'Wait for Acceptance', '', '', '', '2016-06-07 13:02:26.855063+07', 'admin', '2016-06-07 13:02:26.855063+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (587, 'AP_ST', 'C2', 'การเงินรับงาน', true, 'ุ6', ' Accepted', '', '', '', '2016-06-07 13:02:57.587382+07', 'admin', '2016-06-07 13:02:57.587382+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (588, 'AP_ST', 'X1', 'ยกเลิกโดย ผู้ขอ', true, '7', 'Cancelled By Requester', '', '', '', '2016-06-07 13:03:13.20436+07', 'admin', '2016-06-07 13:03:13.20436+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (589, 'AP_ST', 'X2', 'ยกเลิกโดย การเงิน', false, '8', 'Cancelled By Accounting', '', '', '', '2016-06-07 13:03:30.647152+07', 'admin', '2016-06-07 13:03:30.647152+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (602, 'EXP_TYPE', '2', 'เคลียร์เงินยืมพนักงาน', true, '', 'Clear Advance', '', '', '', '2016-06-09 14:14:43.275419+07', 'admin', '2016-06-09 14:14:43.275419+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (558, 'BRW_TYPE', 'attend_seminar', 'ยืมเพื่อเดินทางสัมมนา', true, '2', 'For seminar', '', '', '', '2016-05-30 11:45:36.125395+07', 'admin', '2016-05-30 11:45:36.125395+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (600, 'EXP_TYPE', '1', 'หน่วยงาน/บุคคล ภายนอก', true, '', 'Outsider', '', '', '', '2016-06-09 14:14:16.659867+07', 'admin', '2016-06-09 14:14:16.659867+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (5, 'SYSTEM', 'PCM_ODOO_DB', 'PCM Odoo DB', true, 'PABI2_v7', '', '', '', '', '2016-05-02 17:15:56.649536+07', 'admin', '2016-05-02 17:15:56.649536+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (603, 'EXP_TYPE', '3', 'Internal Charge', true, '', 'Internal Charge', '', '', '', '2016-06-09 14:14:52.385884+07', 'admin', '2016-06-09 14:14:52.385884+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (596, 'SYSTEM', 'EXP_USE_CRITERIA_1', 'Expense Use Criteria 1', true, 'Method', 'pay_type', 'main/master?orderBy=flag1', 'type=''EXP_TYPE''', '', '2016-06-08 12:30:51.601959+07', 'admin', '2016-06-08 12:30:51.601959+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (617, 'SYSTEM', 'EXP_BRW_WF_DESC_FORMAT', 'Exp Brw Workflow Description Format', true, '${id} » ${objective}', '', '', '', '', '2016-08-22 17:27:43.293418+07', 'admin', '2016-08-22 17:27:43.293418+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (618, 'SYSTEM', 'EXP_USE_WF_DESC_FORMAT', 'Exp Use Workflow Description Format', true, '${id} » ${objective}', '', '', '', '', '2016-08-22 17:28:05.5658+07', 'admin', '2016-08-22 17:28:05.5658+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (460, 'PD', 'PD5', 'วิธีพิเศษ', true, '4', 'วิธีพิเศษ - ราคาเกิน 300,000 บาท และต้องเป็นกรณีตามข้อบังคับ กวทช.ว่าด้วยการพัสดุ พ.ศ. 2543 ข้อ 22 หรือ 23', '', '', '', '2016-02-27 11:46:59.200559+07', 'admin', '2016-02-27 11:46:59.200559+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (616, 'SYSTEM', 'PCM_REQ_REF_PR_PERCENT', 'Pcm Req Ref PR Percent', true, '3000', '', '', '', '', '2016-08-22 13:20:48.603283+07', 'admin', '2016-08-22 13:20:48.603283+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (604, 'SYSTEM', 'MAIN_INF_PD_UPDATE_STATUS', 'Main Interface PD Update Status', true, '1', '', '', '', '', '2016-07-05 08:06:25.960855+07', 'admin', '2016-07-05 08:06:25.960855+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (605, 'SYSTEM', 'PCM_REQ_CMT_CHECK_PARAMS', 'Pcm Req Committee Check Parameters', true, '50000,1,3', '', '', '', '', '2016-07-05 08:07:36.704622+07', 'admin', '2016-07-05 08:07:36.704622+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (123, 'SYSTEM', 'PCM_REQ_GRID_FIELD_4', 'Pcm Request Grid Field 4', true, 'objective', 'objective,230,,1', '', '', '', '2016-03-24 13:54:40.186708+07', 'admin', '2016-03-24 13:54:40.186708+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (567, 'SYSTEM', 'EXP_BRW_GRID_FIELD_4', 'Expense Borrow Grid Field 4', true, 'objective', 'objective,230,,1', '', '', '', '2016-06-07 09:30:39.838721+07', 'admin', '2016-06-07 09:30:39.838721+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (591, 'SYSTEM', 'EXP_USE_GRID_FIELD_4', 'Expense Use Grid Field 4', true, 'objective', 'objective,230,,1', '', '', '', '2016-06-07 15:39:39.188775+07', 'admin', '2016-06-07 15:39:39.188775+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (609, 'SYSTEM', 'PCM_ORD_SIGN_FONT_SIZE', 'Pcm Ord Signature Font Size', true, '14', '', '', '', '', '2016-07-20 17:51:22.47041+07', 'admin', '2016-07-20 17:51:22.47041+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (126, 'SYSTEM', 'PCM_REQ_GRID_FIELD_7', 'Pcm Request Grid Field 7', true, 'requester', 'req_by,80', '', '', '', '2016-04-20 12:51:24.060687+07', 'admin', '2016-04-20 12:51:24.060687+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (621, 'SYSTEM', 'PCM_REQ_GRID_FIELD_9', 'Pcm Request Grid Field 9', true, 'requestTime', 'created_time_show,120', '', '', '', '2016-08-25 15:52:37.117476+07', 'admin', '2016-08-25 15:52:37.117476+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (608, 'SYSTEM', 'PCM_ORD_SIGN_POS', 'Pcm Ord Signature Position', true, '460,105', '', '', '', '', '2016-07-20 17:50:49.933557+07', 'admin', '2016-07-20 17:50:49.933557+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (610, 'SYSTEM', 'PCM_ORD_SIGN_DATE_OFFSET', 'Pcm Ord Signature Date Offset', true, '30,-35', '', '', '', '', '2016-07-20 17:51:53.513402+07', 'admin', '2016-07-20 17:51:53.513402+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (461, 'PD', 'PD6', 'วิธีกรณีพิเศษ', true, '5', 'วิธีกรณีพิเศษ - การซื้อหรือการจ้างจากส่วนราชการ รัฐวิสาหกิจ หรือองค์การของรัฐ', '', '', '', '2016-02-27 11:47:54.568948+07', 'admin', '2016-02-27 11:47:54.568948+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (611, 'PR_RSN', 'ใกล้หมด', 'ใกล้หมด', true, '1', 'Run out', '', '', '', '2016-08-01 17:32:02.950683+07', 'admin', '2016-08-01 17:32:02.950683+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (612, 'PR_RSN', 'ชำรุด', 'ชำรุด', true, '2', 'Broken', '', '', '', '2016-08-01 17:32:27.89752+07', 'admin', '2016-08-01 17:32:27.89752+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (613, 'PR_RSN', 'เสื่อมสภาพ', 'เสื่อมสภาพ', true, '3', 'Degenerate', '', '', '', '2016-08-01 17:32:51.646717+07', 'admin', '2016-08-01 17:32:51.646717+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (614, 'PR_RSN', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', true, '4', 'New', '', '', '', '2016-08-01 17:33:41.149733+07', 'admin', '2016-08-01 17:33:41.149733+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (615, 'PR_RSN', 'อื่นๆ', 'อื่นๆ', true, '5', 'Other', '', '', '', '2016-08-01 17:34:05.971325+07', 'admin', '2016-08-01 17:34:05.971325+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (535, 'PD_ST', 'W1', 'รอการอนุมัติ', true, '3', 'Wait for Approval', '', '', '', '2016-05-05 16:50:27.380874+07', 'admin', '2016-05-05 16:50:27.380874+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (629, 'SYSTEM', 'PCM_ORD_GRID_FIELD_3', 'Pcm Order Grid Field 3', true, 'method', 'method_name', '', '', '', '2016-10-24 14:56:33.495806+07', 'admin', '2016-10-24 14:56:33.495806+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (606, 'SYSTEM', 'EXP_ODOO_URL', 'EXP Odoo URL', true, 'http://10.226.202.133:8069', '', '', '', '', '2016-07-06 21:48:08.100591+07', 'admin', '2016-07-06 21:48:08.100591+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (622, 'SYSTEM', 'MAIN_INF_AV_CREATE_AV', 'Main Interface Create AV', true, '1', '', '', '', '', '2016-09-06 08:36:39.761775+07', 'admin', '2016-09-06 08:36:39.761775+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (607, 'SYSTEM', 'EXP_ODOO_DB', 'EXP Odoo DB', true, 'PABI2_v7', '', '', '', '', '2016-07-06 21:48:33.644482+07', 'admin', '2016-07-06 21:48:33.644482+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (121, 'SYSTEM', 'PCM_REQ_GRID_FIELD_2', 'Pcm Request Grid Field 2', true, 'prType', 'objective_type_name,60,center', '', '', '', '2016-03-24 13:51:30.20756+07', 'admin', '2016-03-24 13:51:30.20756+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (627, 'SYSTEM', 'EXP_BRW_TOP_GROUP', 'Exp Borrow Top Group', true, 'account_finance', '', '', '', '', '2016-10-01 10:14:30.874065+07', 'admin', '2016-10-01 10:14:30.874065+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (628, 'SYSTEM', 'EXP_USE_TOP_GROUP', 'Exp Use Top Group', true, 'account_finance', '', '', '', '', '2016-10-01 10:14:51.752246+07', 'admin', '2016-10-01 10:14:51.752246+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (626, 'SYSTEM', 'PCM_ORD_TOP_GROUP', 'Pcm Order Top Group', true, '', '', '', '', '', '2016-10-01 10:14:12.758887+07', 'admin', '2016-10-01 10:14:12.758887+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (625, 'SYSTEM', 'PCM_REQ_TOP_GROUP', 'Pcm Request Top Group', true, '', '', '', '', '', '2016-10-01 10:13:53.545355+07', 'admin', '2016-10-01 10:13:53.545355+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (630, 'EXP_TYPE', '4', 'เงินสดย่อย', false, '', 'Petty Cash', '', '', '', '2016-10-25 16:45:11.341791+07', 'admin', '2016-10-25 16:45:11.341791+07', 'admin');


--
-- TOC entry 3045 (class 0 OID 0)
-- Dependencies: 381
-- Name: pb2_main_master_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_main_master_id_seq', 630, true);


--
-- TOC entry 2963 (class 0 OID 163981)
-- Dependencies: 379
-- Data for Name: pb2_main_master_old; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (203, 'PR_ST', 'D', 'Draft', true, '1', '', '', '', '', '2015-03-26 10:54:24.7+07', NULL, '2015-03-26 10:54:24.7+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (233, 'A', 'SYSTEM', 'System Config', true, 'กำหนดค่า', '', NULL, NULL, NULL, '2015-05-03 17:50:14.6244+07', NULL, '2015-05-03 17:50:14.6244+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (506, 'PTT', 'PTT1', 'ต้นแบบโครงการ (วิจัย)', true, '', '', '', '', '', '2016-04-04 13:59:11.691689+07', 'admin', '2016-04-04 13:59:11.691689+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (110, 'SYSTEM', 'PCM_REQ_CRITERIA_1', 'Pcm Req Criteria 1', true, 'Purchase Type', 'objective_type,200,200', 'main/master?orderBy=flag1', 'type=''PC''', '', '2015-05-20 10:35:54.838422+07', 'admin', '2015-05-20 10:35:54.838422+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (111, 'SYSTEM', 'PCM_REQ_CRITERIA_2', 'Pcm Req Criteria 2', true, 'Status', 'status', 'main/master?orderBy=flag1', 'type=''PR_ST''', '', '2015-05-20 10:35:54.838422+07', 'admin', '2015-05-20 10:35:54.838422+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (499, 'A', 'MP', 'แบบฟอร์มราคากลาง', true, '', '', '', '', '', '2016-04-02 18:40:43.806146+07', 'admin', '2016-04-02 18:40:43.806146+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (170, 'SYSTEM', 'PCM_REQ_MAIL_NOTIFY', 'Pcm Req Mail Notify', true, '0', '', '', '', '', '2015-08-26 15:33:59.285866+07', 'admin', '2015-08-26 15:33:59.285866+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (505, 'A', 'PTT', 'ครุภัณฑ์ต้นแบบ', true, '', '', '', '', '', '2016-04-04 13:58:41.291261+07', 'admin', '2016-04-04 13:58:41.291261+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (171, 'SYSTEM', 'PCM_REQ_MAIL_TEMPLATE', 'Pcm Req Mail Template', true, 'workspace://SpacesStore/33126264-7462-4b99-962c-cb3c9745a78b', '', '', '', '', '2015-08-26 15:36:13.883887+07', 'admin', '2015-08-26 15:36:13.883887+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (172, 'SYSTEM', 'PCM_REQ_MAIL_SUBJECT', 'Pcm Req Mail Subject', true, 'กรุณาอนุมัติใบบันทึก (Memo) เลขที่ ${memoId}', '', '', '', '', '2015-08-26 15:36:48.066462+07', 'admin', '2015-08-26 15:36:48.066462+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (173, 'SYSTEM', 'PCM_REQ_MAIL_FROM', 'Pcm Req Mail From Label', true, 'Localhost', '', '', '', '', '2015-08-26 15:37:20.17415+07', 'admin', '2015-08-26 15:37:20.17415+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (220, 'SYSTEM', 'PCM_ORD_GRID_FIELD_2', 'Pcm Order Grid Field 2', true, 'ศูนย์', 'org_name,60,130', '', '', '', '2016-03-14 13:24:41.634559+07', 'admin', '2016-03-14 13:24:41.634559+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (223, 'SYSTEM', 'PCM_ORD_GRID_FIELD_4', 'Pcm Order Grid Field 4', true, 'ความประสงค์', 'objective', '', '', '', '2016-03-14 13:26:11.68346+07', 'admin', '2016-03-14 13:26:11.68346+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (225, 'SYSTEM', 'PCM_ORD_GRID_FIELD_6', 'Pcm Order Grid Field 6', true, 'ผู้ขอ', 'created_by', '', '', '', '2016-03-14 13:28:16.89383+07', 'admin', '2016-03-14 13:28:16.89383+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (541, 'SYSTEM', 'PCM_ORD_GRID_FIELD_1', 'Pcm Order Grid Field 1', true, 'PD NO.', 'id,150', '', '', '', '2016-05-10 10:42:23.863062+07', 'admin', '2016-05-10 10:42:23.863062+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (491, 'A', 'TR', 'จัดฝึกอบรม', true, '', '', '', '', '', '2016-04-02 18:30:17.735904+07', 'admin', '2016-04-02 18:30:17.735904+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (542, 'SYSTEM', 'PCM_ORD_GRID_FIELD_7', 'Pcm Order Grid Field 7', true, 'Requested Time', 'created_time_show,130', '', '', '', '2016-05-10 10:44:31.582372+07', 'admin', '2016-05-10 10:44:31.582372+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (201, 'SYSTEM', 'PCM_ORD_PATH_FORMAT', 'Pcm Order Path Format', true, 'PD/${fiscal_year[yyyy]}/${id}', '', '', '', '', '2016-03-19 18:16:44.558704+07', 'admin', '2016-03-19 18:16:44.558704+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (228, 'A', 'A', 'Master Type', true, 'ประเภทข้อมูลหลัก', '', NULL, NULL, NULL, '2015-04-28 18:50:30.125156+07', NULL, '2015-04-28 18:50:30.125156+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (533, 'A', 'PD_ST', 'PD Workflow Status', true, 'สถานะ Workflow PD', '', '', '', '', '2016-05-05 16:47:38.031627+07', 'admin', '2016-05-05 16:47:38.031627+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (213, 'A', 'PR_ST', 'PR Workflow Status', true, 'สถานะ Workflow PR', '', '', '', '', '2015-03-30 09:17:50.759+07', NULL, '2015-03-30 09:17:50.759+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (531, 'SYSTEM', 'PCM_ORD_CRITERIA_1', 'Pcm Order Criteria 1', true, 'Method', 'doc_type,500,500', 'main/master?orderBy=flag1', 'type=''PD''', '', '2016-05-05 15:38:29.529432+07', 'admin', '2016-05-05 15:38:29.529432+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (508, 'A', 'VAT', 'VAT', true, '', '', '', '', '', '2016-04-04 15:08:56.891023+07', 'admin', '2016-04-04 15:08:56.891023+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (507, 'PTT', 'PTT2', 'ต้นแบบส่งมอบ', true, '', '', '', '', '', '2016-04-04 13:59:25.448892+07', 'admin', '2016-04-04 13:59:25.448892+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (534, 'PD_ST', 'D', 'Draft', true, '1', '', '', '', '', '2016-05-05 16:50:04.098353+07', 'admin', '2016-05-05 16:50:04.098353+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (204, 'PR_ST', 'W1', 'รอการอนุมัติ', true, '3', '', '', '', '', '2015-03-26 10:54:42.32+07', NULL, '2015-03-26 10:54:42.32+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (205, 'PR_ST', 'C1', 'รอพัสดุรับงาน', true, '5', '', '', '', '', '2015-03-26 10:55:15.436+07', NULL, '2015-03-26 10:55:15.436+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (206, 'PR_ST', 'C2', 'พัสดุรับงาน', true, '6', '', '', '', '', '2015-03-26 10:55:24.561+07', NULL, '2015-03-26 10:55:24.561+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (503, 'PR_ST', 'X1', 'ยกเลิกโดย ผู้ขอ', true, '7', '', '', '', '', '2016-04-02 21:10:44.023056+07', 'admin', '2016-04-02 21:10:44.023056+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (535, 'PD_ST', 'W1', 'รอการอนุมัติ', true, '3', '', '', '', '', '2016-05-05 16:50:27.380874+07', 'admin', '2016-05-05 16:50:27.380874+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (100, 'SYSTEM', 'PCM_REQ_SITE_ID', 'Pcm Req Site ID', true, 'pcm', '', '', '', '', '2016-03-01 18:21:46.282169+07', 'admin', '2016-03-01 18:21:46.282169+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (102, 'SYSTEM', 'PCM_REQ_ID_FORMAT', 'Pcm Req ID Format', true, 'PR${fiscal_year?["yy"]}${running_no?["000000"]}', '', '', '', '', '2016-03-01 17:52:55.201313+07', 'admin', '2016-03-01 17:52:55.201313+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (200, 'SYSTEM', 'PCM_ORD_SITE_ID', 'Pcm Order Site ID', true, 'pcm', '', '', '', '', '2016-03-19 18:22:24.096585+07', 'admin', '2016-03-19 18:22:24.096585+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (532, 'SYSTEM', 'PCM_ORD_CRITERIA_2', 'Pcm Order Criteria 2', true, 'Status', 'status', 'main/master?orderBy=flag1', 'type=''PD_ST''', '', '2016-05-05 16:45:36.718636+07', 'admin', '2016-05-05 16:45:36.718636+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (539, 'PD_ST', 'X1', 'ยกเลิกโดย พัสดุ', true, '6', '', '', '', '', '2016-05-05 16:52:28.490405+07', 'admin', '2016-05-05 16:52:28.490405+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (1, 'SYSTEM', 'MAIN_PAGING_SIZE', 'Main Paging Size', true, '40', '', '', '', '', '2015-05-21 15:32:24.553387+07', 'admin', '2015-05-21 15:32:24.553387+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (2, 'SYSTEM', 'MAIN_TEMP_PATH', 'Main Temp Path', true, 'workspace://SpacesStore/71ce6549-63b7-454c-96c1-5f98ae3e1b50', '', '', '', '', '2015-05-27 18:02:54.070201+07', 'admin', '2015-05-27 18:02:54.070201+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (3, 'SYSTEM', 'MAIN_SIGNATURE_PATH', 'Main Signature Path', true, 'workspace://SpacesStore/d3c83cb5-a3e5-47ec-9005-89dd5dc2103a', '', '', '', '', '2015-05-10 16:00:30.913119+07', 'admin', '2015-05-10 16:00:30.913119+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (101, 'SYSTEM', 'PCM_REQ_PATH_FORMAT', 'Pcm Req Path Format', true, 'PR/${fiscal_year[yyyy]}/${id}', '${objective_type}/${fiscal_year[yyyy]}/${id}', '', '', '', '2016-03-01 17:55:04.196065+07', 'admin', '2016-03-01 17:55:04.196065+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (500, 'MP', 'MP1', 'งานจ้างก่อสร้าง', true, 'workspace://SpacesStore/e3911456-981b-4ab4-ba19-4415ade8dfdf', '', '', '', '', '2016-04-02 20:18:42.320346+07', 'admin', '2016-04-02 20:18:42.320346+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (501, 'MP', 'MP2', 'การจ้างควบคุมงาน', true, 'workspace://SpacesStore/7808ad78-e4ab-4ab4-926b-b5be87ac91d9', '', '', '', '', '2016-04-02 20:18:56.956525+07', 'admin', '2016-04-02 20:18:56.956525+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (512, 'MP', 'MP4', 'การจ้างพัฒนาระบบคอมพิวเตอร์', true, 'workspace://SpacesStore/6c148cca-6889-4038-ad49-a4ca9acd67ab', '', '', '', '', '2016-04-05 08:14:16.789411+07', 'admin', '2016-04-05 08:14:16.789411+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (513, 'MP', 'MP5', 'การจ้างออกแบบ', true, 'workspace://SpacesStore/2d5addb6-a45f-4d68-bd42-a4a5aefb4622', '', '', '', '', '2016-04-05 08:14:34.15099+07', 'admin', '2016-04-05 08:14:34.15099+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (514, 'MP', 'MP6', 'การจ้างงานวิจัยหรือเงินสนับสนุนให้ทุนการวิจัย', true, 'workspace://SpacesStore/c30a371e-fe6a-4c76-a674-4cc074b0dc54', '', '', '', '', '2016-04-05 08:14:49.938248+07', 'admin', '2016-04-05 08:14:49.938248+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (121, 'SYSTEM', 'PCM_REQ_GRID_FIELD_2', 'Pcm Request Grid Field 2', true, 'ประเภท', 'objective_type,60,center', '', '', '', '2016-03-24 13:51:30.20756+07', 'admin', '2016-03-24 13:51:30.20756+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (150, 'SYSTEM', 'PCM_REQ_SEARCH_GRID_ORDER_BY', 'Pcm Req Search Grid Order by', true, 'ORDER_FIELD, updated_time DESC', '', '', '', '', '2016-01-07 12:24:43.579596+07', 'admin', '2016-01-07 12:24:43.579596+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (151, 'SYSTEM', 'PCM_REQ_MSG_MISSING_NEXT_APP', 'Pcm Req Message Missing Next Approver', true, 'Next Approver', '', '', '', '', '2016-02-12 16:58:36.218211+07', 'admin', '2016-02-12 16:58:36.218211+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (152, 'SYSTEM', 'PCM_REQ_WF_FAIL_COND_1', 'Pcm Req Fail Condition 1', true, '2,field3=''IC''|field9!=''true'',Temporary Fix', '', '', '', '', '2016-02-15 13:05:22.242698+07', 'admin', '2016-02-15 13:05:22.242698+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (224, 'SYSTEM', 'PCM_ORD_GRID_FIELD_5', 'Pcm Order Grid Field 5', true, 'ราคารวม', 'total,,right', 'number', '', '', '2016-03-14 13:27:20.729331+07', 'admin', '2016-03-14 13:27:20.729331+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (492, 'A', 'PU', 'หน่วยงานจัดซื้อจัดจ้าง', true, '', '', '', '', '', '2016-04-02 18:30:42.678997+07', 'admin', '2016-04-02 18:30:42.678997+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (509, 'VAT', '00', '0%', true, '', '', '', '', '', '2016-04-04 15:09:26.138663+07', 'admin', '2016-04-04 15:09:26.138663+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (375, 'R', 'R02', 'IC - สรุปสถิติ แยกตามทีม', true, 'IC', 'R02 Title', '', '', '', '2015-12-08 14:56:00.132702+07', 'walai', '2015-12-08 14:56:00.132702+07', 'walai');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (376, 'R', 'R03', 'IC - สรุปสถิติ แยกระดับผลกระทบ', true, 'IC', 'R03 Title', '', '', '', '2015-12-08 14:56:19.736133+07', 'walai', '2015-12-08 14:56:19.736133+07', 'walai');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (413, 'A', 'R', 'Report', true, '', '', '', '', '', '2016-02-04 11:17:35.663821+07', 'admin', '2016-02-04 11:17:35.663821+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (493, 'TR', 'TR1', 'อบรม 1', true, '', '', '', '', '', '2016-04-02 18:34:47.48904+07', 'admin', '2016-04-02 18:34:47.48904+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (494, 'TR', 'TR2', 'อบรม 2', true, '', '', '', '', '', '2016-04-02 18:35:06.077229+07', 'admin', '2016-04-02 18:35:06.077229+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (495, 'TR', 'TR3', 'อบรม 3', true, '', '', '', '', '', '2016-04-02 18:35:17.156982+07', 'admin', '2016-04-02 18:35:17.156982+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (510, 'VAT', '07', '7%', true, '', '', '', '', '', '2016-04-04 15:09:34.559236+07', 'admin', '2016-04-04 15:09:34.559236+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (511, 'VAT', '10', '10%', true, '', '', '', '', '', '2016-04-04 15:09:45.684139+07', 'admin', '2016-04-04 15:09:45.684139+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (437, 'PC', 'ซื้อ', 'ซื้อ', true, '1', '', '', '', '', '2016-02-24 09:00:49.350937+07', 'admin', '2016-02-24 09:00:49.350937+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (438, 'PC', 'จ้าง', 'จ้าง', true, '2', '', '', '', '', '2016-02-24 09:01:07.075668+07', 'admin', '2016-02-24 09:01:07.075668+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (454, 'FUND', 'C', 'Cost Center', true, '', '', '', '', '', '2016-02-26 19:19:51.48891+07', 'admin', '2016-02-26 19:19:51.48891+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (440, 'PC', 'เช่า', 'เช่า', true, '3', '', '', '', '', '2016-02-26 13:26:52.271699+07', 'admin', '2016-02-26 13:26:52.271699+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (451, 'BA', '105014', '105014', true, '', '', '', '', '', '2016-02-26 19:19:03.012544+07', 'admin', '2016-02-26 19:19:03.012544+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (455, 'FUND', 'P', 'Project', true, '', '', '', '', '', '2016-02-26 19:20:48.172425+07', 'admin', '2016-02-26 19:20:48.172425+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (436, 'A', 'PC', 'Purchasing Type', true, 'PC', '', '', '', '', '2016-02-24 08:59:52.801248+07', 'admin', '2016-02-24 08:59:52.801248+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (452, 'BA', '105015', '105015', true, '', '', '', '', '', '2016-02-26 19:19:16.885364+07', 'admin', '2016-02-26 19:19:16.885364+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (453, 'BA', '105046', '105046', true, '', '', '', '', '', '2016-02-26 19:19:28.815388+07', 'admin', '2016-02-26 19:19:28.815388+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (441, 'A', 'CUR', 'Currency', true, 'CUR', '', '', '', '', '2016-02-26 18:51:50.294962+07', 'admin', '2016-02-26 18:51:50.294962+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (442, 'CUR', 'THB', 'Thai Baht', true, '', '', '', '', '', '2016-02-26 18:52:15.503968+07', 'admin', '2016-02-26 18:52:15.503968+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (443, 'CUR', 'USD', 'U.S. Dollar', true, '', '', '', '', '', '2016-02-26 18:52:56.294799+07', 'admin', '2016-02-26 18:52:56.294799+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (444, 'A', 'STOCK', 'คลังจัดเก็บพัสดุ', true, '', '', '', '', '', '2016-02-26 19:16:36.851021+07', 'admin', '2016-02-26 19:16:36.851021+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (445, 'A', 'FUND', 'ศูนย์เงินทุน', true, '', '', '', '', '', '2016-02-26 19:16:57.893776+07', 'admin', '2016-02-26 19:16:57.893776+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (446, 'A', 'BA', 'หน่วยงาน', true, '', '', '', '', '', '2016-02-26 19:17:17.179928+07', 'admin', '2016-02-26 19:17:17.179928+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (448, 'STOCK', 'ST2', 'Biotec', true, '', '', '', '', '', '2016-02-26 19:17:57.239506+07', 'admin', '2016-02-26 19:17:57.239506+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (449, 'STOCK', 'ST3', 'Mtec', true, '', '', '', '', '', '2016-02-26 19:18:10.768522+07', 'admin', '2016-02-26 19:18:10.768522+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (450, 'STOCK', 'ST4', 'Nectec', true, '', '', '', '', '', '2016-02-26 19:18:31.676963+07', 'admin', '2016-02-26 19:18:31.676963+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (447, 'STOCK', 'ST1', 'Center', true, '', '', '', '', '', '2016-02-26 19:17:46.345281+07', 'admin', '2016-02-26 19:17:46.345281+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (207, 'SYSTEM', 'PCM_ORD_WF_DESC_FORMAT', 'Pcm Order Workflow Description Format', true, '${id} » ${doc_type} » ${objective}', '', '', '', '', '2016-03-19 18:22:57.191277+07', 'admin', '2016-03-19 18:22:57.191277+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (504, 'PR_ST', 'X2', 'ยกเลิกโดย พัสดุ', true, '8', '', '', '', '', '2016-04-02 21:11:04.270543+07', 'admin', '2016-04-02 21:11:04.270543+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (103, 'SYSTEM', 'PCM_REQ_WF_DESC_FORMAT', 'Pcm Req Workflow Description Format', true, '${id} » ${objective_type} ${objective} ${reason}', '', '', '', '', '2016-03-01 18:58:33.61801+07', 'admin', '2016-03-01 18:58:33.61801+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (457, 'PD', 'PD1', 'วิธีตกลงราคา - ราคาไม่เกิน 300,000 บาท', true, '1', '', '', '', '', '2016-02-27 11:35:24.518942+07', 'admin', '2016-02-27 11:35:24.518942+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (458, 'PD', 'PD2', 'วิธีสอบราคา - ราคาเกิน 300,000 บาท แต่ไม่เกิน 2,000,000 บาท', true, '2', '', '', '', '', '2016-02-27 11:45:32.303578+07', 'admin', '2016-02-27 11:45:32.303578+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (460, 'PD', 'PD5', 'วิธีพิเศษ - ราคาเกิน 300,000 บาท และต้องเป็นกรณีตามข้อบังคับ กวทช.ว่าด้วยการพัสดุ พ.ศ. 2543 ข้อ 22 หรือ 23', true, '4', '', '', '', '', '2016-02-27 11:46:59.200559+07', 'admin', '2016-02-27 11:46:59.200559+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (456, 'A', 'PD', 'วิธีการจัดหา', true, '', '', '', '', '', '2016-02-27 11:34:37.423435+07', 'admin', '2016-02-27 11:34:37.423435+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (536, 'PD_ST', 'W2', 'ไม่อนุมัติ', true, '2', '', '', '', '', '2016-05-05 16:50:49.434759+07', 'admin', '2016-05-05 16:50:49.434759+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (502, 'MP', 'MP3', 'การจ้างที่ปรึกษา', true, 'workspace://SpacesStore/a77fb4e1-1aca-47ab-a917-3e55416d5fdb', '', '', '', '', '2016-04-02 20:19:09.133286+07', 'admin', '2016-04-02 20:19:09.133286+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (515, 'MP', 'MP7', 'การจัดซื้อจัดจ้างที่มิใช่งานก่อสร้าง', true, 'workspace://SpacesStore/b2aa3d55-d09d-45a2-b6dd-e04bb21b8277', '', '', '', '', '2016-04-05 08:15:06.247067+07', 'admin', '2016-04-05 08:15:06.247067+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (4, 'SYSTEM', 'MAIN_ODOO_URL', 'Main Odoo URL', true, 'http://10.226.202.133:8069', '', 'http://xx103.253.145.215:8069', '', '', '2016-05-02 17:15:32.465576+07', 'admin', '2016-05-02 17:15:32.465576+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (517, 'PU', '34', 'พัสดุ - ศช.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (518, 'PU', '35', 'พัสดุ - ศว.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (519, 'PU', '36', 'พัสดุ - ศอ.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (520, 'PU', '37', 'พัสดุ - ศจ.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (521, 'PU', '38', 'พัสดุ - ศจ. (SWP)', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (522, 'PU', '39', 'พัสดุ - ศจ. (NNSTDA)', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (523, 'PU', '40', 'พัสดุ - ศน.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (516, 'PU', '33', 'พัสดุ - สก.', true, '', '', '', '', '', '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (461, 'PD', 'PD6', 'วิธีกรณีพิเศษ - การซื้อหรือการจ้างจากส่วนราชการ รัฐวิสาหกิจ หรือองค์การของรัฐ', true, '5', '', '', '', '', '2016-02-27 11:47:54.568948+07', 'admin', '2016-02-27 11:47:54.568948+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (459, 'PD', 'PD4', 'วิธีประกวดราคา - ราคาตั้งแต่ 2,000,000 บาท ขึ้นไป', true, '3', '', '', '', '', '2016-02-27 11:46:02.052579+07', 'admin', '2016-02-27 11:46:02.052579+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (462, 'PD', 'PD3', 'วิธีประกวดราคาด้วยวิธีการอิเล็กทรอนิกส์ - ราคาตั้งแต่ 2,000,000 บาท ขึ้นไป', true, '6', '', '', '', '', '2016-02-27 11:48:29.768209+07', 'admin', '2016-02-27 11:48:29.768209+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (537, 'PD_ST', 'S', 'ขอคำปรึกษา', true, '4', '', '', '', '', '2016-05-05 16:51:13.740745+07', 'admin', '2016-05-05 16:51:13.740745+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (527, 'PR_ST', 'W2', 'ไม่อนุมัติ', true, '2', '', '', '', '', '2016-04-20 17:01:33.729903+07', 'admin', '2016-04-20 17:01:33.729903+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (528, 'PR_ST', 'S', 'ขอคำปรึกษา', true, '4', '', '', '', '', '2016-04-20 17:01:57.563096+07', 'admin', '2016-04-20 17:01:57.563096+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (538, 'PD_ST', 'C1', 'ออก PO', true, '5', '', '', '', '', '2016-05-05 16:51:53.574348+07', 'admin', '2016-05-05 16:51:53.574348+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (122, 'SYSTEM', 'PCM_REQ_GRID_FIELD_3', 'Pcm Request Grid Field 3', true, 'งบประมาณที่ใช้', 'budget_cc_name,250', '', '', '', '2016-03-24 13:53:11.602934+07', 'admin', '2016-03-24 13:53:11.602934+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (124, 'SYSTEM', 'PCM_REQ_GRID_FIELD_5', 'Pcm Request Grid Field 5', true, 'จำนวนเงิน', 'total,100,right', 'number', '', '', '2016-03-24 13:55:21.457562+07', 'admin', '2016-03-24 13:55:21.457562+07', '002648');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (125, 'SYSTEM', 'PCM_REQ_GRID_FIELD_6', 'Pcm Request Grid Field 6', true, 'ผู้ขอเบิก', 'req_by,100', '', '', '', '2016-03-24 14:19:15.250307+07', 'admin', '2016-03-24 14:19:15.250307+07', '002648');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (126, 'SYSTEM', 'PCM_REQ_GRID_FIELD_7', 'Pcm Request Grid Field 7', true, 'ผู้บันทึก', 'created_by,100', '', '', '', '2016-04-20 12:51:24.060687+07', 'admin', '2016-04-20 12:51:24.060687+07', '002648');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (5, 'SYSTEM', 'MAIN_ODOO_DB', 'Main Odoo DB', true, 'PABI2_v3', '', '', '', '', '2016-05-02 17:15:56.649536+07', 'admin', '2016-05-02 17:15:56.649536+07', 'admin');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (120, 'SYSTEM', 'PCM_REQ_GRID_FIELD_1', 'Pcm Request Grid Field 1', true, 'PR NO.', 'id,100,,0', '', '', '', '2016-03-24 13:49:37.508309+07', 'admin', '2016-03-24 13:49:37.508309+07', '002648');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (123, 'SYSTEM', 'PCM_REQ_GRID_FIELD_4', 'Pcm Request Grid Field 4', true, 'วัตถุประสงค์', 'objective,,,1', '', '', '', '2016-03-24 13:54:40.186708+07', 'admin', '2016-03-24 13:54:40.186708+07', '002648');
INSERT INTO pb2_main_master_old (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (540, 'SYSTEM', 'PCM_REQ_GRID_FIELD_8', 'Pcm Request Grid Field 8', true, 'วันที่ขอ', 'created_time_show,120', '', '', '', '2016-05-08 16:38:51.070593+07', 'admin', '2016-05-08 16:38:51.070593+07', '002648');


--
-- TOC entry 2946 (class 0 OID 72596)
-- Dependencies: 360
-- Data for Name: pb2_main_msg; Type: TABLE DATA; Schema: public; Owner: alfresco
--



--
-- TOC entry 2947 (class 0 OID 72599)
-- Dependencies: 361
-- Data for Name: pb2_main_workflow; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2210, 'PCM_REQ', 'PR17000367', 'activiti$436401', 'Approve', '003556', '2016-10-24 14:59:31.274+07', 'activiti$436539', '2016-10-24 14:49:02.501114+07', '002648', '000511', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2211, 'PCM_REQ', 'PR17000368', 'activiti$436602', 'Approve', '001923', '2016-10-24 15:21:32.002+07', 'activiti$start436602', '2016-10-24 15:17:24.531027+07', '002648', '000110', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2223, 'PCM_REQ', 'PR17000378', 'activiti$439150', 'Cancel', '000511', '2016-10-25 08:43:03.237+07', 'activiti$439509', '2016-10-24 17:41:31.913191+07', '000511', '000511', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2212, 'PCM_REQ', 'PR17000369', 'activiti$436742', 'Cancel', '003556', '2016-10-24 15:38:36.054+07', 'activiti$start436742', '2016-10-24 15:36:19.956853+07', '002648', '000090', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2243, 'PCM_ORD', 'PD16000165', 'activiti$443637', 'Approve', '000165', '2016-10-27 17:11:15.2+07', 'activiti$443826', '2016-10-27 17:05:09.946943+07', '003556', '000165', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2214, 'PCM_ORD', 'PD16000154', 'activiti$437022', 'Approve', '001028', '2016-10-24 15:50:32.129+07', 'activiti$start437022', '2016-10-24 15:44:23.45298+07', '003556', '001028', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2221, 'PCM_ORD', 'PD16000158', 'activiti$438517', 'Cancel', '003556', '2016-10-24 17:35:30.395+07', 'activiti$start438517', '2016-10-24 17:25:15.646899+07', '003556', '001028', '438517', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2217, 'PCM_ORD', 'PD16000156', 'activiti$437601', 'Cancel', '003556', '2016-10-24 17:35:47.083+07', 'activiti$start437601', '2016-10-24 16:06:18.830048+07', '003556', '001028', '437601', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2236, 'PCM_REQ', 'PR17000380', 'activiti$442058', 'Approve', '003556', '2016-10-25 16:51:43.694+07', 'activiti$442617', '2016-10-25 11:06:16.11628+07', '002648', '000090', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2233, 'EXP_USE', 'EX17000388', 'activiti$440977', 'Cancel', '000090', '2016-10-25 08:59:52.56+07', 'activiti$441173', '2016-10-25 08:58:51.960833+07', '000090', '000090', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2216, 'PCM_REQ', 'PR17000372', 'activiti$437461', 'Approve', '003556', '2016-10-24 16:03:42.45+07', 'activiti$start437461', '2016-10-24 16:01:36.662471+07', '002648', '000511', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2215, 'PCM_REQ', 'PR17000371', 'activiti$437260', 'Approve', '003556', '2016-10-24 16:09:11.058+07', 'activiti$437398', '2016-10-24 15:51:39.416193+07', '003556', '000272', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2218, 'PCM_REQ', 'PR17000373', 'activiti$437714', 'Cancel', '002648', '2016-10-24 16:14:42.742+07', 'activiti$437850', '2016-10-24 16:12:56.161282+07', '002648', '002648', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2234, 'EXP_BRW', 'AV17000288', 'activiti$441655', 'Cancel', '001723', '2016-10-25 09:08:27.148+07', 'activiti$441854', '2016-10-25 09:05:58.080042+07', '002648', '000090', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2222, 'PCM_REQ', 'PR17000377', 'activiti$438687', 'Cancel', '002648', '2016-10-24 17:39:44.126+07', 'activiti$439086', '2016-10-24 17:33:37.477305+07', '002648', '002648', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2229, 'EXP_USE', 'EX17000384', 'activiti$440036', 'Cancel', '002648', '2016-10-25 08:44:09.208+07', 'activiti$440211', '2016-10-25 08:42:14.065955+07', '002648', '002648', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2219, 'PCM_REQ', 'PR17000374', 'activiti$437912', 'Cancel', '002648', '2016-10-24 16:23:00.801+07', 'activiti$438194', '2016-10-24 16:20:15.954884+07', '002648', '002648', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2228, 'EXP_USE', 'EX17000383', 'activiti$439942', 'Cancel', '002648', '2016-10-25 08:44:13.49+07', 'activiti$440273', '2016-10-25 08:41:04.053177+07', '002648', '002648', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2227, 'EXP_USE', 'EX17000382', 'activiti$439848', 'Cancel', '002648', '2016-10-25 08:44:18.022+07', 'activiti$440336', '2016-10-25 08:40:13.529025+07', '002648', '002648', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2220, 'PCM_REQ', 'PR17000375', 'activiti$438255', 'Approve', '003556', '2016-10-24 17:23:33.203+07', 'activiti$438454', '2016-10-24 17:18:15.326861+07', '002648', '000090', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2237, 'EXP_USE', 'EX17000389', 'activiti$442301', 'Resubmit', '002648', '2016-10-29 20:08:40.402+07', 'activiti$444987', '2016-10-25 16:38:55.515872+07', '002648', '001509', '', 'ขออนุมัติใหม่');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2230, 'EXP_USE', 'EX17000385', 'activiti$440485', 'Cancel', '000511', '2016-10-25 08:47:50.117+07', 'activiti$440620', '2016-10-25 08:46:49.976502+07', '000511', '000511', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2224, 'EXP_BRW', 'AV17000287', 'activiti$439246', 'Paid', '001723', '2016-10-24 17:51:00.201+07', 'activiti$439446', '2016-10-24 17:46:44.804209+07', '002648', '000090', '', 'จ่ายเงิน');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2225, 'EXP_USE', 'EX17000380', 'activiti$439571', 'Cancel', '001723', '2016-10-24 18:07:07.5+07', 'activiti$start439571', '2016-10-24 17:55:28.446241+07', '002648', '001509', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2238, 'PCM_ORD', 'PD16000159', 'activiti$442680', 'Approve', '000086', '2016-10-25 17:02:54.926+07', 'activiti$442812', '2016-10-25 16:53:44.351991+07', '003556', '000086', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2226, 'EXP_USE', 'EX17000381', 'activiti$439709', 'Approve', '001723', '2016-10-25 08:37:27.187+07', 'activiti$start439709', '2016-10-25 08:35:42.94201+07', '002648', '001558', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2231, 'EXP_USE', 'EX17000386', 'activiti$440684', 'Cancel', '000090', '2016-10-25 08:52:07.692+07', 'activiti$440819', '2016-10-25 08:51:04.426287+07', '000090', '000090', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2235, 'PCM_REQ', 'PR17000379', 'activiti$441917', 'Approve', '003556', '2016-10-25 17:07:55.795+07', 'activiti$start441917', '2016-10-25 10:25:04.728961+07', '002648', '001509', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2239, 'PCM_ORD', 'PD16000160', 'activiti$442876', 'Approve', '001028', '2016-10-25 17:11:32.063+07', 'activiti$start442876', '2016-10-25 17:09:37.743847+07', '003556', '001028', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2232, 'EXP_USE', 'EX17000387', 'activiti$440883', 'Cancel', '000090', '2016-10-25 09:03:44.876+07', 'activiti$441594', '2016-10-25 08:53:14.693689+07', '000090', '000090', '', 'ยกเลิก');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2240, 'PCM_REQ', 'PR17000381', 'activiti$443101', 'Approve', '001923', '2016-10-26 17:25:42.402+07', 'activiti$start443101', '2016-10-26 17:22:05.208906+07', '002648', '000110', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2213, 'PCM_REQ', 'PR17000370', 'activiti$436882', 'Approve', '003556', '2016-10-26 17:31:09.707+07', 'activiti$start436882', '2016-10-24 15:41:38.983669+07', '000511', '000090', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2242, 'PCM_REQ', 'PR17000382', 'activiti$443375', 'Approve', '003556', '2016-10-27 16:37:12.293+07', 'activiti$443574', '2016-10-27 16:29:45.024866+07', '000511', '000165', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2241, 'PCM_ORD', 'PD16000164', 'activiti$443241', 'Approve', '001028', '2016-10-26 17:34:48.986+07', 'activiti$start443241', '2016-10-26 17:33:29.187903+07', '003556', '001028', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2245, 'PCM_ORD', 'PD16000166', 'activiti$444147', 'Approve', '000165', '2016-10-27 17:46:24.872+07', 'activiti$444337', '2016-10-27 17:44:10.741508+07', '003556', '000165', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2244, 'PCM_REQ', 'PR17000383', 'activiti$443885', 'Approve', '003556', '2016-10-27 17:41:29.023+07', 'activiti$444084', '2016-10-27 17:36:09.734145+07', '000511', '000165', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2246, 'EXP_USE', 'EX17000390', 'activiti$444401', 'Approve', '001723', '2016-10-29 19:36:19.978+07', 'activiti$444598', '2016-10-29 19:34:02.749788+07', '002648', '001509', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2248, 'PCM_ORD', 'PD16000167', 'activiti$445047', 'Approve', '000165', '2016-10-29 21:34:58.365+07', 'activiti$445236', '2016-10-29 21:07:44.640633+07', '003556', '000165', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2250, 'EXP_BRW', 'AV17000290', 'activiti$445429', 'Paid', '001723', '2016-11-01 07:59:00.942+07', 'activiti$445628', '2016-11-01 07:55:16.462204+07', '002648', '000090', '', 'จ่ายเงิน');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2247, 'EXP_BRW', 'AV17000289', 'activiti$444660', 'Approve', '001723', '2016-10-29 19:45:03.096+07', 'activiti$444859', '2016-10-29 19:42:38.525174+07', '002648', '000090', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2249, 'PCM_ORD', 'PD16000157', 'activiti$445295', 'Approve', '001028', '2016-10-29 21:39:01.457+07', 'activiti$start445295', '2016-10-29 21:37:23.726315+07', '003556', '001028', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2251, 'EXP_USE', 'EX17000392', 'activiti$445691', 'Approve', '001723', '2016-11-01 08:02:50.417+07', 'activiti$start445691', '2016-11-01 08:00:40.521538+07', '002648', '001509', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2252, 'EXP_BRW', 'AV17000292', 'activiti$445829', 'Paid', '001723', '2016-11-01 11:19:01.473+07', 'activiti$446369', '2016-11-01 11:00:09.556681+07', '002648', '000086', '', 'จ่ายเงิน');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2253, 'EXP_BRW', 'AV17000293', 'activiti$446049', 'Approve', '001509', '2016-11-01 11:13:42.083+07', 'activiti$446248', '2016-11-01 11:06:50.022736+07', '002648', '000511', '', 'อนุมัติ');
INSERT INTO pb2_main_workflow (id, type, master_id, workflow_ins_id, status, by, by_time, task_id, created_time, created_by, assignee, execution_id, status_th) VALUES (2254, 'EXP_USE', 'EX17000393', 'activiti$446432', 'Start', '002648', '2016-11-01 11:23:22.370182+07', 'activiti$start446432', '2016-11-01 11:23:22.370182+07', '002648', '000511', '', 'ขออนุมัติ');


--
-- TOC entry 2948 (class 0 OID 72605)
-- Dependencies: 362
-- Data for Name: pb2_main_workflow_history; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9046, 2210, '2016-10-24 14:49:02.499+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9047, 2210, '2016-10-24 14:49:02.499+07', '002648', 'Start', 'Preparer', 'ซื้อ ผู้ขอเบิก ใช้ งบหน่วยงาน ตัวเอง ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9048, 2210, '2016-10-24 14:57:39.399+07', '001509', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9049, 2210, '2016-10-24 14:58:20.049+07', '000511', 'Approve', 'Reviewer 2', 'Ok', 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9050, 2210, '2016-10-24 14:59:31.274+07', '003556', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9051, 2211, '2016-10-24 15:17:24.524+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9052, 2211, '2016-10-24 15:17:24.524+07', '002648', 'Start', 'Preparer', 'ซื้อ ผู้ขอเบิกใช้ งบหน่วยงาน อื่น ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9053, 2211, '2016-10-24 15:19:50.873+07', '000110', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9054, 2211, '2016-10-24 15:21:32.002+07', '001923', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9055, 2212, '2016-10-24 15:36:19.946+07', '000511', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9056, 2212, '2016-10-24 15:36:19.946+07', '002648', 'Start', 'Preparer', 'ซื้อ ผู้ขอเบิกใช้ งบหน่วยงาน อื่น ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9057, 2212, '2016-10-24 15:37:16.326+07', '000090', 'Approve', 'Reviewer 1', 'Ok krub', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9058, 2212, '2016-10-24 15:38:36.054+07', '003556', 'Cancel', 'Procurement', 'ยกเลิกโดยพัสดุ', NULL, 'X2', 'ยกเลิก', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9059, 2213, '2016-10-24 15:41:38.983+07', '000511', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9060, 2213, '2016-10-24 15:41:38.983+07', '000511', 'Start', 'Preparer', 'ซื้อ PM ขอเบิก ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9061, 2213, '2016-10-24 15:43:19.116+07', '000090', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9062, 2214, '2016-10-24 15:44:23.443+07', '003556', 'Start', 'Preparer', 'ผู้ขอเบิก ใช้ งบหน่วยงาน ตัวเอง', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9063, 2214, '2016-10-24 15:44:23.443+07', '002840', 'Approve', 'Supervisor', '', 0, NULL, 'อนุมัติ', 'ผู้ตรวจสอบ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9064, 2214, '2016-10-24 15:50:32.129+07', '001028', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9065, 2215, '2016-10-24 15:51:39.404+07', '003556', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9066, 2215, '2016-10-24 15:51:39.404+07', '003556', 'Start', 'Preparer', 'ซื้อ กระดาษ A4 ใกล้หมด', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9067, 2215, '2016-10-24 15:52:15.525+07', '003390', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9068, 2215, '2016-10-24 15:53:30.941+07', '000272', 'Approve', 'Reviewer 2', NULL, 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9069, 2216, '2016-10-24 16:01:36.662+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9070, 2216, '2016-10-24 16:01:36.662+07', '002648', 'Start', 'Preparer', 'ซื้อ PM มีวงเงินพิเศษที่พอ ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9071, 2216, '2016-10-24 16:02:11.102+07', '000511', 'Approve', 'Reviewer 1', 'Go!!!', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9072, 2216, '2016-10-24 16:03:42.45+07', '003556', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9073, 2217, '2016-10-24 16:06:18.824+07', '003556', 'Start', 'Preparer', 'PM มีวงเงินพิเศษที่พอ', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9074, 2217, '2016-10-24 16:06:18.824+07', '002840', 'Approve', 'Supervisor', '', 0, NULL, 'อนุมัติ', 'ผู้ตรวจสอบ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9075, 2215, '2016-10-24 16:09:11.043+07', '003556', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9076, 2218, '2016-10-24 16:12:56.148+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9077, 2218, '2016-10-24 16:12:56.163+07', '002648', 'Start', 'Preparer', 'ซื้อ PM มีวงเงินพิเศษที่ไม่พอ ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9078, 2218, '2016-10-24 16:13:39.008+07', '000511', 'Reject', 'Reviewer 1', 'Please cancel this request krub', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9079, 2218, '2016-10-24 16:14:42.727+07', '002648', 'Cancel', 'Preparer', 'As you wish :)', 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9080, 2219, '2016-10-24 16:20:15.942+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9081, 2219, '2016-10-24 16:20:15.957+07', '002648', 'Start', 'Preparer', 'ซื้อ PM ไม่มีวงเงินพิเศษ ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9082, 2219, '2016-10-24 16:21:04.908+07', '000926', 'Reject', 'Reviewer 1', 'ห้ามใช้เงินโครงการผม', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9083, 2219, '2016-10-24 16:21:59.594+07', '002648', 'Resubmit', 'Preparer', 'เปลี่ยนโครงการแล้วค่ะ', 0, 'W1', 'ขออนุมัติใหม่', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9084, 2219, '2016-10-24 16:22:45.901+07', '000511', 'Reject', 'Reviewer 1', 'ผ่านแล้วยกเลิกได้เลย', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9085, 2219, '2016-10-24 16:23:00.801+07', '002648', 'Cancel', 'Preparer', 'รับทราบ', 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9086, 2220, '2016-10-24 17:18:15.327+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9087, 2220, '2016-10-24 17:18:15.327+07', '002648', 'Start', 'Preparer', 'จ้าง PR ข้ามปีงบประมาณ ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9088, 2220, '2016-10-24 17:19:27.629+07', '001509', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9091, 2220, '2016-10-24 17:23:33.187+07', '003556', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9089, 2220, '2016-10-24 17:19:45.526+07', '000511', 'Approve', 'Reviewer 2', 'Ok', 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9094, 2222, '2016-10-24 17:33:37.469+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9095, 2222, '2016-10-24 17:33:37.469+07', '002648', 'Start', 'Preparer', 'ซื้อ PR เพิ่มเติม ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9090, 2220, '2016-10-24 17:20:07.641+07', '000090', 'Approve', 'Reviewer 3', 'Ok', 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9092, 2221, '2016-10-24 17:25:15.638+07', '003556', 'Start', 'Preparer', 'PR ข้ามปีงบประมาณ', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9093, 2221, '2016-10-24 17:25:15.638+07', '002840', 'Approve', 'Supervisor', '', 0, NULL, 'อนุมัติ', 'ผู้ตรวจสอบ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9096, 2217, '2016-10-24 17:34:29.104+07', '001028', 'Reject', 'Reviewer 1', 'ยกเลิกค่ะ', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9097, 2221, '2016-10-24 17:34:36.167+07', '001028', 'Reject', 'Reviewer 1', 'ยกเลิกค่ะ', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9098, 2221, '2016-10-24 17:35:30.379+07', '003556', 'Cancel', 'Preparer', 'ตามนั้น', 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9099, 2217, '2016-10-24 17:35:47.083+07', '003556', 'Cancel', 'Preparer', 'ตามใจ', 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9100, 2222, '2016-10-24 17:37:04.643+07', '001509', 'Consult', 'Reviewer 1', 'ขอคำปรึกษา', 1, 'S', 'ขอคำปรึกษา', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9101, 2222, '2016-10-24 17:38:03.849+07', '002648', 'Comment', 'Consultant', 'คิดว่าใช่', 1, 'W1', 'ให้ความเห็น', 'ที่ปรึกษาให้ความเห็น');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9102, 2222, '2016-10-24 17:39:31.125+07', '001509', 'Reject', 'Reviewer 1', 'ยกเลิก', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9103, 2222, '2016-10-24 17:39:44.11+07', '002648', 'Cancel', 'Preparer', NULL, 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9104, 2223, '2016-10-24 17:41:31.914+07', '000511', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9105, 2223, '2016-10-24 17:41:31.914+07', '000511', 'Start', 'Preparer', 'ซื้อ PM ขอเบิกมีวงเงินพิเศษที่พอ ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9106, 2224, '2016-10-24 17:46:44.799+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9107, 2224, '2016-10-24 17:46:44.799+07', '002648', 'Start', 'Preparer', 'ยืมเพื่อซื้อวัสดุ/สินค้า ใช้งบหน่วยงานตัวเอง', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9108, 2224, '2016-10-24 17:47:57.526+07', '001509', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9109, 2224, '2016-10-24 17:48:29.648+07', '000511', 'Approve', 'Reviewer 2', 'Ok', 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9110, 2223, '2016-10-24 17:48:50.675+07', '000090', 'Reject', 'Reviewer 1', 'ยกเลิกซะ', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9111, 2224, '2016-10-24 17:49:02.972+07', '000090', 'Approve', 'Reviewer 3', 'Ok', 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9112, 2224, '2016-10-24 17:50:23.984+07', '001723', 'Approve', 'Finance', NULL, NULL, 'C2', 'อนุมัติ', 'การเงิน');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9113, 2224, '2016-10-24 17:51:00.201+07', '001723', 'Paid', '', 'วันที่เช็ค/โอน 2016-10-24', 0, '', 'จ่ายเงิน', '');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9114, 2225, '2016-10-24 17:55:28.447+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9115, 2225, '2016-10-24 17:55:28.447+07', '002648', 'Start', 'Preparer', 'ทดสอบเบิกใช้งบหน่วยงานตัวเอง', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9116, 2225, '2016-10-24 18:06:23.143+07', '001509', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9117, 2225, '2016-10-24 18:07:07.5+07', '001723', 'Cancel', 'Finance', 'ไม่ให้เบิกแล้วค่ะ', NULL, 'X2', 'ยกเลิก', 'การเงิน');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9118, 2226, '2016-10-25 08:35:42.941+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9119, 2226, '2016-10-25 08:35:42.941+07', '002648', 'Start', 'Preparer', 'ทดสอบเบิกใช้งบหน่วยงานอื่น', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9120, 2226, '2016-10-25 08:36:38.58+07', '001558', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9121, 2226, '2016-10-25 08:37:27.187+07', '001723', 'Approve', 'Finance', NULL, NULL, 'C2', 'อนุมัติ', 'การเงิน');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9122, 2227, '2016-10-25 08:40:13.531+07', '001509', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9123, 2227, '2016-10-25 08:40:13.531+07', '002648', 'Start', 'Preparer', 'ทำแทนผู้ขอเบิก', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9124, 2228, '2016-10-25 08:41:04.047+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9125, 2228, '2016-10-25 08:41:04.047+07', '002648', 'Start', 'Preparer', 'ทำแทนผู้ขอเบิก #2', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9126, 2229, '2016-10-25 08:42:14.063+07', '001509', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9127, 2229, '2016-10-25 08:42:14.063+07', '002648', 'Start', 'Preparer', 'ทำแทนผู้ขอเบิก #3', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9128, 2223, '2016-10-25 08:43:03.237+07', '000511', 'Cancel', 'Preparer', NULL, 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9129, 2229, '2016-10-25 08:43:13.628+07', '000511', 'Reject', 'Reviewer 1', 'ยกเลิก', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9130, 2228, '2016-10-25 08:43:33.769+07', '001509', 'Reject', 'Reviewer 1', 'Please cancel this request', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9131, 2227, '2016-10-25 08:44:01.754+07', '001558', 'Reject', 'Reviewer 1', 'Cancel this request', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9132, 2229, '2016-10-25 08:44:09.208+07', '002648', 'Cancel', 'Preparer', NULL, 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9133, 2228, '2016-10-25 08:44:13.49+07', '002648', 'Cancel', 'Preparer', NULL, 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9134, 2227, '2016-10-25 08:44:18.022+07', '002648', 'Cancel', 'Preparer', NULL, 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9135, 2230, '2016-10-25 08:46:49.966+07', '000511', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9136, 2230, '2016-10-25 08:46:49.966+07', '000511', 'Start', 'Preparer', 'ผู้ขอเบิกเป็น PM', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9137, 2230, '2016-10-25 08:47:37.671+07', '000090', 'Reject', 'Reviewer 1', 'สายถูกต้องค่ะ', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9138, 2230, '2016-10-25 08:47:50.117+07', '000511', 'Cancel', 'Preparer', 'Noted krub', 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9139, 2231, '2016-10-25 08:51:04.419+07', '000090', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9140, 2231, '2016-10-25 08:51:04.419+07', '000090', 'Start', 'Preparer', 'ผู้ขอเบิก ไม่เป็น PM + มีวงเงินพิเศษ ที่พอ', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9141, 2231, '2016-10-25 08:51:54.188+07', '000511', 'Reject', 'Reviewer 1', 'Approval Line is correct krub.', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9142, 2231, '2016-10-25 08:52:07.692+07', '000090', 'Cancel', 'Preparer', NULL, 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9143, 2232, '2016-10-25 08:53:14.682+07', '000090', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9144, 2232, '2016-10-25 08:53:14.682+07', '000090', 'Start', 'Preparer', 'ผู้ขอเบิก ไม่เป็น PM + มีวงเงินพิเศษ ที่ไม่พอ', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9145, 2233, '2016-10-25 08:58:51.958+07', '000090', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9146, 2233, '2016-10-25 08:58:51.958+07', '000090', 'Start', 'Preparer', 'ผู้ขอเบิก ไม่เป็น PM + ไม่มีวงเงินพิเศษ', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9147, 2232, '2016-10-25 08:59:30.522+07', '000511', 'Reject', 'Reviewer 1', 'Cancel this request', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9148, 2233, '2016-10-25 08:59:36.772+07', '000511', 'Reject', 'Reviewer 1', 'Cancel this request', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9149, 2233, '2016-10-25 08:59:52.56+07', '000090', 'Cancel', 'Preparer', NULL, 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9150, 2232, '2016-10-25 09:01:05.537+07', '000090', 'Resubmit', 'Preparer', 'Please review and approve.', 0, 'W1', 'ขออนุมัติใหม่', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9151, 2232, '2016-10-25 09:02:11.109+07', '000511', 'Consult', 'Reviewer 1', 'Por, are you agree with this request. Please suggest me.', 1, 'S', 'ขอคำปรึกษา', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9152, 2232, '2016-10-25 09:02:34.97+07', '002648', 'Comment', 'Consultant', 'agree ka.', 1, 'W1', 'ให้ความเห็น', 'ที่ปรึกษาให้ความเห็น');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9153, 2232, '2016-10-25 09:03:15.822+07', '000511', 'Reject', 'Reviewer 1', 'Ok. Please cancel this request.', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9154, 2232, '2016-10-25 09:03:22.947+07', '000090', 'Resubmit', 'Preparer', NULL, 0, 'W1', 'ขออนุมัติใหม่', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9155, 2232, '2016-10-25 09:03:37.126+07', '000511', 'Reject', 'Reviewer 1', 'Please cancel this request.', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9156, 2232, '2016-10-25 09:03:44.876+07', '000090', 'Cancel', 'Preparer', NULL, 0, 'X1', 'ยกเลิก', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9157, 2234, '2016-10-25 09:05:58.069+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9158, 2234, '2016-10-25 09:05:58.085+07', '002648', 'Start', 'Preparer', 'ยืมเพื่อซื้อวัสดุ/สินค้า ใช้งบหน่วยงานอื่น', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9159, 2234, '2016-10-25 09:06:28.828+07', '001558', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9160, 2234, '2016-10-25 09:07:31.761+07', '000511', 'Approve', 'Reviewer 2', NULL, 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9161, 2234, '2016-10-25 09:07:52.403+07', '000090', 'Approve', 'Reviewer 3', NULL, 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9162, 2234, '2016-10-25 09:08:27.148+07', '001723', 'Cancel', 'Finance', 'เปลี่ยนใจ ยกเลิกการยืมไปแล้วค่ะ', NULL, 'X2', 'ยกเลิก', 'การเงิน');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9163, 2235, '2016-10-25 10:25:04.729+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9164, 2235, '2016-10-25 10:25:04.729+07', '002648', 'Start', 'Preparer', 'ซื้อ ทดสอบสกุลเงินต่างประเทศ ใกล้หมด', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9165, 2235, '2016-10-25 10:25:31.855+07', '001509', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9166, 2236, '2016-10-25 11:06:16.108+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9167, 2236, '2016-10-25 11:06:16.108+07', '002648', 'Start', 'Preparer', 'ซื้อ ทดสอบสอบด้วยวิธีสอบราคา ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9168, 2236, '2016-10-25 11:06:54.594+07', '001509', 'Reject', 'Reviewer 1', 'เอากลับไปแก้', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9169, 2237, '2016-10-25 16:38:55.51+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9170, 2237, '2016-10-25 16:38:55.51+07', '002648', 'Start', 'Preparer', 'ทดสอบเบิก', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9171, 2237, '2016-10-25 16:48:01.513+07', '001509', 'Reject', 'Reviewer 1', 'ไปแก้เพิ่มเอกสารแนบมา', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9172, 2236, '2016-10-25 16:49:12.598+07', '002648', 'Resubmit', 'Preparer', 'จะเอาไปทำใบ PD', 0, 'W1', 'ขออนุมัติใหม่', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9173, 2236, '2016-10-25 16:49:39.545+07', '001509', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9174, 2236, '2016-10-25 16:49:54.42+07', '000511', 'Approve', 'Reviewer 2', NULL, 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9175, 2236, '2016-10-25 16:50:23.575+07', '000090', 'Approve', 'Reviewer 3', NULL, 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9176, 2236, '2016-10-25 16:51:43.694+07', '003556', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9177, 2238, '2016-10-25 16:53:44.353+07', '003556', 'Start', 'Preparer', 'ทดสอบสอบด้วยวิธีสอบราคา', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9178, 2238, '2016-10-25 16:53:44.353+07', '002840', 'Approve', 'Supervisor', '', 0, NULL, 'อนุมัติ', 'ผู้ตรวจสอบ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9179, 2238, '2016-10-25 17:01:22.616+07', '001028', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9180, 2238, '2016-10-25 17:02:54.926+07', '000086', 'Approve', 'Reviewer 2', 'Ok', 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9181, 2235, '2016-10-25 17:07:55.795+07', '003556', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9182, 2239, '2016-10-25 17:09:37.734+07', '003556', 'Start', 'Preparer', 'ทดสอบสกุลเงินต่างประเทศ', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9183, 2239, '2016-10-25 17:09:37.734+07', '002840', 'Approve', 'Supervisor', '', 0, NULL, 'อนุมัติ', 'ผู้ตรวจสอบ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9184, 2239, '2016-10-25 17:11:32.063+07', '001028', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9185, 2240, '2016-10-26 17:22:05.199+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9186, 2240, '2016-10-26 17:22:05.199+07', '002648', 'Start', 'Preparer', 'ซื้อ ทดสอบสร้างใบ พด. ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9187, 2240, '2016-10-26 17:23:11.588+07', '000110', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9188, 2240, '2016-10-26 17:25:42.402+07', '001923', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9189, 2213, '2016-10-26 17:31:09.707+07', '003556', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9190, 2241, '2016-10-26 17:33:29.186+07', '003556', 'Start', 'Preparer', 'PM ขอเบิก', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9191, 2241, '2016-10-26 17:33:29.186+07', '002840', 'Approve', 'Supervisor', '', 0, NULL, 'อนุมัติ', 'ผู้ตรวจสอบ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9192, 2241, '2016-10-26 17:34:48.986+07', '001028', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9193, 2242, '2016-10-27 16:29:45.025+07', '000511', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9194, 2242, '2016-10-27 16:29:45.025+07', '000511', 'Start', 'Preparer', 'จ้าง จ้างซักผ้า อื่นๆ จ้างซักผ้าประจำปี 2560', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9195, 2242, '2016-10-27 16:31:04.26+07', '000090', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9196, 2242, '2016-10-27 16:31:39.916+07', '000086', 'Approve', 'Reviewer 2', NULL, 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9197, 2242, '2016-10-27 16:32:27.682+07', '000165', 'Approve', 'Reviewer 3', NULL, 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9198, 2242, '2016-10-27 16:37:12.293+07', '003556', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9203, 2243, '2016-10-27 17:11:15.2+07', '000165', 'Approve', 'Reviewer 3', NULL, 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9204, 2244, '2016-10-27 17:36:09.727+07', '000511', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9205, 2244, '2016-10-27 17:36:09.727+07', '000511', 'Start', 'Preparer', 'จ้าง จ้างซักผ้า อื่นๆ จ้างซักผ้าประจำปี 2560', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9206, 2244, '2016-10-27 17:37:15.71+07', '000090', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9199, 2243, '2016-10-27 17:05:09.945+07', '003556', 'Start', 'Preparer', 'จ้างซักผ้า', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9200, 2243, '2016-10-27 17:05:09.945+07', '002840', 'Approve', 'Supervisor', '', 0, NULL, 'อนุมัติ', 'ผู้ตรวจสอบ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9201, 2243, '2016-10-27 17:09:44.026+07', '001028', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9202, 2243, '2016-10-27 17:10:19.075+07', '000086', 'Approve', 'Reviewer 2', NULL, 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9209, 2244, '2016-10-27 17:41:29.023+07', '003556', 'Approve', 'Procurement', NULL, NULL, 'C2', 'อนุมัติ', 'ฝ่ายพัสดุ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9207, 2244, '2016-10-27 17:37:38.819+07', '000086', 'Approve', 'Reviewer 2', NULL, 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9208, 2244, '2016-10-27 17:38:13.867+07', '000165', 'Approve', 'Reviewer 3', NULL, 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9210, 2245, '2016-10-27 17:44:10.731+07', '003556', 'Start', 'Preparer', 'จ้างซักผ้า', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9211, 2245, '2016-10-27 17:44:10.731+07', '002840', 'Approve', 'Supervisor', '', 0, NULL, 'อนุมัติ', 'ผู้ตรวจสอบ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9212, 2245, '2016-10-27 17:45:49.621+07', '001028', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9213, 2245, '2016-10-27 17:46:10.981+07', '000086', 'Approve', 'Reviewer 2', NULL, 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9214, 2245, '2016-10-27 17:46:24.872+07', '000165', 'Approve', 'Reviewer 3', NULL, 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9215, 2246, '2016-10-29 19:34:02.743+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9216, 2246, '2016-10-29 19:34:02.743+07', '002648', 'Start', 'Preparer', 'ทดสอบเบิกจ่ายเพิ่มฟิลด์รายละเอียด', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9217, 2246, '2016-10-29 19:34:44.698+07', '001509', 'Reject', 'Reviewer 1', 'ko', 1, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9218, 2246, '2016-10-29 19:35:48.337+07', '002648', 'Resubmit', 'Preparer', NULL, 0, 'W1', 'ขออนุมัติใหม่', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9219, 2246, '2016-10-29 19:36:02.916+07', '001509', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9220, 2246, '2016-10-29 19:36:19.978+07', '001723', 'Approve', 'Finance', NULL, NULL, 'C2', 'อนุมัติ', 'การเงิน');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9221, 2247, '2016-10-29 19:42:38.512+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9222, 2247, '2016-10-29 19:42:38.527+07', '002648', 'Start', 'Preparer', 'ยืมเพื่อซื้อวัสดุ/สินค้า ยืมเงินเพื่อทดสอบทำรายการเคลียร์เงินยืม', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9223, 2247, '2016-10-29 19:43:58.51+07', '001509', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9224, 2247, '2016-10-29 19:44:18.772+07', '000511', 'Approve', 'Reviewer 2', NULL, 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9225, 2247, '2016-10-29 19:44:37.397+07', '000090', 'Approve', 'Reviewer 3', NULL, 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9226, 2247, '2016-10-29 19:45:03.096+07', '001723', 'Approve', 'Finance', NULL, NULL, 'C2', 'อนุมัติ', 'การเงิน');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9227, 2237, '2016-10-29 20:08:39.698+07', '002648', 'Resubmit', 'Preparer', 'Ok', 0, 'W1', 'ขออนุมัติใหม่', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9228, 2248, '2016-10-29 21:07:44.637+07', '003556', 'Start', 'Preparer', 'จ้างซักผ้า', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9229, 2248, '2016-10-29 21:07:44.637+07', '002840', 'Approve', 'Supervisor', '', 0, NULL, 'อนุมัติ', 'ผู้ตรวจสอบ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9230, 2248, '2016-10-29 21:09:07.391+07', '001028', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9231, 2248, '2016-10-29 21:09:33.736+07', '000086', 'Approve', 'Reviewer 2', NULL, 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9232, 2248, '2016-10-29 21:34:58.365+07', '000165', 'Approve', 'Reviewer 3', NULL, 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9233, 2249, '2016-10-29 21:37:23.723+07', '003556', 'Start', 'Preparer', 'กระดาษ A4', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9234, 2249, '2016-10-29 21:37:23.723+07', '002840', 'Approve', 'Supervisor', '', 0, NULL, 'อนุมัติ', 'ผู้ตรวจสอบ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9235, 2249, '2016-10-29 21:39:01.457+07', '001028', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9236, 2250, '2016-11-01 07:55:16.448+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9237, 2250, '2016-11-01 07:55:16.464+07', '002648', 'Start', 'Preparer', 'ยืมเพื่อซื้อวัสดุ/สินค้า ยืมเงินเพื่อซื้อของที่ระลึก', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9238, 2250, '2016-11-01 07:55:50.543+07', '001509', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9239, 2250, '2016-11-01 07:56:10.325+07', '000511', 'Approve', 'Reviewer 2', 'Ok', 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9240, 2250, '2016-11-01 07:56:33.077+07', '000090', 'Approve', 'Reviewer 3', 'Ok', 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9241, 2250, '2016-11-01 07:58:15.669+07', '001723', 'Approve', 'Finance', NULL, NULL, 'C2', 'อนุมัติ', 'การเงิน');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9242, 2250, '2016-11-01 07:59:00.926+07', '001723', 'Paid', '', 'วันที่เช็ค/โอน 2016-11-01', 0, '', 'จ่ายเงิน', '');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9243, 2251, '2016-11-01 08:00:40.51+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9244, 2251, '2016-11-01 08:00:40.525+07', '002648', 'Start', 'Preparer', 'เคลียร์เงินยืมพนักงาน ซื้อของที่ระลึก', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9245, 2251, '2016-11-01 08:02:00.555+07', '001509', 'Approve', 'Reviewer 1', 'Ok', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9246, 2251, '2016-11-01 08:02:50.417+07', '001723', 'Approve', 'Finance', NULL, NULL, 'C2', 'อนุมัติ', 'การเงิน');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9247, 2252, '2016-11-01 11:00:09.55+07', '001509', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9248, 2252, '2016-11-01 11:00:09.55+07', '002648', 'Start', 'Preparer', 'ยืมเพื่อเดินทางสัมมนา Odoo 2016', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9249, 2252, '2016-11-01 11:02:46.021+07', '000511', 'Approve', 'Reviewer 1', 'อนุมัติ แต่ให้รีบเคลียร์เงินเก่าด้วย', 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9250, 2252, '2016-11-01 11:04:17.188+07', '000090', 'Reject', 'Reviewer 2', 'ปอ ไม่เคลียร์ ไม่ให้ยืม', 2, 'W2', 'ไม่อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9251, 2253, '2016-11-01 11:06:50.025+07', '002648', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9252, 2253, '2016-11-01 11:06:50.025+07', '002648', 'Start', 'Preparer', 'ยืมเพื่อเดินทางสัมมนา Odoo 2016', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9253, 2252, '2016-11-01 11:13:27.629+07', '002648', 'Resubmit', 'Preparer', NULL, 0, 'W1', 'ขออนุมัติใหม่', 'ผู้บันทึก');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9254, 2253, '2016-11-01 11:13:41.239+07', '001509', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9255, 2252, '2016-11-01 11:14:33.341+07', '000511', 'Approve', 'Reviewer 1', NULL, 1, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 1');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9256, 2252, '2016-11-01 11:15:24.296+07', '000090', 'Approve', 'Reviewer 2', NULL, 2, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 2');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9257, 2252, '2016-11-01 11:16:03.829+07', '000086', 'Approve', 'Reviewer 3', NULL, 3, 'W1', 'อนุมัติ', 'ผู้อนุมัติขั้นที่ 3');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9258, 2252, '2016-11-01 11:18:21.58+07', '001723', 'Approve', 'Finance', NULL, NULL, 'C2', 'อนุมัติ', 'การเงิน');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9259, 2252, '2016-11-01 11:19:01.473+07', '001723', 'Paid', '', 'วันที่เช็ค/โอน 2016-11-01', 0, '', 'จ่ายเงิน', '');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9260, 2254, '2016-11-01 11:23:22.365+07', '001509', '', 'Requester', '', 0, NULL, '', 'ผู้ขอ');
INSERT INTO pb2_main_workflow_history (id, master_id, "time", by, action, task, comment, level, status, action_th, task_th) VALUES (9261, 2254, '2016-11-01 11:23:22.365+07', '002648', 'Start', 'Preparer', 'เคลียร์เงินยืม odoo 2016', 0, 'W1', 'ขออนุมัติ', 'ผู้บันทึก');


--
-- TOC entry 3046 (class 0 OID 0)
-- Dependencies: 363
-- Name: pb2_main_workflow_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_main_workflow_history_id_seq', 9261, true);


--
-- TOC entry 3047 (class 0 OID 0)
-- Dependencies: 364
-- Name: pb2_main_workflow_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_main_workflow_id_seq', 2254, true);


--
-- TOC entry 2951 (class 0 OID 72615)
-- Dependencies: 365
-- Data for Name: pb2_main_workflow_next_actor; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1686, 'PR17000367', 1, NULL, '002840', '2016-10-24 14:49:00.69154+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1687, 'PR17000368', 1, NULL, '000110', '2016-10-24 15:17:22.78409+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1688, 'PR17000369', 1, NULL, '002840', '2016-10-24 15:36:18.277432+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1689, 'PR17000370', 1, NULL, '002840', '2016-10-24 15:41:37.289693+07', '000511', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1690, 'PR17000371', 1, NULL, '002840', '2016-10-24 15:51:37.540718+07', '003556', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1691, 'PR17000372', 1, NULL, '002840', '2016-10-24 16:01:34.897689+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1692, 'PR17000373', 1, NULL, '002840', '2016-10-24 16:12:54.286688+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1694, 'PR17000374', 1, NULL, '002840', '2016-10-24 16:21:37.954933+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1695, 'PR17000375', 1, NULL, '002840', '2016-10-24 17:18:13.529487+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1696, 'PR17000377', 1, NULL, '002840', '2016-10-24 17:33:34.924331+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1697, 'PR17000378', 1, NULL, '002840', '2016-10-24 17:41:30.311246+07', '000511', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1698, 'AV17000287', 1, NULL, 'XXXXXX', '2016-10-24 17:46:42.920667+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1699, 'EX17000380', 1, NULL, 'XXXXXX', '2016-10-24 17:55:26.690026+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1700, 'EX17000381', 1, NULL, 'XXXXXX', '2016-10-25 08:35:41.308819+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1701, 'EX17000382', 1, NULL, 'XXXXXX', '2016-10-25 08:40:11.986548+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1702, 'EX17000383', 1, NULL, 'XXXXXX', '2016-10-25 08:41:02.231932+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1703, 'EX17000384', 1, NULL, 'XXXXXX', '2016-10-25 08:42:12.497189+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1704, 'EX17000385', 1, NULL, 'XXXXXX', '2016-10-25 08:46:48.252148+07', '000511', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1705, 'EX17000386', 1, NULL, 'XXXXXX', '2016-10-25 08:51:02.684997+07', '000090', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1707, 'EX17000388', 1, NULL, 'XXXXXX', '2016-10-25 08:58:50.176784+07', '000090', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1708, 'EX17000387', 1, NULL, 'XXXXXX', '2016-10-25 09:00:35.688199+07', '000090', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1709, 'AV17000288', 1, NULL, 'XXXXXX', '2016-10-25 09:05:56.156645+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1710, 'PR17000379', 1, NULL, '002840', '2016-10-25 10:25:02.952729+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1711, 'PR17000380', 1, NULL, '002840', '2016-10-25 11:06:14.270338+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1713, 'PR17000381', 1, NULL, '000110', '2016-10-26 17:22:03.39503+07', '002648', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1714, 'PR17000382', 1, NULL, '002840', '2016-10-27 16:29:43.20706+07', '000511', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1715, 'PR17000383', 1, NULL, '002840', '2016-10-27 17:36:08.057678+07', '000511', 'Procurement Supervisor');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1716, 'EX17000390', 1, NULL, 'XXXXXX', '2016-10-29 19:34:00.925261+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1717, 'AV17000289', 1, NULL, 'XXXXXX', '2016-10-29 19:42:36.630773+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1718, 'EX17000389', 1, NULL, 'XXXXXX', '2016-10-29 20:08:21.456084+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1719, 'AV17000290', 1, NULL, 'XXXXXX', '2016-11-01 07:55:14.660479+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1720, 'EX17000392', 1, NULL, 'XXXXXX', '2016-11-01 08:00:39.053468+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1721, 'AV17000292', 1, NULL, 'XXXXXX', '2016-11-01 11:00:07.428672+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1722, 'AV17000293', 1, NULL, 'XXXXXX', '2016-11-01 11:06:48.069541+07', '002648', 'Finance Officer');
INSERT INTO pb2_main_workflow_next_actor (id, master_id, level, actor_group, actor_user, created_time, created_by, actor) VALUES (1723, 'EX17000393', 1, NULL, 'XXXXXX', '2016-11-01 11:23:20.600902+07', '002648', 'Finance Officer');


--
-- TOC entry 3048 (class 0 OID 0)
-- Dependencies: 366
-- Name: pb2_main_workflow_next_actor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_main_workflow_next_actor_id_seq', 1723, true);


--
-- TOC entry 2953 (class 0 OID 72623)
-- Dependencies: 367
-- Data for Name: pb2_main_workflow_reviewer; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3583, 'PR17000367', 1, '', '001509', 0, 0, NULL, '2016-10-24 14:49:00.609759+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3584, 'PR17000367', 2, '', '000511', 0, 0, NULL, '2016-10-24 14:49:00.613303+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3585, 'PR17000368', 1, '', '000110', 0, 0, NULL, '2016-10-24 15:17:22.749894+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3586, 'PR17000369', 1, '', '000090', 0, 0, NULL, '2016-10-24 15:36:18.245046+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3587, 'PR17000370', 1, '', '000090', 0, 0, NULL, '2016-10-24 15:41:37.256276+07', '000511');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3588, 'PD16000154', 1, '', '001028', 0, 0, NULL, '2016-10-24 15:44:22.717869+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3589, 'PR17000371', 1, '', '003390', 0, 0, NULL, '2016-10-24 15:51:37.469614+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3590, 'PR17000371', 2, '', '000272', 0, 0, NULL, '2016-10-24 15:51:37.473192+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3591, 'PR17000372', 1, '', '000511', 0, 0, NULL, '2016-10-24 16:01:34.863535+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3592, 'PD16000156', 1, '', '001028', 0, 0, NULL, '2016-10-24 16:06:18.674421+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3593, 'PR17000373', 1, '', '000511', 0, 0, NULL, '2016-10-24 16:12:54.248368+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3594, 'PR17000373', 2, '', '000090', 0, 0, NULL, '2016-10-24 16:12:54.251571+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3597, 'PR17000374', 1, '', '000511', 0, 0, NULL, '2016-10-24 16:21:37.917646+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3598, 'PR17000375', 1, '', '001509', 0, 0, NULL, '2016-10-24 17:18:13.469207+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3599, 'PR17000375', 2, '', '000511', 0, 0, NULL, '2016-10-24 17:18:13.473671+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3600, 'PR17000375', 3, '', '000090', 0, 0, NULL, '2016-10-24 17:18:13.47529+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3601, 'PD16000158', 1, '', '001028', 0, 0, NULL, '2016-10-24 17:25:15.482848+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3602, 'PD16000158', 2, '', '000086', 0, 0, NULL, '2016-10-24 17:25:15.485015+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3603, 'PR17000377', 1, '', '001509', 0, 0, NULL, '2016-10-24 17:33:34.881204+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3604, 'PR17000377', 2, '', '000511', 0, 0, NULL, '2016-10-24 17:33:34.884455+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3605, 'PR17000377', 3, '', '000090', 0, 0, NULL, '2016-10-24 17:33:34.886439+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3606, 'PR17000378', 1, '', '000090', 0, 0, NULL, '2016-10-24 17:41:30.280813+07', '000511');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3607, 'AV17000287', 1, '', '001509', 0, 0, NULL, '2016-10-24 17:46:42.900239+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3608, 'AV17000287', 2, '', '000511', 0, 0, NULL, '2016-10-24 17:46:42.902864+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3609, 'AV17000287', 3, '', '000090', 0, 0, NULL, '2016-10-24 17:46:42.904742+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3610, 'EX17000380', 1, '', '001509', 0, 0, NULL, '2016-10-24 17:55:26.68098+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3611, 'EX17000381', 1, '', '001558', 0, 0, NULL, '2016-10-25 08:35:41.282391+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3612, 'EX17000382', 1, '', '001558', 0, 0, NULL, '2016-10-25 08:40:11.973469+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3613, 'EX17000383', 1, '', '001509', 0, 0, NULL, '2016-10-25 08:41:02.223044+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3614, 'EX17000384', 1, '', '000511', 0, 0, NULL, '2016-10-25 08:42:12.487978+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3615, 'EX17000385', 1, '', '000090', 0, 0, NULL, '2016-10-25 08:46:48.243325+07', '000511');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3616, 'EX17000386', 1, '', '000511', 0, 0, NULL, '2016-10-25 08:51:02.674722+07', '000090');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3619, 'EX17000388', 1, '', '000511', 0, 0, NULL, '2016-10-25 08:58:50.163286+07', '000090');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3620, 'EX17000388', 2, '', '000086', 0, 0, NULL, '2016-10-25 08:58:50.165645+07', '000090');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3621, 'EX17000387', 1, '', '000511', 0, 0, NULL, '2016-10-25 09:00:35.664078+07', '000090');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3622, 'EX17000387', 2, '', '000086', 0, 0, NULL, '2016-10-25 09:00:35.667497+07', '000090');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3623, 'AV17000288', 1, '', '001558', 0, 0, NULL, '2016-10-25 09:05:56.137519+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3624, 'AV17000288', 2, '', '000511', 0, 0, NULL, '2016-10-25 09:05:56.139457+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3625, 'AV17000288', 3, '', '000090', 0, 0, NULL, '2016-10-25 09:05:56.141157+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3626, 'PR17000379', 1, '', '001509', 0, 0, NULL, '2016-10-25 10:25:02.892732+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3627, 'PR17000380', 1, '', '001509', 0, 0, NULL, '2016-10-25 11:06:14.217517+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3628, 'PR17000380', 2, '', '000511', 0, 0, NULL, '2016-10-25 11:06:14.227847+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3629, 'PR17000380', 3, '', '000090', 0, 0, NULL, '2016-10-25 11:06:14.229233+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3631, 'PD16000159', 1, '', '001028', 0, 0, NULL, '2016-10-25 16:53:44.158949+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3632, 'PD16000159', 2, '', '000086', 0, 0, NULL, '2016-10-25 16:53:44.168062+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3633, 'PD16000160', 1, '', '001028', 0, 0, NULL, '2016-10-25 17:09:37.543499+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3634, 'PR17000381', 1, '', '000110', 0, 0, NULL, '2016-10-26 17:22:03.330483+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3635, 'PD16000164', 1, '', '001028', 0, 0, NULL, '2016-10-26 17:33:29.022326+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3636, 'PR17000382', 1, '', '000090', 0, 0, NULL, '2016-10-27 16:29:43.156565+07', '000511');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3637, 'PR17000382', 2, '', '000086', 0, 0, NULL, '2016-10-27 16:29:43.160249+07', '000511');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3638, 'PR17000382', 3, '', '000165', 0, 0, NULL, '2016-10-27 16:29:43.162252+07', '000511');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3639, 'PD16000165', 1, '', '001028', 0, 0, NULL, '2016-10-27 17:05:09.703091+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3640, 'PD16000165', 2, '', '000086', 0, 0, NULL, '2016-10-27 17:05:09.705551+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3641, 'PD16000165', 3, '', '000165', 0, 0, NULL, '2016-10-27 17:05:09.707498+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3642, 'PR17000383', 1, '', '000090', 0, 0, NULL, '2016-10-27 17:36:08.011098+07', '000511');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3643, 'PR17000383', 2, '', '000086', 0, 0, NULL, '2016-10-27 17:36:08.014068+07', '000511');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3644, 'PR17000383', 3, '', '000165', 0, 0, NULL, '2016-10-27 17:36:08.016041+07', '000511');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3645, 'PD16000166', 1, '', '001028', 0, 0, NULL, '2016-10-27 17:44:10.597933+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3646, 'PD16000166', 2, '', '000086', 0, 0, NULL, '2016-10-27 17:44:10.607132+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3647, 'PD16000166', 3, '', '000165', 0, 0, NULL, '2016-10-27 17:44:10.608305+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3648, 'EX17000390', 1, '', '001509', 0, 0, NULL, '2016-10-29 19:34:00.880696+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3649, 'AV17000289', 1, '', '001509', 0, 0, NULL, '2016-10-29 19:42:36.606213+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3650, 'AV17000289', 2, '', '000511', 0, 0, NULL, '2016-10-29 19:42:36.609469+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3651, 'AV17000289', 3, '', '000090', 0, 0, NULL, '2016-10-29 19:42:36.610991+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3652, 'EX17000389', 1, '', '001509', 0, 0, NULL, '2016-10-29 20:08:21.441101+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3653, 'PD16000167', 1, '', '001028', 0, 0, NULL, '2016-10-29 21:07:44.436978+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3654, 'PD16000167', 2, '', '000086', 0, 0, NULL, '2016-10-29 21:07:44.439015+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3655, 'PD16000167', 3, '', '000165', 0, 0, NULL, '2016-10-29 21:07:44.440949+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3656, 'PD16000157', 1, '', '001028', 0, 0, NULL, '2016-10-29 21:37:23.559694+07', '003556');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3657, 'AV17000290', 1, '', '001509', 0, 0, NULL, '2016-11-01 07:55:14.635662+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3658, 'AV17000290', 2, '', '000511', 0, 0, NULL, '2016-11-01 07:55:14.640777+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3659, 'AV17000290', 3, '', '000090', 0, 0, NULL, '2016-11-01 07:55:14.642546+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3660, 'EX17000392', 1, '', '001509', 0, 0, NULL, '2016-11-01 08:00:39.044283+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3661, 'AV17000292', 1, '', '000511', 0, 0, NULL, '2016-11-01 11:00:07.401428+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3662, 'AV17000292', 2, '', '000090', 0, 0, NULL, '2016-11-01 11:00:07.404691+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3663, 'AV17000292', 3, '', '000086', 0, 0, NULL, '2016-11-01 11:00:07.408853+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3664, 'AV17000293', 1, '', '001509', 0, 0, NULL, '2016-11-01 11:06:48.042336+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3665, 'AV17000293', 2, '', '000511', 0, 0, NULL, '2016-11-01 11:06:48.045902+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3666, 'AV17000293', 3, '', '000090', 0, 0, NULL, '2016-11-01 11:06:48.048512+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3667, 'AV17000293', 4, '', '000086', 0, 0, NULL, '2016-11-01 11:06:48.050046+07', '002648');
INSERT INTO pb2_main_workflow_reviewer (id, master_id, level, reviewer_group, reviewer_user, percent, rewarning, hint, created_time, created_by) VALUES (3668, 'EX17000393', 1, '', '000511', 0, 0, NULL, '2016-11-01 11:23:20.589112+07', '002648');


--
-- TOC entry 3049 (class 0 OID 0)
-- Dependencies: 368
-- Name: pb2_main_workflow_reviewer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_main_workflow_reviewer_id_seq', 3668, true);


--
-- TOC entry 2955 (class 0 OID 72631)
-- Dependencies: 369
-- Data for Name: pb2_pcm_ord; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_pcm_ord (id, total, folder_ref, doc_ref, status, created_time, created_by, updated_time, updated_by, waiting_level, workflow_ins_id, objective, section_id, pr_id, doc_type, app_by) VALUES ('PD16000156', 69550, 'workspace://SpacesStore/d592e48d-3f7e-4c9e-b4fe-cb2641806369', 'workspace://SpacesStore/8ae4a735-38c4-42cf-a66a-bc75b62160f3', 'X1', '2016-10-24 16:06:18.555722+07', '003556', '2016-10-24 16:06:18.824202+07', '003556', NULL, 'activiti$437601', 'PM มีวงเงินพิเศษที่พอ', 42, 'PR17000372', 'PD1', '002840');
INSERT INTO pb2_pcm_ord (id, total, folder_ref, doc_ref, status, created_time, created_by, updated_time, updated_by, waiting_level, workflow_ins_id, objective, section_id, pr_id, doc_type, app_by) VALUES ('PD16000164', 69550, 'workspace://SpacesStore/95c5f29d-d549-453a-96b5-92e906dda74a', 'workspace://SpacesStore/b264cb83-67b8-4a13-bfbb-e6e17cf34d40', 'C1', '2016-10-26 17:33:28.788366+07', '003556', '2016-10-26 17:33:29.181262+07', '003556', 2, 'activiti$443241', 'PM ขอเบิก', 42, 'PR17000370', 'PD1', '002840');
INSERT INTO pb2_pcm_ord (id, total, folder_ref, doc_ref, status, created_time, created_by, updated_time, updated_by, waiting_level, workflow_ins_id, objective, section_id, pr_id, doc_type, app_by) VALUES ('PD16000157', 53500, 'workspace://SpacesStore/3b2fcb29-d679-4d07-bc93-5514ec7edec7', 'workspace://SpacesStore/1c85d350-04be-4715-a013-83b5de1b8665', 'C1', '2016-10-29 21:37:23.452173+07', '003556', '2016-10-29 21:37:23.721324+07', '003556', 2, 'activiti$445295', 'กระดาษ A4', 42, 'PR17000371', 'PD1', '002840');
INSERT INTO pb2_pcm_ord (id, total, folder_ref, doc_ref, status, created_time, created_by, updated_time, updated_by, waiting_level, workflow_ins_id, objective, section_id, pr_id, doc_type, app_by) VALUES ('PD16000158', 360000, 'workspace://SpacesStore/dc8bc841-9024-4ba5-8c50-7932ba3ca403', 'workspace://SpacesStore/f9c75e28-413b-4831-8215-31292851a025', 'X1', '2016-10-24 17:25:15.342844+07', '003556', '2016-10-24 17:25:15.638849+07', '003556', NULL, 'activiti$438517', 'PR ข้ามปีงบประมาณ', 42, 'PR17000375PR17000375PR17000375', 'PD6', '002840');
INSERT INTO pb2_pcm_ord (id, total, folder_ref, doc_ref, status, created_time, created_by, updated_time, updated_by, waiting_level, workflow_ins_id, objective, section_id, pr_id, doc_type, app_by) VALUES ('PD16000165', 5136000, 'workspace://SpacesStore/d2f2fe9d-3cc4-490d-bebf-5614849c8486', 'workspace://SpacesStore/9befe267-7efc-4fac-b54f-79b101f86644', 'C1', '2016-10-27 17:05:09.515871+07', '003556', '2016-10-27 17:05:09.941701+07', '003556', 4, 'activiti$443637', 'จ้างซักผ้า', 42, 'PR17000382', 'PD4', '002840');
INSERT INTO pb2_pcm_ord (id, total, folder_ref, doc_ref, status, created_time, created_by, updated_time, updated_by, waiting_level, workflow_ins_id, objective, section_id, pr_id, doc_type, app_by) VALUES ('PD16000159', 310000, 'workspace://SpacesStore/3e46e407-823e-4cb0-bc54-647ceee2e937', 'workspace://SpacesStore/5b34f850-b160-45ee-90dd-cb4c871065cf', 'C1', '2016-10-25 16:53:43.867653+07', '003556', '2016-10-25 16:53:44.346862+07', '003556', 3, 'activiti$442680', 'ทดสอบสอบด้วยวิธีสอบราคา', 42, 'PR17000380', 'PD6', '002840');
INSERT INTO pb2_pcm_ord (id, total, folder_ref, doc_ref, status, created_time, created_by, updated_time, updated_by, waiting_level, workflow_ins_id, objective, section_id, pr_id, doc_type, app_by) VALUES ('PD16000166', 5136000, 'workspace://SpacesStore/cea74661-2b06-4530-b3d3-86e296ca5c5a', 'workspace://SpacesStore/071c41c5-6a7c-4597-83fb-ec9b0dd06ed0', 'C1', '2016-10-27 17:44:10.44892+07', '003556', '2016-10-27 17:44:10.736083+07', '003556', 4, 'activiti$444147', 'จ้างซักผ้า', 42, 'PR17000383', 'PD4', '002840');
INSERT INTO pb2_pcm_ord (id, total, folder_ref, doc_ref, status, created_time, created_by, updated_time, updated_by, waiting_level, workflow_ins_id, objective, section_id, pr_id, doc_type, app_by) VALUES ('PD16000154', 69550, 'workspace://SpacesStore/c0b13df0-8f86-4fb8-9c9c-2ccd3e3c25e1', 'workspace://SpacesStore/702840ac-8589-47ce-88aa-6bc181476771', 'C1', '2016-10-24 15:44:22.551408+07', '003556', '2016-10-24 15:44:23.448965+07', '003556', 2, 'activiti$437022', 'ผู้ขอเบิก ใช้ งบหน่วยงาน ตัวเอง', 42, 'PR17000367', 'PD1', '002840');
INSERT INTO pb2_pcm_ord (id, total, folder_ref, doc_ref, status, created_time, created_by, updated_time, updated_by, waiting_level, workflow_ins_id, objective, section_id, pr_id, doc_type, app_by) VALUES ('PD16000160', 100, 'workspace://SpacesStore/3f8231d9-fec4-4c88-b694-8479db0e017b', 'workspace://SpacesStore/98be54b8-754e-43a6-8fe7-635a3097f88a', 'C1', '2016-10-25 17:09:37.432185+07', '003556', '2016-10-25 17:09:37.733318+07', '003556', 2, 'activiti$442876', 'ทดสอบสกุลเงินต่างประเทศ', 42, 'PR17000379', 'PD1', '002840');
INSERT INTO pb2_pcm_ord (id, total, folder_ref, doc_ref, status, created_time, created_by, updated_time, updated_by, waiting_level, workflow_ins_id, objective, section_id, pr_id, doc_type, app_by) VALUES ('PD16000167', 5136000, 'workspace://SpacesStore/f167e83b-8456-463b-957a-12e461df20a1', 'workspace://SpacesStore/23924a50-6ed2-462b-9a16-80daf301bfd0', 'C1', '2016-10-29 21:07:44.240527+07', '003556', '2016-10-29 21:07:44.637309+07', '003556', 4, 'activiti$445047', 'จ้างซักผ้า', 42, 'PR17000382', 'PD4', '002840');


--
-- TOC entry 2956 (class 0 OID 72639)
-- Dependencies: 370
-- Data for Name: pb2_pcm_req; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000377', 107000, 'workspace://SpacesStore/285b7de7-900c-4803-8921-ce063f9d5295', 'workspace://SpacesStore/7c9992b4-6bb2-40b5-bf6b-8d43ab5607fe', 'activiti$438687', NULL, 'X1', '002648', '2016-10-24 17:31:51.654321+07', '002648', '2016-10-24 17:39:31.134653+07', '002648', 'ซื้อ', 'PR เพิ่มเติม', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, 'PR17000372', '', '', '', 0.0700000003, NULL, '0', '0', '1', 2, '', 'U', 59, 1, 59, NULL, NULL, NULL, 26, 'workspace://SpacesStore/3942f847-39d2-4976-8e4d-9ea58ee8ecf5', '2016-10-24 00:00:00+07', 107000, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000369', 69550, 'workspace://SpacesStore/5784427c-3ba0-493b-9a5f-6622b9460812', 'workspace://SpacesStore/e12ae536-fafb-4974-b7c9-142616c6e993', 'activiti$436742', 2, 'X2', '000511', '2016-10-24 15:25:36.86485+07', '002648', '2016-10-24 15:38:36.06239+07', '000090', 'ซื้อ', 'ผู้ขอเบิกใช้ งบหน่วยงาน อื่น', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'U', 59, 1, 59, NULL, 1, 2, 26, NULL, '2016-10-24 00:00:00+07', 69550, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000367', 69550, 'workspace://SpacesStore/95cea1c1-3568-4dda-999d-a15e44137d2d', 'workspace://SpacesStore/cc8e358b-3748-494d-84a7-a9f748694fca', 'activiti$436401', 3, 'C2', '002648', '2016-10-24 14:49:00.069492+07', '002648', '2016-10-24 14:59:31.280416+07', '000511', 'ซื้อ', 'ผู้ขอเบิก ใช้ งบหน่วยงาน ตัวเอง', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'U', 59, 1, 59, NULL, 1, 2, 26, NULL, '2016-10-24 00:00:00+07', 69550, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000373', 176550, 'workspace://SpacesStore/54c24850-ad62-46d1-9ce1-a457ae5804c7', 'workspace://SpacesStore/add41be0-f323-4132-b8c5-e1a45c332f66', 'activiti$437714', NULL, 'X1', '002648', '2016-10-24 16:12:20.096539+07', '002648', '2016-10-24 16:13:39.025093+07', '002648', 'ซื้อ', 'PM มีวงเงินพิเศษที่ไม่พอ', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'P', 74, 1, 59, NULL, 1, 2, 26, NULL, '2016-10-24 00:00:00+07', 176550, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000368', 69550, 'workspace://SpacesStore/4fc3bb0c-33b7-400d-a1dc-b0a5e9c8a443', 'workspace://SpacesStore/38334c6b-14e4-4b10-af84-85ad1a3e2763', 'activiti$436602', 2, 'C2', '002648', '2016-10-24 15:00:02.0303+07', '002648', '2016-10-24 15:21:32.006358+07', '000110', 'ซื้อ', 'ผู้ขอเบิกใช้ งบหน่วยงาน อื่น', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'U', 168, 3, 59, NULL, 1, 2, 26, NULL, '2016-10-24 00:00:00+07', 69550, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000378', 69550, 'workspace://SpacesStore/04e8273a-0b06-41dd-95ec-cdbba9aaafc9', 'workspace://SpacesStore/d20f725c-3c4e-43f5-a2c0-3a9b2d515a14', 'activiti$439150', NULL, 'X1', '000511', '2016-10-24 17:41:11.518394+07', '000511', '2016-10-24 17:48:50.686604+07', '000511', 'ซื้อ', 'PM ขอเบิกมีวงเงินพิเศษที่พอ', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'P', 74, 1, 57, NULL, 1, 2, 26, NULL, '2016-10-24 00:00:00+07', 69550, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000374', 69550, 'workspace://SpacesStore/b14912e9-3087-4667-8e3d-90980f1e4bff', 'workspace://SpacesStore/eafa2d34-e6ac-469b-b3bc-7b63cb2d4b18', 'activiti$437912', NULL, 'X1', '002648', '2016-10-24 16:19:10.479304+07', '002648', '2016-10-24 16:22:45.907034+07', '002648', 'ซื้อ', 'PM ไม่มีวงเงินพิเศษ', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'P', 74, 1, 59, NULL, 1, 2, 26, NULL, '2016-10-24 00:00:00+07', 69550, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000375', 360000, 'workspace://SpacesStore/67f52021-bc58-413a-bfee-802644550ceb', 'workspace://SpacesStore/4e6fed09-53b8-4f3d-8911-00f138d5dbc0', 'activiti$438255', 4, 'C2', '002648', '2016-10-24 17:14:57.055929+07', '002648', '2016-10-24 17:23:33.200114+07', '000090', 'จ้าง', 'PR ข้ามปีงบประมาณ', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0, NULL, '0', '1', '0', 0, '', 'U', 59, 1, 59, NULL, NULL, NULL, 47, NULL, '2016-10-24 00:00:00+07', 360000, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000372', 69550, 'workspace://SpacesStore/b8dfada2-cd72-4663-a4bc-dc88a0a45a46', 'workspace://SpacesStore/5841bfa8-1122-4f80-893a-91c909c78d49', 'activiti$437461', 2, 'C2', '002648', '2016-10-24 15:59:39.338932+07', '002648', '2016-10-24 16:03:42.458708+07', '000511', 'ซื้อ', 'PM มีวงเงินพิเศษที่พอ', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'P', 74, 1, 59, NULL, 1, 2, 26, NULL, '2016-10-24 00:00:00+07', 69550, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000371', 53500, 'workspace://SpacesStore/dba76132-8652-47eb-b80a-55857ca2e813', 'workspace://SpacesStore/6bdb8581-12b8-4828-888a-b2a90c7813b9', 'activiti$437260', 3, 'C2', '003556', '2016-10-24 15:51:37.123067+07', '003556', '2016-10-24 16:09:11.054673+07', '000272', 'ซื้อ', 'กระดาษ A4', 'ใกล้หมด', 'THB', 1, '', '', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'U', 90, 1, 42, NULL, NULL, NULL, 26, NULL, '2016-10-26 00:00:00+07', 53500, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000381', 69550, 'workspace://SpacesStore/debc9d51-673c-4e65-93b8-51f530be16ef', 'workspace://SpacesStore/37421f67-fd5c-458b-8f44-067e99cb2405', 'activiti$443101', 2, 'C2', '002648', '2016-10-26 17:20:52.693512+07', '002648', '2016-10-26 17:25:42.406838+07', '000110', 'ซื้อ', 'ทดสอบสร้างใบ พด.', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'U', 168, 3, 59, NULL, NULL, NULL, 26, NULL, '2016-10-24 00:00:00+07', 69550, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000380', 310000, 'workspace://SpacesStore/ecb2813b-eba2-4d20-96a3-474322ba0f50', 'workspace://SpacesStore/4a334d09-052b-4cb5-b1a3-90d231177e31', 'activiti$442058', 4, 'C2', '002648', '2016-10-25 11:04:45.310969+07', '002648', '2016-10-25 16:51:43.695887+07', '000090', 'ซื้อ', 'ทดสอบสอบด้วยวิธีสอบราคา', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0, NULL, '0', '0', '0', 0, '', 'U', 59, 1, 59, NULL, 1, 2, 30, NULL, '2016-10-24 00:00:00+07', 310000, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000379', 100, 'workspace://SpacesStore/2f22a499-638c-4e51-8693-a38c1d9d864a', 'workspace://SpacesStore/16780847-99e3-4f27-b0a4-603feba85762', 'activiti$441917', 2, 'C2', '002648', '2016-10-25 10:25:02.707064+07', '002648', '2016-10-25 17:07:55.800781+07', '001509', 'ซื้อ', 'ทดสอบสกุลเงินต่างประเทศ', 'ใกล้หมด', 'USD', 35, '', '', NULL, '', '', '', '', 0, NULL, '0', '0', '0', 0, '', 'U', 59, 1, 59, NULL, NULL, NULL, 26, NULL, '2016-10-25 00:00:00+07', 3500, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000370', 69550, 'workspace://SpacesStore/74a32618-4f88-459f-b74d-76d82d7127eb', 'workspace://SpacesStore/1391c61b-349e-4013-9f3a-a3f00cd0a54c', 'activiti$436882', 2, 'C2', '000511', '2016-10-24 15:40:16.113904+07', '000511', '2016-10-26 17:31:09.710728+07', '000090', 'ซื้อ', 'PM ขอเบิก', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'THB', 1, '', '', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'P', 74, 1, 57, NULL, 1, 2, 26, NULL, '2016-10-24 00:00:00+07', 69550, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000382', 5136000, 'workspace://SpacesStore/13e0a60d-c3da-4951-b267-560a525f655e', 'workspace://SpacesStore/b3b22f67-7069-4be3-978c-dc5a4d53500d', 'activiti$443375', 4, 'C2', '000511', '2016-10-27 16:15:10.840895+07', '000511', '2016-10-27 16:37:12.29799+07', '000165', 'จ้าง', 'จ้างซักผ้า', 'อื่นๆ จ้างซักผ้าประจำปี 2560', 'THB', 1, '', 'สำนักงานกลาง', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'U', 59, 1, 57, NULL, NULL, NULL, 34, NULL, '2016-10-24 00:00:00+07', 5136000, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000383', 5136000, 'workspace://SpacesStore/14834afd-4765-4e25-bb4b-a76c8f460ca5', 'workspace://SpacesStore/820c702f-d7d1-4500-a0e3-d56a4941c451', 'activiti$443885', 4, 'C2', '000511', '2016-10-27 17:35:44.28556+07', '000511', '2016-10-27 17:41:29.028825+07', '000165', 'จ้าง', 'จ้างซักผ้า', 'อื่นๆ จ้างซักผ้าประจำปี 2560', 'THB', 1, '', 'สำนักงานกลาง', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'U', 59, 1, 57, NULL, NULL, NULL, 34, NULL, '2016-10-24 00:00:00+07', 5136000, 1);
INSERT INTO pb2_pcm_req (id, total, folder_ref, doc_ref, workflow_ins_id, waiting_level, status, req_by, created_time, created_by, updated_time, updated_by, objective_type, objective, reason, currency, currency_rate, prototype, location, across_budget, ref_id, method_cond2_rule, method_cond2, method_cond2_dtl, vat, is_stock, is_prototype, is_across_budget, is_ref_id, vat_id, prototype_contract_no, budget_cc_type, budget_cc, pcm_section_id, req_section_id, stock_section_id, cost_control_type_id, cost_control_id, prweb_method_id, ref_doc_ref, contract_date, total_cnv, fund_id) VALUES ('PR17000384', 5136000, 'workspace://SpacesStore/9ffa68aa-030e-48e0-8db6-5d6fdc573c76', NULL, NULL, NULL, 'D', '000511', '2016-11-01 17:52:44.535627+07', '000511', '2016-11-01 17:52:44.535627+07', '000511', 'จ้าง', 'จ้างซักผ้า', 'อื่นๆ จ้างซักผ้าประจำปี 2560', 'THB', 1, '', 'สำนักงานกลาง', NULL, '', '', '', '', 0.0700000003, NULL, '0', '0', '0', 2, '', 'U', 59, 1, 57, NULL, NULL, NULL, 34, NULL, '2016-10-24 00:00:00+07', 5136000, 1);


--
-- TOC entry 2957 (class 0 OID 72647)
-- Dependencies: 371
-- Data for Name: pb2_pcm_req_committee_dtl; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6453, 2572, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-24 14:49:00.07572+07', '002648', '2016-10-24 14:49:00.07572+07', '002648', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6454, 2572, 'ศิริชัย', 'กรรมการ', '2016-10-24 14:49:00.077224+07', '002648', '2016-10-24 14:49:00.077224+07', '002648', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6455, 2572, 'ภูริเดช', 'กรรมการ', '2016-10-24 14:49:00.095348+07', '002648', '2016-10-24 14:49:00.095348+07', '002648', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6573, 2612, 'Sudathip', 'ประธานกรรมการ', '2016-11-01 17:52:44.598401+07', '000511', '2016-11-01 17:52:44.598401+07', '000511', 'Indee', '000040', 'Miss');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6574, 2612, 'Pongsathon', 'กรรมการ', '2016-11-01 17:52:44.599898+07', '000511', '2016-11-01 17:52:44.599898+07', '000511', 'Visoldilokpun', '005278', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6575, 2612, 'Napapatch', 'กรรมการ', '2016-11-01 17:52:44.600373+07', '000511', '2016-11-01 17:52:44.600373+07', '000511', 'Towsajja', '001310', 'Miss');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6462, 2575, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-24 15:17:22.546385+07', '002648', '2016-10-24 15:17:22.546385+07', '002648', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6463, 2575, 'ศิริชัย', 'กรรมการ', '2016-10-24 15:17:22.547288+07', '002648', '2016-10-24 15:17:22.547288+07', '002648', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6464, 2575, 'ภูริเดช', 'กรรมการ', '2016-10-24 15:17:22.547787+07', '002648', '2016-10-24 15:17:22.547787+07', '002648', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6468, 2577, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-24 15:36:18.044319+07', '002648', '2016-10-24 15:36:18.044319+07', '002648', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6469, 2577, 'ศิริชัย', 'กรรมการ', '2016-10-24 15:36:18.045137+07', '002648', '2016-10-24 15:36:18.045137+07', '002648', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6470, 2577, 'ภูริเดช', 'กรรมการ', '2016-10-24 15:36:18.045617+07', '002648', '2016-10-24 15:36:18.045617+07', '002648', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6474, 2579, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-24 15:41:37.0374+07', '000511', '2016-10-24 15:41:37.0374+07', '000511', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6475, 2579, 'ศิริชัย', 'กรรมการ', '2016-10-24 15:41:37.037882+07', '000511', '2016-10-24 15:41:37.037882+07', '000511', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6476, 2579, 'ภูริเดช', 'กรรมการ', '2016-10-24 15:41:37.038362+07', '000511', '2016-10-24 15:41:37.038362+07', '000511', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6477, 2580, 'สุนทรีย์', 'ประธานกรรมการ', '2016-10-24 15:51:37.12795+07', '003556', '2016-10-24 15:51:37.12795+07', '003556', 'โฆษิตชัยยงค์', '003883', 'นางสาว');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6478, 2580, 'กัญจนา', 'กรรมการ', '2016-10-24 15:51:37.130895+07', '003556', '2016-10-24 15:51:37.130895+07', '003556', 'ดำรงค์ไชย', '004077', 'นางสาว');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6479, 2580, 'วงศกร', 'กรรมการ', '2016-10-24 15:51:37.131447+07', '003556', '2016-10-24 15:51:37.131447+07', '003556', 'พูนพิริยะ', '004078', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6483, 2582, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-24 16:01:34.65701+07', '002648', '2016-10-24 16:01:34.65701+07', '002648', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6484, 2582, 'ศิริชัย', 'กรรมการ', '2016-10-24 16:01:34.657797+07', '002648', '2016-10-24 16:01:34.657797+07', '002648', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6485, 2582, 'ภูริเดช', 'กรรมการ', '2016-10-24 16:01:34.658263+07', '002648', '2016-10-24 16:01:34.658263+07', '002648', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6492, 2585, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-24 16:12:54.055948+07', '002648', '2016-10-24 16:12:54.055948+07', '002648', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6493, 2585, 'ศิริชัย', 'กรรมการ', '2016-10-24 16:12:54.056689+07', '002648', '2016-10-24 16:12:54.056689+07', '002648', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6494, 2585, 'ภูริเดช', 'กรรมการ', '2016-10-24 16:12:54.057156+07', '002648', '2016-10-24 16:12:54.057156+07', '002648', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6504, 2589, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-24 16:21:37.779689+07', '002648', '2016-10-24 16:21:37.779689+07', '002648', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6505, 2589, 'ศิริชัย', 'กรรมการ', '2016-10-24 16:21:37.780461+07', '002648', '2016-10-24 16:21:37.780461+07', '002648', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6506, 2589, 'ภูริเดช', 'กรรมการ', '2016-10-24 16:21:37.780928+07', '002648', '2016-10-24 16:21:37.780928+07', '002648', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6510, 2591, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-24 17:18:13.279743+07', '002648', '2016-10-24 17:18:13.279743+07', '002648', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6511, 2591, 'ศิริชัย', 'กรรมการ', '2016-10-24 17:18:13.280552+07', '002648', '2016-10-24 17:18:13.280552+07', '002648', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6512, 2591, 'ภูริเดช', 'กรรมการ', '2016-10-24 17:18:13.280966+07', '002648', '2016-10-24 17:18:13.280966+07', '002648', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6519, 2594, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-24 17:33:33.951967+07', '002648', '2016-10-24 17:33:33.951967+07', '002648', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6520, 2594, 'ศิริชัย', 'กรรมการ', '2016-10-24 17:33:33.952413+07', '002648', '2016-10-24 17:33:33.952413+07', '002648', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6521, 2594, 'ภูริเดช', 'กรรมการ', '2016-10-24 17:33:33.952815+07', '002648', '2016-10-24 17:33:33.952815+07', '002648', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6525, 2596, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-24 17:41:30.071214+07', '000511', '2016-10-24 17:41:30.071214+07', '000511', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6526, 2596, 'ศิริชัย', 'กรรมการ', '2016-10-24 17:41:30.071976+07', '000511', '2016-10-24 17:41:30.071976+07', '000511', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6527, 2596, 'ภูริเดช', 'กรรมการ', '2016-10-24 17:41:30.07246+07', '000511', '2016-10-24 17:41:30.07246+07', '000511', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6528, 2597, 'สุกฤตา', 'ประธานกรรมการ', '2016-10-25 10:25:02.70935+07', '002648', '2016-10-25 10:25:02.70935+07', '002648', 'อุดม', '000367', 'นางสาว');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6529, 2597, 'เมทินี', 'กรรมการ', '2016-10-25 10:25:02.710338+07', '002648', '2016-10-25 10:25:02.710338+07', '002648', 'ประภาประไพ', '001509', 'นางสาว');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6530, 2597, 'บดินทร์', 'กรรมการ', '2016-10-25 10:25:02.7108+07', '002648', '2016-10-25 10:25:02.7108+07', '002648', 'กิจศิริเจริญชัย', '001660', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6534, 2599, 'Thapanat', 'ประธานกรรมการ', '2016-10-25 11:06:14.046782+07', '002648', '2016-10-25 11:06:14.046782+07', '002648', 'Sophon', '004026', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6535, 2599, 'Sirichai', 'กรรมการ', '2016-10-25 11:06:14.0472+07', '002648', '2016-10-25 11:06:14.0472+07', '002648', 'Chitvises', '004010', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6536, 2599, 'Phuridech', 'กรรมการ', '2016-10-25 11:06:14.047626+07', '002648', '2016-10-25 11:06:14.047626+07', '002648', 'Phopiphat', '004783', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6540, 2601, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-26 17:22:02.989083+07', '002648', '2016-10-26 17:22:02.989083+07', '002648', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6541, 2601, 'ศิริชัย', 'กรรมการ', '2016-10-26 17:22:02.989927+07', '002648', '2016-10-26 17:22:02.989927+07', '002648', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6542, 2601, 'ภูริเดช', 'กรรมการ', '2016-10-26 17:22:02.990458+07', '002648', '2016-10-26 17:22:02.990458+07', '002648', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6546, 2603, 'สุดาทิพย์', 'ประธานกรรมการ', '2016-10-27 16:29:42.928525+07', '000511', '2016-10-27 16:29:42.928525+07', '000511', 'อินดี', '000040', 'นางสาว');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6547, 2603, 'พงศ์ศธร', 'กรรมการ', '2016-10-27 16:29:42.929416+07', '000511', '2016-10-27 16:29:42.929416+07', '000511', 'วิศลดิลกพันธ์', '005278', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6548, 2603, 'ณปภัช', 'กรรมการ', '2016-10-27 16:29:42.930389+07', '000511', '2016-10-27 16:29:42.930389+07', '000511', 'โต๋วสัจจา', '001310', 'นางสาว');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6549, 2604, 'ฐาปณัฐ', 'ประธานกรรมการ', '2016-10-27 16:29:42.931328+07', '000511', '2016-10-27 16:29:42.931328+07', '000511', 'โสภณ', '004026', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6550, 2604, 'ศิริชัย', 'กรรมการ', '2016-10-27 16:29:42.931927+07', '000511', '2016-10-27 16:29:42.931927+07', '000511', 'ชิตวิเศษ', '004010', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6551, 2604, 'ภูริเดช', 'กรรมการ', '2016-10-27 16:29:42.932431+07', '000511', '2016-10-27 16:29:42.932431+07', '000511', 'โพธิ์พิพัฒน์', '004783', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6552, 2605, 'สุทธิรัตน์', 'ประธานกรรมการ', '2016-10-27 16:29:42.933523+07', '000511', '2016-10-27 16:29:42.933523+07', '000511', 'รัตนพันธ์', '001861', 'นาย');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6553, 2605, 'ณัฏฐศศิ', 'กรรมการ', '2016-10-27 16:29:42.934059+07', '000511', '2016-10-27 16:29:42.934059+07', '000511', 'มาฆะธรรมเจริญ', '000524', 'นางสาว');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6554, 2605, 'สุธาสินี', 'กรรมการ', '2016-10-27 16:29:42.934544+07', '000511', '2016-10-27 16:29:42.934544+07', '000511', 'ปริญญาณัฏฐ์', '005570', 'นางสาว');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6576, 2613, 'Thapanat', 'ประธานกรรมการ', '2016-11-01 17:52:44.601377+07', '000511', '2016-11-01 17:52:44.601377+07', '000511', 'Sophon', '004026', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6577, 2613, 'Sirichai', 'กรรมการ', '2016-11-01 17:52:44.601876+07', '000511', '2016-11-01 17:52:44.601876+07', '000511', 'Chitvises', '004010', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6578, 2613, 'Phuridech', 'กรรมการ', '2016-11-01 17:52:44.602333+07', '000511', '2016-11-01 17:52:44.602333+07', '000511', 'Phopiphat', '004783', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6579, 2614, 'Suttirat', 'ประธานกรรมการ', '2016-11-01 17:52:44.603245+07', '000511', '2016-11-01 17:52:44.603245+07', '000511', 'Rattanapan', '001861', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6580, 2614, 'Natthasasi', 'กรรมการ', '2016-11-01 17:52:44.603985+07', '000511', '2016-11-01 17:52:44.603985+07', '000511', 'Makhathamjaroen', '000524', 'Miss');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6581, 2614, 'Sutasinee', 'กรรมการ', '2016-11-01 17:52:44.604426+07', '000511', '2016-11-01 17:52:44.604426+07', '000511', 'Parinyanat', '005570', 'Miss');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6564, 2609, 'Sudathip', 'ประธานกรรมการ', '2016-10-27 17:36:07.231325+07', '000511', '2016-10-27 17:36:07.231325+07', '000511', 'Indee', '000040', 'Miss');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6565, 2609, 'Pongsathon', 'กรรมการ', '2016-10-27 17:36:07.232119+07', '000511', '2016-10-27 17:36:07.232119+07', '000511', 'Visoldilokpun', '005278', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6566, 2609, 'Napapatch', 'กรรมการ', '2016-10-27 17:36:07.232625+07', '000511', '2016-10-27 17:36:07.232625+07', '000511', 'Towsajja', '001310', 'Miss');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6567, 2610, 'Thapanat', 'ประธานกรรมการ', '2016-10-27 17:36:07.23525+07', '000511', '2016-10-27 17:36:07.23525+07', '000511', 'Sophon', '004026', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6568, 2610, 'Sirichai', 'กรรมการ', '2016-10-27 17:36:07.23615+07', '000511', '2016-10-27 17:36:07.23615+07', '000511', 'Chitvises', '004010', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6569, 2610, 'Phuridech', 'กรรมการ', '2016-10-27 17:36:07.236676+07', '000511', '2016-10-27 17:36:07.236676+07', '000511', 'Phopiphat', '004783', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6570, 2611, 'Suttirat', 'ประธานกรรมการ', '2016-10-27 17:36:07.237608+07', '000511', '2016-10-27 17:36:07.237608+07', '000511', 'Rattanapan', '001861', 'Mr.');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6571, 2611, 'Natthasasi', 'กรรมการ', '2016-10-27 17:36:07.238019+07', '000511', '2016-10-27 17:36:07.238019+07', '000511', 'Makhathamjaroen', '000524', 'Miss');
INSERT INTO pb2_pcm_req_committee_dtl (id, master_id, first_name, "position", created_time, created_by, updated_time, updated_by, last_name, employee_code, title) VALUES (6572, 2611, 'Sutasinee', 'กรรมการ', '2016-10-27 17:36:07.238494+07', '000511', '2016-10-27 17:36:07.238494+07', '000511', 'Parinyanat', '005570', 'Miss');


--
-- TOC entry 3050 (class 0 OID 0)
-- Dependencies: 372
-- Name: pb2_pcm_req_committee_dtl_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_pcm_req_committee_dtl_id_seq', 6581, true);


--
-- TOC entry 2959 (class 0 OID 72657)
-- Dependencies: 373
-- Data for Name: pb2_pcm_req_committee_hdr; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2572, 'PR17000367', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 14:49:00.07413+07', '002648', '2016-10-24 14:49:00.07413+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2575, 'PR17000368', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 15:17:22.545484+07', '002648', '2016-10-24 15:17:22.545484+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2577, 'PR17000369', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 15:36:18.043449+07', '002648', '2016-10-24 15:36:18.043449+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2579, 'PR17000370', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 15:41:37.036873+07', '000511', '2016-10-24 15:41:37.036873+07', '000511', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2580, 'PR17000371', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 15:51:37.126385+07', '003556', '2016-10-24 15:51:37.126385+07', '003556', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2582, 'PR17000372', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 16:01:34.656157+07', '002648', '2016-10-24 16:01:34.656157+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2585, 'PR17000373', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 16:12:54.055428+07', '002648', '2016-10-24 16:12:54.055428+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2589, 'PR17000374', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 16:21:37.778856+07', '002648', '2016-10-24 16:21:37.778856+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2591, 'PR17000375', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 17:18:13.279153+07', '002648', '2016-10-24 17:18:13.279153+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2594, 'PR17000377', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 17:33:33.951474+07', '002648', '2016-10-24 17:33:33.951474+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2596, 'PR17000378', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-24 17:41:30.070546+07', '000511', '2016-10-24 17:41:30.070546+07', '000511', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2597, 'PR17000379', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-25 10:25:02.708673+07', '002648', '2016-10-25 10:25:02.708673+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2599, 'PR17000380', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-25 11:06:14.046302+07', '002648', '2016-10-25 11:06:14.046302+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2601, 'PR17000381', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-26 17:22:02.98821+07', '002648', '2016-10-26 17:22:02.98821+07', '002648', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2603, 'PR17000382', 'คณะกรรมการรับ และเปิดซองประกวดราคา', '2016-10-27 16:29:42.927672+07', '000511', '2016-10-27 16:29:42.927672+07', '000511', 27);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2604, 'PR17000382', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-27 16:29:42.930837+07', '000511', '2016-10-27 16:29:42.930837+07', '000511', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2605, 'PR17000382', 'คณะกรรมการพิจารณาผลการประกวดราคา', '2016-10-27 16:29:42.932973+07', '000511', '2016-10-27 16:29:42.932973+07', '000511', 36);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2609, 'PR17000383', 'คณะกรรมการรับ และเปิดซองประกวดราคา', '2016-10-27 17:36:07.230475+07', '000511', '2016-10-27 17:36:07.230475+07', '000511', 27);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2610, 'PR17000383', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-10-27 17:36:07.233069+07', '000511', '2016-10-27 17:36:07.233069+07', '000511', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2611, 'PR17000383', 'คณะกรรมการพิจารณาผลการประกวดราคา', '2016-10-27 17:36:07.237132+07', '000511', '2016-10-27 17:36:07.237132+07', '000511', 36);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2612, 'PR17000384', 'คณะกรรมการรับ และเปิดซองประกวดราคา', '2016-11-01 17:52:44.596127+07', '000511', '2016-11-01 17:52:44.596127+07', '000511', 27);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2613, 'PR17000384', 'คณะกรรมการตรวจรับพัสดุ/คณะกรรมการตรวจการจ้าง', '2016-11-01 17:52:44.600833+07', '000511', '2016-11-01 17:52:44.600833+07', '000511', 29);
INSERT INTO pb2_pcm_req_committee_hdr (id, pcm_req_id, committee, created_time, created_by, updated_time, updated_by, committee_id) VALUES (2614, 'PR17000384', 'คณะกรรมการพิจารณาผลการประกวดราคา', '2016-11-01 17:52:44.602781+07', '000511', '2016-11-01 17:52:44.602781+07', '000511', 36);


--
-- TOC entry 3051 (class 0 OID 0)
-- Dependencies: 374
-- Name: pb2_pcm_req_committee_hdr_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_pcm_req_committee_hdr_id_seq', 2614, true);


--
-- TOC entry 2961 (class 0 OID 72664)
-- Dependencies: 375
-- Data for Name: pb2_pcm_req_dtl; Type: TABLE DATA; Schema: public; Owner: alfresco
--

INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2831, 'PR17000367', 'วัสดุไฟฟ้า', 1, '2016-10-24 14:49:00.071759+07', '002648', '2016-10-24 14:49:00.071759+07', '002648', 65000, 65000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2834, 'PR17000368', 'วัสดุไฟฟ้า', 1, '2016-10-24 15:17:22.544407+07', '002648', '2016-10-24 15:17:22.544407+07', '002648', 65000, 65000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2836, 'PR17000369', 'วัสดุไฟฟ้า', 1, '2016-10-24 15:36:18.042369+07', '002648', '2016-10-24 15:36:18.042369+07', '002648', 65000, 65000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2838, 'PR17000370', 'วัสดุไฟฟ้า', 1, '2016-10-24 15:41:37.036327+07', '000511', '2016-10-24 15:41:37.036327+07', '000511', 65000, 65000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2839, 'PR17000371', 'กระดาษถ่ายเอกสาร A4', 500, '2016-10-24 15:51:37.124747+07', '003556', '2016-10-24 15:51:37.124747+07', '003556', 100, 50000, 302, 29, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2841, 'PR17000372', 'วัสดุไฟฟ้า', 1, '2016-10-24 16:01:34.655214+07', '002648', '2016-10-24 16:01:34.655214+07', '002648', 65000, 65000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2844, 'PR17000373', 'วัสดุไฟฟ้า', 1, '2016-10-24 16:12:54.054902+07', '002648', '2016-10-24 16:12:54.054902+07', '002648', 165000, 165000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2848, 'PR17000374', 'วัสดุไฟฟ้า', 1, '2016-10-24 16:21:37.777925+07', '002648', '2016-10-24 16:21:37.777925+07', '002648', 65000, 65000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2850, 'PR17000375', 'ทำความสะอาดตึก', 1, '2016-10-24 17:18:13.277461+07', '002648', '2016-10-24 17:18:13.277461+07', '002648', 120000, 120000, 269, 24, 2017);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2851, 'PR17000375', 'ทำความสะอาดตึก', 1, '2016-10-24 17:18:13.278121+07', '002648', '2016-10-24 17:18:13.278121+07', '002648', 120000, 120000, 269, 24, 2018);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2852, 'PR17000375', 'ทำความสะอาดตึก', 1, '2016-10-24 17:18:13.278635+07', '002648', '2016-10-24 17:18:13.278635+07', '002648', 120000, 120000, 269, 24, 2019);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2855, 'PR17000377', 'วัสดุไฟฟ้า', 1, '2016-10-24 17:33:33.950959+07', '002648', '2016-10-24 17:33:33.950959+07', '002648', 100000, 100000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2857, 'PR17000378', 'วัสดุไฟฟ้า', 1, '2016-10-24 17:41:30.069962+07', '000511', '2016-10-24 17:41:30.069962+07', '000511', 65000, 65000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2858, 'PR17000379', 'การจัดทำรายงานและเอกสาร ', 100, '2016-10-25 10:25:02.707969+07', '002648', '2016-10-25 10:25:02.707969+07', '002648', 1, 100, 203, 23, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2860, 'PR17000380', 'วัสดุไฟฟ้า', 1, '2016-10-25 11:06:14.04577+07', '002648', '2016-10-25 11:06:14.04577+07', '002648', 310000, 310000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2862, 'PR17000381', 'วัสดุไฟฟ้า', 1, '2016-10-26 17:22:02.987125+07', '002648', '2016-10-26 17:22:02.987125+07', '002648', 65000, 65000, 209, 35, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2864, 'PR17000382', 'จ้างซักผ้า', 1, '2016-10-27 16:29:42.926501+07', '000511', '2016-10-27 16:29:42.926501+07', '000511', 4800000, 4800000, 219, 26, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2866, 'PR17000383', 'จ้างซักผ้า', 1, '2016-10-27 17:36:07.229503+07', '000511', '2016-10-27 17:36:07.229503+07', '000511', 4800000, 4800000, 219, 26, 0);
INSERT INTO pb2_pcm_req_dtl (id, master_id, description, quantity, created_time, created_by, updated_time, updated_by, price, total, unit_id, act_grp_id, fiscal_year) VALUES (2867, 'PR17000384', 'จ้างซักผ้า', 1, '2016-11-01 17:52:44.568487+07', '000511', '2016-11-01 17:52:44.568487+07', '000511', 4800000, 4800000, 219, 26, 0);


--
-- TOC entry 3052 (class 0 OID 0)
-- Dependencies: 376
-- Name: pb2_pcm_req_dtl_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_pcm_req_dtl_id_seq', 2867, true);


--
-- TOC entry 2797 (class 2606 OID 164098)
-- Name: pk_nstda_main_master; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_master
    ADD CONSTRAINT pk_nstda_main_master PRIMARY KEY (id);


--
-- TOC entry 2801 (class 2606 OID 164123)
-- Name: pk_pb2_exp_brw; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_exp_brw
    ADD CONSTRAINT pk_pb2_exp_brw PRIMARY KEY (id);


--
-- TOC entry 2803 (class 2606 OID 164134)
-- Name: pk_pb2_exp_brw_dtl; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_exp_brw_attendee
    ADD CONSTRAINT pk_pb2_exp_brw_dtl PRIMARY KEY (id);


--
-- TOC entry 2805 (class 2606 OID 164149)
-- Name: pk_pb2_exp_use; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_exp_use
    ADD CONSTRAINT pk_pb2_exp_use PRIMARY KEY (id);


--
-- TOC entry 2807 (class 2606 OID 164160)
-- Name: pk_pb2_exp_use_dtl; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_exp_use_dtl
    ADD CONSTRAINT pk_pb2_exp_use_dtl PRIMARY KEY (id);


--
-- TOC entry 2809 (class 2606 OID 164176)
-- Name: pk_pb2_exp_use_voyager; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_exp_use_attendee
    ADD CONSTRAINT pk_pb2_exp_use_voyager PRIMARY KEY (id);


--
-- TOC entry 2777 (class 2606 OID 72686)
-- Name: pk_pb2_main_complete_notification; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_complete_notification
    ADD CONSTRAINT pk_pb2_main_complete_notification PRIMARY KEY (id);


--
-- TOC entry 2783 (class 2606 OID 72688)
-- Name: pk_pb2_main_workflow_next_actor; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_workflow_next_actor
    ADD CONSTRAINT pk_pb2_main_workflow_next_actor PRIMARY KEY (id);


--
-- TOC entry 2787 (class 2606 OID 72690)
-- Name: pk_pb2_pcm_ord; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_ord
    ADD CONSTRAINT pk_pb2_pcm_ord PRIMARY KEY (id);


--
-- TOC entry 2789 (class 2606 OID 72692)
-- Name: pk_pb2_pcm_req; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_req
    ADD CONSTRAINT pk_pb2_pcm_req PRIMARY KEY (id);


--
-- TOC entry 2791 (class 2606 OID 72694)
-- Name: pk_pb2_pcm_req_committee_dtl; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_req_committee_dtl
    ADD CONSTRAINT pk_pb2_pcm_req_committee_dtl PRIMARY KEY (id);


--
-- TOC entry 2793 (class 2606 OID 72696)
-- Name: pk_pb2_pcm_req_committee_hdr; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_req_committee_hdr
    ADD CONSTRAINT pk_pb2_pcm_req_committee_hdr PRIMARY KEY (id);


--
-- TOC entry 2795 (class 2606 OID 72698)
-- Name: pk_pb2_pcm_req_dtl; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_req_dtl
    ADD CONSTRAINT pk_pb2_pcm_req_dtl PRIMARY KEY (id);


--
-- TOC entry 2785 (class 2606 OID 72700)
-- Name: pk_pb2_pcm_req_reviewer; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_workflow_reviewer
    ADD CONSTRAINT pk_pb2_pcm_req_reviewer PRIMARY KEY (id);


--
-- TOC entry 2779 (class 2606 OID 72702)
-- Name: pk_pb2_pcm_req_workflow; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_workflow
    ADD CONSTRAINT pk_pb2_pcm_req_workflow PRIMARY KEY (id);


--
-- TOC entry 2781 (class 2606 OID 72704)
-- Name: pk_pb2_pcm_req_workflow_history; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_workflow_history
    ADD CONSTRAINT pk_pb2_pcm_req_workflow_history PRIMARY KEY (id);


--
-- TOC entry 2799 (class 2606 OID 164100)
-- Name: uq_nstda_main_master; Type: CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_main_master
    ADD CONSTRAINT uq_nstda_main_master UNIQUE (type, code);


--
-- TOC entry 2813 (class 2606 OID 164135)
-- Name: exp_brw_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_exp_brw_attendee
    ADD CONSTRAINT exp_brw_id_fkey FOREIGN KEY (master_id) REFERENCES pb2_exp_brw(id);


--
-- TOC entry 2814 (class 2606 OID 164161)
-- Name: exp_use_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_exp_use_dtl
    ADD CONSTRAINT exp_use_id_fkey FOREIGN KEY (master_id) REFERENCES pb2_exp_use(id);


--
-- TOC entry 2815 (class 2606 OID 164177)
-- Name: fk_pb2_exp_use_voyager_master_id; Type: FK CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_exp_use_attendee
    ADD CONSTRAINT fk_pb2_exp_use_voyager_master_id FOREIGN KEY (master_id) REFERENCES pb2_exp_use(id);


--
-- TOC entry 2810 (class 2606 OID 72707)
-- Name: pcm_req_committee_hdr_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_req_committee_dtl
    ADD CONSTRAINT pcm_req_committee_hdr_id_fkey FOREIGN KEY (master_id) REFERENCES pb2_pcm_req_committee_hdr(id);


--
-- TOC entry 2812 (class 2606 OID 72712)
-- Name: pcm_req_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_req_dtl
    ADD CONSTRAINT pcm_req_id_fkey FOREIGN KEY (master_id) REFERENCES pb2_pcm_req(id);


--
-- TOC entry 2811 (class 2606 OID 72717)
-- Name: pcm_req_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alfresco
--

ALTER TABLE ONLY pb2_pcm_req_committee_hdr
    ADD CONSTRAINT pcm_req_id_fkey FOREIGN KEY (pcm_req_id) REFERENCES pb2_pcm_req(id);


-- Completed on 2016-11-03 17:09:39

--
-- PostgreSQL database dump complete
--

INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (233, 'A', 'SYSTEM', 'System Config', true, 'กำหนดค่า', '', NULL, NULL, NULL, '2015-05-03 17:50:14.6244+07', NULL, '2015-05-03 17:50:14.6244+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (110, 'SYSTEM', 'PCM_REQ_CRITERIA_1', 'Pcm Req Criteria 1', true, 'Purchase Type', 'objective_type,200,200', 'main/master?orderBy=flag1', 'type=''PC''', '', '2015-05-20 10:35:54.838422+07', 'admin', '2015-05-20 10:35:54.838422+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (111, 'SYSTEM', 'PCM_REQ_CRITERIA_2', 'Pcm Req Criteria 2', true, 'Status', 'status', 'main/master?orderBy=flag1', 'type=''PR_ST''', '', '2015-05-20 10:35:54.838422+07', 'admin', '2015-05-20 10:35:54.838422+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (499, 'A', 'MP', 'แบบฟอร์มราคากลาง', true, '', '', '', '', '', '2016-04-02 18:40:43.806146+07', 'admin', '2016-04-02 18:40:43.806146+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (170, 'SYSTEM', 'PCM_REQ_MAIL_NOTIFY', 'Pcm Req Mail Notify', true, '0', '', '', '', '', '2015-08-26 15:33:59.285866+07', 'admin', '2015-08-26 15:33:59.285866+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (505, 'A', 'PTT', 'ครุภัณฑ์ต้นแบบ', true, '', '', '', '', '', '2016-04-04 13:58:41.291261+07', 'admin', '2016-04-04 13:58:41.291261+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (171, 'SYSTEM', 'PCM_REQ_MAIL_TEMPLATE', 'Pcm Req Mail Template', true, 'workspace://SpacesStore/33126264-7462-4b99-962c-cb3c9745a78b', '', '', '', '', '2015-08-26 15:36:13.883887+07', 'admin', '2015-08-26 15:36:13.883887+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (172, 'SYSTEM', 'PCM_REQ_MAIL_SUBJECT', 'Pcm Req Mail Subject', true, 'กรุณาอนุมัติใบบันทึก (Memo) เลขที่ ${memoId}', '', '', '', '', '2015-08-26 15:36:48.066462+07', 'admin', '2015-08-26 15:36:48.066462+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (173, 'SYSTEM', 'PCM_REQ_MAIL_FROM', 'Pcm Req Mail From Label', true, 'Localhost', '', '', '', '', '2015-08-26 15:37:20.17415+07', 'admin', '2015-08-26 15:37:20.17415+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (491, 'A', 'TR', 'จัดฝึกอบรม', true, '', '', '', '', '', '2016-04-02 18:30:17.735904+07', 'admin', '2016-04-02 18:30:17.735904+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (201, 'SYSTEM', 'PCM_ORD_PATH_FORMAT', 'Pcm Order Path Format', true, 'PD/${fiscal_year[yyyy]}/${id}', '', '', '', '', '2016-03-19 18:16:44.558704+07', 'admin', '2016-03-19 18:16:44.558704+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (228, 'A', 'A', 'Master Type', true, 'ประเภทข้อมูลหลัก', '', NULL, NULL, NULL, '2015-04-28 18:50:30.125156+07', NULL, '2015-04-28 18:50:30.125156+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (544, 'SYSTEM', 'MAIN_RESET_SEQUENCE', 'Main Reset Sequence Names', true, 'pb2_pcm_req_id_seq', '', '', '', '', '2016-05-27 10:14:33.078138+07', 'admin', '2016-05-27 10:14:33.078138+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (533, 'A', 'PD_ST', 'PD Workflow Status', true, 'สถานะ Workflow PD', '', '', '', '', '2016-05-05 16:47:38.031627+07', 'admin', '2016-05-05 16:47:38.031627+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (213, 'A', 'PR_ST', 'PR Workflow Status', true, 'สถานะ Workflow PR', '', '', '', '', '2015-03-30 09:17:50.759+07', NULL, '2015-03-30 09:17:50.759+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (531, 'SYSTEM', 'PCM_ORD_CRITERIA_1', 'Pcm Order Criteria 1', true, 'Method', 'doc_type,500,500', 'main/master?orderBy=flag1', 'type=''PD''', '', '2016-05-05 15:38:29.529432+07', 'admin', '2016-05-05 15:38:29.529432+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (545, 'SYSTEM', 'EXP_BRW_RPT_TAB', 'Expense Borrow Report Tab', true, '0', '', '', '', '', '2016-05-28 14:03:24.156682+07', 'admin', '2016-05-28 14:03:24.156682+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (100, 'SYSTEM', 'PCM_REQ_SITE_ID', 'Pcm Req Site ID', true, 'pcm', '', '', '', '', '2016-03-01 18:21:46.282169+07', 'admin', '2016-03-01 18:21:46.282169+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (102, 'SYSTEM', 'PCM_REQ_ID_FORMAT', 'Pcm Req ID Format', true, 'PR${fiscal_year?["yy"]}${running_no?["000000"]}', '', '', '', '', '2016-03-01 17:52:55.201313+07', 'admin', '2016-03-01 17:52:55.201313+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (200, 'SYSTEM', 'PCM_ORD_SITE_ID', 'Pcm Order Site ID', true, 'pcm', '', '', '', '', '2016-03-19 18:22:24.096585+07', 'admin', '2016-03-19 18:22:24.096585+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (1, 'SYSTEM', 'MAIN_PAGING_SIZE', 'Main Paging Size', true, '40', '', '', '', '', '2015-05-21 15:32:24.553387+07', 'admin', '2015-05-21 15:32:24.553387+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (2, 'SYSTEM', 'MAIN_TEMP_PATH', 'Main Temp Path', true, 'workspace://SpacesStore/71ce6549-63b7-454c-96c1-5f98ae3e1b50', '', '', '', '', '2015-05-27 18:02:54.070201+07', 'admin', '2015-05-27 18:02:54.070201+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (3, 'SYSTEM', 'MAIN_SIGNATURE_PATH', 'Main Signature Path', true, 'workspace://SpacesStore/d3c83cb5-a3e5-47ec-9005-89dd5dc2103a', '', '', '', '', '2015-05-10 16:00:30.913119+07', 'admin', '2015-05-10 16:00:30.913119+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (101, 'SYSTEM', 'PCM_REQ_PATH_FORMAT', 'Pcm Req Path Format', true, 'PR/${fiscal_year[yyyy]}/${id}', '${objective_type}/${fiscal_year[yyyy]}/${id}', '', '', '', '2016-03-01 17:55:04.196065+07', 'admin', '2016-03-01 17:55:04.196065+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (500, 'MP', 'MP1', 'งานจ้างก่อสร้าง', true, 'workspace://SpacesStore/e3911456-981b-4ab4-ba19-4415ade8dfdf', '', '', '', '', '2016-04-02 20:18:42.320346+07', 'admin', '2016-04-02 20:18:42.320346+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (501, 'MP', 'MP2', 'การจ้างควบคุมงาน', true, 'workspace://SpacesStore/7808ad78-e4ab-4ab4-926b-b5be87ac91d9', '', '', '', '', '2016-04-02 20:18:56.956525+07', 'admin', '2016-04-02 20:18:56.956525+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (512, 'MP', 'MP4', 'การจ้างพัฒนาระบบคอมพิวเตอร์', true, 'workspace://SpacesStore/6c148cca-6889-4038-ad49-a4ca9acd67ab', '', '', '', '', '2016-04-05 08:14:16.789411+07', 'admin', '2016-04-05 08:14:16.789411+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (513, 'MP', 'MP5', 'การจ้างออกแบบ', true, 'workspace://SpacesStore/2d5addb6-a45f-4d68-bd42-a4a5aefb4622', '', '', '', '', '2016-04-05 08:14:34.15099+07', 'admin', '2016-04-05 08:14:34.15099+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (514, 'MP', 'MP6', 'การจ้างงานวิจัยหรือเงินสนับสนุนให้ทุนการวิจัย', true, 'workspace://SpacesStore/c30a371e-fe6a-4c76-a674-4cc074b0dc54', '', '', '', '', '2016-04-05 08:14:49.938248+07', 'admin', '2016-04-05 08:14:49.938248+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (150, 'SYSTEM', 'PCM_REQ_SEARCH_GRID_ORDER_BY', 'Pcm Req Search Grid Order by', true, 'ORDER_FIELD, updated_time DESC', '', '', '', '', '2016-01-07 12:24:43.579596+07', 'admin', '2016-01-07 12:24:43.579596+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (506, 'PTT', 'PTT1', 'ต้นแบบโครงการ (วิจัย)', true, '1', 'ต้นแบบโครงการ (วิจัย)', '', '', '', '2016-04-04 13:59:11.691689+07', 'admin', '2016-04-04 13:59:11.691689+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (220, 'SYSTEM', 'PCM_ORD_GRID_FIELD_2', 'Pcm Order Grid Field 2', true, 'org', 'org_name', '', '', '', '2016-03-14 13:24:41.634559+07', 'admin', '2016-03-14 13:24:41.634559+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (546, 'SYSTEM', 'EXP_BRW_GRID_FIELD_1', 'Expense Borrow Grid Field 1', true, 'avNo', 'id,100,0', '', '', '', '2016-05-29 13:43:11.271047+07', 'admin', '2016-05-29 13:43:11.271047+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (532, 'SYSTEM', 'PCM_ORD_CRITERIA_2', 'Pcm Order Criteria 2', true, 'สถานะ', 'status', 'main/master?orderBy=flag1', 'type=''PD_ST''', '', '2016-05-05 16:45:36.718636+07', 'admin', '2016-05-05 16:45:36.718636+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (120, 'SYSTEM', 'PCM_REQ_GRID_FIELD_1', 'Pcm Request Grid Field 1', true, 'prNo', 'id,100,,0', '', '', '', '2016-03-24 13:49:37.508309+07', 'admin', '2016-03-24 13:49:37.508309+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (223, 'SYSTEM', 'PCM_ORD_GRID_FIELD_4', 'Pcm Order Grid Field 4', true, 'objective', 'objective', '', '', '', '2016-03-14 13:26:11.68346+07', 'admin', '2016-03-14 13:26:11.68346+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (540, 'SYSTEM', 'PCM_REQ_GRID_FIELD_8', 'Pcm Request Grid Field 8', true, 'preparer', 'created_by,80', '', '', '', '2016-05-08 16:38:51.070593+07', 'admin', '2016-05-08 16:38:51.070593+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (541, 'SYSTEM', 'PCM_ORD_GRID_FIELD_1', 'Pcm Order Grid Field 1', true, 'pdNo', 'id,150', '', '', '', '2016-05-10 10:42:23.863062+07', 'admin', '2016-05-10 10:42:23.863062+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (542, 'SYSTEM', 'PCM_ORD_GRID_FIELD_7', 'Pcm Order Grid Field 7', true, 'requestTime', 'created_time_show,130', '', '', '', '2016-05-10 10:44:31.582372+07', 'admin', '2016-05-10 10:44:31.582372+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (590, 'SYSTEM', 'EXP_USE_GRID_FIELD_3', 'Expense Use Grid Field 3', true, 'budget', 'budget_cc_name,250', '', '', '', '2016-06-07 15:39:16.454573+07', 'admin', '2016-06-07 15:39:16.454573+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (595, 'SYSTEM', 'EXP_USE_GRID_FIELD_8', 'Expense Use Grid Field 8', true, 'requestTime', 'created_time_show,120', '', '', '', '2016-06-07 15:42:50.411647+07', 'admin', '2016-06-07 15:42:50.411647+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (508, 'A', 'PR_RSN', 'PR Reason', true, '', '', '', '', '', '2016-04-04 15:08:56.891023+07', 'admin', '2016-04-04 15:08:56.891023+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (203, 'PR_ST', 'D', 'Draft', true, '1', 'Draft', '', '', '', '2015-03-26 10:54:24.7+07', NULL, '2015-03-26 10:54:24.7+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (204, 'PR_ST', 'W1', 'รอการอนุมัติ', true, '3', 'Wait for Approval', '', '', '', '2015-03-26 10:54:42.32+07', NULL, '2015-03-26 10:54:42.32+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (205, 'PR_ST', 'C1', 'รอพัสดุรับงาน', true, '5', 'Wait for Acceptance', '', '', '', '2015-03-26 10:55:15.436+07', NULL, '2015-03-26 10:55:15.436+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (206, 'PR_ST', 'C2', 'พัสดุรับงาน', true, '6', 'Accepted', '', '', '', '2015-03-26 10:55:24.561+07', NULL, '2015-03-26 10:55:24.561+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (503, 'PR_ST', 'X1', 'ยกเลิกโดย ผู้ขอ', true, '7', 'Cancelled by Requester', '', '', '', '2016-04-02 21:10:44.023056+07', 'admin', '2016-04-02 21:10:44.023056+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (534, 'PD_ST', 'D', 'Draft', true, '1', 'Draft', '', '', '', '2016-05-05 16:50:04.098353+07', 'admin', '2016-05-05 16:50:04.098353+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (539, 'PD_ST', 'X1', 'ยกเลิกโดย พัสดุ', true, '6', 'Cancelled By Procurement', '', '', '', '2016-05-05 16:52:28.490405+07', 'admin', '2016-05-05 16:52:28.490405+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (225, 'SYSTEM', 'PCM_ORD_GRID_FIELD_6', 'Pcm Order Grid Field 6', true, 'preparer', 'created_by,80', '', '', '', '2016-03-14 13:28:16.89383+07', 'admin', '2016-03-14 13:28:16.89383+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (507, 'PTT', 'PTT2', 'ต้นแบบส่งมอบ', true, '2', 'ต้นแบบส่งมอบ', '', '', '', '2016-04-04 13:59:25.448892+07', 'admin', '2016-04-04 13:59:25.448892+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (151, 'SYSTEM', 'PCM_REQ_MSG_MISSING_NEXT_APP', 'Pcm Req Message Missing Next Approver', true, 'Next Approver', '', '', '', '', '2016-02-12 16:58:36.218211+07', 'admin', '2016-02-12 16:58:36.218211+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (152, 'SYSTEM', 'PCM_REQ_WF_FAIL_COND_1', 'Pcm Req Fail Condition 1', true, '2,field3=''IC''|field9!=''true'',Temporary Fix', '', '', '', '', '2016-02-15 13:05:22.242698+07', 'admin', '2016-02-15 13:05:22.242698+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (492, 'A', 'PU', 'หน่วยงานจัดซื้อจัดจ้าง', true, '', '', '', '', '', '2016-04-02 18:30:42.678997+07', 'admin', '2016-04-02 18:30:42.678997+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (375, 'R', 'R02', 'IC - สรุปสถิติ แยกตามทีม', true, 'IC', 'R02 Title', '', '', '', '2015-12-08 14:56:00.132702+07', 'walai', '2015-12-08 14:56:00.132702+07', 'walai');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (376, 'R', 'R03', 'IC - สรุปสถิติ แยกระดับผลกระทบ', true, 'IC', 'R03 Title', '', '', '', '2015-12-08 14:56:19.736133+07', 'walai', '2015-12-08 14:56:19.736133+07', 'walai');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (413, 'A', 'R', 'Report', true, '', '', '', '', '', '2016-02-04 11:17:35.663821+07', 'admin', '2016-02-04 11:17:35.663821+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (493, 'TR', 'TR1', 'อบรม 1', true, '', '', '', '', '', '2016-04-02 18:34:47.48904+07', 'admin', '2016-04-02 18:34:47.48904+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (494, 'TR', 'TR2', 'อบรม 2', true, '', '', '', '', '', '2016-04-02 18:35:06.077229+07', 'admin', '2016-04-02 18:35:06.077229+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (495, 'TR', 'TR3', 'อบรม 3', true, '', '', '', '', '', '2016-04-02 18:35:17.156982+07', 'admin', '2016-04-02 18:35:17.156982+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (454, 'FUND', 'C', 'Cost Center', true, '', '', '', '', '', '2016-02-26 19:19:51.48891+07', 'admin', '2016-02-26 19:19:51.48891+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (451, 'BA', '105014', '105014', true, '', '', '', '', '', '2016-02-26 19:19:03.012544+07', 'admin', '2016-02-26 19:19:03.012544+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (455, 'FUND', 'P', 'Project', true, '', '', '', '', '', '2016-02-26 19:20:48.172425+07', 'admin', '2016-02-26 19:20:48.172425+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (436, 'A', 'PC', 'Purchasing Type', true, 'PC', '', '', '', '', '2016-02-24 08:59:52.801248+07', 'admin', '2016-02-24 08:59:52.801248+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (452, 'BA', '105015', '105015', true, '', '', '', '', '', '2016-02-26 19:19:16.885364+07', 'admin', '2016-02-26 19:19:16.885364+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (453, 'BA', '105046', '105046', true, '', '', '', '', '', '2016-02-26 19:19:28.815388+07', 'admin', '2016-02-26 19:19:28.815388+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (556, 'A', 'BRW_TYPE', 'รายการยืมเงิน', true, '', '', '', '', '', '2016-05-30 11:43:06.286876+07', 'admin', '2016-05-30 11:43:06.286876+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (441, 'A', 'CUR', 'Currency', true, 'CUR', '', '', '', '', '2016-02-26 18:51:50.294962+07', 'admin', '2016-02-26 18:51:50.294962+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (442, 'CUR', 'THB', 'Thai Baht', true, '', '', '', '', '', '2016-02-26 18:52:15.503968+07', 'admin', '2016-02-26 18:52:15.503968+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (443, 'CUR', 'USD', 'U.S. Dollar', true, '', '', '', '', '', '2016-02-26 18:52:56.294799+07', 'admin', '2016-02-26 18:52:56.294799+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (444, 'A', 'STOCK', 'คลังจัดเก็บพัสดุ', true, '', '', '', '', '', '2016-02-26 19:16:36.851021+07', 'admin', '2016-02-26 19:16:36.851021+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (445, 'A', 'FUND', 'ศูนย์เงินทุน', true, '', '', '', '', '', '2016-02-26 19:16:57.893776+07', 'admin', '2016-02-26 19:16:57.893776+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (446, 'A', 'BA', 'หน่วยงาน', true, '', '', '', '', '', '2016-02-26 19:17:17.179928+07', 'admin', '2016-02-26 19:17:17.179928+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (448, 'STOCK', 'ST2', 'Biotec', true, '', '', '', '', '', '2016-02-26 19:17:57.239506+07', 'admin', '2016-02-26 19:17:57.239506+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (449, 'STOCK', 'ST3', 'Mtec', true, '', '', '', '', '', '2016-02-26 19:18:10.768522+07', 'admin', '2016-02-26 19:18:10.768522+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (450, 'STOCK', 'ST4', 'Nectec', true, '', '', '', '', '', '2016-02-26 19:18:31.676963+07', 'admin', '2016-02-26 19:18:31.676963+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (447, 'STOCK', 'ST1', 'Center', true, '', '', '', '', '', '2016-02-26 19:17:46.345281+07', 'admin', '2016-02-26 19:17:46.345281+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (543, 'SYSTEM', 'MAIN_INF_CHECK_BUDGET', 'Main Interface Check Budget', true, '0', '', '', '', '', '2016-05-26 17:01:53.789761+07', 'admin', '2016-05-26 17:01:53.789761+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (559, 'SYSTEM', 'EXP_BRW_ID_FORMAT', 'Expense Borrow ID Format', true, 'AV${fiscal_year?["yy"]}${running_no?["000000"]}', '', '', '', '', '2016-06-05 18:14:29.726702+07', 'admin', '2016-06-05 18:14:29.726702+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (563, 'SYSTEM', 'EXP_BRW_SITE_ID', 'Expense Borrow Site ID', true, 'pcm', '', '', '', '', '2016-06-05 18:21:00.63458+07', 'admin', '2016-06-05 18:21:00.63458+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (564, 'SYSTEM', 'EXP_USE_SITE_ID', 'Expense Use Site ID', true, 'pcm', '', '', '', '', '2016-06-05 18:21:16.866552+07', 'admin', '2016-06-05 18:21:16.866552+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (552, 'SYSTEM', 'EXP_BRW_CRITERIA_1', 'Expense Borrow Criteria 1', true, 'Type', 'objective_type', 'main/master?orderBy=flag1', 'type=''BRW_TYPE''', '', '2016-05-30 08:05:49.167603+07', 'admin', '2016-05-30 08:05:49.167603+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (551, 'SYSTEM', 'EXP_BRW_CRITERIA_2', 'Expense Borrow Criteria 2', true, 'Status', 'status', 'main/master?orderBy=flag1', 'type=''AV_ST''', '', '2016-05-29 15:27:28.804631+07', 'admin', '2016-05-29 15:27:28.804631+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (572, 'A', 'AV_ST', 'AV Workflow Status', true, 'สถานะ Workflow AV', '', '', '', '', '2016-06-07 12:27:23.289443+07', 'admin', '2016-06-07 12:27:23.289443+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (103, 'SYSTEM', 'PCM_REQ_WF_DESC_FORMAT', 'Pcm Req Workflow Description Format', true, '${id} » ${objective_type} ${objective} ${reason}', '', '', '', '', '2016-03-01 18:58:33.61801+07', 'admin', '2016-03-01 18:58:33.61801+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (456, 'A', 'PD', 'วิธีการจัดหา', true, '', '', '', '', '', '2016-02-27 11:34:37.423435+07', 'admin', '2016-02-27 11:34:37.423435+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (502, 'MP', 'MP3', 'การจ้างที่ปรึกษา', true, 'workspace://SpacesStore/a77fb4e1-1aca-47ab-a917-3e55416d5fdb', '', '', '', '', '2016-04-02 20:19:09.133286+07', 'admin', '2016-04-02 20:19:09.133286+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (515, 'MP', 'MP7', 'การจัดซื้อจัดจ้างที่มิใช่งานก่อสร้าง', true, 'workspace://SpacesStore/b2aa3d55-d09d-45a2-b6dd-e04bb21b8277', '', '', '', '', '2016-04-05 08:15:06.247067+07', 'admin', '2016-04-05 08:15:06.247067+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (224, 'SYSTEM', 'PCM_ORD_GRID_FIELD_5', 'Pcm Order Grid Field 5', true, 'total', 'total,,right', 'number', '', '', '2016-03-14 13:27:20.729331+07', 'admin', '2016-03-14 13:27:20.729331+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (562, 'SYSTEM', 'EXP_USE_PATH_FORMAT', 'Expense Use Path Format', true, 'EX/${fiscal_year[yyyy]}/${id}', '${objective_type}/${fiscal_year[yyyy]}/${id}', '', '', '', '2016-06-05 18:19:49.560886+07', 'admin', '2016-06-05 18:19:49.560886+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (207, 'SYSTEM', 'PCM_ORD_WF_DESC_FORMAT', 'Pcm Order Workflow Description Format', true, '${id} » ${objective}', '${id} » ${doc_type} » ${objective}', '', '', '', '2016-03-19 18:22:57.191277+07', 'admin', '2016-03-19 18:22:57.191277+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (124, 'SYSTEM', 'PCM_REQ_GRID_FIELD_5', 'Pcm Request Grid Field 5', true, 'amount', 'total,90,right', 'number', '', '', '2016-03-24 13:55:21.457562+07', 'admin', '2016-03-24 13:55:21.457562+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (566, 'SYSTEM', 'EXP_BRW_GRID_FIELD_3', 'Expense Borrow Grid Field 3', true, 'budget', 'budget_cc_name,250', '', '', '', '2016-06-07 09:30:00.139489+07', 'admin', '2016-06-07 09:30:00.139489+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (437, 'PC', 'ซื้อ', 'ซื้อ', true, '1', 'Buy', '', '', '', '2016-02-24 09:00:49.350937+07', 'admin', '2016-02-24 09:00:49.350937+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (438, 'PC', 'จ้าง', 'จ้าง', true, '2', 'Hire', '', '', '', '2016-02-24 09:01:07.075668+07', 'admin', '2016-02-24 09:01:07.075668+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (440, 'PC', 'เช่า', 'เช่า', true, '3', 'Rent', '', '', '', '2016-02-26 13:26:52.271699+07', 'admin', '2016-02-26 13:26:52.271699+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (504, 'PR_ST', 'X2', 'ยกเลิกโดย พัสดุ', true, '8', 'Cancelled by Procurement', '', '', '', '2016-04-02 21:11:04.270543+07', 'admin', '2016-04-02 21:11:04.270543+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (458, 'PD', 'PD2', 'วิธีสอบราคา', true, '2', 'วิธีสอบราคา - ราคาเกิน 300,000 บาท แต่ไม่เกิน 2,000,000 บาท', '', '', '', '2016-02-27 11:45:32.303578+07', 'admin', '2016-02-27 11:45:32.303578+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (549, 'SYSTEM', 'EXP_USE_GRID_FIELD_1', 'Expense Use Grid Field 1', true, 'exNo', 'id,100,0', '', '', '', '2016-05-29 15:05:32.188785+07', 'admin', '2016-05-29 15:05:32.188785+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (536, 'PD_ST', 'W2', 'ไม่อนุมัติ', true, '2', 'Rejected', '', '', '', '2016-05-05 16:50:49.434759+07', 'admin', '2016-05-05 16:50:49.434759+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (574, 'AV_ST', 'D', 'Draft', true, '1', 'Draft', '', '', '', '2016-06-07 12:29:28.341165+07', 'admin', '2016-06-07 12:29:28.341165+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (584, 'AP_ST', 'W1', 'รอการอนุมัติ', true, '3', 'Wait for Approval', '', '', '', '2016-06-07 13:01:56.423916+07', 'admin', '2016-06-07 13:01:56.423916+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (125, 'SYSTEM', 'PCM_REQ_GRID_FIELD_6', 'Pcm Request Grid Field 6', true, 'currency', 'currency,50', '', '', '', '2016-03-24 14:19:15.250307+07', 'admin', '2016-03-24 14:19:15.250307+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (457, 'PD', 'PD1', 'วิธีตกลงราคา', true, '1', 'วิธีตกลงราคา - ราคาไม่เกิน 300,000 บาท', '', '', '', '2016-02-27 11:35:24.518942+07', 'admin', '2016-02-27 11:35:24.518942+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (557, 'BRW_TYPE', 'buy_product', 'ยืมเพื่อซื้อวัสดุ/สินค้า', true, '1', 'For material/product', '', '', '', '2016-05-30 11:44:42.137594+07', 'admin', '2016-05-30 11:44:42.137594+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (517, 'PU', '34', 'พัสดุ - ศช.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (518, 'PU', '35', 'พัสดุ - ศว.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (519, 'PU', '36', 'พัสดุ - ศอ.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (520, 'PU', '37', 'พัสดุ - ศจ.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (521, 'PU', '38', 'พัสดุ - ศจ. (SWP)', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (522, 'PU', '39', 'พัสดุ - ศจ. (NNSTDA)', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (523, 'PU', '40', 'พัสดุ - ศน.', true, NULL, NULL, NULL, NULL, NULL, '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', NULL);
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (516, 'PU', '33', 'พัสดุ - สก.', true, '', '', '', '', '', '2016-04-19 10:24:57.603079+07', NULL, '2016-04-19 10:24:57.603079+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (573, 'A', 'AP_ST', 'AP Workflow Status', true, 'สถานะ Workflow AP', '', '', '', '', '2016-06-07 12:27:44.222844+07', 'admin', '2016-06-07 12:27:44.222844+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (548, 'SYSTEM', 'EXP_USE_RPT_TAB', 'Expense Use Report Tab', true, '0', '', '', '', '', '2016-05-29 15:05:04.615301+07', 'admin', '2016-05-29 15:05:04.615301+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (561, 'SYSTEM', 'EXP_BRW_PATH_FORMAT', 'Expense Borrow Path Format', true, 'AV/${fiscal_year[yyyy]}/${id}', '${objective_type}/${fiscal_year[yyyy]}/${id}', '', '', '', '2016-06-05 18:18:21.164077+07', 'admin', '2016-06-05 18:18:21.164077+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (597, 'SYSTEM', 'EXP_USE_CRITERIA_2', 'Expense Borrow Criteria 2', true, 'Status', 'status', 'main/master?orderBy=flag1', 'type=''AP_ST''', '', '2016-06-08 12:33:13.251551+07', 'admin', '2016-06-08 12:33:13.251551+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (598, 'A', 'EXP_TYPE', 'วิธีเบิกจ่าย/หักล้างค่าใช้จ่าย (เงินยืม)', true, '', '', '', '', '', '2016-06-09 14:07:33.066737+07', 'admin', '2016-06-09 14:07:33.066737+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (527, 'PR_ST', 'W2', 'ไม่อนุมัติ', true, '2', 'Rejected', '', '', '', '2016-04-20 17:01:33.729903+07', 'admin', '2016-04-20 17:01:33.729903+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (594, 'SYSTEM', 'EXP_USE_GRID_FIELD_7', 'Expense Use Grid Field 7', true, 'preparer', 'created_by,90', '', '', '', '2016-06-07 15:41:49.633299+07', 'admin', '2016-06-07 15:41:49.633299+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (570, 'SYSTEM', 'EXP_BRW_GRID_FIELD_7', 'Expense Borrow Grid Field 7', true, 'preparer', 'created_by,90', '', '', '', '2016-06-07 09:34:04.355433+07', 'admin', '2016-06-07 09:34:04.355433+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (571, 'SYSTEM', 'EXP_BRW_GRID_FIELD_8', 'Expense Borrow Grid Field 8', true, 'requestTime', 'created_time_show,90', '', '', '', '2016-06-07 09:34:38.318858+07', 'admin', '2016-06-07 09:34:38.318858+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (550, 'SYSTEM', 'EXP_USE_GRID_FIELD_2', 'Expense Use Grid Field 2', true, 'exType', 'pay_type_name,100', '', '', '', '2016-05-29 15:06:05.686669+07', 'admin', '2016-05-29 15:06:05.686669+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (565, 'SYSTEM', 'EXP_BRW_GRID_FIELD_2', 'Expense Borrow Grid Field 2', true, 'avType', 'objective_type_name,140,center', '', '', '', '2016-06-07 09:29:03.768615+07', 'admin', '2016-06-07 09:29:03.768615+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (568, 'SYSTEM', 'EXP_BRW_GRID_FIELD_5', 'Expense Borrow Grid Field 5', true, 'amount', 'total,100,right', 'number', '', '', '2016-06-07 09:31:28.792279+07', 'admin', '2016-06-07 09:31:28.792279+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (4, 'SYSTEM', 'PCM_ODOO_URL', 'PCM Odoo URL', true, 'http://10.226.202.133:8069', '', 'http://xx103.253.145.215:8069', '', '', '2016-05-02 17:15:32.465576+07', 'admin', '2016-05-02 17:15:32.465576+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (560, 'SYSTEM', 'EXP_USE_ID_FORMAT', 'Expense Use ID Format', true, 'EX${fiscal_year?["yy"]}${running_no?["000000"]}', '', '', '', '', '2016-06-05 18:14:56.843335+07', 'admin', '2016-06-05 18:14:56.843335+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (459, 'PD', 'PD4', 'วิธีประกวดราคา', true, '3', 'วิธีประกวดราคา - ราคาตั้งแต่ 2,000,000 บาท ขึ้นไป', '', '', '', '2016-02-27 11:46:02.052579+07', 'admin', '2016-02-27 11:46:02.052579+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (122, 'SYSTEM', 'PCM_REQ_GRID_FIELD_3', 'Pcm Request Grid Field 3', true, 'budget', 'budget_cc_name,250', '', '', '', '2016-03-24 13:53:11.602934+07', 'admin', '2016-03-24 13:53:11.602934+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (569, 'SYSTEM', 'EXP_BRW_GRID_FIELD_6', 'Expense Borrow Grid Field 6', true, 'requester', 'req_by,90', '', '', '', '2016-06-07 09:32:09.456639+07', 'admin', '2016-06-07 09:32:09.456639+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (592, 'SYSTEM', 'EXP_USE_GRID_FIELD_5', 'Expense Use Grid Field 5', true, 'amount', 'total,100,right', 'number', '', '', '2016-06-07 15:40:04.575948+07', 'admin', '2016-06-07 15:40:04.575948+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (593, 'SYSTEM', 'EXP_USE_GRID_FIELD_6', 'Expense Use Grid Field 6', true, 'requester', 'req_by,90', '', '', '', '2016-06-07 15:41:24.056436+07', 'admin', '2016-06-07 15:41:24.056436+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (528, 'PR_ST', 'S', 'ขอคำปรึกษา', true, '4', 'Consulting', '', '', '', '2016-04-20 17:01:57.563096+07', 'admin', '2016-04-20 17:01:57.563096+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (462, 'PD', 'PD3', 'วิธีประกวดราคาด้วยวิธีการอิเล็กทรอนิกส์', true, '6', 'วิธีประกวดราคาด้วยวิธีการอิเล็กทรอนิกส์ - ราคาตั้งแต่ 2,000,000 บาท ขึ้นไป', '', '', '', '2016-02-27 11:48:29.768209+07', 'admin', '2016-02-27 11:48:29.768209+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (599, 'EXP_TYPE', '0', 'พนักงาน', true, '', 'Employee', '', '', '', '2016-06-09 14:14:01.524964+07', 'admin', '2016-06-09 14:14:01.524964+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (537, 'PD_ST', 'S', 'ขอคำปรึกษา', true, '4', 'Consulting', '', '', '', '2016-05-05 16:51:13.740745+07', 'admin', '2016-05-05 16:51:13.740745+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (538, 'PD_ST', 'C1', 'ออก PO', true, '5', 'PO Created', '', '', '', '2016-05-05 16:51:53.574348+07', 'admin', '2016-05-05 16:51:53.574348+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (575, 'AV_ST', 'W1', 'รอการอนุมัติ', true, '3', 'Wait for Approval', '', '', '', '2016-06-07 12:31:44.937149+07', 'admin', '2016-06-07 12:31:44.937149+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (576, 'AV_ST', 'W2', 'ไม่อนุมัติ', true, '2', 'Rejected', '', '', '', '2016-06-07 12:32:48.156929+07', 'admin', '2016-06-07 12:32:48.156929+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (577, 'AV_ST', 'C1', 'รอการเงินรับงาน', true, '5', 'Wait for Acceptance', '', '', '', '2016-06-07 12:33:24.767754+07', 'admin', '2016-06-07 12:33:24.767754+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (578, 'AV_ST', 'C2', 'การเงินรับงาน', true, '6', 'Accepted', '', '', '', '2016-06-07 12:59:45.500232+07', 'admin', '2016-06-07 12:59:45.500232+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (579, 'AV_ST', 'X1', 'ยกเลิกโดย ผู้ขอ', true, '7', 'Cancelled By Requester', '', '', '', '2016-06-07 12:59:59.685094+07', 'admin', '2016-06-07 12:59:59.685094+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (580, 'AV_ST', 'X2', 'ยกเลิกโดย การเงิน', true, '8', 'Cancelled By Accounting', '', '', '', '2016-06-07 13:00:13.660825+07', 'admin', '2016-06-07 13:00:13.660825+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (581, 'AV_ST', 'S', 'ขอคำปรึกษา', true, '4', 'Consulting', '', '', '', '2016-06-07 13:00:35.013478+07', 'admin', '2016-06-07 13:00:35.013478+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (582, 'AP_ST', 'D', 'Draft', true, '1', 'Draft', '', '', '', '2016-06-07 13:01:28.23601+07', 'admin', '2016-06-07 13:01:28.23601+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (583, 'AP_ST', 'W2', 'ไม่อนุมัติ', true, '2', 'Rejected', '', '', '', '2016-06-07 13:01:41.116981+07', 'admin', '2016-06-07 13:01:41.116981+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (585, 'AP_ST', 'S', 'ขอคำปรึกษา', true, '4', 'Consulting', '', '', '', '2016-06-07 13:02:13.403795+07', 'admin', '2016-06-07 13:02:13.403795+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (586, 'AP_ST', 'C1', 'รอการเงินรับงาน', true, '5', 'Wait for Acceptance', '', '', '', '2016-06-07 13:02:26.855063+07', 'admin', '2016-06-07 13:02:26.855063+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (587, 'AP_ST', 'C2', 'การเงินรับงาน', true, 'ุ6', ' Accepted', '', '', '', '2016-06-07 13:02:57.587382+07', 'admin', '2016-06-07 13:02:57.587382+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (588, 'AP_ST', 'X1', 'ยกเลิกโดย ผู้ขอ', true, '7', 'Cancelled By Requester', '', '', '', '2016-06-07 13:03:13.20436+07', 'admin', '2016-06-07 13:03:13.20436+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (589, 'AP_ST', 'X2', 'ยกเลิกโดย การเงิน', false, '8', 'Cancelled By Accounting', '', '', '', '2016-06-07 13:03:30.647152+07', 'admin', '2016-06-07 13:03:30.647152+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (602, 'EXP_TYPE', '2', 'เคลียร์เงินยืมพนักงาน', true, '', 'Clear Advance', '', '', '', '2016-06-09 14:14:43.275419+07', 'admin', '2016-06-09 14:14:43.275419+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (558, 'BRW_TYPE', 'attend_seminar', 'ยืมเพื่อเดินทางสัมมนา', true, '2', 'For seminar', '', '', '', '2016-05-30 11:45:36.125395+07', 'admin', '2016-05-30 11:45:36.125395+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (600, 'EXP_TYPE', '1', 'หน่วยงาน/บุคคล ภายนอก', true, '', 'Outsider', '', '', '', '2016-06-09 14:14:16.659867+07', 'admin', '2016-06-09 14:14:16.659867+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (5, 'SYSTEM', 'PCM_ODOO_DB', 'PCM Odoo DB', true, 'PABI2_v7', '', '', '', '', '2016-05-02 17:15:56.649536+07', 'admin', '2016-05-02 17:15:56.649536+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (603, 'EXP_TYPE', '3', 'Internal Charge', true, '', 'Internal Charge', '', '', '', '2016-06-09 14:14:52.385884+07', 'admin', '2016-06-09 14:14:52.385884+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (596, 'SYSTEM', 'EXP_USE_CRITERIA_1', 'Expense Use Criteria 1', true, 'Method', 'pay_type', 'main/master?orderBy=flag1', 'type=''EXP_TYPE''', '', '2016-06-08 12:30:51.601959+07', 'admin', '2016-06-08 12:30:51.601959+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (617, 'SYSTEM', 'EXP_BRW_WF_DESC_FORMAT', 'Exp Brw Workflow Description Format', true, '${id} » ${objective}', '', '', '', '', '2016-08-22 17:27:43.293418+07', 'admin', '2016-08-22 17:27:43.293418+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (618, 'SYSTEM', 'EXP_USE_WF_DESC_FORMAT', 'Exp Use Workflow Description Format', true, '${id} » ${objective}', '', '', '', '', '2016-08-22 17:28:05.5658+07', 'admin', '2016-08-22 17:28:05.5658+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (460, 'PD', 'PD5', 'วิธีพิเศษ', true, '4', 'วิธีพิเศษ - ราคาเกิน 300,000 บาท และต้องเป็นกรณีตามข้อบังคับ กวทช.ว่าด้วยการพัสดุ พ.ศ. 2543 ข้อ 22 หรือ 23', '', '', '', '2016-02-27 11:46:59.200559+07', 'admin', '2016-02-27 11:46:59.200559+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (616, 'SYSTEM', 'PCM_REQ_REF_PR_PERCENT', 'Pcm Req Ref PR Percent', true, '3000', '', '', '', '', '2016-08-22 13:20:48.603283+07', 'admin', '2016-08-22 13:20:48.603283+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (604, 'SYSTEM', 'MAIN_INF_PD_UPDATE_STATUS', 'Main Interface PD Update Status', true, '1', '', '', '', '', '2016-07-05 08:06:25.960855+07', 'admin', '2016-07-05 08:06:25.960855+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (605, 'SYSTEM', 'PCM_REQ_CMT_CHECK_PARAMS', 'Pcm Req Committee Check Parameters', true, '50000,1,3', '', '', '', '', '2016-07-05 08:07:36.704622+07', 'admin', '2016-07-05 08:07:36.704622+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (123, 'SYSTEM', 'PCM_REQ_GRID_FIELD_4', 'Pcm Request Grid Field 4', true, 'objective', 'objective,230,,1', '', '', '', '2016-03-24 13:54:40.186708+07', 'admin', '2016-03-24 13:54:40.186708+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (567, 'SYSTEM', 'EXP_BRW_GRID_FIELD_4', 'Expense Borrow Grid Field 4', true, 'objective', 'objective,230,,1', '', '', '', '2016-06-07 09:30:39.838721+07', 'admin', '2016-06-07 09:30:39.838721+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (591, 'SYSTEM', 'EXP_USE_GRID_FIELD_4', 'Expense Use Grid Field 4', true, 'objective', 'objective,230,,1', '', '', '', '2016-06-07 15:39:39.188775+07', 'admin', '2016-06-07 15:39:39.188775+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (609, 'SYSTEM', 'PCM_ORD_SIGN_FONT_SIZE', 'Pcm Ord Signature Font Size', true, '14', '', '', '', '', '2016-07-20 17:51:22.47041+07', 'admin', '2016-07-20 17:51:22.47041+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (126, 'SYSTEM', 'PCM_REQ_GRID_FIELD_7', 'Pcm Request Grid Field 7', true, 'requester', 'req_by,80', '', '', '', '2016-04-20 12:51:24.060687+07', 'admin', '2016-04-20 12:51:24.060687+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (621, 'SYSTEM', 'PCM_REQ_GRID_FIELD_9', 'Pcm Request Grid Field 9', true, 'requestTime', 'created_time_show,120', '', '', '', '2016-08-25 15:52:37.117476+07', 'admin', '2016-08-25 15:52:37.117476+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (608, 'SYSTEM', 'PCM_ORD_SIGN_POS', 'Pcm Ord Signature Position', true, '460,105', '', '', '', '', '2016-07-20 17:50:49.933557+07', 'admin', '2016-07-20 17:50:49.933557+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (610, 'SYSTEM', 'PCM_ORD_SIGN_DATE_OFFSET', 'Pcm Ord Signature Date Offset', true, '30,-35', '', '', '', '', '2016-07-20 17:51:53.513402+07', 'admin', '2016-07-20 17:51:53.513402+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (461, 'PD', 'PD6', 'วิธีกรณีพิเศษ', true, '5', 'วิธีกรณีพิเศษ - การซื้อหรือการจ้างจากส่วนราชการ รัฐวิสาหกิจ หรือองค์การของรัฐ', '', '', '', '2016-02-27 11:47:54.568948+07', 'admin', '2016-02-27 11:47:54.568948+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (611, 'PR_RSN', 'ใกล้หมด', 'ใกล้หมด', true, '1', 'Run out', '', '', '', '2016-08-01 17:32:02.950683+07', 'admin', '2016-08-01 17:32:02.950683+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (612, 'PR_RSN', 'ชำรุด', 'ชำรุด', true, '2', 'Broken', '', '', '', '2016-08-01 17:32:27.89752+07', 'admin', '2016-08-01 17:32:27.89752+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (613, 'PR_RSN', 'เสื่อมสภาพ', 'เสื่อมสภาพ', true, '3', 'Degenerate', '', '', '', '2016-08-01 17:32:51.646717+07', 'admin', '2016-08-01 17:32:51.646717+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (614, 'PR_RSN', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', 'ยังไม่เคยจัดซื้อจัดจ้างมาก่อน', true, '4', 'New', '', '', '', '2016-08-01 17:33:41.149733+07', 'admin', '2016-08-01 17:33:41.149733+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (615, 'PR_RSN', 'อื่นๆ', 'อื่นๆ', true, '5', 'Other', '', '', '', '2016-08-01 17:34:05.971325+07', 'admin', '2016-08-01 17:34:05.971325+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (535, 'PD_ST', 'W1', 'รอการอนุมัติ', true, '3', 'Wait for Approval', '', '', '', '2016-05-05 16:50:27.380874+07', 'admin', '2016-05-05 16:50:27.380874+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (629, 'SYSTEM', 'PCM_ORD_GRID_FIELD_3', 'Pcm Order Grid Field 3', true, 'method', 'method_name', '', '', '', '2016-10-24 14:56:33.495806+07', 'admin', '2016-10-24 14:56:33.495806+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (606, 'SYSTEM', 'EXP_ODOO_URL', 'EXP Odoo URL', true, 'http://10.226.202.133:8069', '', '', '', '', '2016-07-06 21:48:08.100591+07', 'admin', '2016-07-06 21:48:08.100591+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (622, 'SYSTEM', 'MAIN_INF_AV_CREATE_AV', 'Main Interface Create AV', true, '1', '', '', '', '', '2016-09-06 08:36:39.761775+07', 'admin', '2016-09-06 08:36:39.761775+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (607, 'SYSTEM', 'EXP_ODOO_DB', 'EXP Odoo DB', true, 'PABI2_v7', '', '', '', '', '2016-07-06 21:48:33.644482+07', 'admin', '2016-07-06 21:48:33.644482+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (121, 'SYSTEM', 'PCM_REQ_GRID_FIELD_2', 'Pcm Request Grid Field 2', true, 'prType', 'objective_type_name,60,center', '', '', '', '2016-03-24 13:51:30.20756+07', 'admin', '2016-03-24 13:51:30.20756+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (627, 'SYSTEM', 'EXP_BRW_TOP_GROUP', 'Exp Borrow Top Group', true, 'account_finance', '', '', '', '', '2016-10-01 10:14:30.874065+07', 'admin', '2016-10-01 10:14:30.874065+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (628, 'SYSTEM', 'EXP_USE_TOP_GROUP', 'Exp Use Top Group', true, 'account_finance', '', '', '', '', '2016-10-01 10:14:51.752246+07', 'admin', '2016-10-01 10:14:51.752246+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (626, 'SYSTEM', 'PCM_ORD_TOP_GROUP', 'Pcm Order Top Group', true, '', '', '', '', '', '2016-10-01 10:14:12.758887+07', 'admin', '2016-10-01 10:14:12.758887+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (625, 'SYSTEM', 'PCM_REQ_TOP_GROUP', 'Pcm Request Top Group', true, '', '', '', '', '', '2016-10-01 10:13:53.545355+07', 'admin', '2016-10-01 10:13:53.545355+07', 'admin');
INSERT INTO pb2_main_master (id, type, code, name, is_active, flag1, flag2, flag3, flag4, flag5, created_time, created_by, updated_time, updated_by) VALUES (630, 'EXP_TYPE', '4', 'เงินสดย่อย', false, '', 'Petty Cash', '', '', '', '2016-10-25 16:45:11.341791+07', 'admin', '2016-10-25 16:45:11.341791+07', 'admin');
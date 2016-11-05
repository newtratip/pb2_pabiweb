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
-- TOC entry 3043 (class 0 OID 0)
-- Dependencies: 397
-- Name: pb2_exp_brw_dtl_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_exp_brw_dtl_id_seq', 914, true);





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
-- TOC entry 2946 (class 0 OID 72596)
-- Dependencies: 360
-- Data for Name: pb2_main_msg; Type: TABLE DATA; Schema: public; Owner: alfresco
--





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
-- TOC entry 3048 (class 0 OID 0)
-- Dependencies: 366
-- Name: pb2_main_workflow_next_actor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_main_workflow_next_actor_id_seq', 1723, true);



--
-- TOC entry 3049 (class 0 OID 0)
-- Dependencies: 368
-- Name: pb2_main_workflow_reviewer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_main_workflow_reviewer_id_seq', 3668, true);



--
-- TOC entry 3050 (class 0 OID 0)
-- Dependencies: 372
-- Name: pb2_pcm_req_committee_dtl_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_pcm_req_committee_dtl_id_seq', 6581, true);



--
-- TOC entry 3051 (class 0 OID 0)
-- Dependencies: 374
-- Name: pb2_pcm_req_committee_hdr_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alfresco
--

SELECT pg_catalog.setval('pb2_pcm_req_committee_hdr_id_seq', 2614, true);



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



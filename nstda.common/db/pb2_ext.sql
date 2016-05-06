CREATE FOREIGN TABLE pb2_ext_hr_employee (
        id integer NOT NULL,
        employee_code character varying,
        title character varying,
        first_name character varying,
        last_name character varying,
        gender character varying,
        mobile_phone character varying,
        work_email character varying,
        work_phone character varying,
        work_location character varying,
        org_id integer,
        costcenter_id integer,
        section_id integer,
        department_id integer,
        image bytea,
        image_small bytea,
	    name_related character varying ,
	    position_id integer ,
	    position_management_id integer ,
	    is_management boolean
)
        SERVER local_server
        OPTIONS (table_name 'poon_hr_employee');
        
ALTER FOREIGN TABLE pb2_ext_hr_employee OWNER TO alfresco;


CREATE FOREIGN TABLE public.pb2_ext_hr_position (
		id integer NOT NULL,
    	description text ,
    	name character varying NOT NULL
)
		SERVER local_server
		OPTIONS (table_name 'poon_hr_position');
		
ALTER FOREIGN TABLE public.pb2_ext_hr_position OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_purchasingunit_section_rel (
        purchasing_unit_id integer NOT NULL,
        section_id integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_purchasingunit_section_rel');
        
ALTER FOREIGN TABLE pb2_ext_purchasingunit_section_rel OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_res_org (
        id integer NOT NULL,
        description text,
        name character varying
)
        SERVER local_server
        OPTIONS (table_name 'poon_res_org');
        
ALTER FOREIGN TABLE pb2_ext_res_org OWNER TO alfresco;
        
        
CREATE FOREIGN TABLE pb2_ext_res_costcenter (
        id integer NOT NULL,
        description text,
        name character varying,
        mission_id integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_res_costcenter');
        
ALTER FOREIGN TABLE pb2_ext_res_costcenter OWNER TO alfresco;        

        
CREATE FOREIGN TABLE pb2_ext_res_division (
        id integer NOT NULL,
        description text,
        name character varying,
        org_id integer,
        department_id integer NOT NULL
)
        SERVER local_server
        OPTIONS (table_name 'poon_res_division');
        
ALTER FOREIGN TABLE pb2_ext_res_division OWNER TO alfresco;        
        
CREATE FOREIGN TABLE pb2_ext_res_partner_title (
        id integer NOT NULL,
        domain character varying,
        name character varying,
        shortcut character varying
)
        SERVER local_server
        OPTIONS (table_name 'poon_res_partner_title');
        
ALTER FOREIGN TABLE pb2_ext_res_partner_title OWNER TO alfresco;
        

CREATE FOREIGN TABLE pb2_ext_res_project (
        id integer NOT NULL,
        description text,
        name character varying,
        org_id integer,
        costcenter_id integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_res_project');

ALTER FOREIGN TABLE pb2_ext_res_project OWNER TO alfresco;

        
CREATE FOREIGN TABLE pb2_ext_res_project_member (
        id integer NOT NULL,
        project_id integer,
        project_position character varying,
        emp_id integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_res_project_member');
        
ALTER FOREIGN TABLE pb2_ext_res_project_member OWNER TO alfresco;

        
CREATE FOREIGN TABLE pb2_ext_res_section (
        id integer NOT NULL,
        description text,
        name character varying,
        division_id integer,
        sector_id integer,
        org_id integer,
        costcenter_id integer,
        department_id integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_res_section');
        
ALTER FOREIGN TABLE pb2_ext_res_section OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_wkf_cmd_approval_amount (
        id integer NOT NULL,
        level integer,
        org_id integer,
        minimum double precision,
        maximum double precision,
        doctype_id integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_wkf_cmd_approval_amount');
        
ALTER FOREIGN TABLE pb2_ext_wkf_cmd_approval_amount OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_wkf_cmd_boss_level_approval (
        id integer NOT NULL,
        level integer,
        hr_employee_id integer,
        section_id integer,
        org_id integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_wkf_cmd_boss_level_approval');
        
ALTER FOREIGN TABLE pb2_ext_wkf_cmd_boss_level_approval OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_wkf_cmd_boss_special_level (
        id integer NOT NULL,
        hr_employee_id integer,
        special_level integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_wkf_cmd_boss_special_level');
        
ALTER FOREIGN TABLE pb2_ext_wkf_cmd_boss_special_level OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_wkf_cmd_level (
        id integer NOT NULL,
        description text,
        name character varying
)
        SERVER local_server
        OPTIONS (table_name 'poon_wkf_cmd_level');
        
ALTER FOREIGN TABLE pb2_ext_wkf_cmd_level OWNER TO alfresco;


CREATE FOREIGN TABLE public.pb2_ext_wkf_cmd_section_assign (
		id integer NOT NULL,
    	employee_id integer NOT NULL,
    	section_id integer NOT NULL
)
   		SERVER local_server
   		OPTIONS (table_name 'poon_wkf_cmd_section_assign');
   
ALTER FOREIGN TABLE public.pb2_ext_wkf_cmd_section_assign OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_wkf_cmd_special_amount_project_approval (
        id integer NOT NULL,
        minimum double precision,
        maximum double precision,
        doctype_id integer,
        emp_id integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_wkf_cmd_special_amount_project_approval');
        
ALTER FOREIGN TABLE pb2_ext_wkf_cmd_special_amount_project_approval OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_wkf_config_doctype (
        id integer NOT NULL,
        description text,
        name character varying,
        module character varying
)
        SERVER local_server
        OPTIONS (table_name 'poon_wkf_config_doctype');
        
ALTER FOREIGN TABLE pb2_ext_wkf_config_doctype OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_wkf_config_pi_unit (
        id integer NOT NULL,
        description text,
        name character varying,
        org_id integer,
        doctype_id integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_wkf_config_pi_unit');
        
ALTER FOREIGN TABLE pb2_ext_wkf_config_pi_unit OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_wkf_config_pi_unit_responsible (
        id integer NOT NULL,
        level character varying,
        hr_employee_id integer,
        purchasing_unit_id integer
)
        SERVER local_server
        OPTIONS (table_name 'poon_wkf_config_pi_unit_responsible');
        
ALTER FOREIGN TABLE pb2_ext_wkf_config_pi_unit_responsible OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_account_tax (
        id integer NOT NULL,
        tax_code_id integer,
        type character varying,
        name character varying,
        amount numeric,
        type_tax_use character varying,
        active boolean
)
        SERVER local_server
        OPTIONS (table_name 'poon_account_tax');
        
ALTER FOREIGN TABLE pb2_ext_account_tax OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_res_currency (
        id integer NOT NULL,
        name character varying
)
        SERVER local_server
        OPTIONS (table_name 'poon_res_currency');
        
ALTER FOREIGN TABLE pb2_ext_res_currency OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_res_currency_rate (
        id integer NOT NULL,
        currency_id integer,
        rate numeric,
        write_date timestamp without time zone
)
        SERVER local_server
        OPTIONS (table_name 'poon_res_currency_rate');
        
ALTER FOREIGN TABLE pb2_ext_res_currency_rate OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_product_uom (
        id integer NOT NULL,
        name character varying
)
        SERVER local_server
        OPTIONS (table_name 'poon_product_uom');
        
ALTER FOREIGN TABLE pb2_ext_product_uom OWNER TO alfresco;


CREATE FOREIGN TABLE pb2_ext_purchase_request_committee (
        id bigint NOT NULL,
        obj character varying,
        method character varying,
        cond1 character varying,
        cond2 character varying,
		committee1 character varying(255),
		committee2 character varying(255),
		committee3 character varying(255),
		committee4 character varying(255),
		amount_from real,
		amount_to real        
)
        SERVER local_server
        OPTIONS (table_name 'poon_purchase_request_committee');
        
ALTER FOREIGN TABLE pb2_ext_purchase_request_committee OWNER TO alfresco;


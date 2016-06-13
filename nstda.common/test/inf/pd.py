#!/usr/bin/env python
# -*- coding: utf-8 -*-
import xmlrpclib
import base64

def b64str(fname):
	with open(fname) as f:
		encoded = base64.b64encode(f.read())
		return encoded


alfresco = xmlrpclib.ServerProxy("http://admin:password@localhost:8080/alfresco/s/pb/pcm/inf")

doc = b64str('PR_2015011901.pdf')
att1 = b64str('PR_2015011901.pdf')
att2 = b64str('PR_2015011901.pdf')

arg = {
	'action':'1',
	'pdNo':'PD16000002',
	'sectionId':'1',
	'prNo':'PR16000001,PR16000002',
	'docType':'PD1',
	'objective':u'Buy Something 1 piece ทดสอบ',
	'total':'15000.00',
	'reqBy':'000511',
	'appBy':'001509',
	'doc':{'name':'PD16000002.pdf','content':doc},
	'attachments':[{'name':'A.pdf','content':att1},{'name':'B.pdf','content':att2}]
}
result = alfresco.ord.create(arg);

print result 

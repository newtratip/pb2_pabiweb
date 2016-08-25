#!/usr/bin/env python
# -*- coding: utf-8 -*-
import xmlrpclib
import base64

def b64str(fname):
	with open(fname) as f:
		encoded = base64.b64encode(f.read())
		return encoded


alfresco = xmlrpclib.ServerProxy("http://admin:password@localhost:8080/alfresco/s/pb/pcm/inf")

doc = b64str('PD.pdf')
att1 = b64str('PR_2015011901.pdf')
att2 = b64str('PR_2015011901.pdf')

# action : 1=create, 2=resubmit, 3=cancel

arg = {
	'action':'1',
	'pdNo':'PD16000025',
	'sectionId':'44',
	'prNo':'PR16000001,PR16000002',
	'docType':'PD1',
	'objective':u'Test Comment Interface ทดสอบ',
	'total':'15000.00',
	'reqBy':'001509',
	'appBy':'001509',
	'doc':{'name':'PD16000002.pdf','content':doc},
	'attachments':[{'name':'A.pdf','content':att1},{'name':'B.pdf','content':att2}],
	'comment':'Request Request',
}
result = alfresco.ord.action(arg);

print result 

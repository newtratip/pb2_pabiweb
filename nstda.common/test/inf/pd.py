#!/usr/bin/env python
# -*- coding: utf-8 -*-
import xmlrpclib
import base64

def b64str(fname):
	with open(fname) as f:
		encoded = base64.b64encode(f.read())
		return encoded


alfresco = xmlrpclib.ServerProxy("http://003556:password@localhost:8080/alfresco/s/pb/pcm/inf")

doc = b64str('PD.pdf')
att1 = b64str('PR_2015011901.pdf')
att2 = b64str('PR_2015011901.pdf')

# action : 1=create, 2=resubmit, 3=cancel

arg = {
	'action':'1',
	'pdNo':'PD17000020',
	'sectionId':'59',
	'prNo':'PR17000001,PR17000002',
	'docType':'PD4',
	'objective':u'Test Comment Interface ทดสอบ',
	'total':'2675000.00',
	'reqBy':'002648',
	'appBy':'001509',
	'doc':{'name':'PD17000020.pdf','content':doc},
	'attachments':[{'name':'A.pdf','url':'a959e4e5-f551-43ed-8275-8f75356df95c'},{'name':'B.pdf','content':att2}],
	'comment':'Request Request',
}
result = alfresco.ord.action(arg);

print result 

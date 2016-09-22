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
	'pdNo':'PD16000046',
	'sectionId':'67',
	'prNo':'PR16000001,PR16000002',
	'docType':'PD4',
	'objective':u'Test Comment Interface ทดสอบ',
	'total':'2675000.00',
	'reqBy':'003556',
	'appBy':'001587',
	'doc':{'name':'PD16000045.pdf','content':doc},
	'attachments':[{'name':'A.pdf','content':att1,'url':'c496fb12-efc6-4bac-8cbd-c9ff3036f57d'},{'name':'B.pdf','content':att2}],
	'comment':'Request Request',
}
result = alfresco.ord.action(arg);

print result 

#!/usr/bin/env python
# -*- coding: utf-8 -*-
import xmlrpclib

alfresco = xmlrpclib.ServerProxy("http://003556:password@localhost:8080/alfresco/s/pb/exp/inf")

# action : 1=approve, 2=cancel, 3=paid

arg = {
	'action':'2',
	'avNo':'AV16000095',
	'by':'002648',
	'comment':'Cancel',
}
result = alfresco.brw.action(arg);

print result 

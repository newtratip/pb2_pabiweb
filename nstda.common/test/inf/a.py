import xmlrpclib

alfresco = xmlrpclib.ServerProxy("http://admin:password@localhost:8080/alfresco/s/nstda/inf/invoke")

result = alfresco.pcm.approve("0001");
print result 

result = alfresco.pcm.reject("0002");
print result 

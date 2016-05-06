import xmlrpclib

alfresco = xmlrpclib.ServerProxy("http://002648:password@10.226.202.134/alfresco/s/pb/pcm/inf/req")

result = alfresco.action.approve("PR16000003","001587");
print result 

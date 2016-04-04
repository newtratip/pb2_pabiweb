package pb.repo.admin.xmlrpc;


public class PcmInvocationHandler
{

    public String approve(String prNo)
    {
        return "Approve "+prNo;
    }

    public String reject(String prNo)
    {
        return "Reject "+prNo;
    }
    
}

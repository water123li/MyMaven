package cn.shaviation.mymaven.student.action;
 
import org.apache.log4j.Logger;
 
import com.opensymphony.xwork2.ActionSupport;
 
public class LoginController extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private Logger log=Logger.getLogger(this.getClass());
     
    public String  login() {
        log.info("hello  i am limanman");
        return "succerss";
         
    }
 
}
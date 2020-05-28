package cs544.exercise13_1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

@Aspect
public class Logger implements ILogger {
    public void log(String logstring) {
        java.util.logging.Logger.getLogger("BankLogger").info(logstring);
    }

    @After("execution(* cs544.exercise13_1.EmailSender.*(..)) && args(email, message)")
    public void traceaftermethod(JoinPoint joinpoint, String email, String message) {
        System.out.println("method = " + joinpoint.getSignature().getName());

        // b
        System.out.println("address = " + email);
        System.out.println("message = " + message);

        // c
        EmailSender emailSender = (EmailSender) joinpoint.getTarget();
        System.out.println("outgoing mail server = " + emailSender.getOutgoingMailServer());
    }

    //d
    @Around("execution(* cs544.exercise13_1.CustomerDAO.*(..))")
    public Object invoke(ProceedingJoinPoint call) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start(call.getSignature().getName());
        Object retVal = call.proceed();
        sw.stop();
        long totaltime = sw.getLastTaskTimeMillis();
        // print the time to the console
        System.out.println("Time to execute save = " + totaltime);

        return retVal;
    }
}

package cs544.exercise12_1.bank.logging;

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

    // a
    @After("execution(* cs544.exercise12_1.bank.dao.*.*(..))")
    public void traceaftermethod(JoinPoint joinpoint) {
        System.out.println("method = " + joinpoint.getSignature().getName());
    }

    // b
    @Around("execution(* cs544.exercise12_1.bank.service.*.*(..))")
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
    
    // c
    @After("execution(* cs544.exercise12_1.bank.jms.*.*(..))")
    public void traceaftermethodjms(JoinPoint joinpoint) {
        System.out.println("method = " + joinpoint.getSignature().getName());
    }
}

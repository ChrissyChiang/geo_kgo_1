package gov.kcg.kgo.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class FileLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileLogAspect.class);
    private static final String ENTRY_LAYER =
            "gov.kcg.kgo.aop.pointcut.PointcutDefinition.entryLayer()";


    @Around(value = ENTRY_LAYER)
    public Object entry(ProceedingJoinPoint jp) throws Throwable {
        Object result;
        String targetClassNameString = jp.getTarget().getClass().getSimpleName();
        String signatureNameString = jp.getSignature().getName();
        String methodNameString =
                String.format("%s : %s", targetClassNameString, signatureNameString);

        LOGGER.debug("{}() start.....", methodNameString);

        result = jp.proceed();

        LOGGER.debug("{}() end.....", methodNameString);

        return result;
    }

}

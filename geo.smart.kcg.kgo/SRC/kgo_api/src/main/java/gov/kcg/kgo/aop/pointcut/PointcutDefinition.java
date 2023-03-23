package gov.kcg.kgo.aop.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutDefinition {

    @Pointcut("execution(* gov.kcg.kgo.controller.mydata.MyDataServiceController.doMyDataModeOne(..))")
    public void myDataLayer() {}

    @Pointcut("execution(* gov.kcg.kgo.service.impl.CallMyDataServiceImpl.*(..))")
    public void callMyDataLayer() {}
    
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerLayer() {}
    
	/** 後臺API controller */
	@Pointcut( "within(gov.kcg.kgo.controller.backend.*) and execution(* gov.kcg.kgo.controller.backend.auth.*.*(..))")
	public void backendControllerLayer() {}
	
	/** 前臺API controller */
	@Pointcut( "within(gov.kcg.kgo.controller.frontend.*) and execution(* gov.kcg.kgo.controller.frontend.auth.*.*(..))")
	public void frontendControllerLayer() {}
	
	@Pointcut("@annotation(gov.kcg.kgo.aop.annotion.ValidCode)")
	public void validCode() {}

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerLayer() {}

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceLayer() {}

    @Pointcut("controllerLayer() || restControllerLayer() || serviceLayer()")
    public void entryLayer() {}
    
    @Pointcut("validCode() and frontendControllerLayer()")
    public void forntendValidateCode() {}

}

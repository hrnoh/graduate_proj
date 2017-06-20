package org.kpu.ng.aop;

import javax.inject.Inject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.kpu.ng.domain.LogVO;
import org.kpu.ng.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvice {
	@Inject
	LogService logService;
	
	private static final Logger logger = LoggerFactory.getLogger(LogAdvice.class);
	
	@AfterReturning(value = "execution(* *.infoResponse(..))",
			returning="logVO")
	public void addLog(JoinPoint jp, LogVO logVO) throws Exception {
		if(logVO != null) {
			logService.regist(logVO);
			logger.info("로그 추가 완료");
		}
		
		logger.info("로그 남기기 실행!");
	}
}

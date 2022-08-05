package hello.hellospring.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//aop 공통관심사항!
@Aspect //aop 면 적어줘야 함
@Component //스프링 빈에도 등록해주기 <- 컴포넌트 스캔 방식
//config 에 추가하는 방법도 있음
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") //원하는 조건
    //위는 이 패키지 하위로 다 적용해라- 는 조건 (클래스 명으로도 가능)
    //서비스 하위만 하고 싶다 -> * hello.hellospring.service..*(..) 지정하면 된다
    public Object execute(ProceedingJoinPoint joinPoint)throws Throwable{
        //중간에 인터셉팅 해서 실행할 수 있는 기술...
        long start = System.currentTimeMillis();
        System.out.println("START: "+joinPoint.toString());
        try {
            return joinPoint.proceed(); //proceed -> 다음 메소드로 진행시켜줌 프록시?
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish -start;
            System.out.println("END: "+joinPoint.toString()+" "+timeMs+"ms");
        }
    }
}

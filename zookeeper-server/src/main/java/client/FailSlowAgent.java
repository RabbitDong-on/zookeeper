package client;

import java.rmi.Naming;
import utils.*;
import scheduler.*;
/*
 * FailSlowAgent
 * 这里需要向failslow engine发送信息，通过RPC的参数进行传递
 * todo : 确认我们需要传递的信息
 */
public class FailSlowAgent{

    public static void main(String[] args){
        try{
            /*
             * 0 id report progress
             * 1 start trial
             * 2 end trial
             */
            SchedulerService access=(SchedulerService)Naming.lookup("rmi://172.30.0.5:2359/failslow");
            if(args.length==2){
                int id=Integer.parseInt(args[1]);
                access.isGoingOn(id);
            }else {
                int option=Integer.parseInt(args[0]);
                if(option==1){
                    access.startTrial();
                }else{
                    access.endTrial();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        // report();
    }

    public static void reportProgress(int id){
        try{
            SchedulerService access=(SchedulerService)Naming.lookup("rmi://172.30.0.5:2359/failslow");
            // SchedulerService access=(SchedulerService)Naming.lookup("rmi://localhost:2359/failslow");
            access.isGoingOn(id);
        }catch (Exception e){
            e.printStackTrace();
        } 
    }
    public static void report1(){
        try{
            // int type=0;
            FailSlowAgent agent=new FailSlowAgent();
            SchedulerService access=(SchedulerService)Naming.lookup("rmi://172.30.0.5:2359/failslow");
            // SchedulerService access=(SchedulerService)Naming.lookup("rmi://localhost:2359/failslow");
            ScheduleInfo info=agent.getInfo();
            info.type=1;
            ScheduleRes res=access.isInjectDelay(info);
            System.out.println("isInject : "+res.isInject+" delayTime : "+res.delayTime);
            if(res.isInject){
                delay(res.delayTime);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void report2(){
        try{
            // int type=0;
            FailSlowAgent agent=new FailSlowAgent();
            SchedulerService access=(SchedulerService)Naming.lookup("rmi://172.30.0.5:2359/failslow");
            // SchedulerService access=(SchedulerService)Naming.lookup("rmi://localhost:2359/failslow");
            ScheduleInfo info=agent.getInfo();
            info.type=2;
            ScheduleRes res=access.isInjectDelay(info);
            System.out.println("isInject : "+res.isInject+" delayTime : "+res.delayTime);
            if(res.isInject){
                delay(res.delayTime);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void report0(){
        try{
            // int type=0;
            FailSlowAgent agent=new FailSlowAgent();
            SchedulerService access=(SchedulerService)Naming.lookup("rmi://172.30.0.5:2359/failslow");
            // SchedulerService access=(SchedulerService)Naming.lookup("rmi://localhost:2359/failslow");
            ScheduleInfo info=agent.getInfo();
            info.type=0;
            ScheduleRes res=access.isInjectDelay(info);
            System.out.println("isInject : "+res.isInject+" delayTime : "+res.delayTime);
            if(res.isInject){
                delay(res.delayTime);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
     * getInfo
     * 获取当前delaypoint信息
     * 1. 编号交给Engine进行编号吧
     * 2. threadid
     * 3. className
     * 4. functionName
     */
    public ScheduleInfo getInfo(){
        long tId=Thread.currentThread().getId();
        String className=Thread.currentThread().getStackTrace()[3].getClassName();
        String funcName=Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNum=Thread.currentThread().getStackTrace()[3].getLineNumber();
        ScheduleInfo info=new ScheduleInfo();
        info.className=className;
        info.funcName=funcName;
        info.tId=tId;
        info.lineNum=lineNum;
        info.lastSTE=Thread.currentThread().getStackTrace();
        System.out.println("Thread ID : "+tId+" className : "+className+" funcName : "+funcName+" lineNum : "+lineNum);
        return info;
    }

    /*
    * delay
    * 被插桩的函数
    */
    public static void delay(long time){
        try{
            System.out.println("--------delay---------");
            Thread.sleep(time);
        }catch(InterruptedException ie){
            System.out.println("delay error!");
        }
    }
}
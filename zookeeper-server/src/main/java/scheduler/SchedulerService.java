package scheduler;
import utils.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SchedulerService extends Remote{
    /*
     * FailSlowAgent询问是否需要inject delay
     * 具体的实现在SchedulerServiceImpl中实现
     */
    ScheduleRes isInjectDelay(ScheduleInfo info) throws RemoteException;

    /*
     * reportProgress client想server报告进度
     * 如果调用就代表完成
     */
    void isGoingOn(int id) throws RemoteException;
    /*
     * startTrial agent告诉开始执行trial
     */
    void startTrial() throws RemoteException;
    /*
     * endTrial agent告诉结束执行trial
     */
    void endTrial() throws RemoteException;
}
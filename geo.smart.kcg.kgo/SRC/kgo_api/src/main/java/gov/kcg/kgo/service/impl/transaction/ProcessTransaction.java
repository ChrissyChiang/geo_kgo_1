package gov.kcg.kgo.service.impl.transaction;

public interface ProcessTransaction {
    String showProcessImg(String processInstanceId);
    
    public boolean isEnd(String processId);
    
    public boolean isLastTask(String taskId);
}

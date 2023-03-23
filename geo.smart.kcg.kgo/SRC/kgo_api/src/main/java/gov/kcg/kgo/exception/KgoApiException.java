package gov.kcg.kgo.exception;

/**
 * Kgo 前後端分離exception 共通處理.
 * The Class KcgApiException.
 */
public class KgoApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    /** 錯誤訊息. */
    private ErrorResult errorResult = new ErrorResult();
	
	public KgoApiException(){}
	
	public KgoApiException(Throwable cause){super(cause);}
	 
    public KgoApiException(String message){ super(message);}

    public KgoApiException(String message, Throwable cause){

        super(message, cause);
    }
    
    public KgoApiException(ErrorResult errorResult){
    	super(errorResult.getErrorDesc());
    	this.errorResult = errorResult;
    }
    
    public KgoApiException(ErrorResult errorResult, Throwable e){
    	super(errorResult.getErrorDesc(), e);
    	this.errorResult = errorResult;
    }

	public ErrorResult getErrorResult() {
		return errorResult;
	}

	public void setErrorResult(ErrorResult errorResult) {
		this.errorResult = errorResult;
	}
}

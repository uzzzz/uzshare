package uzblog.base.lang;

public class BlogException extends RuntimeException {
	private static final long serialVersionUID = -7443213283905815106L;
	private int code;

	public BlogException() {
	}

	/**
	 * @param code 错误代码
	 */
	public BlogException(int code) {
		super("code=" + code);
		this.code = code;
	}

	/**
	 * MtonsException
	 * 
	 * @param message 错误消息
	 */
	public BlogException(String message) {
		super(message);
	}

	/**
	 * @param cause 捕获的异常
	 */
	public BlogException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message 错误消息
	 * @param cause   捕获的异常
	 */
	public BlogException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code    错误代码
	 * @param message 错误消息
	 */
	public BlogException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}

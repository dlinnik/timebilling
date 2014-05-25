package ru.timebilling.rest.domain;

/**
 * Содержит информацию об ошибке для клиента
 * @author vshmelev
 *
 */
public class ErrorInfo {

	public final String url;
	public final String reason;

	public ErrorInfo(String url, Exception ex) {
		super();
		this.url = url;
		this.reason = ex.getMessage();
	}

	/**
	 * Service endpoint
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Причина ошибки
	 * TODO: использовать ключ ошибки чтобы была возможность выбирать текст ошибки для отображения на клиенте.
	 * @return
	 */
	public String getReason() {
		return reason;
	}
	
	

}

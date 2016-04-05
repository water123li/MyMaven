package cn.shaviation.mymaven.event;

/**
 * 消息中心
 * 
 * @author rli
 *
 */
public interface EventCenter {
	
	/**
	 * 发送某一事件
	 * @param event
	 * @throws Exception
	 */
	public void sendEvent(Event event) throws Exception;
		
}

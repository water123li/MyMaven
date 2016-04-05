package cn.shaviation.mymaven.event;

/**
 * 消息处理
 * 
 * @author rli
 *
 */
public interface EventHandler {
	
	/**
	 * 处理某一事件
	 * @param event
	 * @throws Exception
	 */
	public void handleEvent(Event event) throws Exception;
		
}

package cn.shaviation.mymaven.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import cn.shaviation.mymaven.util.SpringContextUtil;

/**
 * 消息中心
 * 
 * @author rli
 *
 */
@Component("eventCenter")
public class EventCenterImpl implements EventCenter, BeanFactoryPostProcessor {
	private String[] eventHandlerNames;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		eventHandlerNames = beanFactory.getBeanNamesForType(EventHandler.class);
	}

	@Override
	public void sendEvent(Event event) throws Exception {
		for (String beanName : eventHandlerNames) {
			EventHandler eventHandler= (EventHandler) SpringContextUtil.getBean(beanName, EventHandler.class);
			if (eventHandler != null) {
				eventHandler.handleEvent(event);
			}
		}

	}

}

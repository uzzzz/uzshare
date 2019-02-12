//package uzblog.boot;
//
//import org.ehcache.event.CacheEvent;
//import org.ehcache.event.CacheEventListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class CacheEventLogger implements CacheEventListener<Object, Object> {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(CacheEventLogger.class);
//
//	@Override
//	public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
//		LOGGER.info("Event: " + event.getType() + " Key: " + event.getKey() + " old value: " + event.getOldValue()
//				+ " new value: " + event.getNewValue());
//	}
//}
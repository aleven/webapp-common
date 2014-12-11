package it.attocchi.jpa2.entities;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static <T extends IEntityWithIdLong> List<Long> toListOfId(List<T> list) {
		List<Long> ids = new ArrayList<Long>();
		for (T entity : list) {
			ids.add(entity.getId());
		}
		return ids;
	}
	
}


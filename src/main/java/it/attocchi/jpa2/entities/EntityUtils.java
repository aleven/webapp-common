package it.attocchi.jpa2.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>EntityUtils class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class EntityUtils {

	/**
	 * <p>toListOfId.</p>
	 *
	 * @param list a {@link java.util.List} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 */
	public static <T extends IEntityWithIdLong> List<Long> toListOfId(List<T> list) {
		List<Long> ids = new ArrayList<Long>();
		for (T entity : list) {
			ids.add(entity.getId());
		}
		return ids;
	}
	
}


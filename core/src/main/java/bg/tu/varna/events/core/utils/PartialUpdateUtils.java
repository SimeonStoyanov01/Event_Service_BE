package bg.tu.varna.events.core.utils;

import bg.tu.varna.events.api.exceptions.PartialUpdateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Utility class for performing partial updates on entities.
 */
@Component
@RequiredArgsConstructor
public class PartialUpdateUtils {
	/**
	 * Updates the fields of the given entity with the values from the update object, excluding specified fields.
	 *
	 * @param entity
	 * 		the entity to be updated
	 * @param update
	 * 		the object containing updated values
	 * @param excludedFields
	 * 		a set of field names to be excluded from the update
	 * @param <T>
	 * 		the type of the entity
	 * @throws PartialUpdateException
	 * 		if an error occurs during the update process
	 */
	public <T> void updateFields(T entity, Object update, Set<String> excludedFields) {
		try {
			Map<String, Object> updateFields = getFields(update, excludedFields);
			for (Map.Entry<String, Object> entry : updateFields.entrySet()) {
				Field field;
				try {
					field = entity.getClass().getDeclaredField(entry.getKey());
					field.setAccessible(true);
					field.set(entity, entry.getValue());
				} catch (NoSuchFieldException e) {
					e.getLocalizedMessage();
				}
			}
		} catch (IllegalAccessException e) {
			throw new PartialUpdateException();
		}
	}
	/**
	 * Retrieves the fields and their values from the given object, excluding specified fields.
	 *
	 * @param obj            the object to extract fields from
	 * @param excludedFields a set of field names to be excluded
	 * @return a map of field names to their values
	 * @throws IllegalAccessException if the field values cannot be accessed
	 */
	private static Map<String, Object> getFields(Object obj, Set<String> excludedFields) throws IllegalAccessException {
		Map<String, Object> fieldMap = new HashMap<>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = field.get(obj);
			if (value != null && !excludedFields.contains(field.getName())) {
				fieldMap.put(field.getName(), value);
			}
		}
		return fieldMap;
	}
}

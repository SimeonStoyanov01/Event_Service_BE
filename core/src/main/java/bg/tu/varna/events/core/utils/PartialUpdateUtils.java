package bg.tu.varna.events.core.utils;

import bg.tu.varna.events.api.exceptions.PartialUpdateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PartialUpdateUtils {
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
					// Log and ignore the missing field in the entity
				}
			}
		} catch (IllegalAccessException e) {
			throw new PartialUpdateException();
		}
	}

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

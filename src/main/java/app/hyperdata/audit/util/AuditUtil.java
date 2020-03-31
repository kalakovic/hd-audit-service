package app.hyperdata.audit.util;

import app.hyperdata.audit.config.HDNotAudit;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;

public class AuditUtil {

	public static boolean isPropertyAudited(Object entity, String propertyName) {

		Field field = getField(entity, propertyName);
		if (field != null) {
			if (field.getAnnotation(HDNotAudit.class) != null) {
				return false;
			}
		}

		return true;
	}

	public static String getFieldName(Object entity, String propertyName) {

		Field field = getField(entity, propertyName);
		if (field != null) {
			if (field.getAnnotation(Column.class) != null) {
				return field.getAnnotation(Column.class).name();
			}
		}

		return null;
	}

	public static Field getField(Object entity, String propertyName) {
		Field field = null;
		if (entity != null && !StringUtils.isEmpty(propertyName)) {
			Class<?> entityClass = entity.getClass();
			do {
				try {
					field = entityClass.getDeclaredField(propertyName);
				} catch (NoSuchFieldException ex) {
					entityClass = entityClass.getSuperclass();
				}
			} while (field == null && entityClass != null);
		}
		return field;
	}

	public static String getEntityNameWithDbTableName(Object entity) {
		String entityName = entity.getClass().getSimpleName();
		String tableName = entity.getClass().getAnnotation(Table.class).name();
		//add database table name for the entity
		entityName = tableName != null ? entityName + " (" + tableName + ")" : entityName;

		return entityName;
	}

	public static String getPropertyNameWithDbColumnName(Object entity, String propertyName) {

		String fieldName = AuditUtil.getFieldName(entity, propertyName);
		//add database column name for the property
		propertyName = fieldName != null ? propertyName + " (" + fieldName + ")" : propertyName;

		return propertyName;
	}
}

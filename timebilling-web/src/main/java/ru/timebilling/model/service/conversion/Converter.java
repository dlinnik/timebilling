package ru.timebilling.model.service.conversion;

import ru.timebilling.model.domain.BaseEntity;
import ru.timebilling.model.service.ApplicationException;

/**
 * defines contract to convert Entity to DTO and vice versa
 * @author vshmelev
 *
 * @param <E>
 * @param <V>
 */
public interface Converter <E extends BaseEntity, V>{
	
	E toEntity(V v) throws ApplicationException;
	
	V toDTO(E e);

	Iterable<V> toDTO(Iterable<E> page);

}

package ru.timebilling.model.service.conversion;

import ru.timebilling.model.domain.BaseEntity;

/**
 * defines contract to convert Entity to DTO and vice versa
 * @author vshmelev
 *
 * @param <E>
 * @param <V>
 */
public interface Converter <E extends BaseEntity, V>{
	
	E toEntity(V v);
	
	V toDTO(E e);

	Iterable<V> toDTO(Iterable<E> page);

}

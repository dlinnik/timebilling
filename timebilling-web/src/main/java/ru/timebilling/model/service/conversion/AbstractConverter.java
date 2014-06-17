package ru.timebilling.model.service.conversion;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import ru.timebilling.model.domain.BaseEntity;

public abstract class AbstractConverter<E extends BaseEntity, V> implements Converter<E, V>{
	
	public Iterable<V> toDTO(Iterable<E> page) {
		return Lists.transform(Lists.newArrayList(page), new Function<E, V>(){
			@Override
			public V apply(E input) {
				return toDTO(input);
			}
			
		});
	}

}

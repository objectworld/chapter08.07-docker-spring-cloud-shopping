package org.objectworld.shopping.common.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class ObjectMapper {
	private static ModelMapper modelMapper = new ModelMapper();
	
//	static {
//		modelMapper = new ModelMapper();
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//	}
	
	/**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <S>      type of source object to map from.
     * @param <D>      type of result object.
     * @param entity   entity that needs to be mapped.
     * @param outClass class of result object.
     * @return new object of <code>outClass</code> type.
     */
    public static <S, D> D map(final S entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param entityList list of entities that needs to be mapped
     * @param outCLass   class of result list element
     * @param <S>        type of entity in <code>entityList</code>
     * @param <D>        type of objects in result list
     * @return list of mapped object with <code><D></code> type.
     */
    public static <S, D> List<D> mapAll(final Collection<S> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    /**
     * Maps {@code source} to {@code destination}.
     *
     * @param source      object to map from
     * @param destination object to map to
     */
    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}
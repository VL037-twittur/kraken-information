package vincentlow.twittur.information.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class ObjectMapper {

  public static <S, T> T map(S source, Class<T> targetClass) {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    MapperFacade mapper = mapperFactory.getMapperFacade();

    return mapper.map(source, targetClass);
  }
}

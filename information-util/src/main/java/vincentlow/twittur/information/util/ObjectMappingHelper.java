package vincentlow.twittur.information.util;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.base.web.model.response.BaseResponse;

@Slf4j
public class ObjectMappingHelper {

  public static <S, T extends BaseResponse> T toResponse(S source, Class<T> targetClass) {

    if (Objects.isNull(source)) {
      return null;
    }
    return ObjectMapper.map(source, targetClass);
  }
}

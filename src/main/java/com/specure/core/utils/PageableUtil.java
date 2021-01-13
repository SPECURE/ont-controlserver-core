package com.specure.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableUtil {
    public Pageable convertFromSnakeToCamel(Pageable old) {
        if (old.getSort().stream().count() == 0) {
            return PageRequest.of(old.getPageNumber(), old.getPageSize());
        } else {
            var order = old.getSort().stream().findFirst().orElseThrow();
            String phrase = order.getProperty();
            Sort.Direction direction = order.getDirection();
            while (phrase.contains("_")) {
                phrase = phrase.replaceFirst("_[a-z]",
                        String.valueOf(Character.toUpperCase(phrase.charAt(phrase.indexOf("_") + 1))));
            }
            return PageRequest.of(old.getPageNumber(), old.getPageSize(), Sort.by(direction, phrase));
        }
    }
}

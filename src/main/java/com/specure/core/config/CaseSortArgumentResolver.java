package com.specure.core.config;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableSet;
import com.specure.core.annotation.PageableCase;
import com.specure.core.constant.Case;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.*;


@Configuration
public class CaseSortArgumentResolver extends SortHandlerMethodArgumentResolver {
    private static final String KEYWORD = ".keyword";
    private Set<String> esKeywordMapping = ImmutableSet.of("operator", "openTestUuid");

    @Override
    public Sort resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String[] directionParameter = webRequest.getParameterValues(getSortParameter(parameter));
        final boolean isLowercase = Optional.of(parameter)
                .map(MethodParameter::getExecutable)
                .map(e -> e.getAnnotation(PageableCase.class))
                .map(PageableCase::value)
                .map(Case.LOWERCASE::equals)
                .orElse(false);

        if (directionParameter == null)
            return getDefaultFromAnnotationOrFallback(parameter);

        if (directionParameter.length == 1 && !StringUtils.hasText(directionParameter[0]))
            return getDefaultFromAnnotationOrFallback(parameter);

        return parseParameterIntoSort(Arrays.asList(directionParameter), getPropertyDelimiter(), isLowercase);
    }

    private Sort parseParameterIntoSort(List<String> source, String delimiter, boolean isLowercase) {
        final List<Sort.Order> allOrders = new ArrayList<>();

        for (String part : source) {

            if (part == null) {
                continue;
            }

            String[] elements = Arrays.stream(part.split(delimiter))
                    .filter(s -> StringUtils.hasText(s.replace(".", "")))
                    .toArray(String[]::new);

            Optional<Sort.Direction> direction = elements.length == 0 ? Optional.empty()
                    : Sort.Direction.fromOptionalString(elements[elements.length - 1]);

            int lastIndex = direction.map(it -> elements.length - 1).orElseGet(() -> elements.length);

            for (int i = 0; i < lastIndex; i++) {
                final String property = isLowercase ?
                        CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, getElement(elements, i)) :
                        getElement(elements, i);
                toOrder(property, direction).ifPresent(allOrders::add);
            }
        }

        return allOrders.isEmpty() ? Sort.unsorted() : Sort.by(allOrders);
    }

    private String getElement(String[] elements, int i) {
        final String element = elements[i];

        if (esKeywordMapping.contains(element))
            return element + KEYWORD;

        return element;
    }

    private static Optional<Sort.Order> toOrder(String property, Optional<Sort.Direction> direction) {
        if (!StringUtils.hasText(property))
            return Optional.empty();

        return Optional.of(direction.map(it -> new Sort.Order(it, property)).orElseGet(() -> Sort.Order.by(property)));
    }

}

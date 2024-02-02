package org.woodpecker.util.validation;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoHtmlValidator implements ConstraintValidator<NoHtml, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        return value == null || Jsoup.isValid(value, Safelist.none());
    }
}

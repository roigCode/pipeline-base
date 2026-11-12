package com.roigcode.app.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@AnalyzeClasses(packages = "com.roigcode.app", importOptions = ImportOption.DoNotIncludeTests.class)
class CodingRulesTest {

    @ArchTest
    static final ArchRule timeComesFromInjectedClock = noClasses()
            .should()
            .callMethod(Instant.class, "now")
            .orShould()
            .callMethod(LocalDate.class, "now")
            .orShould()
            .callMethod(LocalDateTime.class, "now")
            .orShould()
            .callMethod(LocalTime.class, "now")
            .orShould()
            .callMethod(OffsetDateTime.class, "now")
            .orShould()
            .callMethod(ZonedDateTime.class, "now")
            .orShould()
            .callMethod(System.class, "currentTimeMillis")
            .because("time must come from the injected java.time.Clock")
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule noFieldInjection = NO_CLASSES_SHOULD_USE_FIELD_INJECTION.allowEmptyShould(true);

    @ArchTest
    static final ArchRule noStandardStreams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.allowEmptyShould(true);

    @ArchTest
    static final ArchRule noGenericExceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.allowEmptyShould(true);

    @ArchTest
    static final ArchRule noJavaUtilLogging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.allowEmptyShould(true);
}

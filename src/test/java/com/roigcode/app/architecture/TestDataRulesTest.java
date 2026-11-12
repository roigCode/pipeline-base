package com.roigcode.app.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.roigcode.app.testsupport.TestFaker;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import java.util.Locale;
import java.util.Random;
import net.datafaker.Faker;

@AnalyzeClasses(packages = "com.roigcode.app", importOptions = ImportOption.OnlyIncludeTests.class)
class TestDataRulesTest {

    @ArchTest
    static final ArchRule fakerOnlyThroughTestFaker = noClasses()
            .that()
            .doNotHaveFullyQualifiedName(TestFaker.class.getName())
            .should()
            .callConstructor(Faker.class)
            .orShould()
            .callConstructor(Faker.class, Random.class)
            .orShould()
            .callConstructor(Faker.class, Locale.class)
            .orShould()
            .callConstructor(Faker.class, Locale.class, Random.class)
            .because("test data must use TestFaker.create(), which has a fixed seed")
            .allowEmptyShould(true);
}

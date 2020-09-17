package com.eniac.first;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.eniac.first");

        noClasses()
            .that()
                .resideInAnyPackage("com.eniac.first.service..")
            .or()
                .resideInAnyPackage("com.eniac.first.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.eniac.first.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}

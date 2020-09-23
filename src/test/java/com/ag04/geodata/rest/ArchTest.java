package com.ag04.geodata.rest;

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
            .importPackages("com.ag04.geodata.rest");

        noClasses()
            .that()
                .resideInAnyPackage("com.ag04.geodata.rest.service..")
            .or()
                .resideInAnyPackage("com.ag04.geodata.rest.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.ag04.geodata.rest.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}

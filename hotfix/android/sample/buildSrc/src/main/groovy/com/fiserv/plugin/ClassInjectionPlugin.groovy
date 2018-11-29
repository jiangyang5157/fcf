package com.fiserv.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class ClassInjectionPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // AppExtension -> build.gradle -> android {...}
        def android = project.extensions.findByType(AppExtension)
        def transform = new ClassInjectionTransform(project)
        android.registerTransform(transform)
    }
}
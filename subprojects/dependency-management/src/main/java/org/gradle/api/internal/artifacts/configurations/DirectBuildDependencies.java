/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.artifacts.configurations;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.FileCollectionDependency;
import org.gradle.api.internal.tasks.AbstractTaskDependency;
import org.gradle.api.internal.tasks.TaskDependencyResolveContext;
import org.gradle.api.tasks.TaskDependency;

/**
 * Represents the direct build dependencies of a Configuration.
 * These do not include the build dependencies of any transitive dependencies, but does include self-resolving dependencies of this configuration.
 */
public class DirectBuildDependencies extends AbstractTaskDependency {
    private final DependencySet dependencies;

    public static TaskDependency forDependenciesOnly(Configuration configuration) {
        return new DirectBuildDependencies(configuration.getAllDependencies());
    }

    private DirectBuildDependencies(DependencySet dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public void visitDependencies(TaskDependencyResolveContext context) {
        for (FileCollectionDependency dependency : dependencies.withType(FileCollectionDependency.class)) {
            context.add(dependency);
        }
    }
}

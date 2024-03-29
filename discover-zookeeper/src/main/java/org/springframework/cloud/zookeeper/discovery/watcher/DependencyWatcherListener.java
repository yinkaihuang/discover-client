/*
 * Copyright 2013-2015 the original author or authors.
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
package org.springframework.cloud.zookeeper.discovery.watcher;

/**
 * Performs logic upon change of state of a dependency {@link DependencyState}
 * in the service org.springframework.cloud.zookeeper.discovery system.
 *
 * @author Marcin Grzejszczak
 * @since 1.0.0
 *
 * @see org.springframework.cloud.zookeeper.discovery.dependency.ZookeeperDependencies
 */
public interface DependencyWatcherListener {

	/**
	 * Method executed upon state change of a dependency
	 *
	 * @param dependencyName - alias from microservice configuration
	 * @param newState - new state of the dependency
	 */
	void stateChanged(String dependencyName, DependencyState newState);
}

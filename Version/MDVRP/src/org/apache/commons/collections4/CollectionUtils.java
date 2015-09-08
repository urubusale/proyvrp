/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.iterators.PermutationIterator;

/**
 * Provides utility methods and decorators for {@link Collection} instances.
 * <p>
 * NOTE: From 4.0, method parameters will take {@link Iterable} objects when possible.
 *
 * @since 1.0
 * @version $Id: CollectionUtils.java 1540639 2013-11-11 08:54:12Z tn $
 */
public class CollectionUtils {

	
	/**
	 * Devuelve una colección de todas las permutaciones de la colección de entrada.
	 *
	 * @param	collection La colección para crear las permutaciones, no puede ser nulo.
	 * @return	Una colección desordenada de todas las permutaciones de la colección de entrada.
	 * 
	 */
    public static <E> Collection<List<E>> permutations(final Collection<E> collection) {
        final PermutationIterator<E> it = new PermutationIterator<E>(collection);
        final Collection<List<E>> result = new LinkedList<List<E>>();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result;
    }

}

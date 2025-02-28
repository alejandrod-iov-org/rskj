/*
 * This file is part of RskJ
 * Copyright (C) 2021 RSK Labs Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package co.rsk.pcc.altBN128.impls;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AbstractAltBN128Test {

    @Test
    public void testInitialization_JavaAltBN128() {
        AbstractAltBN128 result = AbstractAltBN128.init(() -> false, () -> null);
        assertTrue(result instanceof JavaAltBN128);
    }

    @Test
    public void testInitialization_fallbackOnJavaAltBN128() {
        AbstractAltBN128 result = AbstractAltBN128.init(() -> true, RuntimeException::new);
        assertTrue(result instanceof JavaAltBN128);
    }

    @Test
    public void testInitialization_GoAltBN128() {
        AbstractAltBN128 result = AbstractAltBN128.init(() -> true, () -> null);
        assertTrue(result instanceof GoAltBN128);
    }
}

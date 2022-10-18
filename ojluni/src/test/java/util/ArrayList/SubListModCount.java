/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/**
 * @test
 * @bug 8234423
 * @summary Modifying ArrayList.subList().subList() resets modCount of subList
 */

package test.java.util.ArrayList;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class SubListModCount {

    // Android-added: method added so TestNG can run the test.
    @org.testng.annotations.Test
    public static void runTests() {
        main(null);
    }

    public static void main(String[] args) {
        int failures = 0;
        var root = new ArrayList<Integer>();
        var subList = root.subList(0, 0);
        root.add(42);
        try {
            subList.size();
            failures++;
            System.out.println("Accessing subList");
        } catch (ConcurrentModificationException expected) {
        }
        var subSubList = subList.subList(0, 0);
        try {
            subSubList.size();
            failures++;
            System.out.println("Accessing subList.subList");
        } catch (ConcurrentModificationException expected) {
        }
        try {
            subSubList.add(42);
            failures++;
            System.out.println("Modifying subList.subList");
        } catch (ConcurrentModificationException expected) {
        }
        try {
            subList.size();
            failures++;
            System.out.println("Accessing subList again");
        } catch (ConcurrentModificationException expected) {
        }
        if (failures > 0) {
            throw new AssertionError(failures + " tests failed");
        }
    }
}
/*
 * MIT License
 *
 * Copyright (c) 2017 Kenji Saito
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.bitbuket.poad.example;

import com.github.poad.functional.type.tuple.Pair;
import com.github.poad.functional.type.tuple.Triple;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * .
 */
public class SampleTest {
    @Test
    public void test() {
        Arrays.asList(
                Pair.of(1, 2),
                Pair.of(3, 4)
        ).stream()
                .map(pair -> Triple.of(pair._1, pair._2, "a"))
                .map(triple -> triple._1 + ", " + triple._2 + ", " + triple._3)
                .collect(Collectors.toList())
                .stream().forEach(System.out::println);
    }
}

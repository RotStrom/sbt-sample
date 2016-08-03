package org.preownedkittens;

import org.junit.*;
import scala.collection.immutable.*;

public class LogicJavaTest {
    @Test
    public void testKitten() {
        Kitten kitten = new Kitten(1, new Vector<>(0, 0, 0));
        Assert.assertEquals(0, kitten.attributes().size());
    }
}
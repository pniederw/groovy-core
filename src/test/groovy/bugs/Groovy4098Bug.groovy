/*
 * Copyright 2003-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package groovy.bugs

class Groovy4098Bug extends GroovyTestCase {
    public String propertyOne
    public String propertyTwo
    public String propertyThree
    public String propertyFour
    public final String propertyFive = "five normal"
    public final String propertySix = "six normal"

    void setPropertyTwo(String propertyTwo) {
        this.propertyTwo = propertyTwo
    }

    String getPropertyThree() {
        propertyThree
    }

    String getPropertyFive() {
        propertyFive
    }

    String getPropertyFour() {
        propertyFour
    }

    void setPropertyFour(String propertyFour) {
        this.propertyFour = propertyFour
    }

    void testOne() {
        propertyOne = "one normal"
        assert propertyOne == "one normal"

        def metaProperty = this.metaClass.getMetaProperty("propertyOne")
        metaProperty.setProperty(this, "one mop")
        assert metaProperty.getProperty(this) == "one mop"
    }

    void testTwo() {
        propertyTwo = "two normal"
        assert propertyTwo == "two normal"

        def metaProperty = this.metaClass.getMetaProperty("propertyTwo")
        metaProperty.setProperty(this, "two mop")
        assert metaProperty.getProperty(this) == "two mop"
    }

    void testThree() {
        propertyThree = "three normal"
        assert propertyThree == "three normal"

        def metaProperty = this.metaClass.getMetaProperty("propertyThree")
        assert metaProperty.getProperty(this) == "three normal"
        metaProperty.setProperty(this, "three mop")
        assert metaProperty.getProperty(this) == "three mop"
    }

    void testFour() {
        propertyOne = "four normal"
        assert propertyOne == "four normal"

        def metaProperty = this.metaClass.getMetaProperty("propertyFour")
        metaProperty.setProperty(this, "four mop")
        assert metaProperty.getProperty(this) == "four mop"
    }

    void testFive() {
        assert propertyFive == "five normal"
        this.@propertyFive = "five changed" // Can't set using property here but can still brute force with field access
        assert propertyFive == "five changed"

        def metaProperty = this.metaClass.getMetaProperty("propertyFive")
        assert metaProperty.getProperty(this) == "five changed"
        def msg = shouldFail {
            metaProperty.setProperty(this, "five mop")
        }
        assert msg == "Cannot set read-only property: propertyFive"
    }

    void testSix() {
        assert propertySix == "six normal"
        this.@propertySix = "six changed" // Can't set using property here but can still brute force with field access
        assert propertySix == "six changed"

        def metaProperty = this.metaClass.getMetaProperty("propertySix")
        assert metaProperty.getProperty(this) == "six changed"
        def msg = shouldFail {
            metaProperty.setProperty(this, "six mop")
        }
        assert msg == "Cannot set the property 'propertySix' because the backing field is final."
    }
}
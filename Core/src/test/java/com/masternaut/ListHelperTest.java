package com.masternaut;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class ListHelperTest {
    @Test
    public void getSingleOrDefault() {
        List<String> singleItem = Arrays.asList("a");

        String result = ListHelper.getSingleOrDefault(singleItem);
        assertEquals("a", result);
    }

    @Test
    public void singleOrDefault_empty_List() {
        List<String> emptyList = new ArrayList<String>();

        String result = ListHelper.getSingleOrDefault(emptyList);
        assertNull(result);
    }

    @Test
    public void singleOrDefault_too_Many_Items() {
        List<String> tooManyItems = Arrays.asList("a", "b");

        try {
            ListHelper.getSingleOrDefault(tooManyItems);
        } catch (Exception e) {
            assertEquals("Too many results. Expected 0 or 1 but found more than 1", e.getMessage());
        }
    }

    @Test
    public void single() {
        List<String> singleItem = Arrays.asList("a");
        ListHelper.getSingle(singleItem);
    }

    @Test
    public void single_when_empty_list() {
        List<String> empty = new ArrayList<String>();
        try {
            ListHelper.getSingle(empty);
            fail("Exception not thrown");
        } catch (PaddingtonException ex) {
            Assert.assertEquals("No single item in list. Expected 1", ex.getMessage());
        }
    }

    @Test
    public void single_when_more_than_one_item() {
        List<String> manyItems = Arrays.asList("a", "b");
        try {
            ListHelper.getSingle(manyItems);
            fail("Exception not thrown");
        } catch (PaddingtonException ex) {
            Assert.assertEquals("Too many results. Expected 1 but found more than 1", ex.getMessage());
        }
    }

    @Test
    public void firstOrDefault_for_empty_list() {
        String val = ListHelper.getFirstOrDefault(new ArrayList<String>());
        assertNull(val);
    }

    @Test
    public void firstOrDefault_from__list() {
        String val = ListHelper.getFirstOrDefault(Arrays.asList("A", "B"));
        Assert.assertEquals("A", val);
    }

    @Test
    public void first_for_empty_list() {
        try {
            ListHelper.getFirst(new ArrayList<String>());
            fail("Exception not thrown");
        } catch (PaddingtonException t) {
            Assert.assertEquals("Empty list", t.getMessage());
        }
    }

    @Test
    public void first_from__list() {
        String val = ListHelper.getFirst(Arrays.asList("A", "B"));
        Assert.assertEquals("A", val);
    }

    @Test
    public void groupBy_with_selector() {
        MyClass dave = new MyClass("blue", "Dave");
        MyClass bob = new MyClass("blue", "Bob");
        MyClass mike = new MyClass("red", "Mike");

        List<MyClass> items = Arrays.asList(
                dave,
                bob,
                mike);

        Map<String, List<MyClass>> grouped = ListHelper.groupBy(items, new ListHelper.Selector<MyClass, String>() {
            @Override
            public String action(MyClass myClass) {
                return myClass.getColor();
            }
        });

        List<MyClass> blue = grouped.get("blue");
        List<MyClass> red = grouped.get("red");

        assertEquals(2, grouped.size());
        assertEquals(2, blue.size());
        assertEquals(1, red.size());

        assertEquals(dave, blue.get(0));
        assertEquals(bob, blue.get(1));
        assertEquals(mike, red.get(0));
    }

    @Test
    public void groupBy_with_null_key() {
        MyClass dave = new MyClass(null, "Dave");

        List<MyClass> items = Arrays.asList(
                dave
        );

        Map<String, List<MyClass>> grouped = ListHelper.groupBy(items, new ListHelper.Selector<MyClass, String>() {
            @Override
            public String action(MyClass myClass) {
                return myClass.getColor();
            }
        });

        assertEquals(1, grouped.size());
        assertEquals("Dave", grouped.get(null).get(0).getName());
    }

    @Test
    public void listToMap() {
        MyClass blue = new MyClass("Blue", "");
        MyClass red = new MyClass("Red", "");

        List<MyClass> list = Arrays.asList(blue, red);

        Map<String, MyClass> hashMap = ListHelper.listToMap(list, new ListHelper.Selector<MyClass, String>() {
            @Override
            public String action(MyClass myClass) {
                return myClass.getColor();
            }
        });

        assertEquals(2, hashMap.size());
        assertEquals(blue, hashMap.get("Blue"));
        assertEquals(red, hashMap.get("Red"));
    }

    @Test
    public void select_by_propertyName() {
        MyClass a = new MyClass("Blue", "N1");
        MyClass b = new MyClass("Red", "N2");

        List<String> selectedValues = ListHelper.select(Arrays.asList(a, b), "name", MyClass.class);
        Assert.assertEquals(2, selectedValues.size());

        Assert.assertEquals("N1", selectedValues.get(0));
        Assert.assertEquals("N2", selectedValues.get(1));

    }

    @Test
    public void select_by_selector() {
        MyClass a = new MyClass("Blue", "N1");
        MyClass b = new MyClass("Red", "N2");

        List<String> selectedValues = ListHelper.select(Arrays.asList(a, b), new ListHelper.Selector<MyClass, String>() {
            @Override
            public String action(MyClass myClass) {
                return myClass.getName();
            }
        });
        Assert.assertEquals(2, selectedValues.size());

        Assert.assertEquals("N1", selectedValues.get(0));
        Assert.assertEquals("N2", selectedValues.get(1));
    }

    @Test
    public void select_by_selector_with_template_return() {
        MyClass a = new MyClass("Blue", "N1");
        MyClass b = new MyClass("Red", "N2");

        List<MyClass> selectedValues = ListHelper.select(Arrays.asList(a, b), new ListHelper.Selector<MyClass, MyClass>() {
            @Override
            public MyClass action(MyClass myClass) {
                return myClass;
            }
        });
        Assert.assertEquals(2, selectedValues.size());

        Assert.assertEquals("N1", selectedValues.get(0).getName());
        Assert.assertEquals("N2", selectedValues.get(1).getName());
    }

    @Test
    public void take() {
        List<String> list = Arrays.asList("A", "B", "C");

        List<String> newList = ListHelper.take(list, 2);

        Assert.assertEquals(2, newList.size());
        Assert.assertEquals("A", newList.get(0));
        Assert.assertEquals("B", newList.get(1));
    }

    @Test
    public void take_when_not_enough_elements() {
        List<String> list = Arrays.asList("A");

        List<String> newList = ListHelper.take(list, 2);

        Assert.assertEquals(1, newList.size());
        Assert.assertEquals("A", newList.get(0));
    }

    @Test
    public void take_when_empty_list() {
        List<String> list = new ArrayList<String>();

        List<String> newList = ListHelper.take(list, 1);

        Assert.assertEquals(0, newList.size());
    }

    @Test
    public void contains_when_does() {
        List<MyClass> list = Arrays.asList(new MyClass("Blue", "Dave"), new MyClass("OtherColor", "OtherName"));

        boolean result = ListHelper.contains(list, new ListHelper.match<MyClass>() {
            @Override
            public boolean isMatch(MyClass myClass) {
                return myClass.color.equals("Blue");
            }
        });

        assertTrue(result);
    }

    @Test
    public void contains_when_does_not() {
        List<MyClass> list = Arrays.asList(new MyClass("Blue", "Dave"));

        boolean result = ListHelper.contains(list, new ListHelper.match<MyClass>() {
            @Override
            public boolean isMatch(MyClass myClass) {
                return myClass.color.equals("NotAColor");
            }
        });

        assertFalse(result);
    }

    @Test
    public void copyMap() {
        Map<Integer, String> source = new HashMap<Integer, String>();
        source.put(1, "A");
        source.put(2, "B");

        HashMap<Integer, String> copy = ListHelper.copyMap(source);
        assertNotSame(source, copy);
        assertEquals(2, copy.size());

        assertEquals("A", copy.get(1));
        assertEquals("B", copy.get(2));
    }

    @Test
    public void copyMap_when_null() {
        HashMap<Integer, String> map = ListHelper.copyMap(null);
        assertNull(map);
    }

    private class MyClass {
        private String color;
        private String name;

        private String id;

        private MyClass(String color, String name) {
            this.color = color;
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void iteratorToList() {
        Iterable<String> iterable = Arrays.asList("A", "B", "C");
        List<String> convertedList = ListHelper.toList(iterable);

        assertEquals(3, convertedList.size());
        assertEquals("A", convertedList.get(0));
        assertEquals("B", convertedList.get(1));
        assertEquals("C", convertedList.get(2));
    }
}

package hw6;

import hw6.bst.AvlTreeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to AVL Tree.
 */
@SuppressWarnings("All")
public class AvlTreeMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new AvlTreeMap<>();
  }

  @Test
  public void insertNoStructuralRotation() {
    map.insert("5", "b");
    map.insert("3", "a");
    map.insert("7", "c");

    String[] expected = new String[]{
      "5:b",
      "3:a 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    //Testing additional insert of a leaf - no structural rotation should occur
    map.insert("8", "d");
    String[] expectedChange = new String[]{
      "5:b",
      "3:a 7:c",
      "null null null 8:d"
    };
    assertEquals((String.join("\n", expectedChange) + "\n"), map.toString());
  }

  @Test
  public void putNoStructuralRotation() {
    map.insert("5", "b");
    map.insert("3", "a");
    map.insert("7", "c");

    String[] expected = new String[]{
      "5:b",
      "3:a 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    //Testing Put method - no structural rotation should occur
    map.put("7", "d");
    String[] expectedChange = new String[]{
      "5:b",
      "3:a 7:d"
    };
    assertEquals((String.join("\n", expectedChange) + "\n"), map.toString());
  }

  //Test for rotations: Insert case
  @Test
  public void insertLeftRotation() {
    map.insert("1", "a");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a
     */

    map.insert("2", "b");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a,
        null 2:b
     */

    map.insert("3", "c"); // it must do a left rotation here!
    // System.out.println(avl.toString());
    // must print
    /*
        2:b,
        1:a 3:c
     */

    String[] expected = new String[]{
        "2:b",
        "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightRotation() {
    map.insert("7", "c");
    map.insert("5", "b");
    map.insert("3", "a"); // point of right rotation

    String[] expected = new String[]{
      "5:b",
      "3:a 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightLeftRotation() {

    map.insert("5", "b");
    map.insert("3", "a");
    map.insert("7", "c");// point of right rotation

    String[] expected = new String[]{
      "5:b",
      "3:a 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRightRotation() {
    map.insert("7", "c");
    map.insert("3", "a");
    map.insert("5", "b"); // point of right rotation

    String[] expected = new String[]{
      "5:b",
      "3:a 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightleftLeftrightComplexRotation() {
    map.insert("10", "a");
    map.insert("05", "b");
    map.insert("20", "c");
    map.insert("07", "e");
    map.insert("06", "ee"); // Right-left
    map.insert("15", "f");
    map.insert("25", "g");
    map.insert("22", "h");
    map.insert("30", "i");
    map.insert("23", "j"); // LeftRight


    String[] expected = new String[]{
      "10:a",
      "06:ee 22:h",
      "05:b 07:e 20:c 25:g",
      "null null null null 15:f null 23:j 30:i"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  //Test for Rotation: Remove case

  @Test
  public void removeNoStructuralRotation() {
    map.insert("5", "b");
    map.insert("3", "a");
    map.insert("7", "c");
    map.insert("8","d");

    String[] expected = new String[]{
      "5:b",
      "3:a 7:c",
      "null null null 8:d"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("8");
    String[] expectedRemove = new String[]{
      "5:b",
      "3:a 7:c"
    };
    assertEquals((String.join("\n", expectedRemove) + "\n"), map.toString());

  }

  @Test
  public void removeRightRotation() {
    map.insert("07", "a");
    map.insert("04", "b");
    map.insert("11", "d");
    map.insert("03", "c");

    String[] expected = new String[]{
      "07:a",
      "04:b 11:d",
      "03:c null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("11");
    String[] expectedAfter = new String[]{
      "04:b",
      "03:c 07:a",
    };
    assertEquals((String.join("\n", expectedAfter) + "\n"), map.toString());
  }

    @Test
    public void removeLeftRotation() {
      map.insert("7", "a");
      map.insert("4", "b");
      map.insert("8", "c");
      map.insert("9", "d");


      String[] expected = new String[]{
        "7:a",
        "4:b 8:c",
        "null null null 9:d"
      };
      assertEquals((String.join("\n", expected) + "\n"), map.toString());

      map.remove("4");
      String[] expectedAfter = new String[]{
        "8:c",
        "7:a 9:d",
      };
      assertEquals((String.join("\n", expectedAfter) + "\n"), map.toString());

    }

  @Test
  public void removeRightLeftRotation() {
    map.insert("12", "a");
    map.insert("09", "b");
    map.insert("15", "c");
    map.insert("08", "d");
    map.insert("14", "e");
    map.insert("16", "f");
    map.insert("13", "g");


    String[] expected = new String[]{
      "12:a",
      "09:b 15:c",
      "08:d null 14:e 16:f",
      "null null null null 13:g null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("09");
    String[] expectedAfter = new String[]{
      "14:e",
      "12:a 15:c",
      "08:d 13:g null 16:f"
    };
    assertEquals((String.join("\n", expectedAfter) + "\n"), map.toString());

  }

  @Test
  public void removeLeftRightRotation() {
    map.insert("12", "a");
    map.insert("09", "b");
    map.insert("13", "c");
    map.insert("08", "d");
    map.insert("10", "e");
    map.insert("14", "f");
    map.insert("11", "g");


    String[] expected = new String[]{
      "12:a",
      "09:b 13:c",
      "08:d 10:e null 14:f",
      "null null null 11:g null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("13");
    String[] expectedAfter = new String[]{
      "10:e",
      "09:b 12:a",
      "08:d null 11:g 14:f"
    };
    assertEquals((String.join("\n", expectedAfter) + "\n"), map.toString());

  }

  @Test
  public void removeRightleftLeftrightComplexRotation() {
    map.insert("07", "a");
    map.insert("02", "b");
    map.insert("12", "c");
    map.insert("01", "d");
    map.insert("05", "e");
    map.insert("09", "f");
    map.insert("14", "g");
    map.insert("15", "gg");
    map.insert("00", "h");
    map.insert("04", "i");
    map.insert("06", "j");
    map.insert("08", "k");
    map.insert("10", "l");
    map.insert("03", "m");
    map.insert("11", "n");


    String[] expected = new String[]{
      "07:a",
      "02:b 12:c",
      "01:d 05:e 09:f 14:g",
      "00:h null 04:i 06:j 08:k 10:l null 15:gg",
      "null null null null 03:m null null null null null null 11:n null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("00");
    map.remove("15");
    String[] expectedAfter = new String[]{
      "07:a",
      "04:i 10:l",
      "02:b 05:e 09:f 12:c",
      "01:d 03:m null 06:j 08:k null 11:n 14:g"
    };
    assertEquals((String.join("\n", expectedAfter) + "\n"), map.toString());
  }

  @Test
  public void insertRemoveMultipleRotationsComplex() {
    map.insert("15","a");
    map.insert("20", "b");
    map.insert("24", "c");
    map.insert("10", "d");
    map.insert("13", "e");
    map.insert("07", "f");
    map.insert("30", "g");
    map.insert("36", "h");
    map.insert("25", "i");


    String[] expected = new String[]{
      "13:e",
      "10:d 24:c",
      "07:f null 20:b 30:g",
      "null null null null 15:a null 25:i 36:h"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("24");
    map.remove("20");

    String[] expectedRemoveFirst = new String[]{
      "13:e",
      "10:d 30:g",
      "07:f null 15:a 36:h",
      "null null null null null 25:i null null"

    };
    assertEquals((String.join("\n", expectedRemoveFirst) + "\n"), map.toString());
  }

  //Other methods testing: Insert, Remove, Get, Has, Put, Size, Iterator
  //Test for insert method
  @Test
  public void insertOneElement() {
    map.insert("key1", "value1");
    assertEquals(1, map.size());
    assertTrue(map.has("key1"));
    assertEquals("value1", map.get("key1"));
  }

  @Test
  public void insertMultipleElement() {
    map.insert("key1", "value1");
    map.insert("key2", "value2");
    map.insert("key3", "value3");
    assertEquals(3, map.size());
    assertTrue(map.has("key1"));
    assertTrue(map.has("key2"));
    assertTrue(map.has("key3"));
    assertEquals("value1", map.get("key1"));
    assertEquals("value2", map.get("key2"));
    assertEquals("value3", map.get("key3"));
  }

  @Test
  public void insertDuplicatedKey() {
    try {
      map.insert("key1", "value1");
      map.insert("key1", "value2");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void insertNullKey() {
    try {
      map.insert(null, "value1");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void insertDuplicatedValue() {
    map.insert("key1", "value1");
    map.insert("key2", "value1");
    assertEquals(2, map.size());
  }

  @Test
  public void insertNullValue() {
    map.insert("null", null);
    assertEquals(1, map.size());
  }

  //Test for remove method
  @Test
  public void removeOneElement() {
    map.insert("key1", "value1");
    assertEquals("value1", map.remove("key1"));
    assertEquals(0, map.size());
  }

  @Test
  public void removeMultipleElements() {
    map.insert("key1", "value1");
    map.insert("key2", "value2");
    map.insert("key3", "value3");
    assertEquals("value1", map.remove("key1"));
    assertEquals("value3", map.remove("key3"));
    assertEquals(1, map.size());
    assertFalse(map.has("key1"));
    assertTrue(map.has("key2"));
    assertFalse(map.has("key3"));
    assertEquals("value2", map.get("key2"));
  }

  @Test
  public void removeNull() {
    try {
      map.remove(null);
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void removeNoSuchElement() {
    try {
      map.remove("key1");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }
  //Testing for other remaining methods - put, get, length, iterator


  @Test
  public void updateValue() {
    map.insert("key1", "value1");
    map.put("key1", "value2");
    assertEquals(1, map.size());
    assertEquals("value2", map.get("key1"));
  }

  @Test
  public void updateMultipleValues() {
    map.insert("key1", "value1");
    map.insert("key2", "value2");
    map.insert("key3", "value3");
    map.put("key1", "updated1");
    map.put("key3", "updated3");
    assertEquals(3, map.size());
    assertEquals("updated1", map.get("key1"));
    assertEquals("value2", map.get("key2"));
    assertEquals("updated3", map.get("key3"));
  }

  @Test
  public void updateMultipleTimes() {
    map.insert("key1", "value1");
    map.put("key1", "value2");
    map.put("key1", "value3");
    map.put("key1", "value4");
    assertEquals(1, map.size());
    assertEquals("value4", map.get("key1"));
  }

  @Test
  public void updateNullValue() {
    map.insert("key1", "value1");
    map.put("key1", null);
    assertEquals(1, map.size());
    assertNull(map.get("key1"));
  }

  @Test
  public void updateNullKey() {
    try {
      map.put(null, "value");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void updateKeyNotMapped() {
    try {
      map.put("key", "value");
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void getKeyNull() {
    try {
      map.get(null);
      fail("Failed to throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void iteratorEmptyMap() {
    for (String key : map) {
      fail("Empty map!");
    }
  }

  @Test
  public void iteratorMultipleElements() {
    map.insert("key1", "value1");
    map.insert("key2", "value2");
    map.insert("key3", "value3");
    int counter = 0;
    for (String key : map) {
      counter++;
      assertTrue(map.has(key));
    }
    assertEquals(3, counter);
  }

}

package hw6;

import hw6.bst.TreapMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to Treap.
 */
@SuppressWarnings("All")
public class TreapMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new TreapMap<>();
  }

  private void setUpTree(int seedForTest) {
    map = new TreapMap<>(seedForTest);
  }

  //Test for rotations: Insert case
  @Test
  public void insertNoStructuralRotation() {
    setUpTree(15);
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    System.out.println(map);

    String[] expected = new String[]{
      "2:b:-1159716814",
      "1:a:-898526952 3:c:453225476"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    //Testing additional insert of a leaf with high priority - no structural rotation should occur
    map.insert("4","d");
    String[] expectedChange = new String[]{
      "2:b:-1159716814",
      "1:a:-898526952 3:c:453225476",
      "null null null 4:d:1796952534"
    };
    assertEquals((String.join("\n", expectedChange) + "\n"), map.toString());
  }


  @Test
  public void insertLeftRotation() {
    setUpTree(20);
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");

    String[] expected = new String[]{
      "2:b:-1704868423",
      "1:a:-1150867590 3:c:884779003"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightRotation() {
    setUpTree(20);
    map.insert("3", "a");
    map.insert("2", "b");
    map.insert("1", "c");

    String[] expected = new String[]{
      "2:b:-1704868423",
      "1:c:884779003 3:a:-1150867590"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  //Test for rotations: Remove case
  @Test
  public void removeRightRotation() {
    setUpTree(1);
    map.insert("3", "a");
    map.insert("2", "b");
    map.insert("1", "c");

    String[] expected = new String[]{
      "3:a:-1155869325",
      "2:b:431529176 null",
      "1:c:1761283695 null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("2");
    String[] expectedRemove = new String[]{
      "3:a:-1155869325",
      "1:c:1761283695 null"
    };
    assertEquals((String.join("\n", expectedRemove) + "\n"), map.toString());
  }

  @Test
  public void removeLeftRotation() {
    setUpTree(10);
    map.insert("1", "c");
    map.insert("3", "a");
    map.insert("2", "b");


    String[] expected = new String[]{
      "1:c:-1157793070",
      "null 2:b:1107254586",
      "null null null 3:a:1913984760"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("1");
    String[] expectedRemove = new String[]{
      "2:b:1107254586",
      "null 3:a:1913984760"
    };
    assertEquals((String.join("\n", expectedRemove) + "\n"), map.toString());
  }

  @Test
  public void removeRightLeftRotation() {
    setUpTree(20);
    map.insert("3", "a");
    map.insert("2", "b");
    map.insert("1", "c");

    String[] expected = new String[]{
      "2:b:-1704868423",
      "1:c:884779003 3:a:-1150867590"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("2");
    String[] expectedRemove = new String[]{
      "3:a:-1150867590",
      "1:c:884779003 null"
    };
    assertEquals((String.join("\n", expectedRemove) + "\n"), map.toString());
  }

  @Test
  public void removeLeaf() { //No Structural rotation should occur
    setUpTree(20);
    map.insert("3", "a");
    map.insert("2", "b");
    map.insert("1", "c");

    String[] expected = new String[]{
      "2:b:-1704868423",
      "1:c:884779003 3:a:-1150867590"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("3");
    String[] expectedRemove = new String[]{
      "2:b:-1704868423",
      "1:c:884779003 null"
    };
    assertEquals((String.join("\n", expectedRemove) + "\n"), map.toString());
  }

  @Test
  public void removeTargetHasOneChild() { //Also testing the case where single rotation is used
    setUpTree(10);
    map.insert("1", "c");
    map.insert("3", "a");
    map.insert("2", "b");


    String[] expected = new String[]{
      "1:c:-1157793070",
      "null 2:b:1107254586",
      "null null null 3:a:1913984760"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("2");
    String[] expectedRemove = new String[]{
      "1:c:-1157793070",
      "null 3:a:1913984760"
    };
    assertEquals((String.join("\n", expectedRemove) + "\n"), map.toString());
  }

  @Test
  public void removeRoot() { //Also testing the case where multiple rotations are used
    setUpTree(8);
    map.insert("2", "a");
    map.insert("1", "b");
    map.insert("4", "c");
    map.insert("3", "c");
    map.insert("5", "c");

    String[] expected = new String[]{
      "4:c:-1731337436",
      "2:a:-1158562568 5:c:858120744",
      "1:b:-70013384 3:c:64862043 null null",
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.remove("4");
    String[] expectedRemove = new String[]{
      "2:a:-1158562568",
      "1:b:-70013384 3:c:64862043",
      "null null null 5:c:858120744"
    };
    assertEquals((String.join("\n", expectedRemove) + "\n"), map.toString());
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